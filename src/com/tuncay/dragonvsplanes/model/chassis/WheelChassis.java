package com.tuncay.dragonvsplanes.model.chassis;

import com.tuncay.dragonvsplanes.math.Tools;
import com.tuncay.dragonvsplanes.model.abstracts.Bubble;
import com.tuncay.dragonvsplanes.model.abstracts.Chassis;
import com.tuncay.dragonvsplanes.model.abstracts.Vehicle;
import com.tuncay.dragonvsplanes.model.components.GoodOrEvil;
import com.tuncay.dragonvsplanes.model.components.Velocity;

public class WheelChassis extends Chassis{
	public WheelChassis(Bubble bubble, int x, int y){
		speed = 8.0f;		
		guided = false;
		setVehicleVelocity(bubble,x,y);		
	}		
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVehicleVelocity(Vehicle vehicle, int x, int y) {
		Velocity v;
		v = Tools.getVelocityFromSpeed(speed, vehicle.getX(), vehicle.getY(), x, y);		
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
		
		if (!vehicle.isTouched() || vehicle.getGoodOrEvil() == GoodOrEvil.Evil ) {		
			int a = (int) (v.getXv() * v.getxDirection()); 
			int b = (int) (v.getYv() * v.getyDirection());
			vehicle.setX(vehicle.getX() + a);
			vehicle.setY(vehicle.getY() + b);			
		}		
	}	
}
