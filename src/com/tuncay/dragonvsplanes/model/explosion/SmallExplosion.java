package com.tuncay.dragonvsplanes.model.explosion;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Explosion;

public class SmallExplosion extends Explosion{
	
	private static final String TAG = SmallExplosion.class.getSimpleName();

	public SmallExplosion (Context context, int x, int y){
		this.setId(nextId);
		nextId += 1;
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode1);
		this.setX(x);
		this.setY(y);
		this.width = this.bitmap.getWidth()/4;
		this.height = this.bitmap.getHeight()/8;
		this.age = 0;		
		this.setDied(false);	
		
		Log.d(TAG, "SmallExplosion is being created");	

	}

}
