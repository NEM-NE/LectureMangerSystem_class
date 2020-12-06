package server_view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server_manager.SOnlineManager;
import valueObject.VOnline;

public class OnlineList extends JTable{
	// data model
	private DefaultTableModel tableModel;
	//
	private SOnlineManager onlineManager;
	// entity data
	private Vector<VOnline> onlines;
	
	//constructor
	public OnlineList() {
		// data model
		Vector<String> header = new Vector<String>();
		header.addElement("ID");
		header.addElement("IP");
		header.addElement("접속시간");
		this.tableModel = new DefaultTableModel(header, 0);
		this.setModel(this.tableModel);
		
		this.onlineManager = new SOnlineManager();
		
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void showOnlines() throws FileNotFoundException {
		try {
			this.tableModel.setRowCount(0);
			//copy onlineEntity
			this.onlines = this.onlineManager.getOnlines();
			
			Vector<String> rowData = null;
			for (VOnline online: onlines) {
				rowData = new Vector<String>();
				rowData.addElement(online.getId());
				rowData.addElement(online.getIp());
				rowData.addElement(online.getTime());
				this.tableModel.addRow(rowData);
			}
			this.updateUI();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ban(int row) {
		for(int i = 0; i < onlines.size(); i++) {
			System.out.println(onlines.get(i).getId());
		}
		VOnline temp = this.onlines.get(row);
		this.onlines.add(temp);
		this.onlineManager.writeToFile(this.onlines, "ban");
	}
}
