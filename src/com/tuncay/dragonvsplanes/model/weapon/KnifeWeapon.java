package com.tuncay.dragonvsplanes.model.weapon;

//import com.tuncay.dragonvsplanes.math.Vector2f;
import android.content.Context;

import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Weapon;

public class KnifeWeapon extends Weapon {
	
	public KnifeWeapon(){
		damage = 5.0f;
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
