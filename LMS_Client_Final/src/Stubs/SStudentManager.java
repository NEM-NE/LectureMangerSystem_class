package Stubs;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import client.Stub;
import valueObject.VStudent;

public class SStudentManager extends Stub implements ISStudentManager{
	
	public SStudentManager(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		super(objectInputStream, objectOutputStream);
		int studentManager = 0;
		super.initialize(studentManager);
	}

	@Override
	public VStudent login(String userName, String password) {
		// find enclosing method
		Object enclosedObject = new Object() {};
		return (VStudent)(invoke(enclosedObject, userName, password));
	}

	@Override
	public Vector<VStudent> getInfos() throws FileNotFoundException {
		// find enclosing method
		Object enclosedObject = new Object() {};
		
		return (Vector<VStudent>)(invoke(enclosedObject));
	}
}
