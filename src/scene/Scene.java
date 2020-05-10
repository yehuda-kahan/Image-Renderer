package scene;

import elements.*;
import geometries.*;
import primitives.*;

/**
 * Represents the scene in our picture
 */
public class Scene {

    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;


    /**
     * Constructor. initialize the geometries to empty array list
     * @param name name of the scene
     */
    public Scene(String name){
        _name = name;
        _geometries = new Geometries();
    }

    /**
     * Getter
     * @return _name
     */
    public String getName() {
        return _name;
    }

    /**
     * Getter
     * @return _background
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * Getter
     * @return _ambientLight
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * Getter
     * @return _geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * Getter
     * @return _camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * Getter
     * @return _distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * Setter
     * @param background
     */
    public void setBackground(Color background) {
        _background = background;
    }

    /**
     * Setter
     * @param ambientLight
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
    }

    /**
     * Setter
     * @param camera
     */
    public void setCamera(Camera camera) {
        _camera = camera;
    }

    /**
     * Setter
     * @param distance
     */
    public void setDistance(double distance) {
        _distance = distance;
    }

    public void addGeometries(Intersectable... geometries){
        _geometries.add(geometries);
    }
}
