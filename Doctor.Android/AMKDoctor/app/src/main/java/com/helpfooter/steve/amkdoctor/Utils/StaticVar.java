package com.helpfooter.steve.amkdoctor.Utils;

import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public final class StaticVar {
	public static String ProjectName="AMKLoveBaby";
	public static String ImageFolderURL="http://www.myhkdoc.com/AMK/FilesServer/";
	public static String APIURL="http://www.myhkdoc.com/AMK/API/";

	public static String IndexBannerApi="index_banner";
	public static String BannerApi="banner";
	public static String BookerApi="booker";
	public static HashMap<String,String> dictHashMap=new  HashMap<String,String>();
	public static String VideoChatServerIp="www.myhkdoc.com";
	public static int VideoChatServerPort=8906;
	public static DoctorObj Doctor=null;
	static {
		dictHashMap.put(IndexBannerApi,APIURL+"Banner/index_banner.php");
		dictHashMap.put(BannerApi,APIURL+"banner.php");
		dictHashMap.put(BookerApi,APIURL+"Order/videochat.php");
	}

	public static String GetSystemTimeString(){
		SimpleDateFormat formatter    =   new    SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate    =   new    Date(System.currentTimeMillis());//��ȡ��ǰʱ��
		String    str    =    formatter.format(curDate);
		return str;
	}
}
