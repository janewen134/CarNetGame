package cn.com.leadfar.game.vo;

import cn.com.leadfar.game.model.Car;

public class CarVO extends RoadThingVO {
	private int maxBullets;
	private int speed;
	private int acceleration; //º”ÀŸ∂»
	private int topSpeed;
	
	private int blood;
	public CarVO(){}
	public CarVO(Car car){
		super(car);
		
		this.maxBullets = car.getMaxBullets();
		this.speed = car.getSpeed();
		this.acceleration = car.getAcceleration();
		this.topSpeed = car.getTopSpeed();
		this.blood = car.getBlood();
	}
	
	public int getMaxBullets() {
		return maxBullets;
	}

	public void setMaxBullets(int maxBullets) {
		this.maxBullets = maxBullets;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	public int getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}
}
