package org.camguard.server.utils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class GreyImage {
	private final int width;
	private final int height;
	private final short[] buffer;
	public static final short WHITE = 255;
	public static final short BLACK = 0;
	
	
	
	public GreyImage(BufferedImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.buffer = new short[width*height];
		
		for(int x=0;x < width;x++) {
			for(int y=0;y < height;y++) {
				this.buffer[x+y*width] = (short) ColorUtils.rgbToGrey(img.getRGB(x, y));
			}
		}	
	}
	
	public GreyImage(int width, int height) {
		this.width = width;
		this.height = height;
		this.buffer = new short[width*height];
	}

	public short getGrey(int x, int y) {
		return this.buffer[x+y*width];
	}
	
	public void setGrey(int x, int y, short v) {
		this.buffer[x+y*width] = v;
		
	}
	
	public int getRGB(int x, int y) {
		return ColorUtils.greyToRgb(this.buffer[x+y*width]);
	}

	public void saturate(int threshold) {
		for(int x=0;x < buffer.length;x++) {			
			this.buffer[x] = this.buffer[x] > threshold ? WHITE : BLACK;			
		}		
	}
	
	public void substractAbs(GreyImage src) {
		for(int x=0;x < buffer.length;x++) {			
			this.buffer[x] = (short)Math.abs((int) (this.buffer[x] - src.buffer[x]));			
		}		
		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BufferedImage toBufferedImage() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x=0;x < width;x++) {
			for(int y=0;y < height;y++) {
				img.setRGB(x, y, ColorUtils.greyToRgb(this.buffer[x+y*width]));				
			}
		}
		return img;
	}
	
	
	
	public void applyGaussianFilter(int size) {
		short[] tmpBuffer = Arrays.copyOf(buffer, buffer.length);
		double[] matrix = ImageUtils.buildGaussianConvolutionMatrix(size);
		
		for(int x=0;x < width;x++) {
			for(int y=0;y < height;y++) {
				tmpBuffer[x + y*width] = ImageUtils.computeConvolution(buffer, width, height, x, y, matrix, size);
			}
		}
		
		System.arraycopy(tmpBuffer, 0, buffer, 0, buffer.length);			
	}
	
	
	
	
}
