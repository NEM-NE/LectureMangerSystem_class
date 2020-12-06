package server_Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DirectoryEntity {
	private int id;
	private String name;
	private String fileName;
	
	public int getId() { return id;	}
	public void setId(int id) {	this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }

	public void readFromFile(ResultSet rs) {
		try {
			this.setId(rs.getInt("id"));
			this.setName(rs.getString("name"));
			this.setFileName(rs.getString("fileName"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
