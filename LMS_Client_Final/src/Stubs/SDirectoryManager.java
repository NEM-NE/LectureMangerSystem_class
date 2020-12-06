package Stubs;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import client.Stub;
import valueObject.VDirectory;

public class SDirectoryManager extends Stub implements ISDirectoryManager{
	
	public SDirectoryManager(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		super(objectInputStream, objectOutputStream);
		int directoryManager = 1;
		super.initialize(directoryManager);
	}

	@Override
	public Vector<VDirectory> getDirectorues(String selection) throws FileNotFoundException {
		// find enclosing method
		Object enclosedObject = new Object() {};
		
		return (Vector<VDirectory>)(invoke(enclosedObject, selection));
	}
}
