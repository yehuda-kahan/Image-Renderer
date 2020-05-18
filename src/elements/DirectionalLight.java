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
     * @param kA
     * @param direction
     */
    public DirectionalLight(Color intensity, double kA, Vector direction){
        super(intensity, kA);
        _direction = new Vector(direction);
    }

    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction.normalize();
    }
}
