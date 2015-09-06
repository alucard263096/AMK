package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorktimeObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.WorktimeLoader;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Steve on 2015/9/6.
 */
public class WorkTimeTableView implements IWebLoaderCallBack,View.OnClickListener {

    int doctor_id;
    PercentLinearLayout layout;
    Context ctx;
    String date;

    ArrayList<TextView> lstText=new ArrayList<TextView>();

    public WorkTimeTableView(Context ctx,int doctor_id,PercentLinearLayout layout){
        this.ctx=ctx;
        this.doctor_id=doctor_id;
        this.layout=layout;
    }

    public void LoadData(String date){
        this.date=date;
        this.order_time="";
        removeAllHandler.sendEmptyMessage(0);
        WorktimeLoader loader=new WorktimeLoader(ctx,doctor_id,date);
        loader.setCallBack(this);
        loader.start();
    }

    private Handler removeAllHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            layout.removeAllViews();
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            layout.removeAllViews();
            if(lstObjs.size()==0){
                TextView txtNoTime=new MyTextView(ctx);
                txtNoTime.setText("您选择的日期没有预约时间可选");
                PercentLinearLayout.LayoutParams txtparam= ToolsUtil.getLayoutParam();
                txtparam.getPercentLayoutInfo().topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.03f,true);
                txtNoTime.setLayoutParams(txtparam);

                layout.addView(txtNoTime);
                return;
            }
            int row_number=4;

            PercentLinearLayout rowLayout=null;
            int count=row_number;
            for(AbstractObj abobj:lstObjs){

                int yushu=count%row_number;
                boolean istrue=yushu==0;
                if(istrue){
                    rowLayout=newARowLayout();
                }

                WorktimeObj obj=(WorktimeObj)abobj;
                TextView txtTime=new MyTextView(ctx);
                PercentLinearLayout.LayoutParams txtparam= ToolsUtil.getLayoutParam();
                txtparam.getPercentLayoutInfo().widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.23f,true);
                txtparam.getPercentLayoutInfo().leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,true);
                txtparam.getPercentLayoutInfo().rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,true);
                txtTime.setText(obj.getTime());
                txtTime.setTextColor(Color.WHITE);
                txtTime.setGravity(Gravity.CENTER);

                String fulldatetime=date+" "+obj.getTime();
                if(obj.getUsed().equals("Y")||!isAfterHours(fulldatetime,2)){
                    txtTime.setBackgroundColor(Color.GRAY);
                }else {
                    txtTime.setBackgroundResource(R.color.myblue);
                    txtTime.setTag(obj.getTime());
                    lstText.add(txtTime);
                    txtTime.setOnClickListener(WorkTimeTableView.this);
                }


                txtTime.setLayoutParams(txtparam);

                rowLayout.addView(txtTime);
                if(yushu==(row_number-1)){
                    layout.addView(rowLayout);
                }
                count++;
            }
            if(count%row_number!=0){
                layout.addView(rowLayout);
            }

            Log.i("Worktime_layoutcount", String.valueOf(layout.getChildCount()));
        }

    };

    private boolean isAfterHours(String fulldatetime, int hours)  {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dd = formatter.parse(fulldatetime);
            Date curdate=new Date(System.currentTimeMillis());
            long xiangcha=(dd.getTime()-curdate.getTime());
            return  xiangcha>hours*3600*1000;

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    ArrayList<AbstractObj> lstObjs;

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        this.lstObjs=lstObjs;
        mHandler.sendEmptyMessage(0);
    }

    public PercentLinearLayout newARowLayout(){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParam();
        param.getPercentLayoutInfo().heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.13f,true);
        param.getPercentLayoutInfo().topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        return layout;
    }

    String order_time;
    @Override
    public void onClick(View v) {
        for(TextView tv:lstText){
            tv.setBackgroundResource(R.color.myblue);
        }
        TextView tv=(TextView)v;
        tv.setBackgroundResource(R.color.myyello);
        order_time=String.valueOf(tv.getTag());
    }

    public String getOrderTime(){
        return  order_time;
    }

    public String getOrderDate(){
        return  this.date;
    }

    public int getDoctorId(){
        return  this.doctor_id;
    }



}
