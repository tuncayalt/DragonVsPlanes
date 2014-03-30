package com.tuncay.dragonvsplanes.model.weapon;

import java.util.Random;

import android.content.Context;

import com.tuncay.dragonvsplanes.MainGamePanel;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.abstracts.Weapon;
import com.tuncay.dragonvsplanes.model.bubble.CommanderBubble;
import com.tuncay.dragonvsplanes.model.bullet.SmallBullet;

public class BossWeapon extends Weapon{
	public BossWeapon(){
		damage = 70.0f;
		setRate(80);
		loaded = false;
	}

	public void UseWeapon(Context context, Bubble bubble1, Bubble bubble2) {
		MeleeAttack(bubble1, bubble2);
		MissileAttack(context, bubble1, bubble2);		
	}

	public String getDescription() {		
		return null;
	}

	@Override
	protected void MissileAttack(Context context, Bubble bubble1, Bubble bubble2) {
		if (loaded){			
			Random random = new Random();
			int i = random.nextInt(2);
			int x1 = bubble1.getX();
			int y1 = bubble1.getY();
			int x2 = bubble2.getX();
			int y2 = bubble2.getY();								
			if (i == 0){
				Bubble bubble = new CommanderBubble(context, x1, y1, bubble2);				
				MainGamePanel.BubbleList.add(bubble);
			}else{
				Bullet bullet = new SmallBullet(context, x1, y1, x2, y2);
				MainGamePanel.BulletList.add(bullet);
			}		
			
			this.loaded = false;
		}		
	}
}
