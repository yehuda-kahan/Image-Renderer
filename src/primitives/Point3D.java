package primitives;

import java.util.Objects;

/**
 * presenting a point in 3D that contains 3 coordinates
 */
public class Point3D {
    private Coordinate _x;
    private Coordinate _y;
    private Coordinate _z;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return point3D._x.equals(_x)
                && point3D._y.equals(_y)
                && point3D._z.equals(_z);
    }


}
