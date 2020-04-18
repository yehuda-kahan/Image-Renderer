package geometries;

import primitives.*;
import java.util.*;

public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}
