package com.yulon.yesapp.util;

public class Constants {
	private static final String SERVER_URL = "http://station.yulon-energy.com.tw";
	private static final String SERVER_PREFIX = "/mobile/station/";
	private static final String SERVER_PATH = SERVER_URL + SERVER_PREFIX ;
	
	public static final String LOGIN = SERVER_PATH + "login";
	public static final String CHPWD = SERVER_PATH + "chpswd";
	public static final String ADD_DEVICE_TOKEN = SERVER_PATH + "addDeviceToken";
	public static final String GET_ALL_STATION_LOC = SERVER_PATH + "getAllStationsByLoc";
	public static final String GET_ALL_STATION_CAR = SERVER_PATH + "getAllStationsByLocCarno";
	public static final String PRESERVE = SERVER_PATH + "preserve";
	public static final String SMART_RESERVE = SERVER_PATH + "smartReserve";
	public static final String PLANNING_RESULT = SERVER_PATH + "planningResult";
	public static final String GET_VAT_INFO = SERVER_PATH + "getVatInfo";
	public static final String SET_VAT_INFO = SERVER_PATH + "addVatInfo";
	public static final String GET_COUNTRY = SERVER_PATH + "getCounty";
	public static final String GET_TOWN = SERVER_PATH + "getTown";
	public static final String GET_NICKNAME = SERVER_PATH + "getNickname";
	public static final String SET_NICKNAME = SERVER_PATH + "addNickname";
	public static final String ADD_FAV = SERVER_PATH + "addFavorite";
	public static final String DEL_FAV = SERVER_PATH + "removeFavorite";
	public static final String GET_FAV = SERVER_PATH + "getFavorites";
	public static final String GET_CAR_STATUS = SERVER_PATH + "getCarStatus";
	public static final String GET_STATION_BY_SPOTID = SERVER_PATH + "getStationBySpotId";
	public static final String REPORT_BAD_CHARGESPOT = SERVER_PATH + "reportBadChargeSpot";
	public static final String GET_CHARGING_STATION_PLOT = SERVER_PATH + "getChargingStationPlot";
	public static final String GET_CHARGING_SPOT_PLOT = SERVER_PATH + "getChargingSpotPlot";
	public static final String SEND_SMS = SERVER_PATH + "sendSMS";
	public static final String GET_APP_MESSAGE = SERVER_PATH + "getAppMessage";
	public static final String GETAPP_MESSAGE_BY_CAT = SERVER_PATH + "getAppMessageByCat";
	public static final String GET_MESSAGE_UNREAD_COUNT = SERVER_PATH + "getMessageUnreadCount";
	public static final String GET_TRIAL_PAGE = SERVER_PATH + "getTrialPage";
	public static final String GET_STATION_DETAIL = SERVER_PATH + "getStationDetail";

	private static final String SERVER_CSC_PREFIX = "/mobile/csc/";
	private static final String SERVER_CSC_PATH = SERVER_URL + SERVER_CSC_PREFIX ;
	public static final String csc_GET_THINK_SIMNO = SERVER_CSC_PATH + "getThinkSimNo";
	
	private static final String ex_URL = "http://www.lifegoplus.com";
	private static final String ex_PREFIX = "/Mobile/ev/";
	private static final String ex_PATH = ex_URL + ex_PREFIX;
	public static final String ex_GET_POI_BY_CAT_BOUNDS = ex_PATH + "getPoiByCatAndBounds";
	public static final String ex_GET_POI_DETAIL = ex_PATH + "getPoiDetail";
	public static final String ex_GET_EV_TRIPS = ex_PATH + "getEvTrips";
	
	private static final String php_URL = "http://61.57.244.99";
	private static final String php_PREFIX = "/YLET/index.php/Dm/";
	private static final String php_PATH = php_URL + php_PREFIX;
	public static final String php_GET_CAR_STATE = php_PATH + "D252/index";
	public static final String php_GET_CHARGING_HISTORY = php_PATH + "D251/index";
	public static final String php_GET_BOOKING_HISTORY = php_PATH + "D255/index";
	
	public static enum ACTION {
	    GET, POST
	} 

}
