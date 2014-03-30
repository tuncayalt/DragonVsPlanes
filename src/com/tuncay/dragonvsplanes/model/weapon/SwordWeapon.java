package com.tuncay.dragonvsplanes.model.weapon;

import android.content.Context;

import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Weapon;

public class SwordWeapon extends Weapon{
	public SwordWeapon(){
		damage = 20.0f;
	}	
	
	public void UseWeapon(Context context, Bubble bubble1, Bubble bubble2) {
		MeleeAttack(bubble1, bubble2);		
	}	

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void MissileAttack(Context context, Bubble bubble1, Bubble bubble2) {
		// TODO Auto-generated method stub		
	}
}
