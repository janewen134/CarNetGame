package cn.com.leadfar.game.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import cn.com.leadfar.game.view.GamePanel;
import cn.com.leadfar.game.vo.RoadVO;

public class Road {
	private CarNetGame game;
	private Map<String,RoadThing> roadThings = new HashMap<String,RoadThing>();
	private int length; //�ܵ�����
	private int width; //�ܵ����
	private int left; //�ܵ���������Ϸ��ͼ����߾���
	
	private BufferedImage bg;
	
	public static int BOTTOM_DISTANCE = 300; //������ǰ�ܵ���300���ؿ��⣬�Ͱѵ�·�����ƶ���
	
	private int offset; //��·Ӧ�����ƫ�ƶ�������
	
	public Road(CarNetGame game,int length){
		this.game = game;
		this.length = length;
		this.width = 700; //�ܵ����
		this.left = 50;
		initPicture();
	}
	
	public Road(CarNetGame game,RoadVO vo){
		this.game = game;
		this.left = vo.getLeft();
		this.length = vo.getLength();
		this.width = vo.getWidth();
		initPicture();
	}
	
	private void initPicture(){
		//���ر���ͼƬ
		//�������·���ĸ�Ŀ¼������Դ��һ�ַ�ʽ��
		//Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/com/leadfar/game/view/bg_sky.jpg");
		try {
			//�������GamePanel������·���м���ͼƬ��Դ
			bg = ImageIO.read(GamePanel.class.getResourceAsStream("bg_sky.jpg"));
			
			Graphics2D g2 = (Graphics2D)bg.getGraphics();
			
			//���û�����ɫ - ��ɫ
			g2.setColor(Color.WHITE);
			
			//�����ߵĿ��
			g2.setStroke(new BasicStroke(4.0f));
			
			//���ܵ���߽�
			g2.drawLine(50, 0, 50, game.getViewHeight());
			
			//���ܵ��ұ߽�
			g2.drawLine(750, 0, 750, game.getViewHeight());
			
			//���м��ܵ�����
			for(int i=1; i<=4; i++){
				
				int x = this.left + ( this.width / 5 ) * i;
				
				//�����ߵ���״
				if(i % 2 == 1){
					g2.setStroke(
						new BasicStroke(
							4.0f,
							BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_MITER,
							1.0f,
							new float[]{100f,100f,200f,100f,100f},
							0.0f
						)
					);
					g2.drawLine(x, 0, x, game.getViewHeight());
				}else{
					g2.setStroke(
						new BasicStroke(
							4.0f,
							BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_MITER,
							1.0f,
							new float[]{150f,100f,150f,100f},
							150.0f
						)
					);
					g2.drawLine(x, 0, x, game.getViewHeight());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void addRoadThing(RoadThing roadThing){
		synchronized (this) {
			roadThings.put(roadThing.getId(),roadThing);
		}
	}
	
	public void removeRoadThing(String roadThingId){
		synchronized (this) {
			roadThings.remove(roadThingId);
		}
	}
	
	public void updateRoadThing(RoadThing roadThing){
		synchronized (this) {
			roadThings.put(roadThing.getId(), roadThing);
		}
	}
	
	public void paint(Graphics g){
		//g.drawString("", 0, 20);
		
		int oy = offset % game.getViewHeight();
		
		g.drawImage(bg, 0, oy, game.getViewWidth(), game.getViewHeight(), null);
		g.drawImage(bg, 0, -(game.getViewHeight() - oy), game.getViewWidth(), game.getViewHeight(), null);
		
		synchronized (this) {
			for(RoadThing rt:roadThings.values()){
				rt.paint(g);
			}
		}
	}
	
	public CarNetGame getGame() {
		return game;
	}
	public void setGame(CarNetGame game) {
		this.game = game;
	}

	public Map<String, RoadThing> getRoadThings() {
		return roadThings;
	}

	public void setRoadThings(Map<String, RoadThing> roadThings) {
		this.roadThings = roadThings;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

}
