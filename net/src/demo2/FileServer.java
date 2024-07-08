package demo2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

	public void start() {
		System.out.println("### Starting FileServer ...");
		try (ServerSocket server = new ServerSocket(5000)) {
			while (true) {
				System.out.println("### Waiting for connecting ...");
				Socket socket = server.accept();
				System.out.println("### Accepted connect request ...");
				
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				
				String message = in.readUTF();
				System.out.println("### client message: " + message);
				
				String[] values = message.split(":");
				String command = values[0];
				String filename = values[1];
				System.out.println("### command: " + command);
				System.out.println("### filename: " + filename);
				
				if ("DOWN".equals(command)) {
					파일다운로드(filename, in, out);
				} else if ("DEL".equals(command)) {
					파일삭제(filename, in, out);
				} else if ("INFO".equals(command)) {
					파일정보(filename, in, out);
				}
				
				System.out.println();
			}
			
			
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void 파일다운로드(String filename, 
			DataInputStream in, DataOutputStream out) throws IOException {
		System.out.println("### 파일 다운로드 요청 처리");
		
		// 파일 다운로드 응답 보내기
		// 응답보내기
// 명령어:파일명 보내기
		out.writeUTF("FILE:" + filename);
		
		File file = new File("src/demo2/" + filename);		
		long filesize = file.length();
// 파일사이즈 보내기
		out.writeLong(filesize);
		
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		int len = 0;
		while((len = fis.read(buf)) != -1) {
// 파일 데이터 보내기
			out.write(buf, 0, len);
		}
		fis.close();
	}
	
	private void 파일삭제(String filename, 
			DataInputStream in, DataOutputStream out) {
		System.out.println("### 파일 삭제 요청 처리");
		
	}
	
	private void 파일정보(String filename, 
			DataInputStream in, DataOutputStream out) throws IOException{
		System.out.println("### 파일 정보 요청 처리");
		
		// 파일정보를 표현하는 File객체 생성하기
		File file = new File("src/demo2/" + filename);
		long filesize = file.length();
		
		// 응답보내기
		out.writeUTF("INFO:" + filename);
		out.writeLong(filesize);
	}
	
	public static void main(String[] args) {
		new FileServer().start();
	}
}
