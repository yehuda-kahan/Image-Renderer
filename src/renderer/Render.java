package renderer;

import elements.*;
import geometries.*;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.*;

import java.util.List;

/**
 * Present image renderer
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

                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, background);
                else{
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
                }
            }
    }

    private Point3D getClosestPoint(List<Point3D> points){
        double distance = Double.MAX_VALUE;
        Point3D P0 = _scene.getCamera().get_p0();
        Point3D minDistancePoint = null;

        for (Point3D point: points)
            if (P0.distance(point) < distance){
                minDistancePoint = new Point3D(point);
                distance = P0.distance(point);
            }

        return minDistancePoint;
    }

    private Color calcColor(Point3D point) {
        return _scene.getAmbientLight().GetIntensity();
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

    public void writeToImage(){
        _imageWriter.writeToImage();
    }
}
