package geometries;

import org.junit.Test;
import geometries.Plane;
import primitives.Point3D;
import primitives.Vector;

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
}