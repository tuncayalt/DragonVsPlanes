package com.tuncay.dragonvsplanes.model.abstracts;

import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.components.Velocity;
import com.tuncay.dragonvsplanes.model.interfaces.IAnimatable;

import android.graphics.Bitmap;
import android.util.Log;

public abstract class Vehicle implements IAnimatable{
	
	private static final String TAG = Vehicle.class.getSimpleName();
	
	protected static int nextId = 0;    // the next available ID
	protected int id;
	protected Bitmap bitmap;
	protected int x;
	protected int y;
	protected int width;	
	protected int height;	
	protected int columnBitmap;	
	protected int rowBitmap;
	protected int srcX;
	protected int srcY;
	protected int prize;
	private boolean died;
	private boolean touched;
	private boolean finishedTurning;
	
	private Chassis chassis;
	private GoodOrEvil goodOrEvil;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
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
	public void setY(int y){
		this.y = y;
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
	public int getPrize() {
		return prize;
	}
	public void setPrize(int prize) {
		this.prize = prize;
	}
	public GoodOrEvil getGoodOrEvil() {
		return goodOrEvil;
	}	
	public void setGoodOrEvil(GoodOrEvil goodOrEvil) {
		this.goodOrEvil = goodOrEvil;
	}
	public Chassis getChassis() {
		return chassis;
	}
	public void setChassis(Chassis chassis) {
		this.chassis = chassis;
	}
	public boolean isDied(){
		return died;		
	}		
	public void setDied(boolean died){
		this.died = died;
	}
	public boolean isTouched() {
		return touched;
	}
	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	public int getColumnBitmap() {
		return columnBitmap;
	}
	public void setColumnBitmap(int columnBitmap) {
		this.columnBitmap = columnBitmap;
	}
	public int getRowBitmap() {
		return rowBitmap;
	}
	public void setRowBitmap(int rowBitmap) {
		this.rowBitmap = rowBitmap;
	}
	public boolean isFinishedTurning() {
		return finishedTurning;
	}
	public void setFinishedTurning(boolean finishedTurning) {
		this.finishedTurning = finishedTurning;
	}	
	
	
	public Velocity getVelocity(){
		return getChassis().getVelocity();
	}
	public void animate() {
		int rowNumber = bitmap.getHeight() / this.getHeight();
		int columnNumber = bitmap.getWidth() / this.getWidth();		
				
		columnBitmap++;
		if (columnBitmap >= columnNumber){			
			columnBitmap = 0;		
			rowBitmap++;
			if (rowBitmap >= rowNumber){
				rowBitmap = 0;
			}
		}	
		srcX = columnBitmap * width;
		srcY = rowBitmap * height;				
		
		Log.d(TAG, Integer.toString(columnBitmap) + Integer.toString(rowBitmap));
		Log.d(TAG, Integer.toString(width) + Integer.toString(height));
		Log.d(TAG, Integer.toString(x) + Integer.toString(y));
		
	}

}
