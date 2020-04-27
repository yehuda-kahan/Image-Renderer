package elements;

import geometries.*;
import org.junit.Test;
import static org.junit.Assert.*;
import primitives.*;

public class CameraIntegrationTest {

    @Test
    void cameraIntersectSphere(){

        Sphere sphere1=new Sphere(new Point3D(0,0,3),1);
        Camera camera1=new Camera(new Point3D(0,0,0), new Vector(0,1,0), new Vector(0,0,1));

        int result = 0;
        for (int i =0; i < 3; ++i)
            for (int j = 0; j <3; ++j){
                result+= sphere1.findIntersections(camera1.constructRayThroughPixel(3,3,j,i,1,3,3)).size();
            }
        assertEquals("",2,result);

    }

}
