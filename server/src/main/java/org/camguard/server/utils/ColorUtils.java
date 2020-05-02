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
}
