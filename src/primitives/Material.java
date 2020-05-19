package primitives;

/**
 * Represents the material from which the geometry is made
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Material {

     //attenuation of the Diffuse
     private double _kD;
     // attenuation of the Specular
     private double _kS;
     // The Shininess of the geometry
     private int _nShininess;

    /**
     * Constructor
     * @param kD attenuation of the Diffuse
     * @param kS attenuation of the Specular
     * @param nShininess The Shininess of the geometry
     */
     public Material(double kD,double kS, int nShininess){

         _kD = kD;
         _kS = kS;
         _nShininess = nShininess;
     }

    /**
     * Getter
     * @return _kD
     */
    public double getKD() {
        return _kD;
    }

    /**
     * Getter
     * @return _kS
     */
    public double getKS() {
        return _kS;
    }

    /**
     * Getter
     * @return _nShininess
     */
    public int getNShininess() {
        return _nShininess;
    }
}
