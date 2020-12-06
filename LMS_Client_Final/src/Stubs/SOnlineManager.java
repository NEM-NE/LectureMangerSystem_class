package Stubs;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import client.Stub;
import valueObject.VOnline;

public class SOnlineManager extends Stub implements ISOnlineManager{
	
	public SOnlineManager(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		super(objectInputStream, objectOutputStream);
		int onlineManager = 4;
		super.initialize(onlineManager);
	}

	@Override
	public Vector<VOnline> getOnlines() throws FileNotFoundException {
		// find enclosing method
		Object enclosedObject = new Object() {};	// 이 클래스를 둘러싸고 있는 함수는 뭐냐?
		
		return (Vector<VOnline>)(invoke(enclosedObject));
	}

	@Override
	public void writeToFile(Vector<VOnline> onlines, String fileName) {
		
	}
	
	public void ok() {
		// find enclosing method
		Object enclosedObject = new Object() {};	// 이 클래스를 둘러싸고 있는 함수는 뭐냐?
		
		invoke(enclosedObject);
	}

}
