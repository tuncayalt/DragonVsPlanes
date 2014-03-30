package com.tuncay.dragonvsplanes.model.chassis;

import com.tuncay.dragonvsplanes.model.abstracts.Chassis;
import com.tuncay.dragonvsplanes.model.abstracts.Vehicle;
import com.tuncay.dragonvsplanes.model.components.Velocity;

public class NoChassis extends Chassis{

	public NoChassis(){
		speed = 0;
		this.setVelocity(new Velocity());
		guided = false;
	}

	@Override
	public String getDescription() {		
		return null;
	}

	@Override
	public void setVehicleVelocity(Vehicle vehicle, int x, int y) {
	
	}

	@Override
	public void MoveVehicle(Vehicle vehicle, int x, int y) {
		
	}


}
