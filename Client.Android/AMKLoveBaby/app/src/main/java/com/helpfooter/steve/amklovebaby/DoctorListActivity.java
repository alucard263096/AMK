package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.DoctorListLoadView;
import com.helpfooter.steve.amklovebaby.CustomControlView.FollowDoctorListLoadView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.MemberFollowDoctorLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DoctorListActivity extends MyActivity implements View.OnClickListener {

    String name="";
    String search="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        InitData();
        InitUI();
    }

    private void InitData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        search = intent.getStringExtra("search");
    }

    private void InitUI() {

        if(name.length()>2){
            ((TextView)findViewById(R.id.title)).setText(name);
        }
        if(search.length()>2){
            search=" and "+search;
        }

        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        DoctorListLoadView lstLoad=new DoctorListLoadView(DoctorListActivity.this,(LinearLayout)findViewById(R.id.doctor_list),search);
        lstLoad.LoadDoctorListData();
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
