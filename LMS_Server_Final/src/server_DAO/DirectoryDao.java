package server_DAO;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import global.ServerConstants;
import server_Entity.DirectoryEntity;

public class DirectoryDao {	
	private Vector<DirectoryEntity> directories;	
	//jdbc
	private Connection conn;
	private Statement state;
	private ResultSet rs;
	
	public DirectoryDao() {
		try {
			this.directories = new Vector<DirectoryEntity>();
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
	
	public Vector<DirectoryEntity> readFromFile(String fileName) throws FileNotFoundException {
		try {
			this.directories = new Vector<DirectoryEntity>();
			String sql = "SELECT * FROM " + fileName;		// SQL문을 저장할 String
			this.rs = state.executeQuery(sql); // SQL문을 전달하여 실행
			
			while (rs.next()) {
				DirectoryEntity directory = new DirectoryEntity();
				directory.readFromFile(rs);
				this.directories.add(directory);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.directories;		
	}
}
