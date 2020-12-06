package server_manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import client.Message;
import server_DAO.PostDao;
import server_Entity.PostEntity;
import valueObject.VPost;

public class SPostManager implements ISPostManager{
	private PostDao postDao;
	//stream
	private ObjectOutputStream objectOutputStream;
	//constructor
	public SPostManager() {
		this.postDao = new PostDao();
	}
	
	public void initialize(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}
	
	public void pina() {
		try {
			this.objectOutputStream.close();
			this.postDao.unActive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(Message message) {
		try {
			
			// process request
			Method method = this.getClass().getDeclaredMethod(message.getMethodName(), message.getParameterTypes());
			Object result = method.invoke(this, message.getParameters());
			// return result
			this.objectOutputStream.writeObject(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.getTargetException().printStackTrace();
		}
	}
	
	public Vector<VPost> getPosts() {
		try {
			Vector<PostEntity> postss =  this.postDao.readFromDB();
			
			//ValueObject에 Entity값 담기
			Vector<VPost> result = new Vector<VPost>();
			if (!postss.isEmpty()) {
				for (int i = 0; i < postss.size(); i++) {
					VPost vPost = new VPost();
					vPost.setId(postss.get(i).getId());
					vPost.setTitle(postss.get(i).getTitle());
					vPost.setAuthor(postss.get(i).getAuthor());
					vPost.setText(postss.get(i).getText());
					vPost.setLike(postss.get(i).getLike());
					result.add(vPost);
				}
			}
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void uploadPost(VPost post) {
		this.postDao.writeToDB(post);
	}
	
	public boolean like(VPost post, String userName) {
		this.postDao.createLike(post);
		
		
		//테이블이 있지만 좋아요 누른 사람이 중복이 아닌 경우
		if(this.postDao.inputData(post, userName)) {
			this.postDao.updateLike(post);
			return true;
		//테이블이 있지만 좋아요 누른 사람이 중복인 경우
		}else if(!this.postDao.inputData(post, userName)) {
			return false;
		}
		return false;

	}
	
}
