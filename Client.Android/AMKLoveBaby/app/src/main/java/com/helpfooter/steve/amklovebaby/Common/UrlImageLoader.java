package com.helpfooter.steve.amklovebaby.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class UrlImageLoader extends Thread {
	
	private final static String ALBUM_PATH  
    = Environment.getExternalStorageDirectory() +"/"+ StaticVar.ProjectName+ "/imgcache/";
	
	ImageView imgView;
	String url="";
	Uri uri=null;
	public UrlImageLoader(ImageView _imgView,String _url){
		this.imgView=_imgView;
		this.url=_url;
	}
	private Handler mHandler = new Handler(){  
        public void handleMessage(android.os.Message msg) {  
            if(msg.what == 0){  
            	imgView.setImageURI(uri);
            }  
        };  
    }; 
	public void run(){
		File f=new File(ALBUM_PATH);
		if(f.exists()==false){
			boolean b=f.mkdirs();
            String a="";
		}
		try{
			uri= GetImageURI(this.url,f);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(uri!=null){
			mHandler.sendEmptyMessage(0);
		}
	}
	
	public Uri GetImageURI(String path, File cache) throws Exception {
        String name = ToolsUtil.Encryption(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);  
        //���ͼƬ���ڱ��ػ���Ŀ¼����ȥ����������
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)��������ܵõ��ļ���URI  
        } else {
            // �������ϻ�ȡͼƬ  
            URL url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setRequestMethod("GET");  
            conn.setDoInput(true);  
            if (conn.getResponseCode() == 200) {  
  
                InputStream is = conn.getInputStream();  
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];  
                int len = 0;  
                while ((len = is.read(buffer)) != -1) {  
                    fos.write(buffer, 0, len);  
                }  
                is.close();  
                fos.close();  
                // ����һ��URI����  
                return Uri.fromFile(file);  
            }  
        }  
        return null;  
    }  
}