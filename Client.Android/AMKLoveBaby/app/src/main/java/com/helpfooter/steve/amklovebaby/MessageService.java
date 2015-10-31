package com.helpfooter.steve.amklovebaby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.helpfooter.steve.amklovebaby.DAO.MessageDao;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MessageObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Loader.ChatLoader;
import com.helpfooter.steve.amklovebaby.Loader.MessageLoader;
import com.helpfooter.steve.amklovebaby.Loader.OrderListLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;


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
    private Notification messageNotification=null;
    private MessageService mService = this;


    private Intent messageIntent4ChatList = null;
    private PendingIntent messagePendingIntent4ChatList = null;
    private Notification messageNotification4ChatList=null;


    private NotificationManager messageNotificatioManager = null;
    private static final int messageSleepTIme = 600000;
    private int messageNotificationID = 1000;
    private int messageNotificationID4ChatLis = 1001;
    private NetworkInfo mWifi;
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void InitMessage(){

        messageNotification = new Notification();
        // messageNotification.icon = R.drawable.icon;
        messageNotification.tickerText = "爱我宝贝新消息";
        messageNotification.defaults = Notification.DEFAULT_SOUND;
        messageIntent = new Intent(this, OrderListActivity.class);
        messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);
        messageNotification.flags = Notification.FLAG_AUTO_CANCEL;
        messageNotification.icon = R.drawable.role_5;
    }
    public void InitOrderList(){

        messageNotification4ChatList = new Notification();
        // messageNotification.icon = R.drawable.icon;
        messageNotification4ChatList.tickerText = "爱我宝贝新消息";
        messageNotification4ChatList.defaults = Notification.DEFAULT_SOUND;
        messageIntent4ChatList = new Intent(this, CharListActivity.class);
        messagePendingIntent4ChatList = PendingIntent.getActivity(this, 0, messageIntent4ChatList, 0);
        messageNotification4ChatList.flags = Notification.FLAG_AUTO_CANCEL;
        messageNotification4ChatList.icon = R.drawable.role_5;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//初始化
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        InitMessage();
        InitOrderList();
        //开启线程



        OrderListLoader orderListLoader = new OrderListLoader(mService.getApplicationContext());
        orderListLoader.setIsCircle(true);
        orderListLoader.setCircleSecond(180);
        orderListLoader.setOnlyWifi(true);
        orderListLoader.setConnectivityManager(connManager);
        orderListLoader.start();
        MessageLoader messageLoader = new MessageLoader(mService.getApplicationContext());
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

                    //获取服务器消息
                    if(mWifi.isConnected()) {
                        MessageDao orderdao = new MessageDao(mService.getApplicationContext());
                        ArrayList<AbstractObj> arrObjs = orderdao.getNoticeOrder();

                        if (arrObjs.size() > 0) {
                            for (AbstractObj arrobj : arrObjs) {
                                MessageObj messageobj = (MessageObj) arrobj;
                                if(messageobj.getLast_one().equals(messageobj.getSendmessage()))
                                {
                                    continue;
                                }
                                String[] arrMessage = messageobj.getLast_one().split(":");
                                String strContent = "";
                                String serverMessage = "";
                                if (arrMessage.length == 3) {
                                    if (arrMessage[1].equals(StaticVar.DOCType)) {
                                        strContent = "[文件]";
                                    } else if (arrMessage[1].equals(StaticVar.IMGType)) {
                                        strContent = "[图片]";
                                    } else {
                                        strContent = arrMessage[2];
                                    }
                                }
                                serverMessage += "医生发来消息：" + strContent;
                                if (serverMessage != null && !"".equals(serverMessage)) {
                                    //更新通知栏
                                    messageNotification4ChatList.setLatestEventInfo(MessageService.this, "爱我宝贝消息", serverMessage, messagePendingIntent4ChatList);
                                    messageNotificatioManager.notify(messageNotificationID4ChatLis, messageNotification4ChatList);
                                    //每次通知完，通知ID递增一下，避免消息覆盖掉
                                    messageNotificationID4ChatLis++;
                                    MessageDao neworderdao = new MessageDao(mService.getApplicationContext());
                                    neworderdao.updateSendMessage(messageobj);
                                }
                            }


                        }
                    }
                    //休息3分钟
                    Thread.sleep(3000);



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
                //获取服务器消息
                    Thread.sleep(messageSleepTIme);
                    if(mWifi.isConnected()) {
                        OrderDao orderdao = new OrderDao(mService.getApplicationContext());
                        //ArrayList<AbstractObj> arrObjs = orderdao.getMessageNoticeList();
                        String serverMessage = "";
                        OrderObj orderobj = orderdao.getLatestOrder(true);
                        String ordertime = orderobj.getOrder_date() + " " + orderobj.getOrder_time();

                        if (getDateMins(ordertime) <= 15 && getDateMins(ordertime) > 0) {
                            serverMessage += orderobj.getOrder_date() + " " + orderobj.getOrder_time();
                            OrderDao neworderdao = new OrderDao(mService.getApplicationContext());
                            neworderdao.updateSendMessage(orderobj);
                            if (serverMessage != null && !"".equals(serverMessage)) {
                                //更新通知栏
                                messageNotification.setLatestEventInfo(MessageService.this, "爱我宝贝消息", "您的预约将于" + serverMessage + "开始", messagePendingIntent);
                                messageNotificatioManager.notify(messageNotificationID, messageNotification);
                                //每次通知完，通知ID递增一下，避免消息覆盖掉
                                messageNotificationID++;
                            }
                            break;
                        }

                    }

                } catch (Exception e) {
                e.printStackTrace();
                }
            }
        }
    }


}