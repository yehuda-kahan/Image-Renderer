package geometries;

import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import primitives.*;

import java.util.*;

/**
 * present a collection of geometries
 *
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Geometries extends Intersectable {

    private List<Intersectable> _geometries;

    /**
     * Default constructor
     */
    public Geometries() {
        _geometries = new ArrayList<>();
    }


    public Geometries(Intersectable... geometries) {
        _geometries = List.of(geometries);
        createBox();
    }

    /**
     * add a collection to the existing collection
     *
     * @param geometries The new added collection
     */
    public void add(Intersectable... geometries) {
        _geometries.addAll(List.of(geometries));
        createBox();
    }

    /**
     * Getter
     *
     * @return field _geometry
     */
    public List<Intersectable> getGeometries() {
        return _geometries;
    }


    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        if (_geometries.isEmpty())
            return null;

        List<GeoPoint> intersectPoints = new ArrayList<>();
        List<GeoPoint> tempIntersectPoints = null;
        for (Intersectable geometry : _geometries) {
            tempIntersectPoints = new ArrayList<>();
            tempIntersectPoints = geometry.findIntersections(ray);
            if (tempIntersectPoints == null)
                continue;
            intersectPoints.addAll(tempIntersectPoints);
        }
        if (intersectPoints.size() == 0)
            return null;
        return intersectPoints;
    }

    /**
     * find intersections between the current geometry and the given ray
     * with acceleration of BVH
     *
     * @param ray
     * @return
     */
    public List<GeoPoint> findIntersectionsBVH(Ray ray) {

        // If there are infinite geometries, find intersection anyway
        boolean containInfinite = false;
        for (Intersectable geometry : _geometries) {
            if (geometry instanceof Infinite) containInfinite = true;
        }

        if (!containInfinite && (_geometries.isEmpty() || !this.checkIntersectionWithBox(ray)))
            return null;

        List<GeoPoint> intersectPoints = new ArrayList<>();
        List<GeoPoint> tempIntersectPoints = null;
        for (Intersectable geometry : _geometries) {
            tempIntersectPoints = new ArrayList<>();
            if (geometry instanceof Geometries) {
                Geometries geometries = (Geometries) geometry;
                tempIntersectPoints = geometries.findIntersectionsBVH(ray);
            }
            else if (geometry instanceof Infinite || geometry.checkIntersectionWithBox(ray)) {
                tempIntersectPoints = geometry.findIntersections(ray);
            }
            if (tempIntersectPoints == null)
                continue;
            intersectPoints.addAll(tempIntersectPoints);
        }
        if (intersectPoints.size() == 0)
            return null;
        return intersectPoints;
    }

    /**
     * Create a BVH hierarchy tree from collection of geometries
     * Note : the tree contained only the finite geometries (and the infinite geometries
     * will be separately within the collection)
     * The complexity is N^3
     */
    public void buildHierarchyTreeN3() {
        List<Intersectable> infiniteGeometries = new LinkedList<>();//temp list for the plane
        for (Intersectable geo : _geometries) {
            if (geo instanceof Plane)
                infiniteGeometries.add(geo);
        }
        _geometries.removeAll(infiniteGeometries);
        double distance = 0;
        Intersectable left = null;
        Intersectable right = null;
        while (_geometries.size() > 1) {//stop when has only one geometries in the list
            double best = Double.POSITIVE_INFINITY;
            for (Intersectable geoI : _geometries)
                for (Intersectable geoJ : _geometries) {
                    ;
                    if (geoI != geoJ && (distance = distance(geoI, geoJ)) < best) {
                        best = distance;
                        left = geoI;
                        right = geoJ;
                    }
                }
            Geometries tempGeometries = new Geometries(left, right);
            _geometries.remove(left);
            _geometries.remove(right);
            _geometries.add(tempGeometries);
            System.out.println(_geometries.size());
        }
        _geometries.addAll(infiniteGeometries);
    }

    /**
     * Create a BVH hierarchy tree from collection of geometries
     * Note : the tree contained only the finite geometries (and the infinite geometries
     * will be separately within the collection)
     * The complexity is N^2
     */
    public void buildHierarchyTreeN2() {
        List<Intersectable> infiniteGeometries = new LinkedList<>();
        for (Intersectable geo : _geometries) {
            if (geo instanceof Plane)
                infiniteGeometries.add(geo);
        }
        _geometries.removeAll(infiniteGeometries);
        double distance = 0;
        Intersectable left = null;
        Intersectable right = null;
        while (_geometries.size() > 1) {//stop when has only one geometries in the list
            double best = Double.POSITIVE_INFINITY;
            Intersectable geoI = _geometries.get(0);
            for (Intersectable geoJ : _geometries) {
                if (geoI != geoJ && (distance = distance(geoI, geoJ)) < best) {
                    best = distance;
                    left = geoI;
                    right = geoJ;
                }
            }
            Geometries tempGeometries = new Geometries(left, right);
            _geometries.remove(left);
            _geometries.remove(right);
            _geometries.add(tempGeometries);
            System.out.println(_geometries.size());
        }
        _geometries.addAll(infiniteGeometries);
    }

    public void buildHierarchyTree() {
        if (_geometries.size() > 1000)
            buildHierarchyTreeN2();
        else
            buildHierarchyTreeN3();
    }

    @Override
    void createBox() {
        for (Intersectable geo : _geometries) {
            if (!(geo instanceof Infinite)) {
                _minX = geo._minX < _minX ? geo._minX : _minX;
                _maxX = geo._maxX > _maxX ? geo._maxX : _maxX;
                _minY = geo._minY < _minY ? geo._minY : _minY;
                _maxY = geo._maxY > _maxY ? geo._maxY : _maxY;
                _minZ = geo._minZ < _minZ ? geo._minZ : _minZ;
                _maxZ = geo._maxZ > _maxZ ? geo._maxZ : _maxZ;
            }
        }
    }
}
