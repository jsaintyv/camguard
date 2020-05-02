package org.camguard.server.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailService {
	
	static final Log LOG = LogFactory.getLog(MailService.class);
	
	private MailService() {
	}

	private static final MailService INSTANCE = new MailService();

	public static MailService instance() {
		return INSTANCE;
	}
	
}
