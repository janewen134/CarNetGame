package cn.com.leadfar.game.vo;

import cn.com.leadfar.game.model.CarNetGame;
import cn.com.leadfar.game.model.Player;

/**
 * 封装服务器向客户端传送的初始化客户端游戏数据的信息
 * @author Administrator
 *
 */
public class CarNetGameVO {
	private PlayerVO[] players = new PlayerVO[5];
	private RoadVO roadVO;
	private String status;
	
	public CarNetGameVO(){}
	
	public CarNetGameVO(CarNetGame game){
		this.status = game.getStatus().name();
		this.roadVO = new RoadVO(game.getRoad());
		Player[] ps = game.getPlayers();
		for(int i=0; i<ps.length; i++){
			if(ps[i] != null){
				players[i] = new PlayerVO(ps[i]);
			}
		}
	}
	
	public PlayerVO[] getPlayers() {
		return players;
	}
	public void setPlayers(PlayerVO[] players) {
		this.players = players;
	}
	public RoadVO getRoadVO() {
		return roadVO;
	}
	public void setRoadVO(RoadVO roadVO) {
		this.roadVO = roadVO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
