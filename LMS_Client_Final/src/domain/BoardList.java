package domain;

import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Stubs.SPostManager;
import client.Manager;
import valueObject.VPost;

public class BoardList extends JTable{
	private static final long serialVersionUID = 1L;
	// data model
	private DefaultTableModel tableModel;
	//manager
	private Manager manager;
	//controller
	private SPostManager sPostManager;
	// entity data
	private Vector<VPost> posts;
	
	public BoardList(Manager manager) {
		// attributes
		this.manager = manager;
		// data model
		Vector<String> header = new Vector<String>();
		header.addElement("번호");
		header.addElement("제목");
		header.addElement("작성자");
		header.addElement("좋아요");
		this.tableModel = new DefaultTableModel(header, 0);
		this.setModel(this.tableModel);
		// add listener
		
		//controller
		this.sPostManager = this.manager.getsPostManager();
		
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public Vector<VPost> showPosts() throws FileNotFoundException {
		this.tableModel.setRowCount(0);
		
		this.posts = this.sPostManager.getPosts();
		Vector<String> rowData = null;
		for (VPost post: posts) {
			rowData = new Vector<String>();
			rowData.addElement(Integer.toString(post.getId()));
			rowData.addElement(post.getTitle());
			rowData.addElement(post.getAuthor());
			rowData.addElement(Integer.toString(post.getLike()));
			this.tableModel.addRow(rowData);
		}
		this.updateUI();
		
		return this.posts;
	}
}
