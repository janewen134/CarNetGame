package cn.com.leadfar.game.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cn.com.leadfar.game.model.CarNetGameClient;
import cn.com.leadfar.game.vo.GamePacket;
import cn.com.leadfar.game.vo.VOUtils;

public class ClientMonitor extends Thread {

	private CarNetGameClient client;
	
	//客户端的监听端口！
	public static int CLIENT_PORT = 2001;
	
	//客户端是否已准备好接收信息
	public static boolean isReady = false;
	
	public ClientMonitor(CarNetGameClient client){
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			DatagramSocket ds = createSocket();
			
			isReady = true;
			
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
				if(gp.getOper().equals("init")){
					client.init(gp.getPlayerName(),gp.getCarNetGameVO());
				}else if(gp.getOper().equals("newPlayer")){
					client.newPlayer(gp.getPlayerVO());
				}else if(gp.getOper().equals("updatePlayerInfo")){
					client.updatePlayerInfo(gp.getPlayerId(),gp.getPlayerVO());
				}else if(gp.getOper().equals("newBullet")){
					client.newBullet(gp.getPlayerId(), gp.getBulletVO());
				}else if(gp.getOper().equals("updateBullet")){
					client.updateBullet(gp.getPlayerId(), gp.getBulletVO());
				}else if(gp.getOper().equals("removeRoadThing")){
					client.removeRoadThing(gp.getPlayerId(), gp.getRoadThingId());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 尝试创建客户端的监控线程所使用的Socket
	 * @return
	 */
	private DatagramSocket createSocket(){
		try {
			DatagramSocket ds = new DatagramSocket(CLIENT_PORT);
			System.out.println("客户端已经在端口【"+CLIENT_PORT+"】启动！");
			return ds;
		} catch (SocketException e) {
			System.out.println("客户端的端口【"+CLIENT_PORT+"】可能已被占用，正在尝试新的端口");
			CLIENT_PORT++;
			return createSocket();
		}
	}

}
