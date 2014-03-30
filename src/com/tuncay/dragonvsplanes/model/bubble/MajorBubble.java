package com.tuncay.dragonvsplanes.model.bubble;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.body.MediumBody;
import com.tuncay.dragonvsplanes.model.chassis.GuidedFeetChassis;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.weapon.SwordWeapon;

public class MajorBubble extends Bubble{
	private static final String TAG = MajorBubble.class.getSimpleName();

	public MajorBubble(Context context, int x, int y, Bubble hedefBubble){
		setGoodOrEvil(GoodOrEvil.Evil);
		this.setId(nextId);
		nextId += 1;
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aircraft23);
		this.setX(x);
		this.setY(y);
		this.width = this.bitmap.getWidth()/4;
		this.height = this.bitmap.getHeight()/8;
		this.setPrize(35);
		this.setTouched(false);
		this.setDied(false);
		this.setChassis(new GuidedFeetChassis(this, hedefBubble.getX(), hedefBubble.getY()));
		this.setWeapon(new SwordWeapon());	
		this.setBody(new MediumBody());
		this.setBoss(false);
		
		Log.d(TAG, "MajorBubble is being created");		
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
			if (columnBitmap >= columnNumber / 2){			
				columnBitmap = 0;			
			}	
		}else{
			if (columnBitmap >= columnNumber){			
				columnBitmap = columnNumber / 2;			
			}	
		}		
		
		srcX = columnBitmap * width;
		srcY = rowBitmap * height;				
		
		/*Log.d(TAG, Integer.toString(columnBitmap) + Integer.toString(rowBitmap));
		Log.d(TAG, Integer.toString(width) + Integer.toString(height));
		Log.d(TAG, Integer.toString(x) + Integer.toString(y));*/	
	}
}
