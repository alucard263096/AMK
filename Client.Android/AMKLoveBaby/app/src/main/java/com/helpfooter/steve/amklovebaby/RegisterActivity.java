package com.helpfooter.steve.amklovebaby;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.CustomObject.VerifyCodeButtonDisable;
import com.helpfooter.steve.amklovebaby.DAO.MemberDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.RegSendLoader;
import com.helpfooter.steve.amklovebaby.Loader.RegisterLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;


public class RegisterActivity extends MyActivity implements View.OnClickListener,IWebLoaderCallBack {

    String threadstatus;
    Button btnSendVerifyCode,btnRegister;
    EditText txtMobile,txtVerifyCode,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        InitUI();


    }


    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.title);
        txtTitle.getPaint().setFakeBoldText(true);

        btnSendVerifyCode=((Button)findViewById(R.id.btnSendVerifyCode));
        btnSendVerifyCode.setOnClickListener(this);

        btnRegister=((Button)findViewById(R.id.btnRegister));
        btnRegister.setOnClickListener(this);

        txtMobile=((EditText)findViewById(R.id.txtMobile));
        txtVerifyCode=((EditText)findViewById(R.id.txtVerifyCode));
        txtPassword=(EditText)findViewById(R.id.txtPassword);

        ((TextView) findViewById(R.id.btnRegPP)).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String mobile= null;
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnRegPP:

                Intent intent = new Intent(this, GeneralTextActivity.class);
                intent.putExtra("code","registerpolicy");
                intent.putExtra("title","服务于协议");
                startActivity(intent);
                return;
            case R.id.btnSendVerifyCode:
                mobile=txtMobile.getText().toString();

                if(ToolsUtil.isMobileNO(mobile)){
                    RegSendLoader loader=new RegSendLoader(this,mobile);
                    loader.setCallBack(this);
                    loader.start();
                    threadstatus="sendcode";
                    VerifyCodeButtonDisable sendTh=new VerifyCodeButtonDisable(btnSendVerifyCode);
                    sendTh.start();;
                }else {
                    Toast.makeText(this, "你输入的手机号码不正确", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegister:
                String verifycode=txtVerifyCode.getText().toString();
                if(verifycode.length()==0){
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_LONG).show();
                    return;
                }
                String password=txtPassword.getText().toString();
                if(password.length()==0){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                mobile=txtMobile.getText().toString();


                memberObj=new MemberObj();
                memberObj.setName(mobile);
                memberObj.setMobile(mobile);



                RegisterLoader loader=new RegisterLoader(this,mobile,verifycode,password);
                loader.setCallBack(this);
                loader.start();
                threadstatus="register";
                btnRegister.setEnabled(false);
                break;
        }
    }
    ResultObj sendcodeResult,registerResult;
    MemberObj memberObj;


    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(threadstatus=="sendcode"){
            if(lstObjs.size()>0){
                sendcodeResult=(ResultObj)lstObjs.get(0);
            }
            sendcodeHandler.sendEmptyMessage(0);
        }else if(threadstatus=="register"){
            if(lstObjs.size()>0){
                registerResult=(ResultObj)lstObjs.get(0);
            }
            memberVerifyHandler.sendEmptyMessage(0);
        }
    }

    private android.os.Handler sendcodeHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(sendcodeResult==null){
                Toast.makeText(RegisterActivity.this, "发送验证码失败，请检查网络或者手机号码。", Toast.LENGTH_LONG).show();
            }else {
                if(sendcodeResult.getId()==2){
                    Toast.makeText(RegisterActivity.this, "该手机号码已经注册，请直接登录。", Toast.LENGTH_LONG).show();
                }else if(sendcodeResult.getId()==0){
                    VerifyCodeButtonDisable sendTh=new VerifyCodeButtonDisable(btnSendVerifyCode);
                    sendTh.start();;
                    Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "注册失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        }
    };



    private android.os.Handler memberVerifyHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            btnRegister.setEnabled(true);
            if(registerResult==null){
                Toast.makeText(RegisterActivity.this, "注册失败，请检查网络", Toast.LENGTH_LONG).show();
            }else {
                if(registerResult.getId()==0){
                    memberObj.setId(Integer.parseInt(registerResult.getRet()));
                    StaticVar.Member=memberObj;
                    MemberDao memberDao=new MemberDao(RegisterActivity.this);
                    memberDao.refreshMember(memberObj);
                    RegisterActivity.this.finish();
                }
                else if(registerResult.getId()==-2){
                    Toast.makeText(RegisterActivity.this, "该手机号码已经注册，请直接登录。", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Toast.makeText(RegisterActivity.this, "注册失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        }
    };


    public boolean PopupNotice(){
        return false;
    }

}
