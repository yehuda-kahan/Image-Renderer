package unittests.primitives;

import org.junit.Test;
import java.util.*;
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
     * Test method for {@link Vector#Vector}
     */
    @Test
    public void testConstructor(){

    }

    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}
     */
    @Test
    public void subtract() {
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    public void add() {
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    public void scale() {
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

        assertEquals("ERROR: length() wrong value",0,v1.length() - 5,0.00001);

    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    public void normalize() {

    }

    /**
     * Test method for {@link Vector#normalized()}
     */
    @Test
    public void normalized() {
    }
}