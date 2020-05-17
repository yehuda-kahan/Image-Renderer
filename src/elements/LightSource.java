package elements;

import primitives.*;
/**
 * presenting a light source element
 * @author Ofir Shmueli, Yehuda Kahan
 */

public interface LightSource {

        Color getIntensity(Point3D p);
        Vector getL(Point3D p);

}
