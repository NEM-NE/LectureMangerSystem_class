package server_manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Vector;

import client.Message;
import global.ServerConstants;
import server_DAO.OnlineDao;
import server_Entity.OnlineEntity;
import valueObject.VOnline;

public class SOnlineManager implements ISOnlineManager{
	private OnlineDao onlineDao;
	//stream
	private ObjectOutputStream objectOutputStream;
	
	public SOnlineManager() {
		this.onlineDao = new OnlineDao();
	}
	
	public void initialize(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}
	
	public void pina() {
		try {
			this.objectOutputStream.close();
			this.onlineDao.unActive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(Message message) {
		try {
			
			// process request
			Method method = this.getClass().getDeclaredMethod(message.getMethodName(), message.getParameterTypes());
			Object result = method.invoke(this, message.getParameters());
			// return result
			this.objectOutputStream.writeObject(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.getTargetException().printStackTrace();
		}
	}
	
	public Vector<VOnline> getOnlines() throws FileNotFoundException {
		Vector<OnlineEntity> Info =  this.onlineDao.readFromFile("online");
		
		//ValueObject에 Entity값 담기
		Vector<VOnline> result = new Vector<VOnline>();
		if (!Info.isEmpty()) {
			for (int i = 0; i < Info.size(); i++) {
				VOnline vOnline = new VOnline();
				vOnline.setId(Info.get(i).getId());
				vOnline.setIp(Info.get(i).getIp());
				vOnline.setTime(Info.get(i).getTime());
				vOnline.setThread(Info.get(i).getThread());
				result.add(vOnline);
			}
		}
		
		return result;
	}
	
	public void writeToFile(Vector<VOnline> onlines, String fileName) {
		try {
			//copy onlineEntity
			Vector<OnlineEntity> temp = new Vector<OnlineEntity>();
			for(int i = 0; i < onlines.size(); i++) {
				OnlineEntity temp3 = new OnlineEntity();
				temp3.setId(onlines.get(i).getId());
				temp3.setIp(onlines.get(i).getIp());
				temp3.setTime(onlines.get(i).getTime());
				temp3.setThread(onlines.get(i).getThread());
				temp.add(temp3);
			}
			
			onlineDao.writeToFile(temp, false, fileName);
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
	
	public void ok() {
		ServerConstants.DUPLICATE.writeInfo(ServerConstants.ID);
	}
}
