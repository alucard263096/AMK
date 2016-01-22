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

import android.content.Context;
import android.content.res.AssetManager;
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

    boolean isgetFromAsset=false;

    public void setRunInHandle(boolean runInHandle) {
        this.runInHandle = runInHandle;
    }

    boolean runInHandle=false;

    ImageLoadHandle imageLoadHandle;

	ImageView imgView;
	String url="";
	Uri uri=null;
	public UrlImageLoader(ImageView _imgView,String _url) {
        this.imgView = _imgView;
        this.url = _url;

        Bitmap assetImage = getFileFromAsset(_imgView.getContext(), _url);
        if (assetImage != null) {
            try {
                imgView.setImageBitmap(assetImage);
                Log.i("imageasset", _url);
                isgetFromAsset = true;
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.i("imageassetfalse", _url);
            }
        } else {
            Log.i("imageassetnotfound", _url);
        }

        File f = new File(ALBUM_PATH);
        if (f.exists() == false) {
            f.mkdirs();
        }

        try {
            Bitmap bp=GetBitmap(imgView.getContext(),url);
            imgView.setImageBitmap(bp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

         imageLoadHandle=new ImageLoadHandle();
    }

    private static Bitmap getFileFromAsset(Context ctx, String url) {
        String assetFilePath=url.replace(StaticVar.ImageFolderURL,"");
        AssetManager assets = ctx.getAssets();
        InputStream is = null;

        try {
            is = assets.open( assetFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

        return bitmap;
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



    public static Bitmap GetBitmap(Context ctx, String _url){
        Bitmap assetImage=getFileFromAsset(ctx,_url);
        if(assetImage!=null){

            return  assetImage;
        }
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
        try{
        LoadFromHandle loadFromHandle=new LoadFromHandle();
        loadFromHandle.mHandler.sendEmptyMessage(0);
        }catch (Exception ex){
            RealRun();
        }
        //RealRun();
	}

    public void RealRun(){
        Log.e("imgurl",this.url);
        if(isgetFromAsset){
            return;
        }
        File f=new File(ALBUM_PATH);
        if(f.exists()==false){
            f.mkdirs();
        }
        try{
            uri= GetImageURI(this.url,f);
            Log.e("imageloader_ready", "yes");
            imageLoadHandle.mHandler.sendEmptyMessage(0);
        }catch(Exception e){
            Log.e("imageloader_geterror", e.getMessage());
            e.printStackTrace();
        }
    }

    class  ImageLoadHandle{

        public Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                Log.e("imageloader_goload", "yes");
                if(uri!=null){
                    imgView.setImageURI(uri);
                }
            }
        };
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
