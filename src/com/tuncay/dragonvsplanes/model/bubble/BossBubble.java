package com.tuncay.dragonvsplanes.model.bubble;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.body.BossBody;
import com.tuncay.dragonvsplanes.model.chassis.BossChassis;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.weapon.BossWeapon;

public class BossBubble extends Bubble{
	private static final String TAG = BossBubble.class.getSimpleName();

	//singleton constructor
	private BossBubble(Context context, int x, int y, Bubble hedefBubble){
		setGoodOrEvil(GoodOrEvil.Evil);
		this.setId(nextId);
		nextId += 1;		
		initialize(context, x, y, hedefBubble);
		
		/*Random random = new Random();
		int hedefX = x + random.nextInt(100) - 100;
		int hedefY = y + random.nextInt(100) - 100;
		this.setChassis(new BossChassis(this, hedefX, hedefY));*/

		Log.d(TAG, "BossBubble is being created");		
	}

	//singleton instance creator
	private static BossBubble uniqueInstance;
	public static BossBubble getInstance(Context context, int x, int y, Bubble hedefBubble) {
		if (uniqueInstance == null) {
			uniqueInstance = new BossBubble(context, x, y, hedefBubble);
		}
		return uniqueInstance;
	}	
	
	public void initialize(Context context, int x, int y, Bubble hedefBubble){
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss1);
		this.setX(x);
		this.setY(y);
		this.width = this.bitmap.getWidth()/3;
		this.height = this.bitmap.getHeight();
		this.setPrize(500);
		this.setTouched(false);
		this.setDied(false);		
		this.setChassis(new BossChassis(this, hedefBubble.getX(), hedefBubble.getY()));
		this.setWeapon(new BossWeapon());	
		this.setBody(new BossBody());
		this.setBoss(true);
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
		if ((getBody().getFullLife()*2)/3 < getBody().getLife()){			
			if (columnBitmap >= columnNumber / 3){			
				columnBitmap = 0;			
			}	
		}else if(getBody().getFullLife()/3 < getBody().getLife()){
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
	@Override
	public void Turn(int eventX, int eventY) {
		rowBitmap = 0;
	}
	@Override
	public void TurnAfterVerticalCollision() {
		rowBitmap = 0;
	}
	@Override
	public void TurnAfterHorizontalCollision() {
		rowBitmap = 0;	
	} 
}
