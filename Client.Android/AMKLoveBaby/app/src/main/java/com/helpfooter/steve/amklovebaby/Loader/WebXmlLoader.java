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

import android.net.Uri;

public abstract class WebXmlLoader extends Thread{
	
	abstract public String getCallUrl();
	
	abstract public void doXml(InputStream is);
	
	public void run(){
		String path=getCallUrl();
		InputStream is=getXml(path);
		if(is!=null){
			doXml(is);
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
	            
	            // 返回一个URI对象  
	            //String ret= convertStreamToString(is);  
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
