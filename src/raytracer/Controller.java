package raytracer;

public class Controller {

    private final Camera camera;
    private final Viewer view;
    private final Scene scene;
    private final Tracer tracer;
    private final int[] pixels;
    private double angle;

    public Controller(Viewer viewer, Scene model) {
        view = viewer;
        scene = model;
        pixels = new int[view.getWidth() * view.getHeight()];
        camera = new Camera(250.0, view.getHeight());
        tracer = new Tracer(pixels, view.getWidth(), view.getHeight());
    }

    public Viewer getView() {
        return view;
    }

    public void step() {
        camera.rotate(0.6, angle += 0.01);
        tracer.render(camera, scene);
        view.setRGB(pixels);
    }
}
