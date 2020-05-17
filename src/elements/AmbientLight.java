package elements;

import primitives.Color;

/**
 * Represents the color of ambient lighting
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class AmbientLight extends Light {


    /**
     * Constructor : initialize the _intensity with the color multiply by the kA
     * @param iA The Colors of the ambient light
     * @param kA The intensity of light
     */
    public AmbientLight(Color iA, double kA) {
        super(iA, kA);

    }

}
