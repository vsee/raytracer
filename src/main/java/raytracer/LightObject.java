package raytracer;
public class LightObject extends SceneObject {

	public Vector position;

	public LightObject(Vector p, Colour c, Type t) {
		super(c, t);
		position = p;
	}

	@Override
	public String toString() {
		return super.toString() + "\npos: " + position;
	}

	@Override
	public Vector getPosition() {
		return position;
	}
}
