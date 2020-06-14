package org.camguard.server.controllers;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.camguard.server.models.AlertEntry;
import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.models.ConfigurationCamGuard.WebcamUrl;
import org.camguard.server.scheduled.ScheduledCollect;
import org.camguard.server.services.AlertService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {
	private static final Logger log = Logger.getLogger(AlertController.class.getName());

	
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
