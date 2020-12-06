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
	
	//�Խñ� ����Ʈ�� �ҷ��� �޼���
	public Vector<VPost> getPosts() {
		// find enclosing method
		Object enclosedObject = new Object() {};	// �� Ŭ������ �ѷ��ΰ� �ִ� �Լ��� ����?
		
		return (Vector<VPost>)(invoke(enclosedObject));
	}
	
	//�ۼ��� ���� ���� �޼���
	public void uploadPost(VPost post) {
		// find enclosing method
		Object enclosedObject = new Object() {};	// �� Ŭ������ �ѷ��ΰ� �ִ� �Լ��� ����?
		
		invoke(enclosedObject, post);
	}
	
	//���ƿ� ������ �ø� �޼���
	public boolean like(VPost post, String userName) {
		// find enclosing method
		Object enclosedObject = new Object() {};	// �� Ŭ������ �ѷ��ΰ� �ִ� �Լ��� ����?
		return (boolean)invoke(enclosedObject, post, userName);
	}

}
