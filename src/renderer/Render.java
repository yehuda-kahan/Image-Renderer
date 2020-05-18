package renderer;

import elements.*;
import geometries.*;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Present image renderer
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Render {

    private ImageWriter _imageWriter;
    private Scene _scene;

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
        Color color = _scene.getAmbientLight().GetIntensity();
        color = color.add(point._geometry.getEmmission());
        return color;
    }

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
