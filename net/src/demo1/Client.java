package demo1;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {
		//1. 서버에 연결요청을 보내고, 서버와 통신을 담당하는 Socket 생성하기
		//   서버의 ip주소와 포트번호를 설정한다.
		Socket socket = new Socket("192.168.0.29", 3000);
		
		//2. 소켓이 제공하는 스트림 가져오기
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		
		//3. 서버로 데이터 송신하기
		String data = "한결";
		out.write(data.getBytes());
		out.flush();
		
		//4. 서버가 보낸 데이터 수신하기
		byte[] buf = new byte[1024];
		int len = in.read(buf);
		String message = new String(buf, 0, len);
		System.out.println("### 수신한 메시지: " + message);
	}
}