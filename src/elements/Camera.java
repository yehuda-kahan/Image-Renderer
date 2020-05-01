package elements;

import primitives.*;

import static primitives.Util.*;

public class Camera {

    private Point3D _p0;
    private Vector _vUp;
    private Vector _vTo;
    private Vector _vRight;

    public Camera(Point3D p0, Vector vTo, Vector vUp){

        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The vUp and vTo must be perpendicular");

        _p0 = new Point3D(p0);
        _vTo = new Vector(vTo).normalize();
        _vUp = new Vector(vUp).normalize();
        _vRight = new Vector(vTo.crossProduct(vUp)).normalize();
    }

    /**
     * Create ray that passes through the pixel j,i
     * @param nX Number of pixels in the screen width
     * @param nY Number of pixels in the screen height
     * @param j Index of the width (start from zero)
     * @param i Index of the height (start from zero)
     * @param screenDistance Distance from the p0 of the camera
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     * @return Ray
     */
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight){

        if (screenDistance <= 0 || i < 0 || j < 0)
            throw new IllegalArgumentException("Screen distance must be positive");

        Point3D pC = _p0.add(_vTo.scale(screenDistance));

        double rY = screenHeight / nY; // Height of one pixel
        double rX = screenWidth / nX;  // Width of one pixel

        double xJ = (j - nX/2d) * rX + rX/2d;
        double yI = (i - nY/2d) * rY + rY/2d;

        Point3D pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(_vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(_vUp.scale(-yI));

        Vector vIJ = new Vector(_p0.subtract(pIJ));
        return new Ray(_p0,vIJ);
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
