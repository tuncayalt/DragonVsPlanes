package com.tuncay.dragonvsplanes.model.abstracts;

//import com.tuncay.dragonvsplanes.math.Vector2f;
import android.content.Context;

import com.tuncay.dragonvsplanes.math.Tools;

public abstract class Weapon {
	
	protected float damage;
	protected boolean loaded;
	private int rate;
	protected int originalRate;	
	

	protected int offsetX;
	protected int offsetY;
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getOriginalRate() {
		return originalRate;
	}

	public void setOriginalRate(int originalRate) {
		this.originalRate = originalRate;
	}

	/**
	 * The trigger to use the weapon.
	 * @param target - where on the map is the target
	 */
	public abstract void UseWeapon(Context context, Bubble bubble1, Bubble bubble2);	
	
	protected void MeleeAttack(Bubble bubble1, Bubble bubble2) {
		if (Tools.getDistanceBubble(bubble1, bubble2) <= 0){
			//if two bubbles are touched, first one dies, second one takes damage
			bubble1.setDied(true);
			
			float life = bubble2.getBody().getLife();
			float armorPoint = bubble2.getArmor().getArmorPoint();
			life = life - damage / armorPoint;
			bubble2.getBody().setLife(life);
			if (life < 0){
				bubble2.setDied(true);
			}
		}
	}
	
  	protected abstract void MissileAttack(Context context, Bubble bubble1, Bubble bubble2);	  	
	
	/**
	 * Returns the description of the weapon
	 */
	public abstract String getDescription();	
	
}
