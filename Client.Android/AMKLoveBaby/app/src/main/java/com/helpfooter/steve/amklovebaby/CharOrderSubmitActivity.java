package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.helpfooter.steve.amklovebaby.Loader.CharchatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


public class CharOrderSubmitActivity extends Activity  implements View.OnClickListener,IWebLoaderCallBack {

    DoctorObj doctor;
    ImageView btnBack;
    Button btnSubmit;

    TextView txtSex,txtMobile;
    EditText txtName,txtAge,txtDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submit);

        //MemberMgr.GetMemberInfoFromDb(this);

        if(MemberMgr.CheckIsLogin(this)) {
            InitData();
            InitUI();
        }

    }

    private void InitUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);


        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);




        txtSex=(TextView)findViewById(R.id.txtSex);
        txtSex.setOnClickListener(this);


        txtName=(EditText)findViewById(R.id.txtName);
        txtAge=(EditText)findViewById(R.id.txtAge);
        txtDescription=(EditText)findViewById(R.id.txtDescription);

        txtMobile=(TextView)findViewById(R.id.txtMobile);

        if(StaticVar.Member!=null){
            txtName.setText(StaticVar.Member.getName());
            txtAge.setText(StaticVar.Member.getAge());
            txtMobile.setText(StaticVar.Member.getMobile());

            setTxtSex(StaticVar.Member.getSex());

        }

    }

    private void setTxtSex(String value){
        if(!value.equals("F")){
            txtSex.setText("男");
            txtSex.setTag("M");
        }else {
            txtSex.setText("女");
            txtSex.setTag("F");
        }
        txtSex.setTextColor(Color.BLACK);
    }


    private void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
        doctor = (DoctorObj) dao.getObj(id);
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.txtSex:
                intent = new Intent(this, SexSelectActivity.class);
                startActivityForResult(intent, 2);
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

    String getSex(){
        if(txtSex.getTag()==null||!((String)txtSex.getTag()).equals("F")){
            return "F";
        }
        return "M";
    }

    private void ClickSubmit() {
        CharchatOrderCreateLoader loader=new CharchatOrderCreateLoader(this,doctor.getId(), StaticVar.Member.getId(),txtName.getText().toString(),txtAge.getText().toString(),getSex(),txtDescription.getText().toString());
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=-1){
            return;
        }
        String retstr=data.getStringExtra("return");
        if(requestCode == 2){
            setTxtSex(retstr);
        }
    }

    ResultObj res;
    private android.os.Handler resultHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (res.getId()){
                case -103:
                    Toast.makeText(CharOrderSubmitActivity.this,  "您选择的时间已经被使用", Toast.LENGTH_LONG).show();
                    return;
                case -101:
                    Toast.makeText(CharOrderSubmitActivity.this, "你选择的医生无效", Toast.LENGTH_LONG).show();
                    return;
                case -106:
                    Toast.makeText(CharOrderSubmitActivity.this, "医生没有启用视频会诊功能", Toast.LENGTH_LONG).show();
                    return;
                case -102:
                    Toast.makeText(CharOrderSubmitActivity.this, "您的会员身份无效", Toast.LENGTH_LONG).show();
                    return;
                case 0:
                    Intent intent = new Intent(CharOrderSubmitActivity.this, OrderPaymentActivity.class);
                    int returnId=Integer.parseInt(res.getRet());
                    intent.putExtra("Id", returnId);
                    startActivity(intent);
                    return;
                default:
                    Toast.makeText(CharOrderSubmitActivity.this, "其它错误", Toast.LENGTH_LONG).show();
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
