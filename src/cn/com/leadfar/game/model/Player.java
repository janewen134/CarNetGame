package cn.com.leadfar.game.model;

import java.awt.Graphics;
import java.util.Random;

import cn.com.leadfar.game.vo.PlayerVO;

public class Player {
	private int id;
	private CarNetGame game;
	private Car car;
	private String name;
	private int exp;
	
	public Player(CarNetGame game,String playerName){
		this.id = SerialNumberGenerator.next();
		this.name = playerName;
		this.game = game;
		this.car = new Car(this);
	}
	
	public Player(CarNetGame game,PlayerVO vo){
		this.id = vo.getId();
		this.game = game;
		this.name = vo.getName();
		this.exp = vo.getExp();
		this.car = new Car(this,vo.getCarVO());
	}
	
	public void paint(Graphics g){
		car.paint(g);
	}
	
	public void left(){
		car.left();
		
		//通知游戏，某个游戏者的信息发生了变化！
		game.notifyPlayerInfoUpdate(this.id,new PlayerVO(this));
	}
	
	public void right(){
		car.right();
		game.notifyPlayerInfoUpdate(this.id,new PlayerVO(this));
	}
	
	public void forward(){
		car.forward();
		game.notifyPlayerInfoUpdate(this.id,new PlayerVO(this));
	}
	
	public void backward(){
		car.backward();
		game.notifyPlayerInfoUpdate(this.id,new PlayerVO(this));
	}	
	
	public void stopCar(){
		car.stop();
		game.notifyPlayerInfoUpdate(this.id,new PlayerVO(this));
	}
	
	public void updatePlayer(PlayerVO vo){
		//TODO 更新游戏者姓名等等
		car.updateCar(vo.getCarVO());
	}
	
	public void bullet(){
		car.bullet();
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CarNetGame getGame() {
		return game;
	}

	public void setGame(CarNetGame game) {
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
