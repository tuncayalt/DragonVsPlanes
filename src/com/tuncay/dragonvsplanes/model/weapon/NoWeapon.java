package com.tuncay.dragonvsplanes.model.weapon;

//import com.tuncay.dragonvsplanes.math.Vector2f;
import android.content.Context;

import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Weapon;

public class NoWeapon extends Weapon{

	@Override
	public void UseWeapon(Context context, Bubble bubble1, Bubble bubble2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void MissileAttack(Context context, Bubble bubble1, Bubble bubble2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
