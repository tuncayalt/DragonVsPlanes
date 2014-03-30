package com.tuncay.dragonvsplanes.model.chassis;

import com.tuncay.dragonvsplanes.math.Tools;
import com.tuncay.dragonvsplanes.model.abstracts.Bullet;
import com.tuncay.dragonvsplanes.model.abstracts.Chassis;
import com.tuncay.dragonvsplanes.model.abstracts.Vehicle;
import com.tuncay.dragonvsplanes.model.components.Velocity;

public class GoodBulletChassis extends Chassis {

	public GoodBulletChassis(Bullet bullet, int x, int y){
		speed = 50.0f;				
		setVehicleVelocity(bullet,x,y);		
	}		

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVehicleVelocity(Vehicle vehicle, int x, int y) {
		Velocity v;
		v = Tools.getVelocityFromSpeed(speed, vehicle.getX(),vehicle.getY(), x, y);		
		this.setVelocity(v);			
	}

	@Override
	public void MoveVehicle(Vehicle vehicle, int x, int y) {
		Velocity v;
		if (guided){
			v = Tools.getVelocityFromSpeed(speed, vehicle.getX(), vehicle.getY(), x, y);
		}
		else{
			v = this.getVelocity();			
		}		
			
		int a = (int) (v.getXv() * v.getxDirection()); 
		int b = (int) (v.getYv() * v.getyDirection());
		vehicle.setX(vehicle.getX() + a);
		vehicle.setY(vehicle.getY() + b);			
	}

	
}
