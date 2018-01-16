package raytracer;
import java.io.FileOutputStream;
import java.io.IOException;

public class JRGBFrameBuffer implements Cloneable {
	public static final int CHANNEL_NUM = 3;

	private int width;
	private int height;
	private int buffSize;

	/** Treat this as unsigned byte by interpreting with: b & 0xFF */
	private byte[] buffer;

	public JRGBFrameBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		buffSize = width * height * CHANNEL_NUM;
		buffer = new byte[buffSize];
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getSize() { return buffSize; }

	public int getRawChannel(int idx) {
		return buffer[idx] & 0xFF;
	}

	public int getChannel(int x, int y, int channel) {
		if (channel > CHANNEL_NUM - 1 || channel < 0)
			throw new IllegalArgumentException("Given channel must be between 0 and " + (CHANNEL_NUM - 1) + ": " + channel);
		if (x < 0 || x >= width)
			throw new IllegalArgumentException("Given x coordinate out of bounds: " + x);
		if (y < 0 || y >= height)
			throw new IllegalArgumentException("Given y coordinate out of bounds: " + y);

		int idx = (y * width + x) * CHANNEL_NUM + channel;

		return buffer[idx] & 0xFF;
	}

	public void setChannel(int x, int y, int channel, int value) {
		if (channel > CHANNEL_NUM - 1 || channel < 0)
			throw new IllegalArgumentException("Given channel must be between 0 and " + (CHANNEL_NUM - 1) + ": " + channel);
		if (x < 0 || x >= width)
			throw new IllegalArgumentException("Given x coordinate out of bounds: " + x);
		if (y < 0 || y >= height)
			throw new IllegalArgumentException("Given y coordinate out of bounds: " + y);

		int idx = (y * width + x) * CHANNEL_NUM + channel;

		buffer[idx] = (byte) value;
	}

	public void setColour(int x, int y, Colour c) {
		setChannel(x, y, 0, c.R);
		setChannel(x, y, 1, c.G);
		setChannel(x, y, 2, c.B);
	}

	public Colour getColour(int x, int y) {
		return new Colour((byte) getChannel(x, y, 0),
				(byte) getChannel(x, y, 1), (byte) getChannel(x, y, 2));
	}

	public void writeToFile(String filename) throws IOException {
		FileOutputStream out = new FileOutputStream(filename);
		out.write(("P6\n" + getWidth() + " " + getHeight() + "\n255\n").getBytes()); // write header
		out.write(buffer);
		out.close();
	}
}
