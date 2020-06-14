package org.camguard.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.camguard.server.models.AlertEntry;

public class AlertService {
	private static final AlertService INSTANCE  = new AlertService();
	private AlertService() {		
	}
	
	public static AlertService instance() {
		return INSTANCE;
	}
	
	/**
	 * Temporary store in memory
	 */
	private List<AlertEntry> alerts = new ArrayList<AlertEntry>();
		
	public void register(String webcam, String filename) {
		AlertEntry alert = new AlertEntry();
		alert.setFilename(filename);
		alert.setTime(System.currentTimeMillis());
	}
	
	public int countLastDay(String webcam) {
		final long minTime = System.currentTimeMillis() - (24*3600*1000);
		return (int)alerts.stream().filter((a)->webcam.equals(a.getWebcam()) && a.getTime() > minTime).count();
	}
	
	public List<AlertEntry> listAlert(String webcam) {
		return alerts.stream().filter((a)->webcam.equals(a.getWebcam())).collect(Collectors.toList());
	}
	
	
}
