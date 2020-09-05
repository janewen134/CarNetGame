package cn.com.leadfar.game.view;

import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import cn.com.leadfar.game.model.CarNetGameClient;
import cn.com.leadfar.game.model.CarNetGameServer;

public class CreateOrJoinGameDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCheckBox server = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField playerName = null;
	private JTextField roadLength = null;
	private JTextField serverAddress = null;
	private JButton jButton = null;

	/**
	 * @param owner
	 */
	public CreateOrJoinGameDialog(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(331, 275);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("创建或加入游戏");
		this.setVisible(true);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(28, 147, 78, 30));
			jLabel3.setText("服务器地址");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(19, 67, 86, 25));
			jLabel2.setText("是否做服务器");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(27, 105, 79, 30));
			jLabel1.setText("跑道长度");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(29, 26, 76, 27));
			jLabel.setText("游戏者姓名");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getServer(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getPlayerName(), null);
			jContentPane.add(getRoadLength(), null);
			jContentPane.add(getServerAddress(), null);
			jContentPane.add(getJButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes server	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getServer() {
		if (server == null) {
			server = new JCheckBox();
			server.setBounds(new Rectangle(119, 69, 21, 21));
		}
		return server;
	}

	/**
	 * This method initializes playerName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPlayerName() {
		if (playerName == null) {
			playerName = new JTextField();
			playerName.setBounds(new Rectangle(117, 27, 155, 27));
		}
		return playerName;
	}

	/**
	 * This method initializes roadLength	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRoadLength() {
		if (roadLength == null) {
			roadLength = new JTextField();
			roadLength.setBounds(new Rectangle(119, 106, 154, 26));
		}
		return roadLength;
	}

	/**
	 * This method initializes serverAddress	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getServerAddress() {
		if (serverAddress == null) {
			serverAddress = new JTextField();
			serverAddress.setBounds(new Rectangle(118, 148, 155, 29));
		}
		return serverAddress;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(85, 187, 156, 35));
			jButton.setText("创建或加入游戏");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//创建游戏服务器或创建游戏客户端
					if("".equals(playerName.getText().trim())){
						JOptionPane.showMessageDialog(CreateOrJoinGameDialog.this, "请输入游戏者姓名");
						return;
					}
					if(server.isSelected()){ //要做服务器
						if("".equals(roadLength.getText().trim())){
							JOptionPane.showMessageDialog(CreateOrJoinGameDialog.this, "请输入跑道长度");
							return;
						}
						CarNetGameMainFrame frame = (CarNetGameMainFrame)CreateOrJoinGameDialog.this.getOwner();
						CarNetGameServer game = new CarNetGameServer(playerName.getText(),Integer.parseInt(roadLength.getText())); 
						frame.setGame(game);
						game.addObserver(frame);
						frame.repaint();
					}else{
						if("".equals(serverAddress.getText().trim())){
							JOptionPane.showMessageDialog(CreateOrJoinGameDialog.this, "服务器地址不能为空");
							return;
						}
						CarNetGameMainFrame frame = (CarNetGameMainFrame)CreateOrJoinGameDialog.this.getOwner();
						CarNetGameClient client = new CarNetGameClient(playerName.getText(),serverAddress.getText());
						frame.setGame(client);
						client.addObserver(frame);
						frame.repaint();
					}
					//关闭对话框
					CreateOrJoinGameDialog.this.dispose();
				}
			});
		}
		return jButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
