package com.tuncay.dragonvsplanes.model.bubble;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.armor.NoArmor;
import com.tuncay.dragonvsplanes.model.body.GoodBody;
import com.tuncay.dragonvsplanes.model.chassis.NoChassis;
import com.tuncay.dragonvsplanes.model.components.FlagType;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.components.SoundManager;
import com.tuncay.dragonvsplanes.model.interfaces.ITouchable;
import com.tuncay.dragonvsplanes.model.weapon.GoodWeapon;

public class GoodBubble extends Bubble implements ITouchable{
	
	Context context;
	
	private static final String TAG = GoodBubble.class.getSimpleName();
	
	List<FlagType> effectiveFlagList;
	List<FlagType> previousFlagList;
	
	//singleton constructor
	private GoodBubble(Context context, int x, int y, SoundManager soundManager){
		this.setId(nextId);
		nextId += 1;
		initialize(context, x, y, soundManager);		
		Log.d(TAG, "GoodBubble is being created");		
	}
	
	//singleton instance creator
	private static GoodBubble uniqueInstance;
	public static GoodBubble getInstance(Context context, int x, int y, SoundManager soundManager) {
		if (uniqueInstance == null) {
			uniqueInstance = new GoodBubble(context, x, y, soundManager);
		}
		return uniqueInstance;
	}
	
	public void initialize(Context context, int x, int y, SoundManager soundManager){
		this.context = context;
		setGoodOrEvil(GoodOrEvil.Good);		
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dragon);
		this.setX(x);
		this.setY(y);
		this.width = this.bitmap.getWidth()/10;
		this.height = this.bitmap.getHeight()/8;
		this.setTouched(false);
		this.setDied(false);
		this.setChassis(new NoChassis());
		this.setWeapon(new GoodWeapon(context, soundManager));	
		this.setBody(new GoodBody());
		this.setArmor(new NoArmor());
		this.effectiveFlagList = null;
		this.previousFlagList = null;
		this.setBoss(false);
		this.setRowBitmap(6);
		this.animate();
	}
	
	public void handleActionDown(int eventX, int eventY) {

		if 	((this.getGoodOrEvil() == GoodOrEvil.Good) &&
				(eventX >= (x - this.getWidth() / 2 - 20) && 
				(eventX <= (x + this.getWidth() / 2 + 20))) && 
				(eventY >= (y - this.getHeight() / 2 - 20) && 
				(eventY <= (y + this.getHeight() / 2 + 20)))) {
			// bubble touched
			setTouched(true);
		} 
		else {
			setTouched(false);
		}
		// dokunarak ates et
		if (!this.isTouched()){
			this.Turn(eventX, eventY);
			((GoodWeapon)this.getWeapon()).UseWeapon(context, this, eventX, eventY);			
		}		
	}		

	@Override
	public void display() {
				
	}

	public void handleActionMove(int eventX, int eventY) {
		if (this.isTouched()) {
			// the Bubble was picked up and is being dragged
			this.Turn(eventX, eventY);
			
			if (eventX < 0)
				eventX = 0;
			if (eventY < 0)
				eventY = 0;			
			this.setX(eventX);
			this.setY(eventY);
		}
		else{
			if (!this.isLongTouched()){
				this.Turn(eventX, eventY);
				((GoodWeapon)this.getWeapon()).UseWeapon(context, this, eventX, eventY);	
				
			}			
		}		
	}
	
	public void handleActionLongDown(int eventX, int eventY) {
		if 	((this.getGoodOrEvil() == GoodOrEvil.Good) &&
				(eventX >= (x - this.getWidth() / 2) && 
				(eventX <= (x + this.getWidth() / 2))) && 
				(eventY >= (y - this.getHeight() / 2) && 
				(eventY <= (y + this.getHeight() / 2)))) {
			// bubble touched
			setLongTouched(true);
		} 
		else {
			setLongTouched(false);
		}
		while (!this.isLongTouched()){
			((GoodWeapon)this.getWeapon()).UseWeapon(context, this, eventX, eventY);				
		}
	}

	@Override
	public void move(Bubble bubble, int x, int y) {	
		//initial upwards turning
		y = y - 100;
		super.move(bubble, x, y);
	}
	
	@Override
	public void animate() {
		//int rowNumber = bitmap.getHeight() / this.getHeight();
		int columnNumber = bitmap.getWidth() / this.getWidth();				
		
		columnBitmap++;
		if (columnBitmap >= columnNumber){			
			columnBitmap = 0;		
			/*rowBitmap++;
			if (rowBitmap >= rowNumber){
				rowBitmap = 0;
			}*/
		}	
		srcX = columnBitmap * width;
		srcY = rowBitmap * height;		
		
		/*Log.d(TAG, Integer.toString(columnBitmap) + Integer.toString(rowBitmap));
		Log.d(TAG, Integer.toString(width) + Integer.toString(height));
		Log.d(TAG, Integer.toString(x) + Integer.toString(y));*/		
	}
	@Override
	public void setArmorWithLevel(int gameLevel) {
		//do nothing
	}		
}
