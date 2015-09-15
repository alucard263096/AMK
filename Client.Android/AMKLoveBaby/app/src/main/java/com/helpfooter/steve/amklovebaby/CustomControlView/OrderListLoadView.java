package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scai on 2015/9/15.
 */
public class OrderListLoadView  implements View.OnClickListener,IWebLoaderCallBack {

    Context ctx;
    PercentLinearLayout layout;
    ArrayList<OrderObj> lstOrder;
    String filterstatus="";

    ArrayList<LinearLayout> lstLayout=new ArrayList<LinearLayout>();

    public OrderListLoadView(Context ctx,PercentLinearLayout layout,String status){
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
        //PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParam();
        //param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,false);
        //param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,false);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        //mainLayout.setLayoutParams(param);
        mainLayout.setBackgroundColor(Color.BLUE);

        PercentLinearLayout titleLayout=new PercentLinearLayout(this.ctx);
        //PercentLinearLayout.LayoutParams titleparam= ToolsUtil.getLayoutParam();
        //titleLayout.setLayoutParams(titleparam);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);

        MyTextView txtDoctorName=new MyTextView(this.ctx);
        txtDoctorName.setText(doctor.getName());
        TextPaint tp= txtDoctorName.getPaint();
        tp.setFakeBoldText(true);


        MyTextView txtAct = new MyTextView(this.ctx);
        txtAct.setText("/视频咨询");

        MyTextView txtOrderNo = new MyTextView(this.ctx);
        txtOrderNo.setGravity(Gravity.RIGHT);
        txtOrderNo.setText("订单编号:" + obj.getOrder_no());

        titleLayout.addView(txtDoctorName);
        titleLayout.addView(txtAct);
        titleLayout.addView(txtOrderNo);



        PercentLinearLayout detailLayout=new PercentLinearLayout(this.ctx);
        //PercentLinearLayout.LayoutParams detailparam= ToolsUtil.getLayoutParam();
        //detailLayout.setLayoutParams(detailparam);
        detailLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView doctorPhoto=new ImageView(this.ctx);
        ///PercentLinearLayout.LayoutParams doctorPhotoparam= ToolsUtil.getLayoutParam();
        //doctorPhotoparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f,true);
        detailLayout.addView(doctorPhoto);
        UrlImageLoader imageLoader=new UrlImageLoader(doctorPhoto, StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());
        imageLoader.start();

        PercentLinearLayout dcLayout=new PercentLinearLayout(this.ctx);
        //PercentLinearLayout.LayoutParams dcparam= ToolsUtil.getLayoutParam();
        //dcLayout.setLayoutParams(dcparam);
        dcLayout.setOrientation(LinearLayout.VERTICAL);
        MyTextView txtOrderTime=new MyTextView(this.ctx);
        txtOrderTime.setText("预约通话时间:" + obj.getOrder_date() + " " + obj.getOrder_time());
        dcLayout.addView(txtOrderTime);

        MyTextView txtPrice=new MyTextView(this.ctx);
        txtPrice.setText(String.valueOf(obj.getPrice())+"元/20分钟");
        dcLayout.addView(txtPrice);

        MyTextView txtOrderCreatedTime=new MyTextView(this.ctx);
        txtOrderCreatedTime.setText("订单时间:"+obj.getCreated_time());
        dcLayout.addView(txtOrderCreatedTime);

        detailLayout.addView(dcLayout);


        mainLayout.addView(titleLayout);
        mainLayout.addView(genPLine());
        mainLayout.addView(detailLayout);



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
}
