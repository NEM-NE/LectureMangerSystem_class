package Stubs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import client.Stub;
import valueObject.VPost;

public class SPostManager extends Stub implements ISPostManager{
	
	public SPostManager(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		super(objectInputStream, objectOutputStream);
		int postManager = 3;
		super.initialize(postManager);
	}
	
	//게시글 리스트를 불러올 메서드
	public Vector<VPost> getPosts() {
		// find enclosing method
		Object enclosedObject = new Object() {};	// 이 클래스를 둘러싸고 있는 함수는 뭐냐?
		
		return (Vector<VPost>)(invoke(enclosedObject));
	}
	
	//작성된 글을 보낼 메서드
	public void uploadPost(VPost post) {
		// find enclosing method
		Object enclosedObject = new Object() {};	// 이 클래스를 둘러싸고 있는 함수는 뭐냐?
		
		invoke(enclosedObject, post);
	}
	
	//좋아요 갯수를 올릴 메서드
	public boolean like(VPost post, String userName) {
		// find enclosing method
		Object enclosedObject = new Object() {};	// 이 클래스를 둘러싸고 있는 함수는 뭐냐?
		return (boolean)invoke(enclosedObject, post, userName);
	}

}
