package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Test
    public void findIntersections() {

        Tube tube = new Tube(new Ray(new Point3D(1,0,0),new Vector(0,0,1)),1);

        // ============ Equivalence Partitions Tests ==============

        //T1 : Ray start inside the tube and intersect ones
        Point3D p1 = new Point3D(1.8660254037844,0.5,-0.1928203230276);
        assertEquals("Ray start inside the tube and intersect ones", List.of(p1)
                ,tube.findIntersections(new Ray(new Ray(new Point3D(1,0.5,0.5),new Vector(1,0,-0.8)))));

        //T2 ; Ray cross the tube and intersect him Twice
        p1 = new Point3D(0,0,0.5);
        Point3D p2 = new Point3D(2,0,0.5);
        List<Point3D> result = tube.findIntersections(new Ray(new Point3D(-1,0,0.5),new Vector(1,0,0)));
        if (result.get(0).get_x().getCoord() > 0)
            result = List.of(result.get(1),result.get(0));
        assertEquals("Ray cross the tube and intersect him Twice", List.of(p1,p2)
                ,result);

        //T3 : Ray doesn't intersect with the tube
        assertEquals("Ray doesn't intersect with the tube",null
        ,tube.findIntersections(new Ray(new Point3D(-1,0,0.5),new Vector(1,1,0))));

        
    }
}