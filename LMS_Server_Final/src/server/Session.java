package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Vector;

import client.Message;
import global.ServerConstants;
import server_DAO.OnlineDao;
import server_Entity.OnlineEntity;
import server_manager.SDirectoryManager;
import server_manager.SLectureManager;
import server_manager.SOnlineManager;
import server_manager.SPostManager;
import server_manager.SStudentManager;

public class Session extends Thread {
	private Socket clientSocket;
	// stream
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	//manager
	private SStudentManager sStudentManager;
	private SDirectoryManager sDirectoryManager;
	private SLectureManager sLectureManager;
	private SPostManager sPostManager;
	private SOnlineManager sOnlineManager;
	private Vector<String> info;
	
	//constructor
	public Session(Socket socket) {
		//connect socket
		this.clientSocket = socket;
		this.sStudentManager = new SStudentManager("student", this.clientSocket.getInetAddress().toString(), this.getName());
		this.sDirectoryManager = new SDirectoryManager();
		this.sLectureManager = new SLectureManager();
		this.sPostManager = new SPostManager();
		this.sOnlineManager = new SOnlineManager();
	}

	public void initialize() {
		try {
			// create stream
			this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
			this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			//connect stream
			this.sStudentManager.initialize(objectOutputStream);
			this.sDirectoryManager.initialize(objectOutputStream);
			this.sLectureManager.initialize(objectOutputStream);
			this.sPostManager.initialize(objectOutputStream);
			this.sOnlineManager.initialize(objectOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pina() {
		try {
			this.sOnlineManager.pina();
			this.sPostManager.pina();
			this.sLectureManager.pina();
			this.sDirectoryManager.pina();
			this.sStudentManager.pina();
			this.objectInputStream.close();
			this.objectOutputStream.close();
			this.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			Object obj;
			while((obj = this.objectInputStream.readObject()) != null) {
				Message message = (Message) obj;
				int i = message.getObjectId();
				
				if(i == 0) {
					this.sStudentManager.run(message);
				}else if(i == 1) {
					this.sDirectoryManager.run(message);
				}else if(i == 2) {
					this.sLectureManager.run(message);
				}else if(i == 3) {
					this.sPostManager.run(message);
				}else if(i == 4) {
					this.sOnlineManager.run(message);
				} else {
					System.out.println(i + "ERROR!!!");
				}
			}
		} catch(SocketException e) {
			System.out.println(this.getName() + ": QUIT!!!!!!!");
			offLine();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void offLine() {
		try {
			Vector<OnlineEntity> onlines = new Vector<OnlineEntity>();
			
			//read online
			onlines = ServerConstants.CHECKING.readFromFile("online");
			
			//find my name
			Vector<OnlineEntity> real = new Vector<OnlineEntity>();
			for(int i = 0; i < onlines.size(); i++) {
				OnlineEntity temp = onlines.get(i);
				if(!this.getName().equals(temp.getThread())) {
					real.add(temp);
				}
			}
			
			//rewrite
			new OnlineDao().writeToFile(real, true, "online");	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
