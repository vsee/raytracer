package raytracer;
public class Plane {

	private final Vector normal;
	private final double distOrigin;

	public Plane(Vector normal, double distOrigin) {
		this.normal = Utils.norm(normal);
		this.distOrigin = distOrigin;
	}

	public Vector getNormal() { return normal; }
	public double getDistOrigin() { return distOrigin; }
}
