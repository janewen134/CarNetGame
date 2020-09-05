package cn.com.leadfar.game.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cn.com.leadfar.game.model.CarNetGameClient;
import cn.com.leadfar.game.vo.GamePacket;
import cn.com.leadfar.game.vo.VOUtils;

public class ClientMonitor extends Thread {

	private CarNetGameClient client;
	
	//�ͻ��˵ļ����˿ڣ�
	public static int CLIENT_PORT = 2001;
	
	//�ͻ����Ƿ���׼���ý�����Ϣ
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
				System.out.println("��"+packet.getAddress()+":"+packet.getPort()+" ��" +
						"���յ����ݡ�"+jsonString+"��");
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
	 * ���Դ����ͻ��˵ļ���߳���ʹ�õ�Socket
	 * @return
	 */
	private DatagramSocket createSocket(){
		try {
			DatagramSocket ds = new DatagramSocket(CLIENT_PORT);
			System.out.println("�ͻ����Ѿ��ڶ˿ڡ�"+CLIENT_PORT+"��������");
			return ds;
		} catch (SocketException e) {
			System.out.println("�ͻ��˵Ķ˿ڡ�"+CLIENT_PORT+"�������ѱ�ռ�ã����ڳ����µĶ˿�");
			CLIENT_PORT++;
			return createSocket();
		}
	}

}
