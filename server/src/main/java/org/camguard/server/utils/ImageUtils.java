package org.camguard.server.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.camguard.server.models.ConfigurationCamGuard;

public class ImageUtils {
	private static final Logger log = Logger.getLogger(ImageUtils.class.getName());
	
	public static boolean shouldKeep(File previous, InputStream next, ConfigurationCamGuard.WebcamUrl webcamConfiguration) {
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
		
		try {
			long duration = System.currentTimeMillis() - previous.lastModified();
			if (duration > (3600 * 1000)) {
				return true;
			}

			BufferedImage src = ImageIO.read(previous);
			BufferedImage dst = ImageIO.read(next);

			if (src.getWidth() != dst.getWidth() || src.getHeight() != dst.getHeight()) {
				return true;
			}

			double sum = 0;
			for (int x = 0; x < src.getWidth(); x++) {
				for (int y = 0; y < src.getHeight(); y++) {
					if(ignoreLeftUpperX <= x && ignoreLeftUpperY <= y && x <= ignoreRightBottomX && y <= ignoreRightBottomY)
						continue;
					
					sum += ColorUtils.diffColor(src.getRGB(x, y), dst.getRGB(x, y));
				}
			}
			
			if(sum > levelScore) {
				log.log(Level.INFO, "Diff sum " + sum);
			}
			return sum > levelScore;
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
