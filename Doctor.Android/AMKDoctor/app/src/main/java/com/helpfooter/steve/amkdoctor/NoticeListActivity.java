package com.helpfooter.steve.amkdoctor;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.helpfooter.steve.amkdoctor.CustomControlView.NoticeListLoadView;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;


public class NoticeListActivity extends Activity implements View.OnClickListener {
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticelist);
        InitUI();
        InitData();

    }

    private void InitData() {

    }
    NoticeListLoadView lstLoad;
    boolean hasNewView=false;
    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        if(hasNewView==false){
            lstLoad = new NoticeListLoadView(this, this.getApplicationContext(), (LinearLayout) findViewById(R.id.normal_notice_list));
            lstLoad.LoadList();
            hasNewView=true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                lstLoad.UnloadList();
                return;
        }
    }





}
