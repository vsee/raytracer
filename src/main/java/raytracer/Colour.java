package raytracer;
public class Colour {

	public static final Colour GREY = new Colour(100,100,100);
	public static final Colour WHITE = new Colour(255,255,255);
	public static final Colour RED = new Colour(255,0,0);
	public static final Colour GREEN = new Colour(0,255,0);
	public static final Colour BLUE = new Colour(0,0,255);
	public static final Colour BLACK = new Colour(0,0,0);;

	public int R;
	public int G;
	public int B;

	public Colour(int r, int g, int b) {
		if(r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0)
			throw new RuntimeException("Colour channel out of range");

		R = r;
		G = g;
		B = b;
	}

	@Override
	public String toString() {
		return R + " " + G + " " + B;
	}
}
