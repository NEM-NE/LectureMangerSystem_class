package server_DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import global.ServerConstants;
import server_Entity.OnlineEntity;

public class OnlineDao {
	private Vector<OnlineEntity> onlines;	
	//jdbc
	private Connection conn;
	private Statement state;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public OnlineDao() {
		try {
			this.onlines = new Vector<OnlineEntity>();
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
	
	public Vector<OnlineEntity> readFromFile(String fileName) throws FileNotFoundException {
		try {
			this.onlines = new Vector<OnlineEntity>();
			
			String sql = "SELECT * FROM " + fileName; 	// SQL문을 저장할 String
			this.state = conn.createStatement();
			this.rs = state.executeQuery(sql);
			
			while (rs.next()) {
				OnlineEntity online = new OnlineEntity();
				online.readFromFile(rs);
				onlines.add(online);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // SQL문을 전달하여 실행

		return onlines;		
	}

	public void writeToFile(Vector<OnlineEntity> onlines, boolean rewrite, String fileName)
			throws IOException, SQLException {
		
		if(onlines.isEmpty() && rewrite) {
			//DELETE TABLE ALL ROW;
			String sql = "DELETE FROM " + fileName;	// SQL문을 저장할 String
			this.pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
		}else if(rewrite || fileName.equals("ban")) {
			//DELETE TABLE ALL ROW;
			String sql = "DELETE FROM " + fileName;	// SQL문을 저장할 String
			this.pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			//INSERT TABLE ROW;
			for (OnlineEntity online: onlines) {
				sql = "INSERT INTO " + fileName + "(id, ip, time, thread)"
						+ "VALUES(?, ?, ?, ?)";		// SQL문을 저장할 String
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, online.getId());
				pstmt.setString(2, online.getIp());
				pstmt.setString(3, online.getTime());
				pstmt.setString(4, online.getThread());
				
				pstmt.executeUpdate();
			}
		}else {
			//override lecture dao write 1
			for (OnlineEntity online: onlines) {
				String sql = "INSERT INTO " + fileName + "(id, ip, time, thread)"
						+ "VALUES(?, ?, ?, ?)";		// SQL문을 저장할 String
				this.pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, online.getId());
				pstmt.setString(2, online.getIp());
				pstmt.setString(3, online.getTime());
				pstmt.setString(4, online.getThread());
				
				pstmt.executeUpdate();
			}
		}
		
	}
}
