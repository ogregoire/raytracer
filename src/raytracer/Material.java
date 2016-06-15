package raytracer;

public class Material {

    public final Color ambientColor;
    public final Color diffuseColor;
    public final Color specularColor;
    public final double glossiness;

    public Material(Color ambientColor, Color diffuseColor, Color specularColor) {
        this(ambientColor, diffuseColor, specularColor, 24.0);
    }

    public Material(Color ambientColor, Color diffuseColor, Color specularColor, double glossiness) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.glossiness = glossiness;
    }

    public Color computeDiffuse(Ray hitRay) {
        return diffuseColor;
    }

    public Color computeSpecular(Ray hitRay) {
        return specularColor;
    }

    public double computeGlossiness(Ray hitRay) {
        return glossiness;
    }
}
