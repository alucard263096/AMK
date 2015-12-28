package com.helpfooter.steve.amkdoctor.Utils;

import com.helpfooter.steve.amkdoctor.CustomObject.BottomBarButton;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Interfaces.IMyActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public final class StaticVar {
	public static String ProjectName="AMKLoveBaby";
	public static String ImageFolderURL="http://www.myhkdoc.com/AMK/FilesServer/";
	public static String APIURL="http://www.myhkdoc.com/AMK/API/";
    public static String TxtType="TXT";
	public static String IMGType="IMG";
	public static String DOCType="DOC";
	public static String IndexBannerApi="index_banner";
	public static String VersionApi="version";
	public static String BannerApi="banner";
	public static String BookerApi="booker";
	public static String MessageApi="Message";
	public static String IMGCHATURL="http://www.myhkdoc.com/AMK/FilesServer/charchat/";
	public static String ChatApi="Chat";
	public static String ChatUpdateApi="ChatUpdate";
	public static String MemberPhotoApi="memberphoto";
	public static String ChatEndApi="ChatEnd";
	public static String ChatEndTimeApi="ChatEndTime";
	public static String CurrentVersion="1.0.0.6";
	public static HashMap<String,String> dictHashMap=new  HashMap<String,String>();
	public static String VideoChatServerIp="www.myhkdoc.com";
	public static int VideoChatServerPort=8906;
	public static  String UPLOADFILEURL = "http://www.myhkdoc.com/AMK/CMS/fileupload.php?module=charchat&field=uploadfile";
    public static ArrayList<BottomBarButton> lstBottomBar=null;
	public static String GeneralTextUrl="http://www.myhkdoc.com/AMK/CMS/general.php?code=";
	public static String NoticApi="Notice";
	public static String NoticReadApi="NoticeRead";
	public static IMyActivity CurrentActivity;
	public static DoctorObj Doctor=null;
	static {
		dictHashMap.put(IndexBannerApi,APIURL+"Banner/index_banner.php");
		dictHashMap.put(BannerApi,APIURL+"banner.php");
		dictHashMap.put(NoticApi,APIURL+"notice/list.php");
		dictHashMap.put(NoticReadApi,APIURL+"notice/read.php");
		dictHashMap.put(BookerApi,APIURL+"Order/videochat.php");
		dictHashMap.put(MessageApi,APIURL+"Order/charchat.php");
		dictHashMap.put(ChatApi,APIURL+"Order/charchat_one.php");
		dictHashMap.put(VersionApi,APIURL+"/version.xml");
		dictHashMap.put(ChatUpdateApi,APIURL+"Order/charchat_update.php");
		dictHashMap.put(ChatEndApi,APIURL+"/Order/order_finish.php");
		dictHashMap.put(ChatEndTimeApi,APIURL+"/Order/videochat_chattime.php");
		dictHashMap.put(VersionApi,APIURL+"versiondoctor.xml");
		dictHashMap.put(MemberPhotoApi,APIURL+"Member/member_photo.php");
	}

	public static String GetSystemTimeString(){
		SimpleDateFormat formatter    =   new    SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate    =   new    Date(System.currentTimeMillis());//��ȡ��ǰʱ��
		String    str    =    formatter.format(curDate);
		return str;
	}
}
