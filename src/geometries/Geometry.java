package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Abstract class of a geometry which includes function to get the normal
 * @author Ofir Shmueli, Yehuda Kahan
 */
public abstract class Geometry implements Intersectable {

    protected Color _emmission;

    abstract public Vector getNormal (Point3D point);

    /**
     *  Default constructor. Initializing the _emmission field to black
     */
    protected Geometry(){
        _emmission = Color.BLACK;
    }

    /**
     * Constructor
     * @param emmission Set the _emmission field
     */
    protected Geometry(Color emmission){

        _emmission = new Color(emmission);
    }

    /**
     * Gertter
     * @return the emmission color of the current geometry
     */
    public Color getEmmission() {
        return _emmission;
    }
}
