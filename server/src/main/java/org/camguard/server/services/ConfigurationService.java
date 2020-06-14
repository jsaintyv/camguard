package org.camguard.server.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.camguard.server.models.ConfigurationCamGuard;
import org.camguard.server.models.ConfigurationCamGuard.WebcamUrl;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationService {
	public ConfigurationCamGuard configuration;
	private ConfigurationService() {	
	}
	
	public static final ConfigurationService INSTANCE = new ConfigurationService();
	 
	public static ConfigurationService getInstance() {
		return INSTANCE;
	}	
	
	public ConfigurationCamGuard getConfiguration() {
		return configuration;
	}
	
	public WebcamUrl getWebcamByName(String name) {
		for (WebcamUrl webcam : configuration.webcams) {
			if (name.equals(webcam.name)) {
				return webcam;
			}
		}
		return null;
	}

	public void loadInstance(File file) throws IOException {
		configuration = loadConfiguration(file);
	}

	private ConfigurationCamGuard loadConfiguration(File file) throws IOException {
		if(! file.exists()) {
			throw new RuntimeException("Configuration not found :" + file.getAbsolutePath());
		}
		try(FileInputStream fin = new FileInputStream(file)) {
			return loadConfiguration(fin);
		}		
	}
		
	static ConfigurationCamGuard loadConfiguration(InputStream in)  {
		ObjectMapper mapper = new ObjectMapper();		
		ConfigurationCamGuard configuration;
		try {
			configuration = mapper.readValue(in, ConfigurationCamGuard.class);
		} catch (Exception e) {			
			throw new RuntimeException(e);
		}
		return configuration;
	}
}
