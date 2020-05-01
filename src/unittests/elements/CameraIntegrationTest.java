package elements;

import geometries.*;
import org.junit.Test;
import static org.junit.Assert.*;
import primitives.*;

import java.util.List;

/**
 * Integration tests for elements.Camera, geometries.Geometries class
 */
public class CameraIntegrationTest {

    //Test Integration camera with Sphere
    @Test
     public void cameraIntersectSphere(){

        Sphere sphere =new Sphere(new Point3D(0,0,3),1);
        Camera camera =new Camera(Point3D.ZERO, new Vector(0,0,1), new Vector(0,-1,0));

        //T1 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,3) , r = 1)
        int sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = sphere.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,3) , r = 1)"
                ,2,sumIntersections);

        //T2 : Amount of intersections : [p0 = (0,0,-0.5) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,2.5) , r = 2.5)
        sphere = new Sphere(new Point3D(0,0,2.5),2.5);
        camera = new Camera(new Point3D(0,0,-0.5), new Vector(0,0,1), new Vector(0,-1,0));

        sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = sphere.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,-0.5) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,2.5) , r = 2.5)"
                ,18,sumIntersections);

        //T3 : Amount of intersections : [p0 = (0,0,-0.5) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,2) , r = 2)
        sphere = new Sphere(new Point3D(0,0,2),2);
        // same camera

        sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = sphere.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,-0.5) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,2) , r = 2)"
                ,10,sumIntersections);

        //T4 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,2) , r = 4)
        sphere = new Sphere(new Point3D(0,0,2),4);
        camera = new Camera(Point3D.ZERO,new Vector(0,0,1),new Vector(0,-1,0));

        sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = sphere.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,2) , r = 4)"
                ,9,sumIntersections);

        //T5 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,-1) , r = 0.5)
        sphere = new Sphere(new Point3D(0,0,-1),0.5);
        // same camera

        sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = sphere.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - (p0 = (0,0,-1) , r = 0.5)"
                ,0,sumIntersections);
    }

    //Test Integration camera with Plane
    @Test
    public void cameraIntersectPlane(){

        Plane plane = new Plane(new Point3D(0,0,3), new Point3D(2,1,3),new Point3D(2,2,3));
        Camera camera = new Camera(Point3D.ZERO, new Vector(0,0,1), new Vector(0,-1,0));

        //T1 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [p = (0,0,3) , N = (0,0,1)]
        int sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = plane.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [p = (2,0,0) , N = (1,0,0)]"
                ,9,sumIntersections);

        //T2 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [p = (0,0,5) , N = (0,0.2,1)]
        plane = new Plane(new Point3D(0,0,5),new Vector(0,-0.2,1));
        //same camera

        sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = plane.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [p = (0,0,5) , N = (0,0.2,1)]"
                ,9,sumIntersections);

        //T3 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [p = (0,0,5) , N = (0,1,1)]
        plane = new Plane(new Point3D(0,0,5), new Vector(0,1,1));
        // same camera
        sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = plane.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [p = (0,0,5) , N = (0,1,1)]"
                ,6,sumIntersections);
    }

    //Test Integration camera with Triangle
    @Test
    public void cameraIntersectTriangle(){

        Triangle triangle = new Triangle(new Point3D(0,-1,2)
                ,new Point3D(1,1,2),new Point3D(-1,1,2));
        Camera camera = new Camera(Point3D.ZERO, new Vector(0,0,1), new Vector(0,-1,0));

        //T1 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [P1 = (0,-1,2) , P2 = (1,1,2), P3 = (-1,1,2)]
        int sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = triangle.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [P1 = (0,-1,2) , P2 = (1,1,2), P3 = (-1,1,2)]"
                ,1,sumIntersections);

        //T2 : Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [P1 = (0,-20,2) , P2 = (1,1,2), P3 = (-1,1,2)]
        triangle = new Triangle(new Point3D(0,-20,2)
                ,new Point3D(1,1,2),new Point3D(-1,1,2));
        // same camera
        sumIntersections = 0;
        for (int i =0; i < 3; ++i){
            for (int j = 0; j <3; ++j){
                List<Point3D> intersectPoints = triangle.findIntersections(camera.constructRayThroughPixel(3,3,j,i,1,3,3));
                if (intersectPoints != null)
                    sumIntersections += intersectPoints.size();
            }
        }
        assertEquals("Amount of intersections : [p0 = (0,0,0) ,3x3 ,3x3(hxw) ,d = 1] - [P1 = (0,-20,2) , P2 = (1,1,2), P3 = (-1,1,2)]"
                ,2,sumIntersections);
    }
}
