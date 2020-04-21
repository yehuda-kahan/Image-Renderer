package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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

        Vector v1 = new Vector(p1.subtract(p2));
        Vector v2 = new Vector(p1.subtract(p3));
        // v1 is the right vector
        _normal = v1.crossProduct(v2).normalize();
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

    /**
     * checks if the given point is on the current plane
     * @param point the given point
     * @return true if is on the plane, and false otherwise
     */
    public boolean IsOnThePlane(Point3D point){

        // left side of the equation
        double leftSide = _normal.dotProduct(new Vector(point));
        // right side of the equation
        double rightSide = _normal.dotProduct(new Vector(_p));

        return isZero(rightSide - leftSide);
    }

    @Override
    public String toString() {
        return "_p=" + _p +
                ", _normal=" + _normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        if (ray.get_POO().equals(_p)) // Ray start at the point that present the plane
            return null;

        double nv = ray.get_direction().dotProduct(_normal);

        if (isZero(nv)) // Ray is parallel to the plane
            return null;

        double nQMinusP0 = _normal.dotProduct(ray.get_POO().subtract(_p));
        double t = alignZero(nQMinusP0/nv);

        if (t > 0) {
            Point3D p = new Point3D(ray.getPoint(t));
            return List.of(p);
        }
        return null;
    }
}
