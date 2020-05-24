package elements;

import primitives.*;


/**
 * Presenting spot light element
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class SpotLight extends PointLight  {

    private Vector _direction;

    /**
     * Constructor
     * @param intensity of the color
     * @param kC  attenuation of the distance
     * @param kL  attenuation of the distance (linear)
     * @param kQ  attenuation of the distance (square)
     * @param position of the spot light
     * @param direction of the spot light
     */
    public SpotLight(Color intensity, double kC, double kL, double kQ
            , Point3D position, Vector direction){

        super(intensity, kC, kL, kQ, position);
        _direction = new Vector(direction).normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {

        Color color = super.getIntensity(p);
        color = color.scale(Math.max(0, _direction.dotProduct(getL(p))));
        return color;
    }

    @Override
    public Vector getL(Point3D p) {
        return super.getL(p).normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return super.getDistance(point);
    }
}
