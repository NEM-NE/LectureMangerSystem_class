package server_Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostEntity {
	private int id;
	private String title;
	private String author;
	private String text;
	private int good;
	
	public void readFromFile(ResultSet rs) {
		try {
			this.setId(rs.getInt("id"));
			this.setTitle(rs.getString("title"));
			this.setAuthor(rs.getString("author"));
			this.setText(rs.getString("text"));
			this.setLike(rs.getInt("good"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public int getLike() {
		return good;
	}
	public void setLike(int like) {
		this.good = like;
	}
}
