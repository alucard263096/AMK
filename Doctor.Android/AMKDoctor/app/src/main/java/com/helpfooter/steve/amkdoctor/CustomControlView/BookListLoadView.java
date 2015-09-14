package com.helpfooter.steve.amkdoctor.CustomControlView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.helpfooter.steve.amkdoctor.DAO.BookerDao;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.DAO.DoctorDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.R;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by scai on 2015/9/1.
 */
public class BookListLoadView implements View.OnClickListener {
    public LinearLayout mainlayout;
    public ArrayList<BookerObj> lstBooker;
    public Context ctx;
    public  BookListLoadView(Context ctx,LinearLayout layout){
        this.ctx=ctx;
        this.mainlayout=layout;
        BookerDao dao=new BookerDao(this.ctx);
        ArrayList<AbstractObj> lst=dao.getBookerList();
        lstBooker=new ArrayList<BookerObj>();
        for(AbstractObj obj:lst){
            lstBooker.add((BookerObj)obj);
        }
    }

    public void LoadDoctorListData(){
        int i=1;
        for(BookerObj obj:lstBooker){
            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
            param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.16f,false);
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,false);
            param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,false);
            //String backgroundcolor=getBackgroundColor(i);
            //layout.setBackgroundColor(Color.parseColor(backgroundcolor));
            layout.setLayoutParams(param);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imgPhoto=getPhotoView(obj);
            layout.addView(imgPhoto);
            LinearLayout infolayout=getInfoLayout(obj);
            layout.addView(infolayout);

            layout.setTag(obj);
            layout.setOnClickListener(this);
            this.mainlayout.addView(layout);
            i++;
        }
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
        titleparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.62f,true);
        titleparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.13f,true);
        txtOrderTime.setTextSize(15);
        //txtTitle.setGravity(Gravity.CENTER_VERTICAL);
        txtOrderTime.setLayoutParams(titleparam);
        txtOrderTime.setText(booker.getOrderdate()+" "+booker.getOrdertime());
        TextPaint tp= txtOrderTime.getPaint();
        tp.setFakeBoldText(true);
        Log.i("video_chatTime", booker.getOrderdate()+" "+booker.getOrdertime());

        TextView txtName=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams custnameparam=ToolsUtil.getLayoutParam();
        custnameparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.2f,true);
        txtName.setGravity(Gravity.CENTER_VERTICAL);
        txtName.setLayoutParams(custnameparam);
        txtName.setTextColor(Color.GRAY);
        txtName.setText(booker.getCustname());
        txtName.setTextSize(15);
        TextPaint tnp= txtName.getPaint();
        tnp.setFakeBoldText(true);
        Log.i("video_chatName", booker.getCustname());


        PercentLinearLayout tipsLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams buttonparam=ToolsUtil.getLayoutParam();
        buttonparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.67f,true);
        buttonparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.17f,true);
        tipsLayout.setLayoutParams(buttonparam);
        tipsLayout.setOrientation(LinearLayout.HORIZONTAL);



        TextView txtBeginChart=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams upvoteparam= ToolsUtil.getLayoutParam();
        upvoteparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
        txtBeginChart.setLayoutParams(upvoteparam);
        txtBeginChart.setClickable(true);


        txtBeginChart.setBackgroundColor(Color.parseColor("#37A4D4"));
        txtBeginChart.setTextColor(Color.parseColor("#ffffff"));
        txtBeginChart.setGravity(Gravity.RIGHT);
        txtBeginChart.setText("开始视频");
        txtBeginChart.setTextSize(15);
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
            default:
                return "#37A4D4";
        }
    }

    @Override
    public void onClick(View v) {

       // DoctorObj obj=(DoctorObj)v.getTag();
       // Intent intent = new Intent(this.ctx, DoctorDetailActivity.class);
        //intent.putExtra("Id", obj.getId());
       // this.ctx.startActivity(intent);
    }
}
