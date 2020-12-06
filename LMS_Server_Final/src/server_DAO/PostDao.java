package server_DAO;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Vector;

import global.ServerConstants;
import server_Entity.LectureEntity;
import server_Entity.PostEntity;
import valueObject.VPost;

public class PostDao {
	private Vector<PostEntity> posts;	
	//jdbc
	private Connection conn;
	private Statement state;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public PostDao() {
		try {
			this.posts = new Vector<PostEntity>();
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
	
	public Vector<PostEntity> readFromDB() throws FileNotFoundException {
		this.posts = new Vector<PostEntity>();
		try {
			String sql = "SELECT * FROM post"; 	// SQL문을 저장할 String
			this.rs = state.executeQuery(sql); // SQL문을 전달하여 실행
			
			while (rs.next()) {
				PostEntity post = new PostEntity();
				post.readFromFile(rs);
				posts.add(post);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posts;		
	}
	
	public void writeToDB(VPost post) {
			try {
				String sql = "INSERT INTO post (title, author, text)"
						+ "VALUES(?, ?, ?)";		// SQL문을 저장할 String
				this.pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, post.getTitle());
				pstmt.setString(2, post.getAuthor());
				pstmt.setString(3, post.getText());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

	public void updateLike(VPost post) {
		try {
			String sql = "UPDATE post SET good = ? WHERE id = ?";
			this.pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, post.getLike());
			pstmt.setInt(2, post.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createLike(VPost post) {
		try {
			String sql ="CREATE TABLE post" + post.getId() + "("
                    + "id INT NOT NULL auto_increment,"
                    + "name VARCHAR(45) NOT NULL,"
                    + "primary key(id))";
			state.executeUpdate(sql);
		} catch(SQLSyntaxErrorException e) {
			return;
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean inputData(VPost post, String userName) {
		try {
			if(checkFromDB(post, userName)) {
				String sql = "INSERT INTO post" + post.getId()  +"(name)"
						+ "VALUES(?)";		// SQL문을 저장할 String
				this.pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userName);
				pstmt.executeUpdate();
			}else {
				return false;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}
	
	private boolean checkFromDB(VPost post, String userName) {
		this.posts = new Vector<PostEntity>();
		try {
			String sql = "SELECT * FROM post" + post.getId(); 	// SQL문을 저장할 String
			this.rs = state.executeQuery(sql); // SQL문을 전달하여 실행
			
			while (rs.next()) {
				String temp = rs.getString("name");
				if(userName.equals(temp)) {
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;	
	}
	
	
	
}
