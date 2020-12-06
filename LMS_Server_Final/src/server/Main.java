package server;

/*
 * @Date: 2019-11-10
 * @author: Sung Bin IM
 * @description:
 * @discuss: control�� Ŭ�� �����ϰ� DAO�� entity�� ������ �ִ� �ɷ� �����ؼ� ������ ���� ����: �����ڿ� ����
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
