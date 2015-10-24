package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;


public class PaymentSuccActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_succ);


        ((Button) findViewById(R.id.btnBack)).setOnClickListener(this);

        ((WebView) findViewById(R.id.txtContext)).loadUrl(StaticVar.GeneralTextUrl + "paymentsuccess");
        ((WebView) findViewById(R.id.txtContext)).setBackgroundColor(0);


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
        }
    }
}
