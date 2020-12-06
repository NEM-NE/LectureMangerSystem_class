package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Stubs.SPostManager;
import client.Manager;
import global.ClientConstants;
import valueObject.VPost;

public class PostFrame extends JFrame {

	private JPanel contentPane;
	private JTextField titleTf;
	private JLabel lblTitle;
	private JLabel lblPost;
	private JButton btnCreate;
	private JTextArea textArea;

	private Manager manager;
	//controller
	private SPostManager sPostManager;
	
	private BoardFrame boardFrame;
	
	public PostFrame(Manager manager, BoardFrame boardFrame) {
		this.manager = manager;
		this.boardFrame =boardFrame;
		
		setTitle("\uAC8C\uC2DC\uAE00 \uC791\uC131");
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		setBounds(100, 100, 725, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//listener
		ActionHandler actionHandler = new ActionHandler();
		
		lblTitle = new JLabel("\uC81C\uBAA9");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(42, 53, 97, 28);
		contentPane.add(lblTitle);
		
		titleTf = new JTextField();
		titleTf.setHorizontalAlignment(SwingConstants.CENTER);
		titleTf.setBounds(142, 53, 475, 28);
		contentPane.add(titleTf);
		titleTf.setColumns(10);
		
		lblPost = new JLabel("\uAE00 \uB0B4\uC6A9");
		lblPost.setHorizontalAlignment(SwingConstants.CENTER);
		lblPost.setBounds(198, 108, 290, 28);
		contentPane.add(lblPost);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 149, 679, 280);
		contentPane.add(scrollPane);
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		btnCreate = new JButton("\uC791\uC131");
		btnCreate.setBounds(254, 455, 192, 54);
		btnCreate.addActionListener(actionHandler);
		contentPane.add(btnCreate);
		
		//controller
		this.sPostManager = this.manager.getsPostManager();
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
				//null check
				if(lblTitle.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "양식에 맞게 채워주세요.");
				}else if(textArea.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "양식에 맞게 채워주세요.");
				}else {
					VPost post = new VPost();
					post.setAuthor(ClientConstants.USERNAME);
					post.setText((textArea.getText()));
					post.setTitle(titleTf.getText());
					sPostManager.uploadPost(post);
					boardFrame.showPosts();
					dispose();
				}
			}
		}
	}
}
