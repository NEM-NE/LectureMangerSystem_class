package valueObject;

import java.io.Serializable;

public class VPost implements Serializable{
	private int id;
	private String title;
	private String author;
	private String text;
	private int good;
	
	
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
