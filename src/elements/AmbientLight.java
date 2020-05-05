package elements;

import primitives.Color;

/**
 * Represents the color of ambient lighting
 */
public class AmbientLight {

    private Color _intensity;

    /**
     * Constructor : initialize the _intensity with the color multiply by the kA
     * @param iA The Colors of the ambient light
     * @param kA The intensity of light
     */
    public AmbientLight(Color iA, double kA){
        _intensity = iA.scale(kA);
    }

    /**
     * Getter of the ambient light field
     * @return _intensity
     */
    public Color GetIntensity(){return _intensity;}
}
