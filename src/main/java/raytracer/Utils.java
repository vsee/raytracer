package raytracer;
public final class Utils {

	private static final double EPSILON = 0.001;

	private Utils() {}

	public static Vector norm(Vector v) {
		return new Vector(
				v.getX() / v.getLength(),
				v.getY() / v.getLength(),
				v.getZ() / v.getLength());
	}

	/**
	 * Returns the closest intersection distance of r and s
	 * or null if there is none.
	 */
	public static Double intersect(Ray r, BoundingSphere s) {
		Double res;

		double a = dot(r.dir, r.dir);
		double b = dot(scalar(r.dir, 2), subtract(r.origin, s.centre));
		double c = dot(subtract(r.origin, s.centre), subtract(r.origin, s.centre)) - Math.pow(s.radius, 2);

		double sqrtTerm = b*b - 4*a*c;

		if(sqrtTerm < 0) res = null;
		else if(sqrtTerm == 0) {
			res = (-1 * b) / (2*a);
			if(Math.abs(res) < EPSILON) res = null;
		} else {
			sqrtTerm = Math.sqrt(sqrtTerm);
			double i1 = (-1 * b + sqrtTerm) / (2*a);
			double i2 = (-1 * b - sqrtTerm) / (2*a);

			if(Math.abs(i1) < EPSILON) res = i2;
			else if(Math.abs(i2) < EPSILON) res = i1;
			else res = Math.min(i1, i2);
		}

		return res;
	}

	public static double dot(Vector a, Vector b) {
		return a.getX()*b.getX() + a.getY()*b.getY() + a.getZ()*b.getZ();
	}

	public static Vector scalar(Vector a, double x) {
		return new Vector(a.getX() * x, a.getY() * x, a.getZ() * x);
	}

	public static Vector subtract(Vector a, Vector b) {
		return new Vector(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
	}

	public static Vector add(Vector a, Vector b) {
		return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
	}

	public static double getInnerAngle(Vector v1, Vector v2) {
		double cosAngle = Utils.dot(v1, v2) / (v1.getLength() * v2.getLength());
		return Math.acos(cosAngle);
	}

}
