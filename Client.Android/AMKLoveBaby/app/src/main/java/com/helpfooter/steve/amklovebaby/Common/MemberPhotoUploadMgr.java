package com.helpfooter.steve.amklovebaby.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.VersionObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IMemberPhotoUploadCallBack;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.MemberPhotoAddLoader;
import com.helpfooter.steve.amklovebaby.Loader.VersionLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.File;
import java.lang.String;
import java.util.ArrayList;

/**
 * Created by Steve on 2015/10/7.
 */
public class MemberPhotoUploadMgr {

    Activity ctx;
    ArrayList<String> lst;
    AlertDialog dialog;

    public String GetReturnFileList() {
        return returnFileList;
    }

    String returnFileList;

    public MemberPhotoUploadMgr(Activity ctx,ArrayList<String> lst){
        this.ctx=ctx;
        this.lst=lst;
        returnFileList="";
    }

    public void StartUpload(){
        showUploadProcessDialoghandler.sendEmptyMessage(0);
    }



    Handler showUploadProcessDialoghandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            showUploadProcessDialog();
        }
    };

    Handler uploadFailDialoghandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(ctx, "上传失败，请重新上传", Toast.LENGTH_LONG).show();
        }
    };


    IMemberPhotoUploadCallBack callBack;
    public void setCallBack(IMemberPhotoUploadCallBack val){
        callBack=val;
    }

    /*
     * 从服务器中下载APK
     */
    protected void showUploadProcessDialog() {
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(this.ctx);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在上传病例");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    pd.setMax(lst.size());
                    int count=0;
                    for(String path:lst){
                        UploadMgr mgr=new UploadMgr(ctx,path,"member");
                        mgr.StartUpload();
                        while (!mgr.IsCompleted()){
                            Thread.sleep(1000);
                        }
                        if(mgr.IsSuccess()){
                            returnFileList+=","+mgr.GetReturnFile();
                        }
                        count++;
                        pd.setProgress(count);
                    }
                    if(callBack!=null){
                        callBack.CallBack(returnFileList);
                    }
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    uploadFailDialoghandler.sendEmptyMessage(0);
                    pd.dismiss(); //结束掉进度条对话框
                    e.printStackTrace();
                }
            }}.start();
    }
}
