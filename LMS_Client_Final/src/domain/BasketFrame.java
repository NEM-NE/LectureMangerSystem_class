package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import client.Manager;
import valueObject.VLecture;

public class BasketFrame extends JFrame {

	private JPanel contentPane;
	// sub-components
	private JButton btMoveToRight, btMoveToLeft;
	private JButton btSave;
	private JButton deleteBasket;
	private JButton deleteSincheong;
	LectureList basketList;
	LectureList sincheongList;

	private Manager manager;
	private String userName;
	/**
	 * Create the frame.
	 */
	public BasketFrame(Manager manager, String userName) {
		this.manager = manager;
		this.userName = userName;
		
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		
		setResizable(false);
		setBounds(100, 100, 1035, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.basketList = new LectureList(null, this.manager);
		JScrollPane basketListScrollPane = new JScrollPane();
		basketListScrollPane.setViewportView(basketList);
		basketListScrollPane.setBounds(18, 72, 426, 204);
		contentPane.add(basketListScrollPane);
		
		this.sincheongList = new LectureList(null, this.manager);
		JScrollPane sincheongListScrollPane = new JScrollPane();
		sincheongListScrollPane.setViewportView(sincheongList);
		sincheongListScrollPane.setBounds(577, 72, 426, 204);
		contentPane.add(sincheongListScrollPane);
		
		ActionHandler actionHandler = new ActionHandler();
		
		this.btMoveToLeft = new JButton("<<<");
		btMoveToLeft.addActionListener(actionHandler);
		btMoveToLeft.setBounds(458, 108, 105, 27);
		contentPane.add(btMoveToLeft);
		
		this.btMoveToRight = new JButton(">>>");
		btMoveToRight.addActionListener(actionHandler);
		btMoveToRight.setBounds(458, 190, 105, 27);
		contentPane.add(btMoveToRight);
		
		JLabel lblBasket = new JLabel("Basket");
		lblBasket.setBounds(200, 42, 62, 18);
		contentPane.add(lblBasket);
		
		JLabel lblSincheong = new JLabel("Sincheong");
		lblSincheong.setBounds(745, 42, 128, 18);
		contentPane.add(lblSincheong);
		
		this.deleteBasket = new JButton("\uC0AD\uC81C");
		deleteBasket.addActionListener(actionHandler);
		deleteBasket.setBounds(170, 288, 105, 27);
		contentPane.add(deleteBasket);
		
		this.deleteSincheong = new JButton("\uC0AD\uC81C");
		deleteSincheong.addActionListener(actionHandler);
		deleteSincheong.setBounds(745, 288, 105, 27);
		contentPane.add(deleteSincheong);
		
		this.btSave = new JButton("\uC800\uC7A5");
		btSave.addActionListener(actionHandler);
		btSave.setBounds(458, 288, 105, 27);
		contentPane.add(btSave);
		
		
		try {
			showRegistrations("basket" + userName, "sincheong" + userName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeLecture(String name) {
		if(name.equals("basket" + userName)) {
			this.basketList.removeSelectedLectures();
		}else if(name.equals("sincheong" + userName)) {
			this.sincheongList.removeSelectedLectures();
		}
		
	}
	
	public void showRegistrations(String basketFileName, String sincheongFileName) throws FileNotFoundException {
		this.basketList.showLectures(basketFileName);		
		this.sincheongList.showLectures(sincheongFileName);		
	}

	public void saveRegistrations(String basketFileName, String sincheongFileName) throws FileNotFoundException {
		this.basketList.saveLectures(basketFileName);		
		this.sincheongList.saveLectures(sincheongFileName);		
	}
	public void addLectures(Vector<VLecture> selectedLectures) {
		//여기서 강좌 중복 검사
		boolean check = this.basketList.checkDuplicate(selectedLectures, "basket" + userName);
		if(check) {
			System.out.println("중복 아님!");
			this.basketList.addLectures(selectedLectures);
			try {
				this.basketList.saveLectures("basket" + userName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("@@");
			JOptionPane.showMessageDialog(this, "중복된 강좌입니다.", "", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void moveToRight() {
		Vector<VLecture> selectedLectures = this.basketList.removeSelectedLectures();
		boolean check = this.sincheongList.checkDuplicate(selectedLectures, "sincheong" + userName);
		if(check) {
			this.sincheongList.addLectures(selectedLectures);
		}else {
			JOptionPane.showMessageDialog(this, "중복된 강좌입니다.", "", JOptionPane.ERROR_MESSAGE);
			addLectures(selectedLectures);
		}
		
	}
	
	private void moveToLeft() {
		Vector<VLecture> selectedLectures = this.sincheongList.removeSelectedLectures();
		this.basketList.addLectures(selectedLectures);	
		try {
			saveRegistrations("basket" + userName, "sincheong" + userName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class JFrameWindowClosingEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();
			frame.dispose();
			}
	}
	
	public class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(btMoveToRight)) {
				moveToRight();
			}
			else if (event.getSource().equals(btMoveToLeft)) {
				moveToLeft();
			}else if(event.getSource().equals(btSave)) {
				try {
					saveRegistrations("basket" + userName, "sincheong" + userName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(event.getSource().equals(deleteBasket)) {
				removeLecture("basket" + userName);
			}else if(event.getSource().equals(deleteSincheong)) {
				removeLecture("sincheong" + userName);
			}
		}
	}
}
