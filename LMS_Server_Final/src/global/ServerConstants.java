package global;

import java.util.Vector;

import server.ChatSession;
import server.Session;
import server_DAO.OnlineDao;
import server_manager.SStudentManager;

public class ServerConstants {
	public final static int PORTNUMBER = 10001;
	public final static String IP = "localhost";

	public final static String LOGINVIEW_TITLE = "Login";
	public final static String FILENAME_ROOT = "root";
	
	public final static Vector<ChatSession> Chat = new Vector<ChatSession>();
	
	//jdbc
	public final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //����̹�
	public final static String DB_URL = "jdbc:mysql://localhost/lms?&useSSL=false&useUnicode=true"
			+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //������ DB ����
	public final static String USER_NAME = "root"; //DB�� ������ ����� �̸��� ����� ����
	public final static String PASSWORD = "!2Dlatjdqls"; //������� ��й�ȣ�� ����� ����
	
	public final static OnlineDao CHECKING = new OnlineDao();
	public final static Vector<Session> CLIENT = new Vector<Session>();
	
	public static String ID;
	public static SStudentManager DUPLICATE;
	public static boolean Open;
}
