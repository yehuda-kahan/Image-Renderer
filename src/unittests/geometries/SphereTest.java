package geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

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
}