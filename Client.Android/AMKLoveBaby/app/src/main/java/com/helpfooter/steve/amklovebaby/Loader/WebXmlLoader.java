package com.helpfooter.steve.amklovebaby.Loader;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.OrderPaymentActivity;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.XmlDataTableReader;

public abstract class WebXmlLoader extends Thread{

	protected Context ctx;
	protected String callApi="";

	public void setOnlyWifi(boolean onlyWifi) {
		this.onlyWifi = onlyWifi;
	}

	protected boolean onlyWifi=false;

	public void setConnectivityManager(ConnectivityManager connectivityManager) {
		this.connectivityManager = connectivityManager;
	}

	ConnectivityManager connectivityManager=null;


	public boolean isCircle() {
		return isCircle;
	}

	public void setIsCircle(boolean isCircle) {
		this.isCircle = isCircle;
	}

	boolean isCircle=false;

	public int getCircleSecond() {
		return circleSecond;
	}

	public void setCircleSecond(int circleSecond) {
		this.circleSecond = circleSecond;
	}

	int circleSecond=10;

	public WebXmlLoader(Context ctx,String defaultCallApi){
		this.ctx=ctx;
		this.callApi=defaultCallApi;
		NewFailResult();
	}

	ResultFail resultFail;

	public void NewFailResult(){
		resultFail=new ResultFail();
	}

	public void setCallCode(String val){
		callApi=val;
	}

	//N for Not yet, F for Fail, D for done
	public String getStatus() {
		return status;
	}
	String status="N";

	//默认的远端数据接口URL的调用，可用于重载
	public String getCallUrl() {
		// TODO Auto-generated method stub
		ParamsDao dao=new ParamsDao(ctx);
		String api=callApi;
		String url=StaticVar.dictHashMap.get(callApi);
		String update_date=dao.getParam(callApi, "1991-1-1");
		url= (url+"?last_time="+update_date).replace(" ", "%20");
		return url;
	}

	IWebLoaderCallBack callBack;
	public void setCallBack(IWebLoaderCallBack val){
		callBack=val;
	}

	//需要重写的doXml方法，对返回的业务数据进行交互操作
	abstract public void doXml(ArrayList<HashMap<String,String>> lstRow);

	public void RealRun(){
		if(onlyWifi){
			if(connectivityManager!=null) {
				NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mWifi.isConnected() == false) {
					status = "F";
					return;
				}
			}
		}
		String path=getCallUrl();
		Log.i("webload_debugUrl", path);
		InputStream is=getXml(path);
		if(is!=null){
			XmlDataTableReader xmlreader=new XmlDataTableReader(is);
			Log.i("webload_apirow", callApi + ":" + String.valueOf(xmlreader.getDataTableValue().size()));
			ArrayList<HashMap<String,String>> lstResult=xmlreader.getDataTableValue();

			doXml(lstResult);
			status="D";
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		status="F";
	}

	//多线程调用的线程方法
	public void run(){

		RealRun();
		while (isCircle){
			try {
				Thread.sleep(circleSecond*1000);
				Log.i("webload thread",this.getCallUrl());
				RealRun();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	//获取远端XML的工作流
	protected InputStream getXml(String path){
		InputStream is=null;
		URL url;
		try {
			//path = java.net.URLEncoder.encode(path,"utf-8");
			url = new URL(path);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5000);  
	        conn.setRequestMethod("GET");  
	        conn.setDoInput(true);  
	        if (conn.getResponseCode() == 200) {
	             is = conn.getInputStream();
	            return is;
	        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(resultFail==null){
				ToastFail(0);
			}else {
				resultFail.show(0);
			}
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(resultFail==null){
				ToastFail(1);
			}else {
				resultFail.show(1);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(resultFail==null){
				ToastFail(2);
			}else {
				resultFail.show(2);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(resultFail==null){
				ToastFail(3);
			}else {
				resultFail.show(3);
			}
		}
        return is;
	}
	class ResultFail{
		public void show(int error){
				getResultFailHandler.sendEmptyMessage(error);
		}

		public android.os.Handler getResultFailHandler = new android.os.Handler(){
			@Override
			public void handleMessage(Message msg)
			{
				ToastFail(msg.what);
			}
		};

	}

	public void ToastFail(int what){
		switch (what){
			case 0:
				Toast.makeText(ctx,"网络错误，请检查链接",Toast.LENGTH_LONG).show();
				return;
			case 1:
				Toast.makeText(ctx,"网络错误，请检查协议",Toast.LENGTH_LONG).show();
				return;
			case 2:
				Toast.makeText(ctx,"网络错误，请检查读写",Toast.LENGTH_LONG).show();
				return;
			case  3:
			default:
				Toast.makeText(ctx,"无法连接到网络，请稍后再试",Toast.LENGTH_LONG).show();
				return;
		}
	}



	//把工作流转换为字符串
	public String convertStreamToString(InputStream is) {   
		   BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        try {   
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "/n");
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                is.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        return sb.toString();
		    }
}
