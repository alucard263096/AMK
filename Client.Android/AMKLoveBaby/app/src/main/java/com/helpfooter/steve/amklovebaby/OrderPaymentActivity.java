package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
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

import com.helpfooter.steve.amklovebaby.Common.AlipayMgr;
import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.OrderLoader;
import com.helpfooter.steve.amklovebaby.Loader.PaymentLoader;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


public class OrderPaymentActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack{
    int order_id=0;
    OrderObj order;
    ResultObj res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);
        if(MemberMgr.CheckIsLogin(this)) {
            ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
            InitData();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(StaticVar.Member==null){
            this.finish();
            return;
        }
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        InitData();
    }

    private void InitUI() {

        Button btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setBackgroundResource(R.color.myblue);

        ((TextView)findViewById(R.id.txtPrice)).setText(String.valueOf(order.getPrice()) + "元");


    }

    private void InitData() {
        Intent intent = getIntent();
        order_id = intent.getIntExtra("Id", 0);

        OrderLoader loader=new OrderLoader(this,order_id, StaticVar.Member.getId());
        loader.setCallBack(this);
        loader.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnSubmit:
                AlipayMgr mgr=new AlipayMgr(this,order);
                mgr.pay(v);
                return;
        }
    }



    private android.os.Handler uiInitHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(order==null){
                Toast.makeText(OrderPaymentActivity.this, "订单加载失败，请检查网络", Toast.LENGTH_LONG).show();
            }else {
                InitUI();
            }
        }
    };

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
            if (lstObjs.size() > 0) {
                order = (OrderObj) lstObjs.get(0);
            }
            uiInitHandler.sendEmptyMessage(0);
    }
}
