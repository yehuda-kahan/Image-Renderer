package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for geometries.Tube class
 */
public class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {

        // ============ Equivalence Partitions Tests ==============

        Tube tb = new Tube(new Ray(new Point3D(0,1,0),new Vector(0,0,1)),2);

        // Test if the return normal is proper
        assertEquals(new Vector(0,1,0),tb.getNormal(new Point3D(0,3,-3)));

        // =============== Boundary Values Tests ==================

        // Test if the return normal is proper (t == 0)
        assertEquals(new Vector(0,1,0),tb.getNormal(new Point3D(0,3,0)));
    }
}