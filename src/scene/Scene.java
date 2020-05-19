package scene;

import elements.*;
import geometries.*;
import primitives.*;

import java.util.*;


/**
 * Represents the scene in our picture
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Scene {

    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometries _geometries;
    private Camera _camera;
    private double _distance;
    private List<LightSource> _lights;


    /**
     * Constructor. initialize the geometries to empty array list
     * @param name name of the scene
     */
    public Scene(String name){
        _name = name;
        _geometries = new Geometries();
        _lights = new LinkedList<LightSource>();
    }

    /**
     * Getter
     * @return _lights
     */
    public List<LightSource> getLights() {
        return _lights;
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

    /**
     * Add all the given geometries to the _geometries filed
     * @param geometries
     */
    public void addGeometries(Intersectable... geometries){
        _geometries.add(geometries);
    }

    /**
     * Add all the given source light to the _light filed
     * @param lights
     */
    public void addLights(LightSource... lights) { _lights.addAll(List.of(lights)); }
}
