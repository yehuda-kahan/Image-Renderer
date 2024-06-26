package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        createBox();
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);

        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * and also gets the emmisiion color of the current polygon
     * @param vertices list of vertices according to their order by edge path
     * @param color the emission color of the polygon
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Color color, Point3D... vertices) {

        this(vertices);
        _plane._emmission = new Color(color);
    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * and also gets the emmisiion color of the current polygon
     * @param vertices list of vertices according to their order by edge path
     * @param color the emission color of the polygon
     * @param material which the polygon made of
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Color color,Material material, Point3D... vertices){

        this(color,vertices);
        _plane._material = material;
    }


    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        if (_plane.findIntersections(ray) == null) // Ray doesn't intersect with the triangle
            return null;

        Vector temp;
        List<Vector> vectors = new ArrayList<>();
        for (int i = 0; i < _vertices.size(); ++i) {

            vectors.add(ray.getP00().subtract(_vertices.get(i)));
        }

        List<Vector> normals = new ArrayList<>();
        for (int i = 0 ; i < _vertices.size() - 1; ++i) {
            normals.add(vectors.get(i).crossProduct(vectors.get(i+1)));
        }
        normals.add(vectors.get(_vertices.size() - 1).crossProduct(vectors.get(0)));

        int plus = 0, minus = 0;
        for (int i = 0; i < _vertices.size(); ++i){
            double t = alignZero(normals.get(i).dotProduct(ray.getDirection()));
            if (isZero(t))
                return null;
            if(t > 0)
                plus++;
            else
                minus++;
        }
        if (plus != _vertices.size() && minus != _vertices.size())
            return null;
        return _plane.findIntersections(ray);
    }

    @Override
    void createBox() {
        double x;
        double y;
        double z;
        for (Point3D p : _vertices){
            x = p.getX().getCoord();
            y = p.getY().getCoord();
            z = p.getZ().getCoord();

            _minX = x < _minX ? x : _minX;
            _maxX = x > _maxX ? x : _maxX;
            _minY = y < _minY ? y : _minY;
            _maxY = y > _maxY ? y : _maxY;
            _minZ = z < _minZ ? z : _minZ;
            _maxZ = z > _maxZ ? z : _maxZ;
        }
    }
}
