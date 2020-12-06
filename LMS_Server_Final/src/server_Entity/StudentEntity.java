package server_Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author choi.sungwoon
 * @version 1.0
 * @created 11-9-2017 오전 10:03:02
 */
public class StudentEntity {
	// attributes
	private int id;	//학번
	private String name;
	private String userName;	//id
	private String password;	//비번
	private int departmentId;	//학과
	
	// constructors & destructor
	public StudentEntity(){
	}
	// read & write
	public void readFromFile(ResultSet rs) {
		try {
			this.setId(rs.getInt("id"));
			this.setName(rs.getString("name"));
			this.setUserName(rs.getString("userName"));
			this.setPassword(rs.getString("password"));
			this.setDepartmentId(rs.getInt("departmentId"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// getters & setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
}//end Student