package client;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int objectId; //왜 integer냐면 array에다가 집어넣고 나중에 인덱스를 집어넣어 찾아서 쓸것
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] parameters;

	public Message() {
	}

	public Message(int objectId, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
		this.objectId = objectId;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.parameters = parameters;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
}
