package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;


public class PaymentSuccActivity extends MyActivity implements View.OnClickListener {

    OrderObj order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_succ);

        InitData();
        ((Button) findViewById(R.id.btnBack)).setOnClickListener(this);

        ((WebView) findViewById(R.id.txtContext)).loadUrl(StaticVar.GeneralTextUrl + "paymentsuccess");
        ((WebView) findViewById(R.id.txtContext)).setBackgroundColor(0);

        ((TextView)findViewById(R.id.txtAct)).setText(order.getActName());
        ((TextView)findViewById(R.id.txtDescription)).setText(order.getActPaymentSuccess());

        if(order.getAct().equals("CC")){
            ((Button)findViewById(R.id.btnStart)).setOnClickListener(this);
        }else {
            ((Button)findViewById(R.id.btnStart)).setVisibility(View.GONE);
        }


    }

    private void InitData() {

        OrderDao orderDao=new OrderDao(this);

        Intent intent = getIntent();
        int order_id = intent.getIntExtra("Id", 0);
        if(order_id==0){
            Uri uridata = this.getIntent().getData();
            String order_no = uridata.getQueryParameter("order_no");
            order_id=orderDao.getOrderId(order_no);
        }
        order=(OrderObj)orderDao.getObj(order_id);
        orderDao.setPaymentSuccess(order.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                startActivity(intent);
                this.finish();
            case R.id.btnStart:
                if(order.getAct().equals("CC")){
                    Intent intenta = new Intent();
                    intenta.setClass(this, ChatActivity.class);
                    intenta.putExtra("orderId", order.getId());
                    intenta.putExtra("tag", order.getTag());
                    startActivity(intenta);
                }
                return;
        }
    }

    public boolean PopupNotice(){
        return false;
    }
}
