package org.camguard.server.controllers;


import java.io.File;
import java.util.List;

import org.camguard.server.models.AlertEntry;
import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.models.ConfigurationCamGuard.WebcamUrl;
import org.camguard.server.services.AlertService;
import org.camguard.server.services.ConfigurationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {
	public AlertController() {		
	}
	
	@GetMapping("/api/alert/list/{name}")
	public ResponseEntity<AlertEntry[]>  list(@PathVariable String name) {
					
		ConfigurationCamGuard configuration = ConfigurationService.getInstance().getConfiguration();
		File dirImage = new File(configuration.imagedir);
		if(! dirImage.exists()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		WebcamUrl webcam = configuration.byName(name);
		if(webcam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<AlertEntry> result = AlertService.instance().listAlert(webcam.name);			
		return new ResponseEntity<>(result.toArray(new AlertEntry[result.size()]), HttpStatus.ACCEPTED);
	}
		
	
	
}
