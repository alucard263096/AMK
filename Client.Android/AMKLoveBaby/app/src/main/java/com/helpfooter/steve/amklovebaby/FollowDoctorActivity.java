package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.CustomControlView.DoctorListLoadView;
import com.helpfooter.steve.amklovebaby.CustomControlView.FollowDoctorListLoadView;
import com.helpfooter.steve.amklovebaby.CustomObject.VerifyCodeButtonDisable;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.MemberFollowDoctorLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


public class FollowDoctorActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack{

    String code;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_doctor);

        InitUI();
        InitData();
    }

    private void InitData() {

    }

    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);

        MemberFollowDoctorLoader loader=new MemberFollowDoctorLoader(this,StaticVar.Member.getId());
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                return;
        }
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        sendcodeHandler.sendEmptyMessage(0);

    }


    private android.os.Handler sendcodeHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            FollowDoctorListLoadView lstLoad=new FollowDoctorListLoadView(FollowDoctorActivity.this,(LinearLayout)findViewById(R.id.doctor_list));
            lstLoad.LoadDoctorListData();
        }
    };


}