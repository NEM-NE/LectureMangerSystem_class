package valueObject;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

public class VLecture implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String professorName;
	private int credit;
	private String time;
	
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
	public void readFromFile(Scanner scanner) {
		this.id = scanner.nextInt();
		this.name = scanner.next();
		this.professorName = scanner.next();
		this.credit = scanner.nextInt();
		this.time = scanner.next();
	}
	public void writeToFile(PrintWriter printWriter) {
		printWriter.print(this.id);
		printWriter.print(" ");
		printWriter.print(this.name);
		printWriter.print(" ");
		printWriter.print(this.professorName);
		printWriter.print(" ");
		printWriter.print(this.credit);
		printWriter.print(" ");
		printWriter.println(this.time);
	}
}
