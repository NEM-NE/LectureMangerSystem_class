package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

import domain.MainView;
import global.ClientConstants;

/*
 * @Date: 2019-11-10
 * @author: Sung Bin IM
 * @description: 지난번 과제를 살짝 수정
 * @discuss: 서버와 연결된 클라이언트 but 어떻게 mainView와 연결할 것인가?
 */

public class Client {
	// attributes - server info
	private int portNumber;
	private String IP;
	//socket
	private Socket socket;
	//GUI
	private MainView mainView;
	//manager
	private Manager manager;

	public Client() {
		// set attributes
		this.IP = ClientConstants.IP;
		this.portNumber = ClientConstants.PORTNUMBER;
	}
	
	public void initialize() {
		try {
			// initialize socket
			this.socket = new Socket(IP, portNumber);
			this.manager = new Manager(this.socket);
		} catch(ConnectException e) {
			JOptionPane.showMessageDialog(null, "지금은 수강 신청 시간이 아닙니다.");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pina() {
	}
	
	public void run() {
		try {
			this.manager.initialize();
			this.mainView = new MainView(this.manager);
			this.mainView.setVisible(true);
			
		} catch (NullPointerException e) {	
			
		}
	}

}
