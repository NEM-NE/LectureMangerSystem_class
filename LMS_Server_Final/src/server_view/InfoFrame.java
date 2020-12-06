package server_view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;


public class InfoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel basketLabel;
	private JLabel sincheongLabel;
	private JButton deleteBasket;
	private JButton deleteSincheong;
	
	private StuList info;
	private LectureList basket;
	private LectureList sincheong;
	private String userName;
	
	public InfoFrame() {
		
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		
		setBounds(100, 100, 793, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ActionHandler actionHandler = new ActionHandler();
		
		JScrollPane scrollPane = null;
		this.info = new StuList();
		info.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = info.getSelectedRow();
					userName = info.getUserName(row);
					showDirectories(row);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.info);
		scrollPane.setBounds(14, 12, 747, 209);
		contentPane.add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.basket = new LectureList();
		basket.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrollPane_1.setViewportView(this.basket);
		scrollPane_1.setBounds(14, 269, 370, 303);
		contentPane.add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		this.sincheong = new LectureList();
		scrollPane_2.setViewportView(this.sincheong);
		scrollPane_2.setBounds(391, 269, 370, 303);
		contentPane.add(scrollPane_2);
		
		this.basketLabel = new JLabel("\uC7A5\uBC14\uAD6C\uB2C8");
		basketLabel.setHorizontalAlignment(SwingConstants.CENTER);
		basketLabel.setBounds(83, 241, 62, 18);
		contentPane.add(basketLabel);
		
		this.sincheongLabel = new JLabel("\uC2E0\uCCAD");
		sincheongLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sincheongLabel.setBounds(449, 241, 62, 18);
		contentPane.add(sincheongLabel);
		
		this.deleteBasket = new JButton("\uC0AD\uC81C");
		deleteBasket.setBounds(203, 236, 91, 23);
		deleteBasket.addActionListener(actionHandler);
		contentPane.add(deleteBasket);
		
		this.deleteSincheong = new JButton("\uC0AD\uC81C");
		deleteSincheong.setBounds(582, 236, 91, 23);
		deleteSincheong.addActionListener(actionHandler);
		contentPane.add(deleteSincheong);
	}
	
	public void initialize() {
		this.showDirectories(null);
	}
	
	public void removeLecture(String name){
		try {
			if (name.equals("basket" + userName)) {
				this.basket.removeSelectedLectures();
				this.basket.saveLectures("basket" + userName);
				
			} else if (name.equals("sincheong" + userName)) {
				this.sincheong.removeSelectedLectures();
				this.sincheong.saveLectures("sincheong" + userName);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void showDirectories(Object source) {
		try {
			String fileName = null;
			if (source == null) {
				this.info.showInfos();
			} else {
				fileName = this.info.getUserName((int)source);
				this.basket.showLectures("basket"+fileName);
				this.sincheong.showLectures("sincheong"+fileName);
			}
		} catch (FileNotFoundException e) {
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
			if(event.getSource().equals(deleteBasket)) {
				removeLecture("basket" + userName);
			}else if(event.getSource().equals(deleteSincheong)) {
				removeLecture("sincheong" + userName);
			}
		}
	}
}
