package domain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.Manager;
import global.ClientConstants;

public class DirectoryFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	//handler
	private ListSelectionHandler listSelectionHandler;
	private ActionHandler actionHandler;
	//
	private DirectoryList campusList;
	private DirectoryList collegeList;
	private DirectoryList departmentList;
	private LectureList lectureList;
	private BasketFrame basketFrame;
	//GUI
	private JPanel contentPane;
	//manager
	private Manager manager;
	
	public DirectoryFrame(BasketFrame basketFrame, Manager manager) {
		this.manager = manager;
		
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		
		setResizable(false);
		setBounds(100, 100, 674, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.basketFrame = basketFrame;
		
		// listeners
		this.listSelectionHandler = new ListSelectionHandler();
		this.actionHandler = new ActionHandler();
		
		JScrollPane scrollPane = null;
		this.campusList = new DirectoryList(this.listSelectionHandler, this.manager);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.campusList);
		scrollPane.setBounds(33, 12, 192, 185);
		contentPane.add(scrollPane);
		
		this.collegeList = new DirectoryList(this.listSelectionHandler, this.manager);
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setViewportView(this.collegeList);
		scrollPane1.setBounds(239, 12, 192, 185);
		contentPane.add(scrollPane1);
		
		this.departmentList = new DirectoryList(this.listSelectionHandler, this.manager);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.departmentList);
		scrollPane.setBounds(445, 12, 192, 185);
		contentPane.add(scrollPane);
		
		this.lectureList = new LectureList(this.listSelectionHandler, this.manager);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.lectureList);
		scrollPane.setBounds(33, 209, 607, 232);
		contentPane.add(scrollPane);
		
		JButton btSincheong = new JButton("\uC2E0\uCCAD");
		btSincheong.setBounds(254, 453, 177, 42);
		btSincheong.addActionListener(actionHandler);
		btSincheong.setActionCommand("Sincheong");
		contentPane.add(btSincheong);
	}
	
	public void initialize() {
		this.showDirectories(null);
	}
	
	private void showDirectories(Object source) {
		try {
			String fileName = null;
			if (source == null) {
				this.campusList.showDirectories(ClientConstants.FILENAME_ROOT);
			} else if (source.equals(this.campusList)) {
				fileName = this.campusList.getSelectedFileName();
				this.collegeList.showDirectories(fileName);
				fileName = this.collegeList.getSelectedFileName();
				this.departmentList.showDirectories(fileName);
				fileName = this.departmentList.getSelectedFileName();
				this.lectureList.showLectures(fileName);
			} else if (source.equals(this.collegeList)) {
				fileName = this.collegeList.getSelectedFileName();
				this.departmentList.showDirectories(fileName);			
				fileName = this.departmentList.getSelectedFileName();
				this.lectureList.showLectures(fileName);
			} else if (source.equals(this.departmentList)) {
				fileName = this.departmentList.getSelectedFileName();				
				fileName = this.departmentList.getSelectedFileName();
				this.lectureList.showLectures(fileName);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	public void showRegistrations() {
		this.basketFrame.addLectures(this.lectureList.getSelectedLectures());
	}
	
	public class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Sincheong")) {
				showRegistrations();
			}
		}
	}
	
	class JFrameWindowClosingEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();
			frame.dispose();
			}
		}
	
	public class ListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			showDirectories(event.getSource());
		}
	}
}
