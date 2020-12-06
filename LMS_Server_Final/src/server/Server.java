package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.Date;

import global.ServerConstants;
import server_view.MainView;

public class Server {
	//socket
	private ServerSocket serverSocket;
	//GUI
	private MainView mainView;
	
	public void initialize() {
		try {
			this.serverSocket = new ServerSocket(ServerConstants.PORTNUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pina() {
		// close connection
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			//GUI set
			this.mainView = new MainView();
			this.mainView.loginInit();
			this.mainView.setVisible(true);
			
			// wait & create socket;
			boolean brunning = this.mainView.check();
			while (brunning) {
				// client request for connection
				Socket clientSocket = serverSocket.accept(); // 블락 되어 있다가 연결되면 소켓 생성
																
				// create service for client
				Session session = new Session(clientSocket);
				session.initialize();
				//send sessionInfo to server
				this.mainView.setSession(session);
				session.start();				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
