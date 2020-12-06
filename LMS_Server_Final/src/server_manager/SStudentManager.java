package server_manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import client.Message;
import global.ServerConstants;
import server_DAO.OnlineDao;
import server_DAO.StudentDao;
import server_Entity.OnlineEntity;
import server_Entity.StudentEntity;
import valueObject.VStudent;

public class SStudentManager implements ISStudentManager{
	private StudentDao studentDao;
	//stream
	private ObjectOutputStream objectOutputStream;
	
	private String threadName;
	private String ip;
	
	public SStudentManager(String cls) {
		try {
			this.studentDao = new StudentDao(cls);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SStudentManager(String cls, String ip, String threadName) {
		try {
			this.studentDao = new StudentDao(cls);
			this.ip = ip;
			this.threadName = threadName;
			ServerConstants.DUPLICATE = this;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initialize(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}
	
	public void pina() {
		try {
			this.objectOutputStream.close();
			this.studentDao.unActive();
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
	
	@Override
	public VStudent login(String userName, String password) {
		StudentEntity student = 
				this.studentDao.login(userName, password);
		
		//ValueObject에 Entity값 담기
		VStudent stuInfo = null;
		if(student != null) {
			stuInfo = new VStudent();
			stuInfo.setUserName(student.getUserName());
			stuInfo.setPassword(student.getPassword());
			stuInfo.setId(student.getId());
			stuInfo.setDepartmentId(student.getDepartmentId());
			ServerConstants.ID = student.getUserName();
		}
		return stuInfo;
	}
	
	//for Server
	public Vector<VStudent> getInfos() throws FileNotFoundException {
		Vector<StudentEntity> stuInfo =  this.studentDao.readFromFile("student");
		//ValueObject에 Entity값 담기
		Vector<VStudent> result = new Vector<VStudent>();
		if (!stuInfo.isEmpty()) {
			for (int i = 0; i < stuInfo.size(); i++) {
				VStudent vStudent = new VStudent();
				vStudent.setId(stuInfo.get(i).getId());
				vStudent.setName(stuInfo.get(i).getName());
				vStudent.setUserName(stuInfo.get(i).getUserName());
				vStudent.setDepartmentId(stuInfo.get(i).getDepartmentId());
				result.add(vStudent);
			}
		}
		
		return result;
	}
	
	public void writeInfo(String id) {
		try {
			Vector<OnlineEntity> write = new Vector<OnlineEntity>();
			OnlineEntity temp = new OnlineEntity();
			
			Date today = new Date();
			SimpleDateFormat time = new SimpleDateFormat("a_hh:mm:ss");

			temp.setId(id);
			temp.setIp(ip);
			temp.setTime(time.format(today));
			temp.setThread(threadName);
			write.add(temp);
			
			if(temp.getThread() != null) {
				OnlineDao wr = new OnlineDao();
				wr.writeToFile(write, false, "online");	
			}
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
