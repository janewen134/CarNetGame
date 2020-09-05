package cn.com.leadfar.game.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import cn.com.leadfar.game.CollidingDetectUtils;
import cn.com.leadfar.game.vo.BulletVO;

public class Bullet extends RoadThing{
	private Car car;
	private int speed;
	
	public Bullet(Road r,Car c,int l,int d) {
		super(r);
		this.car = c;
		this.speed = 50;
		this.left = l;
		this.distance = d;
		this.width = 10;
		this.height = 10;
		
		//告诉游戏，一颗子弹产生了
		r.getGame().newBullet(c.getPlayer().getId(),new BulletVO(this));
		
		//启动一个线程，用来控制子弹的运动
		new Thread(){
			public void run(){
				try {
					for(int i=0; i<10; i++){
						distance = distance + speed;
						road.getGame().notifyObservers();
						
						//发生了碰撞，结束子弹线程，不再继续往前运动！
						if(isCollidingOtherCar()){
							break;
						}
						
						//告诉游戏，子弹的信息发生了变化
						road.getGame().updateBullet(car.getPlayer().getId(),new BulletVO(Bullet.this));
						
						sleep(31);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				road.removeRoadThing(Bullet.this.getId());
				road.getGame().notifyObservers();
				road.getGame().removeRoadThing(car.getPlayer().getId(),id);
			}
		}.start();
	}
	
	public Bullet(Road r,Car c,BulletVO vo){
		super(r,vo);
		this.car = c;
		this.speed = vo.getSpeed();
		this.left = vo.getLeft();
		this.distance = vo.getDistance();
		this.width = vo.getWidth();
		this.height = vo.getHeight();
	}
	
	//子弹是否与其它车发生了碰撞
	public boolean isCollidingOtherCar(){
		Player[] ps = road.getGame().getPlayers();
		for(Player p:ps){
			//非当前车辆，需检测是否碰撞
			if(p != null && !p.getName().equals(car.getPlayer().getName())){
				Rectangle rect = CollidingDetectUtils.colliding(this, p.getCar());
				if(!rect.isEmpty()){
					//TODO 在碰撞的位置显示一些图片，以示碰撞效果
					p.getCar().updateBlood(-1); //生命力减少1点！
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void draw(Graphics g,int x,int y) {
		Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.drawOval(x, y, width, height);
		g.fillOval(x, y, width, height);
		g.setColor(oldColor);
	}



	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
