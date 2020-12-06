package domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Stubs.SLectureManager;
import client.Manager;
import domain.DirectoryFrame.ListSelectionHandler;
import valueObject.VLecture;

public class LectureList extends JTable {
	private static final long serialVersionUID = 1L;
	// controller
	private SLectureManager sLectureManager;
	// data model
	private DefaultTableModel tableModel;
	// entity data
	private Vector<VLecture> lectures;
	// selected lectures
	private Vector<VLecture> selectedLectures;
	
	private Manager manager;
	
	public LectureList(ListSelectionHandler listSelectionHandler, Manager manager) {
		// attributes
		this.manager = manager;
		// data model
		Vector<String> header = new Vector<String>();
		header.addElement("강좌명");
		header.addElement("담당교수");
		this.tableModel = new DefaultTableModel(header, 0);
		this.setModel(this.tableModel);
		// add listener
		//controller
		this.sLectureManager = this.manager.getsLectureManager();
		
		
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void initialize(){
	}
	
	//강좌 중복도 확인하기
	public boolean checkDuplicate(Vector<VLecture> lectures, String name) {
		try {
			Vector<VLecture> basket = this.sLectureManager.getLectures(name);
					
			for(int a = 0; a < lectures.size(); a++) {
				for(int b = 0; b < basket.size(); b++) {
					if(lectures.get(a).getName().equals(basket.get(b).getName()) ==true) {
						return false;
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void addLectures(Vector<VLecture> lectures) {
		Vector<String> rowData = null;
		for (VLecture lecture: lectures) {
			rowData = new Vector<String>();
			rowData.addElement(lecture.getName());
			rowData.addElement(lecture.getProfessorName());
			this.tableModel.addRow(rowData);
			this.lectures.addElement(lecture);
		}
		this.updateUI();
	}
	
	public Vector<VLecture> getSelectedLectures() {
		this.selectedLectures = new Vector<VLecture>();
		for (int i=0; i<this.getRowCount(); i++) {
			if (this.isRowSelected(i)) {
				this.selectedLectures.addElement(this.lectures.get(i));
			}
		}
		return this.selectedLectures; 
	}
	
	public Vector<VLecture> removeSelectedLectures() {
		this.selectedLectures = new Vector<VLecture>();
		for (int i=this.getRowCount()-1; i>=0; i--) {
			if (this.isRowSelected(i)) {
				this.selectedLectures.addElement(this.lectures.get(i));
				this.lectures.remove(i);
				this.tableModel.removeRow(i);
			}
		}
		this.updateUI();	
		return this.selectedLectures; 
	}


	public void showLectures(String fileName) throws FileNotFoundException {
		try {
			this.tableModel.setRowCount(0);
			if (fileName == null) {
				return;
			}
			this.lectures = this.sLectureManager.getLectures(fileName);
			Vector<String> rowData = null;
			for (VLecture lecture: lectures) {
				rowData = new Vector<String>();
				rowData.addElement(lecture.getName());
				rowData.addElement(lecture.getProfessorName());
				this.tableModel.addRow(rowData);
			}
			this.updateUI();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveLectures(String fileName) throws FileNotFoundException {
		this.sLectureManager.saveLectures(fileName, this.lectures);
	}
}
