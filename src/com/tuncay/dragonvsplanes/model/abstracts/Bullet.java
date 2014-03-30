package com.tuncay.dragonvsplanes.model.abstracts;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.tuncay.dragonvsplanes.MainGamePanel;
import com.tuncay.dragonvsplanes.math.Tools;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.components.Velocity;

//import com.tuncay.dragonvsplanes.model.components.Velocity;

public abstract class Bullet extends Vehicle{

	protected static final String TAG = Bullet.class.getSimpleName();
		
	private float damage;
	
	public float getDamage() {
		return damage;
	}
	public void setDamage(float damage) {
		this.damage = damage;
	}

	public void ApplyDamage(Bullet bullet, Bubble bubble){
		if (Tools.getDistanceBullet(bullet, bubble) <= 0){
			if ((bullet.getGoodOrEvil() == GoodOrEvil.Good && bubble.getGoodOrEvil() == GoodOrEvil.Evil)||
					(bullet.getGoodOrEvil() == GoodOrEvil.Evil && bubble.getGoodOrEvil() == GoodOrEvil.Good)){
				//if bubble and bullet are touched, bullet dies, bubble takes damage
				//bubble and bullet are (good and evil) or vice versa
				bullet.setDied(true);

				float life = bubble.getBody().getLife();
				float armorPoint = bubble.getArmor().getArmorPoint();
				life = life - damage / armorPoint;
				bubble.getBody().setLife(life);
				if (life <= 0){
					bubble.setDied(true);
				}
			}
		}
	}
	
	public void draw(Canvas canvas) {
		Rect src = new Rect(srcX, srcY, width + srcX, height + srcY);
		Rect dst = new Rect(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
		//canvas.drawBitmap(bitmap, x - (this.getWidth() / 2), y - (this.getHeight() / 2), null);
		canvas.drawBitmap(bitmap, src, dst, null);
	}
	public void update(View view, Bubble goodBubble) {
		BulletCollisionCheck(view);		
		this.getChassis().MoveVehicle(this, goodBubble.getX(), goodBubble.getY());
		this.animate();
		
		damageBubbles();
		
		if (isDied()){					
			die();											
		}
	}
	private void BulletCollisionCheck(View view) {
		if (this.getChassis().getVelocity().getxDirection() == Velocity.DIRECTION_RIGHT
				&& this.getX() + this.getWidth() / 2 >= view.getWidth()) {
			this.setDied(true);
		}
		// check collision with left wall if heading left
		if (this.getChassis().getVelocity().getxDirection() == Velocity.DIRECTION_LEFT
				&& this.getX() - this.getWidth() / 2 <= 0) {
			this.setDied(true);
		}
		// check collision with bottom wall if heading down
		if (this.getChassis().getVelocity().getyDirection() == Velocity.DIRECTION_DOWN
				&& this.getY() + this.getHeight() / 2 >= view.getHeight()) {
			this.setDied(true);
		}
		// check collision with top wall if heading up
		if (this.getChassis().getVelocity().getyDirection() == Velocity.DIRECTION_UP
				&& this.getY() - this.getHeight() / 2 <= 0) {
			this.setDied(true);
		}	
	}
	public void die() {
		MainGamePanel.BulletList.remove(this);
		Log.d(TAG, "bullet is dead");
	}
	public void damageBubbles() {
		for (int j = 0; j < MainGamePanel.BubbleList.size(); j++){
			ApplyDamage(this, MainGamePanel.BubbleList.get(j));
		}
	}



	

}
