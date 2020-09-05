package cn.com.leadfar.game.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.GamePacket;
import cn.com.leadfar.game.vo.PlayerVO;
import cn.com.leadfar.game.vo.VOUtils;

public class CarNetGameStubImpl implements CarNetGameStub {

	private String serverAddress;
	
	public CarNetGameStubImpl(String serverAddress){
		this.serverAddress = serverAddress;
	}
	
	@Override
	public void connect(String playerName) {
		/**
		 * JSON(JavaScript Object Notation)
		 * JSON�����ڶ�����ַ���֮�����ת��
		 * ��ʾһ������{"name":"����","exp":100,"carVO":{"speed":15,"width":150,....}}
		 * ��ʾһϵ�ж���[
		 *   {"name":"����","exp":100,"carVO":{"speed":15,"width":150,....}}
		 *   {"name":"����","exp":100,"carVO":{"speed":15,"width":150,....}}
		 *   {"name":"����","exp":100,"carVO":{"speed":15,"width":150,....}}
		 *   ...
		 * ]
		 */
		//�ȴ��ͻ����߳�׼������֮�󣬲��������������������
		while(!ClientMonitor.isReady){}
		
		send(new GamePacket("connect",playerName,ClientMonitor.CLIENT_PORT));
	}


	@Override
	public void notifyPlayerInfoUpdate(int playerId, PlayerVO playerVO) {
		send(new GamePacket("notifyPlayerInfoUpdate",playerId,playerVO));
	}

	
	@Override
	public void newBullet(int playerId, BulletVO vo) {
		send(new GamePacket("newBullet",playerId,vo));
	}

	@Override
	public void removeRoadThing(int playerId, String roadThingId) {
		send(new GamePacket("removeRoadThing",playerId,roadThingId));
	}

	@Override
	public void updateBullet(int playerId, BulletVO vo) {
		send(new GamePacket("updateBullet",playerId,vo));
	}

	private void send(GamePacket gp){
		try {
			//����һ��UDP SOCKET���˿������������췽��ָ��һ���˿�
			//����Ķ˿�
			DatagramSocket ds = new DatagramSocket();
			String jsonString = VOUtils.convertVO2String(gp);
			byte[] data = jsonString.getBytes("UTF-8");
			DatagramPacket packet = new DatagramPacket(
					data,
					data.length,
					InetAddress.getByName(serverAddress),
					ServerMonitor.SERVER_PORT 
			);
			ds.send(packet);

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
