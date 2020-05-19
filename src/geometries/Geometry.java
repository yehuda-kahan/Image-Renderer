package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Abstract class of a geometry which includes function to get the normal
 * @author Ofir Shmueli, Yehuda Kahan
 */
public abstract class Geometry implements Intersectable {

    protected Color _emmission;
    protected Material _material;

    abstract public Vector getNormal (Point3D point);

    /**
     * Constructor
     * @param emmission color of the geometry
     * @param material which the geometry made of
     */
    protected Geometry(Color emmission , Material material){

        _emmission = new Color(emmission);
        _material = new Material(material.getKD(), material.getKS(), material.getNShininess());
    }


    /**
     *  Default constructor. Initializing the _emmission field to black
     *  and the _material filed to 0,0,0
     */
    protected Geometry(){
        this(Color.BLACK , new Material(0,0,0));
    }

    /**
     * Constructor
     * @param emmission Set the _emmission field
     * and initialize the material field to 0,0,0
     */
    protected Geometry(Color emmission){

        this(emmission , new Material(0,0,0));
    }

    /**
     * Gertter
     * @return the emmission color of the current geometry
     */
    public Color getEmmission() {
        return _emmission;
    }

    /**
     * Getter
     * @return _material
     */
    public Material getMaterial() {
        return _material;
    }
}
