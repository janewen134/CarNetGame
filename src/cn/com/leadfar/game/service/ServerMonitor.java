package cn.com.leadfar.game.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import cn.com.leadfar.game.model.CarNetGameServer;
import cn.com.leadfar.game.vo.GamePacket;
import cn.com.leadfar.game.vo.VOUtils;

public class ServerMonitor extends Thread {

	private CarNetGameServer server;
	
	public static int SERVER_PORT = 9999;
	
	public ServerMonitor(CarNetGameServer server){
		this.server = server;
	}
	
	@Override
	public void run() {
		
		try {
			//创建一个UDP SOCKET，端口随机，或给构造方法指定一个端口
			DatagramSocket ds = new DatagramSocket(SERVER_PORT);
			System.out.println("服务器已启动，监听端口为【"+SERVER_PORT+"】");
			
			while(true){
				byte[] data = new byte[10000];
				DatagramPacket packet = new DatagramPacket(
						data,
						data.length
				);
				ds.receive(packet);
				String jsonString = new String(packet.getData() ,0 ,packet.getLength(),"UTF-8");
				GamePacket gp = (GamePacket)VOUtils.convertString2VO(jsonString, GamePacket.class);
				System.out.println("从"+packet.getAddress()+":"+packet.getPort()+" 处" +
						"接收到数据【"+jsonString+"】");
				
				if(gp.getOper().equals("connect")){
					
					//客户端IP地址
					String clientAddress = packet.getAddress().getHostAddress();
					
					//客户端用于接收的端口号
					int clientPort = gp.getClientPort();
					
					//需要从GamePacket里面取出playerName来
					server.connect(gp.getPlayerName(),clientAddress,clientPort);
				}else if(gp.getOper().equals("notifyPlayerInfoUpdate")){
					server.notifyPlayerInfoUpdate(gp.getPlayerId(), gp.getPlayerVO());
				}else if(gp.getOper().equals("newBullet")){
					server.newBullet(gp.getPlayerId(), gp.getBulletVO());
				}else if(gp.getOper().equals("updateBullet")){
					server.updateBullet(gp.getPlayerId(), gp.getBulletVO());
				}else if(gp.getOper().equals("removeRoadThing")){
					server.removeRoadThing(gp.getPlayerId(), gp.getRoadThingId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
