package com.tuncay.dragonvsplanes.model.abstracts;

public abstract class Body {
	private float life;	
	private float fullLife;
		
	public float getLife(){
		return this.life;
	}
	
	public void setLife(float life){
		this.life = life;
	}

	public float getFullLife() {
		return fullLife;
	}

	public void setFullLife(float fullLife) {
		this.fullLife = fullLife;
	}	
}
