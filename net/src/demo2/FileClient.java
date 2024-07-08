package demo2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class FileClient {

	public static void main(String[] args) throws Exception {
		
		Socket socket = new Socket("192.168.0.29", 5000);
	
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		DataInputStream in = new DataInputStream(socket.getInputStream());
		
		// 서버로 요청 보내기
		// 	파일 다운로드 요청 보내기
		// 	"DOWN:파일명"
		out.writeUTF("DOWN:a.pdf");
		out.flush();
		
		// 서버의 응답 처리하기
		String data = in.readUTF();
		String[] values = data.split(":");
		String command = values[0];
		String filename = values[1];
		
		if ("FILE".equals(command)) {
			System.out.println("### 파일다운로드를 수신함");
			
			FileOutputStream fos = new FileOutputStream("src/demo2/" + filename);
			
// 파일 사이즈 읽어오기
			long size = in.readLong();
			// 총 읽어온 사이즈
			int readSize = 0;
			byte[] buf = new byte[1024];
			int len = 0;
// 파일 데이터 읽어오기
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
				// 읽어온 사이즈를 누적한다.
				readSize += len;
				// 읽어온 사이즈와 파일사이즈가 동일하면 탈출한다.
				if (readSize == size) {
					break;
				}
			}
			fos.close();
			
			
		} else if ("DEL".equals(command)) {
			System.out.println("### 파일삭제 정보를 수신함");
			
		} else if ("INFO".equals(command)) {
			System.out.println("### 파일정보를 수신함");
			
			long filesize = in.readLong();
			
			System.out.println("### 명령어: " + command);
			System.out.println("### 파일명: " + filename);
			System.out.println("### 파일사이즈: " + filesize);
		}
		
		
	}
}
