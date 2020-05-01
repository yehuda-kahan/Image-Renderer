package geometries;

import primitives.*;
import primitives.Vector;

import java.awt.*;
import static primitives.Util.*;
import java.util.*;
import java.util.List;

/**
 * presenting a triangle
 * @author Ofir Shmueli, Yehuda Kahan
 */
public class Triangle extends Polygon {

    /**
     * constructor that gets 3 points and sets local fields
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1,Point3D p2, Point3D p3){
        super(new Point3D(p1), new Point3D(p2), new Point3D(p3));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        if (_plane.findIntersections(ray) == null) // Ray doesn't intersect with the triangle
            return null;

        List<Vector> vectors = new ArrayList<>(3);
        for (int i = 0; i < 3; ++i) {
            vectors.add(ray.get_POO().subtract(_vertices.get(i)));
        }

        List<Vector> normals = new ArrayList<>(3);
        for (int i = 0 ; i < 2; ++i) {
            normals.add(vectors.get(i).crossProduct(vectors.get(i+1)));
        }
        normals.add(vectors.get(2).crossProduct(vectors.get(0)));

        int plus = 0, minus = 0;
        for (int i = 0; i < 3; ++i){
           double t = alignZero(normals.get(i).dotProduct(ray.get_direction()));
           if (isZero(t))
               return null;
           if(t > 0)
               plus++;
           else
               minus++;
        }
        if (plus != 3 && minus != 3)
            return null;
        return _plane.findIntersections(ray);
    }
}
