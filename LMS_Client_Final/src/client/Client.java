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
 * @description: ������ ������ ��¦ ����
 * @discuss: ������ ����� Ŭ���̾�Ʈ but ��� mainView�� ������ ���ΰ�?
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
			JOptionPane.showMessageDialog(null, "������ ���� ��û �ð��� �ƴմϴ�.");
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
