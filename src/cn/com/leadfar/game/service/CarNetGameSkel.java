package cn.com.leadfar.game.service;

import cn.com.leadfar.game.vo.BulletVO;
import cn.com.leadfar.game.vo.CarNetGameVO;
import cn.com.leadfar.game.vo.PlayerVO;

public interface CarNetGameSkel {
	public void init(String playerName,CarNetGameVO vo);
	public void newPlayer(PlayerVO playerVO);
	public void updatePlayerInfo(int playerId,PlayerVO vo);
	public void newBullet(int playerId,BulletVO vo);
	public void updateBullet(int playerId,BulletVO vo);
	public void removeRoadThing(int playerId,String roadThingId);
}
