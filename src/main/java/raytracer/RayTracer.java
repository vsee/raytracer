package raytracer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RayTracer {

	public static void main(String[] args) {

		final String imgOutName = "image.ppm";
		final int imageWidth = 1000;
		final int imageHeight = 800;
		final int MAX_DEPTH = 3;

		SceneObject[] scene = {
				new LightObject(new Vector(-50, 50, 250), Colour.WHITE, SceneObject.Type.POINTLIGHT),
//				new LightObject(new Vector(-50, -50, 250), Colour.WHITE, SceneObject.Type.POINTLIGHT),
				new OrbObject(new BoundingSphere(new Vector(0, -10,300), 20), Colour.RED, SceneObject.Type.SPHERE),
				new OrbObject(new BoundingSphere(new Vector(50,20,300), 30), Colour.BLUE, SceneObject.Type.SPHERE)
		};

		System.out.println("Tracing image: " + imageWidth + "x" + imageHeight + " ...");


		double invWidth = 1 / (double) imageWidth;
		double invHeight = 1 / (double) imageHeight;
		double fov = 30;
		double aspectratio = imageWidth / (double) imageHeight;
		double angle = Math.tan(Math.PI * 0.5 * fov / (double)180);

		Vector camera = new Vector(0,0,0);

		JRGBFrameBuffer image = new JRGBFrameBuffer(imageWidth, imageHeight);

		for(int y = 0; y < imageHeight; y++) {
			for(int x = 0; x < imageWidth; x++) {

//				System.out.println("Cast at " + x + "x" + y);

				double xx = (2 * ((x + 0.5) * invWidth) - 1) * angle * aspectratio;
				double yy = (1 - 2 * ((y + 0.5) * invHeight)) * angle;

				Ray r = new Ray(new Vector(xx, yy, 1), camera);
				Colour c = render(r, scene, MAX_DEPTH);
				image.setColour(x, y, c == null ? Colour.GREY : c);
			}
		}

		try {
			image.writeToFile(imgOutName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Image written to " + imgOutName);
	}

	private static class Intersection implements Comparable<Intersection> {

		double dist;
		SceneObject obj;

		public Intersection(double d, SceneObject o) {
			dist = d;
			obj = o;
		}

		@Override
		public int compareTo(Intersection o) {
			if(o == null) return 1;
			else if(dist < o.dist) return -1;
			else if(dist == o.dist) return 0;
			else return 1;
		}


	}

	private static List<Intersection> getIntersects(Ray r, SceneObject[] scene) {
		List<Intersection> inters = new ArrayList<Intersection>();
		for(SceneObject obj : scene) {
			Double dist = null;
			switch(obj.type) {
				case POINTLIGHT: // no intersections with point light sources
					continue;
				case SPHERE:
					dist = Utils.intersect(r, ((OrbObject)obj).bs);
					break;
				default:
					throw new RuntimeException("Unknown object type: " + obj.type.name());
			}

			if(dist != null && dist > 0) {
				inters.add(new Intersection(dist, obj));
			}
		}

		Collections.sort(inters);
		return inters;
	}

	private static Colour render(Ray r, SceneObject[] scene, int depth) {
		if(depth < 0) return null;

		List<Intersection> inters = getIntersects(r, scene);

		if(inters.size() != 0) {
			Intersection i = inters.get(0);
			if(i.obj.type == SceneObject.Type.POINTLIGHT) return i.obj.colour;
			else return renderDiffuse(r, i, scene);
		}
		else return null;
	}

	private static Colour renderDiffuse(Ray r, Intersection i, SceneObject[] scene) {
		Vector iPos = Utils.add(Utils.scalar(r.dir, i.dist), r.origin);
		Colour res = null;

		for(SceneObject obj : scene) {
			if(obj.type == SceneObject.Type.POINTLIGHT) {
				Vector lightVec = Utils.subtract(obj.getPosition(), iPos);
				Ray diffuseR = new Ray(lightVec, iPos);

				List<Intersection> diffInters = getIntersects(diffuseR, scene);
				if(diffInters.size() == 0) {
					Vector surfNormal = Utils.norm(Utils.subtract(iPos, i.obj.getPosition()));
					Vector reflecV = Utils.subtract(r.dir, Utils.scalar(surfNormal, 2 * Utils.dot(r.dir, surfNormal)));

					double angle = Utils.getInnerAngle(reflecV, lightVec);
					double intensity = Math.max((-1 / (Math.PI)) * angle + 1, 0);

					Colour diffCol = i.obj.colour;
					Colour specCol = new Colour((int)(obj.colour.R * intensity),
							(int)(obj.colour.G * intensity),
							(int)(obj.colour.B * intensity));
					res = blendColours(blendColours(diffCol, specCol), res);
				}
				else {
					res = blendColours(Colour.BLACK, res);
				}
			}
		}

		return res;
	}

	private static Colour blendColours(Colour c1, Colour c2) {
		if(c1 == null) return c2;
		if(c2 == null) return c1;

		return new Colour(
				(c1.R + c2.R) / 2,
				(c1.G + c2.G) / 2,
				(c1.B + c2.B) / 2);
	}
}
