package raytracer;

public class BoundingSphere {

	public Vector centre;
	public double radius;

	public BoundingSphere(Vector centre, double radius) {
		this.centre = centre;
		this.radius = radius;
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		bld.append("Centre: ").append(centre).append(" ")
		.append("radius: ").append(radius);
		return bld.toString();
	}
}
