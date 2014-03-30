/**
 * 
 */
package com.tuncay.dragonvsplanes.math;

/**
 * A base 2D vector implementation 
 * 
 * @author impaler
 *
 */
public class Vector2f {

	private float x;
	private float y;

	// ~ Constructors -------------------------
	public Vector2f() {
		this.x = .0f;
		this.y = .0f;
	}
	
	public Vector2f(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// ~ Getters and setters ------------------
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	// ----------------------------------------------
	// - Vector operations
	// ----------------------------------------------
	
	// - To be added
}
