package org.camguard.server.utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.lang3.NotImplementedException;
import org.camguard.server.models.ConfigurationCamGuard;

public class ImageUtils {
	private static final Logger log = Logger.getLogger(ImageUtils.class.getName());

	public static boolean shouldKeep(BufferedImage src, BufferedImage dst,
			ConfigurationCamGuard.WebcamUrl webcamConfiguration) {
		int levelScore = webcamConfiguration.minDiff == null ? 1500000 : webcamConfiguration.minDiff;
		int ignoreLeftUpperX = -1;
		int ignoreLeftUpperY = -1;
		int ignoreRightBottomX = -1;
		int ignoreRightBottomY = -1;

		if (webcamConfiguration.isIgnoreValid()) {
			ignoreLeftUpperX = webcamConfiguration.ignoreLeftUpperX;
			ignoreLeftUpperY = webcamConfiguration.ignoreLeftUpperY;
			ignoreRightBottomX = webcamConfiguration.ignoreRightBottomX;
			ignoreRightBottomY = webcamConfiguration.ignoreRightBottomY;
		}

		if (src.getWidth() != dst.getWidth() || src.getHeight() != dst.getHeight()) {
			return true;
		}

		double sum = 0;
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				if (ignoreLeftUpperX <= x && ignoreLeftUpperY <= y && x <= ignoreRightBottomX
						&& y <= ignoreRightBottomY)
					continue;

				sum += ColorUtils.diffColor(src.getRGB(x, y), dst.getRGB(x, y));
			}
		}

		if (sum > levelScore) {
			log.log(Level.INFO, "Diff sum " + sum);
		}
		return sum > levelScore;
	}

	public static boolean shouldAlert(BufferedImage srcImg, BufferedImage dstImg,
			ConfigurationCamGuard.WebcamUrl webcamConfiguration) {
		if (srcImg == null) {
			return false;
		}

		int levelScore = webcamConfiguration.minAlert == null ? 500000 : webcamConfiguration.minAlert;
		int ignoreLeftUpperX = -1;
		int ignoreLeftUpperY = -1;
		int ignoreRightBottomX = -1;
		int ignoreRightBottomY = -1;

		if (webcamConfiguration.isIgnoreValid()) {
			ignoreLeftUpperX = webcamConfiguration.ignoreLeftUpperX;
			ignoreLeftUpperY = webcamConfiguration.ignoreLeftUpperY;
			ignoreRightBottomX = webcamConfiguration.ignoreRightBottomX;
			ignoreRightBottomY = webcamConfiguration.ignoreRightBottomY;
		}
				
		GreyImage src = new GreyImage(srcImg);
		GreyImage dst = new GreyImage(dstImg);
		
		src.applyGaussianFilter(15);
		dst.applyGaussianFilter(15);		
		dst.substractAbs(src);
		dst.saturate(100);
		
		//DEBUG show(dst, true);
		
		
			
		double sum = 0;
		for (int x = 1; x < src.getWidth(); x++) {
			for (int y = 1; y < src.getHeight(); y++) {
				if (ignoreLeftUpperX <= x && ignoreLeftUpperY <= y && x <= ignoreRightBottomX
						&& y <= ignoreRightBottomY)
					continue;

				sum += dst.getGrey(x, y) > 10  ? 255 : 0;
			}
		}

		
			log.log(Level.INFO, "Diff alert " + sum);
		
		return sum > levelScore;
	}

	/**
	 * Sum weighted gray color
	 */
	private static int sumWeightedGrayColor(int[] matrixWeight, int refx, int refy, BufferedImage src) {
		int sum = 0;
		for (int x = 0; x <= 2; x++) {
			for (int y = 0; y <= 2; y++) {
				int indexMatrix = y * 3 + x;
				sum += matrixWeight[indexMatrix] * ColorUtils.rgbToGrey(src.getRGB(x - 1 + refx, y - 1 + refy));
			}
		}
		return sum;
	}

	/**
	 * Edge detection with sobel operator (
	 * https://en.wikipedia.org/wiki/Sobel_operator )
	 * 
	 * @param src image source
	 * @return image filtered to show edge
	 */
	public static GreyImage sobel(BufferedImage src) {

		int[] gx = new int[] { -1, 0, 1, -2, 0, 2, -1, 0, 1 };
		int[] gy = new int[] { -1, -2, -1, 0, 0, 0, 1, 2, 1 };

		int rows = src.getWidth();
		int cols = src.getHeight();

		GreyImage dst = new GreyImage(rows, cols);

		int threshold = 255;
		int ratioToGetWhite = 255 / threshold;
		for (int x = 1; x < (rows - 1); x++) {
			for (int y = 1; y < (cols - 1); y++) {
				int s1 = sumWeightedGrayColor(gx, x, y, src);
				int s2 = sumWeightedGrayColor(gy, x, y, src);
				int grayScale = (int) Math.sqrt(s1 * s1 + s2 * s2);
				if (grayScale > threshold) {
					grayScale = threshold;
				}
				dst.setGrey(x, y, (short)(grayScale * ratioToGetWhite));
			}
		}
		return dst;
	}
	
	public static void saturate(BufferedImage src, int threshold) {
		int width = src.getWidth();
		int height = src.getHeight();
		
		int white = ColorUtils.greyToRgb(255);
		int black = ColorUtils.greyToRgb(0);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int gray = ColorUtils.rgbToGrey(src.getRGB(x, y));
				if(gray > threshold) {
					src.setRGB(x, y, white);
				} else {
					src.setRGB(x, y, black);
				}				
			}
		}		
	}
	
	public static void substractAbs(BufferedImage from, BufferedImage by) {
		int width = from.getWidth();
		int height = from.getHeight();
					
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				int diff = Math.abs(ColorUtils.rgbToGrey(from.getRGB(x, y)) - ColorUtils.rgbToGrey(by.getRGB(x, y)));
				from.setRGB(x, y, ColorUtils.greyToRgb(diff));
				
			}
		}		
	}
	
	public static void show(GreyImage dst) {
		show(dst.toBufferedImage());
	}
	
	public static void show(GreyImage dst, boolean wait) {
		show(dst);
		
		if(wait) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
	public static void show(BufferedImage dst) {					
		JFrame frame = new JFrame();
		frame.add(new JLabel(new ImageIcon(dst)));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}		
		});
		frame.pack();
		frame.setVisible(true);
	}
	 
	
	/**
	 * @see https://en.wikipedia.org/wiki/Gaussian_blur
	 */
	public static double[] buildGaussianConvolutionMatrix(int size) {
		if(size % 2 != 1) {
			throw new NotImplementedException(size + " should be odd");
		}
		
		if(size < 3) {
			throw new NotImplementedException(size + " should be odd  >= 3");
		}
		
		final double[] matrix = new double[size*size];
		final double sigma = 0.84089642;
		final double sigmaSquare = sigma*sigma;
		final double w = 1 /(2*Math.PI*sigmaSquare); 
		for(int x=0;x < size;x++) {
			for(int y=0;y < size;y++) {
				int rx = x - (size/2);
				int ry = y - (size/2);
				matrix[x +size*y] = w*Math.exp(-((rx*rx)+(ry*ry))/(2*sigmaSquare));
			}
		}
		return matrix;
	}
	
	public static short computeConvolution(short[] buffer, int width, int height, int px, int py, double[] matrix, int size) {
		double sumWeight = 0;
		double total = 0;
		int halfSize= size/2;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				int refx = px + x - halfSize;
				int refy = py + y - halfSize;
				if(refx < 0 || refx >= width || refy < 0 || refy >= height) {
					continue;
				}
				
				double weight = matrix[x+y*size];
				sumWeight += weight;
				 
				total += weight * buffer[refx + refy * width];
			}
		}
		// normalize
		return (short)(total / sumWeight); 
	} 

	public static void applyGaussian(BufferedImage img, int size) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		double[] matrix = buildGaussianConvolutionMatrix(size);
		
		short[] reds = new short[width*height];
		short[] greens = new short[width*height];
		short[] blues = new short[width*height];
				
		for(int x=0;x < width;x++) {
			for(int y=0;y < height;y++) {
				int rgb = img.getRGB(x, y);
				reds[x + y * width] = (short) ((rgb >> 16) & 0xFF);
				greens[x + y * width] = (short) ((rgb >> 8) & 0xFF);
				blues[x + y * width] = (short) (rgb & 0xFF);				
			}
		}
		
		for(int x=0;x < width;x++) {
			for(int y=0;y < height;y++) {
				int r = computeConvolution(reds, width, height, x, y, matrix, size);
				int g = computeConvolution(greens, width, height, x, y, matrix, size);
				int b = computeConvolution(blues, width, height, x, y, matrix, size);
				img.setRGB(x, y, (r << 16) + (g << 8) + b);				
			}
		}
	}
}
