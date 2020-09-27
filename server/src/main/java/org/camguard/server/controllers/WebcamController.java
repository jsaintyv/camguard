package org.camguard.server.controllers;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.models.ConfigurationCamGuard.WebcamUrl;
import org.camguard.server.services.ConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebcamController {		
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
