package com.helpfooter.steve.amkdoctor.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amkdoctor.DAO.ParamsDao;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.XmlDataTableReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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

	public String getCallUrl() {
		// TODO Auto-generated method stub
		ParamsDao dao=new ParamsDao(ctx);
		String api=callApi;
		String url=StaticVar.dictHashMap.get(callApi);
		String update_date=dao.getParam(callApi, "1991-1-1");
		url= (url+"?last_time="+update_date).replace(" ", "%20");
		Log.i("IndexBanner_debugUrl", url);
		return url;
	}
	
	abstract public void doXml(ArrayList<HashMap<String,String>> lstRow);
	
	public void run(){
		String path=getCallUrl();
		InputStream is=getXml(path);
		if(is!=null){
			XmlDataTableReader xmlreader=new XmlDataTableReader(is);
			doXml(xmlreader.getDataTableValue());
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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
	            
	            //����һ��URI����
	            //String ret= convertStreamToString(is);
				//Log.i("returnXml",ret);
	            //is.close();
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
