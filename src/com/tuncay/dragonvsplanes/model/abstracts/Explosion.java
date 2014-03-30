package com.tuncay.dragonvsplanes.model.abstracts;

import com.tuncay.dragonvsplanes.MainGamePanel;
import com.tuncay.dragonvsplanes.model.components.SoundManager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Explosion {
	
	
	protected static int nextId = 0;    // the next available ID
	private int id;
	protected Bitmap bitmap;
	
	private int x;
	private int y;
	protected int width;	
	protected int height;
	protected int columnBitmap;	
	protected int rowBitmap;
	protected int srcX;
	protected int srcY;
	protected int age;
	
	private boolean died;		
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
		
	
	
	public void update(Bubble bubble, SoundManager soundManager) {
		age ++;					
		if (age == 1){		
			soundManager.playPoolSound(3, false);
		}
		Animate();		
		if (isDied()){
			die();					
		}
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
	public void Animate() {
		
		int rowNumber = bitmap.getHeight() / this.height;
		int columnNumber = bitmap.getWidth() / this.width;	
		
		columnBitmap++;		
		if (columnBitmap >= columnNumber){			
			columnBitmap = 0;			
			rowBitmap++;
			if (rowBitmap >= rowNumber){
				this.setDied(true);
			}
		}			
		
		srcX = columnBitmap * width;
		srcY = rowBitmap * height;				
		
		/*Log.d(TAG, Integer.toString(columnBitmap) + Integer.toString(rowBitmap));
		Log.d(TAG, Integer.toString(width) + Integer.toString(height));
		Log.d(TAG, Integer.toString(x) + Integer.toString(y));*/	
	}
	public void draw(Canvas canvas) {
		Rect src = new Rect(srcX, srcY, width + srcX, height + srcY);
		Rect dst = new Rect(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
		canvas.drawBitmap(bitmap, src, dst, null);
		//.drawBitmap(bitmap, x - (this.getWidth() / 2), y - (this.getHeight() / 2), null);
	}

	public boolean isDied() {
		return died;
	}

	public void setDied(boolean died) {
		this.died = died;
	}

	public void die() {
		MainGamePanel.ExplosionList.remove(this);
	}
}
