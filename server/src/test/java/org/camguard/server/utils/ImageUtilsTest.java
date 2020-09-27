package org.camguard.server.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.camguard.server.models.ConfigurationCamGuard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImageUtilsTest {

	@Test
	public void testSobel() throws IOException {

		BufferedImage src = ImageIO.read(getClass().getResourceAsStream("sample.jpg"));
		src = ImageUtils.sobel(src).toBufferedImage();

		// Reference from gimp
		BufferedImage ref = ImageIO.read(getClass().getResourceAsStream("edge.jpg"));

		for (int x = 1; x < (src.getWidth() - 1); x++) {
			for (int y = 1; y < (src.getHeight() - 1); y++) {
				int pixelSrc = ColorUtils.rgbToGrey(src.getRGB(x, y));
				int pixelRef = ColorUtils.rgbToGrey(ref.getRGB(x, y));

				int diff = Math.abs(pixelRef - pixelSrc);
				Assertions.assertTrue(diff < 55, "[" + x + "," + y + "]: " + pixelSrc + "!=" + pixelRef);
			}
		}

	}

	@Test
	public void testGaussianMatrix() {		
		int size = 7;
		double[] ref = new double[] {
				  1.0E-6,      2.3E-5,     1.91E-4,     3.88E-4,     1.91E-4,      2.3E-5,      1.0E-6,
			      2.3E-5,     7.86E-4,     0.00656,    0.013303,     0.00656,     7.86E-4,      2.3E-5,
			     1.91E-4,     0.00656,     0.05472,    0.110979,     0.05472,     0.00656,     1.91E-4,
			     3.88E-4,    0.013303,    0.110979,    0.225079,    0.110979,    0.013303,     3.88E-4,
			     1.91E-4,     0.00656,     0.05472,    0.110979,     0.05472,     0.00656,     1.91E-4,
			      2.3E-5,     7.86E-4,     0.00656,    0.013303,     0.00656,     7.86E-4,      2.3E-5,
			      1.0E-6,      2.3E-5,     1.91E-4,     3.88E-4,     1.91E-4,      2.3E-5,      1.0E-6
		};
		
		double[] matrix = ImageUtils.buildGaussianConvolutionMatrix(size);
		
		
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {		
				double rounded = matrix[x+y*size];
				
				rounded = Math.round(rounded*10e5)/10e5;
				//System.out.print(StringUtils.leftPad(""+rounded, 12, " ") + ",");
				assertEquals(rounded, ref[x+y*size]);
			}
			//System.out.println();
		}	
		
	}

	@Test
	public void testNoAlert() throws IOException {		
		BufferedImage src = ImageIO.read(getClass().getResourceAsStream("sample.jpg"));
		BufferedImage ref = ImageIO.read(getClass().getResourceAsStream("sample2.jpg"));

		ConfigurationCamGuard.WebcamUrl webcamConfig = new ConfigurationCamGuard.WebcamUrl();
		webcamConfig.ignoreLeftUpperX = 320;
		webcamConfig.ignoreLeftUpperY = 0;
		webcamConfig.ignoreRightBottomX = 640;
		webcamConfig.ignoreRightBottomY = 10;
		webcamConfig.minAlert = 500000;

		Assertions.assertFalse(ImageUtils.shouldAlert(src, ref, webcamConfig));

	}
	
	
	@Test
	public void testShouldAlert() throws IOException {		
		BufferedImage src = ImageIO.read(getClass().getResourceAsStream("shouldAlert1.jpg"));
		BufferedImage ref = ImageIO.read(getClass().getResourceAsStream("shouldAlert2.jpg"));

		ConfigurationCamGuard.WebcamUrl webcamConfig = new ConfigurationCamGuard.WebcamUrl();
		webcamConfig.ignoreLeftUpperX = 320;
		webcamConfig.ignoreLeftUpperY = 0;
		webcamConfig.ignoreRightBottomX = 640;
		webcamConfig.ignoreRightBottomY = 10;
		webcamConfig.minAlert = 500000;
		
		
		Assertions.assertTrue(ImageUtils.shouldAlert(src, ref, webcamConfig));

	}
}
