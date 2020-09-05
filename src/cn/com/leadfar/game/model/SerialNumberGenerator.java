package cn.com.leadfar.game.model;

public class SerialNumberGenerator {
	private static int id = 1;
	public synchronized static int next(){
		return id++;
	}
}
