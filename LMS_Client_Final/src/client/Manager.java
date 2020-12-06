package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Stubs.SChatManager;
import Stubs.SDirectoryManager;
import Stubs.SLectureManager;
import Stubs.SOnlineManager;
import Stubs.SPostManager;
import Stubs.SStudentManager;

public class Manager {
	//manager
	private SStudentManager sStudentManager;
	private SDirectoryManager sDirectoryManager;
	private SLectureManager sLectureManager;
	private SChatManager sChatManager;
	private SPostManager sPostManager;
	private SOnlineManager sOnlineManager;

	//socket
	private Socket socket;
	//stream
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	public Manager(Socket socket) {
		try {
			this.socket = socket;
			this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
			this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		this.sStudentManager = new SStudentManager(this.objectInputStream, this.objectOutputStream);
		this.sDirectoryManager = new SDirectoryManager(this.objectInputStream, this.objectOutputStream);
		this.sLectureManager = new SLectureManager(this.objectInputStream, this.objectOutputStream);
		this.sPostManager = new SPostManager(objectInputStream, objectOutputStream);
		this.sOnlineManager = new SOnlineManager(objectInputStream, objectOutputStream);
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
	
	//getter&setter
	public SOnlineManager getsOnlineManager() {
		return sOnlineManager;
	}
	public void setsOnlineManager(SOnlineManager sOnlineManager) {
		this.sOnlineManager = sOnlineManager;
	}
	public SPostManager getsPostManager() {
		return sPostManager;
	}
	public void setsPostManager(SPostManager sPostManager) {
		this.sPostManager = sPostManager;
	}
	public SStudentManager getsStudentManager() {
		return sStudentManager;
	}
	public void setsStudentManager(SStudentManager sStudentManager) {
		this.sStudentManager = sStudentManager;
	}

	public SDirectoryManager getsDirectoryManager() {
		return sDirectoryManager;
	}
	public void setsDirectoryManager(SDirectoryManager sDirectoryManager) {
		this.sDirectoryManager = sDirectoryManager;
	}

	public SLectureManager getsLectureManager() {
		return sLectureManager;
	}
	public void setsLectureManager(SLectureManager sLectureManager) {
		this.sLectureManager = sLectureManager;
	}
	
	public SChatManager getsChatManager() {
		return sChatManager;
	}

	public void setsChatManager(SChatManager sChatManager) {
		this.sChatManager = sChatManager;
	}
	
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}
}
