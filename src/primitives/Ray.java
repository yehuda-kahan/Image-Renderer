package primitives;

import java.util.*;

import static primitives.Util.alignZero;
import static primitives.Util.getRandomNumber;


/**
 * presenting a ray
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Ray {

    final Point3D _P00;
    final Vector _direction;

    // Constant for moving the ray
    private static final double DELTA = 0.1;
    private Vector firstNormal;

    /**
     * constructor that gets a point and a vector and sets to local fields
     * @param point
     * @param vector
     */
    public Ray(Point3D point, Vector vector){
        _P00 = new Point3D(point);
        _direction = new Vector(vector).normalize();
    }

    /**
     * constructor that gets head point, direction, and normal
     * and construct a ray that start from head + delta in the direcion of the
     * normal.
     * @param head The head point of the ray (+ delta)
     * @param direction The direction of the ray
     * @param normal The direction of the delta
     */
    public Ray(Point3D head, Vector direction, Vector normal){
        Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : - DELTA);
        _P00 = head.add(delta);
        _direction = direction;
    }

    /**
     * copy constructor
     */
    public Ray(Ray other){
        _P00=new Point3D(other._P00);
        _direction = new Vector(other._direction);
    }

    /**
     * getter
     * @return _POO
     */
    public Point3D getP00() { return _P00;}

    /**
     * getter
     * @return _direction
     */
    public Vector get_direction() {
        return _direction;
    }

    /**
     * Gets a double and returns the point added with the direction scaled with the given double
     * @param t
     * @return
     */
    public Point3D getPoint(double t){
       return _P00.add(_direction.scale(t));
    }

    /**
     * Gets the num of rays and the area's degrees where all the rays will be
     * @param NumOfRays num of additional rays
     * @param Degree of the area for all the rays
     * @param distance of the ray to the area
     * @return original ray among with the additional rays
     */
    public List<Ray> raySplitter(Vector normal,int NumOfRays, double Degree , double distance){

        if (NumOfRays == 0) return List.of(this);

        double nv = alignZero(normal.dotProduct(_direction));

        double radius = Math.tan(Degree / 360d * 2 * Math.PI );

        Vector firstNormal = _direction.createNormal();
        Vector secondNormal = firstNormal.crossProduct(_direction);

        List<Ray> splittedRays = new LinkedList<>();
        Point3D centerCirclePoint = this.getPoint(distance);
        Point3D randomCirclePoint = null;
        Random random = new Random();
        double x=0;
        double y=0;
        double r = 0;
        double nr;
        for(int i=0;i<NumOfRays;i++) {
            randomCirclePoint = centerCirclePoint;
            x = getRandomNumber(-1,1);
            y = Math.sqrt(1 - x * x);
            r = getRandomNumber(-radius,radius);
            x = alignZero( x * r);
            y = alignZero( y * r);
            if (x != 0)
                randomCirclePoint = randomCirclePoint.add(firstNormal.scale(x));
            if (y != 0)
                randomCirclePoint =  randomCirclePoint.add(secondNormal.scale(y));
            Vector v = _P00.subtract(randomCirclePoint);
            nr = normal.dotProduct(v);
            if (nr * nv > 0)
                splittedRays.add(new Ray(_P00, v));
        }
        splittedRays.add(this);
        return splittedRays;
    }

    @Override
    public String toString() {
        return "_POO : " +  _P00.toString() +" _direction : " + _direction.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return ray._P00.equals(_P00) && ray._direction.equals(_direction);
    }
}
