package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface of a geometry which includes function to get the normal
 * @author Ofir Shmueli, Yehuda Kahan
 */
public interface Geometry extends Intersectable {
    Vector getNormal (Point3D point);

}
