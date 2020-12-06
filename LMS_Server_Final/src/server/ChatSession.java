package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import javax.swing.JOptionPane;

import global.ServerConstants;
import valueObject.VChat;

public class ChatSession extends Thread{
	private Vector<ChatSession> clients;
	//socket
	private Socket socket;
	//stream
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	

	//constructor
	public ChatSession() {}
	
	public void initialize(Socket socket) {
		try {
			this.socket = socket;
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			this.objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pina() {
		try {
			this.objectInputStream.close();
			this.objectOutputStream.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void sendToAll(VChat chat) {
		
		for (int i = 0; i < this.clients.size(); i++) {
			try {
				ObjectOutputStream oos = this.clients.get(i).getObjectOutputStream();
				oos.writeObject(chat);
			} catch(SocketException e) {
				JOptionPane.showMessageDialog(null, "�������� ������ ������ϴ�.");
				System.exit(0);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	} // sendToAll
	
	public void run() {
		try {
			VChat chat;
			while((chat = (VChat) this.objectInputStream.readObject()) != null) {
				//�޼��� �ޱ�
				//vector<skeleton>ȣ��
				this.clients = ServerConstants.Chat;
				//send() of vector<skeleton>'s elements ȣ��
				sendToAll(chat);
			}
		} catch(SocketException e) {
			System.out.println("["+socket.getInetAddress() +":"+socket.getPort()+"]"+"���� ������ �����Ͽ����ϴ�.");
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//getter & setter
	public ObjectOutputStream getObjectOutputStream() {	return objectOutputStream;}
	public void setObjectOutputStream(ObjectOutputStream objectOutputStream)
		{	this.objectOutputStream = objectOutputStream;}

	public ObjectInputStream getObjectInputStream() { return objectInputStream;}
	public void setObjectInputStream(ObjectInputStream dataInputStream)
		{ this.objectInputStream = dataInputStream;}

}
