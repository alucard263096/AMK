package com.helpfooter.steve.amkdoctor.Common;

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

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.VersionObj;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.VersionLoader;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Steve on 2015/10/7.
 */
public class VersionUpdateMgr implements IWebLoaderCallBack {

    Activity ctx;
    VersionObj version;
    AlertDialog dialog;

    public VersionUpdateMgr(Activity ctx){
        this.ctx=ctx;
    }

    public void startCheckVersion(){
        VersionLoader loader=new VersionLoader(ctx);
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(lstObjs.size()>0){
            VersionObj version=(VersionObj)lstObjs.get(0);
            String currentVersion=getVersionName();
            if(currentVersion.equals("")==false
                    && version.getVersion().equals(currentVersion)==false) {
                this.version=version;
                showUpdataDialoghandler.sendEmptyMessage(0);
            }
        }
    }

    Handler showUpdataDialoghandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            showUpdataDialog();
        }
    };

    Handler downloadFailDialoghandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Toast.makeText(ctx, "下载新版本失败", Toast.LENGTH_LONG).show();
            dialog.cancel();
        }
    };
    /*
     *
     * 弹出对话框通知用户更新程序
     *
     * 弹出对话框的步骤：
     *  1.创建alertDialog的builder.
     *  2.要给builder设置属性, 对话框的内容,样式,按钮
     *  3.通过builder 创建一个对话框
     *  4.对话框show()出来
     */
    protected void showUpdataDialog() {
        final AlertDialog.Builder builer = new AlertDialog.Builder(this.ctx) ;
        builer.setTitle("版本升级");
        builer.setMessage("检测到最新版本，请及时更新！");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("STARTDOWNLOAD", "下载apk,更新");
                downLoadApk();
            }
        });
        //当点取消按钮时进行登录
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        dialog = builer.create();
        dialog.show();
    }



    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(this.ctx);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = DownloadMgr.getFileFromServer(version.getUrl(), pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    downloadFailDialoghandler.sendEmptyMessage(0);
                    e.printStackTrace();
                }
            }}.start();
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        ctx.startActivity(intent);
    }
    /*
 * 获取当前程序的版本号
 */
    private String getVersionName() {

//        //获取packagemanager的实例
//        PackageManager packageManager = ctx.getPackageManager();
//        //getPackageName()是你当前类的包名，0代表是获取版本信息
//        PackageInfo packInfo = null;
//        try {
//            packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return "";
//        }
//        return packInfo.versionName;
        return StaticVar.CurrentVersion;
    }
}
