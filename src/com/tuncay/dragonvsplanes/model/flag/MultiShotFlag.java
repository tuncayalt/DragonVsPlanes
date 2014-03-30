package com.tuncay.dragonvsplanes.model.flag;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tuncay.dragonvsplanes.R;
import com.tuncay.dragonvsplanes.model.abstracts.Flag;
import com.tuncay.dragonvsplanes.model.components.FlagType;

public class MultiShotFlag extends Flag{
	
	private static final String TAG = MultiShotFlag.class.getSimpleName();
	
	public MultiShotFlag (Context context, int x, int y){
		this.setId(nextId);
		nextId += 1;
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.flag3);
		this.setX(x);
		this.setY(y);
		this.age = 0;
		this.effectiveAge = 0;
		this.nonEffectiveAge = 0;
		this.died = false;
		this.effective = false;
		this.previous = false;
		this.setPrize(50);
		
		this.flagType = FlagType.MultiShot;
		
		Log.d(TAG, "MultiShotFlag is being created");	
	}
}
