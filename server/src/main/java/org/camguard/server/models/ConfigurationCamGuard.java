package org.camguard.server.models;

public class ConfigurationCamGuard {
	public String login;
	public String password;
	public String imagedir;	
	public WebcamUrl[] webcams;
	
	public String smtpServer;
	public Integer smtpPort;
	public Boolean smtpTls;
	public String smtpLogin;
	public String smtpPassword;
	public String senderEmail;
	public String[] alertEmails;
		
	public WebcamUrl byName(String name) {
		for(WebcamUrl webcam: webcams) {
			if(webcam.name.equals(name)) {
				return webcam;
			}			
		}
		return null;
	}
	
	public static class WebcamUrl {
		public String name;
		public String url;
		public String login;
		public String password;
		public Integer minDiff;
		public Integer ignoreLeftUpperX;
		public Integer ignoreLeftUpperY;
		public Integer ignoreRightBottomX;
		public Integer ignoreRightBottomY;
		public Long purgeOlderInSeconds;
		
		public Integer minAlert;
		public Boolean alertEmail;
		
		public boolean isIgnoreValid() {
			return ignoreLeftUpperX != null && ignoreLeftUpperY != null && ignoreRightBottomX != null && ignoreRightBottomY != null;
		}
	}
}
