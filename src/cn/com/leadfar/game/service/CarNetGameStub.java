package cn.com.leadfar.game.service;

import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.PlayerVO;

/**
 * ���ڿͻ���������������й���Ϣ
 * @author Administrator
 *
 */
public interface CarNetGameStub {
	
	/**
	 * �ͻ�������������͹������ӵ���Ϣ
	 * @param playerName
	 */
	public void connect(String playerName);
	
	public void notifyPlayerInfoUpdate(int playerId,PlayerVO playerVO);

	public void newBullet(int playerId,BulletVO vo);
	public void updateBullet(int playerId,BulletVO vo);
	public void removeRoadThing(int playerId,String roadThingId);	
}
