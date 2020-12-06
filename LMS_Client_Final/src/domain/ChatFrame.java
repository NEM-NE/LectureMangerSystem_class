package domain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Stubs.SChatManager;
import client.Manager;
import global.ClientConstants;
import valueObject.VChat;

public class ChatFrame extends JFrame {

	private JPanel contentPane;
	private JTextField msg;
	private JTextArea chat;
	private JTextArea visitList;
	
	private SChatManager sChatManager;
	private Manager manager;
	private String userName;
	private boolean bRunning;
	
	private Socket socket;
	
	public ChatFrame(String userName) {
		this.userName = userName;
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		
		setResizable(false);
		setBounds(100, 100, 668, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.msg = new JTextField();
		msg.setBounds(14, 461, 427, 31);
		contentPane.add(msg);
		msg.setColumns(10);
		
		//send msg
		ActionHandler actionHandler = new ActionHandler();
		JButton btnNewButton = new JButton("\uC804\uC1A1");
		btnNewButton.addActionListener(actionHandler);
		btnNewButton.setBounds(458, 462, 178, 29);
		contentPane.add(btnNewButton);
		
		this.chat = new JTextArea();
		chat.setEditable(false);
		JScrollPane scrollPane_1 = new JScrollPane(chat);	
		scrollPane_1.setBounds(14, 12, 622, 420);
		contentPane.add(scrollPane_1);
		
		
		//키보드에서 키 하나를 눌렀을때 자동으로 실행되는 메소드..: 콜백 메소드
		msg.addKeyListener(new KeyAdapter() { 
            public void keyReleased(KeyEvent e) {
                char keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ENTER) {
    				chat(msg.getText());
                } 
            } 
        }); 
		
		try {
			this.socket = new Socket(ClientConstants.IP, 10002);
			this.sChatManager = new SChatManager(this.socket);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			
		}
		
		this.chat.setText("채팅방에 입장하셨습니다!");
		Thread receiver = new Thread() {
			public void run() {
				bRunning = true;
				while(bRunning) {
					//show msg
					String past = chat.getText();
					VChat temp = sChatManager.receiveMsg();
					String userName = temp.getMyName();
					String mssage = temp.getMsg();
					String result = past + "\n" + userName + ": " + mssage; 
					chat.setText(result);
					msg.setText("");
				}
				
			}
		};
		
		receiver.start();
	}
	
	public void chat(String text) {
		//send
		if(this.userName == null) {
			this.userName = "";
		}
		sChatManager.sendMsg(text, this.userName);		
	}
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			chat(msg.getText());
		}
	}
	
	class JFrameWindowClosingEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();
			chat(userName + "님께서 퇴장하셨습니다.");
			frame.dispose();
			}
	}
}
