package raytracer;
public class OrbObject extends SceneObject {

	public BoundingSphere bs;

	public OrbObject(BoundingSphere bs, Colour c, Type t) {
		super(c, t);
		this.bs = bs;
	}

	@Override
	public String toString() {
		return super.toString() + "\nBS: " + bs;
	}

	@Override
	public Vector getPosition() {
		return bs.centre;
	}

}
