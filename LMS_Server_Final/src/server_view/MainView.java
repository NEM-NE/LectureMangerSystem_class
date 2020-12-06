package server_view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.Session;

public class MainView extends JFrame{
	private JPanel contentPane;
	private JButton stuInfo;
	private JButton stuConn;
	private JButton lecSet;
	private JButton console;
	private JButton btnLogout;
	private TextPanel timePanel;
	private LoginView loginView;
	//frame
	private ConnFrame connFrame;
	private InfoFrame infoFrame;
	private SetUpFrame setUpFrame;
	
	private Vector<Session> session;
	private boolean state;
	/**
	 * Create the frame.
	 */
	
	public MainView() {
		setTitle("LMS-Server");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 341, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ActionHandler actionHandler = new ActionHandler();
		
		//logout
		this.btnLogout = new JButton("Logout");
		btnLogout.setBounds(212, 12, 97, 42);
		btnLogout.setActionCommand("Logout");
		btnLogout.addActionListener(actionHandler);
		contentPane.add(btnLogout);
		
		//time
		this.timePanel = new TextPanel();
		timePanel.setBounds(14, 3, 184, 51);
		contentPane.add(timePanel);
		timePanel.setLayout(null);
		
		//회원 정보 관리
		this.stuInfo = new JButton("\uD68C\uC6D0 \uC815\uBCF4 \uAD00\uB9AC");
		stuInfo.setBounds(14, 57, 295, 83);
		stuInfo.setActionCommand("StudentInformation");
		stuInfo.addActionListener(actionHandler);
		contentPane.add(stuInfo);
		
		//회원 연결 관리
		this.stuConn = new JButton("\uD68C\uC6D0 \uC5F0\uACB0 \uAD00\uB9AC");
		stuConn.setBounds(14, 152, 295, 83);
		stuConn.setActionCommand("StudentConnection");
		stuConn.addActionListener(actionHandler);
		contentPane.add(stuConn);
		
		//강좌 개설
		this.lecSet = new JButton("\uAC15\uC88C \uAC1C\uC124/\uC0AD\uC81C");
		lecSet.setBounds(14, 247, 295, 83);
		lecSet.setActionCommand("LectureSetUp");
		lecSet.addActionListener(actionHandler);
		contentPane.add(lecSet);
		
		//콘솔창
		this.console = new JButton("Console");
		console.setBounds(14, 346, 295, 83);
		console.setActionCommand("Console");
		console.addActionListener(actionHandler);
		contentPane.add(console);
		
		this.session = new Vector<Session>();
		this.state = true;
	}
	
	public void loginInit() {
		try {
			loginView = new LoginView(this);
			loginView.setVisible(true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSession(Session session) {
		this.session.add(session);
	}
	
	public boolean check() {
		return this.state;
	}
	
	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if(actionEvent.getActionCommand().equals("Logout")) {
				state = false;
				System.exit(EXIT_ON_CLOSE);			
			}else if(actionEvent.getActionCommand().equals("StudentInformation")) {
				infoFrame = new InfoFrame();
				infoFrame.initialize();
				infoFrame.setVisible(true);
			}else if(actionEvent.getActionCommand().equals("StudentConnection")) {
				connFrame = new ConnFrame(session);
				connFrame.initialize();
				connFrame.setVisible(true);
			}else if(actionEvent.getActionCommand().equals("LectureSetUp")) {
				setUpFrame = new SetUpFrame();
				setUpFrame.initialize();
				setUpFrame.setVisible(true);
			}else if(actionEvent.getActionCommand().equals("Console")) {
				
			}
			
		}
	
	}

}
