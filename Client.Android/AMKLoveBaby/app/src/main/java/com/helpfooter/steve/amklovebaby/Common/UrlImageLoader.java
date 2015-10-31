package com.helpfooter.steve.amklovebaby.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class UrlImageLoader extends Thread {
	
	private final static String ALBUM_PATH  
    = Environment.getExternalStorageDirectory() +"/"+ StaticVar.ProjectName+ "/imgcache/";

    public void setRunInHandle(boolean runInHandle) {
        this.runInHandle = runInHandle;
    }

    boolean runInHandle=false;

	ImageView imgView;
	String url="";
	Uri uri=null;
	public UrlImageLoader(ImageView _imgView,String _url){
		this.imgView=_imgView;
		this.url=_url;

        File f=new File(ALBUM_PATH);
        if(f.exists()==false){
            f.mkdirs();
        }
        String name = ToolsUtil.Encryption(this.url) + this.url.substring(this.url.lastIndexOf("."));
        File file = new File(f, name);
        if (file.exists()) {
            try{
                imgView.setImageURI(Uri.fromFile(file));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
	}


    public static Bitmap GetBitmap(String _url){
        File f=new File(ALBUM_PATH);
        String name = ToolsUtil.Encryption(_url) + _url.substring(_url.lastIndexOf("."));
        return  BitmapFactory.decodeFile(ALBUM_PATH+name);
    }

    public static String GetImageCacheFileName(String _url){

        String name = ToolsUtil.Encryption(_url) + _url.substring(_url.lastIndexOf("."));
        return ALBUM_PATH+name;
    }

    public static void LoadImage(ImageView _imgView,String _url){
        File f=new File(ALBUM_PATH);
        String name = ToolsUtil.Encryption(_url) + _url.substring(_url.lastIndexOf("."));
        File file = new File(f, name);
        _imgView.setImageURI(Uri.fromFile(file));
    }

	public void run(){
        RealRun();
	}

    public void RealRun(){
        File f=new File(ALBUM_PATH);
        if(f.exists()==false){
            f.mkdirs();
        }
        try{
            uri= GetImageURI(this.url,f);
            Log.i("imageloader_ready", "yes");
            if(uri!=null){
                imgView.setImageURI(uri);
            }
        }catch(Exception e){
            Log.i("imageloader_geterror",e.getMessage());
            e.printStackTrace();
        }
    }

    class LoadFromHandle{

        public Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                RealRun();
            }
        };
    }
	
	public Uri GetImageURI(String path, File cache) throws Exception {
        String name = ToolsUtil.Encryption(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);

        if (file.exists()) {
            int remoteFileLength = GetFileContentLength(path);
            Log.i("imageloader_length",String.valueOf(remoteFileLength)+"="+String.valueOf(file.length()));
            if(file.length()==remoteFileLength) {
                return null;
            }else {
                file.delete();
                file = new File(cache, name);
            }
        }
        Log.i("imageloader_notexists","Yes");
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
            return Uri.fromFile(file);
        }
        Log.i("imageloader_loadgood","Yes");
        return null;
    }

    public int GetFileContentLength(String path) {

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            return Integer.parseInt(conn.getHeaderField("Content-Length"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
