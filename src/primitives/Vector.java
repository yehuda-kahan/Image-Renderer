package primitives;


import java.awt.*;
import java.util.Objects;

/**
 * presenting a vector that begins in the (0,0,0) and ends in the head, which we get
 * @authors Ofir Shmueli and Yehuda Kahan
 */
public class Vector {
    private Point3D _head;

    /**
     * constructor that gets point and sets _head with that point
     * @param point
     */
    public Vector(Point3D point){
        if(point.get_x()==(0,0,0)){

        }
        _head=point;
    }

    /**
     * copy constructor
     * @param other vector
     */
    public Vector(Vector other){
        _head=other._head;
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
