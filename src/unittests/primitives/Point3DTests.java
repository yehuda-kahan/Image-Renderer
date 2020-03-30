package primitives;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Point3D class
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Point3DTests {

    /**
     * Test method for {@link Point3D#subtract(Point3D)}
     */
    @Test
    public void subtract() {

        // ============ Equivalence Partitions Tests ==============

        Point3D p1 = new Point3D(1,-2,2);
        Point3D p2 = new Point3D(2,-2,1);

        // Test ordinary subtract from two points (include [negative - negative], [positive - positive])
        assertEquals("ERROR: subtracting the two points p1 and p2 is different from the returned value",new Vector(-1,0,1), p2.subtract(p1));
    }

    /**
     * Test method for {@link Point3D#add(Vector)}
     */
    @Test
    public void add() {

        // ============ Equivalence Partitions Tests ==============

        Vector v1 = new Vector(-1,1,2);
        Point3D p1 = new Point3D(-1,1,-2);

        // Test ordinary addition, point with vector ([positive + positive] , [negative + positive]..)
        assertEquals("ERROR: adding vector v1 to point p1 is different from the returned value",new Point3D(-2,2,0),p1.add(v1));
    }

    /**
     * Test method for {@link Point3D#distanceSquared(Point3D)}
     */
    @Test
    public void distanceSquared() {

        Point3D p1 = new Point3D(1,-2,0);
        Point3D p2 = new Point3D(-1,-2,3);

        // ============ Equivalence Partitions Tests ==============

        // Test distance squared of point3D
        assertEquals(13, p1.distanceSquared(p2),1e-10);
    }

    /**
     * Test method for {@link Point3D#distance(Point3D)}
     */
    @Test
    public void distance() {

        Point3D p1 = new Point3D(1,-2,0);
        Point3D p2 = new Point3D(-1,-2,3);

        // ============ Equivalence Partitions Tests ==============

        // Test distance of point3D
        assertEquals("ERROR: the distance between p1 and p2 is different from the returned value",3.605, p1.distance(p2),0.01);
    }
}