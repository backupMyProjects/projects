package com.leo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class SocketActivity extends Activity {
	/** Called when the activity is first created. */
	private final String TAG = "SocketActivity";
	SocketServer server = new SocketServer();
	final String localIP = getLocalIpAddress();
	final int port = 5000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Log.i(TAG,localIP);
		boolean createChat = true;
		if(createChat){
			if (server != null) {
				server.start();
			}
		}
		

		findviews();
		setonclick();

	}

	private EditText chattxt;
	private TextView chattxt2;
	private Button chatok;

	public void findviews() {
		chattxt = (EditText) this.findViewById(R.id.chattxt);
		chattxt2 = (TextView) this.findViewById(R.id.chattxt2);
		chatok = (Button) this.findViewById(R.id.chatOk);
	}

	private void setonclick() {
		chatok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					// the ip & port will be change to the friend's one
					Map socketData = new HashMap();
					socketData.put("type", "message");
					socketData.put("info", chattxt.getText().toString());
					connectServer(localIP, port, socketData);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* Map socketData :
	 * key = type ; value = message / voice
	 * key = info ; value = String Object / Voice Object 
	 */
	public void connectServer(String ip, int port, Map socketData) throws UnknownHostException,
			IOException {
		Socket socket = RequestSocket(ip, port);
		Log.i(TAG, "contected");
		String type = (String)socketData.get("type");
		if("message".equals(type)){
			SendMsg(socket, (String)socketData.get("info"));
		}else if("voice".equals(type)){
			// TODO send voice from here
		}
		String txt = ReceiveMsg(socket);
		this.chattxt2.setText(txt);
	}

	private Socket RequestSocket(String host, int port)
			throws UnknownHostException, IOException {
		Socket socket = new Socket(host, port);
		return socket;
	}

	private void SendMsg(Socket socket, String msg) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		writer.write(msg.replace("\n", " ") + "\n");
		writer.flush();
	}

	private String ReceiveMsg(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		String txt = reader.readLine();
		return txt;

	}
	
	
	
	
	
	public String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e(TAG, ex.toString());
	    }
	    return "0.0.0.0";
	}
}