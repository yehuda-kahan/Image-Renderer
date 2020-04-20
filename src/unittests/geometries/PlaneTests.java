package geometries;

import org.junit.Test;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTests {

    /**
     * Test method for {@link Plane#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        Plane plane = new Plane(new Point3D(-0.5,0,0)
                ,new Point3D(0,1,1)
                ,new Point3D(-1,-1,1));
        // Test if we get the actual normal
        assertEquals(new Vector(2,-1,0).normalize(), plane.getNormal(new Point3D(0,1,1)));

    }

    /**
     * Test method for {@link Plane#IsOnThePlane(Point3D)}
     */
    @Test
    public void IsOnThePlane(){

        // ============ Equivalence Partitions Tests ==============

        Plane plane = new Plane(new Point3D(1,0,0),new Vector(2,1,3));

        assertTrue(plane.IsOnThePlane(new Point3D(1,-2,4/6d)));
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)}
     */
    @Test
    public void findIntersections(){

        Plane plane = new Plane(new Point3D(1,0,0), new Vector(1,1,1));

        // ============ Equivalence Partitions Tests ==============

        // *** Group - Ray is neither parallel nor orthogonal to the plane
        //T1 : Ray dosen't intersect the plane
        assertEquals("Ray dosen't intersect the plane", null
        , plane.findIntersections(new Ray(new Point3D(1,1,1), new Vector(0,0,1))));

        //T2 : Ray intersect the plane
        Point3D p = new Point3D(1,1,-1);
        List<Point3D> result = plane.findIntersections(new Ray(new Point3D(1,1,1),new Vector(0,0,-1)));
        assertEquals("Ray intersect the plane", List.of(p),result);

        // =============== Boundary Values Tests ==================

        // ** Group - Ray is parallel to the plane (all 0 points)
        //T3 : Ray not included in the plane
        assertEquals("Ray not included in the plane", null
                , plane.findIntersections(new Ray(new Point3D(1,1,1), new Vector(1,0,-1))));

        //T4 : Ray include in the plane
        assertEquals("Ray included in the plane", null
                , plane.findIntersections(new Ray(new Point3D(0,1,0), new Vector(1,0,-1))));

        // ** Group - Ray is orthogonal to the plane
        //T5 : Ray start before the plane
        p = new Point3D(1/3d,1/3d,1/3d);
        result = plane.findIntersections(new Ray(new Point3D(1,1,1),new Vector(-1,-1,-1)));
        assertEquals("Ray start before the plane",List.of(p),result);

        //T6 : Ray start in the plane
        assertEquals("Ray start in the plane (orthogonal)", null
        , plane.findIntersections(new Ray(new Point3D(0,1,0),new Vector(-1,-1,-1))));

        //T7 : Ray start after the plane
        assertEquals("Ray start after the plane (orthogonal)", null
                , plane.findIntersections(new Ray(new Point3D(0,-1,0),new Vector(-1,-1,-1))));

        // *** Group - Ray is neither parallel nor orthogonal to the plane
        //T8 : Ray start at the plane
        assertEquals("Ray start at the plane", null
        ,plane.findIntersections(new Ray(new Point3D(0,1,0),new Vector(1,1,2))));

        //T9 : Ray start at the same point which appears as reference point in the plane
        assertEquals("Ray start at the same point which appears as reference point in the plane",null
        , plane.findIntersections(new Ray(new Point3D(1,0,0), new Vector(1,1,2))));

        //T10 : Ray insert with the point which appears as reference point in the plane
        p = new Point3D(1,0,0);
        result = plane.findIntersections(new Ray(new Point3D(2,1,1), new Vector(-1,-1,-1)));
        assertEquals("Ray start at the same point which appears as reference point in the plane",List.of(p)
                , result);


    }
}