package cn.com.leadfar.game.vo;

import cn.com.leadfar.game.model.Player;

public class PlayerVO {
	private String name;
	private int exp;
	private int id;
	private CarVO carVO;
	public PlayerVO(){}
	public PlayerVO(Player player){
		this.id = player.getId();
		this.name = player.getName();
		this.exp = player.getExp();
		this.carVO = new CarVO(player.getCar());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public CarVO getCarVO() {
		return carVO;
	}
	public void setCarVO(CarVO carVO) {
		this.carVO = carVO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
