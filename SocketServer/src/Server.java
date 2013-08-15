import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	private ServerSocket server = null;
	private static final int PORT = 5000;
	private BufferedWriter writer;
	private BufferedReader reader;

	private Server() throws IOException {
		CreateSocket();
		// 创建Socket服务器
	}

	public void run() {
		Socket client;
		String txt;
		try {
			while (true)
			{
				client = ResponseSocket();
				
				while (true) {
					
					txt = ReceiveMsg(client);
					System.out.println(txt);
					// get message from client

					SendMsg(client, "GOT_MESSAGE:"+txt);
					// must play back, else CloseSocket(client) get error

					System.out.println("waiting..");
					break;
					// break and wait
				}
				

				CloseSocket(client);
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	private void CreateSocket() throws IOException {
		server = new ServerSocket(PORT, 100);
		System.out.println("Server starting..");
	}

	private Socket ResponseSocket() throws IOException {
		Socket client = server.accept();
		System.out.println("client connected..");

		return client;
	}

	private void CloseSocket(Socket socket) throws IOException {
		reader.close();
		writer.close();
		socket.close();
		System.out.println("client closed..");
	}

	private void SendMsg(Socket socket, String Msg) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.write(Msg + "\n");
		writer.flush();

		System.out.println("server send message");

	}

	private String ReceiveMsg(Socket socket) throws IOException {
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("server get message from client socket..");
		String txt = reader.readLine();

		return txt;
	}

	public static void main(final String args[]) throws IOException {
		Server server = new Server();
		if (server != null) {
			server.start();
		}
	}

}