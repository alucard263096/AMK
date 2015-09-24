package com.helpfooter.steve.amklovebaby.Utils;

import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;

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
	public static String LoginSendApi="loginsend";
	public static String RegisterApi="register";
	public static String RegisterSendApi="registersend";
	public static String MemberApi="member";
	public static String OrderListApi="orderlist";
	public static HashMap<String,String> dictHashMap=new  HashMap<String,String>();
	public static MemberObj Member=null;
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
		dictHashMap.put(LoginSendApi,APIURL+"Member/login_send.php");
		dictHashMap.put(RegisterApi,APIURL+"Member/reg.php");
		dictHashMap.put(RegisterSendApi,APIURL+"Member/reg_send.php");
		dictHashMap.put(MemberApi,APIURL+"Member/member_get.php");
		dictHashMap.put(OrderListApi,APIURL+"Member/orderlist.php");
	}

	public static String VideoChatServerIp="www.myhkdoc.com";
	public static int VideoChatServerPort=8906;

	public static String GetSystemTimeString(){
		SimpleDateFormat formatter    =   new    SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate    =   new    Date(System.currentTimeMillis());
		String    str    =    formatter.format(curDate);
		return str;
	}

	//ALIPAY
	public static String AlipayPartnerId="2088911829797670";
	public static String AlipaySellerId="3188577198@qq.com";
	public static String AlipayToken="70yr74ztz7uwjtjdxmxrqrlmwuvql63i";

}
