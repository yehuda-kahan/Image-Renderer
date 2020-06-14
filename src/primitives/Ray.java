package primitives;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;



/**
 * presenting a ray
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Ray {

    final Point3D _POO;
    final Vector _direction;

    // Constant for moving the ray
    private static final double DELTA = 0.1;

    /**
     * constructor that gets a point and a vector and sets to local fields
     * @param point
     * @param vector
     */
    public Ray(Point3D point, Vector vector){
        _POO = new Point3D(point);
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
        _POO = head.add(delta);
        _direction = direction;
    }

    /**
     * copy constructor
     */
    public Ray(Ray other){
        _POO=new Point3D(other._POO);
        _direction = new Vector(other._direction);
    }

    /**
     * getter
     * @return _POO
     */
    public Point3D get_POO() { return _POO;}

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
       return _POO.add(_direction.scale(t));
    }

    /**
     * Gets the num of rays and the area's degrees where all the rays will be
     * @param NumOfRays num of additional rays
     * @param Degree of the area for all the rays
     * @param HitPoint of the ray to the area
     * @return original ray among with the additional rays
     */
    public List<Ray> raySplitter(int NumOfRays, double Degree, Point3D HitPoint){

        if (NumOfRays == 0) return List.of(this);

        double radius = Math.tan(Degree);

        Vector firstNormal = null;
        Vector secondNormal = null;
        if(_direction._head._x._coord == 0 && _direction._head._y._coord == 0)
            firstNormal = new Vector(1,0,0);
        else
            firstNormal = new Vector(-(_direction._head._y._coord),_direction._head._x._coord,0);

        secondNormal = _direction.crossProduct(firstNormal);

        List<Ray> splittedRays = new ArrayList<>();
        Point3D topPoint = null;
        Random random = new Random();
        double firstRandom=0;
        double secondRandom=0;
        for(int i=0;i<NumOfRays;i++) {
            firstRandom = radius * random.nextDouble();
            secondRandom = radius * random.nextDouble();
            topPoint = HitPoint.add(firstNormal.scale(firstRandom)).add(secondNormal.scale(secondRandom));
            splittedRays.add(new Ray(_POO, _POO.subtract(topPoint)));
        }
        splittedRays.add(this);
        return splittedRays;
    }

    @Override
    public String toString() {
        return "_POO : " +  _POO.toString() +" _direction : " + _direction.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return ray._POO.equals(_POO) && ray._direction.equals(_direction);
    }
}
