package geometries;

import primitives.*;

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
     * getter
     * @return _height
     */
    public double get_height() {
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
        Plane bottom = new Plane(_axisRay.get_POO(),_axisRay.get_direction());

        // create a new plane that present the top of the cylinder
        Point3D P00Top = _axisRay.get_POO().add(_axisRay.get_direction().scale(_height));
        Plane top = new Plane(P00Top, new Vector(_axisRay.get_direction().scale(-1)));

        if (top.IsOnThePlane(point))
            return  new Vector(_axisRay.get_direction());
        if (bottom.IsOnThePlane(point))
            return new Vector(_axisRay.get_direction().scale(-1));
        // if the point is on the scope
        return super.getNormal(point);
    }
}
