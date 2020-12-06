package server_manager;

import java.io.FileNotFoundException;
import java.util.Vector;

import server_Entity.OnlineEntity;
import valueObject.VOnline;

public interface ISOnlineManager {
	public Vector<VOnline> getOnlines() throws FileNotFoundException;
	public void writeToFile(Vector<VOnline> onlines, String fileName);
	public void ok();
}
