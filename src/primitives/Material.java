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
     // attenuation of the transparency
     private double _kT;
     // attenuation of the reflection
     private double _kR;

    /**
     * Constructor
     * @param kD attenuation of the Diffuse
     * @param kS attenuation of the Specular
     * @param nShininess The Shininess of the geometry
     */
     public Material(double kD,double kS, int nShininess){

         this(kD,kS,nShininess,0,0);
     }

    /**
     * Constructor
     * @param kD attenuation of the Diffuse
     * @param kS attenuation of the Specular
     * @param nShininess The Shininess of the geometry
     * @param kT attenuation of the transparency
     * @param kR attenuation of the reflection
     */
     public Material(double kD,double kS, int nShininess, double kT, double kR){

         _kD = kD;
         _kS = kS;
         _nShininess = nShininess;
         _kT = kT;
         _kR = kR;
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

    /**
     * Getter
     * @return _kT
     */
    public double getKT() {
        return _kT;
    }

    /**
     * Getter
     * @return _kR
     */
    public double getKR() {
        return _kR;
    }
}
