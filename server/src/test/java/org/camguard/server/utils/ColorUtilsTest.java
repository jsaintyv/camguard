package org.camguard.server.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class ColorUtilsTest {

	@Test
	public void test() {
		Color a = new Color(255, 250, 255);
		Color b = new Color(255, 255, 250);
		assertEquals(10, ColorUtils.diffColor(a.getRGB(), b.getRGB()));
		
		a = new Color(250, 250, 255);
		b = new Color(255, 255, 250);
		assertEquals(15, ColorUtils.diffColor(a.getRGB(), b.getRGB()));
		
		a = new Color(240, 255, 255);
		b = new Color(255, 255, 255);
		assertEquals(15, ColorUtils.diffColor(a.getRGB(), b.getRGB()));
	}
}
