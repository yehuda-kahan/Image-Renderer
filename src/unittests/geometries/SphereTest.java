package geometries;

import org.junit.Test;
import primitives.*;
import geometries.Intersectable.*;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 */
public class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        Sphere sp = new Sphere(new Point3D(0,1,0), 2);

        assertEquals(new Vector(0,0,1), sp.getNormal(new Point3D(0,1,2)));
    }


    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void findIntersections() {

        Sphere sphere = new Sphere( new Point3D(1, 0, 0),1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        GeoPoint p1 =new GeoPoint(sphere,new Point3D(0.0651530771650466, 0.355051025721682, 0));
        GeoPoint p2 =new GeoPoint(sphere, new Point3D(1.53484692283495, 0.844948974278318, 0));
        List<GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getX().getCoord() > result.get(1)._point.getX().getCoord())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        p1 =new GeoPoint(sphere, new Point3D(1.8,0.6,0));
        result = sphere.findIntersections(new Ray(new Point3D(1.5,0.5,0),new Vector(3,1,0)));
        assertEquals("Ray inside the sphere", List.of(p1),result);

        // TC04: Ray starts after the sphere (0 points)
        assertEquals("Ray start after the sphere", null ,
                sphere.findIntersections(new Ray(new Point3D(-1,0,0),new Vector(-3,-1,0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        p1 = new GeoPoint(sphere, new Point3D(0.4, 0.8, 0));
        result = sphere.findIntersections(new Ray(new Point3D(1,1,0), new Vector(-3,-1,0)));
        assertEquals("Ray starts at sphere and goes inside", List.of(p1),result);

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray starts at sphere and goes outside", null,
        sphere.findIntersections(new Ray(new Point3D(1,1,0),new Vector(3,1,0))));

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 =new GeoPoint(sphere, new Point3D(0, 0, 0));
        p2 =new GeoPoint(sphere, new Point3D(2, 0, 0));
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0)._point.getX().getCoord() > result.get(1)._point.getX().getCoord())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere (in the center)", List.of(p1, p2), result);

        // TC14: Ray starts at sphere and goes inside (1 points)
        p1 =new GeoPoint(sphere, new Point3D(2, 0, 0));
        result = sphere.findIntersections(new Ray(new Point3D(0,0,0),new Vector(1,0,0)));
        assertEquals("Ray starts at sphere and goes inside (across the center)", List.of(p1),result);

        // TC15: Ray starts inside (1 points)
        p1 =new GeoPoint(sphere, new Point3D(2, 0, 0));
        result = sphere.findIntersections(new Ray(new Point3D(1.5,0,0),new Vector(1,0,0)));
        assertEquals("Ray starts inside (center)", List.of(p1),result);

        // TC16: Ray starts at the center (1 points)
        p1 =new GeoPoint(sphere, new Point3D(2, 0, 0));
        result = sphere.findIntersections(new Ray(new Point3D(1,0,0),new Vector(1,0,0)));
        assertEquals("Ray starts at the center", List.of(p1),result);

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray starts at sphere and goes outside", null
                ,sphere.findIntersections(new Ray(new Point3D(2,0,0),new Vector(1,0,0))));

        // TC18: Ray starts after sphere (0 points)
        assertEquals("Ray starts after sphere (goes outside)", null
                ,sphere.findIntersections(new Ray(new Point3D(2.5,0,0),new Vector(1,0,0))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertEquals("Ray starts before the tangent point", null
                ,sphere.findIntersections(new Ray(new Point3D(0,1,0),new Vector(1,0,0))));

        // TC20: Ray starts at the tangent point
        assertEquals("Ray starts at the tangent point", null
                ,sphere.findIntersections(new Ray(new Point3D(1,1,0),new Vector(1,0,0))));

        // TC21: Ray starts after the tangent point
        assertEquals("Ray starts after the tangent point", null
                ,sphere.findIntersections(new Ray(new Point3D(1.5,1,0),new Vector(1,0,0))));

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertEquals("Ray is orthogonal to the center", null
                ,sphere.findIntersections(new Ray(new Point3D(1,2,0),new Vector(1,0,0))));

        // TC20: Ray is orthogonal to center minus P00 (ray's line is inside)
        p1=new GeoPoint(sphere, new Point3D(0.5, 0, 0.8660254037844));
        result = sphere.findIntersections(new Ray(new Point3D(0.5,0,0), new Vector(0,0,1)));
        assertEquals("Ray is orthogonal to center minus P00", List.of(p1), result);

    }
}