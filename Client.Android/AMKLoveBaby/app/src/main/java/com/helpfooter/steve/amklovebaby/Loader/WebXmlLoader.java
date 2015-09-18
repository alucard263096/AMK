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
import android.net.Uri;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.XmlDataTableReader;

public abstract class WebXmlLoader extends Thread{

	protected Context ctx;
	protected String callApi="";
	public WebXmlLoader(Context ctx,String defaultCallApi){
		this.ctx=ctx;
		this.callApi=defaultCallApi;
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

	//需要重写的doXml方法，对返回的业务数据进行交互操作
	abstract public void doXml(ArrayList<HashMap<String,String>> lstRow);

	//多线程调用的线程方法
	public void run(){
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
	//获取远端XML的工作流
	protected InputStream getXml(String path){
		InputStream is=null;
		URL url;
		try {
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
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return is;
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
