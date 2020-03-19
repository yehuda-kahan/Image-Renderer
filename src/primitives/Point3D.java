package primitives;

import java.util.Objects;

/**
 * presenting a point in 3D that contains 3 coordinates
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
    static final Point3D ZERO = (new Point3D(new Coordinate(0), new Coordinate(0),new Coordinate(0)));


    /**
     * constructor that gets 3 coordinates and sets to local params
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z){
        _x=x;
        _y=y;
        _z=z;
    }

    /**
     * לשאול את המרצה
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z){
        _x=new Coordinate(x);
        _y=new Coordinate(y);
        _z=new Coordinate(z);
    }

    /**
     * copy constructor
     * @param other coordinate
     */
    public Point3D(Point3D other){
        _x=other._x;
        _y=other._y;
        _z=other._z;
    }

    /**
     * gets the _x param
     * @return _x
     */
    public Coordinate get_x() {
        return _x;
    }

    /**
     * gets the _y param
     * @return _y
     */
    public Coordinate get_y() {
        return _y;
    }

    /**
     * gets the _z param
     * @return _z
     */
    public Coordinate get_z() {
        return _z;
    }

    /**
     * Vector subtractions which gets a point and subtracts from it our original point in order to get a vector
     * @param point
     * @return the new vector created from the points
     */
    public Vector subtract(Point3D point){
        return new Vector(point._x._coord - _x._coord, point._y._coord - _y._coord, point._z._coord - _z._coord );
    }

    public Point3D add(Vector vector){
        return new Point3D(_x._coord + vector._head._x._coord, _y._coord + vector._head._y._coord, _z._coord + vector._head._z._coord);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return point3D._x.equals(_x)
                && point3D._y.equals(_y)
                && point3D._z.equals(_z);
    }

    @Override
    public String toString() {
        return _x.toString() + " " +
                _y.toString() + " " +
                 _z.toString();

    }
}
