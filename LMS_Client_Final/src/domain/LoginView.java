package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Stubs.SOnlineManager;
import Stubs.SStudentManager;
import client.Manager;
import global.ClientConstants;
import valueObject.VOnline;
import valueObject.VStudent;

public class LoginView extends JDialog {
	private static final long serialVersionUID = 1L;
	//manager
	private SStudentManager sStudentManager;
	// sub-components
	private JLabel icon;
	private JTextField tfUserName; 
	private JPasswordField tfPassword;
	private JButton btOk, btCancel;
	
	private MainView mainView;
	//manager
	private Manager manager;
	private SOnlineManager sOnlineManager;
	
	public LoginView(MainView frame, Manager manager) throws FileNotFoundException {		
		super(frame, ClientConstants.LOGINVIEW_TITLE, true);
		
		this.mainView = frame;
		this.manager = manager;
		this.sStudentManager = this.manager.getsStudentManager();
		this.sOnlineManager = this.manager.getsOnlineManager();
		
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		setBounds(100, 100, 471, 669);
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(frame);
		
		this.tfUserName = new JTextField();
		tfUserName.setText("\uC544\uC774\uB514\uB97C \uC785\uB825\uD558\uC2DC\uC624");
		tfUserName.setColumns(10);
		tfUserName.setBounds(97, 287, 265, 46);
		getContentPane().add(tfUserName);
		
		this.tfPassword = new JPasswordField();
		tfPassword.setBounds(97, 345, 266, 42);
		getContentPane().add(tfPassword);
		
		ActionHandler actionHandler = new ActionHandler();
		this.btOk = new JButton("\uB85C\uADF8\uC778");
		btOk.setActionCommand("OK");
		btOk.setBounds(97, 399, 134, 46);
		this.btOk.addActionListener(actionHandler);
		getContentPane().add(btOk);
		
		this.btCancel = new JButton("\uB2EB\uAE30");
		btCancel.setActionCommand("Cancel");
		btCancel.setBounds(228, 399, 134, 46);
		this.btCancel.addActionListener(actionHandler);
		getContentPane().add(btCancel);
		
		this.icon = new JLabel("New label");
		icon.setIcon(new ImageIcon("media/명지대.png"));
		icon.setBounds(97, 24, 248, 279);
		getContentPane().add(icon);
		
		tfPassword.addKeyListener(new KeyAdapter() { 
            public void keyReleased(KeyEvent e) {
                char keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ENTER) {
    				login();	
                } 
            } 
        }); 
	}
	
	//ok btn reaction method
	private void login() {
		String userName = this.tfUserName.getText();
		String password = 
				new String(this.tfPassword.getPassword());
		
		//암호화
		String newPassword = SHA256Util.getEncrypt(password, userName);
		
		VStudent student = 
				this.sStudentManager.login(userName, newPassword);
		if (student == null) {
			JOptionPane.showMessageDialog(this, "로그인에 실패하였습니다.", "", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				//check duplicate
				Vector<VOnline> temp = this.sOnlineManager.getOnlines();
				for(int i = 0; i < temp.size(); i++) {
					if(userName.equals(temp.get(i).getId())) {
						JOptionPane.showMessageDialog(this, "이미 사용 중입니다.", "", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				this.sOnlineManager.ok();
				//store userData in global
				ClientConstants.USERNAME = userName;
				//start main view
				this.mainView.setStuInfo(student);
				this.dispose();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	private void cancel() {
		System.exit(EXIT_ON_CLOSE);
	}
	
	//btn eventHandler
	class ActionHandler implements ActionListener {	
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getActionCommand().equals("OK")) {
				login();
			} else if (actionEvent.getActionCommand().equals("Cancel")) {
				cancel();
			}
		}
	}
	
	class JFrameWindowClosingEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(EXIT_ON_CLOSE);
		}
	}
}