package com.tuncay.dragonvsplanes.model.abstracts;

import com.tuncay.dragonvsplanes.MainGamePanel;
import com.tuncay.dragonvsplanes.math.Tools;
import com.tuncay.dragonvsplanes.model.components.FlagType;
import com.tuncay.dragonvsplanes.model.components.SoundManager;
import com.tuncay.dragonvsplanes.parameter.Parameter;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Flag {	
	
	protected static int nextId = 0;    // the next available ID
	private int id;
	protected Bitmap bitmap;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int age;
	protected int effectiveAge;
	protected int nonEffectiveAge;
	protected boolean died;		
	protected boolean effective;
	protected int prize;
	
	public int getPrize() {
		return prize;
	}
	public void setPrize(int prize) {
		this.prize = prize;
	}
	protected boolean previous;
	
	protected FlagType flagType;

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isDied() {
		return died;
	}
	public void setDied(boolean died) {
		this.died = died;
	}
	public boolean isEffective() {
		return effective;
	}
	public void setEffective(boolean effective) {
		this.effective = effective;
	}
	
	public FlagType getFlagType() {
		return flagType;
	}
	public void setFlagType(FlagType flagType) {
		this.flagType = flagType;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void update(Bubble bubble, SoundManager soundManager) {
		age ++;
		
		if (effective){
			effectiveAge++;
		}else{
			nonEffectiveAge++;
		}
		
		if (Tools.getDistanceFlag(this, bubble) <= 0){		
			this.effective = true;
			this.givePoints();
			soundManager.playPoolSound(2, false);
		}
		if (effectiveAge >= Parameter.flagMaxEffectiveAge){
			this.effective = false;
			this.previous = true;
		}
		if (nonEffectiveAge >= Parameter.flagMaxNonEffectiveAge){
			this.died = true;
		}		
		if (isEffective()){
			setX(-500);
		}
		if (isDied()){
			die();					
		}
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (this.getWidth() / 2), y - (this.getHeight() / 2), null);
	}
	public void die() {
		MainGamePanel.FlagList.remove(this);
	}
	public void givePoints() {
		Tools.points += getPrize();		
	}
}
