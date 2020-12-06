package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import global.ServerConstants;

public class ChatServer extends Thread{
	private ServerSocket serverSocket;
	private Vector<ChatSession> clients;

	public void initialize() {
		try {
			this.serverSocket = new ServerSocket(10002);
			System.out.println("CHATSERVER INIT!!!!");
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
			System.out.println("CHAT SERVER RUN!!!!");
			// wait & create socket;
			boolean brunning = true;
			while (brunning) {
				// client request for connection
				Socket clientSocket = serverSocket.accept(); // 블락 되어 있다가 연결되면 소켓 생성
				
				ChatSession chatSession = new ChatSession();
				chatSession.initialize(clientSocket);
				ServerConstants.Chat.add(chatSession);
				chatSession.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
