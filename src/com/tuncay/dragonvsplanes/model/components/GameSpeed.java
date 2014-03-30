package com.tuncay.dragonvsplanes.model.components;

public enum GameSpeed {
	Slow(27), Medium(35), Fast(43);
	private int fps;
	GameSpeed(int p) {
		fps = p;
	}
	public int getFps() {
	    return fps;
	} 
}
