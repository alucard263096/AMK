package com.helpfooter.steve.amkdoctor.CustomControlView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amkdoctor.ChatActivity;
import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.DAO.MessageDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.ChatObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MessageObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.ImageShower;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.ChatLoader;
import com.helpfooter.steve.amkdoctor.Loader.MessageLoader;
import com.helpfooter.steve.amkdoctor.R;
import com.helpfooter.steve.amkdoctor.Utils.ChatMsgEntity;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by scai on 2015/9/1.
 */
public class ChatListLoadView implements View.OnClickListener,IWebLoaderCallBack {
    public LinearLayout mainlayout;
    public ArrayList<MessageObj> lstMessages = new ArrayList<MessageObj>();

    public boolean IsFristRun = true;
    public Activity mActivity;
    public int mOrderid;
    public String mCurrUserType;
    private ArrayList<ChatMsgEntity> lstMsgEntity;
    private IWebLoaderCallBack valCallBack;
    private ArrayList<ChatMsgEntity> lstOldList;
    private String fileName;
    private ProgressBar progressBar;
    private int DownedFileLength;
    private int FileLength;


    public ChatListLoadView(Activity activ, LinearLayout layout, int orderid, String currusertype) {
        this.mActivity = activ;
        this.mainlayout = layout;
        this.mOrderid = orderid;
        this.mCurrUserType = currusertype;
    }

    public void LoadList() {
        lstOldList=new ArrayList<ChatMsgEntity>();
        progressBar=(ProgressBar)mActivity.findViewById(R.id.progressBar);
        valCallBack = this;
        new Thread() {
            public void run() {
                try {
                    while (true) {
                        ChatLoader loader = new ChatLoader(mActivity, mOrderid);
                        loader.setCallBack(valCallBack);
                        loader.run();
                        sleep(3000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }.start();
    }


    //currUserType 当前用户类型C 用户 D 医生
    private void initMsgEntity(String context) {
        if (context.isEmpty()) {
            return;
        }
        lstMsgEntity = new ArrayList<ChatMsgEntity>();
        String[] messageList = context.split("\\{\\|\\}");
        int i=1;
        for (String message : messageList) {
            if (message.isEmpty()) {
                continue;
            }
            String[] arrMessage = new String[3];
            arrMessage = message.split(":");
            if (arrMessage.length != 3) {
                continue;
            }
            ChatMsgEntity cme = new ChatMsgEntity();
            if (arrMessage[0].equals(this.mCurrUserType)) {
                cme.setMsgType(false);
            } else {
                cme.setMsgType(true);
            }
            cme.setContextType(arrMessage[1]);
            cme.setMessage(arrMessage[2]);
            cme.setSeq(i);
            lstMsgEntity.add(cme);

        }
    }

    private Handler onloadMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            OnloadMessage();
        }
    };

    private void OnloadMessage() {

        if(lstMsgEntity.size()<=lstOldList.size())
        {
            return;
        }
        List<ChatMsgEntity> NewMstEntitys=lstMsgEntity.subList(lstOldList.size(),lstMsgEntity.size());
        //mainlayout.removeAllViewsInLayout();
        for (ChatMsgEntity obj : NewMstEntitys) {

            LinearLayout sublayout = null;


            sublayout = LoadChatListData(obj);

            if (sublayout != null) {
                sublayout.setTag(obj);
                try {
                    mainlayout.addView(sublayout);
                } catch (Exception ex) {

                    Log.i("ERROR", ex.toString());
                }
                lstOldList.add(obj);
            }

        }
        onloadScrollHandler.sendEmptyMessage(0);

    }

    private Handler onloadScrollHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ScrollView scrollView = (ScrollView) mActivity.findViewById(R.id.chatScroll);
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    };

    private LinearLayout LoadChatListData(ChatMsgEntity obj) {
        PercentLinearLayout layout = new PercentLinearLayout(this.mActivity);
        PercentLinearLayout.LayoutParams param = ToolsUtil.getLayoutParam();

        param.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.10f, false);
        param.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f, false);
        param.mPercentLayoutInfo.rightMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f, false);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        //对方发送消息
        if (obj.getMsgType()) {
            //添加头像
            ImageView headerView = getPhotoView(obj);
            layout.addView(headerView);
        }

        //文本
        if (obj.getContextType().equals(StaticVar.TxtType)) {
            TextView contextView = getTextView(obj);
            layout.addView(contextView);
        }
        //文件
        if (obj.getContextType().equals(StaticVar.DOCType)) {
            TextView contextView = getTextView(obj);
            contextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
            contextView.setTag(obj);
            contextView.setOnClickListener(this);
            layout.addView(contextView);
        }
        if (obj.getContextType().equals(StaticVar.IMGType)) {
            ImageView IMGView = getIMGView(obj);
            //图片重新设置父项高度
            // PercentLinearLayout.LayoutParams topparam=(PercentLinearLayout.LayoutParams)layout.getLayoutParams();
            //param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f,false);
            layout.addView(IMGView);
        }
        //自己发送消息
        if (!obj.getMsgType()) {
            //添加头像
            ImageView headerView = getPhotoView(obj);
            layout.addView(headerView);
        }


        return layout;

    }


    //文本内容
    public TextView getTextView(ChatMsgEntity obj) {
        TextView txtContext = new MyTextView(this.mActivity);
        PercentLinearLayout.LayoutParams contextparam = ToolsUtil.getLayoutParam();
        contextparam.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.8f, true);

        if (obj.getMsgType()) {
            contextparam.gravity = Gravity.LEFT;
            txtContext.setGravity(Gravity.LEFT);
        } else {
            contextparam.gravity = Gravity.RIGHT;
            txtContext.setGravity(Gravity.RIGHT);
        }


        txtContext.setLayoutParams(contextparam);
        txtContext.setText(obj.getMessage());
        txtContext.setTextColor(Color.GRAY);
        txtContext.setTextSize(13);
        return txtContext;
    }

    //头像
    public ImageView getPhotoView(ChatMsgEntity obj) {
        ImageView img = new ImageView(this.mActivity);

        PercentLinearLayout.LayoutParams param = ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.2f, true);


        if (obj.getMsgType()) {
            param.gravity = Gravity.LEFT;
            img.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            param.gravity = Gravity.RIGHT;
            img.setScaleType(ImageView.ScaleType.FIT_END);
        }

        String url = "http://www.myhkdoc.com/AMK/FilesServer/doctor/15083123000b.png";
        Log.i("doctor_photo", url);
        UrlImageLoader imgLoad = new UrlImageLoader(img, url);
        imgLoad.start();

        img.setLayoutParams(param);


        return img;
    }

    //图片
    public ImageView getIMGView(ChatMsgEntity obj) {
        ImageView img = new ImageView(this.mActivity);

        PercentLinearLayout.LayoutParams param = ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.8f, true);
        param.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);

        if (obj.getMsgType()) {
            param.gravity = Gravity.LEFT;
            img.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            param.gravity = Gravity.RIGHT;
            img.setScaleType(ImageView.ScaleType.FIT_END);
        }

        String url = StaticVar.IMGCHATURL + obj.getMessage();
        Log.i("doctor_photo", url);
        UrlImageLoader imgLoad = new UrlImageLoader(img, url);
        imgLoad.start();

        img.setLayoutParams(param);
        img.setAdjustViewBounds(true);
        img.setTag(obj);
        img.setOnClickListener(this);
        return img;
    }

    @Override
    public void onClick(View v) {
        //先判断是否图片点击
        ChatMsgEntity obj = (ChatMsgEntity) v.getTag();
        if (obj.getContextType().equals(StaticVar.IMGType)) {
            ImageView imageview=(ImageView)v;
            String url=StaticVar.IMGCHATURL+ obj.getMessage();
            Intent intent = new Intent(mActivity, ImageShower.class);
            intent.putExtra("url", url);
            this.mActivity.startActivity(intent);



        } else if (obj.getContextType().equals(StaticVar.DOCType)) {
            fileName = obj.getMessage();
            new Thread() {
                public void run() {
                    try {
                        downLoadFile(fileName);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        for (AbstractObj obj : lstObjs) {

            ChatObj chatobj = (ChatObj) obj;
            initMsgEntity(chatobj.getContent());
        }

        if (this.lstMsgEntity != null && this.lstMsgEntity.size() > 0) {
            onloadMessageHandler.sendEmptyMessage(0);
        }

    }

    private Handler downhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        progressBar.setMax(FileLength);
                        progressBar.setVisibility(View.VISIBLE);
                        Log.i("文件长度----------->", progressBar.getMax() + "");
                        break;
                    case 1:
                        progressBar.setProgress(DownedFileLength);
                        int x = DownedFileLength * 100 / FileLength;

                        break;
                    case 2:
                        Toast.makeText(mActivity.getApplicationContext(), "下载完成", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }
        }

    };


    //文件下载
    private void downLoadFile(String fileName){
         //本地sd卡存放目录
        String dirName = "/sdcard/AMKChat/";
        //本地存放路径
        String newFilename = dirName + fileName;
        //文件网路路径
        String urlDownloadAll=StaticVar.IMGCHATURL+fileName;
        //判断本地存放目录是否存，不存在则新建文件夹
        File f = new File(dirName);
        if(!f.exists())
        {
            f.mkdir();
        }
            File file = new File(newFilename);
            //如果目标文件已经存在，则删除，产生覆盖旧文件的效果（此处以后可以扩展为已经存在图片不再重新下载功能）
            if(file.exists())
            {
                file.delete();
            }
            try{
                //构造URL
                URL url = new URL(urlDownloadAll);
                //打开连接
                URLConnection con = url.openConnection();
                //获得文件的长度

                //System.out.println("长度 :"+contentLength);
                //输入流

                //1K的数据缓冲



                Message message=new Message();
                try {
                    InputStream is = con.getInputStream();
                     OutputStream os = new FileOutputStream(newFilename);
                    byte [] buffer=new byte[1024*4];
                    FileLength = con.getContentLength();
                    message.what=0;
                    downhandler.sendMessage(message);
                    while (DownedFileLength<FileLength) {
                        os.write(buffer);
                        DownedFileLength+=is.read(buffer);
                        Log.i("-------->", DownedFileLength+"");
                        Message message1=new Message();
                        message1.what=1;
                        downhandler.sendMessage(message1);
                    }
                    Message message2=new Message();
                    message2.what=2;
                    downhandler.sendMessage(message2);
                    //完毕，关闭所有链接
                    os.close();
                    is.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            }catch(Exception e){
                e.printStackTrace();
            }

    }

    //图片点击处理


}
