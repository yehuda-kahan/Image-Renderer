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
        return null;
    }
}
