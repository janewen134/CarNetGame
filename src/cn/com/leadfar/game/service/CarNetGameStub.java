package cn.com.leadfar.game.service;

import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.PlayerVO;

/**
 * 用于客户端向服务器传送有关信息
 * @author Administrator
 *
 */
public interface CarNetGameStub {
	
	/**
	 * 客户端向服务器传送关于连接的信息
	 * @param playerName
	 */
	public void connect(String playerName);
	
	public void notifyPlayerInfoUpdate(int playerId,PlayerVO playerVO);

	public void newBullet(int playerId,BulletVO vo);
	public void updateBullet(int playerId,BulletVO vo);
	public void removeRoadThing(int playerId,String roadThingId);	
}
