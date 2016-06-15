package raytracer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tracer {

    public static final double TOLERANCE = 0.000001;

    private static final int MAX_RECURSION = 4;
    private static final double REFLECTIVITY = 0.10;
    private static final double ONE_OVER_256 = 1.0 / 256.0;

    private final ExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private final List<Callable<Object>> todo = new ArrayList<>();
    private final int[] pixels;
    private final int width;
    private final int height;

    public Tracer(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public void render(Camera camera, Scene scene) {
        int xStart = width >> 1;
        int yStart = height >> 1;

        todo.clear();
        for (int y = 0; y < height; y++) {
            int finalY = y;
            todo.add(Executors.callable(() -> {
                int offset = finalY * width;
                Ray ray = new Ray();
                camera.transform(ray.origin.set(0.0, 0.0, 1.0)).scale(-camera.distance);
                for (int wx = -xStart; wx < xStart; wx++) {
                    camera.transform(ray.direction.set(wx, yStart - finalY, camera.fov)).normalize();
                    pixels[offset++] = traceRay(scene, ray, 0);
                }
            }));
        }

        try {
            executor.invokeAll(todo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int traceRay(Scene scene, Ray ray, int level) {
        Ray hitRay = new Ray();
        Geometry intersected = scene.intersect(ray, hitRay);

        if (intersected != null) {
            double kdColorr = 0.0;
            double kdColorg = 0.0;
            double kdColorb = 0.0;
            double krColorr = 0.0;
            double krColorg = 0.0;
            double krColorb = 0.0;
            double ksColorr = 0.0;
            double ksColorg = 0.0;
            double ksColorb = 0.0;

            Material material = intersected.getMaterial();

            for (Light light : scene.lights) {
                Vector toLight = new Vector(light.position).sub(hitRay.origin);
                boolean shadowed = scene.isShadowed(toLight, hitRay.origin);

                if (!shadowed) {
                    toLight.normalize();
                    double intensity = toLight.dot(hitRay.direction);

                    if (intensity > 0.0) {
                        intensity *= 1.0 - REFLECTIVITY;

                        Color color = material.computeDiffuse(hitRay);
                        kdColorr += intensity * color.r * light.color.r;
                        kdColorg += intensity * color.g * light.color.g;
                        kdColorb += intensity * color.b * light.color.b;

                        double specular = new Vector(ray.origin).sub(hitRay.origin)
                                                                .normalize()
                                                                .add(toLight)
                                                                .normalize()
                                                                .dot(hitRay.direction);
                        if (specular > 0.0) {
                            color = material.computeSpecular(hitRay);
                            specular = Math.pow(Math.min(specular, 1.0), material.computeGlossiness(hitRay));
                            ksColorr += specular * color.r * light.color.r;
                            ksColorg += specular * color.g * light.color.g;
                            ksColorb += specular * color.b * light.color.b;
                        }
                    }
                }
            }

            if (++level <= MAX_RECURSION) {
                Ray result = new Ray();
                result.direction.set(hitRay.direction).scale(-2.0 * ray.direction.dot(hitRay.direction)).add(ray.direction);
                result.origin.set(result.direction).scale(TOLERANCE).add(hitRay.origin);
                int color = traceRay(scene, result, level);
                krColorr += REFLECTIVITY * ONE_OVER_256 * (0xFF & color >> 16);
                krColorg += REFLECTIVITY * ONE_OVER_256 * (0xFF & color >> 8);
                krColorb += REFLECTIVITY * ONE_OVER_256 * (0xFF & color);
            }

            return shade(scene.ambient.r * material.ambientColor.r + kdColorr + ksColorr + krColorr, 0x10) |
                   shade(scene.ambient.g * material.ambientColor.g + kdColorg + ksColorg + krColorg, 0x08) |
                   shade(scene.ambient.b * material.ambientColor.b + kdColorb + ksColorb + krColorb, 0x00);
        }
        return 0x000000;
    }

    private static int shade(double v, int shift) {
        return (int)(Math.max(0.0, Math.min(v, 1.0)) * 255.0) << shift;
    }
}

