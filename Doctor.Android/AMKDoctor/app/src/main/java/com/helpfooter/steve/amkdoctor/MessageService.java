package com.helpfooter.steve.amkdoctor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.helpfooter.steve.amkdoctor.DAO.MessageDao;
import com.helpfooter.steve.amkdoctor.DAO.BookerDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MessageObj;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.Loader.ChatLoader;
import com.helpfooter.steve.amkdoctor.Loader.BookerLoader;
import com.helpfooter.steve.amkdoctor.Loader.MessageLoader;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;


public class MessageService extends Service {
    public MessageService() {
    }

    //获取消息线程
    private MessageThread messageThread = null;
    //加载最新视频预约列表


    //加载最新聊天列表
    private ChatlistThread chatlistThread = null;

    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;
    private MessageService mService = this;
    private Notification messageNotification = null;
    private NotificationManager messageNotificatioManager = null;
    private static final int messageSleepTIme = 600000;//600000;
    private int messageNotificationID = 1000;
    private BookerLoader ol;
   private NetworkInfo mWifi;
    private MessageLoader messageLoader;
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//初始化
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
         mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        messageNotification = new Notification();
        // messageNotification.icon = R.drawable.icon;
        messageNotification.tickerText = "新消息";
        messageNotification.defaults = Notification.DEFAULT_SOUND;
        messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        messageIntent = new Intent(this, MainActivity.class);
        messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);
        messageNotification.flags = Notification.FLAG_AUTO_CANCEL;
        messageNotification.icon = R.drawable.icon;
        //开启线程


         ol = new BookerLoader(mService.getApplicationContext());
        ol.setIsCircle(true);
        ol.setCircleSecond(600);
        ol.setOnlyWifi(true);
        ol.setConnectivityManager(connManager);
        ol.start();
        messageLoader = new MessageLoader(mService.getApplicationContext());
        messageLoader.setIsCircle(true);
        messageLoader.setCircleSecond(180);
        messageLoader.setOnlyWifi(true);
        messageLoader.setConnectivityManager(connManager);
        messageLoader.start();

        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();


           chatlistThread = new ChatlistThread();
            chatlistThread.isRunning = true;
            chatlistThread.start();
        return super.onStartCommand(intent, flags, startId);

    }








    /**
          * 从服务器端获取消息
          *
          */
    class ChatlistThread extends Thread{
        //运行状态，下一步骤有大用
        public boolean isRunning = true;
        public void run() {
            while(isRunning){
                try {

                    //获取服务器消息 wifi连接且后台运行时才提示
                    if(mWifi.isConnected()&&isBackground(mService.getApplicationContext())) {
                        MessageDao orderdao = new MessageDao(mService.getApplicationContext());
                        ArrayList<AbstractObj> arrObjs = orderdao.getNoticeOrder();
                        String serverMessage = "";
                        if (arrObjs.size() > 0) {
                            for (AbstractObj arrobj : arrObjs) {
                                MessageObj messageobj = (MessageObj) arrobj;
                                if(messageobj.getLast_one().equals(messageobj.getSendmessage()))
                                {
                                    continue;
                                }
                                String[] arrMessage = messageobj.getLast_one().split(":");
                                serverMessage = "";
                                String strContent = "";
                                if (arrMessage.length == 3) {
                                    if (arrMessage[1].equals(StaticVar.DOCType)) {
                                        strContent = "[文件]";
                                    } else if (arrMessage[1].equals(StaticVar.IMGType)) {
                                        strContent = "[图片]";
                                    } else {
                                        strContent = arrMessage[2];
                                    }
                                }
                                serverMessage += "会员发来消息：" + strContent;
                                if (serverMessage != null && !"".equals(serverMessage)) {
                                    //更新通知栏
                                    messageNotification.setLatestEventInfo(MessageService.this, "新消息", serverMessage, messagePendingIntent);
                                    messageNotificatioManager.notify(messageNotificationID, messageNotification);
                                    //每次通知完，通知ID递增一下，避免消息覆盖掉
                                    messageNotificationID++;
                                    MessageDao neworderdao = new MessageDao(mService.getApplicationContext());
                                    neworderdao.updateSendMessage(messageobj);
                                }
                            }


                        }
                    }
                    //休息3分钟
                    Thread.sleep(180000);



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public int getDateMins (String orderTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date date = sdf.parse(orderTime);// 通过日期格式的parse()方法将字符串转换成日期              Date dateBegin = sdf.parse(date2);
            long betweenTime = date.getTime()-System.currentTimeMillis();
            betweenTime  = betweenTime  / 1000 / 60;
            int diffTime= (int)betweenTime;
            if(diffTime<=0)
            {
                return 100;
            }
            else
            {
                return diffTime;

            }

        } catch(Exception e)
        {
        }
        return 100;

    }
            /**
     * 从服务器端获取消息
     *
     */
        class MessageThread extends Thread{
        //运行状态，下一步骤有大用
            public boolean isRunning = true;
            public void run() {
                while(isRunning){
                try {
                //休息5分钟
                Thread.sleep(messageSleepTIme);
                //获取服务器消息
                    if(mWifi.isConnected()) {
                        BookerDao orderdao = new BookerDao(mService.getApplicationContext());
                        ArrayList<AbstractObj> arrObjs = orderdao.getMessageNoticeList();
                        String serverMessage = "";
                        if (arrObjs.size() > 0) {
                            for (AbstractObj arrobj : arrObjs) {
                                BookerObj orderobj = (BookerObj) arrobj;
                                String ordertime=orderobj.getOrderdate()+" "+orderobj.getOrdertime();

                                if(getDateMins(ordertime)<=15) {
                                    serverMessage += orderobj.getOrderdate() + " " + orderobj.getOrdertime();
                                    BookerDao neworderdao = new BookerDao(mService.getApplicationContext());
                                    neworderdao.updateSendMessage(orderobj);
                                    if (serverMessage != null && !"".equals(serverMessage)) {
                                        //更新通知栏
                                        messageNotification.setLatestEventInfo(MessageService.this, "新消息", "您有一个视频预约将于" + serverMessage + "开始", messagePendingIntent);
                                        messageNotificatioManager.notify(messageNotificationID, messageNotification);
                                        //每次通知完，通知ID递增一下，避免消息覆盖掉
                                        messageNotificationID++;
                                    }

                                }

                            }

                        }
                    }



                } catch (InterruptedException e) {
                e.printStackTrace();
                }
            }
        }
    }


}