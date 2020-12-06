package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.SocketException;

import javax.swing.JOptionPane;

import client.Message;

public class Stub {
	// stream
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private int objectId;

	public Stub(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		// initialize connection
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
	}

	public void initialize(int obejctId) {
		this.objectId = obejctId;
	}

	public void pina() {
		try {
			this.objectInputStream.close();
			this.objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Object invoke(int objectId, String methodName, Class<?>[] parameterTypes, Object[] parameters) { // parameter��
		// �������� invoke parameter�� �޾ƿ� �͵��� ó���� ��
		Object object = null;
		try {
			Message message = new Message(objectId, methodName, parameterTypes, parameters);

			this.objectOutputStream.writeObject(message);
			// write data to socket
			object = this.objectInputStream.readObject();
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null, "�������� ������ ������ϴ�.");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get reply
		return object;
	}

	protected Object invoke(Object enclosedObject, Object... parameters) {
		Method method = enclosedObject.getClass().getEnclosingMethod(); // enclosed������Ʈ�� ���δ� �޼ҵ带 �θ�
		// set arguments
		String methodName = method.getName(); // ���δ� �޼ҵ忡�� �Լ��̸��� �θ�
		Class<?>[] parameterTypes = new Class<?>[parameters.length];

		for (int i = 0; i < parameters.length; i++) {
			Object obj = parameters[i];
			parameterTypes[i] = obj.getClass();
		}

		Object result = invoke(this.objectId, methodName, parameterTypes, parameters);
		return result;

	}
}
