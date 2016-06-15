package raytracer;

public abstract class Geometry {

    private Material material;

    protected Geometry() {
        setMaterial(new Material(Color.WHITE, Color.WHITE, Color.WHITE));
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material value) {
        material = value;
    }

    public abstract boolean intersect(Ray inRay, Ray outRay);
}
