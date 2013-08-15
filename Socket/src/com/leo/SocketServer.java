package com.leo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {

	public SocketServer() {
		try {
			CreateSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建Socket服务器
	}

	public void run() {
		Socket client;
		String message;
		try {
			while (true)
			// 线程无限循环，实时监听socket端口
			{
				client = ResponseSocket();
				// 响应客户端链接请求。。


				while (true) {
					message = ReceiveMsg(client);
					System.out.println(message);
					// 链接获得客户端发来消息，并将其显示在Server端的屏幕上

					SendMsg(client, "GOT_MESSAGE:"+message);
					// 一定要向Client回復，否則 CloseSocket(client); 會出現錯誤

					break;
					// 中断，继续等待链接请求
				}
				

				CloseSocket(client);
				// 关闭此次链接
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	private ServerSocket server = null;
	private static final int PORT = 5000;
	private BufferedWriter writer;
	private BufferedReader reader;

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
		writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		writer.write(Msg + "\n");
		writer.flush();

	}

	private String ReceiveMsg(Socket socket) throws IOException {
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		System.out.println("server get input from client socket..");
		String txt = "Sever send:" + reader.readLine();

		return txt;
	}

	/*
	public static void main(final String args[]) throws IOException {
		SocketServer server = new SocketServer();
		if (server != null) {
			server.start();
		}
	}
	*/

}