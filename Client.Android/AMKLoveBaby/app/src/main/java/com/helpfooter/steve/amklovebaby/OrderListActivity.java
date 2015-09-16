package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.OrderListLoadView;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;

import java.util.ArrayList;
import java.util.List;

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
        ((TextView) findViewById(R.id.btnShowOrdered)).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnShowAll)).setOnClickListener(this);

        orderListLoadView=new OrderListLoadView(this,(PercentLinearLayout)findViewById(R.id.layoutOrderList),"P");
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
        }
    }
}
