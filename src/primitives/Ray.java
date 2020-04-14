package primitives;

import java.util.Objects;

/**
 * presenting a ray
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Ray {

    final Point3D _POO;
    final Vector _direction;

    /**
     * constructor that gets a point and a vector and sets to local fields
     * @param point
     * @param vector
     */
    public Ray(Point3D point, Vector vector){
        _POO = new Point3D(point);
        _direction = new Vector(vector);
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
