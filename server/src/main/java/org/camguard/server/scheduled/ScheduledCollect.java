package org.camguard.server.scheduled;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.services.AlertService;
import org.camguard.server.services.ConfigurationService;
import org.camguard.server.services.MailService;
import org.camguard.server.utils.FileUtils;
import org.camguard.server.utils.ImageUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledCollect {
	private static final Logger log = Logger.getLogger(Scheduled.class.getName());

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	private Map<String, String> mapLastKeepedFile = new HashMap<>();	

	@Scheduled(fixedRate = 1000)
	public void collect() {		
		ConfigurationCamGuard configuration = ConfigurationService.getInstance().getConfiguration();
		File dirImage = new File(configuration.imagedir);
		if (!dirImage.exists()) {
			return;
		}

		for (ConfigurationCamGuard.WebcamUrl webcam : configuration.webcams) {
			try {
				consume(dirImage, configuration, webcam);
			} catch(Exception e) {
				log.log(Level.SEVERE, webcam.name + ": Exception " + e.getMessage() + " during processing ", e);
			}
			purge(dirImage, configuration, webcam);
		}
	}

	
	public void consume(File dirImage, ConfigurationCamGuard configuration, ConfigurationCamGuard.WebcamUrl webcam) throws Exception {

		byte[] resultBuffer = downloadImage(webcam);
		if(resultBuffer == null) {
			return;
		}
		
		BufferedImage newImage;		
		newImage = ImageIO.read(new ByteArrayInputStream(resultBuffer));
		
		
		BufferedImage previousImage = loadPreviousFile(dirImage, webcam);
		if (previousImage == null || ImageUtils.shouldKeep(previousImage, newImage, webcam)) {
			String fileName = webcam.name + "." + dateFormat.format(new Date()) + ".jpg";	
			FileUtils.copyToFile(new ByteArrayInputStream(resultBuffer), new File(dirImage, fileName));
					
			if(previousImage != null && ImageUtils.shouldAlert(previousImage, newImage, webcam)) {
				if(Boolean.TRUE.equals(webcam.alertEmail) && AlertService.instance().countLastDay(webcam.name) < 20) {
					// Avoid to send too mail for webcam.
					MailService.sendAlert(new File(fileName));									
				}
				AlertService.instance().register(webcam.name, fileName);
			}
							
			mapLastKeepedFile.put(webcam.name, fileName);
			log.log(Level.INFO, "Keeped " + fileName);
		}
	}


	private BufferedImage loadPreviousFile(File dirImage, ConfigurationCamGuard.WebcamUrl webcam) {
		String lastKeepedFile = mapLastKeepedFile.get(webcam.name);
		BufferedImage previousImage = null;
		if(lastKeepedFile != null) {
			File previousFile = new File(dirImage, lastKeepedFile);		
			if(previousFile.exists()) {				
				long duration = System.currentTimeMillis() - previousFile.lastModified();
				if (duration < (3600 * 1000)) {
					try {
						previousImage = ImageIO.read(previousFile);
					} catch(IOException e) {
						log.log(Level.SEVERE, webcam.name + ":" + previousFile.getAbsolutePath() + " can't be load ", e);
					}
				}							
			}
		}
		return previousImage;
	}

	public static byte[] downloadImage(ConfigurationCamGuard.WebcamUrl webcam) {
		final byte[] buffer = new byte[512 * 1024];
		HttpGet httpGet = new HttpGet(webcam.url);
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(new AuthScope(httpGet.getURI().getHost(), httpGet.getURI().getPort()),
				new UsernamePasswordCredentials(webcam.login, webcam.password));

		
		byte[] resultBuffer = null;
		try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider)
				.build()) {
			CloseableHttpResponse reponse = httpclient.execute(httpGet);
			if (reponse.getStatusLine().getStatusCode() >= 400) {
				log.log(Level.INFO, reponse.getStatusLine().getStatusCode() + " " + webcam.url);
				return null;
			}
			InputStream in = reponse.getEntity().getContent();
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {				
				int len;
				while ((len = in.read(buffer)) > 0) {
					baos.write(buffer, 0, len);
				}
				resultBuffer = baos.toByteArray();
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
		return resultBuffer;
	}
	
	public void purge(File dirImage, ConfigurationCamGuard configuration, ConfigurationCamGuard.WebcamUrl webcam) {
		if(webcam.purgeOlderInSeconds == null)
			return;
		
		int countPurge = 0;
		long refTime = System.currentTimeMillis() - (webcam.purgeOlderInSeconds*1000);
		for(File file : dirImage.listFiles()) {
			if(file.lastModified() < refTime) {
				file.delete();
				countPurge++;
			}
		}
		
		if(countPurge > 0) {
			log.log(Level.INFO, "Purge " + countPurge + " files(s)");
		}
	}	
}
