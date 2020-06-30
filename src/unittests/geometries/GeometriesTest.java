package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Geometries class
 */
public class GeometriesTest {

    @Test
    public void add() {

        Geometries geo = new Geometries();

        //T1 : simple addition
        assertEquals("simple addition", 0, geo.getGeometries().size());
        Intersectable[] geometries = new Intersectable[2];
        geometries[0] = new Plane(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        geometries[1]= new Sphere(new Point3D(1,0,0),1);
        geo.add(geometries);
        assertEquals("simple addition", 2,geo.getGeometries().size());

    }

    @Test
    public void findIntersections() {

        Geometries geo = new Geometries();

        // =============== Boundary Values Tests ==================

        //T1 : The collection is empty
        assertEquals("The collection is empty", null
                    ,geo.findIntersections(new Ray(new Point3D(1,0,0),new Vector(1,1,0))));

        //T2 : Ray doesn't intersect any of the geometries
        Intersectable[] geometries = new Intersectable[2];
        geometries[0] = new Plane(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        geometries[1]= new Sphere(new Point3D(1,0,0),1);
        geo.add(geometries);
        assertEquals("Ray doesn't intersect any of the shapes", null
        , geo.findIntersections(new Ray(new Point3D(2,1,1),new Vector(-1,0,1))));

        //T3 : Ray intersect with one geometry only
        assertEquals("Ray intersect with one geometry only", 2
                ,geo.findIntersections(new Ray(new Point3D(0,0,2), new Vector(1,0,-1))).size());

        // =============== Equivalence Values Tests ==================

        //T4 : Ray intersect with few geometry (but not all of them)
        Intersectable[] g ={new Sphere(new Point3D(5,0,0),1)};
        geo.add(g); // add to 'geo' a teared geometry
        assertEquals("Ray intersect with few geometry (but not all of them)",3
        ,geo.findIntersections(new Ray(new Point3D(1,0,2), new Vector(0,0,-1))).size());

        // =============== Boundary Values Tests ==================

        //T5 : Ray intersect with all the geometries
        assertEquals("Ray intersect with all the geometries", 5
        ,geo.findIntersections(new Ray(new Point3D(-1,0,0),new Vector(1,0,0))).size());
    }
}