package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.CustomControlView.OrderSubmitInfoView;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.logging.Handler;


public class VedioOrderSubmitActivity extends OrderSubmitActivity {

    DoctorObj doctor;
    String order_date;
    String order_time;



    protected void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
        doctor=(DoctorObj)dao.getObj(id);

        order_date=intent.getStringExtra("order_date");
        order_time=intent.getStringExtra("order_time");
    }

    boolean haveinit=false;
    protected void InitUI() {
        super.InitUI();
        LinearLayout linearLayout=(LinearLayout)this.findViewById(R.id.layoutMainInfo);
        if(haveinit==false) {
            OrderSubmitInfoView infoView = new OrderSubmitInfoView(this, linearLayout);

            infoView.AddDisplayField("预约医生", doctor.getName());
            infoView.AddDisplayField("预约费用", doctor.getVideochatPrice()+"元/20分钟");
            infoView.AddDisplayField("预约时间", order_date+" "+order_time);
        }

        haveinit=true;

    }

    @Override
    void ClickSubmit() {
        VideochatOrderCreateLoader loader=new VideochatOrderCreateLoader(this,doctor.getId(),order_date,order_time, StaticVar.Member.getId(),txtName.getText().toString(),txtMobile.getText().toString(),txtDescription.getText().toString());
        loader.setCallBack(this);
        loader.start();
    }
}
