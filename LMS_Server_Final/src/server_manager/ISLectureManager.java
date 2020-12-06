package server_manager;

import java.io.FileNotFoundException;
import java.util.Vector;

import valueObject.VLecture;

public interface ISLectureManager {
	public Vector<VLecture> getLectures(String fileName) throws FileNotFoundException;
	
	public void saveLectures(String fileName, Vector<VLecture> lectures) throws FileNotFoundException;
}
