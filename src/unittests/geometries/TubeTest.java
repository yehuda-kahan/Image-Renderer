package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

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

        assertEquals(new Vector(0,1,0),tb.getNormal(new Point3D(0,3,0)));
    }
}