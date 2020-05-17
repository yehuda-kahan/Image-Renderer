package geometries;
import primitives.*;

import java.util.*;

/**
 * present a collection of geometries
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _geometries;

    /**
     * Default constructor
     */
    public Geometries(){
        _geometries = new ArrayList<>();
    }


    public Geometries(Intersectable... geometries){
        _geometries = List.of(geometries);
    }

    /**
     * add a collection to the existing collection
     * @param geometries The new added collection
     */
    public void add(Intersectable... geometries){
        _geometries.addAll(List.of(geometries));
    }

    /**
     * @return field _geometry
     */
    public List<Intersectable> get_geometries(){return _geometries;}

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        if (_geometries.isEmpty())
            return null;

        List<GeoPoint> intersectPoints = new ArrayList<>();
        for (Intersectable geometry : _geometries){
            if (geometry.findIntersections(ray ) == null)
                continue;
            intersectPoints.addAll(geometry.findIntersections(ray));
        }
        if (intersectPoints.size() == 0)
            return null;
        return intersectPoints;
    }
}
