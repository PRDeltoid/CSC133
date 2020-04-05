package com.mycompany.a3;

public interface ICollider {
	//Check if the current object is colliding with otherObject
	abstract boolean collidesWith(GameObject otherObject);
	
	//Handle collision with otherObject
	abstract void handleCollision(GameObject otherObject);

}
