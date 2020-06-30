package geometries;

import primitives.*;

import java.util.List;

/**
 * presenting a tube
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Tube extends RadialGeometry implements Infinite {

    Ray _axisRay;

    /**
     * constructor that gets ray and radius, radius sent to parent constructor and ray sets to local field
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius){
        super(radius);
        _axisRay=new Ray(axisRay);
    }

    /**
     * constructor that gets ray and radius, radius sent to parent constructor and ray sets to local field
     * and gets the emmission color of the tube
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius , Color color){
        super(radius, color);
        _axisRay=new Ray(axisRay);
    }

    /**
     * constructor that gets ray and radius, radius sent to parent constructor and ray sets to local field
     * and gets the emmission color of the tube
     * and gets the material which the tube is made of
     * @param axisRay
     * @param radius
     * @param color
     * @param material
     */
    public Tube(Ray axisRay, double radius , Color color, Material material){

        this(axisRay,radius,color);
        _material = new Material(material.getKD(),material.getKS(),material.getNShininess());
    }

    /**
     * getter
     * @return _axisRay
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return super.toString() +
                " _axisRay=" + _axisRay;

    }

    @Override
    public Vector getNormal(Point3D point) {

        Vector temp;
        double t = _axisRay.getDirection().dotProduct(_axisRay.getP00().subtract(point));

        // in that case we cannot make scale action with t, and the normal will be -> p-POO
        if (t == 0)
            return new Vector(_axisRay.getP00().subtract(point)).normalize();

        Point3D O = _axisRay.getP00().add(_axisRay.getDirection().scale(t));
        return new Vector(O.subtract(point)).normalize();
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }

    @Override
    void createBox() {
       // no need a box
    }
}
