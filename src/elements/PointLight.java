package elements;

import primitives.*;
import primitives.Color;

import java.awt.*;

/**
 * Present point light element
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    /**
     * Constructor
     * @param intensity of the color
     * @param kC attenuation of the distance
     * @param kL attenuation of the distance (linear)
     * @param kQ attenuation of the distance (square)
     * @param position Position of spot light
     */
    public PointLight(Color intensity, double kC, double kL, double kQ, Point3D position){
        super(intensity, 1);
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
        _position = new Point3D(position);
    }
    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(_position);
        Color color = _intensity.reduce(_kC + _kL*d + _kQ * d * d);
        return color;
    }

    @Override
    public Vector getL(Point3D p) {
        return new Vector(_position.subtract(p)).normalize();
    }
}
