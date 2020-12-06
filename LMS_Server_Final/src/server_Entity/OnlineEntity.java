package server_Entity;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OnlineEntity {
	private String id;
	private String ip;
	private String time;
	private String thread;
	
	public void readFromFile(ResultSet rs) {
		try {
			this.setId(rs.getString("id"));
			this.setIp(rs.getString("ip"));
			this.setTime(rs.getString("time"));
			this.setThread(rs.getString("thread"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToFile(PrintWriter printWriter) {
		if(this.id == null && this.thread == null) {
			printWriter.print("");
		}else {
			printWriter.print(this.id);
			printWriter.print(" ");
			printWriter.print(this.ip);
			printWriter.print(" ");
			printWriter.print(this.time);
			printWriter.print(" ");
			printWriter.println(this.thread);	
		}
	}
	
	//getter&setter
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
