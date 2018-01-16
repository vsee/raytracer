package raytracer;
public class Vector {

	private final double x;
	private final double y;
	private final double z;

	private Double length = null;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getLength() {
		if(length == null) length = Math.sqrt(x*x + y*y + z*z);
		return length;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }

	@Override
	public String toString() {
		return x + "x" + y + "x" + z;
	}
}
