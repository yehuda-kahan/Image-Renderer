package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.List;

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
        return new Vector(new Point3D(_center).subtract(point)).normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        // Ray start at the center of the sphere
        if(ray.get_POO().equals(_center))
        {
            Point3D p = new Point3D(_center).add(ray.get_direction().scale(_radius));
            return List.of(p);
        }





        return null;
    }
}
