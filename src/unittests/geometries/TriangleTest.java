package geometries;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Triangle class
 */
public class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {

        // ============ Equivalence Partitions Tests ==============


        Triangle tr = new Triangle(new Point3D(0,0,1)
                , new Point3D(0,1,0)
                , new Point3D(1,0,0));

        assertEquals(new Vector(-1,-1,-1).normalize(), tr.getNormal(new Point3D(0,0,1)));

    }
}