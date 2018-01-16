package raytracer;
public class Ray {

	public Vector dir;
	public Vector origin;

	public Ray(Vector direction, Vector origin) {
		dir = Utils.norm(direction);
		this.origin = origin;
	}
}
