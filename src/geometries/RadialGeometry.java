package geometries;

import primitives.Color;
import primitives.Material;

/**
 * presenting a radial geometry
 * @author Ofir Shmueli, Yehuda Kahan
 */
public abstract class RadialGeometry extends Geometry {
    double _radius;


    /**
     * constructor that gets double value and sets to local param
     * @param radius
     */
    public RadialGeometry(double radius){
        _radius = radius;
    }

    /**
     * constructor that gets double value and sets to local param (radius)
     * and also get color for the emmisiion color of the radial geometry
     * @param radius
     * @param color For the emmission color of the radial geometry
     */
    public RadialGeometry(double radius , Color color){
        super(color);
        _radius = radius;
    }

    public RadialGeometry(double radius , Color color , Material material){

        this(radius,color);
        _material = new Material(material.getKD(),material.getKS(),material.getNShininess());
    }

    /**
     * copy constructor
     */
    public RadialGeometry(RadialGeometry other){
        _radius=other._radius;
    }

    /**
     * gets _radius value
     * @return _radius
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "_radius=" + _radius;
    }
}
