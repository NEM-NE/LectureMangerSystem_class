package server_manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import client.Message;
import server_DAO.DirectoryDao;
import server_Entity.DirectoryEntity;
import valueObject.VDirectory;

public class SDirectoryManager implements ISDirectoryManager {
	private DirectoryDao directoryDao;
	// stream
	private ObjectOutputStream objectOutputStream;

	public SDirectoryManager() {
		this.directoryDao = new DirectoryDao();
	}

	public void initialize(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public void pina() {
		try {
			this.objectOutputStream.close();
			this.directoryDao.unActive();
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
	public Vector<VDirectory> getDirectorues(String selection) throws FileNotFoundException {
		Vector<DirectoryEntity> dirInfo = this.directoryDao.readFromFile(selection);

		// ValueObject에 Entity값 담기
		Vector<VDirectory> result = new Vector<VDirectory>();
		//null check
		if (!dirInfo.isEmpty()) {
			for (int i = 0; i < dirInfo.size(); i++) {
				VDirectory vDirectory = new VDirectory();
				vDirectory.setFileName(dirInfo.get(i).getFileName());
				vDirectory.setId(dirInfo.get(i).getId());
				vDirectory.setName(dirInfo.get(i).getName());
				result.add(vDirectory);
			}
		}
		
		return result;
	}
}
