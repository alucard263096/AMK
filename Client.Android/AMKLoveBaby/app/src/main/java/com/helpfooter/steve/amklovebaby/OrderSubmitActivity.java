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

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


public abstract class OrderSubmitActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack {

    ImageView btnBack;

    EditText txtName,txtMobile,txtDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_order_submit);

        if(MemberMgr.CheckIsLogin(this)) {
            InitData();
            InitUI();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(StaticVar.Member==null){
            this.finish();
            return;
        }
        InitData();
        InitUI();
    }

    protected void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
    }

    protected void InitUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        ((Button)findViewById(R.id.btnSubmit)).setOnClickListener(this);

        txtName=(EditText)findViewById(R.id.txtName);
        txtMobile=(EditText)findViewById(R.id.txtMobile);
        txtDescription=(EditText)findViewById(R.id.txtDescription);

        if(StaticVar.Member!=null){
            txtName.setText(StaticVar.Member.getName());
            txtMobile.setText(StaticVar.Member.getMobile());
        }

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnSubmit:
                if(txtName.getText().toString().length()<2||txtMobile.getText().toString().length()!=11){//||txtDescription.getText().toString().length()<10
                    Toast.makeText(this, "请尽可能详细地完善你的个人资料", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    ClickSubmit();
                }
                return;
        }
    }

    abstract void ClickSubmit();

    ResultObj res;
    private android.os.Handler resultHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (res.getId()){
                case -103:
                    Toast.makeText(OrderSubmitActivity.this,  "您选择的时间已经被使用", Toast.LENGTH_LONG).show();
                    return;
                case -101:
                    Toast.makeText(OrderSubmitActivity.this, "你选择的医生无效", Toast.LENGTH_LONG).show();
                    return;
                case -106:
                    Toast.makeText(OrderSubmitActivity.this, "医生没有启用视频会诊功能", Toast.LENGTH_LONG).show();
                    return;
                case -102:
                    Toast.makeText(OrderSubmitActivity.this, "您的会员身份无效", Toast.LENGTH_LONG).show();
                    return;
                case 0:
                    Intent intent = new Intent(OrderSubmitActivity.this, OrderPaymentActivity.class);
                    int returnId=Integer.parseInt(res.getRet());
                    intent.putExtra("Id", returnId);
                    startActivity(intent);
                    return;
                default:
                    Toast.makeText(OrderSubmitActivity.this, "其它错误", Toast.LENGTH_LONG).show();
                    return;
            }
        }
    };


    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(lstObjs.size()>0){
            res=(ResultObj)lstObjs.get(0);
            resultHandler.sendEmptyMessage(0);

        }
    }
}
