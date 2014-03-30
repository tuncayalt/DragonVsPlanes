package com.tuncay.dragonvsplanes.model.interfaces;

public interface ITouchable {
	
	public void handleActionDown(int eventX, int eventY); 
	
	public void handleActionMove(int eventX, int eventY); 
	
	public void handleActionLongDown(int eventX, int eventY);

}
