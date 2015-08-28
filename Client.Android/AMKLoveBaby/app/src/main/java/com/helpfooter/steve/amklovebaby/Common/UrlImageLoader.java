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
        //如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI  
        } else {
            // 从网络上获取图片  
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
                // 返回一个URI对象  
                return Uri.fromFile(file);  
            }  
        }  
        return null;  
    }  
}
