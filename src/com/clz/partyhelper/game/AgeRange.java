package com.clz.partyhelper.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AgeRange extends IntRange{
	public final static int MIN_AGE = 0;
	public final static int MAX_AGE = 200;
	
	public AgeRange(int minAge, int maxAge){
		super(minAge, maxAge);
	}
	
	public static enum AgeLevel{
		PRIMARY_SCHOOL, JUNIOR_HIGH_SCHOOL, HIGH_SCHOOL, UNDDERGRUADUATE, OFFICE_WORKER, ELDER;
		public int toInt(){
			return ordinal();
		}
	};
	public static final Map<AgeLevel, AgeRange> ageRangeMap = new HashMap<AgeLevel, AgeRange>();
	static{
		ageRangeMap.put(AgeLevel.PRIMARY_SCHOOL, new AgeRange(6, 12));
		ageRangeMap.put(AgeLevel.JUNIOR_HIGH_SCHOOL, new AgeRange(13, 16));
		ageRangeMap.put(AgeLevel.HIGH_SCHOOL, new AgeRange(17, 20));
		ageRangeMap.put(AgeLevel.UNDDERGRUADUATE, new AgeRange(21, 25));
		ageRangeMap.put(AgeLevel.OFFICE_WORKER, new AgeRange(26, 50));
		ageRangeMap.put(AgeLevel.ELDER, new AgeRange(51, 150));
	}
	
	/**
	 * 
	 * @return the array of AgeLevel that is contained in the range
	 */
	public ArrayList<AgeLevel> toEnum(){
		ArrayList<AgeLevel> res = new ArrayList<AgeLevel>();
		for(Entry<AgeLevel, AgeRange> entry : ageRangeMap.entrySet()){
			if(withInRange(entry.getValue()))
				res.add(entry.getKey());
		}
		return res;
	}
}
