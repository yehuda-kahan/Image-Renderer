package primitives;

import org.junit.Test;
import java.util.*;

import primitives.Point3D;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class VectorTests {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}
     */
    @Test
    public void subtract() {

        // ============ Equivalence Partitions Tests ==============

        // Test ordinary subtract from two vectors
        assertEquals("ERROR: subtracting v3 from v2 is not equal to the returned value",new Vector(2,7,4), v2.subtract(v3));
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    public void add() {

        // ============ Equivalence Partitions Tests ==============

        // Test ordinary addition vector with vector
        assertEquals("ERROR: adding v3 to v2 is not equal to the returned value",new Vector(-2,-1,-8), v2.add(v3));
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    public void scale() {

        int alfa = 2,beta = -1 ,zero = 0;

        // ============ Equivalence Partitions Tests ==============

        // Test scale with positive number
        assertEquals("ERROR: multiplying vector v1 with scalar alfa is different from the returned value",new Vector(2,4,6), v1.scale(alfa));
        // Test scale with negative number
        assertEquals(new Vector(-1,-2,-3), v1.scale(beta));

        // =============== Boundary Values Tests ==================

        // Test scale with zero number
        try {
            assertEquals(new Vector(0,0,0), v1.scale(zero));
            fail("ERROR: should throw an exception");
        }
        catch (IllegalArgumentException ex){}
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    public void dotProduct() {

        // ============ Equivalence Partitions Tests ==============

        // Test the length of dot-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("ERROR: dotProduct() for orthogonal vectors is not zero", 0, v1.dotProduct(v3),0.00001);
        assertEquals("ERROR: dotProduct() wrong value", 0, v1.dotProduct(v3), 0.00001);
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    public void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    public void lengthSquared() {

        // ============ Equivalence Partitions Tests ==============

        // Test that length squard of v1 is proper
        assertEquals("ERROR: lengthSquared() wrong value",0,v1.lengthSquared() - 14,0.00001);
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    public void length() {
        Vector v1 = new Vector(0,3,4);

        // ============ Equivalence Partitions Tests ==============

        //  Test that length of v1 is proper
        assertEquals("ERROR: length() wrong value",0,v1.length() - 5,0.00001);

    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    public void normalize() {

        // ============ Equivalence Partitions Tests ==============

        // Test ordinary normalization
        Vector v1 = new Vector(3.5, -5, 10);
        v1.normalize();
        assertEquals(1, v1.length(), 1e-10);

        try {
            Vector v2 = new Vector(0, 0, 0);
            v2.normalize();
            fail("ERROR: Didn't throw divide by zero exception!");
        } catch (IllegalArgumentException ex) {
            assertEquals("ERROR: The vector cannot be the Zero vector", ex.getMessage());
        }
        assertTrue(true);

    }

    /**
     * Test method for {@link Vector#normalized()}
     */
    @Test
    public void normalized() {

        // ============ Equivalence Partitions Tests ==============

        Vector v1 = new Vector(2,3,-5);
        Vector v2 = v1.normalized();
        assertEquals("ERROR: normalize of vector v2 is different from the returned value",1, v2.length(), 1e-10);
    }
}