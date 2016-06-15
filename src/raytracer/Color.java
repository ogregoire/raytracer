package raytracer;

public class Color {

    public static final Color WHITE = new Color(1.0, 1.0, 1.0);
    public static final Color BLACK = new Color(0.0, 0.0, 0.0);

    public final double r;
    public final double g;
    public final double b;

    public Color(int rgb) {
        this.r = clampRGB(rgb, 0x10);
        this.g = clampRGB(rgb, 0x08);
        this.b = clampRGB(rgb, 0x00);
    }

    public Color(double r, double g, double b) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
    }

    private static double clamp(double v) {
        return v < 0.0 ? 0.0 : v > 1.0 ? 1.0 : v;
    }

    private static double clampRGB(int rgb, int shift) {
        return (rgb >> shift & 0xFF) / 255.0;
    }
}
