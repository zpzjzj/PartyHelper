package com.clz.partyhelper.game;

import com.clz.partyhelper.game.AgeRange.AgeLevel;

import android.graphics.Bitmap;

public class Game {
	public enum Type{heavyTaste, fresh, puzzle, girlPreffered, boyPreffered} 
	//don't mind the translation = =
	public enum Place{indoor, outdoor};
	
	private long id;
	private Bitmap img = null;
	private String name = null;
	private AgeRange ageRange = AgeRange.ageRangeMap.get(AgeLevel.PRIMARY_SCHOOL);//enum class
	private PeopleNumRange peopleNumRange = new PeopleNumRange(PeopleNumRange.MIN_NUM, PeopleNumRange.MAX_NUM);
	private Type type = Type.puzzle;
	private Place place = Place.indoor;
	private Object picture;
	private String detail = "empty";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AgeRange getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(AgeRange ageRange) {
		this.ageRange = ageRange;
	}
	public PeopleNumRange getPeopleNumRange() {
		return peopleNumRange;
	}
	public void setPeopleNumRange(PeopleNumRange peopleNumRange) {
		this.peopleNumRange = peopleNumRange;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public Object getPicture() {
		return picture;
	}
	public void setPicture(Object picture) {
		this.picture = picture;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Bitmap getImg() {
		return img;
	}
	public void setImg(Bitmap img) {
		this.img = img;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}