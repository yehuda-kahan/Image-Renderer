package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 */
public class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        Cylinder cl = new Cylinder(new Ray(new Point3D(0,1,0),new Vector(0,0,1)),2,2);

        // Test for point on the top
        assertEquals(new Vector(0,0,1),cl.getNormal(new Point3D(-1,1,2)));

        // Test for point on the bottom
        assertEquals(new Vector(0,0,-1),cl.getNormal(new Point3D(-1,1,0)));

        // Test for point on the side
        assertEquals(new Vector(0,1,0),cl.getNormal(new Point3D(0,3,1)));

        // =============== Boundary Values Tests ==================

        // Test for point on the side and the top
        assertEquals(new Vector(0,0,1), cl.getNormal(new Point3D(0,-1,2)));

        // Test for point on the side and the bottom
        assertEquals(new Vector(0,0,-1), cl.getNormal(new Point3D(0,-1,0)));

    }
}