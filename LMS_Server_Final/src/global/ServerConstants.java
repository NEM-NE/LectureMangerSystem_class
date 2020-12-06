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
	public final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버
	public final static String DB_URL = "jdbc:mysql://localhost/lms?&useSSL=false&useUnicode=true"
			+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //접속할 DB 서버
	public final static String USER_NAME = "root"; //DB에 접속할 사용자 이름을 상수로 정의
	public final static String PASSWORD = "!2Dlatjdqls"; //사용자의 비밀번호를 상수로 정의
	
	public final static OnlineDao CHECKING = new OnlineDao();
	public final static Vector<Session> CLIENT = new Vector<Session>();
	
	public static String ID;
	public static SStudentManager DUPLICATE;
	public static boolean Open;
}
