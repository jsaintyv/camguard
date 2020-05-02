package org.camguard.server.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	public static void copyToFile(InputStream in, File file) throws FileNotFoundException, IOException {
		byte[] buffer = new byte[10*1024];
		try(BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(file))) {
			int len;
			while ((len = in.read(buffer)) > 0) {
				bis.write(buffer, 0, len);
			}			
			bis.flush();
		}
	}
}
