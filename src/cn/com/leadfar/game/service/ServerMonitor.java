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
			//����һ��UDP SOCKET���˿������������췽��ָ��һ���˿�
			DatagramSocket ds = new DatagramSocket(SERVER_PORT);
			System.out.println("�������������������˿�Ϊ��"+SERVER_PORT+"��");
			
			while(true){
				byte[] data = new byte[10000];
				DatagramPacket packet = new DatagramPacket(
						data,
						data.length
				);
				ds.receive(packet);
				String jsonString = new String(packet.getData() ,0 ,packet.getLength(),"UTF-8");
				GamePacket gp = (GamePacket)VOUtils.convertString2VO(jsonString, GamePacket.class);
				System.out.println("��"+packet.getAddress()+":"+packet.getPort()+" ��" +
						"���յ����ݡ�"+jsonString+"��");
				
				if(gp.getOper().equals("connect")){
					
					//�ͻ���IP��ַ
					String clientAddress = packet.getAddress().getHostAddress();
					
					//�ͻ������ڽ��յĶ˿ں�
					int clientPort = gp.getClientPort();
					
					//��Ҫ��GamePacket����ȡ��playerName��
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
