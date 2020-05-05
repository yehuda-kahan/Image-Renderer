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
    public String get_name() {
        return _name;
    }

    /**
     * Getter
     * @return _background
     */
    public Color get_background() {
        return _background;
    }

    /**
     * Getter
     * @return _ambientLight
     */
    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    /**
     * Getter
     * @return _geometries
     */
    public Geometries get_geometries() {
        return _geometries;
    }

    /**
     * Getter
     * @return _camera
     */
    public Camera get_camera() {
        return _camera;
    }

    /**
     * Getter
     * @return _distance
     */
    public double get_distance() {
        return _distance;
    }

    /**
     * Setter
     * @param background
     */
    public void set_background(Color background) {
        _background = background;
    }

    /**
     * Setter
     * @param ambientLight
     */
    public void set_ambientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
    }

    /**
     * Setter
     * @param camera
     */
    public void set_camera(Camera camera) {
        _camera = camera;
    }

    /**
     * Setter
     * @param distance
     */
    public void set_distance(double distance) {
        _distance = distance;
    }

    public void addGeometries(Intersectable... geometries){
        _geometries.add(geometries);
    }
}
