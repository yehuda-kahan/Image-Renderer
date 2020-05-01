package geometries;

import primitives.*;

import java.util.List;

/**
 * presenting a tube
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Tube extends RadialGeometry {

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
     * getter
     * @return _axisRay
     */
    public Ray get_axisRay() {
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
        double t = _axisRay.get_direction().dotProduct(_axisRay.get_POO().subtract(point));

        // in that case we cannot make scale action with t, and the normal will be -> p-POO
        if (t == 0)
            return new Vector(_axisRay.get_POO().subtract(point)).normalize();

        Point3D O = _axisRay.get_POO().add(_axisRay.get_direction().scale(t));
        return new Vector(O.subtract(point)).normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {


        double xp = ray.get_POO().get_x().getCoord();
        double yp = ray.get_POO().get_y().getCoord();
        double zp = ray.get_POO().get_z().getCoord();
        double xv = ray.get_direction().get_head().get_x().getCoord();
        double yv = ray.get_direction().get_head().get_y().getCoord();
        double zv = ray.get_direction().get_head().get_z().getCoord();
        double r = _radius;
        Point3D p1;
        Point3D p2;

        double a = 0;
        double b = 0;
        double c = 0;
        double xm = 0;
        double ym = 0;
        double zm = 0;
        double xm1 = 0;
        double ym1 = 0;
        double zm1 = 0;
        double xm2 = 0;
        double ym2 = 0;
        double zm2 = 0;
        double lambda = 0;
        double lambda1 = 0;
        double lambda2 = 0;
        double delta = 0;
        double rrad = 0;

        a = Math.pow(xv, 2) + Math.pow(yv, 2);
        b = 2 * (xv * xp + yv * yp);
        c = Math.pow(xp, 2) + Math.pow(yp, 2) - Math.pow(r, 2);

        delta = Math.pow(b, 2) - 4 * a * c;

        if (a == 0 && b == 0 && c == 0)
            return null;
        else {
            if (delta < 0)
                return null;
            else if (delta == 0) {
                //printf("1 intersection point :\n");
                lambda = -b / 2 * a;
                xm = xp + lambda * xv;
                ym = yp + lambda * yv;
                zm = zp + lambda * zv;
                p1 = new Point3D(xm,ym,zm);
                return List.of(p1);
            } else if (delta > 0) {
                //printf("2 intersection points :\n");
                lambda = (-b - Math.sqrt(delta)) / (2 * a);
                lambda2 = (-b + Math.sqrt(delta)) / (2 * a);
                xm1 = xp + lambda * xv;
                ym1 = yp + lambda * yv;
                zm1 = zp + lambda * zv;

                xm2 = xp + lambda2 * xv;
                ym2 = yp + lambda2 * yv;
                zm2 = zp + lambda2 * zv;
                p1 = new Point3D(xm1, ym1,zm1);
                p2 = new Point3D(xm2, ym2, zm2);
                return List.of(p1,p2);
            }
        }
        return null;
    }
}
