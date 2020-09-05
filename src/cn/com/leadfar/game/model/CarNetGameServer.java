package cn.com.leadfar.game.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.com.leadfar.game.service.CarNetGameSkel;
import cn.com.leadfar.game.service.CarNetGameSkelImpl;
import cn.com.leadfar.game.service.ServerMonitor;
import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.CarNetGameVO;
import cn.com.leadfar.game.vo.PlayerVO;

public class CarNetGameServer extends CarNetGame {
	
	private Map<Integer,CarNetGameSkel> skels = new HashMap<Integer,CarNetGameSkel>();
	
	public CarNetGameServer(String playerName,int roadLength){
		
		this.road = new Road(this,roadLength);
		this.road.setLength(roadLength);		
		
		Player player = new Player(this,playerName);
		players[0] = player; 
		
		this.player = player;
		
		this.status = GameStatus.WATING;
		
		//启动服务器端监控线程
		new ServerMonitor(this).start();
	}
	
	/**
	 * 处理客户端连接到服务器的有关事项
	 * @param playerName 客户端游戏者的姓名
	 */
	public void connect(String playerName,String clientAddress,int clientPort){
		//创建Player对象
		if(getPlayerSize() == 5){
			throw new RuntimeException("服务器已经到达最大的游戏者数量，无法支持连接！");
		}
		//给客户端创建代理
		CarNetGameSkelImpl skel = new CarNetGameSkelImpl(clientAddress,clientPort);
		
		Player newPlayer = null;
		for(int i=0; i<players.length; i++){
			Player p = players[i];
			if(p == null){
				players[i] = new Player(this,playerName);
				newPlayer = players[i];
				break;
			}
		}
		
		notifyObservers();
		
		//给当前连接者发送关于服务器游戏数据的信息
		CarNetGameVO vo = new CarNetGameVO(this);
		skel.init(playerName,vo);
		
		Collection<CarNetGameSkel> sk = skels.values();
		for(CarNetGameSkel s:sk){ //给其它的客户端发送关于新增加一个游戏者的信息
			s.newPlayer(new PlayerVO(newPlayer));
		}
		
		//本客户端代理加入到服务器的代理列表中！
		skels.put(newPlayer.getId(), skel);

	}
	
	/**
	 * 当某个游戏者的信息发生变化的时候，它有可能从服务器过来，也有可能从客户端过来
	 */
	public void notifyPlayerInfoUpdate(int playerId,PlayerVO playerVO){
		//如果是客户端告诉我，它的游戏者信息发生了变化
		if(playerId != player.getId()){
			for(int i=0; i<players.length; i++){
				if(players[i] != null && players[i].getId() == playerVO.getId()){
					players[i].updatePlayer(playerVO);
					notifyObservers(); //通知视图，游戏数据发生变化了！
					break;
				}
			}
		}
		
		//通知其它游戏者，某个游戏者的信息发生了变化
		Set skelsSet = skels.entrySet();
		for (Iterator iterator = skelsSet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer pid = (Integer)entry.getKey();
			CarNetGameSkel skel = (CarNetGameSkel)entry.getValue();
			if(pid != playerId){
				skel.updatePlayerInfo(playerId,playerVO);
			} 
		}
	}

	@Override
	public void newBullet(int playerId, BulletVO vo) {
		if(playerId != player.getId()){ //这个信息不是我自己产生的！
			road.addRoadThing(new Bullet(road,(Car)road.getRoadThings().get(vo.getCarId()),vo));
			notifyObservers();
		}
		
		//通知其它游戏者，某个游戏者产生了一颗子弹
		Set skelsSet = skels.entrySet();
		for (Iterator iterator = skelsSet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer pid = (Integer)entry.getKey();
			CarNetGameSkel skel = (CarNetGameSkel)entry.getValue();
			if(pid != playerId){
				skel.newBullet(playerId,vo); //告诉其它客户端
			} 
		}
		
	}

	@Override
	public void removeRoadThing(int playerId, String roadThingId) {
		if(playerId != player.getId()){ //这个信息不是我自己产生的！
			road.removeRoadThing(roadThingId);
			notifyObservers();
		}
		
		//通知其它游戏者，某个游戏者的某颗子弹信息发生了变化
		Set skelsSet = skels.entrySet();
		for (Iterator iterator = skelsSet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer pid = (Integer)entry.getKey();
			CarNetGameSkel skel = (CarNetGameSkel)entry.getValue();
			if(pid != playerId){
				skel.removeRoadThing(playerId,roadThingId); //告诉其它客户端
			} 
		}
	}

	@Override
	public void updateBullet(int playerId, BulletVO vo) {
		if(playerId != player.getId()){ //这个信息不是我自己产生的！
			Bullet bullet = (Bullet)road.getRoadThings().get(vo.getId());
			if(bullet != null){
				bullet.setDistance(vo.getDistance());
			}
			notifyObservers();
		}
		
		//通知其它游戏者，某个游戏者的某颗子弹信息发生了变化
		Set skelsSet = skels.entrySet();
		for (Iterator iterator = skelsSet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer pid = (Integer)entry.getKey();
			CarNetGameSkel skel = (CarNetGameSkel)entry.getValue();
			if(pid != playerId){
				skel.updateBullet(playerId,vo); //告诉其它客户端
			} 
		}
	}	
}
