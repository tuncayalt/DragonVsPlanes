package com.tuncay.dragonvsplanes.model.abstracts;

import java.util.Random;

import com.tuncay.dragonvsplanes.MainGamePanel;
import com.tuncay.dragonvsplanes.math.Tools;
import com.tuncay.dragonvsplanes.model.armor.HeavyArmor;
import com.tuncay.dragonvsplanes.model.armor.LightArmor;
import com.tuncay.dragonvsplanes.model.armor.MediumArmor;
import com.tuncay.dragonvsplanes.model.armor.NoArmor;
import com.tuncay.dragonvsplanes.model.components.FlagAffection;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.components.Velocity;
import com.tuncay.dragonvsplanes.model.explosion.BigExplosion;
import com.tuncay.dragonvsplanes.model.explosion.SmallExplosion;
import com.tuncay.dragonvsplanes.model.flag.LifeDrainFlag;
import com.tuncay.dragonvsplanes.model.flag.MultiShotFlag;
import com.tuncay.dragonvsplanes.model.flag.RapidShotFlag;
import com.tuncay.dragonvsplanes.parameter.Parameter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public abstract class Bubble extends Vehicle {
	
	private static final String TAG = Bubble.class.getSimpleName();
		
	private int age = 0;
	private int rateAge = 0;
		
	private Weapon weapon;
	private Body body;	
	private Armor armor;
	
	
	private boolean longTouched;
	private boolean Boss;
		
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}	
	public Body getBody() {
		return body;
	}	
	public void setBody(Body body) {
		this.body = body;
	}	
	public Armor getArmor() {
		return armor;
	}
	public void setArmor(Armor armor) {
		this.armor = armor;
	} 
	public boolean isBoss() {
		return Boss;
	}
	public void setBoss(boolean boss) {
		Boss = boss;
	}
		
	/*public void moveToPosition(int x, int y) {
		System.out.print(id + " > " );
		chassis.MoveBubble(float speed, Bubble bubble, int x, int y, boolean guided);
	}*/
	
	/**
	 *  Engages the position on the screen whether it is occupied by
	 *  an enemy or not. Each strategy should decide how to do it.
	 */
	public void attackPosition(int x, int y) {
		//System.out.print(id + " > ");
		//weapon.UseWeapon(new Vector2f(x, y));
	}		
	
	public void update(View view, MainGamePanel mainGamePanel, Context context, Bubble goodBubble, int gameLevel) {		
		Log.d(TAG, "update start");
				
		bubbleCollisionCheck(view);
		
		setArmorWithLevel(gameLevel);
		
		age++;
		
		rateAge++;
		
		loadWeapon();		

		animate();
		
		shoot(context, goodBubble);		
		
		move(this, goodBubble.getX(), goodBubble.getY());
		
		if (isDied()){
			die(mainGamePanel, goodBubble);
		}
		
	}
	
	private void shoot(Context context, Bubble goodBubble) {
		if (this.getGoodOrEvil() == GoodOrEvil.Evil){
			this.UseWeapon(context, this, goodBubble);	
			Log.d(TAG, "shooting finished");
		}
	}
	private void loadWeapon() {
		if (rateAge >= this.getWeapon().getRate()){
			this.getWeapon().loaded = true;
			rateAge = 0;
			Log.d(TAG, "loading finish");
		}
		
	}	
	
	public void draw(Canvas canvas) {
		Rect src = new Rect(srcX, srcY, width + srcX, height + srcY);
		Rect dst = new Rect(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
		canvas.drawBitmap(bitmap, src, dst, null);
		//.drawBitmap(bitmap, x - (this.getWidth() / 2), y - (this.getHeight() / 2), null);
	}
	
	public void bubbleCollisionCheck(View view) {
		if (getGoodOrEvil() == GoodOrEvil.Evil){			
			if (this.getChassis().getVelocity().getxDirection() == Velocity.DIRECTION_RIGHT
					&& this.getX() + this.getWidth() / 2 >= view.getWidth()) {
				this.getChassis().getVelocity().toggleXDirection();
				this.TurnAfterVerticalCollision();				
			}			
			// check collision with left wall if heading left
			if (this.getChassis().getVelocity().getxDirection() == Velocity.DIRECTION_LEFT
					&& this.getX() - this.getWidth() / 2 <= 0) {
				this.getChassis().getVelocity().toggleXDirection();
				this.TurnAfterVerticalCollision();				
			}			
			// check collision with bottom wall if heading down
			if (getChassis().getVelocity().getyDirection() == Velocity.DIRECTION_DOWN
					&& getY() + this.getHeight() / 2 >= view.getHeight()) {
				this.getChassis().getVelocity().toggleYDirection();
				this.TurnAfterHorizontalCollision();				
			}			
			// check collision with top wall if heading up
			if (this.getChassis().getVelocity().getyDirection() == Velocity.DIRECTION_UP
					&& this.getY() - this.getHeight() / 2 <= 0) {
				this.getChassis().getVelocity().toggleYDirection();
				this.TurnAfterHorizontalCollision();				
			}			
		}		
	}
	
	/**
	 * Displays some info on the bubble
	 */
	public abstract void display();
	public boolean isLongTouched() {
		return longTouched;
	}
	public void setLongTouched(boolean longTouched) {
		this.longTouched = longTouched;
	}
	public void UseWeapon(Context context, Bubble bubble, Bubble goodBubble) {
		getWeapon().UseWeapon(context, this, goodBubble);		
	}
	public void move(Bubble bubble, int x, int y) {	
		if (!isFinishedTurning()){
			bubble.Turn(x, y);
			if (!getChassis().guided){
				setFinishedTurning(true);
			}
		}		
		getChassis().MoveVehicle(this, x, y);
		Log.d(TAG, "moving finished");
	}
	/*public void RotateToShootingTarget(int x1, int y1, int x2, int y2) {
		Log.d(TAG, "rotating 01");
		double angle = Tools.GetAngleFromCoords(x1, y1, x2, y2)/Math.PI*180+90;
		Log.d(TAG, "rotating 02");
		Matrix matrix = new Matrix();
		Log.d(TAG, "rotating 03");
		RectF rectf = new RectF(x1-width/2, y1-height/2, x1 + width/2, y1 + height/2);
		Log.d(TAG, "rotating 04");
		matrix.mapRect(rectf);
		Log.d(TAG, "rotating 05");
		matrix.setRotate((float) angle, x1, y1);		
		Log.d(TAG, "rotating 06");
		Bitmap tempBitmap = Bitmap.createBitmap(this.bitmap, srcX, srcY, width, height);
		this.bitmap = Bitmap.createBitmap(tempBitmap, 0, 0, this.width, this.height, matrix, true);
		Log.d(TAG, "rotating 07");
	}	*/
	
	public void Turn(int eventX, int eventY) {
		double angle = Math.atan2(this.y - eventY, eventX - this.x);
		rowBitmap = (8 - Math.round((float)(4 * angle / Math.PI))) % 8;
	}
	
	public void TurnAfterVerticalCollision() {
		if (rowBitmap == 2 || rowBitmap == 6)
			return;
		if (rowBitmap <= 4){
			rowBitmap = 4 - rowBitmap;
		}else{
			rowBitmap = 12 - rowBitmap;
		}
	}
	
	public void TurnAfterHorizontalCollision() {
		if (rowBitmap == 0 || rowBitmap == 4)
			return;		
		rowBitmap = 8 - rowBitmap;		
	}
	public void setArmorWithLevel(int gameLevel) {
		int gameTurn = gameLevel / Parameter.bossTurn;
		switch(gameTurn){
		case 0:
			this.setArmor(new NoArmor());
			break;
		case 1:
			this.setArmor(new LightArmor());
			break;
		case 2:
			this.setArmor(new MediumArmor());
			break;
		default:
			this.setArmor(new HeavyArmor());
			break;			
		}		
	}
	
	public void die(MainGamePanel mainGamePanel, Bubble goodBubble) {		
		givePoints();
		createFlag(mainGamePanel);
		createExplosion(mainGamePanel);
		drainLife(goodBubble, getBody());
		MainGamePanel.BubbleList.remove(this);
		Log.d(TAG, "Bubble is dead");
	}
	
	private void drainLife(Bubble goodBubble, Body body) {
		FlagAffection flagAffection = new FlagAffection();
		flagAffection.doLifeDrainFlagJob(goodBubble, body);
	}
	
	public void createExplosion(MainGamePanel mainGamePanel) {		
		Explosion explosion;
		if (isBoss()){
			explosion = new BigExplosion(mainGamePanel.getContext(), getX(), getY());
		}else{
			explosion = new SmallExplosion(mainGamePanel.getContext(), getX(), getY());
		}
		
		MainGamePanel.ExplosionList.add(explosion);
	}
	public void createFlag(MainGamePanel mainGamePanel) {
		Random random = new Random();
		int randomInt = random.nextInt(1200);
		if (randomInt <= Parameter.flagCreationProbability / 3){			
			Flag flag = new MultiShotFlag(mainGamePanel.getContext(), getX(), getY());
			MainGamePanel.FlagList.add(flag);
		}
		else if (randomInt <= (2 * Parameter.flagCreationProbability) / 3){
			Flag flag = new RapidShotFlag(mainGamePanel.getContext(), getX(), getY());
			MainGamePanel.FlagList.add(flag);
		}
		else if (randomInt <= Parameter.flagCreationProbability){
			Flag flag = new LifeDrainFlag(mainGamePanel.getContext(), getX(), getY());
			MainGamePanel.FlagList.add(flag);
		}
		else{
			return;
		}
	}
	public void givePoints() {
		Tools.points += getPrize();		
	}
	
}
