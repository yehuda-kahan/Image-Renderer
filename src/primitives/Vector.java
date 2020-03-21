package primitives;


import java.awt.*;
import java.util.Objects;

/**
 * presenting a vector that begins in the (0,0,0) and ends in the head, which we get
 * @authors Ofir Shmueli and Yehuda Kahan
 */
public class Vector {

    final Point3D _head;

    public Vector(Coordinate x, Coordinate y, Coordinate z){
        if(x._coord==0 && y._coord==0 && z._coord==0){
            throw new IllegalArgumentException("The vector cannot be the Zero vector");
        }
        _head = new Point3D(x,y,z);

    }

    public Vector(double x, double y, double z){
        if(x==0 && y==0 && z==0) {
            throw new IllegalArgumentException("The vector cannot be the Zero vector");
        }
        _head = new Point3D(x,y,z);
    }

    /**
     * constructor that gets point and sets _head with that point
     * @param point
     */
    public Vector(Point3D point){

          if(point.equals(Point3D.ZERO)){
              throw new IllegalArgumentException("The vector cannot be the Zero vector");
                 }
        _head = new Point3D(point);
    }

    /**
     * copy constructor
     * @param other vector
     */
    public Vector(Vector other){
        _head = new Point3D(other._head);
    }

    /**
     * gets _head
     * @return _head
     */
    public Point3D get_head() {
        return _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

}
