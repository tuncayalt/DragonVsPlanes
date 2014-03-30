package com.tuncay.dragonvsplanes.model.components;

import android.content.Context;

import com.tuncay.dragonvsplanes.MainGamePanel;
import com.tuncay.dragonvsplanes.math.Tools;
import com.tuncay.dragonvsplanes.model.abstracts.Body;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.abstracts.Weapon;
import com.tuncay.dragonvsplanes.model.bullet.GoodBullet;

public class FlagAffection {
	
	
	public void doMultiShotFlagJob(Context context, int x1, int y1, int x2, int y2){
		if (MainGamePanel.FlagList.size() > 0){	
			boolean multiShot = false;			
			
			for (int i = 0; i < MainGamePanel.FlagList.size(); i++){					
				if (isEffectiveFlag(i, FlagType.MultiShot)){											
					multiShot = true;
				}								
			}			
			
			if (multiShot){					
				MultiShot(context, x1, y1, x2, y2);					
			}			
		}			
	}
	
	public void doRapidShotFlagJob(Weapon weapon){
		if (MainGamePanel.FlagList.size() > 0){				
			
			boolean rapidShot = false;
			
			for (int i = 0; i < MainGamePanel.FlagList.size(); i++){						
				if (isEffectiveFlag(i, FlagType.RapidShot)){
					rapidShot = true;
				}					
			}
			
			if (rapidShot){					
				RapidShot(weapon);					
			}
			else{
				RapidShotExpire(weapon);
			}			
		}			
	}
	
	public void doLifeDrainFlagJob(Bubble goodBubble, Body body){
		if (MainGamePanel.FlagList.size() > 0){				
			
			boolean lifeDrain = false;
			
			for (int i = 0; i < MainGamePanel.FlagList.size(); i++){						
				if (isEffectiveFlag(i, FlagType.LifeDrain)){
					lifeDrain = true;
				}					
			}			
			if (lifeDrain){					
				lifeDrain(goodBubble, body);					
			}				
		}			
	}
	
	private void lifeDrain(Bubble goodBubble, Body body) {		
		goodBubble.getBody().setLife(goodBubble.getBody().getLife() + (int)(body.getFullLife()/2));
		if (goodBubble.getBody().getLife() > goodBubble.getBody().getFullLife())
			goodBubble.getBody().setLife(goodBubble.getBody().getFullLife());
	}

	private boolean isEffectiveFlag(int i, FlagType flagType) {
		return (MainGamePanel.FlagList.get(i).getFlagType() == flagType) &&
			(MainGamePanel.FlagList.get(i).isEffective()) &&
			!(MainGamePanel.FlagList.get(i).isDied());
	}	
	
	private void MultiShot(Context context, int x1, int y1, int x2, int y2) {		
		int[] multiShotCoords = Tools.GetMultiShotCoords(x1, y1, x2, y2);		
		Bullet bullet1 = new GoodBullet(context, multiShotCoords[0], multiShotCoords[1], multiShotCoords[2], multiShotCoords[3]);		
		Bullet bullet2 = new GoodBullet(context, multiShotCoords[4], multiShotCoords[5], multiShotCoords[6], multiShotCoords[7]);
		MainGamePanel.BulletList.add(bullet1);		
		MainGamePanel.BulletList.add(bullet2);		
	}
	
	private void RapidShot(Weapon weapon) {		
		weapon.setRate(4);		
	}
	private void RapidShotExpire(Weapon weapon) {
		weapon.setRate(weapon.getOriginalRate());		
	}
}
