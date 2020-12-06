package valueObject;

import java.io.Serializable;
import java.util.Scanner;

public class VStudent implements Serializable{
	private static final long serialVersionUID = 1L;
	// attributes
	private int id;	//학번
	private String name;
	private String userName;	//id
	private String password;	//비번
	private int departmentId;	//학과
	
	// constructors & destructor
	public VStudent(){
	}
	// read & write
	public void readFromFile(Scanner scanner) {
		this.setId(scanner.nextInt());
		this.setName(scanner.next());
		this.setUserName(scanner.next());
		this.setPassword(scanner.next());
		this.setDepartmentId(scanner.nextInt());
	}
	void writeToFile() {}
	
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
	
}
