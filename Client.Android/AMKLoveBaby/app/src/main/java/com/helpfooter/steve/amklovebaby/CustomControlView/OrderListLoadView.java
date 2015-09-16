package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.OrderListLoader;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scai on 2015/9/15.
 */
public class OrderListLoadView  implements View.OnClickListener,IWebLoaderCallBack {

    Activity ctx;
    PercentLinearLayout layout;
    ArrayList<OrderObj> lstOrder;
    String filterstatus="";

    ArrayList<LinearLayout> lstLayout=new ArrayList<LinearLayout>();

    public OrderListLoadView(Activity ctx,PercentLinearLayout layout,String status){
        this.ctx=ctx;
        this.layout=layout;
        filterstatus=status;
    }

    public void setfilterstatus(String status) {
        this.filterstatus = status;

    }


    public void LoadList(){
        OrderListLoader loader=new OrderListLoader(this.ctx);
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    public void onClick(View v) {

    }

    private Handler onloadAllHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            OnloadOrder();
            Filter(filterstatus);
        }
    };

    private void OnloadOrder() {

        for(OrderObj obj:lstOrder){

            LinearLayout sublayout=null;

            if(obj.getAct().equals("VC")){
                sublayout=gerVideoChatLayout(obj);
            }
            if(sublayout!=null){
                sublayout.setTag(obj);
                layout.addView(sublayout);
                lstLayout.add(sublayout);
            }

        }

    }

    private LinearLayout gerVideoChatLayout(OrderObj obj) {
        DoctorDao doctorDao=new DoctorDao(this.ctx);
        DoctorObj doctor=(DoctorObj)doctorDao.getObj(Integer.valueOf(obj.getTag()));


        PercentLinearLayout mainLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.2f,false);
        param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(param);
        //mainLayout.setBackgroundColor(Color.RED);

        PercentLinearLayout titleLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams titleparam= ToolsUtil.getLayoutParam();
        titleparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,false);
        titleLayout.setLayoutParams(titleparam);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);

        MyTextView txtDoctorName=new MyTextView(this.ctx);
        txtDoctorName.setText(doctor.getName());
        TextPaint tp= txtDoctorName.getPaint();
        tp.setFakeBoldText(true);
        txtDoctorName.setTextSize(14);


        MyTextView txtAct = new MyTextView(this.ctx);
        txtAct.setText("/视频咨询");
        txtAct.setTextSize(14);

        MyTextView txtOrderNo = new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams txtOrderNoparam= ToolsUtil.getLayoutParam();
        txtOrderNo.setLayoutParams(txtOrderNoparam);
        //txtOrderNo.setGravity(Gravity.);
        txtOrderNo.setText("订单编号:" + obj.getOrder_no());

        titleLayout.addView(txtDoctorName);
        titleLayout.addView(txtAct);
        //titleLayout.addView(txtOrderNo);
        titleLayout.setGravity(Gravity.CENTER_VERTICAL);



        PercentLinearLayout detailLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams detailparam= ToolsUtil.getLayoutParam();
        detailparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.6f,false);
        detailparam.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,false);
        detailparam.mPercentLayoutInfo.bottomMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,false);
        detailLayout.setLayoutParams(detailparam);
        detailLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView doctorPhoto=new ImageView(this.ctx);
        //doctorPhoto.setBackgroundColor(Color.YELLOW);
        PercentLinearLayout.LayoutParams doctorPhotoparam= ToolsUtil.getLayoutParam();
        doctorPhotoparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.20f,true);
        doctorPhoto.setLayoutParams(doctorPhotoparam);
        detailLayout.addView(doctorPhoto);
        doctorPhoto.setScaleType(ImageView.ScaleType.FIT_START);
        UrlImageLoader imageLoader=new UrlImageLoader(doctorPhoto, StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());
        imageLoader.start();

        PercentLinearLayout dcLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams dcparam= ToolsUtil.getLayoutParam();
        //dcparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.005f,true);
        dcLayout.setLayoutParams(dcparam);
        dcLayout.setOrientation(LinearLayout.VERTICAL);
        MyTextView txtOrderTime=new MyTextView(this.ctx);
        txtOrderTime.setText("预约通话时间:" + obj.getOrder_date() + " " + obj.getOrder_time());
        txtOrderTime.setTextSize(15);
        dcLayout.addView(txtOrderTime);

        MyTextView txtPrice=new MyTextView(this.ctx);
        txtPrice.setText(String.valueOf(obj.getPrice()) + "元/20分钟");
        txtPrice.setTextSize(14);
        txtPrice.setTextColor(this.ctx.getResources().getColor(R.color.mydeepblue));
        dcLayout.addView(txtPrice);

        MyTextView txtOrderCreatedTime=new MyTextView(this.ctx);
        txtOrderCreatedTime.setText("订单时间:" + obj.getCreated_time());
        txtOrderCreatedTime.setTextColor(Color.GRAY);
        dcLayout.addView(txtOrderCreatedTime);

        detailLayout.addView(dcLayout);


        mainLayout.addView(titleLayout);
        mainLayout.addView(genPLine());
        mainLayout.addView(detailLayout);
        mainLayout.addView(genPLine());



        return mainLayout;
    }

    private LinearLayout genPLine(){

        LinearLayout pLine=new LinearLayout(this.ctx);
        LinearLayout.LayoutParams pLineLayout=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
        pLine.setLayoutParams(pLineLayout);
        pLine.setBackgroundColor(Color.parseColor("#cccccc"));

        return pLine;
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        OrderDao dao=new OrderDao(this.ctx);
        lstOrder=dao.getOrderList();
        onloadAllHandler.sendEmptyMessage(0);
    }

    public void Filter(String filter) {
        boolean haveone=false;

        for(LinearLayout layout:lstLayout){
            OrderObj obj=(OrderObj)layout.getTag();
            PercentLinearLayout.LayoutParams param= (PercentLinearLayout.LayoutParams)layout.getLayoutParams();
            if(filter.isEmpty()||filter.equals(obj.getStatus())){
             layout.setVisibility(View.VISIBLE);
             param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.2f,false);
                haveone=true;
            }else{
                layout.setVisibility(View.INVISIBLE);
                param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0f,false);
            }
        }
        if(haveone==false){
            ((ImageView)this.ctx.findViewById(R.id.imgNoOrder)).setVisibility(View.VISIBLE);
            ((TextView)this.ctx.findViewById(R.id.txtNoOrder)).setVisibility(View.VISIBLE);
        }else {
            ((ImageView)this.ctx.findViewById(R.id.imgNoOrder)).setVisibility(View.INVISIBLE);
            ((TextView)this.ctx.findViewById(R.id.txtNoOrder)).setVisibility(View.INVISIBLE);
        }
        //((ScrollView)this.ctx.findViewById(R.id.scrollView)).setTop(0);
    }
}
