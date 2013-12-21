package com.clz.partyhelper;
import com.clz.partyhelper.game.AgeRange;
import com.clz.partyhelper.game.AgeRange.AgeLevel;
import com.clz.partyhelper.game.Game.Place;
import com.clz.partyhelper.game.Game.Type;
import com.clz.partyhelper.game.PeopleNumRange;
/*
 * manage all the spinner items 
 */
public class SpinnerMap {
	static public AgeRange getAgeRange(int pos){
		AgeLevel level;
		AgeRange range = null;
		switch (pos){
		case 1:level = AgeLevel.PRIMARY_SCHOOL;break;
		case 2:level = AgeLevel.JUNIOR_HIGH_SCHOOL; break;
		case 3:level = AgeLevel.HIGH_SCHOOL; break;
		case 4:level = AgeLevel.UNDDERGRUADUATE; break;
		case 5:level = AgeLevel.OFFICE_WORKER; break;
		default:return null;
		}
		range = AgeRange.ageRangeMap.get(level);
		return range;
	}
	
	static public PeopleNumRange getNumberRange(int pos){
		PeopleNumRange range = null;
		int min=0;
		int max=0;
		switch (pos){
		case 1:min = 2; max = 2; break;
		case 2:min = 3; max = 5; break;
		case 3:min = 6; max = 10; break;
		case 4:min = 11; break;
		default: return null;
		}
		range = new PeopleNumRange(min, max);
		
		return range;
	}
	
	static public Place getPlace(int pos){
		switch (pos){
		case 1:return Place.indoor;
		case 2:return Place.outdoor;
		}		
		return null;
	}
	
}
