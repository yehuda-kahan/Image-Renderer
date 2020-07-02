package renderer;

import elements.*;

import geometries.Intersectable;

import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;

import static primitives.Util.getRandomNumber;

public class AccelerationTests {

    /**
     * 52 geometry in hierarchy build
     */
    @Test
    public void AccelerationTest(){

        Scene scene = new Scene("AccelerationTest");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
        for (int i = 0; i < 1; i++) {
            double rand = getRandomNumber(0,1);
            scene.addGeometries(

                    new Polygon(new Color(new Color(105, 105, 105)), new Material(0.2, 0.2, 30, rand, 0) // AEFD
                            , new Point3D( i * 80, i * 80,  i * 80), new Point3D( i * 80, 70 + i * 80,  i * 80)
                            , new Point3D(-50 + i * 80, 70 + i * 80, 50 + i * 80), new Point3D(-50 + i * 80, i * 80, 50 + i * 80))
                    , new Polygon(new Color(new Color(105, 105, 105)), new Material(0.2, 0.2, 30, rand, 0) // TOP
                            , new Point3D( i * 80, 70 + i * 80,  i * 80), new Point3D(-50 + i * 80, 70 + i * 80, 50 + i * 80)
                            , new Point3D( i * 80, 70 + i * 80, 100 + i * 80), new Point3D(50 + i * 80, 70 + i * 80, 50 + i * 80))
                    , new Polygon(new Color(new Color(105, 105, 105)), new Material(0.2, 0.2, 30, rand, 0) // DFHB
                            , new Point3D(-50 + i * 80,  i * 80, 50 + i * 80), new Point3D(-50 + i * 80, 70 + i * 80, 50 + i * 80)
                            , new Point3D( i * 80, 70 + i * 80, 100 + i * 80), new Point3D( i * 80,  i * 80, 100 + i * 80))
                    , new Polygon(new Color(new Color(105, 105, 105)), new Material(0.2, 0.2, 30, rand, 0) // BHGC
                            , new Point3D(i * 80,  i * 80, 100 + i * 80), new Point3D(i * 80, 70 + i * 80, 100 + i * 80)
                            , new Point3D(50 + i * 80, 70 + i * 80, 50 + i * 80), new Point3D(50 + i * 80,  i * 80, 50 + i * 80))
                    , new Polygon(new Color(new Color(105, 105, 105)), new Material(0.2, 0.2, 30, rand, 0) // CGEA
                            , new Point3D(50 + i * 80,  i * 80, 50 + i * 80), new Point3D(50 + i * 80, 70 + i * 80, 50 + i * 80)
                            , new Point3D( i * 80, 70 + i * 80, i * 80), new Point3D(i * 80, i * 80, i * 80))
                    , new Polygon(new Color(new Color(105, 105, 105)), new Material(0.2, 0.2, 30, rand, 0) // BOTTOM
                            , new Point3D(i * 80, i * 80, i * 80), new Point3D(-50 + i * 80, i * 80, 50 + i * 80)
                            , new Point3D(i * 80, i * 80, 100 + i * 80), new Point3D(50 + i * 80, i * 80, 50 + i * 80))

                    , new Sphere(new Point3D( i * 80, 35 + i * 80, 50 + i * 80), 25, new Color(getRandomNumber(100,220),getRandomNumber(0,345),getRandomNumber(30,255)), new Material(0.5, 0.5, 100))

                    //,new Plane(new Point3D(0,-8,-2),new Vector(0,4,1),new Color(java.awt.Color.DARK_GRAY),new Material(0.25,0.3,50,0,0.8))
                    //,new Plane(new Point3D(0,-20/7d,-2),new Vector(0,10/7d,1),new Color(java.awt.Color.DARK_GRAY),new Material(0,0,0,0,0.8))
                    // ,new Plane(new Point3D(0,0,150),new Vector(0,-0.2,-1),new Color(java.awt.Color.DARK_GRAY),new Material(0.2,0.1,100,0,0.8))


            );
        }

        scene.addGeometries(new Plane(new Point3D(0, -5, 0), new Vector(0, 1, 0)
                        ,new Color(java.awt.Color.DARK_GRAY), new Material(0.25, 0.3, 50, 0, 0.8)));


        scene.addLights(new SpotLight(new Color(1000, 600, 1000), 1,
                        0.0001, 0.00005, new Point3D(-100, 100, 100), new Vector(1, -0.4, -1))

                ,new DirectionalLight(new Color(255,215,0),new Vector(-1,-0.4,1))
        );


        ImageWriter imageWriter = new ImageWriter("cubes", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene,40,5,true).setMultithreading(3).setDebugPrint();


        scene.getGeometries().buildHierarchyTree();

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Star of triangles (manual tree)
     */
    @Test
    public void newTest2Triangle() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        int j = 50;
        int n1 = 0;
        int n2;

        Geometries cRight = new Geometries(), cLeft = new Geometries(), cUp = new Geometries(), cDown = new Geometries(),
                aRight = new Geometries(), aLeft = new Geometries(), bRight = new Geometries(), bLeft = new Geometries(),
                e = new Geometries(), f = new Geometries(), g = new Geometries(), d = new Geometries(), c = new Geometries(), a = new Geometries(), b = new Geometries();
        //        bLeft.add(new Triangle(new Color(getRandom(1, 255), getRandom(1, 250), getRandom(1, 255)), new Material(0.5, 0.5, 100, 0.5, 0.5),
//                new Point3D(-100, 400, 150), new Point3D(100, 400, 350), new Point3D(0, 200, 250)));
        for (int i = 1; i < 500; i++) {
            cUp.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(-50, n1 -= 30, j += 200), new Point3D(50, n1, j), new Point3D(0, n1 - 100, j)));
        }
        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            cDown.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(-50, n1 += 30, j += 200), new Point3D(50, n1, j), new Point3D(0, n1 + 100, j)));
        }

        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            cLeft.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, 50, j += 200), new Point3D(n1 + 100, 50, j), new Point3D(n1 + 50, -50, j)));
        }
        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            cRight.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, 50, j += 200), new Point3D(n1 + 100, 50, j), new Point3D(n1 + 50, -50, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            aRight.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, n2 -= 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            aLeft.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, n2 -= 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            bRight.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, n2 += 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;


        for (int i = 1; i < 500; i++) {
            bLeft.add(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, n2 += 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        a.add(cUp, aRight);
        b.add(aLeft, cLeft);
        c.add(a, b);
        d.add(cRight, bRight);
        e.add(cDown, bLeft);
        f.add(d, e);
        g.add(c, f);
        scene.addGeometries(g);

        scene.addLights(new SpotLight(new Color(java.awt.Color.white)
                         ,1, 4E-5, 2E-7,new Point3D(0, 0, -1500), new Vector(0, 0, 1)),
                new PointLight(new Color(java.awt.Color.white), 1, 4E-5, 2E-7,new Point3D(0.001, -100, 499)));

        ImageWriter imageWriter = new ImageWriter("BVH 4000Triangles with like star", 400, 400, 500, 500);
        Render render = new Render(imageWriter, scene, 30, 5).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

    /**
     * 34  m 57 s without acceleration (and threads)
     * 16 s with acceleration and threads
     * Star of triangles with hierarchy tree build
     */
    @Test
    public void TestTriangleWithHierarchy() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        int j = 50;
        int n1 = 0;
        int n2;


        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(-50, n1 -= 30, j += 200), new Point3D(50, n1, j), new Point3D(0, n1 - 100, j)));
        }
        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(-50, n1 += 30, j += 200), new Point3D(50, n1, j), new Point3D(0, n1 + 100, j)));
        }

        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, 50, j += 200), new Point3D(n1 + 100, 50, j), new Point3D(n1 + 50, -50, j)));
        }
        j = 50;
        n1 = 0;

        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, 50, j += 200), new Point3D(n1 + 100, 50, j), new Point3D(n1 + 50, -50, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, n2 -= 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, n2 -= 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;

        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 += 50, n2 += 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }
        j = 50;
        n1 = 0;
        n2 = 0;


        for (int i = 1; i < 500; i++) {
            scene.addGeometries(new Triangle(new Color(getRandomNumber(1, 180), getRandomNumber(1, 400), getRandomNumber(1, 300)), new Material(0.2, 0.2, 200, 0, 0.5)
                    , new Point3D(n1 -= 50, n2 += 50, j += 200), new Point3D(n1 + 100, n2, j), new Point3D(n1 + 50, n2 - 100, j)));
        }

        scene.addLights(new DirectionalLight(new Color(240,230,140), new Vector(0,0.5,1))
                ,new PointLight(new Color(100,55,234), 1, 0.001, 0.00001
                        , new Point3D(0,0,0))
               , new SpotLight(new Color(java.awt.Color.white)
                        ,1, 4E-5, 2E-7,new Point3D(0, 0, -1500), new Vector(0, 0, 1)),
                new PointLight(new Color(java.awt.Color.white), 1, 4E-5, 2E-7,new Point3D(0.001, -100, 499)));

        ImageWriter imageWriter = new ImageWriter("no BVH 4000Triangles with like star", 400, 400, 500, 500);
        Render render = new Render(imageWriter, scene, 40, 5).setMultithreading(3).setDebugPrint();

        //scene.getGeometries().buildHierarchyTree();

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void improvePerformanceHirarchy() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
       /* Geometries
                a = new Geometries(),
                b = new Geometries(),
                c = new Geometries(),
                d = new Geometries();*/

        //the sea
        scene.addGeometries(
                new Plane(new Point3D(-150, 200, 115), new Vector(0, 1, 0), new Color(java.awt.Color.BLUE),new Material(0.5, 0.4, 100, 0.001, 0))
                 ,new Sphere( new Point3D(290, -290, 1000),100
                        ,new Color(200, 200, 0), new Material(0.5, 0.5, 200, 0, 0.2)                        ),
                new Sphere( new Point3D(60, -50, -10000),50
                        ,new Color(java.awt.Color.BLACK), new Material(0, 0, 100, 0, 1))
               , new Plane(new Point3D(0, 0, 30000), new Vector(0, 0, 1), new Color(100, 100, 500),new Material(0.2, 0.2, 70, 0, 0.5))
                         ,new Triangle(Color.BLACK, new Material(0.8, 0.5, 60), new Point3D(0, 125, 1200),
                        new Point3D(0, -100, 1200), new Point3D(90, 125, 1210)),
                new Triangle(Color.BLACK, new Material(0.8, 0.5, 60), new Point3D(0, 125, 1200),
                        new Point3D(0, -100, 1200), new Point3D(-90, 125, 1210)),
                new Polygon(Color.BLACK, new Material(0.2, 0.5, 60), new Point3D(-150, 125, 1200),
                        new Point3D(-105, 200, 1200), new Point3D(105, 200, 1200), new Point3D(150, 125, 1200))
                , new Sphere( new Point3D(-95, -150, 1000),25
                        ,new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2)),
                new Sphere(new Point3D(-145, -130, 1000),25
                        , new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2)),
                new Sphere(new Point3D(-125, -130, 1000),24
                        , new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2)),
                new Sphere(new Point3D(-135, -170, 1000),25
                        , new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2)),
                new Sphere(new Point3D(-115, -170, 1000),24
                        , new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2)),
                new Sphere(new Point3D(-165, -150, 1000),25
                        , new Color(java.awt.Color.WHITE), new Material(0.5, 0.5, 200, 0, 0.2)
                ));

        double j = 3000, n = 200, n1 = 20000, n2, n3 = 10, n4 = 2, n6 = 15, n7 = -3000, count = 1;
        for (int i = 1; i < 10000; i++) {
            if (j <= n7) {
                j = 3000;
                j -= 20 * count++;
                n7 += 20;
                n1 -= 6000 / n4;
                n4 += 1;
                j = j - n6;
                n6 *= -1;
            }
            n2 = n1 - n3;
            scene.addGeometries(new Triangle(new Color(java.awt.Color.BLUE), new Material(0.2, 0.8, 300, 0, 0),
                    new Point3D(j -= 45, n, n2), new Point3D(j + 12, n - 5, n2),
                    new Point3D(j + 24, n, n2)));
            n3 *= -1;

        }
        scene.addLights(new SpotLight(new Color(400, 400, 700), //
                         1, 4E-5, 2E-7,new Point3D(100, -100, -1000), new Vector(0, 0, 1)),
                new SpotLight(new Color(400, 700, 600), //
                         1, 7E-5, 5E-7,new Point3D(60, -50, -20000), new Vector(0, 0, 1)),
                new PointLight(new Color(java.awt.Color.yellow),  1, 4E-5, 2E-7,new Point3D(290, -290, 1000)));


        ImageWriter imageWriter = new ImageWriter("Beach waves with algorithm hierarchy", 400, 400, 1000, 1000);
        Render render = new Render(imageWriter, scene,0,0).setDebugPrint().setMultithreading(3);


        scene.getGeometries().buildHierarchyTree();
        System.out.println(java.time.LocalTime.now());
        render.renderImage();
        System.out.println(java.time.LocalTime.now());
        render.writeToImage();
    }
}
