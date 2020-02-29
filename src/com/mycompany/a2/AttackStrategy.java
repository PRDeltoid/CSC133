package com.mycompany.a2;

public class AttackStrategy implements IStrategy {
	String name= "Attack";
	NonPlayerCyborg target;
	
	public AttackStrategy(NonPlayerCyborg cyborg) {
		target = cyborg;
	}

	public void apply() {
		// TODO Auto-generated method stub
	}

	public String getStrategyName() {
		return name;
	}

}
