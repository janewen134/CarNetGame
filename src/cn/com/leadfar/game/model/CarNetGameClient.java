package cn.com.leadfar.game.model;

import cn.com.leadfar.game.service.CarNetGameStub;
import cn.com.leadfar.game.service.CarNetGameStubImpl;
import cn.com.leadfar.game.service.ClientMonitor;
import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.CarNetGameVO;
import cn.com.leadfar.game.vo.PlayerVO;

public class CarNetGameClient extends CarNetGame {
	
	private CarNetGameStub stub;
	
	public CarNetGameClient(String playerName,String serverAddress){
		
		//创建客户端代理对象
		stub = new CarNetGameStubImpl(serverAddress);
		
		//创建客户端监控线程，并启动
		new ClientMonitor(this).start();
		
		//连接到服务器
		stub.connect(playerName);
	}
	
	//初始化客户端的数据模型
	public void init(String playerName ,CarNetGameVO vo){
		
		//初始化跑道的信息
		this.road = new Road(this,vo.getRoadVO());
		
		//初始化游戏者的信息
		PlayerVO[] playerVOs = vo.getPlayers();
		for(int i=0; i<playerVOs.length; i++){
			PlayerVO p = playerVOs[i];
			if(p != null){
				players[i] = new Player(this,p);
				if(playerName.equals(p.getName())){
					player = players[i]; //客户端当前的游戏者
				}
			}
		}
		
		this.status = GameStatus.valueOf(vo.getStatus());
		
		notifyObservers();
	}
	
	public void newPlayer(PlayerVO vo){
		for(int i=0; i<players.length; i++){
			if(players[i] == null){
				players[i] = new Player(this,vo);
				break;
			}
		}
		notifyObservers(); //通知视图刷新界面
	}
	
	@Override
	public void notifyPlayerInfoUpdate(int playerId, PlayerVO playerVO) {
		stub.notifyPlayerInfoUpdate(playerId, playerVO);
	}

	//服务器告诉客户端，某个客户端发生了变化
	public void updatePlayerInfo(int playerId,PlayerVO vo){
		for(int i=0; i<players.length; i++){
			if(players[i] != null && players[i].getId() == vo.getId()){
				players[i].updatePlayer(vo);
				notifyObservers(); //通知视图，游戏数据发生变化，重画！
				break;
			}
		}
	}

	@Override
	public void newBullet(int playerId, BulletVO vo) {
		if(playerId != player.getId()){ //别人告诉我的
			road.addRoadThing(new Bullet(road,(Car)road.getRoadThings().get(vo.getCarId()),vo));
			notifyObservers();
		}else{ //我自己产生的子弹
			stub.newBullet(playerId,vo); //需告诉服务器，以便能通知其它游戏者！
		}
	}

	@Override
	public void removeRoadThing(int playerId, String roadThingId) {
		if(playerId != player.getId()){ //别人告诉我的
			road.removeRoadThing(roadThingId);
			notifyObservers();
		}else{ //我自己产生的子弹
			stub.removeRoadThing(playerId,roadThingId); //需告诉服务器，以便能通知其它游戏者！
		}
	}

	@Override
	public void updateBullet(int playerId, BulletVO vo) {
		if(playerId != player.getId()){ //别人告诉我的
			Bullet bullet = (Bullet)road.getRoadThings().get(vo.getId());
			if(bullet != null){
				bullet.setDistance(vo.getDistance());
			}
			notifyObservers();
		}else{ //我自己产生的子弹
			stub.updateBullet(playerId,vo); //需告诉服务器，以便能通知其它游戏者！
		}
	}
}
