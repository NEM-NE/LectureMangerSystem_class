package server_DAO;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import global.ServerConstants;
import server_Entity.OnlineEntity;
import server_Entity.StudentEntity;

public class StudentDao {
	
	private Vector<StudentEntity> studentList;
	//jdbc
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	public StudentDao(String cls) throws FileNotFoundException {
		try {
			this.studentList = new Vector<StudentEntity>();
			Class.forName(ServerConstants.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(ServerConstants.DB_URL, ServerConstants.USER_NAME, ServerConstants.PASSWORD);
			this.state = conn.createStatement();
			
			this.readFromFile(cls);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void unActive() {
		try {
			rs.close();
			state.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<StudentEntity> readFromFile(String cls) throws FileNotFoundException {
		try {
			this.studentList = new Vector<StudentEntity>();

			String sql; // SQL문을 저장할 String
			sql = "SELECT * FROM " + cls;
			this.rs = state.executeQuery(sql); // SQL문을 전달하여 실행

			while (rs.next()) {
				StudentEntity student = new StudentEntity();
				student.readFromFile(rs);
				this.studentList.addElement(student);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		return studentList;
	}
	
	public StudentEntity login(String userName, String password) {
		try {
			
			//check ban list
			OnlineDao test = new OnlineDao();
			Vector<OnlineEntity> temp = test.readFromFile("ban");
			for(int i = 0; i < temp.size(); i++) {
				if(userName.equals(temp.get(i).getId())) {
					return null;
				}
			}
			
			//inspect login
			for(StudentEntity student: this.studentList) {
				if (student.getUserName().equals(userName) &&
						student.getPassword().equals(password)) {
					return student;				
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
