package cn.com.leadfar.game.vo;

public class GamePacket {
	//用于客户端和服务器之间传送请求的类型，比如：
	//连接、左移、右移等等
	private String oper; 
	
	//用于客户端请求连接，向服务器端传送的信息
	private String playerName;
	
	//客户端请求连接的时候，需要向服务器发送它用于接收信息的端口！
	private int clientPort;
	
	//用于服务器向客户端应答连接请求，返回的信息
	private CarNetGameVO carNetGameVO;
	
	//用于服务器向其它客户端传送关于新增加的游戏者的相关信息
	private PlayerVO playerVO;
	
	//用于服务器向客户端传送关于新发射的子弹的有关信息
	private BulletVO bulletVO;

	//游戏者的ID
	private int playerId;
	
	//游戏物体的唯一标识
	private String roadThingId;
	
	/**
	 * 默认的构造方法必须提供
	 * 因为JSONTools工具需要用到这个缺省的构造方法！
	 */
	public GamePacket(){
	}
	
	/**
	 * 用于客户端向服务器连接的时候
	 * @param oper connect
	 * @param playerName 游戏者姓名
	 * @param clientPort 客户端用于接收信息的端口
	 */
	public GamePacket(String oper,String playerName,int clientPort){
		this.oper = oper;
		this.playerName = playerName;
		this.clientPort = clientPort;
	}
	
	/**
	 * 用于服务器向客户端应答连接的有关信息
	 * @param oper
	 * @param playerName
	 * @param vo
	 */
	public GamePacket(String oper,String playerName,CarNetGameVO vo){
		this.oper = oper;
		this.playerName = playerName;
		this.carNetGameVO = vo;
	}
	
	//用于服务器告诉客户端，新连接了一个游戏者
	public GamePacket(String oper,PlayerVO vo){
		this.oper = oper;
		this.playerVO = vo;
	}
	
	//用于传递关于游戏者信息发生变化
	public GamePacket(String oper,int playerId,PlayerVO vo){
		this.oper = oper;
		this.playerId = playerId;
		this.playerVO = vo;
	}
	
	public GamePacket(String oper,int playerId,BulletVO bulletVO){
		this.oper = oper;
		this.playerId = playerId;
		this.bulletVO = bulletVO;
	}
	
	public GamePacket(String oper,int playerId,String roadThingId){
		this.oper = oper;
		this.playerId = playerId;
		this.roadThingId = roadThingId;
	}
	
	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public CarNetGameVO getCarNetGameVO() {
		return carNetGameVO;
	}

	public void setCarNetGameVO(CarNetGameVO carNetGameVO) {
		this.carNetGameVO = carNetGameVO;
	}

	public PlayerVO getPlayerVO() {
		return playerVO;
	}

	public void setPlayerVO(PlayerVO playerVO) {
		this.playerVO = playerVO;
	}

	public BulletVO getBulletVO() {
		return bulletVO;
	}

	public void setBulletVO(BulletVO bulletVO) {
		this.bulletVO = bulletVO;
	}


	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public String getRoadThingId() {
		return roadThingId;
	}

	public void setRoadThingId(String roadThingId) {
		this.roadThingId = roadThingId;
	}
}
