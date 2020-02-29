package com.mycompany.a2;

public class RandomMoveStrategy implements IStrategy {
	String name= "RandomMove";
	NonPlayerCyborg target;
	
	public RandomMoveStrategy(NonPlayerCyborg cyborg) {
		target = cyborg;
	}

	public void apply() {
		// TODO Auto-generated method stub
	}

	public String getStrategyName() {
		return name;
	}

}
