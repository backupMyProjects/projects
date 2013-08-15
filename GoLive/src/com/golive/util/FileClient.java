package com.golive.util;
/*
package com.twgan.utils;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

public class FileClient {
	private String address = Constants.SERVER;
	private int port = 5000;

	public FileClient(String filePath, String fileName) throws IOException {
		Socket client = new Socket();
		// 準備要傳送的資料
		HashMap<String, Object> socketData = new HashMap<String, Object>();
		//socketData.put("type", "text");
		//socketData.put("said", "Hello, I am client 123");
		socketData.put("type", "voice");
		socketData.put("said", fileName);
		File myFile = new File(filePath, fileName);
		//-----------
		// 必須將檔案讀成一個物件
		byte[] result = file2byteA(myFile);
		//-----------
		socketData.put("file", result);
		Object obj = SerializationUtils.clone((Serializable) socketData);

		InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
		try {
			client.connect(isa, 10000);
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			// 送出object
			out.writeObject(obj);
			out.flush();
			out.close();
			out = null;
			obj = null;
			client.close();
			client = null;

		} catch (java.io.IOException e) {
			System.out.println("client:Socket連線有問題 !");
			System.out.println("IOException :" + e.toString());
		}
	}
	
	public byte[] file2byteA(File input){
		byte[] result = null;
		try {
            FileInputStream stream = new FileInputStream(input);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            result = out.toByteArray();
        } catch (IOException e){
        	e.printStackTrace();
        }
		
		return result;
	}
	

	public static void main(String args[]) throws IOException {
		new FileClient("testA/testB", "test.mp3");
	}
}
*/