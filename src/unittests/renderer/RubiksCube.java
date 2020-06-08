package renderer;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;


public class RubiksCube {

    @Test
    public void RubiksCube(){

        Scene scene = new Scene("Cube scene");
        scene.setCamera(new Camera(new Point3D(0, 1070, -10000/7d), new Vector(0, -1, 10/7d), new Vector(0,10/7d,1)));
       // scene.setCamera(new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Polygon(new Color(java.awt.Color.RED),new Material(0.2,0.4,50,0,0) // AEFD
                        , new Point3D(0,0,0), new Point3D(0,70,0),new Point3D(-50,70,50), new Point3D(-50,0,50))
               ,new Polygon(new Color(java.awt.Color.gray),new Material(0.2,0.4,50,0,0) // TOP
                        , new Point3D(0,70,0), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(50,70,50))
                ,new Polygon(new Color(java.awt.Color.BLUE),new Material(0.2,0.4,50,0,0) // DFHB
                       , new Point3D(-50,0,50), new Point3D(-50,70,50), new Point3D(0,70,100),new Point3D(0,0,100))
               ,new Polygon(new Color(java.awt.Color.YELLOW),new Material(0.2,0.4,50,0,0) // BHGC
                       , new Point3D(0,0,100), new Point3D(0,70,100), new Point3D(50,70,50),new Point3D(50,0,50))
               ,new Polygon(new Color(java.awt.Color.MAGENTA),new Material(0.2,0.4,50,0,0) // CGEA
                       , new Point3D(50,0,50), new Point3D(50,70,50), new Point3D(0,70,0),new Point3D(0,0,0))
               ,new Polygon(new Color(java.awt.Color.ORANGE),new Material(0.2,0.4,50,0,0) // BOTTOM
                      , new Point3D(0,0,0), new Point3D(-50,0,50), new Point3D(0,0,100),new Point3D(50,0,50))

                ,new Plane(new Point3D(0,0,0),new Vector(0,10,7),new Color(java.awt.Color.DARK_GRAY),new Material(0.2,0.1,100,0,0.8))

        );

        /*scene.addLights(
                new PointLight(new Color(0,900,790),1,0.00001, 0.000001,new Point3D(105,105,48))
                ,new DirectionalLight(new Color(java.awt.Color.WHITE),new Vector(-1,1,1))
        );*/


        ImageWriter imageWriter = new ImageWriter("RubiksCube", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
