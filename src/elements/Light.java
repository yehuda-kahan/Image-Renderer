package elements;

import primitives.Color;

/**
 * Presenting light element
 * @author Ofir Shmueli, Yehuda Kahan
 */
abstract class Light {

    protected Color _intensity;

    /**
     * Constructor that scales kA
     * @param intensity
     */
    Light(Color intensity, double kA){
        _intensity = intensity.scale(kA);
    }

    /**
     * getter
     * @return _intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
