package com.helpfooter.steve.amklovebaby;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.FollowDoctorListLoadView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DAO.MemberFollowDoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.MemberFollowDoctorLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


public class FollowDoctorActivity extends MyActivity implements View.OnClickListener,IWebLoaderCallBack{

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
        TextView txtTitle = (TextView)findViewById(R.id.title);
        txtTitle.getPaint().setFakeBoldText(true);
        int member_id=0;
        if(StaticVar.Member!=null){
            member_id=StaticVar.Member.getId();
        }
        MemberFollowDoctorDao dao=new MemberFollowDoctorDao(this);
        MemberFollowDoctorLoader loader=new MemberFollowDoctorLoader(this,member_id);
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