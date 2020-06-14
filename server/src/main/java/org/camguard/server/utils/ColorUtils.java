package org.camguard.server.utils;

public class ColorUtils {	
	public static int diffColor(int colorA, int colorB) {
		int mask = 0xff;
		int diff = Math.abs((colorA & mask) - (colorB & mask));
		colorA = colorA >> 8;
		colorB = colorB >> 8;
		diff += Math.abs((colorA & mask) - (colorB & mask));
		colorA = colorA >> 8;
		colorB = colorB >> 8;
		diff += Math.abs((colorA & mask) - (colorB & mask));
		return diff;
	}
	
	/**
	 * Rgb to gray
	 */
	public static int rgbToGrey(int rgb) {		
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb & 0xFF);       
        return (r+g+b)/3;
	}
	
	/**
	 * Gray to rgb
	 */
	public static int greyToRgb(int gray) {
		if(gray > 255) {
			throw new RuntimeException(gray + " Should be between [0-255] ");
		}
        gray = gray & 0xFF;
               
        return (gray << 16) + (gray << 8) + gray;
	}
}
