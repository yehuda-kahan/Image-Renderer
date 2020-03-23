package geometries;

import primitives.*;

/**
 * presenting a plane by a point and normal
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Plane implements Geometry {

    Point3D _p;
    Vector _normal;

    /**
     * constructor that gets point and vector and sets to local fields
     * @param point
     * @param vector
     */
    public Plane(Point3D point, Vector vector){
        if (vector.get_head().equals(Point3D.ZERO)){
            throw new IllegalArgumentException("The vector cannot be the Zero vector");
        }
        _p = new Point3D(point);
        _normal = new Vector(vector);
    }

    /**
     *  constructor that gets 3 points, calculates the normal and sets to local fields
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3){
        _p = new Point3D(p1);
        _normal = null;
    }

    /**
     * getter
     * @return _p
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * getter
     * @return _normal
     */
    public Vector get_normal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "_p=" + _p +
                ", _normal=" + _normal;
    }

    /**
     *
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
