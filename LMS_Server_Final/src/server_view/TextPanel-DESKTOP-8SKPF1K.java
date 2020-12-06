package server_view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TextPanel extends JPanel {
	private JLabel text;
	
	public TextPanel () {
		this.text = new JLabel();
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setBounds(0, 12, 170, 27);
		text.setText("서버입니다.");
		
	}
}
