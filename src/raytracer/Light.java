package raytracer;

public class Light {

    public final Vector position;
    public final Color color;

    public Light(double x, double y, double z, Color color) {
        this.position = new Vector(x, y, z);
        this.color = color;
    }
}
