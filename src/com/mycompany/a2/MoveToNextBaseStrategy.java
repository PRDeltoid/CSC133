package com.mycompany.a2;

public class MoveToNextBaseStrategy implements IStrategy {
	String name= "MoveToNextBase";
	NonPlayerCyborg target;
	
	public MoveToNextBaseStrategy(NonPlayerCyborg cyborg) {
		target = cyborg;
	}

	public void apply() {
		// TODO Auto-generated method stub
	}

	public String getStrategyName() {
		return name;
	}

}
