package com.helpfooter.steve.amklovebaby.Utils;

import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public final class StaticVar {
	public static String ProjectName="AMKLoveBaby";
	public static String ImageFolderURL="http://www.myhkdoc.com/AMK/FilesServer/";
	public static String APIURL="http://www.myhkdoc.com/AMK/API/";
	public static  String UPLOADFILEURL = "http://www.myhkdoc.com/AMK/CMS/fileupload.php?module=charchat&field=uploadfile";
	public static  String UPLOADFILEURL4Member = "http://www.myhkdoc.com/AMK/CMS/fileupload.php?module=member&field=uploadfile";
	public static String IMGCHATURL="http://www.myhkdoc.com/AMK/FilesServer/charchat/";
	public static String IndexBannerApi="index_banner";
	public static String BannerApi="banner";
	public static String DoctorApi="doctor";
	public static String DoctorStatisticsApi="doctorstatistics";
	public static String NewsApi="news";
	public static String NewsContentApi="newscontent";
	public static String WorktimeApi="worktime";
	public static String VideochatOrderCreateApi="videochatordercreate";
	public static String CharchatOrderCreateApi="charchatordercreate";
	public static String OrderGetApi="orderget";
	public static String PaymentApi="payment";
	public static String LoginSendApi="loginsend";
	public static String RegisterApi="register";
	public static String RegisterSendApi="registersend";
	public static String MemberApi="member";
	public static String MemberUpdateApi="memberupdate";
	public static String OrderListApi="orderlist";
	public static String VersionApi="version";
	public static String ChatApi="Chat";
	public static String ChatUpdateApi="ChatUpdate";
	public static String MessageApi="Message";
	public static HashMap<String,String> dictHashMap=new  HashMap<String,String>();
	public static MemberObj Member=null;
	public static String CurrentVersion="1.0.0.2";
	public static String TxtType="TXT";
	public static String IMGType="IMG";
	public static String DOCType="DOC";
	public static MainActivity MainForm;
	public static String GeneralTextUrl="http://www.myhkdoc.com/AMK/CMS/general.php?code=";
	public static String DoctorFollowAPI="doctorfollow";
	public static String UploadDoctorFollowAPI="uploaddoctorfollow";

	public static int width ;     // 屏幕宽度（像素）
	public static int height ;   // 屏幕高度（像素）
	public static float density ;      // 屏幕密度（0.75 / 1.0 / 1.5）
	public static int densityDpi ;  // 屏幕密度DPI（120 / 160 / 240）

	static {
		dictHashMap.put(IndexBannerApi,APIURL+"Banner/index_banner.php");
		dictHashMap.put(BannerApi,APIURL+"banner.php");
		dictHashMap.put(DoctorApi,APIURL+"doctor.php");
		dictHashMap.put(WorktimeApi,APIURL+"doctor/doctor_worktime.php");
		dictHashMap.put(NewsApi,APIURL+"news.php");
		dictHashMap.put(NewsContentApi,APIURL+"news/news_get.php?news_id=");
		dictHashMap.put(VideochatOrderCreateApi,APIURL+"Order/videochat_create.php");
		dictHashMap.put(CharchatOrderCreateApi,APIURL+"Order/charchat_create.php");//?doctor_id=1&order_date=2015-9-7&order_time=9:30&member_id=1&description=verygood");
		dictHashMap.put(OrderGetApi,APIURL+"Order/order_get.php");
		dictHashMap.put(PaymentApi,APIURL+"Order/order_payment.php");
		dictHashMap.put(LoginSendApi,APIURL+"Member/login_send.php");
		dictHashMap.put(RegisterApi,APIURL+"Member/reg.php");
		dictHashMap.put(RegisterSendApi,APIURL+"Member/reg_send.php");
		dictHashMap.put(ChatApi,APIURL+"Order/charchat_one.php");
		dictHashMap.put(MemberApi,APIURL+"Member/member_get.php");
		dictHashMap.put(MemberUpdateApi,APIURL+"Member/member_update.php");
		dictHashMap.put(OrderListApi,APIURL+"Member/orderlist.php");
		dictHashMap.put(ChatUpdateApi,APIURL+"Order/charchat_update.php");
		dictHashMap.put(VersionApi,APIURL+"version.xml");
		dictHashMap.put(DoctorFollowAPI,APIURL+"Member/member_doctor.php");
		dictHashMap.put(UploadDoctorFollowAPI,APIURL+"Member/follow_doctor.php");
		dictHashMap.put(MessageApi,APIURL+"Order/charchat_member.php");
		dictHashMap.put(DoctorStatisticsApi,APIURL+"Doctor/doctor_statistic.php");
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
	public static String AlipayRSA="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL/Rx6qA1AWYQtm7" +
			"2tjKbzosKsdbUZQWD3/Xh2sK87gbi5ox2tMdPZqWPIIcSsFDnfBFPbyYtXqebiwR" +
			"Y75o//qwS4VxdRUfY0HLnShFvgu0K4Ml+qe0FHbwayXrzV9Ik3HHzeah57y37Bd4" +
			"PMNyQRNBDo1zbqBERwspGkilCVExAgMBAAECgYBa7TwyjKL0i/qUb8ILpdoXvzwA" +
			"CD+hbNuoBCjDCwwAp74DN8K89E1xa+n+2x5sgnrpnEjpj5HnMBaVeSDI78j23zVm" +
			"8yraVA+bpwYIfjGR0SrxUuXCk6b7NV0IvuS+kBraAIS8J9SFealuawS3mgN/XKJv" +
			"IN/qq0MepnlESV2vYQJBAPaQ75BX6DUrro2X06AIT61Q4oAcl8X8+7wa6zd0g+6c" +
			"+CMdv0Vzn7M3ACzbIhSHbCOCNmMh0f21AFo2Sp0/to0CQQDHKJyQ4smLDlvDCrlT" +
			"/xM0lSfovRJO43XDRJfBzMGI/O9SuMjFlJ6j6CHSd5P2BHcjVbDf4vfhbZY9ue0q" +
			"RB41AkAnGORj5wyTIdIT8bdE8QxxVxoTZnVKl8rzEPGrYXZqJ8nRB66EvMjeCiKk" +
			"+jp1NuQW3VbycTJzIHhl1aYPOUMhAkEAwTVh5qFNOw0b8nTF5HoRzLJi/EPorgW2" +
			"6k/yFb4ph1cyagMAK8YJvSNnJIKDVWlxlyL7q8fKCLVCyvFD5vwcfQJBAOSi5swX" +
			"q6eBjtBPA7DnWi/+VOgV69hWfcrIZzxIVbfdzvUXWD9NBFe6G2eZRx50pWab1iTd" +
			"Ju5b8IIkuhvYb1I=";
}
