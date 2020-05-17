package geometries;

import primitives.*;
import java.util.*;

public interface Intersectable {

    /**
     * Represent a point on a specific geometry
     */
    public static class GeoPoint {
        public Geometry _geometry;
        public Point3D _point;

        /**
         * Constructor witch initializing the type of the geometry and the intersection point on him
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point){
            _geometry = geometry;
            _point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return (geoPoint._geometry == _geometry) &&
                    _point.equals(geoPoint._point);
        }
    }

    /**
     * find intersections between the current geometry and the given ray
     * @param ray
     * @return The intersect of the ray with the geometry
     */
    List<GeoPoint> findIntersections(Ray ray);
}
