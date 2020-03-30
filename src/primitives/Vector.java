package primitives;


/**
 * presenting a vector that begins in the (0,0,0) and ends in the head, which we get
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Vector {

    Point3D _head;

    /**
     * contructor that gets 3 coordinate and sets the _head according to them
     * @param x
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z){
        if(x._coord==0 && y._coord==0 && z._coord==0){
            throw new IllegalArgumentException("The vector cannot be the Zero vector");
        }
        _head = new Point3D(x,y,z);

    }

    /**
     * comstructor that gets point data in 3 double variables and sets it as a point to _head
     * @param x
     * @param y
     * @param z
     */
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


    /**
     * subtracts the head vector from the given vector
     * @param vector
     * @return vector which subtracts the head vector from the given vector
     */
    public Vector subtract(Vector vector){

        return new Point3D(_head).subtract(vector._head);
//        return new Vector(vector._head._x._coord - _head._x._coord,
//                vector._head._y._coord - _head._y._coord,
//                vector._head._z._coord - _head._z._coord);
    }


    /**
     * adds the head vector to the given vector
     * @param vector
     * @return vector combined from the two vectors
     */
    public Vector add(Vector vector){

        return new Vector(new Point3D(_head).add(vector));
//        return new Vector(vector._head._x._coord + _head._x._coord,
//                vector._head._y._coord + _head._y._coord,
//                vector._head._z._coord + _head._z._coord);
    }


    /**
     * vector multiplied scalar
     * @param number
     * @return vector multiplied with scalar
     */
    public Vector scale(double number){
        return new Vector(number*_head._x._coord,number*_head._y._coord,number*_head._z._coord);
    }


    /**
     * scalar multiplication
     * @param vector
     * @return the sum of scalar multiplication
     */
    public double dotProduct(Vector vector){
        return _head._x._coord*vector._head._x._coord +
                _head._y._coord*vector._head._y._coord +
                _head._z._coord*vector._head._z._coord;
    }


    /**
     * cross multiplication
     * @param vector
     * @return the normal - vector which is the sum of cross multiplication
     */
    public Vector crossProduct(Vector vector){
        return new Vector(_head._y._coord*vector._head._z._coord - _head._z._coord*vector._head._y._coord,
                _head._z._coord*vector._head._x._coord - _head._x._coord*vector._head._z._coord,
                _head._x._coord*vector._head._y._coord - _head._y._coord*vector._head._x._coord);
    }


    /**
     * sends _head to distanceSquared function in Point3D class
     * @return the length squared
     */
    public double lengthSquared(){
        return Point3D.ZERO.distanceSquared(_head);
    }

    /**
     * calculating the length of the _head vector
     * @return the length of the _head vector
     */
    public double length(){
        return Math.sqrt(Point3D.ZERO.distanceSquared(_head));
    }

    /**
     * normalizing the vector
     * @return the normalized vector
     */
    public Vector normalize() {
        _head = new Point3D(_head._x._coord / length(), _head._y._coord / length(), _head._z._coord / length());
        return this;
    }

    /**
     *
     * @return a new normalized vector which has direction like the current vector
     */
    public Vector normalized(){
        Vector temp = new Vector(_head);
        return temp.normalize();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "Vector :" + _head.toString();
    }
}
