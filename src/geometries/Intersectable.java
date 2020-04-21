package geometries;

import primitives.*;
import java.util.*;

public interface Intersectable {
    /**
     * find intersections between the current geometry and the given ray
     * @param ray
     * @return The intersect of the ray with the geometry
     */
    List<Point3D> findIntersections(Ray ray);
}
