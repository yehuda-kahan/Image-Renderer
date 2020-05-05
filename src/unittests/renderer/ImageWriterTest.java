package renderer;

import org.junit.Test;
import primitives.*;
import primitives.Color;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * check first use with ImageWriter class
 */
public class ImageWriterTest {

    // Test to crate first image with ImageWriter obj
    @Test
    public void createImage(){

        Color backGround = new Color(100, 200,150);
        Color net = Color.BLACK;

        ImageWriter imageWriter = new ImageWriter("myFirstImage",1600,1000,800,500);

        for (int j = 0; j < 800; ++j){
            for (int i = 0; i < 500; ++i){
                if (j % 50 == 0 || i % 50 == 0)
                    imageWriter.writePixel(j,i,net.getColor());
                else
                  imageWriter.writePixel(j,i,backGround.getColor());
            }
        }

        imageWriter.writeToImage();
    }
}