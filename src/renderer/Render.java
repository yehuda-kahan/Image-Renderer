package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import primitives.Color;
import scene.*;
import geometries.Intersectable.GeoPoint;

import java.awt.*;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Present image renderer
 */
public class Render {

    // Number of recursive calls
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    // The constant k that decide who match the transparency and the reflection are will affect
    private static final double MIN_CALC_COLOR_K = 0.001;
    // The distance
    private static final double DISTANCE = 10;

    private ImageWriter _imageWriter;
    private Scene _scene;
     //The number of additional rays that emerge when there is reflection and transparency
     private int _NumOfRays;
     //The number of degrees from reflection's or transparency's main ray that the new rays will create
     private double _Degrees;





    /**
     * Constructor
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene){

        _imageWriter = imageWriter;
        _scene = scene;
        _Degrees = 0;
        _NumOfRays = 0;
    }

    public Render(ImageWriter imageWriter, Scene scene, int NumOfRays, double Degrees){
        if(NumOfRays<0 || Degrees <0)
            throw new IllegalArgumentException("The num of rays and the num of degrees must be bigger/equal to zero");
        if(NumOfRays == 0 && Degrees > 0)
            throw new IllegalArgumentException("When the number of rays equals zero, the degree must be zero as well");
        _NumOfRays = NumOfRays;
        _Degrees = Degrees;
        _imageWriter = imageWriter;
        _scene = scene;
    }


    /**
     * Takes the scene and the image writer and makes the photo with them
     */
    public void renderImage(){

        Camera camera = _scene.getCamera();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double distance = _scene.getDistance();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        for (int i = 0; i < nY; ++i) {
            System.out.println(i);
            for (int j = 0; j < nX; ++j) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);

                GeoPoint closestPoint = findClosestIntersection(ray);
                _imageWriter.writePixel(j, i, closestPoint == null ? background
                        : calcColor(closestPoint, ray).getColor());
            }
        }
    }

    /**
     * The main calcColor function that calc the ambient light
     * and calls to the recursive calcColor func
     * @param point The pixel (point)
     * @param ray The direction of the looking
     *        (from the camera or after that from the reflection and the refraction)
     * @return Color of the given pixel
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }

    /**
     * calculate the closest point from the given points, from our camera
     * @param points
     * @return The closest point between all the given points
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points){
        double distance = Double.MAX_VALUE;
        Point3D P0 = _scene.getCamera().getP0();
        GeoPoint minDistancePoint = null;

        for (GeoPoint point: points)
            if (P0.distance(point._point) < distance){
                minDistancePoint = new GeoPoint(point._geometry,point._point);
                distance = P0.distance(point._point);
            }

        return minDistancePoint;
    }

    /**
     * Calculate the color of the pixel in the given point
     * @param point The given point (pixel)
     * @param inRay The direction from which we are looking
     * @param level The amount of numbers we do the recursion
     * @param k The level at which the darkening of the reflection and the refraction
     * @return
     */
    private Color calcColor(GeoPoint point, Ray inRay, int level, double k) {

        Color color = point._geometry.getEmmission();

        Vector v = _scene.getCamera().getP0().subtract(point._point).normalize();
        Vector n = point._geometry.getNormal(point._point);
        Material material = point._geometry.getMaterial();
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();

        double nv = n.dotProduct(v);

        for (LightSource lightSource : _scene.getLights()){
            Vector l = lightSource.getL(point._point);
            double nl = n.dotProduct(l);
            if ((nv >= 0 && nl >= 0) || (nv <= 0 && nl <= 0)){
                double ktr = transparency(lightSource,l,n,point);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(point._point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        if (level == 1) return Color.BLACK;
        double kr = point._geometry.getMaterial().getKR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, point._point, inRay);
            color = calcBeamColor(color,n,reflectedRay,level,kr,kkr);
        }
        double kt = point._geometry.getMaterial().getKT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n,point._point, inRay) ;
            color = calcBeamColor(color,n,refractedRay,level,kt,kkt);
        }
        return color;
    }

    /**
     * this function calculate the color of point with the help of beam
     *
     * @param color  the color of the intersection point
     * @param n      The normal vector of the point where beam start
     * @param refRay reflected/refracted ray
     * @param level  The level of recursiun
     * @param k      kt/kr
     * @param kk     kkt/kkr
     * @return The color
     */
    private Color calcBeamColor(Color color, Vector n, Ray refRay, int level, double k, double kk) {
        Color addColor = Color.BLACK;
        List<Ray> rays = refRay.raySplitter(n ,_NumOfRays, _Degrees ,DISTANCE);
        for (Ray ray : rays) {
            GeoPoint refPoint = findClosestIntersection(ray);
            if (refPoint != null) {
                addColor = addColor.add(calcColor(refPoint, ray, level - 1, kk).scale(k));
            }
        }
        int size = rays.size();
        color = color.add(size > 1 ? addColor.reduce(size) : addColor);
        return color;
    }

    /**
     * calculate the diffuse of the light
     * and according that we draw the color in the specific point
     *
     * @param kd attenuation of the Diffuse
     * @param l the direction from the light source to the point
     * @param n the normal to the geometry
     * @param lightIntensity on the point (according the kind of the light source)
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n , Color lightIntensity){

        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * calculate the specular of the light
     * and according that we draw the color in the specific point
     *
     * @param ks attenuation of the Specular
     * @param l the direction from the light source to the point
     * @param n the normal to the geometry
     * @param v the direction of the camera
     * @param nShininess the shininess level of the geometry
     * @param lightIntensity on the point (according the kind of the light source)
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity){
        Vector r = null;
        try {
            r = n.scale(2 * l.dotProduct(n)).subtract(l);
        }
        catch (IllegalArgumentException ex){
            return Color.BLACK;
        }
        Vector minusV = v.scale(-1);
        return lightIntensity.scale(ks * Math.pow(r.dotProduct(minusV),nShininess));
    }

    /**
     * calculate the shadow on a point from each of the source lights
     * @param light The light source that we want to check if he impact
     *        on our point
     * @param l The direction from the light source to the point
     * @param n The normal from the current point from current geometry
     * @param geopoint The current point
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint._point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return 1.0;
        double lightDistance = light.getDistance(geopoint._point);
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0){
                ktr *= gp._geometry.getMaterial().getKT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }


    /**
     * Calc the reflation ray from the camera (inRay direction)
     * @param n Normal of the geometry in the given point
     * @param point The point on the geometry
     * @param inRay The direction from where we are looking (camera)
     * @return
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay){
        Vector r = n.scale(2 * inRay.get_direction().dotProduct(n)).subtract(inRay.get_direction());
        return new Ray(point,r,n);
    }

    /**
     * Calc the refraction ray from the camera (inRay direction)
     * @param n Normal of the geometry in the given point
     * @param point The point on the geometry
     * @param inRay The direction from where we are looking (camera)
     * @return
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay){
        return new Ray(point , inRay.get_direction(), n);
    }

    /**
     * Find the closest intersection point from the head
     * of the ray
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray){
        Intersectable geometries = _scene.getGeometries();
        List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
        if (intersectionPoints == null) return null;

        double distance = Double.MAX_VALUE;
        Point3D P0 = ray.getP00();
        GeoPoint minDistancePoint = null;

        for (GeoPoint point: intersectionPoints)
            if (P0.distance(point._point) < distance){
                minDistancePoint = new GeoPoint(point._geometry,point._point);
                distance = P0.distance(point._point);
            }

        return minDistancePoint;
    }

    /**
     * Print a grid on the picture with size of Squares according
     * the interval
     * @param interval
     * @param color
     */
    public void printGrid(int interval, java.awt.Color color){

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j){
                if ((i % interval == 0 || j % interval == 0) && i != 0 && j != 0)
                    _imageWriter.writePixel(j,i,color);
            }
    }

    /**
     *  Make the image file (after the renderImage)
     */
    public void writeToImage(){
        _imageWriter.writeToImage();
    }
}
