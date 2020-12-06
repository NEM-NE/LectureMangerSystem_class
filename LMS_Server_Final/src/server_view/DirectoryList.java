package server_view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import server_manager.SDirectoryManager;
import server_view.SetUpFrame.ListSelectionHandler;
import valueObject.VDirectory;

public class DirectoryList extends JList<String> {
	private static final long serialVersionUID = 1L;
	// view
	private Vector<String> listData;
	//manager
	private SDirectoryManager sDirectoryManager;
	
	private Vector<VDirectory> directories;
	
	public DirectoryList(ListSelectionHandler listSelectionHandler) {

		// subject
		this.listData = new Vector<String>();
		this.setListData(this.listData);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// add listener
		this.addListSelectionListener(listSelectionHandler);
		
		this.sDirectoryManager = new SDirectoryManager();
	}
	
	public void initialize(){
	}
	
	public String getSelectedFileName() {
		int index = this.getSelectedIndex();
		VDirectory directory = this.directories.get(index);
		return directory.getFileName();
	}
	
	public void showDirectories(String fileName) throws FileNotFoundException {
		try {
			this.listData.clear();
			if (fileName == null) {
				return;
			}

			this.directories = this.sDirectoryManager.getDirectorues(fileName);
			
			if(this.directories != null) {
				for (VDirectory directory: directories) {
					this.listData.add(directory.getName());
				}	
			}
			this.setSelectedIndex(0);
			this.updateUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
