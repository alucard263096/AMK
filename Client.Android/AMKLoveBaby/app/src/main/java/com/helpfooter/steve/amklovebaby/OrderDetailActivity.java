package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.CustomControlView.OrderDetailLoadView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.OrderLoader;
import com.helpfooter.steve.amklovebaby.Loader.PaymentLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;


public class OrderDetailActivity extends MyActivity implements IWebLoaderCallBack,View.OnClickListener {

    OrderObj order=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        InitData();

    }

    boolean hasload=false;

    private void InitData() {
        Intent intent = getIntent();
        int order_id = intent.getIntExtra("Id", 0);
        if(hasload==false) {
            OrderLoader loader = new OrderLoader(this, order_id, StaticVar.Member.getId());
            loader.setCallBack(this);
            loader.start();
            hasload=true;
        }
    }

    private void InitUI() {

        order.LoadDoctorObj(this);

        ((TextView)findViewById(R.id.title)).getPaint().setFakeBoldText(true);

        ImageView imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.height = 200;
        param.width = 200;
        param.leftMargin = 20;
        param.topMargin = 20;
        param.rightMargin = 10;
        param.leftMargin = 10;
        imgPhoto.setLayoutParams(param);
        String url = StaticVar.ImageFolderURL + "doctor/" + order.getDoctor().getPhoto();
        UrlImageLoader urlImageLoader = new UrlImageLoader(imgPhoto, url);
        urlImageLoader.start();

        //Bitmap bitmap=UrlImageLoader.GetBitmap(url);
        //imgPhoto.setImageBitmap(bitmap);

        ((TextView) findViewById(R.id.txtActName)).setText(order.getActName());
        ((TextView) findViewById(R.id.txtPrice)).setText(order.OrderPrice());
        if (order.getAct().equals("VC")) {
            ((TextView) findViewById(R.id.txtBookingTime)).setText(order.OrderBookingTime());
        } else {
            ((TextView) findViewById(R.id.txtBookingTime)).setVisibility(View.GONE);
        }
        ((TextView) findViewById(R.id.txtStatusName)).setText(order.getStatusName());

        ((TextView) findViewById(R.id.txtOrderNo)).setText(order.getOrder_no());
        ((TextView) findViewById(R.id.txtCreatedTime)).setText(order.getCreated_time());


        ((TextView) findViewById(R.id.txtMemberName)).setText(order.getName());
        ((TextView) findViewById(R.id.txtMemberMobile)).setText(order.getMobile());
        ((TextView) findViewById(R.id.txtMemberAge)).setText(order.getAge());
        ((TextView) findViewById(R.id.txtMemberSex)).setText(order.getSexName());
        ((TextView) findViewById(R.id.txtMemberDescription)).setText(order.getDescription());

        OrderDetailLoadView orderDetailLoadView = new OrderDetailLoadView(this, order);
        orderDetailLoadView.AddSetOrderNext();
    }
    private android.os.Handler uiInitHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(order==null){
                Toast.makeText(OrderDetailActivity.this, "订单加载失败，请检查网络", Toast.LENGTH_LONG).show();
            }else {
                InitUI();
            }
        }
    };

    String action="LOADORDER";
    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(action.equals("LOADORDER")) {
            if (lstObjs.size() > 0) {
                order = (OrderObj) lstObjs.get(0);
            }
            uiInitHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnSubmit:
                action="PAYMENT";
//                PaymentLoader loader=new PaymentLoader(this,order_id, StaticVar.Member.getId(),"ALIPAY");
//                loader.setCallBack(this);
//                loader.start();
                return;
        }
    }
    public void SetCurrentActivity(){
        StaticVar.CurrentActivity=null;
    }

    public boolean PopupNotice(){
        return false;
    }
}
