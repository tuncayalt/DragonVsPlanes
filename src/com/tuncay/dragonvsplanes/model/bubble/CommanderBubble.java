package com.tuncay.dragonvsplanes.model.bubble;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.body.BigBody;
import com.tuncay.dragonvsplanes.model.chassis.FeetChassis;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.weapon.StoneWeapon;

public class CommanderBubble extends Bubble {
	private static final String TAG = CommanderBubble.class.getSimpleName();

	public CommanderBubble(Context context, int x, int y, Bubble hedefBubble){
		setGoodOrEvil(GoodOrEvil.Evil);
		this.setId(nextId);
		nextId += 1;
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aircraft31);
		this.setX(x);
		this.setY(y);
		this.width = this.bitmap.getWidth()/3;
		this.height = this.bitmap.getHeight()/8;
		this.setPrize(40);
		this.setTouched(false);
		this.setDied(false);
		this.setChassis(new FeetChassis(this, hedefBubble.getX(), hedefBubble.getY()));
		this.setWeapon(new StoneWeapon());	
		this.setBody(new BigBody());
		this.setBoss(false);
		
		Log.d(TAG, "CommanderBubble is being created");		
	}	

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void animate() {
		//first two images are normal, last two are burning
		
		//int rowNumber = bitmap.getHeight() / this.getHeight();
		int columnNumber = bitmap.getWidth() / this.getWidth();		
		columnBitmap++;
		if (getBody().getFullLife() == getBody().getLife()){			
			if (columnBitmap >= columnNumber / 3){			
				columnBitmap = 0;			
			}	
		}else if(getBody().getFullLife()/2 < getBody().getLife()){
			if (columnBitmap >= (columnNumber / 3) * 2){			
				columnBitmap = columnNumber / 3;			
			}	
		}else{
			if (columnBitmap >= columnNumber){			
				columnBitmap = (columnNumber / 3) * 2;			
			}	
		}
		
		srcX = columnBitmap * width;
		srcY = rowBitmap * height;				
		
		/*Log.d(TAG, Integer.toString(columnBitmap) + Integer.toString(rowBitmap));
		Log.d(TAG, Integer.toString(width) + Integer.toString(height));
		Log.d(TAG, Integer.toString(x) + Integer.toString(y));*/	
	}
}
