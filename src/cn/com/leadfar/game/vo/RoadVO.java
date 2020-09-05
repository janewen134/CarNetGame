package cn.com.leadfar.game.vo;

import cn.com.leadfar.game.model.Road;

public class RoadVO {
	private int length; //跑道长度
	private int width; //跑道宽度
	private int left; //跑道离整个游戏视图的左边距离
	public RoadVO(){}
	public RoadVO(Road road){
		this.left = road.getLeft();
		this.length = road.getLength();
		this.width = road.getWidth();
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}
}
