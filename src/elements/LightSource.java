package elements;

import primitives.*;
/**
 * presenting a light source element
 * @author Ofir Shmueli, Yehuda Kahan
 */

public interface LightSource {

        /**
         * Getter the intensity of the color in a specific point on the geometry
         * (according the kind of the light source)
         * @param p The point on the geometry
         * @return The intensity of the color
         */
        Color getIntensity(Point3D p);

        /**
         * Getter the vector L which represent the direction from the
         * light source to the given point
         * @param p
         * @return Direction vector
         */
        Vector getL(Point3D p);

}
