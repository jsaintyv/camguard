package org.camguard.server.models;

public class AlertEntry {
	private String webcam;
	private String filename;
	private long time;
	
	public String getWebcam() {
		return webcam;
	}

	public void setWebcam(String webcam) {
		this.webcam = webcam;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}	
}
