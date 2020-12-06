package server_Entity;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LectureEntity {
	private int id;
	private String name;
	private String professorName;
	private int credit;
	private String time;

	public void readFromFile(ResultSet rs) {
		try {
			this.setId(rs.getInt("id"));
			this.setName(rs.getString("name"));
			this.setProfessorName(rs.getString("professorName"));
			this.setCredit(rs.getInt("credit"));
			this.setTime(rs.getString("time"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getId() { return id;	}
	public void setId(int id) {	this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getProfessorName() { return professorName; }
	public void setProfessorName(String professorName) { this.professorName = professorName; }

	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
