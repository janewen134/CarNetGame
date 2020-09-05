package cn.com.leadfar.game.vo;

import cn.com.leadfar.game.model.RoadThing;

public class RoadThingVO {
	protected String id;
	protected int distance;
	protected int left;
	protected int width;
	protected int height;
	public RoadThingVO(){}
	public RoadThingVO(RoadThing rt){
		this.id = rt.getId();
		this.distance = rt.getDistance();
		this.left = rt.getLeft();
		this.width = rt.getWidth();
		this.height = rt.getHeight();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
