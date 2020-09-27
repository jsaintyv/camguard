package org.camguard.server.controllers;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.models.ConfigurationCamGuard.WebcamUrl;
import org.camguard.server.scheduled.ScheduledCollect;
import org.camguard.server.services.ConfigurationService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {		
	private Random randomSimulationCam = new Random(System.currentTimeMillis());
	
	private static final String[] simulationNoAlertSet = new String[] {
		"sim1.jpg",
		"sim2.jpg",
	};
	
	private static final String[] simulationShouldAlertSet = new String[] {
		"sim3.jpg",			
	};
	
	public ImageController() {		
	}
	
	@GetMapping("/api/images/list/{name}")
	public ResponseEntity<String[]>  list(@PathVariable String name, @RequestParam(required = false) Long start, @RequestParam(required = false) Long end) {
		List<String> result = new ArrayList<>();
		
		System.out.println(start + "=>" + end);
		
		
		
		if(end == null) {
			end = System.currentTimeMillis();
		}
		
		if(start == null) {
			start = System.currentTimeMillis() - 600000; // 10min	
		}
		
		System.out.println(new Date(start) + " to " + new Date(end));
				
		ConfigurationCamGuard configuration = ConfigurationService.getInstance().getConfiguration();
		File dirImage = new File(configuration.imagedir);
		if(! dirImage.exists()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		WebcamUrl webcam = configuration.byName(name);
		if(webcam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
				
		
		for(String fileName : dirImage.list()) {
			if(fileName.startsWith(name)) {
				File file = new File(dirImage, fileName);
				if(start <= file.lastModified() && file.lastModified() <= end) {
					result.add(fileName);	
				}				
			}
		}		
		Collections.sort(result, (a,b)->-a.compareTo(b));		
		return new ResponseEntity<>(result.toArray(new String[result.size()]), HttpStatus.ACCEPTED);
	}
		
	
	@RequestMapping(value = "/api/images/retrieve/{filename:.+}", 
			method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> retrieve(@PathVariable("filename") String filename) throws IOException {
		ConfigurationCamGuard configuration = ConfigurationService.getInstance().getConfiguration();
		File dirImage = new File(configuration.imagedir);
		if(! dirImage.exists()) {			
			return new ResponseEntity<InputStreamResource>(HttpStatus.BAD_REQUEST);
		}
	
		File fileImage = new File(dirImage, filename);
		if(! fileImage.exists()) {		
			return new ResponseEntity<InputStreamResource>(HttpStatus.BAD_REQUEST);
		}
        
		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.cacheControl(CacheControl.maxAge(Duration.ofDays(10)))
				.body(new InputStreamResource(new FileInputStream(fileImage)));		
    }
	
	@RequestMapping(value = "/api/images/direct/{webcamName:.+}", 
			method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> retrieveDirect(@PathVariable("webcamName") String webcamName) throws IOException {			
		WebcamUrl webcam = ConfigurationService.getInstance().getWebcamByName(webcamName);
		if(webcam == null) {
			return new ResponseEntity<InputStreamResource>(HttpStatus.BAD_REQUEST);
		}
		byte[] resultBuffer = ScheduledCollect.downloadImage(webcam);
		        
		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.cacheControl(CacheControl.maxAge(Duration.ofDays(10)))
				.body(new InputStreamResource(new ByteArrayInputStream(resultBuffer)));		
    }
	
	
	
	/**
	 * Simulate camera for localtest
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/test/camera", 
			method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> testCamera() throws IOException {			
		String ressourcePath = "/org/camguard/server/controllers/res/";
		long t = randomSimulationCam.nextInt(100);
		if(t < 80) {
			ressourcePath += simulationNoAlertSet[randomSimulationCam.nextInt(simulationNoAlertSet.length)];
		} else {
			ressourcePath += simulationShouldAlertSet[randomSimulationCam.nextInt(simulationShouldAlertSet.length)];
		}
					        
		return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.cacheControl(CacheControl.maxAge(Duration.ofDays(10)))
				.body(new InputStreamResource(this.getClass().getResourceAsStream(ressourcePath)));		
    }
}
