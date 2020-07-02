package geometries;

import primitives.*;

import java.util.*;

// Present a base class for geometry or collection of geometry.
// The class has :
// * fields witch present the box of each geometry
// or geometries.
// * abstract functions (that must be realized by any geometry or collection of geometries) :
// 1. List<GeoPoint> findIntersection(Ray)
// 2. void createBox()
// - see the comments below -
public abstract class Intersectable {

    // Presents the box for each geometry or geometries
    protected double _minX = Double.POSITIVE_INFINITY;
    protected double _maxX = Double.NEGATIVE_INFINITY;
    protected double _minY = Double.POSITIVE_INFINITY;
    protected double _maxY = Double.NEGATIVE_INFINITY;
    protected double _minZ = Double.POSITIVE_INFINITY;
    protected double _maxZ = Double.NEGATIVE_INFINITY;

    /**
     * find intersections between the current geometry or geometries, and the given ray
     *
     * @param ray
     * @return All the intersection points (List) of the ray with the geometry
     */
    abstract public List<GeoPoint> findIntersections(Ray ray);


    /**
     * Create box for the geometry or the geometries
     * Initializes the box fields - _minX , _maxX etc.
     * - must be implement by all the finite geometries -
     */
    abstract void createBox();

    /**
     * This function will be called from a particular geometry
     * or a collection of geometries, and check's if the ray
     * intersect with his box
     *
     * @param ray
     * @return true if ray intersect with the box, and false otherwise
     */
    public boolean checkIntersectionWithBox(Ray ray) {

        double xP = ray.getP00().getX().getCoord();
        double yP = ray.getP00().getY().getCoord();
        double zP = ray.getP00().getZ().getCoord();
        double xD = ray.getDirection().getHead().getX().getCoord();
        double yD = ray.getDirection().getHead().getY().getCoord();
        double zD = ray.getDirection().getHead().getZ().getCoord();
        double t;
        Point3D p;
        double x;
        double y;
        double z;

        //if (xD != 0){
        if ((t = (_minX - xP) / xD) > 0) {
            p = ray.getPoint(t);
            y = p.getY().getCoord();
            z = p.getZ().getCoord();
            if (y > _minY && y < _maxY && z > _minZ && z < _maxZ)
                return true;
        }
        if ((t = (_maxX - xP) / xD) > 0) {
            p = ray.getPoint(t);
            y = p.getY().getCoord();
            z = p.getZ().getCoord();
            if (y > _minY && y < _maxY && z > _minZ && z < _maxZ)
                return true;
        }
        // }

        //if (yD != 0){
        if ((t = (_minY - yP) / yD) > 0) {
            p = ray.getPoint(t);
            x = p.getX().getCoord();
            z = p.getZ().getCoord();
            if (x > _minX && x < _maxX && z > _minZ && z < _maxZ)
                return true;
        }
        if ((t = (_maxY - yP) / yD) > 0) {
            p = ray.getPoint(t);
            x = p.getX().getCoord();
            z = p.getZ().getCoord();
            if (x > _minX && x < _maxX && z > _minZ && z < _maxZ)
                return true;
        }
        // }

        //if (zD != 0){
        if ((t = (_minZ - zP) / zD) > 0) {
            p = ray.getPoint(t);
            x = p.getX().getCoord();
            y = p.getY().getCoord();
            if (x > _minX && x < _maxX && y > _minY && y < _maxY)
                return true;
        }
        if ((t = (_maxZ - zP) / zD) > 0) {
            p = ray.getPoint(t);
            x = p.getX().getCoord();
            y = p.getY().getCoord();
            if (x > _minX && x < _maxX && y > _minY && y < _maxY)
                return true;
        }
        // }
        return false;
    }

   /*public Boolean checkIntersectionWithBox(Ray ray) {
        Point3D pV = ray.getDirection().getHead();
        Point3D pC = ray.getP00();
        double pCX = pC.getX().getCoord();
        double pCY = pC.getY().getCoord();
        double pCZ = pC.getZ().getCoord();

        double pVX = pV.getX().getCoord();
        double pVY = pV.getY().getCoord();
        double pVZ = pV.getZ().getCoord();


        double tXmin = (_minX - pCX) / pVX;
        double tXmax = (_maxX - pCX) / pVX;
        if (tXmin > tXmax) {
            double temp = tXmin;
            tXmin = tXmax;
            tXmax = temp;
        }

        double tYmin = (_minY - pCY) / pVY;
        double tYmax = (_maxY - pCY) / pVY;
        if (tYmin > tYmax) {
            double temp = tYmin;
            tYmin = tYmax;
            tYmax = temp;
        }
        if ((tXmin > tYmax) || (tYmin > tXmax))
            return false;

        if (tYmin > tXmin)
            tXmin = tYmin;

        if (tYmax < tXmax)
            tXmax = tYmax;


        double tZmin = (_minZ - pCZ) / pVZ;
        double tZmax = (_maxZ - pCZ) / pVZ;

        if (tZmin > tZmax) {
            double temp = tZmin;
            tZmin = tZmax;
            tZmax = temp;
        }
        return ((tXmin <= tZmax) && (tZmin <= tXmax));
    }*/

    /**
     * Calculate the distance between two geometries
     * according the middle of they boxes
     * @param geoI
     * @param geoJ
     * @return The distance between geoI to geoJ
     */
    public static double distance(Intersectable geoI, Intersectable geoJ) {
        double midX, midY, midZ;
        midX = (geoI._minX + geoI._maxX) / 2;
        midY = (geoI._minY + geoI._maxY) / 2;
        midZ = (geoI._minZ + geoI._maxZ) / 2;
        Point3D pGeoI = new Point3D(midX, midY, midZ);

        midX = (geoJ._minX + geoJ._maxX) / 2;
        midY = (geoJ._minY + geoJ._maxY) / 2;
        midZ = (geoJ._minZ + geoJ._maxZ) / 2;
        Point3D pGeoJ = new Point3D(midX, midY, midZ);


        return pGeoI.distance(pGeoJ);
    }

    /**
     * Represent a point on a specific geometry
     *
     * @author Ofir Shmueli, Yehuda Kahan
     */
    public static class GeoPoint {
        public Geometry _geometry;
        public Point3D _point;

        /**
         * Constructor witch initializing the type of the geometry and the intersection point on him
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
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
}
