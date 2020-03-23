package geometries;

/**
 * presenting a radial geometry
 * @author Ofir Shmueli, Yehuda Kahan
 */
public abstract class RadialGeometry implements Geometry {
    double _radius;


    /**
     * constructor that gets double value and sets to local param
     * @param radius
     */
    public RadialGeometry(double radius){
        _radius = radius;
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
    public double get_radius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "_radius=" + _radius;
    }
}
