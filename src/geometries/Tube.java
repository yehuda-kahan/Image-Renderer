package geometries;

import primitives.*;

/**
 * presenting a tube
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Tube extends RadialGeometry {

    Ray _axisRay;

    /**
     * constructor that gets ray and radius, radius sent to parent constructor and ray sets to local field
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius){
        super(radius);
        _axisRay=new Ray(axisRay);
    }

    /**
     * getter
     * @return _axisRay
     */
    public Ray get_axisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return super.toString() +
                " _axisRay=" + _axisRay;

    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
