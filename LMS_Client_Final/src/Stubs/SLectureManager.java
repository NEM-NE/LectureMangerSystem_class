package Stubs;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import client.Stub;
import valueObject.VLecture;

public class SLectureManager extends Stub implements ISLectureManager {
	
	public SLectureManager(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		super(objectInputStream, objectOutputStream);
		int lectureManager = 2;
		super.initialize(lectureManager);
	}

	@Override
	public Vector<VLecture> getLectures(String fileName) throws FileNotFoundException {
		// find enclosing method
		Object enclosedObject = new Object() {};
		Object obj = invoke(enclosedObject, fileName);
		
		return (Vector<VLecture>)obj;
	}

	@Override
	public void saveLectures(String fileName, Vector<VLecture> lectures) throws FileNotFoundException {
		//create new vector
		Vector<VLecture> result = new Vector<VLecture>();
		for (int i = 0; i < lectures.size(); i++) {
			VLecture vLecture = new VLecture();
			vLecture.setCredit(lectures.get(i).getCredit());
			vLecture.setId(lectures.get(i).getId());
			vLecture.setName(lectures.get(i).getName());
			vLecture.setProfessorName(lectures.get(i).getProfessorName());
			vLecture.setTime(lectures.get(i).getTime());
			result.add(vLecture);
		}
		
		// find enclosing method
		Object enclosedObject = new Object() {};	// 이 클래스를 둘러싸고 있는 함수는 뭐냐?

		invoke(enclosedObject, fileName, lectures);
	}
}
