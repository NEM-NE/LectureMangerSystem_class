package Stubs;

import java.util.Vector;

import valueObject.VPost;

public interface ISPostManager {
	public Vector<VPost> getPosts();
	public void uploadPost(VPost post);
	public boolean like(VPost post, String userName);
}
