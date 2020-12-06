package Stubs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import valueObject.VChat;

public class SChatManager {
	private Socket socket;
	//stream
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	public SChatManager(Socket socket) {
		try {
			this.socket = socket;
			this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
			this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
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
	
	public void sendMsg(String msg, String userName) {
		try {
			VChat chat = new VChat();
			chat.setMsg(msg);
			chat.setMyName(userName);
			this.objectOutputStream.writeObject(chat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VChat receiveMsg() {
		try {
			while(this.objectInputStream !=null) {
				return (VChat) objectInputStream.readObject();
			}
		} catch(SocketException e) {
			System.out.println("채팅창 종료....");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
