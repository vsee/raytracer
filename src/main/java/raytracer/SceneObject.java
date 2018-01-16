package raytracer;
public abstract class SceneObject {

	public enum Type { POINTLIGHT, SPHERE };

	public Colour colour;
	public Type type;

	public SceneObject(Colour c, Type t) {
		colour = c;
		type = t;
	}

	public abstract Vector getPosition();

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder()
		.append("Colour: ").append(colour).append("\n")
		.append("type: ").append(type.name());
		return bld.toString();
	}

}
