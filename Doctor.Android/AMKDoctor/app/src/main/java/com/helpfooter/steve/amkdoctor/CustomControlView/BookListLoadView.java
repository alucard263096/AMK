package com.helpfooter.steve.amkdoctor.CustomControlView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import com.helpfooter.steve.amkdoctor.MemberInfoActivity;
import com.helpfooter.steve.amkdoctor.R;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;
import com.helpfooter.steve.amkdoctor.VideoChatActivity;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.UUID;

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
            //this.mainlayout.removeViews(0, this.mainlayout.getChildCount() - 1);
            this.mainlayout.removeAllViews();
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

                sublayout=LoadBookerListData(obj);

            if(sublayout!=null){
                sublayout.setTag(obj);
                mainlayout.addView(sublayout);
                lstLayout.add(sublayout);
            }

        }

    }


    public LinearLayout LoadBookerListData(BookerObj obj){
        PercentLinearLayout fullLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams fullLayoutparam= ToolsUtil.getLayoutParamHeightWrap();
        fullLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        fullLayout.setOrientation(LinearLayout.VERTICAL);
        fullLayout.setLayoutParams(fullLayoutparam);




        PercentLinearLayout mainLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.topMargin=20;
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setLayoutParams(param);

        LinearLayout leftLayout=getLeftLayout(obj);
        mainLayout.addView(leftLayout);


        LinearLayout rightLayout=getRightLayout(obj);
        mainLayout.addView(rightLayout);

        fullLayout.addView(mainLayout);
        LinearLayout line=ToolsUtil.GenPLine(ctx);
        fullLayout.addView(line);
        return fullLayout;

    }

    private LinearLayout getRightLayout(BookerObj booker) {
        PercentLinearLayout mainLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.75f,true);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(param);

        TextView txtTitle=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtTitleparam= ToolsUtil.getLayoutParamHeightWrap();
        txtTitle.setLayoutParams(txtTitleparam);
        txtTitle.setTextSize(17);
        txtTitle.setText("名称： " + booker.getCustname());

        mainLayout.addView(txtTitle);


        TextView txtTime=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtTimeparam= ToolsUtil.getLayoutParamHeightWrap();
        txtTime.setLayoutParams(txtTimeparam);
        txtTime.setTextSize(14);
        txtTime.setText("预约时间： " + booker.getOrderdate() + " " + booker.getOrdertime());
        mainLayout.addView(txtTime);


        TextView txtBeginChart=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtBeginChartparam= ToolsUtil.getLayoutParamHeightWrap();
        txtBeginChartparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.4f,true);
        txtBeginChartparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.6f,true);
        txtBeginChart.setLayoutParams(txtBeginChartparam);
        txtBeginChart.setClickable(true);
        txtBeginChart.setBackgroundColor(Color.parseColor("#37A4D4"));
        txtBeginChart.setTextColor(Color.parseColor("#ffffff"));
        txtBeginChart.setGravity(Gravity.CENTER);
        txtBeginChart.setText("开始视频");
        txtBeginChart.setTextSize(15);
        txtBeginChart.setContentDescription("BUTTON");
        txtBeginChart.setTag(booker);
        txtBeginChart.setOnClickListener(this);
        mainLayout.addView(txtBeginChart);


        return mainLayout;
    }

    private LinearLayout getLeftLayout(BookerObj booker) {

        PercentLinearLayout mainLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.21f,true);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(param);

        ImageView img=new ImageView(ctx);
        PercentLinearLayout.LayoutParams imgparam= ToolsUtil.getLayoutParamHeightWrap();
        img.setAdjustViewBounds(true);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setLayoutParams(imgparam);
        img.setContentDescription("IMAGE");
        img.setTag(booker);
        img.setOnClickListener(this);
        String url=StaticVar.ImageFolderURL+"member/"+booker.getMember_photo();
        UrlImageLoader imageLoader=new UrlImageLoader(img,url);
        imageLoader.start();

        TextView txt=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtparam= ToolsUtil.getLayoutParamHeightWrap();
        txt.setLayoutParams(txtparam);
        txt.setGravity(Gravity.CENTER);
        txt.setText("点击查看");

        mainLayout.addView(img);
        mainLayout.addView(txt);
        return mainLayout;
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
        if(v.getContentDescription().equals("IMAGE")) {
            Intent intent = new Intent(this.ctx, MemberInfoActivity.class);
            intent.putExtra("id", String.valueOf(obj.getCustid()));
            intent.putExtra("age", String.valueOf(obj.getAge()));
            intent.putExtra("sex", String.valueOf(obj.getSex()));
            this.ctx.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this.ctx, VideoChatActivity.class);
            intent.putExtra("docId", String.valueOf(obj.getDoctorid()));
            intent.putExtra("custId", String.valueOf(obj.getCustid()));
            intent.putExtra("orderId", String.valueOf(obj.getId()));
            this.ctx.startActivity(intent);
        }



    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        BookerDao dao=new BookerDao(this.ctx);

        ArrayList<AbstractObj> lstObj=dao.getBookerList();
        lstBooker.clear();
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
                            sleep(30000);
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
