package raytracer;

public class Vector {

    public double x;
    public double y;
    public double z;

    public Vector() {
        set(0.0, 0.0, 0.0);
    }

    public Vector(double x, double y, double z) {
        set(x, y, z);
    }

    public Vector(Vector other) {
        set(other);
    }

    public double dot(Vector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double lengthSquared() {
        return dot(this);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector set(Vector other) {
        return set(other.x, other.y, other.z);
    }

    public Vector scale(double s) {
        return set(x * s, y * s, z * s);
    }

    public Vector sub(Vector other) {
        return set(x - other.x, y - other.y, z - other.z);
    }

    public Vector add(Vector other) {
        return set(x + other.x, y + other.y, z + other.z);
    }

    public Vector normalize() {
        return scale(1.0 / length());
    }
}
