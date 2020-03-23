package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * presenting a sphere
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * constructor which gets point and sets to local field
     * @param point
     */
    public Sphere(Point3D point, double radius){
        super(radius);
        _center =new Point3D(point);
    }

    /**
     * getter
     * @return _center
     */
    public Point3D get_center() {
        return _center;
    }


    @Override
    public String toString() {
        return super.toString() + "_center=" + _center;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
