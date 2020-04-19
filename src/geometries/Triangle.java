package geometries;

import primitives.*;

import java.awt.*;
import java.util.List;

/**
 * presenting a triangle
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Triangle extends Polygon {

    /**
     * constructor that gets 3 points and sets local fields
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1,Point3D p2, Point3D p3){
        super(new Point3D(p1), new Point3D(p2), new Point3D(p3));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
