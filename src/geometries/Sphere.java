package geometries;

import primitives.Color;
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
     * constructor which gets point and radius and set the locals fields
     * @param point
     */
    public Sphere(Point3D point, double radius){
        super(radius);
        _center =new Point3D(point);
    }

    /**
     * constructor which gets point and radius and set the locals fields
     * and also get the emmission color of the sphere
     * @param point
     * @param radius
     * @param color
     */
    public Sphere(Point3D point, double radius , Color color){
        super(radius,color);
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
    public List<GeoPoint> findIntersections(Ray ray) {

        // Ray start at the center of the sphere
        if(ray.get_POO().equals(_center))
        {
            GeoPoint geoPoint = new GeoPoint(this,new Point3D(ray.getPoint(_radius)));
            return List.of(geoPoint);
        }
        Vector u = ray.get_POO().subtract(_center);
        double tm = alignZero(u.dotProduct(ray.get_direction()));
        double d = alignZero(Math.sqrt(u.lengthSquared()-(tm*tm)));
        if(d>=_radius)
            return null;
        double th = alignZero(Math.sqrt((_radius*_radius)-(d*d)));
        double t1 = alignZero(tm+th);
        double t2 = alignZero(tm-th);
        if(t1>0 && t2>0)
        {
            GeoPoint geoPoint1 = new GeoPoint(this, new Point3D(ray.getPoint(t1)));
            GeoPoint geoPoint2 = new GeoPoint(this, new Point3D(ray.getPoint(t2)));
            return List.of(geoPoint1,geoPoint2);
        }
        if(t1<=0 && t2<=0)
        {
            return null;
        }
        if(t1>0 && t2<=0)
        {
            GeoPoint geoPoint = new GeoPoint(this, new Point3D(ray.getPoint(t1)));
            return List.of(geoPoint);
        }
        if(t1<=0 && t2>0)
        {
            GeoPoint geoPoint = new GeoPoint(this, new Point3D(ray.getPoint(t2)));
            return List.of(geoPoint);
        }
        return null;
    }
}
