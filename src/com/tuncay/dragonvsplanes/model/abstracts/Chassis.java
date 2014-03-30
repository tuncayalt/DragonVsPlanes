package com.tuncay.dragonvsplanes.model.abstracts;

import com.tuncay.dragonvsplanes.model.components.Velocity;

public abstract class Chassis {
	
	private Velocity velocity;
	
	protected float speed;
	public boolean guided;
	
	public Velocity getVelocity(){
		return this.velocity;
	}
	
	public void setVelocity(Velocity velocity){
		this.velocity = velocity;
	}
	
	public abstract void setVehicleVelocity(Vehicle vehicle, int x, int y);	
	
	/**
	 * Delegates the movement to the supporting chassis and
	 * tries to move the unit to x,y
	 */
	public abstract void MoveVehicle(Vehicle vehicle, int x, int y);

	/**
	 * Returns the description of the chassis
	 */	
	public abstract String getDescription();
	
	
}
