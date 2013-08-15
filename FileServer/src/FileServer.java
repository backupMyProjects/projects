import java.net.*;
import java.io.*;

public class FileServer {
	String serverIP = "127.0.0.1";
	int serverPort = 13267;
	String fileName;
	
	public static void main(String[] args) throws IOException {
		// create socket
		FileServer fileServer = new FileServer();
		fileServer.fileSendServer();
		
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public void fileSendServer() throws IOException{
		ServerSocket servsock = new ServerSocket(serverPort);
		while (true) {
			System.out.println("Waiting...");

			Socket sock = servsock.accept();
			System.out.println("Accepted connection : " + sock);

			// sendfile
			File myFile = new File(fileName);
			byte[] mybytearray = new byte[(int) myFile.length()];
			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
			OutputStream os = sock.getOutputStream();
			System.out.println("Sending...");
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();
			sock.close();
		}
	}
}