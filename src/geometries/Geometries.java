package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable {

    List<Intersectable> geometries = new ArrayList<>();

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
