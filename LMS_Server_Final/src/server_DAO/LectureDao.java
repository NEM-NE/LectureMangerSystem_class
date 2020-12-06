package server_DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import global.ServerConstants;
import server_Entity.LectureEntity;

public class LectureDao {	
	private Vector<LectureEntity> lectures;	
	//jdbc
	private Connection conn;
	private Statement state;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public LectureDao() {
		try {
			this.lectures = new Vector<LectureEntity>();
			Class.forName(ServerConstants.JDBC_DRIVER);
			this.conn = DriverManager.getConnection(ServerConstants.DB_URL, ServerConstants.USER_NAME, ServerConstants.PASSWORD);
			this.state = conn.createStatement();
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
			//null check
			if(pstmt != null) {
				pstmt.close();	
			}
			if(rs != null) {
				rs.close();
			}
			state.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<LectureEntity> readFromFile(String fileName) throws FileNotFoundException {
		this.lectures = new Vector<LectureEntity>();
		try {
			String sql = "SELECT * FROM " + fileName; 	// SQL문을 저장할 String
			this.rs = state.executeQuery(sql); // SQL문을 전달하여 실행
			
			while (rs.next()) {
				LectureEntity lecture = new LectureEntity();
				lecture.readFromFile(rs);
				lectures.add(lecture);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lectures;		
	}

	public void writeToFile(String fileName, Vector<LectureEntity> lectures, boolean override) throws IOException {
		try {
			if(override) {
				for (LectureEntity lecture: lectures) {
					String sql = "INSERT INTO " + fileName + "(id, name, professorName, credit, time)"
							+ "VALUES(?, ?, ?, ?, ?)";		// SQL문을 저장할 String
					this.pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, lecture.getId());
					pstmt.setString(2, lecture.getName());
					pstmt.setString(3, lecture.getProfessorName());
					pstmt.setInt(4, lecture.getCredit());
					pstmt.setString(5, lecture.getTime());
					
					pstmt.executeUpdate();
				}
			}else {
				//DELETE TABLE ALL ROW;
				String sql = "DELETE FROM " + fileName;	// SQL문을 저장할 String
				this.pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				
				//INSERT TABLE ROW;
				for (LectureEntity lecture: lectures) {
					sql = "INSERT INTO " + fileName + "(id, name, professorName, credit, time)" 
							+ "VALUES(?, ?, ?, ?, ?)";		// SQL문을 저장할 String
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, lecture.getId());
					pstmt.setString(2, lecture.getName());
					pstmt.setString(3, lecture.getProfessorName());
					pstmt.setInt(4, lecture.getCredit());
					pstmt.setString(5, lecture.getTime());
					
					pstmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
