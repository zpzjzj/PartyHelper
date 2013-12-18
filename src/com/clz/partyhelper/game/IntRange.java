package com.clz.partyhelper.game;

//interface for the range
public class IntRange {
	int min;
	int max;
	
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

