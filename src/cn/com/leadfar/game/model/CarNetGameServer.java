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
		
		//�����������˼���߳�
		new ServerMonitor(this).start();
	}
	
	/**
	 * ����ͻ������ӵ����������й�����
	 * @param playerName �ͻ�����Ϸ�ߵ�����
	 */
	public void connect(String playerName,String clientAddress,int clientPort){
		//����Player����
		if(getPlayerSize() == 5){
			throw new RuntimeException("�������Ѿ�����������Ϸ���������޷�֧�����ӣ�");
		}
		//���ͻ��˴�������
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
		
		//����ǰ�����߷��͹��ڷ�������Ϸ���ݵ���Ϣ
		CarNetGameVO vo = new CarNetGameVO(this);
		skel.init(playerName,vo);
		
		Collection<CarNetGameSkel> sk = skels.values();
		for(CarNetGameSkel s:sk){ //�������Ŀͻ��˷��͹���������һ����Ϸ�ߵ���Ϣ
			s.newPlayer(new PlayerVO(newPlayer));
		}
		
		//���ͻ��˴�����뵽�������Ĵ����б��У�
		skels.put(newPlayer.getId(), skel);

	}
	
	/**
	 * ��ĳ����Ϸ�ߵ���Ϣ�����仯��ʱ�����п��ܴӷ�����������Ҳ�п��ܴӿͻ��˹���
	 */
	public void notifyPlayerInfoUpdate(int playerId,PlayerVO playerVO){
		//����ǿͻ��˸����ң�������Ϸ����Ϣ�����˱仯
		if(playerId != player.getId()){
			for(int i=0; i<players.length; i++){
				if(players[i] != null && players[i].getId() == playerVO.getId()){
					players[i].updatePlayer(playerVO);
					notifyObservers(); //֪ͨ��ͼ����Ϸ���ݷ����仯�ˣ�
					break;
				}
			}
		}
		
		//֪ͨ������Ϸ�ߣ�ĳ����Ϸ�ߵ���Ϣ�����˱仯
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
		if(playerId != player.getId()){ //�����Ϣ�������Լ������ģ�
			road.addRoadThing(new Bullet(road,(Car)road.getRoadThings().get(vo.getCarId()),vo));
			notifyObservers();
		}
		
		//֪ͨ������Ϸ�ߣ�ĳ����Ϸ�߲�����һ���ӵ�
		Set skelsSet = skels.entrySet();
		for (Iterator iterator = skelsSet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer pid = (Integer)entry.getKey();
			CarNetGameSkel skel = (CarNetGameSkel)entry.getValue();
			if(pid != playerId){
				skel.newBullet(playerId,vo); //���������ͻ���
			} 
		}
		
	}

	@Override
	public void removeRoadThing(int playerId, String roadThingId) {
		if(playerId != player.getId()){ //�����Ϣ�������Լ������ģ�
			road.removeRoadThing(roadThingId);
			notifyObservers();
		}
		
		//֪ͨ������Ϸ�ߣ�ĳ����Ϸ�ߵ�ĳ���ӵ���Ϣ�����˱仯
		Set skelsSet = skels.entrySet();
		for (Iterator iterator = skelsSet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer pid = (Integer)entry.getKey();
			CarNetGameSkel skel = (CarNetGameSkel)entry.getValue();
			if(pid != playerId){
				skel.removeRoadThing(playerId,roadThingId); //���������ͻ���
			} 
		}
	}

	@Override
	public void updateBullet(int playerId, BulletVO vo) {
		if(playerId != player.getId()){ //�����Ϣ�������Լ������ģ�
			Bullet bullet = (Bullet)road.getRoadThings().get(vo.getId());
			if(bullet != null){
				bullet.setDistance(vo.getDistance());
			}
			notifyObservers();
		}
		
		//֪ͨ������Ϸ�ߣ�ĳ����Ϸ�ߵ�ĳ���ӵ���Ϣ�����˱仯
		Set skelsSet = skels.entrySet();
		for (Iterator iterator = skelsSet.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer pid = (Integer)entry.getKey();
			CarNetGameSkel skel = (CarNetGameSkel)entry.getValue();
			if(pid != playerId){
				skel.updateBullet(playerId,vo); //���������ͻ���
			} 
		}
	}	
}
