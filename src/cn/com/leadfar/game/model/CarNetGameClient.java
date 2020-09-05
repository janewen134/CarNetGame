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
		
		//�����ͻ��˴������
		stub = new CarNetGameStubImpl(serverAddress);
		
		//�����ͻ��˼���̣߳�������
		new ClientMonitor(this).start();
		
		//���ӵ�������
		stub.connect(playerName);
	}
	
	//��ʼ���ͻ��˵�����ģ��
	public void init(String playerName ,CarNetGameVO vo){
		
		//��ʼ���ܵ�����Ϣ
		this.road = new Road(this,vo.getRoadVO());
		
		//��ʼ����Ϸ�ߵ���Ϣ
		PlayerVO[] playerVOs = vo.getPlayers();
		for(int i=0; i<playerVOs.length; i++){
			PlayerVO p = playerVOs[i];
			if(p != null){
				players[i] = new Player(this,p);
				if(playerName.equals(p.getName())){
					player = players[i]; //�ͻ��˵�ǰ����Ϸ��
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
		notifyObservers(); //֪ͨ��ͼˢ�½���
	}
	
	@Override
	public void notifyPlayerInfoUpdate(int playerId, PlayerVO playerVO) {
		stub.notifyPlayerInfoUpdate(playerId, playerVO);
	}

	//���������߿ͻ��ˣ�ĳ���ͻ��˷����˱仯
	public void updatePlayerInfo(int playerId,PlayerVO vo){
		for(int i=0; i<players.length; i++){
			if(players[i] != null && players[i].getId() == vo.getId()){
				players[i].updatePlayer(vo);
				notifyObservers(); //֪ͨ��ͼ����Ϸ���ݷ����仯���ػ���
				break;
			}
		}
	}

	@Override
	public void newBullet(int playerId, BulletVO vo) {
		if(playerId != player.getId()){ //���˸����ҵ�
			road.addRoadThing(new Bullet(road,(Car)road.getRoadThings().get(vo.getCarId()),vo));
			notifyObservers();
		}else{ //���Լ��������ӵ�
			stub.newBullet(playerId,vo); //����߷��������Ա���֪ͨ������Ϸ�ߣ�
		}
	}

	@Override
	public void removeRoadThing(int playerId, String roadThingId) {
		if(playerId != player.getId()){ //���˸����ҵ�
			road.removeRoadThing(roadThingId);
			notifyObservers();
		}else{ //���Լ��������ӵ�
			stub.removeRoadThing(playerId,roadThingId); //����߷��������Ա���֪ͨ������Ϸ�ߣ�
		}
	}

	@Override
	public void updateBullet(int playerId, BulletVO vo) {
		if(playerId != player.getId()){ //���˸����ҵ�
			Bullet bullet = (Bullet)road.getRoadThings().get(vo.getId());
			if(bullet != null){
				bullet.setDistance(vo.getDistance());
			}
			notifyObservers();
		}else{ //���Լ��������ӵ�
			stub.updateBullet(playerId,vo); //����߷��������Ա���֪ͨ������Ϸ�ߣ�
		}
	}
}
