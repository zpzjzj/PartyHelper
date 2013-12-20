package com.clz.partyhelper.game;

/**
 * integer range with min and max
 * base class for AgeRange and PeopleNumRange
 * @author zhaoping
 *
 */
public class IntRange {
	private int min;
	private int max;
	
	IntRange(int min, int max){
		this.min = min;
		this.max = max;
	}
	
	public int getMin(){
		return min;
	}
	
	public int getMax(){
		return max;
	}
	
	public boolean withInRange(IntRange other){
		return other.getMin() >= getMin() && other.getMax() <= getMax(); 
	}
}

