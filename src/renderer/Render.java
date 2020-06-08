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
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;


    // Number of recursive calls
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    // The constant k that decide who match the transparency and the reflection are will affect
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * Constructor
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene){

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

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j){
                Ray ray = camera.constructRayThroughPixel(nX,nY,j,i,distance,width,height);

                GeoPoint closestPoint = findClosestIntersection(ray);
                _imageWriter.writePixel(j, i, closestPoint == null ? background
                        : calcColor(closestPoint,ray).getColor());
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
        Point3D P0 = _scene.getCamera().get_p0();
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

        Vector v = _scene.getCamera().get_p0().subtract(point._point).normalize();
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
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level - 1, kkr).scale(kr));
        }
        double kt = point._geometry.getMaterial().getKT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n,point._point, inRay) ;
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay,
                        level - 1, kkt).scale(kt));
        }
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
        Vector r = n.scale(2 * l.dotProduct(n)).subtract(l);
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
        Point3D P0 = ray.get_POO();
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
