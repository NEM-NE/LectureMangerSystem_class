package server_manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import client.Message;
import server_DAO.LectureDao;
import server_Entity.LectureEntity;
import valueObject.VLecture;

public class SLectureManager implements ISLectureManager{
	private LectureDao lectureDao;
	//stream
	private ObjectOutputStream objectOutputStream;
	
	private Vector<VLecture> lectures;
	
	public SLectureManager() {
		this.lectureDao = new LectureDao();
	}
	
	public void initialize(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}
	
	public void pina() {
		try {
			this.objectOutputStream.close();
			this.lectureDao.unActive();
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
	public Vector<VLecture> getLectures(String fileName) throws FileNotFoundException {
		Vector<LectureEntity> lectureInfo =  this.lectureDao.readFromFile(fileName);

		//ValueObject에 Entity값 담기
		Vector<VLecture> result = new Vector<VLecture>();
		if (!lectureInfo.isEmpty()) {
			for (int i = 0; i < lectureInfo.size(); i++) {
				VLecture vLecture = new VLecture();
				vLecture.setCredit(lectureInfo.get(i).getCredit());
				vLecture.setId(lectureInfo.get(i).getId());
				vLecture.setName(lectureInfo.get(i).getName());
				vLecture.setProfessorName(lectureInfo.get(i).getProfessorName());
				vLecture.setTime(lectureInfo.get(i).getTime());
				result.add(vLecture);
			}
		}
		
		return result;
	}

	@Override
	public void saveLectures(String fileName, Vector<VLecture> lectures) throws FileNotFoundException {
		this.lectures = lectures;
		
		//Entity에 ValueObject값 담기
		Vector<LectureEntity> result = new Vector<LectureEntity>();
		for(int i = 0; i < this.lectures.size(); i++) {
			LectureEntity temp = new LectureEntity();
			temp.setCredit(this.lectures.get(i).getCredit());
			temp.setId(this.lectures.get(i).getId());
			temp.setName(this.lectures.get(i).getName());
			temp.setProfessorName(this.lectures.get(i).getProfessorName());
			temp.setTime(this.lectures.get(i).getTime());
			result.add(temp);
		}
		try {
			this.lectureDao.writeToFile(fileName, result, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
