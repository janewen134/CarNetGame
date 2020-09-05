package cn.com.leadfar.game.view;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import cn.com.leadfar.game.model.CarNetGame;

/**
 * 把我们的视图看成是观察者
 * @author Administrator
 *
 */
public class CarNetGameMainFrame extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu = null;
	private JMenuItem jMenuItem = null;
	private CarNetGame game;  //  @jve:decl-index=0:
	
	//用来存储哪个按键被按下了
	private boolean[] pressed = new boolean[5];
	private int[] keyCodes = new int[]{KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_SPACE};
	
	//最后一个被按下的按键所在的索引
	private int currentKey = -1;
	
	//这个对象的锁用来控制对pressed/keyCodes/currentKey这些资源的访问！
	private Object lock = new Object();  //  @jve:decl-index=0:
	
	public CarNetGame getGame() {
		return game;
	}

	public void setGame(CarNetGame game) {
		this.game = game;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu("玩游戏");
			jMenu.add(getJMenuItem());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem("创建/加入游戏");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// TODO Auto-generated Event stub actionPerformed()
					//System.out.println("actionPerformed()"); 
					new CreateOrJoinGameDialog(CarNetGameMainFrame.this);
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CarNetGameMainFrame thisClass = new CarNetGameMainFrame();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * 这个方法，在被观察者发出通知的时候，将会被调用！
	 */
	@Override
	public void update(Observable o, Object arg) {
		//System.out.println("我知道了，马上更新！");
		repaint();
	}

	/**
	 * This is the default constructor
	 */
	public CarNetGameMainFrame() {
		super();
		initialize();
		new Thread(){
			public void run(){
				while(true){
					try {
						//表示当前有按键正在按下
						while(currentKey != -1){
							int pressedNoPointer = 0;
							for(int i =0; i<pressed.length ; i++){
								
								//判断哪个按键被按下
								if(pressed[i]){
									
									//当前按下的按键，并非正在发出事件的按键
									if(currentKey != i){
										pressedNoPointer += 1;
										simulateKeyPressed(keyCodes[i]);
										sleep(31); //模拟持续按键事件的中间的停顿
									}
									
								}
							}
							//当前只有一个按键被按下！
							if(pressedNoPointer == 0){
								synchronized (lock) {
									lock.wait(); //进入等待区，直到被通知才苏醒！
								}								
							}
						}
						synchronized (lock) {
							lock.wait(); //进入等待区，直到被通知才苏醒！
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		}.start();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(808, 659);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("飞车一族");
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				int code = e.getKeyCode();
				CarNetGameMainFrame.this.keyPressed(code);
				switch(code){
					case KeyEvent.VK_LEFT:{ 
						game.left();
					} break;
					case KeyEvent.VK_RIGHT: {
						game.right();
					} break;
					case KeyEvent.VK_UP: {
						game.forward();
					} break;
					case KeyEvent.VK_DOWN: {
						game.backward();
					} break;
					case KeyEvent.VK_SPACE:{ //按空格键
						game.bullet();
					}break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				CarNetGameMainFrame.this.keyReleased(code);
				switch(code){
					case KeyEvent.VK_LEFT:{ 
						
					}
					case KeyEvent.VK_RIGHT: {
						
					}
					case KeyEvent.VK_UP: {
						
					}
					case KeyEvent.VK_DOWN: {
						game.stopCar();
					} break;
				}
			}
		});
	}
	
	private void keyPressed(int code){
		synchronized (lock) {
			for(int i=0; i<keyCodes.length; i++){
				if(keyCodes[i] == code){
					pressed[i] = true;
					currentKey = i; //当前按键
					break;
				}
			}
			lock.notify();
		}
	}
	
	private void keyReleased(int code){
		synchronized (lock) {
			for(int i=0; i<keyCodes.length; i++){
				if(keyCodes[i] == code){
					pressed[i] = false;
					break;
				}
			}
			int currentKey = -1;
			for(int i=0; i<pressed.length; i++){
				if(pressed[i]){
					currentKey = i; //被按下的按键
				}
			}
			if(currentKey == -1){
				this.currentKey = currentKey; //置为-1
			}
			lock.notify();
		}
	}
	
	//模拟按键的发出！
	private void simulateKeyPressed(int code){
		switch(code){
			case KeyEvent.VK_LEFT:{ 
				game.left();
			};break;
			case KeyEvent.VK_RIGHT: {
				game.right();
			};break;
			case KeyEvent.VK_UP: {
				game.forward();
			};break;
			case KeyEvent.VK_DOWN: {
				game.backward();
			} break;
		}	
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new GamePanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
