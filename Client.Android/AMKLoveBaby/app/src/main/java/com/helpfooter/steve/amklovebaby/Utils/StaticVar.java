package com.helpfooter.steve.amklovebaby.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public final class StaticVar {
	public static String ProjectName="AMKLoveBaby";
	public static String ImageFolderURL="http://www.myhkdoc.com/AMK/FilesServer/";
	public static String APIURL="http://www.myhkdoc.com/AMK/API/";

	public static String IndexBannerApi="index_banner";
	public static String BannerApi="banner";
	public static String DoctorApi="doctor";
	public static String NewsApi="news";
	public static String NewsContentApi="newscontent";
	public static String WorktimeApi="worktime";
	public static String VideochatOrderCreateApi="videochatordercreate";
	public static String OrderGetApi="orderget";
	public static String PaymentApi="payment";
	public static HashMap<String,String> dictHashMap=new  HashMap<String,String>();
	public static int member_id=0;
	static {
		dictHashMap.put(IndexBannerApi,APIURL+"Banner/index_banner.php");
		dictHashMap.put(BannerApi,APIURL+"banner.php");
		dictHashMap.put(DoctorApi,APIURL+"doctor.php");
		dictHashMap.put(WorktimeApi,APIURL+"doctor/doctor_worktime.php");
		dictHashMap.put(NewsApi,APIURL+"news.php");
		dictHashMap.put(NewsContentApi,APIURL+"news/news_get.php?news_id=");
		dictHashMap.put(VideochatOrderCreateApi,APIURL+"Order/videochat_create.php");//?doctor_id=1&order_date=2015-9-7&order_time=9:30&member_id=1&description=verygood");
		dictHashMap.put(OrderGetApi,APIURL+"Order/order_get.php");
		dictHashMap.put(PaymentApi,APIURL+"Order/order_payment.php");
	}

	public static String GetSystemTimeString(){
		SimpleDateFormat formatter    =   new    SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate    =   new    Date(System.currentTimeMillis());
		String    str    =    formatter.format(curDate);
		return str;
	}
}
