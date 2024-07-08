package demo1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws Exception {

		// 1. 클라이언트의 연결요청 접수받는 ServerSocket 생성하기
		//    3000번 포트를 사용하는 ServerSocket 생성
		ServerSocket server = new ServerSocket(3000);

		// 2. 클라이언트의 연결요청을 기다리고, 수락하기
		//    클라이언트의 연결요청이 올 때까지 기다린다.
		//    클라이언트의 연결요청이 오면 해당 클라이언트와 통신을 담당하는 Socket객체를 반환한다.
		while(true) {
			System.out.println("### 클라이언트의 연결요청을 대기중...");
			Socket socket = server.accept();
			System.out.println("### 클라이언트의 연결요청을 수락함...");
			String remoteIpAddress = socket.getInetAddress().getHostAddress();

			// 3. 소켓이 제공하는 스트림 획득하기
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			
			//4. 클라이언트가 보낸 메시지 수신하기
			byte[] buf = new byte[1024];
			int len = in.read(buf);
			String data = new String(buf, 0, len);
			System.out.println("### 수신된 메시지: " + data);
			
			// 5. 클라이언트로 응답 메시지 송신하기
			String message = data + "님 환영합니다.";
			out.write(message.getBytes()); // 클라이언트로 메시지 보내는 코드
			out.flush();
		}
	}
}