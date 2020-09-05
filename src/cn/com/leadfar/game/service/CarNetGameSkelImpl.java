package cn.com.leadfar.game.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.CarNetGameVO;
import cn.com.leadfar.game.vo.GamePacket;
import cn.com.leadfar.game.vo.PlayerVO;
import cn.com.leadfar.game.vo.VOUtils;

public class CarNetGameSkelImpl implements CarNetGameSkel {

	/**
	 * 用来表示CarNetGameSkel代理的客户端地址
	 */
	private String clientAddress;
	
	/**
	 * 用来表示CarNetGameSkel代理的客户端端口
	 */
	private int clientPort;
	
	public CarNetGameSkelImpl(String clientAddress,int clientPort){
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
	}
	
	@Override
	public void init(String playerName,CarNetGameVO vo) {
		send(new GamePacket("init",playerName,vo));
	}

	@Override
	public void newPlayer(PlayerVO playerVO) {
		send(new GamePacket("newPlayer",playerVO));
	}
	
	public void updatePlayerInfo(int playerId,PlayerVO vo){
		send(new GamePacket("updatePlayerInfo",playerId,vo));
	}
	
	private void send(GamePacket gp){
		try {
			//创建一个UDP SOCKET，端口随机，或给构造方法指定一个端口
			//自身的端口
			DatagramSocket ds = new DatagramSocket();
			String jsonString = VOUtils.convertVO2String(gp);
			byte[] data = jsonString.getBytes("UTF-8");
			DatagramPacket packet = new DatagramPacket(
					data,
					data.length,
					InetAddress.getByName(clientAddress),
					clientPort 
			);
			ds.send(packet);

		} catch (Exception e) {
			e.printStackTrace();
		}		
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
}
