package server;

/*
 * @Date: 2019-11-10
 * @author: Sung Bin IM
 * @description:
 * @discuss: control만 클라에 연결하고 DAO와 entity는 서버에 있는 걸로 연결해서 진행할 예정 이유: 공유자원 개념
 */

public class Main {

	public static void main(String[] args) {
		Server server = new Server();
		ChatServer chatServer = new ChatServer();
		
		server.initialize();
		chatServer.initialize();
		
		chatServer.start();
		server.run();
		
		chatServer.pina();
		server.pina();
		
	}

}
