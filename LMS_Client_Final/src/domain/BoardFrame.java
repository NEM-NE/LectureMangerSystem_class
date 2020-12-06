package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Stubs.SPostManager;
import client.Manager;
import global.ClientConstants;
import valueObject.VPost;


public class BoardFrame extends JFrame {
	//component
	private JPanel contentPane;
	private JLabel titleTf;
	private JButton btnCreate;
	private JButton btnLikes;
	private JTextArea textArea;
	private JLabel lblAuthor;
	private JLabel lblLikes;
	private BoardList boardList;
	//manager
	private Manager manager;
	private SPostManager sPostManager;
	
	private Vector<VPost> posts;
	private boolean check;
	private VPost post;
	private BoardFrame boardFrame;
	private JLabel label;
	
	public BoardFrame(Manager manager) {
		this.manager = manager;
		this.check = false;
		boardFrame = this;
		setTitle("\uAC8C\uC2DC\uD310");
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		setBounds(100, 100, 672, 644);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ActionHandler actionHandler = new ActionHandler();
		
		this.boardList = new BoardList(manager);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(boardList);
		scrollPane.setBounds(14, 43, 626, 213);
		contentPane.add(scrollPane);
		boardList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = boardList.getSelectedRow();
				showPost(row);
			}
		});
		
		
		titleTf = new JLabel();
		titleTf.setText("\uC81C\uBAA9");
		titleTf.setBounds(14, 283, 369, 24);
		contentPane.add(titleTf);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(14, 319, 626, 206);
		contentPane.add(scrollPane_1);
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);
		
		lblAuthor = new JLabel("\uC791\uC131\uC790");
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthor.setBounds(370, 286, 134, 18);
		contentPane.add(lblAuthor);
		
		lblLikes = new JLabel("\uC88B\uC544\uC694");
		lblLikes.setHorizontalAlignment(SwingConstants.CENTER);
		lblLikes.setBounds(545, 286, 95, 18);
		contentPane.add(lblLikes);
		
		btnCreate = new JButton("\uAC8C\uC2DC\uAE00 \uC791\uC131");
		btnCreate.addActionListener(actionHandler);
		btnCreate.setBounds(67, 537, 162, 48);
		contentPane.add(btnCreate);
		
		btnLikes = new JButton("\uC88B\uC544\uC694");
		btnLikes.addActionListener(actionHandler);
		btnLikes.setBounds(396, 537, 162, 48);
		contentPane.add(btnLikes);
		
		label = new JLabel("\uC88B\uC544\uC694:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(516, 286, 42, 18);
		contentPane.add(label);
		
		this.sPostManager = this.manager.getsPostManager();
		
		showPosts();
	}
	
	protected void showPost(int row) {
		this.post = this.posts.get(row);
		this.titleTf.setText("제목: " + post.getTitle());
		this.textArea.setText(post.getText());
		this.lblAuthor.setText("작성자: " + post.getAuthor());
		this.lblLikes.setText(Integer.toString(post.getLike()));
	}

	public void showPosts() {
		try {
			this.posts = this.boardList.showPosts();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public void like() {
		int like = Integer.parseInt(this.lblLikes.getText());
		++like;
		this.post.setLike(like);
		if(this.sPostManager.like(this.post, ClientConstants.USERNAME)) {
			this.lblLikes.setText(Integer.toString(like));
			showPosts();
		}else {
			JOptionPane.showMessageDialog(null, "이미 좋아요를 누르셨습니다.");
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
			if(event.getSource().equals(btnCreate)) {
				PostFrame postFrame = new PostFrame(manager, boardFrame);
				postFrame.setVisible(true);
			}else if(event.getSource().equals(btnLikes)) {
				like();
			}
		}
	}
}
