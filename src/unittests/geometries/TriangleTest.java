package geometries;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

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

    /**
     * Test method for {@link Triangle#findIntersections(Ray)}
     */
    @Test
    public void findIntersections(){

        Triangle tr = new Triangle(new Point3D(1,0,0)
                ,new Point3D(0,1,0),new Point3D(0,0,1));

        // All this tests after it is already known that the
        // ray intersect the plane containing the triangle (by using findIntersections of plane),
        // and now its check if its in the triangle

        // ============ Equivalence Partitions Tests ==============

        //T1 : Ray is against the triangle
        GeoPoint p = new GeoPoint(tr,new Point3D(1/3d,1/3d,1/3d));
        List<GeoPoint> result = tr.findIntersections(new Ray(new Point3D(1,1,1), new Vector(-1,-1,-1)));
        assertEquals("Ray is against the triangle", List.of(p),result);

        p = new GeoPoint(tr, new Point3D(0.4285714285714, 0.4285714285714, 0.1428571428571));
        result = tr.findIntersections(new Ray(new Point3D(1,1,1), new Vector(-1,-1,-1.5)));
        assertEquals("Ray is against the triangle", List.of(p),result);

        //T2 : Ray is against the side of the triangle
        assertEquals("Ray is against the side of the triangle", null
        ,tr.findIntersections(new Ray(new Point3D(0,2,1),new Vector(-1,-1,-1))));

        //T3: Ray is against the vertex of the triangle
        assertEquals("Ray is against the vertex of the triangle", null
                ,tr.findIntersections(new Ray(new Point3D(2,0,0),new Vector(-1,-1,-1))));

        // =============== Boundary Values Tests ==================

        // *** Group - as above but the ray begins at the plane
        //T4 : Ray start at the plane (inside)
        assertEquals("Ray start at the plane (inside the triangle)", null
                ,tr.findIntersections(new Ray(new Point3D(1/4d,1/4d,1/2d),new Vector(-1,-1,-1))));

        //T5 : Ray start at the plane (along side the triangle)
        assertEquals("Ray start at the plane (along side the triangle)", null
                ,tr.findIntersections(new Ray(new Point3D(1,1,-1),new Vector(-1,-1,-1))));

        //T6 : Ray start at the plane (front the vertex of the triangle)
        assertEquals("Ray start at the plane (front the vertex of the triangle)", null
                ,tr.findIntersections(new Ray(new Point3D(3,-1,-1),new Vector(-1,-1,-1))));

        // *** Group - Ray start at the boundaries of the triangle (before and at)
        //T7 : Ray start at the side of triangle
        assertEquals("Ray start at the side of triangle", null
                ,tr.findIntersections(new Ray(new Point3D(1/2d,1/2d,0),new Vector(-1,-1,-1))));

        //T8 : Ray start at the vertex of triangle
        assertEquals("Ray start at the vertex of triangle", null
                ,tr.findIntersections(new Ray(new Point3D(1,0,0),new Vector(-1,-1,-1))));

        //T9 : Ray start on front of the triangle vertex
        assertEquals("Ray start on front of the triangle vertex", null
                ,tr.findIntersections(new Ray(new Point3D(2,-1,0),new Vector(-1,-1,-1))));

        //T10 : Ray start before the side of triangle
        assertEquals("Ray start before the side of triangle", null
                ,tr.findIntersections(new Ray(new Point3D(3/2d,3/2d,1),new Vector(-1,-1,-1))));

        //T11 : Ray start before the vertex of triangle
        assertEquals("Ray start before the vertex of triangle", null
                ,tr.findIntersections(new Ray(new Point3D(2,1,1),new Vector(-1,-1,-1))));

        //T12 : Ray start before the front of the triangle vertex
        assertEquals("Ray start before the front of the triangle vertex", null
                ,tr.findIntersections(new Ray(new Point3D(3,0,1),new Vector(-1,-1,-1))));

        //T13 : Ray start at the the triangle vertex
        assertEquals("Ray start at the the triangle vertex", null
        ,tr.findIntersections(new Ray(new Point3D(1,0,0),new Vector(0,-1,0))));
    }
}