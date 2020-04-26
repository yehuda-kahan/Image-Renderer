package elements;

import primitives.*;

import static primitives.Util.*;

public class Camera {

    private Point3D _p0;
    private Vector _vUp;
    private Vector _vTo;
    private Vector _vRight;

    public Camera(Point3D p0, Vector vUp, Vector vTo){

        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The vUp and vTo must be perpendicular");

        _p0 = new Point3D(p0);
        _vUp = new Vector(vUp).normalize();
        _vTo = new Vector(vTo).normalize();
        _vRight = new Vector(vTo.crossProduct(vUp)).normalize();
    }

    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight){
        return null;
    }


    public Point3D get_p0() {
        return _p0;
    }

    public Vector get_vUp() {
        return _vUp;
    }

    public Vector get_vRight() {
        return _vRight;
    }

    public Vector get_vTo() {
        return _vTo;
    }
}
