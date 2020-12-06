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
		this.text.setText("설정하고 싶은 메뉴를 선택해주세요.");
	}
}
