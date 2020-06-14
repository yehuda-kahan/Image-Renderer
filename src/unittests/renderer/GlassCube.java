package renderer;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;


public class GlassCube {

    @Test
    public void GlassCube(){

        Scene scene = new Scene("Cube scene");
        scene.setCamera(new Camera(new Point3D(0, 475, -1800), new Vector(0, -1, 4), new Vector(0,4,1)));
        //scene.setCamera(new Camera(new Point3D(0, 1070, -10000/7d), new Vector(0, -1, 10/7d), new Vector(0,10/7d,1)));
        //scene.setCamera(new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // AEFD
                        , new Point3D(0,0,0), new Point3D(0,70,0),new Point3D(-50,70,50), new Point3D(-50,0,50))
               ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // TOP
                        , new Point3D(0,70,0), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(50,70,50))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // DFHB
                       , new Point3D(-50,0,50), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(0,0,100))
               ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // BHGC
                       , new Point3D(0,0,100), new Point3D(0,70,100), new Point3D(50,70,50),new Point3D(50,0,50))
               ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // CGEA
                       , new Point3D(50,0,50), new Point3D(50,70,50), new Point3D(0,70,0),new Point3D(0,0,0))
               ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // BOTTOM
                      , new Point3D(0,0,0), new Point3D(-50,0,50), new Point3D(0,0,100),new Point3D(50,0,50))

                ,new Sphere(new Point3D(0, 35, 50),25,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100))

               ,new Plane(new Point3D(0,-8,-2),new Vector(0,4,1),new Color(java.awt.Color.DARK_GRAY),new Material(0.25,0.3,50,0,0.8))
                //,new Plane(new Point3D(0,-20/7d,-2),new Vector(0,10/7d,1),new Color(java.awt.Color.DARK_GRAY),new Material(0,0,0,0,0.8))
               // ,new Plane(new Point3D(0,0,150),new Vector(0,-0.2,-1),new Color(java.awt.Color.DARK_GRAY),new Material(0.2,0.1,100,0,0.8))

                ,new Sphere(new Point3D(-100,23,0),30,new Color(220,20,60),new Material(0.3,0.4,70,0,0.3))

        );

        scene.addLights(new SpotLight(new Color(1000, 600, 1000), 1,
                0.0001, 0.00005, new Point3D(-100, 100, 100), new Vector(1, -0.4, -1))

                ,new DirectionalLight(new Color(255,215,0),new Vector(-1,-0.4,1))
        );


        ImageWriter imageWriter = new ImageWriter("GlassCube", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void GlassCubeAnotherAngle(){

        Scene scene = new Scene("Cube scene");
        scene.setCamera(new Camera(new Point3D(-10350/7d, 1070, 0), new Vector(10/7d, -1, 0), new Vector(1,10/7d,0)));
        //scene.setCamera(new Camera(new Point3D(0, 1070, -10000/7d), new Vector(0, -1, 10/7d), new Vector(0,10/7d,1)));
        //scene.setCamera(new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // AEFD
                        , new Point3D(0,0,0), new Point3D(0,70,0),new Point3D(-50,70,50), new Point3D(-50,0,50))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // TOP
                        , new Point3D(0,70,0), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(50,70,50))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // DFHB
                        , new Point3D(-50,0,50), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(0,0,100))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // BHGC
                        , new Point3D(0,0,100), new Point3D(0,70,100), new Point3D(50,70,50),new Point3D(50,0,50))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // CGEA
                        , new Point3D(50,0,50), new Point3D(50,70,50), new Point3D(0,70,0),new Point3D(0,0,0))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // BOTTOM
                        , new Point3D(0,0,0), new Point3D(-50,0,50), new Point3D(0,0,100),new Point3D(50,0,50))

                ,new Sphere(new Point3D(0, 35, 50),25,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100))

                ,new Plane(new Point3D(0,-8,-2),new Vector(0,4,1),new Color(java.awt.Color.DARK_GRAY),new Material(0.25,0.3,50,0,0.8))
                //,new Plane(new Point3D(0,-20/7d,-2),new Vector(0,10/7d,1),new Color(java.awt.Color.DARK_GRAY),new Material(0,0,0,0,0.8))
                // ,new Plane(new Point3D(0,0,150),new Vector(0,-0.2,-1),new Color(java.awt.Color.DARK_GRAY),new Material(0.2,0.1,100,0,0.8))

                ,new Sphere(new Point3D(-100,23,0),30,new Color(220,20,60),new Material(0.3,0.4,70,0,0.3))

        );

        scene.addLights(new SpotLight(new Color(1000, 600, 1000), 1,
                        0.0001, 0.00005, new Point3D(-100, 100, 100), new Vector(1, -0.4, -1))
                ,new DirectionalLight(new Color(255,215,0),new Vector(-1,-0.4,1))
        );


        ImageWriter imageWriter = new ImageWriter("GlassCubeAnotherAngle", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void GlassCubeWithUpgrade(){

        Scene scene = new Scene("Cube scene");
        scene.setCamera(new Camera(new Point3D(0, 475, -1800), new Vector(0, -1, 4), new Vector(0,4,1)));
        //scene.setCamera(new Camera(new Point3D(0, 1070, -10000/7d), new Vector(0, -1, 10/7d), new Vector(0,10/7d,1)));
        //scene.setCamera(new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // AEFD
                        , new Point3D(0,0,0), new Point3D(0,70,0),new Point3D(-50,70,50), new Point3D(-50,0,50))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // TOP
                        , new Point3D(0,70,0), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(50,70,50))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // DFHB
                        , new Point3D(-50,0,50), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(0,0,100))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // BHGC
                        , new Point3D(0,0,100), new Point3D(0,70,100), new Point3D(50,70,50),new Point3D(50,0,50))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // CGEA
                        , new Point3D(50,0,50), new Point3D(50,70,50), new Point3D(0,70,0),new Point3D(0,0,0))
                ,new Polygon(new Color(new Color(105,105,105)),new Material(0.2, 0.2, 30, 0.6, 0) // BOTTOM
                        , new Point3D(0,0,0), new Point3D(-50,0,50), new Point3D(0,0,100),new Point3D(50,0,50))

                ,new Sphere(new Point3D(0, 35, 50),25,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100))

                ,new Plane(new Point3D(0,-8,-2),new Vector(0,4,1),new Color(java.awt.Color.DARK_GRAY),new Material(0.25,0.3,50,0,0.8))
                //,new Plane(new Point3D(0,-20/7d,-2),new Vector(0,10/7d,1),new Color(java.awt.Color.DARK_GRAY),new Material(0,0,0,0,0.8))
                // ,new Plane(new Point3D(0,0,150),new Vector(0,-0.2,-1),new Color(java.awt.Color.DARK_GRAY),new Material(0.2,0.1,100,0,0.8))

                ,new Sphere(new Point3D(-100,23,0),30,new Color(220,20,60),new Material(0.3,0.4,70,0,0.3))

        );

        scene.addLights(new SpotLight(new Color(1000, 600, 1000), 1,
                        0.0001, 0.00005, new Point3D(-100, 100, 100), new Vector(1, -0.4, -1))

                ,new DirectionalLight(new Color(255,215,0),new Vector(-1,-0.4,1))
        );


        ImageWriter imageWriter = new ImageWriter("Glass Cube with upgrade", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene,10, 5);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void TestingTest(){
        Scene scene = new Scene("Testing Test");
        //scene.setCamera(new Camera(new Point3D(0, 475, -1800), new Vector(0, -1, 4), new Vector(0,4,1)));
        //scene.setCamera(new Camera(new Point3D(0, 1070, -10000/7d), new Vector(0, -1, 10/7d), new Vector(0,10/7d,1)));
        scene.setCamera(new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Point3D(0, 0, 30),30,new Color(java.awt.Color.BLUE)
                        , new Material(0.4, 0.3, 100, 0.3, 0)),
                new Sphere(new Point3D(0, 0, 30),15,new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100)),
                new Plane(new Point3D(0,15,30),new Vector(0,-1,0),new Color(java.awt.Color.DARK_GRAY),new Material(0.25,0.3,50,0,0.8))
                );

        scene.addLights(new SpotLight(new Color(1000, 600, 0), 1,
                0.0004, 0.0000006, new Point3D(-100, 100, -500), new Vector(-1, 1, 2)));

        ImageWriter imageWriter = new ImageWriter("testingTest", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();


    }
}
