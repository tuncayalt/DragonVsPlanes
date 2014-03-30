package com.tuncay.dragonvsplanes.model.bullet;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.chassis.SlowBulletChassis;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;

public class SmallBullet extends Bullet{	
	
	public SmallBullet(Context context, int x, int y, int hedefX, int hedefY ){
		setDamage(5.0f);		
		this.setId(nextId);
		nextId += 1;		
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red4);
		this.x = x;
		this.y = y;
		this.width = this.bitmap.getWidth();
		this.height = this.bitmap.getHeight();
		this.setGoodOrEvil(GoodOrEvil.Evil);
		this.setDied(false);
		this.setChassis(new SlowBulletChassis(this, hedefX, hedefY));
		Log.d(TAG, "small bullet is being created");
	}
	

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (this.getWidth() / 2), y - (this.getHeight() / 2), null);
		
	}
	
}


