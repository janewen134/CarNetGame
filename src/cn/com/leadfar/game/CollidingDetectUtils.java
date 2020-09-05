package cn.com.leadfar.game;

import java.awt.Rectangle;

import cn.com.leadfar.game.model.RoadThing;

/**
 * Åö×²¼ì²â¹¤¾ßÀà
 * @author Administrator
 *
 */
public class CollidingDetectUtils {
	
	private static Rectangle rect1 = new Rectangle();
	private static Rectangle rect2 = new Rectangle();
	
	public static Rectangle colliding(RoadThing rt1,RoadThing rt2){
		rect1.setBounds(rt1.getLeft()+rt1.getRoad().getLeft(),
				rt1.getRoad().getGame().getViewHeight()-rt1.getDistance()-rt1.getHeight(), 
				rt1.getWidth(), 
				rt1.getHeight()
				);
		rect2.setBounds(rt2.getLeft()+rt2.getRoad().getLeft(),
				rt2.getRoad().getGame().getViewHeight()-rt2.getDistance()-rt2.getHeight(), 
				rt2.getWidth(), 
				rt2.getHeight()
				);
		return rect1.intersection(rect2);
	}
}
