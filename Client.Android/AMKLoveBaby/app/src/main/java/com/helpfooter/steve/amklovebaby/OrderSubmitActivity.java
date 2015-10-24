package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


public abstract class OrderSubmitActivity extends Activity implements
        View.OnClickListener,
        IWebLoaderCallBack {

    ImageView btnBack;
    EditText txtName,txtDescription,txtAge;
    RadioButton rbFemale,rbMale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submit);

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

    abstract void InitData();

    protected void InitUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        ((Button)findViewById(R.id.btnSubmit)).setOnClickListener(this);

        txtName=(EditText)findViewById(R.id.txtName);
        txtAge=(EditText)findViewById(R.id.txtAge);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        rbMale=(RadioButton)findViewById(R.id.rbMale);
        rbFemale=(RadioButton)findViewById(R.id.rbFemale);

        if(StaticVar.Member!=null){
            txtName.setText(StaticVar.Member.getName());
            txtAge.setText(StaticVar.Member.getAge());
            if(StaticVar.Member.getSex()!=null||StaticVar.Member.getSex().equals("M")){
                rbMale.setChecked(true);
            }
        }
    }

    protected String getSex(){
        if(rbMale.isChecked()){
            return "M";
        }
        return "F";
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnSubmit:
                if(txtDescription.getText().toString().length()<10){
                    Toast.makeText(this, "请尽可能详细地完善你的描述", Toast.LENGTH_LONG).show();
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
