package server_view;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import server.Session;

public class ConnFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnDisConnect;
	private JButton btnBan;
	private JButton refresh;	
	
	private OnlineList onlineList;
	private Vector<Session> onClients;

	private int row;

	public ConnFrame(Vector<Session> session) {
		this.onClients = session;
		
		this.addWindowListener(new JFrameWindowClosingEventHandler());
		setResizable(false);
		setBounds(100, 100, 684, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		this.onlineList = new OnlineList();
		onlineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		onlineList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row = onlineList.getSelectedRow();
			}
		});
		scrollPane.setViewportView(this.onlineList);
		scrollPane.setBounds(14, 38, 638, 238);
		contentPane.add(scrollPane);
		
		this.lblNewLabel = new JLabel("\uC811\uC18D \uC911\uC778 \uD68C\uC6D0\uC815\uBCF4");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(230, 8, 201, 18);
		contentPane.add(lblNewLabel);
		
		ActionHandler actionHandler = new ActionHandler();
		
		this.btnDisConnect = new JButton("\uC5F0\uACB0 \uB04A\uAE30");
		btnDisConnect.setBounds(72, 318, 131, 59);
		btnDisConnect.addActionListener(actionHandler);
		contentPane.add(btnDisConnect);
		
		this.btnBan = new JButton("Ban");
		btnBan.setBounds(467, 318, 131, 59);
		btnBan.addActionListener(actionHandler);
		contentPane.add(btnBan);
		
		this.refresh = new JButton("\uC0C8\uB85C \uACE0\uCE68");
		refresh.setBounds(270, 318, 131, 59);
		refresh.addActionListener(actionHandler);
		contentPane.add(refresh);
	}

	public void initialize() {
		try {
			this.onlineList.showOnlines();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void disconnect(int row) {
		Session session = this.onClients.get(row);
		this.onClients.remove(row);
		session.pina();
	}
	
	class JFrameWindowClosingEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			JFrame frame = (JFrame)e.getWindow();
			frame.dispose();
		}
	}
	
	public class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(btnDisConnect)) {
				disconnect(row);
				try {
					onlineList.showOnlines();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(event.getSource().equals(btnBan)) {
				System.out.println(row);
				onlineList.ban(row);
				disconnect(row);
				try {
					onlineList.showOnlines();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(event.getSource().equals(refresh)) {
				try {
					onlineList.showOnlines();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
