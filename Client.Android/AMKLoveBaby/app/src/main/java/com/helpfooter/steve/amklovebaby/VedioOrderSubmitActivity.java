package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;

import java.util.ArrayList;
import java.util.logging.Handler;


public class VedioOrderSubmitActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack {

    DoctorObj doctor;
    String order_date;
    String order_time;
    ImageView btnBack;

    EditText txtName,txtMobile,txtDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_order_submit);

        InitData();
        InitUI();

    }

    private void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
        doctor=(DoctorObj)dao.getObj(id);

        order_date=intent.getStringExtra("order_date");
        order_time=intent.getStringExtra("order_time");
    }

    private void InitUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        ((TextView)findViewById(R.id.txtDoctorName)).setText(doctor.getName());
        ((TextView)findViewById(R.id.txtPrice)).setText(String.valueOf(doctor.getVideochatPrice())+"次/20分钟");
        ((TextView)findViewById(R.id.txtOrderDatetime)).setText(order_date+" "+order_time);

        ((Button)findViewById(R.id.btnSubmit)).setOnClickListener(this);

        txtName=(EditText)findViewById(R.id.txtName);
        txtMobile=(EditText)findViewById(R.id.txtMobile);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnSubmit:
                if(txtName.getText().toString().length()<2||txtMobile.getText().toString().length()!=11||txtDescription.getText().toString().length()<10){
                    Toast.makeText(this, "请尽可能详细地完善你的个人资料", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    VideochatOrderCreateLoader loader=new VideochatOrderCreateLoader(this,doctor.getId(),order_date,order_time,1,txtName.getText().toString(),txtMobile.getText().toString(),txtDescription.getText().toString());
                    loader.setCallBack(this);
                    loader.start();
                }
                return;
        }
    }

    ResultObj res;
    private android.os.Handler resultHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (res.getId()){
                case -103:
                    Toast.makeText(VedioOrderSubmitActivity.this,  "您选择的时间已经被使用", Toast.LENGTH_LONG).show();
                    return;
                case -101:
                    Toast.makeText(VedioOrderSubmitActivity.this, "你选择的医生无效", Toast.LENGTH_LONG).show();
                    return;
                case -106:
                    Toast.makeText(VedioOrderSubmitActivity.this, "医生没有启用视频会诊功能", Toast.LENGTH_LONG).show();
                    return;
                case -102:
                    Toast.makeText(VedioOrderSubmitActivity.this, "您的会员身份无效", Toast.LENGTH_LONG).show();
                    return;
                case 0:
                    Toast.makeText(VedioOrderSubmitActivity.this, "成功创建订单，ID是"+res.getRet(), Toast.LENGTH_LONG).show();
                    return;
                default:
                    Toast.makeText(VedioOrderSubmitActivity.this, "其它错误", Toast.LENGTH_LONG).show();
                    return;
            }
        }
    };


    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        res=(ResultObj)lstObjs.get(0);
        resultHandler.sendEmptyMessage(0);


    }
}
