package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Manager;
import global.ClientConstants;
import valueObject.VStudent;

public class MainView extends JFrame {

	private JPanel contentPane;
	private JButton Sincheong;
	private JButton basket;
	private JButton post;
	private JButton chat;
	private JButton web;
	private JButton btnLogout;
	private TextPanel timePanel;
	//frame
	private LoginView loginView;
	private DirectoryFrame directoryFrame;
	private BasketFrame basketFrame;
	private BoardFrame boardFrame;
	private ChatFrame chatFrame;
	//manager
	private Manager manager;
	
	private String userName;
	
	public MainView(Manager manager) {
		try {
			this.manager = manager;
			this.userName = "";
			loginView = new LoginView(this, this.manager);
			loginView.setVisible(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		setTitle("LMS" + ClientConstants.USERNAME);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 341, 584);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		ActionHandler actionHandler = new ActionHandler();
		//logout
		this.btnLogout = new JButton("Logout");
		btnLogout.setBounds(212, 12, 97, 42);
		btnLogout.setActionCommand("Logout");
		btnLogout.addActionListener(actionHandler);
		contentPane.add(btnLogout);
		
		//time
		this.timePanel = new TextPanel();
		timePanel.setBounds(14, 3, 184, 51);
		contentPane.add(timePanel);
		timePanel.setLayout(null);
		
		//수강신청
		this.Sincheong = new JButton("\uC218\uAC15\uC2E0\uCCAD");
		Sincheong.setBounds(14, 57, 295, 83);
		Sincheong.setActionCommand("Sincheong");
		Sincheong.addActionListener(actionHandler);
		contentPane.add(Sincheong);
		
		//장바구니/ 신청
		this.basket = new JButton("\uC7A5\uBC14\uAD6C\uB2C8 / \uC2E0\uCCAD");
		basket.setBounds(14, 152, 295, 83);
		basket.setActionCommand("basket");
		basket.addActionListener(actionHandler);
		contentPane.add(basket);
		
		//게시판
		this.post = new JButton("\uAC8C\uC2DC\uD310");
		post.setBounds(14, 247, 295, 83);
		post.setActionCommand("Post");
		post.addActionListener(actionHandler);
		contentPane.add(post);
		
		//채팅
		this.chat = new JButton("\uCC44\uD305");
		chat.setBounds(14, 342, 295, 83);
		chat.setActionCommand("Chat");
		chat.addActionListener(actionHandler);
		contentPane.add(chat);
		
		//민원센터
		this.web = new JButton("\uBBFC\uC6D0\uC13C\uD130");
		web.setBounds(14, 437, 295, 83);
		web.setActionCommand("Web");
		web.addActionListener(actionHandler);
		contentPane.add(web);
	}
	
	public void loginInit() {
		try { 
			loginView = new LoginView(this, this.manager);
			loginView.setVisible(true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setStuInfo(VStudent student) {
		this.userName = student.getUserName();
	}
	
	class ActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent actionEvent) {
			if(actionEvent.getActionCommand().equals("Logout")) {
				try {
					basketFrame = new BasketFrame(manager, userName);
					basketFrame.saveRegistrations("basket" + userName, "sincheong" + userName);
					System.exit(EXIT_ON_CLOSE);
				} catch (FileNotFoundException e) {	e.printStackTrace();}
				
			} else if (actionEvent.getActionCommand().equals("Sincheong")) {
				basketFrame = new BasketFrame(manager, userName);
				directoryFrame = new DirectoryFrame(basketFrame, manager);
				directoryFrame.initialize();
				directoryFrame.setVisible(true);
				
			} else if (actionEvent.getActionCommand().equals("basket")) {
				basketFrame = new BasketFrame(manager, userName);
				basketFrame.setVisible(true);
				
			} else if(actionEvent.getActionCommand().equals("Chat")) {
				chatFrame = new ChatFrame(userName);
				chatFrame.setVisible(true);
				
			} else if(actionEvent.getActionCommand().equals("Post")) {
				boardFrame = new BoardFrame(manager);
				boardFrame.setVisible(true);
				
			} else if(actionEvent.getActionCommand().equals("Web")) {
				  try {
					  String addr ="https://www.mju.ac.kr/mbs/mjukr/index.jsp";  //사이트주소
					  String[] cmd = new String[] {"rundll32", "url.dll", "FileProtocolHandler", addr};
					  Process process = new ProcessBuilder(cmd).start();
				  } catch (IOException e) {e.printStackTrace();}
			}
		}		
	}
}
