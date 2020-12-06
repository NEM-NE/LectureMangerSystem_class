package valueObject;

import java.io.Serializable;

public class VChat implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String msg;
	private String myName;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
}
