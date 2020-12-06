package server_view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import global.ServerConstants;
import server_DAO.LectureDao;
import server_Entity.LectureEntity;
import valueObject.VLecture;


public class SetUpFrame extends JFrame {
	//handler
	private ListSelectionHandler listSelectionHandler;
	private ActionHandler actionHandler;
	
	private JPanel contentPane;
	private JTextField idTf;
	private JTextField titleTf;
	private JTextField nameTf;
	private JTextField creditTf;
	private JTextField timeTf;
	
	private JLabel main;
	private JLabel idLabel;
	private JLabel titleLabel;
	private JLabel nameLabel;
	private JLabel creditLabel;
	private JLabel timeLabel;
	private JLabel view;
	
	private JButton createBtn;
	private JButton deleteBtn;
	
	private DirectoryList campusList;
	private DirectoryList collegeList;
	private DirectoryList departmentList;
	private LectureList lectureList;
	private String fileName;
	
	public SetUpFrame() {
		setTitle("\uAC15\uC88C \uAC1C\uC124/\uC0AD\uC81C");
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		setBounds(100, 100, 739, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// listeners
		this.listSelectionHandler = new ListSelectionHandler();
		this.actionHandler = new ActionHandler();
		
		
		JScrollPane scrollPane = new JScrollPane();
		this.campusList = new DirectoryList(listSelectionHandler);
		scrollPane.setViewportView(this.campusList);
		scrollPane.setBounds(14, 47, 200, 113);
		contentPane.add(scrollPane);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		this.collegeList = new DirectoryList(listSelectionHandler);
		scrollPane_2.setViewportView(this.collegeList);
		scrollPane_2.setBounds(263, 47, 200, 113);
		contentPane.add(scrollPane_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		this.departmentList = new DirectoryList(listSelectionHandler);
		scrollPane_1.setViewportView(this.departmentList);
		scrollPane_1.setBounds(507, 47, 200, 113);
		contentPane.add(scrollPane_1);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		this.lectureList = new LectureList();
		scrollPane_3.setViewportView(this.lectureList);
		scrollPane_3.setBounds(14, 172, 626, 135);
		contentPane.add(scrollPane_3);
		
		this.main = new JLabel("\uAC15\uC88C \uAC1C\uC124");
		main.setFont(new Font("굴림", Font.BOLD, 18));
		main.setHorizontalAlignment(SwingConstants.CENTER);
		main.setBounds(210, 319, 313, 27);
		contentPane.add(main);
		
		this.idLabel = new JLabel("\uAC15\uC88CID");
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setBounds(14, 358, 152, 27);
		contentPane.add(idLabel);
		
		this.titleLabel = new JLabel("\uAC15\uC88C\uBA85");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(14, 407, 152, 27);
		contentPane.add(titleLabel);
		
		this.nameLabel = new JLabel("\uAD50\uC218\uBA85");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(14, 463, 152, 27);
		contentPane.add(nameLabel);
		
		this.creditLabel = new JLabel("\uD559\uC810");
		creditLabel.setHorizontalAlignment(SwingConstants.CENTER);
		creditLabel.setBounds(14, 514, 152, 27);
		contentPane.add(creditLabel);
		
		this.timeLabel = new JLabel("\uC2DC\uAC04");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setBounds(14, 569, 152, 27);
		contentPane.add(timeLabel);
		
		idTf = new JTextField();
		idTf.setBounds(210, 358, 460, 26);
		contentPane.add(idTf);
		idTf.setColumns(10);
		
		titleTf = new JTextField();
		titleTf.setColumns(10);
		titleTf.setBounds(210, 408, 460, 26);
		contentPane.add(titleTf);
		
		nameTf = new JTextField();
		nameTf.setColumns(10);
		nameTf.setBounds(210, 464, 460, 26);
		contentPane.add(nameTf);
		
		creditTf = new JTextField();
		creditTf.setColumns(10);
		creditTf.setBounds(210, 515, 460, 26);
		contentPane.add(creditTf);
		
		timeTf = new JTextField();
		timeTf.setColumns(10);
		timeTf.setBounds(210, 570, 460, 26);
		contentPane.add(timeTf);
		
		this.view = new JLabel("\uD559\uACFC \uC120\uD0DD");
		view.setFont(new Font("굴림", Font.BOLD, 16));
		view.setHorizontalAlignment(SwingConstants.CENTER);
		view.setBounds(301, 12, 126, 18);
		contentPane.add(view);
		
		this.createBtn = new JButton("\uAC1C\uC124");
		createBtn.addActionListener(actionHandler);
		createBtn.setBounds(301, 608, 141, 33);
		contentPane.add(createBtn);
		
		this.deleteBtn = new JButton("\uC0AD\uC81C");
		deleteBtn.addActionListener(actionHandler);
		deleteBtn.setBounds(645, 172, 62, 135);
		contentPane.add(deleteBtn);
	}
	
	public void initialize() {
		this.showDirectories(null);
	}
	
	private void showDirectories(Object source) {
		try {
			this.fileName = null;
			if (source == null) {
				this.campusList.showDirectories(ServerConstants.FILENAME_ROOT);
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
	
	public void create() {
		
		try {
			String fileName = this.departmentList.getSelectedFileName();
			LectureDao lectureDao = new LectureDao();
			//
			LectureEntity temp = new LectureEntity();
			temp.setId(Integer.parseInt(idTf.getText()));
			temp.setName(titleTf.getText());
			temp.setProfessorName(nameTf.getText());
			temp.setCredit(Integer.parseInt(creditTf.getText()));
			temp.setTime(timeTf.getText());
			//check null
			if(titleTf.getText().equals("") || nameTf.getText().equals("")
					|| timeTf.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "양식을 채워주세요.", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// add lecture in vector
			Vector<LectureEntity> lecture = new Vector<LectureEntity>();
			lecture.add(temp);
			//write
			lectureDao.writeToFile(fileName, lecture, true);
			//
			JOptionPane.showMessageDialog(this, "강좌가 개설되었습니다!", "", JOptionPane.OK_OPTION);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "양식에 맞게 채워주세요.", "", JOptionPane.ERROR_MESSAGE);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			this.lectureList.removeSelectedLectures();
			this.lectureList.saveLectures(this.fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(createBtn)) {
				create();
			}else if(event.getSource().equals(deleteBtn)) {
				delete();
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
