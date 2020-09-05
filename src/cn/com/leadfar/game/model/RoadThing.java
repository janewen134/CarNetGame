package cn.com.leadfar.game.model;

import java.awt.Graphics;

import cn.com.leadfar.game.vo.RoadThingVO;

public abstract class RoadThing {
	protected String id;
	protected Road road;
	protected int distance;
	protected int left;
	protected int width;
	protected int height;
	
	public RoadThing(Road road){
		if(road.getGame().getPlayer() != null){
			this.id = road.getGame().getPlayer().getId()+"_"+SerialNumberGenerator.next();
		}
		this.road = road;
		this.road.addRoadThing(this);
	}
	
	public RoadThing(Road road,RoadThingVO vo){
		this.id = vo.getId();
		this.road = road;
		this.road.addRoadThing(this);
		this.distance = vo.getDistance();
		this.left = vo.getLeft();
		this.width = vo.getWidth();
		this.height = vo.getHeight();
	}
	
	public final void paint(Graphics g){
		//必须落在本视图范围内的物体，才需要显示出来
		if((this.distance + this.height >= road.getOffset())
				&&
			(this.distance <= road.getOffset() + road.getGame().getViewHeight())
			){
			
			int x = this.left + road.getLeft();
			int y = road.getGame().getViewHeight() - 
				( this.distance + this.getHeight() - road.getOffset());
			draw(g,x,y);
		}
	}
	
	protected abstract void draw(Graphics g,int x,int y);
	
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
	public Road getRoad() {
		return road;
	}
	public void setRoad(Road road) {
		this.road = road;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
