package primitives;

import java.lang.Math;

/**
 * presenting a point in 3D that contains 3 coordinates
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Point3D {

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;
    public static final Point3D ZERO = new Point3D(0,0,0);


    /**
     * constructor that gets 3 coordinates and sets to local params
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z){
        _x =new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z){
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * copy constructor
     * @param other coordinate
     */
    public Point3D(Point3D other){
        _x = new Coordinate(other._x.getCoord());
        _y = new Coordinate(other._y.getCoord());
        _z = new Coordinate(other._z.getCoord());
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

    /**
     * add the given vector to the current point and return a new point
     * @param vector
     * @return point3D
     */
    public Point3D add(Vector vector){
        return new Point3D(_x._coord + vector._head._x._coord,
                _y._coord + vector._head._y._coord,
                _z._coord + vector._head._z._coord);
    }

    /**
     * calculate the squared distance between the given point to the current point
     * @param point
     * @return distance Squared
     */
    public double distanceSquared(Point3D point){
        return (point._x._coord - _x._coord)*(point._x._coord - _x._coord) +
                (point._y._coord - _y._coord)*(point._y._coord - _y._coord)+
                (point._z._coord - _z._coord)*(point._z._coord - _z._coord);
    }

    /**
     * calculate the distance between the given point to the current point
     * @param point
     * @return distance between two points
     */
    public double distance(Point3D point){
        return Math.sqrt(distanceSquared(point));
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

    /**
     * לשנות
     *
     *
     *
     *
     *
     *
     * @return
     */
    @Override
    public String toString() {
        return "Point3D :" + _x.toString() + " " +
                _y.toString() + " " +
                 _z.toString();

    }
}
