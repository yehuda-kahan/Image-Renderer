package elements;

import primitives.*;

/**
 * Presenting directional light element
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector _direction;

    /**
     * Constructor that sends intensity and kA to parent (Light)
     * sets _direction
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction){
        super(intensity,1);
        _direction = new Vector(direction).normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
