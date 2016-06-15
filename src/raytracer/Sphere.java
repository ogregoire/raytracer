package raytracer;

public class Sphere extends Geometry {

    private final Vector position;
    private final double radius;

    public Sphere(double x, double y, double z, double radius) {
        this.position = new Vector(x, y, z);
        this.radius = radius;
    }

    @Override
    public boolean intersect(Ray inRay, Ray outRay) {
        outRay.origin.set(inRay.origin).sub(position);
        double b = 2.0 * inRay.direction.dot(outRay.origin);
        double discriminant = b * b - 4.0 * (outRay.origin.lengthSquared() - radius * radius);
        if (discriminant > 0.0) {
            double d = Math.sqrt(discriminant);
            double t = Math.min(-b - d, -b + d) * 0.5;
            if (t > 0.0) {
                outRay.origin.set(inRay.direction).scale(t).add(inRay.origin);
                outRay.direction.set(outRay.origin).sub(position).scale(1.0 / radius);
                return true;
            }
        }
        return false;
    }
}
