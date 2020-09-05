package cn.com.leadfar.game.vo;

import cn.com.leadfar.game.model.Road;

public class RoadVO {
	private int length; //�ܵ�����
	private int width; //�ܵ����
	private int left; //�ܵ���������Ϸ��ͼ����߾���
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
