package com.helpfooter.steve.amklovebaby.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Steve on 2015/10/8.
 */
public class UploadMgr {
    Context ctx;
    String path;

    public String GetReturnFile() {
        return returnFile;
    }

    String returnFile;
    boolean isCompleted=false;
    boolean isSuccess=false;

    public boolean IsCompleted() {
        return isCompleted;
    }

    public boolean IsSuccess() {
        return isSuccess;
    }

    public UploadMgr(Context ctx, String path) {
        this.ctx = ctx;
        this.path = path;
    }

    public  void StartUpload() {
        //异步的客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        //指定url路径
        String url = StaticVar.UPLOADFILEURL4Member;
        //封装文件上传的参数
        RequestParams params = new RequestParams();
        //根据路径创建文件
        File file = new File(path);
        try {
            //放入文件
            params.put("uploadfile", file);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("文件不存在----------");
        }
        //执行post请求
        client.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, String result) {
                if (i == 200) {
                    String[] arrResult = result.split("\\|");
                    if (arrResult != null && arrResult.length == 3) {
                        String filename = arrResult[2];
                        returnFile = filename;
                        isSuccess = true;
                    }
                    isCompleted = true;
                }
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String result, Throwable throwable) {
                isCompleted = true;
            }

        });
    }
}
