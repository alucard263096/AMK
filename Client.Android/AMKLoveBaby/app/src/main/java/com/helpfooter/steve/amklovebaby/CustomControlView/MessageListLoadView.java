package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.ChatActivity;
import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.MessageDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MessageObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.MessageLoader;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by scai on 2015/9/1.
 */
public class MessageListLoadView implements View.OnClickListener,IWebLoaderCallBack {
    public LinearLayout mainlayout;
    public ArrayList<MessageObj> lstMessages=new ArrayList<MessageObj>() ;
    public Context ctx;
    public boolean IsFristRun=true;
    public Activity mActivity;
    ArrayList<LinearLayout> lstLayout=new ArrayList<LinearLayout>();
    public MessageListLoadView(Activity activ, Context ctx, LinearLayout layout){
        this.mActivity=activ;
        this.ctx=ctx;
        this.mainlayout=layout;

    }

    public void LoadList(){
        MessageLoader loader=new MessageLoader(this.ctx);
        loader.setCallBack(this);
        loader.start();
    }

    private Handler onloadMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            OnloadMessage();
        }
    };
    private void OnloadMessage() {

        if(this.mainlayout.getChildCount()>0) {
            this.mainlayout.removeViews(0, this.mainlayout.getChildCount() - 1);
        }
        //mainlayout.removeAllViewsInLayout();
        for(MessageObj obj:lstMessages){

            LinearLayout sublayout=null;

            sublayout=LoadMessageListData(obj);

            if(sublayout!=null){
                sublayout.setTag(obj);
                try {
                    mainlayout.addView(sublayout);
                }
                catch (Exception ex)
                {

                    Log.i("ERROR",ex.toString());
                }
                lstLayout.add(sublayout);
            }

        }

    }




    public ImageView getPhotoView(MessageObj obj)  {
        ImageView img=new ImageView(this.ctx);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.2f,true);
       // param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);

        String strurl= "http://www.myhkdoc.com/AMK/FilesServer/doctor/15083123000b.png";
        Log.i("doctor_photo", strurl);
        UrlImageLoader imgLoad=new UrlImageLoader(img,strurl);
        imgLoad.start();
       /* Drawable drawable = img.getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            img.setImageBitmap(toOvalBitmap(bitmap, 1f));
        }*/
       img.setLayoutParams(param);

        return img;
    }



    public  Bitmap toOvalBitmap(Bitmap bitmap,float ratio) {
        Bitmap output=Bitmap.createBitmap(bitmap.getHeight(),bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(output);
        Paint paint=new Paint();
        Rect rect=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        RectF rectF=new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect,rectF, paint);
        return output;
    }



    public LinearLayout LoadMessageListData(MessageObj obj){
        PercentLinearLayout toplayout=new PercentLinearLayout(ctx);
        PercentLinearLayout.LayoutParams topparam=ToolsUtil.getLayoutParam();
        topparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.10f,false);
        topparam.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
        topparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
        topparam.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
        topparam.mPercentLayoutInfo.bottomMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
        toplayout.setLayoutParams(topparam);
        toplayout.setOrientation(LinearLayout.VERTICAL);

        PercentLinearLayout layout=new PercentLinearLayout(ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.98f,false);
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);



        layout.setBackgroundColor(Color.parseColor("#ffffff"));
        //String backgroundcolor=getBackgroundColor(i);
        //layout.setBackgroundColor(Color.parseColor(backgroundcolor));
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imgPhoto=getPhotoView(obj);
        layout.addView(imgPhoto);
        LinearLayout infolayout=getInfoLayout(obj);
        layout.addView(infolayout);
        toplayout.addView(layout);
        LinearLayout line =ToolsUtil.GenPLine(this.ctx);
        toplayout.addView(line);
        layout.setTag(obj);
        layout.setOnClickListener(this);
        return toplayout;

        //this.mainlayout.addView(layout);

    }





    public LinearLayout getInfoLayout(MessageObj message){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.8f,true);
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,true);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.VERTICAL);


        TextView txtContent=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams upvoteparam= ToolsUtil.getLayoutParam();
        upvoteparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        upvoteparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.1f,true);
        upvoteparam.gravity=Gravity.BOTTOM;
        txtContent.setLayoutParams(upvoteparam);
        String strContent = "";
        String[] arrMessage=message.getLast_one().split(":");
        if (message.getLast_one().equals("")||arrMessage.length<3) {
            strContent=message.getDescription();
        }
        else
        {

            strContent="";
            if(arrMessage[1].equals(StaticVar.DOCType)) {
                strContent = "[文件]";
            }
            else if(arrMessage[1].equals(StaticVar.IMGType))
            {
                strContent = "[图片]";
            }
            else
            {
                strContent=arrMessage[2];
            }
        }
        txtContent.setText(strContent);
        txtContent.setTextSize(14);

         PercentLinearLayout tipsLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams buttonparam=ToolsUtil.getLayoutParam();
        buttonparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        buttonparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.1f,true);

        tipsLayout.setLayoutParams(buttonparam);
        tipsLayout.setOrientation(LinearLayout.HORIZONTAL);



        //设置聊天对象名
        PercentLinearLayout.LayoutParams nameparam=ToolsUtil.getLayoutParam();
        nameparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,true);
        //  nameparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
        nameparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,true);
        nameparam.gravity=Gravity.LEFT;
        TextView txtName = new MyTextView(this.ctx);

        txtName.setTextSize(15);
        txtName.setGravity(Gravity.LEFT);
        txtName.setLayoutParams(nameparam);
        txtName.setText(message.getName());
        TextPaint tp= txtName.getPaint();
        tp.setFakeBoldText(true);
        Log.i("member_name", message.getName());
        //设置最后一次聊天时间
        TextView lastchatTime=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams lastchatparam=ToolsUtil.getLayoutParam();
        lastchatparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.68f,true);
        // lastchatparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
        lastchatparam.gravity=Gravity.RIGHT;
        //lastchatTime.setGravity(Gravity.CENTER_HORIZONTAL);
        lastchatTime.setGravity(Gravity.RIGHT);
        lastchatTime.setLayoutParams(lastchatparam);
        lastchatTime.setTextColor(Color.GRAY);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateformat.parse(message.getCreated_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateformat =   new SimpleDateFormat( " MM月dd日 HH:mm" );

        String strOrderTime = dateformat.format(date);
        lastchatTime.setText(strOrderTime);
        lastchatTime.setTextSize(13);

        Log.i("video_chatName", message.getCreated_time());
        tipsLayout.addView(txtName);
        tipsLayout.addView(lastchatTime);

        layout.addView(tipsLayout);
        layout.addView(txtContent);

        return  layout;
    }


    private String getBackgroundColor(int number) {
        switch (number%4){
            case 0:
                return "#FD7CAD";
            case 2:
                return "#F7CB20";
            case 3:
                return "#B9E5C3";
            case 4:
                return "#ffffff";
            default:
                return "#37A4D4";
        }
    }

    @Override
    public void onClick(View v) {
        MessageObj obj=(MessageObj)v.getTag();
       /* UserItem item=new UserItem(Integer.parseInt(obj.getCustid()),obj.getCustname(),"192.168.1.1");
       *//* BussinessCenter.sessionItem = new SessionItem(0, item.getUserId(),
                obj.getDoctorid());*//*
        *//*Dialog dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_CALLRESUME,
                item, mActivity);
        dialog.show();*//*
        BussinessCenter.mContext=this.mActivity;
        BussinessCenter.getBussinessCenter().onVideoCallStart(
                0, item.getUserId(),
                obj.getDoctorid(), "Test");*/
        Intent intent = new Intent(mActivity, ChatActivity.class);
        intent.putExtra("orderId", String.valueOf(obj.getId()));
        intent.putExtra("tag", String.valueOf(obj.getDoctor_id()));
        mActivity.startActivity(intent);

    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        MessageDao dao=new MessageDao(this.ctx);

        //ArrayList<AbstractObj> lstObj=dao.getMessageList(StaticVar.Doctor.getId());

        for(AbstractObj obj:lstObjs) {
            lstMessages.add((MessageObj)obj);
        }

        if(lstMessages.size()>0) {
            onloadMessageHandler.sendEmptyMessage(0);
        }
        if(IsFristRun)
        {
            new Thread(){
                public void run()
                {
                    try {
                        while(true) {
                            sleep(1000000);
                            LoadList();

                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        IsFristRun =false;
    }
}
