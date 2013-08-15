import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;


public class Client {

	private static String ServerIP = "127.0.0.1";
	private static final int PORT = 5000;

	public void connectServer(String ip, int port, Map socketData) throws UnknownHostException, IOException {
		Socket socket = RequestSocket(ip, port);
		System.out.println("contected");
		String type = (String) socketData.get("type");
		//-----------------------------------------------//
		
		
		if ("text".equals(type)) {
			//SendMsg(socket, (String) socketData.get("type"));
			//SendMsg(socket, type);
			//SendMsg(socket, (String) socketData.get("said"));
		} else if ("voice".equals(type)) {
			//SendMsg(socket, (Object) socketData.get("info"));
		}
		//-----------------------------------------------//
		String txt = ReceiveMsg(socket);
	}

	private Socket RequestSocket(String host, int port)throws UnknownHostException, IOException {
		Socket socket = new Socket(host, port);
		return socket;
	}
	
	private void sendObject(Socket socket, Map socketData){
		Object obj = SerializationUtils.clone((Serializable) socketData);
		
	}

	private void SendMsg(Socket socket, String msg) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.write(msg.replace("\n", " ") + "\n");
		writer.flush();
	}
	
	private void SendFile(Socket socket, String fileName) throws IOException {
		//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		//writer.write(msg.replace("\n", " ") + "\n");
		//writer.flush();
		
		File myFile = new File(fileName);
		byte[] mybytearray = new byte[(int) myFile.length()];
		FileInputStream fis = new FileInputStream(myFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(mybytearray, 0, mybytearray.length);
		System.out.println("File Sending...");
		OutputStream os = socket.getOutputStream();
		os.write(mybytearray, 0, mybytearray.length);
		os.flush();
		socket.close();
		
	}

	private String ReceiveMsg(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String txt = reader.readLine();
		return txt;

	}
	
	public static void main(final String args[]) throws IOException {
		Client client = new Client();
		try {
			// the ip & port will be change to the friend's one
			Map socketData = new HashMap();
			socketData.put("type", "text");
			socketData.put("said", "Hello, I am client 123");
			//socketData.put("type", "voice");
			//socketData.put("said", "/Users/leo/test.txt");
			client.connectServer(ServerIP, PORT, socketData);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
