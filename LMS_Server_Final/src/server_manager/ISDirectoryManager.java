package server_manager;

import java.io.FileNotFoundException;
import java.util.Vector;

import valueObject.VDirectory;

public interface ISDirectoryManager {
	public Vector<VDirectory> getDirectorues(String selection) throws FileNotFoundException;
}
