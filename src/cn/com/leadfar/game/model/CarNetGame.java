package cn.com.leadfar.game.model;

import java.awt.Graphics;
import java.util.Observable;

import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.PlayerVO;

public class CarNetGame extends Observable{
	protected Player[] players = new Player[5];
	protected Road road;
	protected Player player; //��ǰ��Ϸ�ߣ�����
	//��Ϸ״̬
	protected GameStatus status;
	
	private int viewWidth = 800; //��Ϸ��ͼ���
	private int viewHeight = 600; //��Ϸ��ͼ�߶�
	
	public void paint(Graphics g){
		if(road != null){
			road.paint(g);
			for(Player player:players){
				if(player != null){
					player.paint(g);
				}
			}
			
			//��ӡ��ǰ��Ϸ�ߵ�ͳ����Ϣ
			String str = "��Ϸ��������"+player.getName()+
				",�ܵ��ܳ���"+road.getLength()+
				",Ŀǰ���"+player.getCar().getDistance()+
				",�ٶȣ�"+player.getCar().getSpeed()+
				",��������"+player.getCar().getBlood()+
				",ʣ���ӵ���"+player.getCar().getMaxBullets();
			g.drawString(str, 0, 10);
		}
	}
	
	public int getPlayerSize(){
		int num = 0;
		for(Player p:players){
			if(p != null){
				num = num + 1;
			}
		}
		return num;
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}

	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
	
	public void notifyPlayerInfoUpdate(int playerId,PlayerVO playerVO){
	}
	
	public void newBullet(int playerId,BulletVO vo){
		
	}
	public void updateBullet(int playerId,BulletVO vo){
		
	}
	public void removeRoadThing(int playerId,String roadThingId){
		
	}

	public void left(){
		player.left();
	}
	
	public void right(){
		player.right();
	}
	
	public void forward(){
		player.forward();
	}
	
	public void backward(){
		player.backward();
	}
	
	public void stopCar(){
		player.stopCar();
	}
	
	public void bullet(){
		player.bullet();
	}
	
	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}



	public int getViewWidth() {
		return viewWidth;
	}



	public void setViewWidth(int viewWidth) {
		this.viewWidth = viewWidth;
	}



	public int getViewHeight() {
		return viewHeight;
	}



	public void setViewHeight(int viewHeight) {
		this.viewHeight = viewHeight;
	}

}
