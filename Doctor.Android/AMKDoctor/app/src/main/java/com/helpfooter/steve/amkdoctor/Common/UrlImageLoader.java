package com.helpfooter.steve.amkdoctor.Common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
	public void run(){
		File f=new File(ALBUM_PATH);
		if(f.exists()==false){
			boolean b=f.mkdirs();
            String a="";
		}
		try{
			uri= GetImageURI(this.url,f);
            imgView.setImageURI(uri);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

    public static Bitmap copressImage(String _url){
        String name = ToolsUtil.Encryption(_url) + _url.substring(_url.lastIndexOf("."));
        File picture = new File(ALBUM_PATH+name);
        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
//下面这个设置是将图片边界不可调节变为可调节
        bitmapFactoryOptions.inJustDecodeBounds = true;
        bitmapFactoryOptions.inSampleSize = 2;
        int outWidth = bitmapFactoryOptions.outWidth;
        int outHeight = bitmapFactoryOptions.outHeight;
        Bitmap bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),
                bitmapFactoryOptions);
        float imagew = 150;
        float imageh = 150;
        int yRatio = (int) Math.ceil(bitmapFactoryOptions.outHeight
                / imageh);
        int xRatio = (int) Math
                .ceil(bitmapFactoryOptions.outWidth / imagew);
        if (yRatio > 1 || xRatio > 1) {
            if (yRatio > xRatio) {
                bitmapFactoryOptions.inSampleSize = yRatio;
            } else {
                bitmapFactoryOptions.inSampleSize = xRatio;
            }
        }
        bitmapFactoryOptions.inJustDecodeBounds = false;
        bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),
                bitmapFactoryOptions);
        if(bmap != null){
//ivwCouponImage.setImageBitmap(bmap);
            return bmap;
        }
        return null;
    }


    public static Bitmap GetBitmap(String _url){
        File f=new File(ALBUM_PATH);
        String name = ToolsUtil.Encryption(_url) + _url.substring(_url.lastIndexOf("."));
        return  BitmapFactory.decodeFile(ALBUM_PATH+name);
    }

	public Uri GetImageURI(String path, File cache) throws Exception {
        String name = ToolsUtil.Encryption(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);
        if (file.exists()) {
            return Uri.fromFile(file);
        } else {

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
        }  
        return null;  
    }  
}
