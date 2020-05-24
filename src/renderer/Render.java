package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import scene.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Present image renderer
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;

    // Constant for moving the ray
    private static final double DELTA = 0.1;

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
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double distance = _scene.getDistance();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j){
                Ray ray = camera.constructRayThroughPixel(nX,nY,j,i,distance,width,height);

                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, background);
                else{
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
                }
            }
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
     * @param point
     * @return The appropriate color
     */
    private Color calcColor(GeoPoint point) {
        Color color = _scene.getAmbientLight().getIntensity();
        color = color.add(point._geometry.getEmmission());

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
                if (unshaded(lightSource,l,n,point)) {
                    Color lightIntensity = lightSource.getIntensity(point._point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
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
     * @param l The direction from the light source to the point
     * @param n The normal from the current point from current geometry
     * @param geopoint The current point
     * @return 0 if there is some intersections points and 1 otherwise
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        Point3D point = geopoint._point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint._point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0)
                return false;
        }
        return true;
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
