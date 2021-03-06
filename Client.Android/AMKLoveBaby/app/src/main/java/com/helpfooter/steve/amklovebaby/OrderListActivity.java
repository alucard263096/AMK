package com.helpfooter.steve.amklovebaby;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.CustomControlView.OrderListLoadView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

public class OrderListActivity extends MyActivity implements View.OnClickListener {
    OrderListLoadView orderListLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);


            if(MemberMgr.CheckIsLogin(this)) {
                InitData();
                InitUI();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            if (StaticVar.Member == null) {
                this.finish();
                return;
            }
            InitData();
            InitUI();
        }
    boolean hasloader=false;
    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        ((TextView)findViewById(R.id.title)).getPaint().setFakeBoldText(true);
        ((TextView) findViewById(R.id.btnShowFinished)).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnShowOrdered)).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnShowWaitpayment)).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnShowAll)).setOnClickListener(this);
        if(hasloader==false){
        orderListLoadView=new OrderListLoadView(this,(PercentLinearLayout)findViewById(R.id.layoutOrderList),(ImageView)findViewById(R.id.imgNoOrder),(TextView)findViewById(R.id.txtNoOrder),"P");
            orderListLoadView.AddFilterTV(((TextView) findViewById(R.id.btnShowFinished)));
            orderListLoadView.AddFilterTV(((TextView) findViewById(R.id.btnShowOrdered)));
            orderListLoadView.AddFilterTV(((TextView) findViewById(R.id.btnShowWaitpayment)));
        orderListLoadView.LoadList();
            hasloader=true;
        }
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


    public boolean PopupNotice(){
        return false;
    }

}
