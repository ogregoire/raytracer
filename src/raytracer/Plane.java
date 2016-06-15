package raytracer;

public class Plane extends Geometry {

    private final Vector normal;
    private final double distance;

    public Plane(double nx, double ny, double nz, double distance) {
        this.normal = new Vector(nx, ny, nz).normalize();
        this.distance = distance;
    }

    @Override
    public boolean intersect(Ray inRay, Ray outRay) {
        double vd = normal.dot(inRay.direction);
        if (vd != 0.0) {
            double t = (distance - normal.dot(inRay.origin)) / vd;
            if (t > 0.0) {
                outRay.origin.set(inRay.direction).scale(t).add(inRay.origin);
                outRay.direction.set(normal);
                return true;
            }
        }
        return false;
    }
}
