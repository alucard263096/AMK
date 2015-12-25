package com.helpfooter.steve.amklovebaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.DoctorCommentView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.DoctorCommentLoader;

import java.util.ArrayList;


public class DoctorCommentActivity extends MyActivity implements View.OnClickListener,IWebLoaderCallBack {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_comment);

        InitUI();
        InitData();

    }


    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        TextView txtTitle = (TextView)findViewById(R.id.title);
        txtTitle.getPaint().setFakeBoldText(true);
    }

    boolean hasload=false;
    DoctorCommentView view;
    private void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        if(hasload==false){
            PercentLinearLayout svBody=(PercentLinearLayout)findViewById(R.id.svBody);
             view=new DoctorCommentView(this,svBody);
            DoctorCommentLoader loader=new DoctorCommentLoader(this,id,100);
            loader.setCallBack(this);
            loader.start();
            hasload=true;
        }
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        view.showCommentAll(lstObjs);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                return;
        }
    }
}
