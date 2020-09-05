package cn.com.leadfar.game.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import cn.com.leadfar.game.model.CarNetGame;

public class GamePanel extends JPanel {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		CarNetGameMainFrame frame = (CarNetGameMainFrame)this.getTopLevelAncestor();
		
		CarNetGame game = frame.getGame();
		//因为CarNetGame对象持有所有的游戏数据，把数据呈现的职责交给CarNetGame对象
		if(game != null){
			game.paint(g);
		}
		
		//
//		g.drawString("panel.width:"+this.getWidth()+",panel.height:"+this.getHeight()+
//				",frame.width:"+frame.getWidth()+",frame.height:"+frame.getHeight(), 0, 20);
//		
//		Rect rect = frame.getRect();
//		g.drawRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
	}

}
