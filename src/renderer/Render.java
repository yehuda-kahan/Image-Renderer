package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import primitives.Color;
import scene.*;
import geometries.Intersectable.GeoPoint;

import java.awt.*;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 *
 * @author Dan
 */
public class Render {

    // Number of recursive calls
    private static final int MAX_CALC_COLOR_LEVEL = 4;
    // The constant k that decide who match the transparency and the reflection are will affect
    private static final double MIN_CALC_COLOR_K = 0.001;
    // The distance of the radius circle from the head's ray
    private static final double DISTANCE = 80;

    private ImageWriter _imageWriter;
    private Scene _scene;
    //The number of additional rays for the beam that emerge when there is reflection and transparency
    //(not including the original ray)
    private int _NumOfRays;
    //The number of degrees from reflection's or transparency's main ray that the new rays will create
    private double _Degrees;
    // acceleration of BVH
    private boolean _acceleration = false;


    // used for threads
    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;


    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) {
                    System.out.println("");
                    System.out.printf("\r %02d%%", percents);
                }
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }

    /**
     * Constructor
     *
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {

        _imageWriter = imageWriter;
        _scene = scene;
        _Degrees = 0;
        _NumOfRays = 0;
    }

    /**
     * Constructor
     *
     * @param imageWriter handle with creating the image file
     * @param scene       the pictured sence
     * @param NumOfRays   additional attribute if we want to create Glossy Surfaces and Diffused Glass effects
     * @param Degrees     of the area of the ray's beam
     */
    public Render(ImageWriter imageWriter, Scene scene, int NumOfRays, double Degrees) {
        if (NumOfRays < 0 || Degrees < 0)
            throw new IllegalArgumentException("The num of rays and the num of degrees must be bigger/equal to zero");
        if (NumOfRays == 0 && Degrees > 0)
            throw new IllegalArgumentException("When the number of rays equals zero, the degree must be zero as well");
        _NumOfRays = NumOfRays;
        _Degrees = Degrees;
        _imageWriter = imageWriter;
        _scene = scene;
    }

    /**
     * Constructor
     *
     * @param imageWriter  handle with creating the image file
     * @param scene        the pictured sence
     * @param NumOfRays    additional attribute if we want to create Glossy Surfaces and Diffused Glass effects
     * @param Degrees      of the area of the ray's beam
     * @param acceleration of the render with BVH
     */
    public Render(ImageWriter imageWriter, Scene scene, int NumOfRays, double Degrees, boolean acceleration) {
        this(imageWriter, scene, NumOfRays, Degrees);
        _acceleration = acceleration;
    }

    public void setAcceleration(boolean acceleration) {
        _acceleration = acceleration;
    }


    /**
     * Takes the scene and the image writer and create the photo with them
     *//*
    public void renderImage(){

        Camera camera = _scene.getCamera();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double distance = _scene.getDistance();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        for (int i = 0; i < nY; ++i) {
            System.out.println(i);
            for (int j = 0; j < nX; ++j) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);

                GeoPoint closestPoint = findClosestIntersection(ray);
                _imageWriter.writePixel(j, i, closestPoint == null ? background
                        : calcColor(closestPoint, ray).getColor());
            }
        }
    }*/

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();
        java.awt.Color background = _scene.getBackground().getColor();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, //
                            dist, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);
                    _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background
                            : calcColor(closestPoint, ray).getColor());
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * The main calcColor function that calc the ambient light
     * and calls to the recursive calcColor func
     *
     * @param point The pixel (point)
     * @param ray   The direction of the looking
     *              (from the camera or after that from the reflection and the refraction)
     * @return Color of the given pixel
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }

    /**
     * calculate the closest point from the given points, from our camera
     *
     * @param points
     * @return The closest point between all the given points
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points) {
        double distance = Double.MAX_VALUE;
        Point3D P0 = _scene.getCamera().getP0();
        GeoPoint minDistancePoint = null;

        for (GeoPoint point : points)
            if (P0.distance(point._point) < distance) {
                minDistancePoint = new GeoPoint(point._geometry, point._point);
                distance = P0.distance(point._point);
            }

        return minDistancePoint;
    }

    /**
     * Calculate the color of the pixel in the given point
     *
     * @param point The given point (pixel)
     * @param inRay The direction from which we are looking
     * @param level The amount of numbers we do the recursion
     * @param k     The level at which the darkening of the reflection and the refraction
     * @return
     */
    private Color calcColor(GeoPoint point, Ray inRay, int level, double k) {

        Color color = point._geometry.getEmmission();

        Vector v = _scene.getCamera().getP0().subtract(point._point).normalize();
        Vector n = point._geometry.getNormal(point._point);
        Material material = point._geometry.getMaterial();
        int nShininess = material.getNShininess();
        double kd = material.getKD();
        double ks = material.getKS();

        double nv = n.dotProduct(v);

        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(point._point);
            double nl = n.dotProduct(l);
            if ((nv >= 0 && nl >= 0) || (nv <= 0 && nl <= 0)) {
                double ktr = transparency(lightSource, l, n, point);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(point._point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        if (level == 1) return Color.BLACK;
        double kr = point._geometry.getMaterial().getKR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, point._point, inRay);
            color = calcBeamColor(color, n, reflectedRay, level, kr, kkr);
        }
        double kt = point._geometry.getMaterial().getKT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, point._point, inRay);
            color = calcBeamColor(color, n, refractedRay, level, kt, kkt);
        }
        return color;
    }

    /**
     * this function calculate the color of point with the help of beam
     *
     * @param color  the color of the intersection point
     * @param n      The normal vector of the point where beam start
     * @param refRay reflected/refracted ray
     * @param level  The level of recursiun
     * @param k      kt/kr
     * @param kk     kkt/kkr
     * @return The color
     */
    private Color calcBeamColor(Color color, Vector n, Ray refRay, int level, double k, double kk) {
        Color addColor = Color.BLACK;
        List<Ray> rays = refRay.raySplitter(n, _NumOfRays, _Degrees, DISTANCE);
        for (Ray ray : rays) {
            GeoPoint refPoint = findClosestIntersection(ray);
            if (refPoint != null) {
                addColor = addColor.add(calcColor(refPoint, ray, level - 1, kk).scale(k));
            }
        }
        int size = rays.size();
        color = color.add(addColor.reduce(size));
        return color;
    }

    /**
     * calculate the diffuse of the light
     * and according that we draw the color in the specific point
     *
     * @param kd             attenuation of the Diffuse
     * @param l              the direction from the light source to the point
     * @param n              the normal to the geometry
     * @param lightIntensity on the point (according the kind of the light source)
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {

        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * calculate the specular of the light
     * and according that we draw the color in the specific point
     *
     * @param ks             attenuation of the Specular
     * @param l              the direction from the light source to the point
     * @param n              the normal to the geometry
     * @param v              the direction of the camera
     * @param nShininess     the shininess level of the geometry
     * @param lightIntensity on the point (according the kind of the light source)
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = null;
        try {
            r = n.scale(2 * l.dotProduct(n)).subtract(l);
        } catch (IllegalArgumentException ex) {
            return Color.BLACK;
        }
        Vector minusV = v.scale(-1);
        return lightIntensity.scale(ks * Math.pow(r.dotProduct(minusV), nShininess));
    }

    /**
     * calculate the shadow on a point from each of the source lights
     *
     * @param light    The light source that we want to check if he impact
     *                 on our point
     * @param l        The direction from the light source to the point
     * @param n        The normal from the current point from current geometry
     * @param geopoint The current point
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint._point, lightDirection, n);
        List<GeoPoint> intersections = null;
        if (_acceleration)
            intersections = _scene.getGeometries().findIntersectionsBVH(lightRay);
        else
            intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return 1.0;
        double lightDistance = light.getDistance(geopoint._point);
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0) {
                ktr *= gp._geometry.getMaterial().getKT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }


    /**
     * Calc the reflation ray from the camera (inRay direction)
     *
     * @param n     Normal of the geometry in the given point
     * @param point The point on the geometry
     * @param inRay The direction from where we are looking (camera)
     * @return
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
        Vector r = n.scale(2 * inRay.getDirection().dotProduct(n)).subtract(inRay.getDirection());
        return new Ray(point, r, n);
    }

    /**
     * Calc the refraction ray from the camera (inRay direction)
     *
     * @param n     Normal of the geometry in the given point
     * @param point The point on the geometry
     * @param inRay The direction from where we are looking (camera)
     * @return
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
        return new Ray(point, inRay.getDirection(), n);
    }

    /**
     * Find the closest intersection point from the head
     * of the ray
     *
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        Geometries geometries = _scene.getGeometries();
        List<GeoPoint> intersectionPoints = null;
        if (_acceleration)
            intersectionPoints = geometries.findIntersectionsBVH(ray);
        else
            intersectionPoints = geometries.findIntersections(ray);

        if (intersectionPoints == null) return null;

        double distance = Double.MAX_VALUE;
        Point3D P0 = ray.getP00();
        GeoPoint minDistancePoint = null;

        for (GeoPoint point : intersectionPoints)
            if (P0.distance(point._point) < distance) {
                minDistancePoint = new GeoPoint(point._geometry, point._point);
                distance = P0.distance(point._point);
            }

        return minDistancePoint;
    }

    /**
     * Print a grid on the picture with size of Squares according
     * the interval
     *
     * @param interval
     * @param color
     */
    public void printGrid(int interval, java.awt.Color color) {

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j) {
                if ((i % interval == 0 || j % interval == 0) && i != 0 && j != 0)
                    _imageWriter.writePixel(j, i, color);
            }
    }

    /**
     * Make the image file (after the renderImage)
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }
}
