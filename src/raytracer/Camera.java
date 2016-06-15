package raytracer;

public class Camera {

    private Matrix transform1;
    private Matrix transform2;
    public final double distance;
    public final double fov;

    public Camera(double distance, double fov) {
        this.transform1 = new Matrix();
        this.transform2 = new Matrix();
        this.distance = distance;
        this.fov = fov;
    }

    public Vector transform(Vector v) {
        return v.set(v.x * transform1.e00 + v.y * transform1.e10 + v.z * transform1.e20,
                     v.x * transform1.e01 + v.y * transform1.e11 + v.z * transform1.e21,
                     v.x * transform1.e02 + v.y * transform1.e12 + v.z * transform1.e22);
    }

    public void rotate(double aboutX, double aboutY) {
        transform1.rotationAboutX(aboutX);
        transform2.rotationAboutY(aboutY);
        transform1.concatenate(transform1, transform2);
    }
}
