package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.AlipayMgr;
import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
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


public class OrderPaymentActivity extends MyActivity implements View.OnClickListener,IWebLoaderCallBack{
    int order_id=0;
    OrderObj order;
    ResultObj res;

    TextView txtPaymentType;

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

        txtPaymentType=((TextView)findViewById(R.id.txtPaymentType));
        txtPaymentType.setOnClickListener(this);
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
            case R.id.txtPaymentType:
                Intent intent = new Intent(this, PaymentTypeSelectActivity.class);
                startActivityForResult(intent, 2);
                return;
            case R.id.btnSubmit:
                //AlipayMgr mgr=new AlipayMgr(this,order);
                //mgr.pay(v);

                String payment_type=(String)txtPaymentType.getTag();
                if(payment_type.length()<1){
                    Toast.makeText(this,"请选择一种支付方式",Toast.LENGTH_LONG).show();
                    return;
                }
                if(payment_type.equals("TEST")) {
                    action = "PAYMENT";
                    PaymentLoader loader = new PaymentLoader(this, order_id, StaticVar.Member.getId(), "ALIPAY");
                    loader.setCallBack(this);
                    loader.start();
                }else {
                    Toast.makeText(this,"此支付方式暂未开通",Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=-1){
            return;
        }
        String retstr=data.getStringExtra("return");
        String returnName=data.getStringExtra("returnName");
        if(requestCode == 2){
            txtPaymentType.setText(returnName);
            txtPaymentType.setTag(retstr);
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

    private android.os.Handler paymentHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(res==null){
                Toast.makeText(OrderPaymentActivity.this, "订单支付失败", Toast.LENGTH_LONG).show();
            }else {
                switch (res.getId()){
                    case -103:
                        Toast.makeText(OrderPaymentActivity.this, "订单已经支付", Toast.LENGTH_LONG).show();
                        return;
                    case 0:
                        Intent intent = new Intent(OrderPaymentActivity.this, PaymentSuccActivity.class);
                        intent.putExtra("Id", order_id);
                        startActivity(intent);
                        return;
                    default:
                        Toast.makeText(OrderPaymentActivity.this, "订单支付失败", Toast.LENGTH_LONG).show();
                        return;
                }
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
        }else if(action.equals("PAYMENT")){
            if (lstObjs.size() > 0) {
                res = (ResultObj) lstObjs.get(0);
            }
            paymentHandler.sendEmptyMessage(0);
        }
    }

    public boolean PopupNotice(){
        return false;
    }
}
