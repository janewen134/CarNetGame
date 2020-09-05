package cn.com.leadfar.game.vo;

public class GamePacket {
	//���ڿͻ��˺ͷ�����֮�䴫����������ͣ����磺
	//���ӡ����ơ����Ƶȵ�
	private String oper; 
	
	//���ڿͻ����������ӣ���������˴��͵���Ϣ
	private String playerName;
	
	//�ͻ����������ӵ�ʱ����Ҫ����������������ڽ�����Ϣ�Ķ˿ڣ�
	private int clientPort;
	
	//���ڷ�������ͻ���Ӧ���������󣬷��ص���Ϣ
	private CarNetGameVO carNetGameVO;
	
	//���ڷ������������ͻ��˴��͹��������ӵ���Ϸ�ߵ������Ϣ
	private PlayerVO playerVO;
	
	//���ڷ�������ͻ��˴��͹����·�����ӵ����й���Ϣ
	private BulletVO bulletVO;

	//��Ϸ�ߵ�ID
	private int playerId;
	
	//��Ϸ�����Ψһ��ʶ
	private String roadThingId;
	
	/**
	 * Ĭ�ϵĹ��췽�������ṩ
	 * ��ΪJSONTools������Ҫ�õ����ȱʡ�Ĺ��췽����
	 */
	public GamePacket(){
	}
	
	/**
	 * ���ڿͻ�������������ӵ�ʱ��
	 * @param oper connect
	 * @param playerName ��Ϸ������
	 * @param clientPort �ͻ������ڽ�����Ϣ�Ķ˿�
	 */
	public GamePacket(String oper,String playerName,int clientPort){
		this.oper = oper;
		this.playerName = playerName;
		this.clientPort = clientPort;
	}
	
	/**
	 * ���ڷ�������ͻ���Ӧ�����ӵ��й���Ϣ
	 * @param oper
	 * @param playerName
	 * @param vo
	 */
	public GamePacket(String oper,String playerName,CarNetGameVO vo){
		this.oper = oper;
		this.playerName = playerName;
		this.carNetGameVO = vo;
	}
	
	//���ڷ��������߿ͻ��ˣ���������һ����Ϸ��
	public GamePacket(String oper,PlayerVO vo){
		this.oper = oper;
		this.playerVO = vo;
	}
	
	//���ڴ��ݹ�����Ϸ����Ϣ�����仯
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
