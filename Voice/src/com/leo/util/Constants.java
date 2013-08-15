package com.leo.util;

public class Constants {
	private static String SERVER_URL = "http://192.168.43.52";
	//private static String SERVER_URL = "http://192.168.1.17";
	private static String SERVER_PREFIX = "/~leo/voice/";
	
	private static String UPLOAD_PATH = "chat_upload.php";
	public static String UPLOAD_URL = SERVER_URL + SERVER_PREFIX + UPLOAD_PATH;

	private static String SETDATA_PATH = "chat_setData.php";
	public static String SETDATA_URL = SERVER_URL + SERVER_PREFIX + SETDATA_PATH;

	private static String GETDATA_PATH = "chat_getData.php";
	public static String GETDATA_URL = SERVER_URL + SERVER_PREFIX + GETDATA_PATH;

	private static String MEDIA_FOLDER = "uploaded/";
	public static String MEDIA_URL = SERVER_URL + SERVER_PREFIX + MEDIA_FOLDER;
	public static String MEDIA_LOCAL = "/sdcard/voice/";
}
