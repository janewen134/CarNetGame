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
		 * JSON用于在对象和字符串之间进行转换
		 * 表示一个对象：{"name":"张三","exp":100,"carVO":{"speed":15,"width":150,....}}
		 * 表示一系列对象：[
		 *   {"name":"张三","exp":100,"carVO":{"speed":15,"width":150,....}}
		 *   {"name":"李四","exp":100,"carVO":{"speed":15,"width":150,....}}
		 *   {"name":"王五","exp":100,"carVO":{"speed":15,"width":150,....}}
		 *   ...
		 * ]
		 */
		//等待客户端线程准备好了之后，才向服务器发送链接请求
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
			//创建一个UDP SOCKET，端口随机，或给构造方法指定一个端口
			//自身的端口
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
