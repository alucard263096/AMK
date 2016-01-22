package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.ChatActivity;
import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ChatObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.ImageShower;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.ChartPhotoCancelLoader;
import com.helpfooter.steve.amklovebaby.Loader.ChatLoader;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.ChatMsgEntity;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

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
public class ChatListLoadView  implements View.OnClickListener,IWebLoaderCallBack, View.OnLongClickListener {
    public LinearLayout mainlayout;
    public boolean IsFristRun = true;
    public Activity mActivity;
    public int mOrderid;
    public DoctorObj doctor;
    public String mCurrUserType;
    private ArrayList<ChatMsgEntity> lstMsgEntity;
    private String fileName;
    private ProgressBar progressBar;
    private int DownedFileLength;
    private int FileLength;
    public ArrayList<ChatMsgEntity> lstOldList;
    private Bitmap myphoto;
    private Bitmap doctorphoto;

    private String updated_date="";
    private ChartPhotoCancelLoader cancelloader;
   private ChatMsgEntity imgobj;
    public ChatListLoadView(Activity activ, LinearLayout layout, int orderid,int doctorid, String currusertype) {
        this.mActivity = activ;
        this.mainlayout = layout;
        this.mOrderid = orderid;
        this.mCurrUserType = currusertype;

        DoctorDao doctorDao=new DoctorDao(mActivity);
       doctor=(DoctorObj)doctorDao.getObj(doctorid);

        String doctorphotourl=StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto();
        doctorphoto=UrlImageLoader.GetBitmap(this.mActivity, doctorphotourl);


        String memberphotourl=StaticVar.ImageFolderURL+"member/"+StaticVar.Member.getPhoto();
        myphoto=UrlImageLoader.GetBitmap(this.mActivity,memberphotourl);

    }

    ChatLoader loader;
    public void LoadContent(){
        lstOldList=new ArrayList<ChatMsgEntity>();
        loader = new ChatLoader(mActivity.getApplicationContext(), mOrderid,doctor.getId());
        loader.setCallBack(this);
        loader.setCircleSecond(1);
        loader.setIsCircle(true);
        loader.start();
    }

    public void UnloadContent(){

        loader.setIsCircle(false);
        loader.interrupt();
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

        if(lstMsgEntity.size()==lstOldList.size())
        {
            return;
        }
        List<ChatMsgEntity> NewMstEntitys=lstMsgEntity.subList(lstOldList.size(),lstMsgEntity.size());
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

    public LinearLayout LoadChatListData(ChatMsgEntity obj) {
        PercentLinearLayout layout = new PercentLinearLayout(this.mActivity);
        PercentLinearLayout.LayoutParams param = ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.9f,true);
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        param.topMargin=15;
        param.bottomMargin=15;
        layout.setLayoutParams(param);


        ImageView doctorView = getPhotoView();
        layout.addView(doctorView);



        PercentLinearLayout content = new PercentLinearLayout(this.mActivity);
        //content.setBackground(mActivity.getResources().getDrawable(R.drawable.view_raduis));
        PercentLinearLayout.LayoutParams contentparam =ToolsUtil.getLayoutParamHeightWrap();
        contentparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.7f,true);
        content.setLayoutParams(contentparam);
        layout.addView(content);
        if (obj.getContextType().equals(StaticVar.TxtType)) {
            TextView contextView = getTextView(obj);
            content.addView(contextView);
        }else if (obj.getContextType().equals(StaticVar.DOCType)) {

            TextView contextView = getTextView(obj);
            contextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
            contextView.setTag(obj);

            contextView.setText("发送了一个文件");

            contextView.setOnClickListener(this);
            content.addView(contextView);

        }else if (obj.getContextType().equals(StaticVar.IMGType)) {
            ImageView IMGView = getIMGView(obj);
            content.addView(IMGView);
        }

        ImageView myView = getPhotoView();
        layout.addView(myView);



        if (obj.getMsgType()) {
            doctorView.setImageBitmap(doctorphoto);
            content.setGravity(Gravity.LEFT);
        }else {
           myView.setImageBitmap(myphoto);
            content.setGravity(Gravity.RIGHT);
        }

        return layout;

    }

    public LinearLayout LoadChatListImgData(ChatMsgEntity obj,String path) {
        PercentLinearLayout layout = new PercentLinearLayout(this.mActivity);
        PercentLinearLayout.LayoutParams param = ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.9f,true);
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        param.topMargin=15;
        param.bottomMargin=15;
        layout.setLayoutParams(param);


        ImageView doctorView = getPhotoView();
        layout.addView(doctorView);



        PercentLinearLayout content = new PercentLinearLayout(this.mActivity);
        //content.setBackground(mActivity.getResources().getDrawable(R.drawable.view_raduis));
        PercentLinearLayout.LayoutParams contentparam =ToolsUtil.getLayoutParamHeightWrap();
        contentparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.7f,true);
        content.setLayoutParams(contentparam);
        layout.addView(content);

            ProcessImageView IMGView = getProcessIMGView(obj,path);
            content.addView(IMGView);


        ImageView myView = getPhotoView();
        layout.addView(myView);



        if (obj.getMsgType()) {
            doctorView.setImageBitmap(doctorphoto);
            content.setGravity(Gravity.LEFT);
        }else {
            myView.setImageBitmap(myphoto);
            content.setGravity(Gravity.RIGHT);
        }

        return layout;

    }


    //文本内容
    public TextView getTextView(ChatMsgEntity obj) {
        TextView txtContext = new MyTextView(this.mActivity);
        //txtContext.setBackgroundColor(Color.parseColor("#ccaacc"));
        //txtContext.setBackgroundResource(R.drawable.chatbg);
        if (obj.getMsgType()) {
            txtContext.setBackgroundResource(R.drawable.text_view_border4);
        }else {
            txtContext.setBackgroundResource(R.drawable.text_view_border3);
        }
        PercentLinearLayout.LayoutParams contentparam = ToolsUtil.getLayoutParamWidthHeightWrap();
        contentparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        contentparam.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        txtContext.setPadding(20,0,20,0);

        if (obj.getMsgType()) {
            txtContext.setGravity(Gravity.LEFT);
        }else {
            txtContext.setGravity(Gravity.LEFT);
        }
        txtContext.setLayoutParams(contentparam);
        txtContext.setText(obj.getMessage());
        //txtContext.setTextSize(17);
        return txtContext;
    }

    //头像

    public ImageView getPhotoView() {
        ImageView img = new ImageView(this.mActivity);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        PercentLinearLayout.LayoutParams param =ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.15f,true);
        img.setAdjustViewBounds(true);

        img.setLayoutParams(param);
        return img;
    }
//
//    //图片
    public ImageView getIMGView(ChatMsgEntity obj) {
        ImageView img = new ImageView(this.mActivity);
        //img.setBackgroundColor(Color.parseColor("#cceecc"));
        if (obj.getMsgType()) {
            img.setBackgroundResource(R.drawable.text_view_border4);
        }else {
            img.setBackgroundResource(R.drawable.text_view_border3);
        }
        img.setPadding(20,20, 20, 20);
        //img.setBackgroundColor(Color.parseColor("#ccaacc"));
        PercentLinearLayout.LayoutParams contentparam = ToolsUtil.getLayoutParamWidthHeightWrap();
        contentparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        contentparam.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);


        String url = StaticVar.IMGCHATURL + obj.getMessage();
        Log.i("doctor_photo", url);
        /*UrlImageLoader imgLoad = new UrlImageLoader(img, url);
        imgLoad.start();*/
        Bitmap bitmap=UrlImageLoader.copressImage(url);
        img.setImageBitmap(bitmap);
        //img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setLayoutParams(contentparam);
        img.setAdjustViewBounds(true);
        img.setTag(obj);
        img.setOnClickListener(this);
        img.setOnLongClickListener(this);
        return img;
    }


    public ProcessImageView getProcessIMGView(ChatMsgEntity obj,String path) {
        ProcessImageView img = new ProcessImageView(this.mActivity);
        //img.setBackgroundColor(Color.parseColor("#cceecc"));
        if (obj.getMsgType()) {
            img.setBackgroundResource(R.drawable.text_view_border4);
        }else {
            img.setBackgroundResource(R.drawable.text_view_border3);
        }
        img.setPadding(20,20, 20, 20);
        //img.setBackgroundColor(Color.parseColor("#ccaacc"));
        PercentLinearLayout.LayoutParams contentparam = ToolsUtil.getLayoutParamWidthHeightWrap();
        contentparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        contentparam.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);

        Bitmap bitmap = null;
        try
        {
            File file = new File(path);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(path);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }


        img.setImageBitmap(bitmap);
        //img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setLayoutParams(contentparam);
        img.setAdjustViewBounds(true);
        img.setTag(obj);
        img.setOnClickListener(this);
        img.setOnLongClickListener(this);
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
        if(lstObjs.size()>0) {
            for (AbstractObj obj : lstObjs) {
                ChatObj chatobj = (ChatObj) obj;
                if(!updated_date.equals(chatobj.getUpdated_date())){
                    initMsgEntity(chatobj.getContent());
                    if (this.lstMsgEntity != null && this.lstMsgEntity.size() > 0) {
                        onloadMessageHandler.sendEmptyMessage(0);
                    }
                    updated_date=chatobj.getUpdated_date();
                }
            }
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

    @Override
    public boolean onLongClick(View v) {
        imgobj = (ChatMsgEntity) v.getTag();
        deletedialog();
        return false;
    }

    protected void deletedialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("确认撤销图片发送吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                cancelloader=new ChartPhotoCancelLoader(mActivity,mOrderid,doctor.getId(),"C:IMG:"+imgobj.getMessage());

                new Thread(){
                    public void run()
                    {
                        try {
                            lstOldList.clear();
                            cancelloader.run();
                        } catch (Exception e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    //图片点击处理


}
