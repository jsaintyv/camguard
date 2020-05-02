package org.camguard.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.camguard.server.models.ConfigurationCamGuard;
import org.junit.jupiter.api.Test;

public class ConfigurationServiceTest {
	@Test
	public void test() {
		 InputStream in = ConfigurationServiceTest.class.getResourceAsStream("/org/camguard/server/services/configuration.json");
		 ConfigurationCamGuard configuration = ConfigurationService.loadConfiguration(in);
		 
		 assertEquals("admin", configuration.login);
		 assertEquals("secret", configuration.password);
		 
		 ConfigurationCamGuard.WebcamUrl webcam = configuration.webcams[0];
		 assertEquals("http://172.0.0.10/tmpfs/auto.jpg", webcam.url);
		 assertEquals("admin", webcam.login);
		 assertEquals("admin", webcam.password);
	}
}
