package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.CustomControlView.OrderListLoadView;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class OrderListActivity extends Activity implements View.OnClickListener {
    OrderListLoadView orderListLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        InitData();
        InitUI();
    }

    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);

        ((TextView) findViewById(R.id.btnShowFinished)).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnShowOrdered)).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnShowWaitpayment)).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnShowAll)).setOnClickListener(this);

        orderListLoadView=new OrderListLoadView(this,(PercentLinearLayout)findViewById(R.id.layoutOrderList),(ImageView)findViewById(R.id.imgNoOrder),(TextView)findViewById(R.id.txtNoOrder),"P");
        orderListLoadView.LoadList();


    }


    private void InitData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnShowFinished:
            case R.id.btnShowOrdered:
            case R.id.btnShowWaitpayment:
            case R.id.btnShowAll:
                SetOnFilterOnSelect(v);
                return;
        }
    }

    private void SetOnFilterOnSelect(View v) {
        ((LinearLayout)findViewById(R.id.udShowAll)).setVisibility(View.INVISIBLE);
        ((LinearLayout)findViewById(R.id.udShowFinished)).setVisibility(View.INVISIBLE);
        ((LinearLayout)findViewById(R.id.udShowWaitpayment)).setVisibility(View.INVISIBLE);
        ((LinearLayout)findViewById(R.id.udShowOrdered)).setVisibility(View.INVISIBLE);


        switch (v.getId()){
            case R.id.btnShowOrdered:
                ((LinearLayout)findViewById(R.id.udShowOrdered)).setVisibility(View.VISIBLE);
                break;
            case R.id.btnShowFinished:
                ((LinearLayout)findViewById(R.id.udShowFinished)).setVisibility(View.VISIBLE);
                break;
            case R.id.btnShowWaitpayment:
                ((LinearLayout)findViewById(R.id.udShowWaitpayment)).setVisibility(View.VISIBLE);
                break;
            case R.id.btnShowAll:
                ((LinearLayout)findViewById(R.id.udShowAll)).setVisibility(View.VISIBLE);
                break;
        }
        //PercentLinearLayout.LayoutParams positionlayout=(PercentLinearLayout.LayoutParams)((LinearLayout)findViewById(R.id.underScroll)).getLayoutParams();
        //positionlayout.mPercentLayoutInfo.leftMarginPercent.percent=position;
        orderListLoadView.Filter(String.valueOf(v.getTag()));
    }


}
