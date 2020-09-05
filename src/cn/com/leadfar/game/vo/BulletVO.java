package cn.com.leadfar.game.vo;

import cn.com.leadfar.game.model.Bullet;

public class BulletVO extends RoadThingVO {
	private int speed;
	private String carId; //子弹是由哪个车产生的
	public BulletVO(){}
	public BulletVO(Bullet bullet){
		super(bullet);
		this.speed = bullet.getSpeed();
		this.carId = bullet.getCar().getId();
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
}
