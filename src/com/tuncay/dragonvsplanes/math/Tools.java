package com.tuncay.dragonvsplanes.math;

import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.abstracts.Flag;
import com.tuncay.dragonvsplanes.model.abstracts.Vehicle;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.components.Velocity;
import com.tuncay.dragonvsplanes.parameter.Parameter;

public class Tools {
	
	//private static final String TAG = Tools.class.getSimpleName();
	
	public static boolean paused;
	
	public static int points;
	
	public static int getRadius (Bubble bubble){		
		return (int)((bubble.getHeight()/2) + (bubble.getWidth()/2)) / 2;			
	}
	public static int getRadius (Bullet bullet){		
		return (int)((bullet.getHeight()/2) + (bullet.getWidth()/2)) / 2;			
	}
	public static int getRadius (Flag flag) {
		return (int)((flag.getHeight()/2) + (flag.getWidth()/2)) / 2;	
	}
	
	public static double getDistanceBubble (Bubble bubble1, Bubble bubble2){

		double centerDistance = Math.sqrt(((bubble1.getX() - bubble2.getX())*(bubble1.getX() - bubble2.getX()))+
							   ((bubble1.getY() - bubble2.getY())*(bubble1.getY() - bubble2.getY())));
		return centerDistance - getRadius(bubble1) - getRadius(bubble2);		
	}
	
	public static double getDistanceBullet (Bullet bullet, Bubble bubble){

		double centerDistance = Math.sqrt(((bullet.getX() - bubble.getX())*(bullet.getX() - bubble.getX()))+
							   ((bullet.getY() - bubble.getY())*(bullet.getY() - bubble.getY())));
		
		if (bubble.getGoodOrEvil()==GoodOrEvil.Good){
			return centerDistance - getRadius(bubble);
		}else{
			return centerDistance - getRadius(bubble) - getRadius(bullet);
		}		
	}
	
	public static double getDistanceFlag (Flag flag, Bubble bubble){

		double centerDistance = Math.sqrt(((flag.getX() - bubble.getX())*(flag.getX() - bubble.getX()))+
							   ((flag.getY() - bubble.getY())*(flag.getY() - bubble.getY())));
		return centerDistance - getRadius(flag) - getRadius(bubble);		
	}
	
	
	public static Velocity getVelocityFromSpeed(float speed, int x1, int y1, int x2, int y2) {
				
		int xv = (int) Math.sqrt(speed * speed - (speed * speed * (y2-y1)*(y2-y1)) / ((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
		//int yv = (int) Math.sqrt(speed * speed - xv * xv);
		int yv = (int) Math.sqrt(speed * speed - (speed * speed * (x2-x1)*(x2-x1)) / ((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
		
		Velocity velocity = new Velocity (xv,yv);
		if (x1 > x2){
			velocity.toggleXDirection();
		}
		if (y1 > y2){
			velocity.toggleYDirection();
		}			
		return velocity;
	}
	public static int[] GetMultiShotCoords(int x1, int y1, int x2, int y2) {
						
		double theta = Math.atan2(y2-y1,x2-x1);		
		double newAlfa1 = theta - Math.PI/2;		
		double newAlfa2 = theta + Math.PI/2;		
		int r = Parameter.multiShotRadius;		
		int[] coords = new int[8];		
		
		coords[0] = x1 + (int) (Math.cos(newAlfa1) * r);		
		coords[1] = y1 + (int) (Math.sin(newAlfa1) * r);		
		coords[2] = x2 + (int) (Math.cos(newAlfa1) * r);
		coords[3] = y2 + (int) (Math.sin(newAlfa1) * r);
		coords[4] = x1 + (int) (Math.cos(newAlfa2) * r);
		coords[5] = y1 + (int) (Math.sin(newAlfa2) * r);
		coords[6] = x2 + (int) (Math.cos(newAlfa2) * r);
		coords[7] = y2 + (int) (Math.sin(newAlfa2) * r);
		
		return coords;			
	}
	public static double GetMovingAngleOfVehicle(Vehicle vehicle){
		float Xv = vehicle.getVelocity().getXv();
		float Yv = vehicle.getVelocity().getYv();
		double angle = Math.atan2(Yv, Xv);		
		return angle;
	}
	public static double GetAngleFromCoords(int x1, int y1, int x2, int y2){
		return Math.atan2(y2 - y1, x2 - x1);
	}
	
	
}
