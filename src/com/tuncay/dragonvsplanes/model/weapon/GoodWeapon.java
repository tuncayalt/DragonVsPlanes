package com.tuncay.dragonvsplanes.model.weapon;

import android.content.Context;

import com.tuncay.dragonvsplanes.MainGamePanel;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.abstracts.Weapon;
import com.tuncay.dragonvsplanes.model.bullet.GoodBullet;
import com.tuncay.dragonvsplanes.model.components.FlagAffection;
import com.tuncay.dragonvsplanes.model.components.SoundManager;

public class GoodWeapon extends Weapon{
	
	//private static final String TAG = GoodWeapon.class.getSimpleName();
	int previousRate;
	SoundManager soundManager;
	
	public GoodWeapon(Context context, SoundManager soundManager){
		damage = 5.0f;
		setRate(10);		
		originalRate = getRate();
		loaded = false;		
		this.soundManager = soundManager;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Bullet MissileAttack(Context context, Bubble bubble1, int x2, int y2) {
		if (loaded){		
			CalculateOffsetCoords(bubble1.getRowBitmap());
			int x1 = bubble1.getX() + offsetX * bubble1.getWidth() / 2;
			int y1 = bubble1.getY() + offsetY * bubble1.getHeight() / 2;
			/*int x2 = bubble2.getX();
			int y2 = bubble2.getY();*/					
			
			Bullet bullet = new GoodBullet(context, x1, y1, x2, y2);	
						
			soundManager.playPoolSound(1,false);
			
			FlagAffection flagAffection = new FlagAffection();
			
			flagAffection.doMultiShotFlagJob(context, x1, y1, x2, y2);
			flagAffection.doRapidShotFlagJob(this);
			
			this.loaded = false;
			return bullet;
		}
		return null;
				
	}

	private void CalculateOffsetCoords(int rowBitmap) {
		switch (rowBitmap){
		case 0:
			offsetX = 1;
			offsetY = 0;
			break;
		case 1:
			offsetX = 1;
			offsetY = 1;
			break;
		case 2:
			offsetX = 0;
			offsetY = 1;
			break;
		case 3:
			offsetX = -1;
			offsetY = 1;
			break;
		case 4:
			offsetX = -1;
			offsetY = 0;
			break;
		case 5:
			offsetX = -1;
			offsetY = -1;
			break;
		case 6:
			offsetX = 0;
			offsetY = -1;
			break;
		case 7:
			offsetX = 1;
			offsetY = -1;
			break;			
		}
	}	

	@Override
	protected void MissileAttack(Context context, Bubble bubble1, Bubble bubble2) {
		// do nothing, used above
	}

	
	public void UseWeapon(Context context, Bubble bubble1, int x2, int y2) {
		Bullet bullet = MissileAttack(context, bubble1, x2, y2);
		if (bullet != null)
			MainGamePanel.BulletList.add(bullet);
	}
	
	@Override
	public void UseWeapon(Context context, Bubble bubble1, Bubble bubble2) {
		// do nothing
	}

	
}
