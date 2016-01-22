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
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.ChatActivity;
import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.MessageDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
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
import java.util.HashMap;

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
    public HashMap<Integer,Bitmap> dictDoctorImage=new HashMap<Integer,Bitmap>();

    public MessageListLoadView(Activity activ, Context ctx, LinearLayout layout){
        this.mActivity=activ;
        this.ctx=ctx;
        this.mainlayout=layout;
        Log.i("MessageListLoadView","Construct");
    }
    MessageLoader loader;
    public void LoadList(){
         loader=new MessageLoader(this.ctx);
        loader.setCallBack(this);
        loader.setIsCircle(true);
        loader.setCircleSecond(3);
        loader.start();
        Log.i("MessageListLoadView", "LoadList");
    }
    public void UnloadList(){

        loader.setIsCircle(false);
        loader.interrupt();
    }
    int count;
    private Handler onloadMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            try {
                count++;
                if(inOnload==false) {
                    OnloadMessage();
                }
                //Toast.makeText(ctx,"count+"+String.valueOf(count),Toast.LENGTH_SHORT).show();
                Log.i("runcount", "count+" + String.valueOf(count));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
    boolean inOnload=false;
    private void OnloadMessage() {
        inOnload=true;
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
                    LinearLayout line =ToolsUtil.GenPLine(this.ctx);
                    mainlayout.addView(line);
                }
                catch (Exception ex)
                {

                    Log.i("ERROR",ex.toString());
                }
                lstLayout.add(sublayout);
            }

        }
        inOnload=false;
    }




    public ImageView getPhotoView(MessageObj obj,DoctorObj doctor)  {
        ImageView img=new ImageView(this.ctx);
        //img.setBackgroundColor(Color.parseColor("#ccaacc"));
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setAdjustViewBounds(true);
        img.setBackgroundColor(Color.parseColor("#aaeecc"));
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.15f,true);
        img.setLayoutParams(param);
       // param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);

        String strurl= StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto();
        Log.i("doctor_photo", strurl);

        Bitmap bitmap=null;
        if(dictDoctorImage.containsKey(doctor.getId())){
            bitmap=dictDoctorImage.get(doctor.getId());
        }else {
             bitmap=UrlImageLoader.GetBitmap(this.ctx, strurl);
            dictDoctorImage.put(doctor.getId(),bitmap);
        }
        img.setImageBitmap(bitmap);
        //imgLoad.start();
        //imgLoad.LoadImage();
       /* Drawable drawable = img.getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            img.setImageBitmap(toOvalBitmap(bitmap, 1f));
        }*/

        return img;
    }

    public LinearLayout LoadMessageListData(MessageObj obj){

        DoctorDao doctorDao=new DoctorDao(this.ctx);
        DoctorObj doctor=(DoctorObj)doctorDao.getObj(obj.getDoctor_id());


        PercentLinearLayout layout=new PercentLinearLayout(ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        layout.setPadding(20,20,20,20);



        layout.setBackgroundColor(Color.parseColor("#ffffff"));
        //String backgroundcolor=getBackgroundColor(i);
        //layout.setBackgroundColor(Color.parseColor(backgroundcolor));
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imgPhoto=getPhotoView(obj,doctor);
        layout.addView(imgPhoto);

        LinearLayout infolayout=getInfoLayout(obj,doctor);
        layout.addView(infolayout);
        //toplayout.addView(layout);
        layout.setTag(obj);
        layout.setOnClickListener(this);
        return layout;

        //this.mainlayout.addView(layout);

    }





    public LinearLayout getInfoLayout(MessageObj message,DoctorObj doctor){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        //layout.setBackgroundColor(Color.parseColor("#ccaaee"));
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.03f,true);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.VERTICAL);


        TextView txtContent=new MyTextView(this.ctx);
        //txtContent.setBackgroundColor(Color.parseColor("#cceedd"));
        //txtContent.setTextColor(Color.BLACK);
        PercentLinearLayout.LayoutParams upvoteparam= ToolsUtil.getLayoutParamHeightWrap();
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
            if(arrMessage[0].equals("C")){
                strContent="我";
            }else {
                strContent="医生";
            }
            if(arrMessage[1].equals(StaticVar.DOCType)) {
                strContent += "发送了一个文件";
            }
            else if(arrMessage[1].equals(StaticVar.IMGType))
            {
                strContent += "贴了一张图片";
            }
            else
            {
                strContent+="说"+arrMessage[2];
            }
        }
        txtContent.setText(strContent);
        txtContent.setTextSize(14);
        txtContent.setTextColor(Color.parseColor("#909090"));
         PercentLinearLayout tipsLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams buttonparam=ToolsUtil.getLayoutParamHeightWrap();

        tipsLayout.setLayoutParams(buttonparam);
        tipsLayout.setOrientation(LinearLayout.HORIZONTAL);



        //设置聊天对象名
        PercentLinearLayout.LayoutParams nameparam=ToolsUtil.getLayoutParamHeightWrap();
        nameparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,true);
        //  nameparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
        nameparam.gravity=Gravity.LEFT;
        TextView txtName = new MyTextView(this.ctx);

        txtName.setTextSize(15);
        txtName.setTextColor(Color.parseColor("#909090"));
        txtName.setGravity(Gravity.LEFT);
        txtName.setLayoutParams(nameparam);
        txtName.setText(doctor.getName());
        TextPaint tp= txtName.getPaint();
        tp.setFakeBoldText(true);
        Log.i("doctor name", doctor.getName());
        //设置最后一次聊天时间
        TextView lastchatTime=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams lastchatparam=ToolsUtil.getLayoutParamHeightWrap();
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
        if(message.getSendside().equals("D")){
            lastchatTime.setTextColor(Color.RED);
        }

        Log.i("video_chatName", message.getCreated_time());
        tipsLayout.addView(txtName);
        tipsLayout.addView(lastchatTime);

        layout.addView(tipsLayout);
        layout.addView(txtContent);

        return  layout;
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

        //MessageDao dao=new MessageDao(this.ctx);

        //ArrayList<AbstractObj> lstObj=dao.getMessageList(StaticVar.Doctor.getId());
        lstMessages.clear();
        for(AbstractObj obj:lstObjs) {
            lstMessages.add((MessageObj)obj);
        }

        if(lstMessages.size()>0) {
            onloadMessageHandler.sendEmptyMessage(0);
        }
    }
}
