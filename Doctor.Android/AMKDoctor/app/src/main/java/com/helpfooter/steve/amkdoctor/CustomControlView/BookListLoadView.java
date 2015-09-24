package com.helpfooter.steve.amkdoctor.CustomControlView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.helpfooter.steve.amkdoctor.DAO.BookerDao;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.BookerLoader;
import com.helpfooter.steve.amkdoctor.R;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;
import com.helpfooter.steve.amkdoctor.VideoChatActivity;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by scai on 2015/9/1.
 */
public class BookListLoadView implements View.OnClickListener,IWebLoaderCallBack {
    public LinearLayout mainlayout;
    public ArrayList<BookerObj> lstBooker=new ArrayList<BookerObj>() ;
    public Context ctx;
    public boolean IsFristRun=true;
    public Activity mActivity;
    ArrayList<LinearLayout> lstLayout=new ArrayList<LinearLayout>();
    public  BookListLoadView(Activity activ,Context ctx,LinearLayout layout){
        this.mActivity=activ;
        this.ctx=ctx;
        this.mainlayout=layout;

    }

    public void LoadList(){
        BookerLoader loader=new BookerLoader(this.ctx);
        loader.setCallBack(this);
        loader.start();
    }

    private Handler onloadAllHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            OnloadBooker();
        }
    };
    private void OnloadBooker() {

        if(this.mainlayout.getChildCount()>0) {
            this.mainlayout.removeViews(0, this.mainlayout.getChildCount() - 1);
        }
        //mainlayout.removeAllViewsInLayout();
        for(BookerObj obj:lstBooker){

            LinearLayout sublayout=null;
            //int i;
            //for(LinearLayout oldlayout : lstLayout) {
              //  BookerObj oldObj = (BookerObj)oldlayout.getTag();
                //如果订单编号相同，并且时间相同，continue
                //if(oldObj.getBookno().equals(obj.getBookno()) && oldObj.getOrderdate().eq)
            //}
            if(obj.getStatus().equals("P")){
                sublayout=LoadBookerListData(obj);
            }
            if(sublayout!=null){
                sublayout.setTag(obj);
                mainlayout.addView(sublayout);
                lstLayout.add(sublayout);
            }

        }

    }


    public LinearLayout LoadBookerListData(BookerObj obj){
        PercentLinearLayout toplayout=new PercentLinearLayout(ctx);
        PercentLinearLayout.LayoutParams topparam=ToolsUtil.getLayoutParam();
        topparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.19f,false);
        topparam.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.03f,false);
        topparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
        topparam.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);


        toplayout.setBackgroundResource(R.drawable.cardlinearlayout);
        // layout.setBackgroundColor(Color.parseColor("#ffffff"));
        //String backgroundcolor=getBackgroundColor(i);
        //layout.setBackgroundColor(Color.parseColor(backgroundcolor));
        toplayout.setLayoutParams(topparam);
        toplayout.setOrientation(LinearLayout.HORIZONTAL);

            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
            param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.98f,false);
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
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
        return toplayout;
            //layout.setTag(obj);
            //layout.setOnClickListener(this);
            //this.mainlayout.addView(layout);

    }
    public ImageView getPhotoView(BookerObj obj){
        ImageView img=new ImageView(this.ctx);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.33f,true);
        String url= "http://www.myhkdoc.com/AMK/FilesServer/doctor/15083123000b.png";
        Log.i("doctor_photo", url);
        UrlImageLoader imgLoad=new UrlImageLoader(img,url);
        imgLoad.start();

        img.setLayoutParams(param);

        return img;
    }

    public LinearLayout getInfoLayout(BookerObj booker){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.62f,true);
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.VERTICAL);


        TextView txtOrderTime=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams titleparam=ToolsUtil.getLayoutParam();
        titleparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        titleparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.13f,true);
        txtOrderTime.setTextSize(15);
        //txtTitle.setGravity(Gravity.CENTER_VERTICAL);
        txtOrderTime.setLayoutParams(titleparam);
        txtOrderTime.setText("预约时间： "+booker.getOrderdate()+" "+booker.getOrdertime());
        TextPaint tp= txtOrderTime.getPaint();
        tp.setFakeBoldText(true);
        Log.i("video_chatTime", booker.getOrderdate()+" "+booker.getOrdertime());

        TextView txtName=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams custnameparam=ToolsUtil.getLayoutParam();
        custnameparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.2f,true);
        txtName.setGravity(Gravity.CENTER_VERTICAL);
        txtName.setLayoutParams(custnameparam);
        txtName.setTextColor(Color.GRAY);
        txtName.setText("名称： "+booker.getCustname());
        txtName.setTextSize(15);
        TextPaint tnp= txtName.getPaint();
        tnp.setFakeBoldText(true);
        Log.i("video_chatName", booker.getCustname());


        PercentLinearLayout tipsLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams buttonparam=ToolsUtil.getLayoutParam();
        buttonparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        buttonparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.17f,true);

        tipsLayout.setLayoutParams(buttonparam);
        tipsLayout.setOrientation(LinearLayout.HORIZONTAL);



        TextView txtBeginChart=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams upvoteparam= ToolsUtil.getLayoutParam();
        upvoteparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,true);
        upvoteparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.65f,true);
        txtBeginChart.setLayoutParams(upvoteparam);
        txtBeginChart.setClickable(true);


        txtBeginChart.setBackgroundColor(Color.parseColor("#37A4D4"));
        txtBeginChart.setTextColor(Color.parseColor("#ffffff"));
        txtBeginChart.setGravity(Gravity.CENTER);
        txtBeginChart.setText("开始视频");
        txtBeginChart.setTextSize(15);
        txtBeginChart.setTag(booker);
        txtBeginChart.setOnClickListener(this);
        tipsLayout.addView(txtBeginChart);

        layout.addView(txtOrderTime);
        layout.addView(txtName);
        layout.addView(tipsLayout);
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
        BookerObj obj=(BookerObj)v.getTag();
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
        Intent intent = new Intent(this.ctx, VideoChatActivity.class);
        intent.putExtra("docId", String.valueOf(obj.getDoctorid()));
        intent.putExtra("custId", String.valueOf(obj.getCustid()));
        intent.putExtra("orderId", String.valueOf(obj.getId()));
        this.ctx.startActivity(intent);

    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        BookerDao dao=new BookerDao(this.ctx);

        ArrayList<AbstractObj> lstObj=dao.getBookerList();

        for(AbstractObj obj:lstObj) {
            lstBooker.add((BookerObj)obj);
        }

        if(lstObj.size()>0) {
            onloadAllHandler.sendEmptyMessage(0);
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
