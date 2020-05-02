package org.camguard.server.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.models.ConfigurationCamGuard.WebcamUrl;
import org.camguard.server.services.ConfigurationService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebcamController {
	private static final Logger log = Logger.getLogger(WebcamController.class.getName());
	
	public WebcamController() {		
	}
	
	@GetMapping("/api/webcams/list")
	public ResponseEntity<String[]>  list() {
		List<String> result = new ArrayList<>();
		
		ConfigurationCamGuard configuration = ConfigurationService.getInstance().getConfiguration();
		File dirImage = new File(configuration.imagedir);
		if(! dirImage.exists()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for(WebcamUrl webcam: configuration.webcams) {
			result.add(webcam.name);
		}
			
		return new ResponseEntity<>(result.toArray(new String[result.size()]), HttpStatus.ACCEPTED);
	}		
}
