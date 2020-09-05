package cn.com.leadfar.game.model;

import java.awt.Graphics;
import java.awt.Rectangle;

import cn.com.leadfar.game.CollidingDetectUtils;
import cn.com.leadfar.game.vo.CarVO;
import cn.com.leadfar.game.vo.PlayerVO;

public class Car extends RoadThing{
	private Player player;
	private int maxBullets;
	private int speed;
	private int acceleration; //加速度
	private int topSpeed;
	
	private int blood;
	public Car(Player player){
		super(player.getGame().getRoad());
		this.player = player;
		this.id = this.player.getId() + "_" + SerialNumberGenerator.next();
		this.distance = 0;
		this.width = 135;
		this.height = 160;
		this.speed = 0;
		this.blood = 100;
		this.acceleration = 1;
		this.topSpeed = 20;
		this.maxBullets = 100;
		
		//根据游戏中车的数量，来决定车的左边界
		int carNumber = player.getGame().getPlayerSize();
		this.left = (carNumber) * 140 + 2;

	}
	
	public Car(Player player,CarVO vo){
		super(player.getGame().getRoad(),vo);
		this.player = player;
		this.speed = vo.getSpeed();
		this.blood = vo.getBlood();
		this.acceleration = vo.getAcceleration();
		this.topSpeed = vo.getTopSpeed();
		this.maxBullets = vo.getMaxBullets();
	}

	@Override
	protected void draw(Graphics g, int x, int y) {
		g.drawRect(x, y, width, height);
		
		//在车身上显示游戏者的姓名
		g.drawString(player.getName(), x, y+height/2);
	}

	public void left(){
		addSpeed();
		int oldLeft = left;
		if(left - speed < 0){
			left = 0;
		}else{
			left = left - speed;
		}
		if(oldLeft != left){
			player.getGame().notifyObservers();
		}
		isCollidingOtherCar("left");
	}
	
	public void right(){
		addSpeed();
		if(left + speed > (road.getWidth() - this.getWidth())){
			this.left = road.getWidth() - this.getWidth();
		}else{
			left = left + speed;
		}
		player.getGame().notifyObservers();
		isCollidingOtherCar("right");
	}
	
	public void forward(){
		addSpeed();
		updateDistance(distance + speed);
		player.getGame().notifyObservers();
		isCollidingOtherCar("forward");
	}
	
	public void backward(){
		addSpeed();
		updateDistance(distance - speed);
		player.getGame().notifyObservers();
		isCollidingOtherCar("backward");
	}
	
	/**
	 * 检测车在某个方向上与某车发生了碰撞
	 * @param direction
	 * @return
	 */
	public boolean isCollidingOtherCar(String direction){
		Player[] ps = road.getGame().getPlayers();
		for(Player p:ps){
			//非当前车辆，需检测是否碰撞
			if(p != null && !p.getName().equals(player.getName())){
				Rectangle rect = CollidingDetectUtils.colliding(this, p.getCar());
				if(!rect.isEmpty()){
					//TODO 在碰撞的位置显示一些图片，以示碰撞效果
					p.getCar().updateBlood(-1); //被碰撞的车生命力减少1点！
					
					if("left".equals(direction)){
						player.right();
					}else if("right".equals(direction)){
						player.left();
					}else if("forward".equals(direction)){
						player.backward();
					}else if("backward".equals(direction)){
						player.forward();
					}
					
					return true;
				}
			}
		}
		return false;
	}
	
	public void stop(){
		speed = 0;
	}
	
	public void updateCar(CarVO vo){
		this.distance = vo.getDistance();
		this.left = vo.getLeft();
		this.blood = vo.getBlood();
		this.acceleration = vo.getAcceleration();
		this.maxBullets = vo.getMaxBullets();
		this.speed = vo.getSpeed();
		this.topSpeed = vo.getTopSpeed();
	}
	
	/**
	 * 增加或减少车的生命力
	 * @param newBlood 正数表示增加，负数表示减少
	 */
	public void updateBlood(int newBlood){
		if(blood + newBlood > 100){
			blood = 100;
		}else if(blood + newBlood < 0){
			blood = 0;
		}else{
			blood = blood + newBlood;
		}
		player.getGame().notifyObservers();
		player.getGame().notifyPlayerInfoUpdate(player.getGame().getPlayer().getId(), new PlayerVO(player));
	}
	
	public void bullet(){
		if(maxBullets - 1 > 0){
			new Bullet(road,this,this.getLeft()+this.getWidth()/2,this.getDistance()+this.getHeight());
			maxBullets = maxBullets - 1;
		}
	}
	
	private void addSpeed(){
		speed = speed + acceleration;
		if(speed > topSpeed){
			speed = topSpeed;
		}
	}
	
	private void updateDistance(int newDistance){
		this.distance = newDistance;
		if(distance < 0){
			distance = 0;
		}else if(distance > road.getLength()){
			distance = road.getLength();
		}
		
		if(distance > Road.BOTTOM_DISTANCE){
			road.setOffset( distance - Road.BOTTOM_DISTANCE );
		}else {
			road.setOffset(0);
		}
		
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
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

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
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
}
