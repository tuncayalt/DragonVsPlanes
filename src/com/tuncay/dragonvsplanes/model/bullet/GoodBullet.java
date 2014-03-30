package com.tuncay.dragonvsplanes.model.bullet;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.chassis.GoodBulletChassis;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;

public class GoodBullet extends Bullet {
	
	private static final String TAG = GoodBullet.class.getSimpleName();

	public GoodBullet(Context context, int x, int y, int hedefX, int hedefY ){
		setDamage(5.0f);		
		this.setId(nextId);
		nextId += 1;		
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball1);
		this.x = x;
		this.y = y;
		this.width = this.bitmap.getWidth()/3;
		this.height = this.bitmap.getHeight();
		this.setGoodOrEvil(GoodOrEvil.Good);
		this.setDied(false);
		this.setChassis(new GoodBulletChassis(this, hedefX, hedefY));
		/*this.setSoundManager(new SoundManager());
		getSoundManager().initSounds(context);
		getSoundManager().addSound(1, R.raw.whip);*/
		
				
		Log.d(TAG, "good bullet is being created");		
	}
	

	/*@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (this.getWidth() / 2), y - (this.getHeight() / 2), null);		
	}*/
}
