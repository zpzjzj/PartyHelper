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
	
	public IntRange(int min, int max){
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
	
	/*add by CHEN*/
	@Override
	public String toString(){
		String str = new String();
		if (min == max){
			str = String.valueOf(min);
		}
		else {
			str = String.valueOf(min)+ " - " + String.valueOf(max);
		}
		
		return str;
	}
}

