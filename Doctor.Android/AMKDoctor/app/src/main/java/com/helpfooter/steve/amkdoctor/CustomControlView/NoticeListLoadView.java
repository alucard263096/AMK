package com.helpfooter.steve.amkdoctor.CustomControlView;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amkdoctor.ChatActivity;
import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.DAO.DoctorDao;
import com.helpfooter.steve.amkdoctor.DAO.NoticeDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MessageObj;
import com.helpfooter.steve.amkdoctor.DataObjs.NoticeObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.MessageLoader;
import com.helpfooter.steve.amkdoctor.Loader.NoticeLoader;
import com.helpfooter.steve.amkdoctor.R;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;


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
public class NoticeListLoadView implements View.OnClickListener,IWebLoaderCallBack {
    public LinearLayout mainlayout;
    public ArrayList<NoticeObj> lstNotices=new ArrayList<NoticeObj>() ;
    public Context ctx;
    public boolean IsFristRun=true;
    public Activity mActivity;
    ArrayList<LinearLayout> lstLayout=new ArrayList<LinearLayout>();
    public HashMap<Integer,Bitmap> dictDoctorImage=new HashMap<Integer,Bitmap>();

    public NoticeListLoadView(Activity activ, Context ctx, LinearLayout layout){
        this.mActivity=activ;
        this.ctx=ctx;
        this.mainlayout=layout;
        Log.i("NoticeListLoadView","Construct");
    }
    NoticeLoader loader;
    public void LoadList(){
         loader=new NoticeLoader(this.ctx);
        loader.setCallBack(this);
        loader.setIsCircle(false);

        loader.start();
        Log.i("NoticeListLoadView", "LoadList");
    }
    public void UnloadList(){

        loader.setIsCircle(false);
        loader.interrupt();
    }
    int count;
    private Handler onloadNoticeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            try {
                count++;
                if(inOnload==false) {
                    OnloadNotice();
                }
                //Toast.makeText(ctx,"count+"+String.valueOf(count),Toast.LENGTH_SHORT).show();
                Log.i("runcount", "count+" + String.valueOf(count));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
    boolean inOnload=false;
    private void OnloadNotice() {
        inOnload=true;
        if(this.mainlayout.getChildCount()>0) {
            this.mainlayout.removeViews(0, this.mainlayout.getChildCount() - 1);
        }
        //mainlayout.removeAllViewsInLayout();
        for(NoticeObj obj:lstNotices){
            LinearLayout sublayout=null;

            sublayout=LoadNoticeListData(obj);

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




    public ImageView getPhotoView(NoticeObj obj)  {
        ImageView img=new ImageView(this.ctx);
        //img.setBackgroundColor(Color.parseColor("#ccaacc"));
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setAdjustViewBounds(true);
        img.setBackgroundColor(Color.parseColor("#aaeecc"));
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.15f,true);
        img.setLayoutParams(param);
        if(obj.getHaveread()=="N") {
            img.setImageResource(R.drawable.cycle);
        }
        else
        {
            img.setImageResource(R.drawable.mycon);
        }


        return img;
    }

    public LinearLayout LoadNoticeListData(NoticeObj obj){


        PercentLinearLayout layout=new PercentLinearLayout(ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        layout.setPadding(20,20,20,20);



        layout.setBackgroundColor(Color.parseColor("#ffffff"));
        //String backgroundcolor=getBackgroundColor(i);
        //layout.setBackgroundColor(Color.parseColor(backgroundcolor));
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imgPhoto=getPhotoView(obj);
        layout.addView(imgPhoto);

        LinearLayout infolayout=getInfoLayout(obj);
        layout.addView(infolayout);
        //toplayout.addView(layout);
        layout.setTag(obj);
        layout.setOnClickListener(this);
        return layout;

        //this.mainlayout.addView(layout);

    }





    public LinearLayout getInfoLayout(NoticeObj notice){
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
        String strContent = notice.getTitle();
        txtContent.setEllipsize(TextUtils.TruncateAt.END);
        txtContent.setSingleLine();
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
        if(notice.getNotice_type()=="M") {
            txtName.setText("一般");
        }
        else
        {
            txtName.setText("重要");
            txtName.setTextColor(Color.RED);
            TextPaint tp= txtName.getPaint();
            tp.setFakeBoldText(true);
        }


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
            date = dateformat.parse(notice.getPublish_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateformat =   new SimpleDateFormat( " MM月dd日 HH:mm" );

        String strOrderTime = dateformat.format(date);
        lastchatTime.setText(strOrderTime);

        Log.i("video_chatName", notice.getHaveread());
        tipsLayout.addView(txtName);
        tipsLayout.addView(lastchatTime);

        layout.addView(tipsLayout);
        layout.addView(txtContent);

        return  layout;
    }



    @Override
    public void onClick(View v) {
        NoticeObj obj=(NoticeObj)v.getTag();

        Intent intent = new Intent(mActivity, ChatActivity.class);
        intent.putExtra("orderId", String.valueOf(obj.getId()));
        intent.putExtra("tag", String.valueOf(obj.getDoctor_id()));
        mActivity.startActivity(intent);

    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        NoticeDao dao=new NoticeDao(this.ctx);

        ArrayList<AbstractObj> lstObj=dao.getNoticeList();
        lstNotices.clear();
        for(AbstractObj obj:lstObjs) {
            lstNotices.add((NoticeObj)obj);
        }

        if(lstNotices.size()>0) {
            onloadNoticeHandler.sendEmptyMessage(0);
        }
    }
}
