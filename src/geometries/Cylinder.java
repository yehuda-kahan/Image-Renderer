package geometries;

import primitives.*;

import java.util.List;

/**
 * presenting cylinder
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Cylinder extends Tube {
    double _height;

    /**
     * constructor that gets a ray, radius and height, sends the ray and the radius to the parent constructor and sets height to local field
     * @param ray
     * @param radius
     * @param height
     */
    public Cylinder(Ray ray, double radius, double height){
        super(ray, radius);
        _height=height;
    }

    /**
     * constructor that gets a ray, radius and height,
     * sends the ray and the radius to the parent constructor and sets height to local field
     * and gets the emission color of the cylinder
     * @param ray
     * @param radius
     * @param height
     */
    public Cylinder(Ray ray, double radius, double height, Color color){
        super(ray, radius,color);
        _height=height;
    }

    /**
     * constructor that gets a ray, radius and height,
     * sends the ray and the radius to the parent constructor and sets height to local field
     * and gets the emission color of the cylinder
     * and gets the material which the cylinder is made of
     * @param ray
     * @param radius
     * @param height
     * @param color
     * @param material
     */
    public Cylinder(Ray ray, double radius, double height, Color color, Material material){
        this(ray, radius, height, color);
        _material = new Material(material.getKD(),material.getKS(),material.getNShininess());
    }

    /**
     * getter
     * @return _height
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return super.toString() +
                " _height=" + _height;
    }

    @Override
    public Vector getNormal(Point3D point) {

        // create a new plane that present the bottom of the cylinder
        Plane bottom = new Plane(_axisRay.getP00(),_axisRay.get_direction());

        // create a new plane that present the top of the cylinder
        Point3D P00Top = _axisRay.getP00().add(_axisRay.get_direction().scale(_height));
        Plane top = new Plane(P00Top, new Vector(_axisRay.get_direction().scale(-1)));

        if (top.IsOnThePlane(point))
            return  new Vector(_axisRay.get_direction());
        if (bottom.IsOnThePlane(point))
            return new Vector(_axisRay.get_direction().scale(-1));
        // if the point is on the scope
        return super.getNormal(point);
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }
}
