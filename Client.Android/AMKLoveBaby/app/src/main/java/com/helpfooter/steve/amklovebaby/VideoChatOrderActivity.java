package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.Common.VersionUpdateMgr;
import com.helpfooter.steve.amklovebaby.CustomControlView.WorkTimeTableView;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CancellationException;


public class VideoChatOrderActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack {

    DoctorObj doctor;
    ImageView btnBack;
    Button btnSubmit;

    TextView txtOrderDate,txtOrderTime,txtSex,txtMobile;
    EditText txtName,txtAge,txtDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat_order);

        //MemberMgr.GetMemberInfoFromDb(this);
        if(MemberMgr.CheckIsLogin(this)) {
            InitData();
            InitUI();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (StaticVar.Member == null) {
            this.finish();
            return;
        }
        InitData();
        InitUI();
    }

    private void InitUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);


        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);


        txtOrderDate=(TextView)findViewById(R.id.txtOrderDate);
        txtOrderDate.setOnClickListener(this);


        txtOrderTime=(TextView)findViewById(R.id.txtOrderTime);
        txtOrderTime.setOnClickListener(this);

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
            case R.id.txtOrderDate:
                intent = new Intent(this, WorkDaySelectActivity.class);
                intent.putExtra("Id", doctor.getId());
                startActivityForResult(intent, 0);
                return;
            case R.id.txtOrderTime:
                if(txtOrderDate.getTag()!=null&&((String)txtOrderDate.getTag()).length()>1){
                    intent = new Intent(this, WorkTimeSelectActivity.class);
                    intent.putExtra("Id", doctor.getId());
                    intent.putExtra("Date", ((String)txtOrderDate.getTag()));
                    startActivityForResult(intent, 1);
                }else {
                    Toast.makeText(this,"请先选中预约日期",Toast.LENGTH_LONG).show();
                }
                return;
            case R.id.txtSex:
                intent = new Intent(this, SexSelectActivity.class);
                startActivityForResult(intent, 2);
                return;
            case R.id.btnSubmit:
                String order_date=((String)txtOrderDate.getTag());
                String order_time=((String)txtOrderTime.getTag());
                if(order_date.length()<1||order_time.length()<1){
                    Toast.makeText(this, "请选中预约日期和预约时间", Toast.LENGTH_LONG).show();
                    return;
                }else if(txtDescription.getText().toString().length()<10){
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
        String order_date=((String)txtOrderDate.getTag());
        String order_time=((String)txtOrderTime.getTag());
        VideochatOrderCreateLoader loader=new VideochatOrderCreateLoader(this,doctor.getId(),order_date,order_time, StaticVar.Member.getId(),txtName.getText().toString(),txtAge.getText().toString(),getSex(),txtDescription.getText().toString());
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
        if(requestCode == 0){
            //edit.setText(data.getStringExtra("return"));
            txtOrderDate.setText(retstr);
            txtOrderDate.setTextColor(Color.BLACK);
            txtOrderDate.setTag(retstr);

            txtOrderTime.setText("请选择");
            txtOrderTime.setTextColor(Color.parseColor("#81CAE6"));

        }else if(requestCode == 1){
            txtOrderTime.setText(retstr);
            txtOrderTime.setTextColor(Color.BLACK);
            txtOrderTime.setTag(retstr);
        }else if(requestCode == 2){
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
                    Toast.makeText(VideoChatOrderActivity.this,  "您选择的时间已经被使用", Toast.LENGTH_LONG).show();
                    return;
                case -101:
                    Toast.makeText(VideoChatOrderActivity.this, "你选择的医生无效", Toast.LENGTH_LONG).show();
                    return;
                case -106:
                    Toast.makeText(VideoChatOrderActivity.this, "医生没有启用视频会诊功能", Toast.LENGTH_LONG).show();
                    return;
                case -102:
                    Toast.makeText(VideoChatOrderActivity.this, "您的会员身份无效", Toast.LENGTH_LONG).show();
                    return;
                case 0:
                    Intent intent = new Intent(VideoChatOrderActivity.this, OrderPaymentActivity.class);
                    int returnId=Integer.parseInt(res.getRet());
                    intent.putExtra("Id", returnId);
                    startActivity(intent);
                    return;
                default:
                    Toast.makeText(VideoChatOrderActivity.this, "其它错误", Toast.LENGTH_LONG).show();
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

    public boolean PopupNotice(){
        return false;
    }
}
