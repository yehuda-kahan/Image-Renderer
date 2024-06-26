package geometries;

import primitives.*;

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
        _center =point;
        createBox();
    }

    /**
     * constructor which gets point and radius and set the locals fields
     * and also gets the emmission color of the sphere
     * @param point
     * @param radius
     * @param color
     */
    public Sphere(Point3D point, double radius , Color color){
        super(radius,color);
        _center =point;
        createBox();
    }

    /**
     * constructor which gets point and radius and set the locals fields
     * and also gets the emmission color of the sphere
     * and also gets the material which the sphere made of
     * @param point
     * @param radius
     * @param color
     * @param material
     */
    public Sphere(Point3D point, double radius , Color color, Material material){

        this(point,radius,color);
        _material = material;
    }

    /**
     * getter
     * @return _center
     */
    public Point3D getCenter() {
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
        if(ray.getP00().equals(_center))
        {
            GeoPoint geoPoint = new GeoPoint(this,new Point3D(ray.getPoint(_radius)));
            return List.of(geoPoint);
        }
        Vector u = ray.getP00().subtract(_center);
        double tm = alignZero(u.dotProduct(ray.getDirection()));
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

    @Override
    void createBox() {
        double x = _center.getX().getCoord();
        double y = _center.getY().getCoord();
        double z = _center.getZ().getCoord();
        double r = _radius;
        _minX = x - r;
        _maxX = x + r;
        _minY = y - r;
        _maxY = y + r;
        _minZ = z - r;
        _maxZ = z + r;
    }
}
