package raytracer;

public class Ray {

    public final Vector origin;
    public final Vector direction;

    public Ray() {
        origin = new Vector();
        direction = new Vector();
    }

    public Ray set(Vector origin, Vector direction) {
        this.origin.set(origin);
        this.direction.set(direction);
        return this;
    }

    public Ray set(Ray other) {
        return set(other.origin, other.direction);
    }
}
