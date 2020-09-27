package org.camguard.server;

import java.io.File;

import org.camguard.server.services.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerApplication {
	private static String configurationJson;
	private static final Logger LOG = LoggerFactory.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		if(args.length == 0) {
			configurationJson = "./configuration.json"; 
		} else {
			configurationJson = args[0];
		}
		
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		
		return () -> {			
			LOG.info("### Load configuration ### ");
			ConfigurationService.getInstance().loadInstance(new File(configurationJson));
		};
	}

}
