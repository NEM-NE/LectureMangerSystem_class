package Stubs;

import java.io.FileNotFoundException;
import java.util.Vector;

import valueObject.VStudent;

public interface ISStudentManager {
	public VStudent login(String userName, String password);
	public Vector<VStudent> getInfos() throws FileNotFoundException;
}
