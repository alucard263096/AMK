package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.CustomObject.VerifyCodeButtonDisable;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.LoginSendLoader;
import com.helpfooter.steve.amklovebaby.Loader.MemberLoader;
import com.helpfooter.steve.amklovebaby.Loader.RegSendLoader;
import com.helpfooter.steve.amklovebaby.Loader.RegisterLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;


public class RegisterActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack {

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

        btnSendVerifyCode=((Button)findViewById(R.id.btnSendVerifyCode));
        btnSendVerifyCode.setOnClickListener(this);

        btnRegister=((Button)findViewById(R.id.btnLogin));
        btnRegister.setOnClickListener(this);

        txtMobile=((EditText)findViewById(R.id.txtMobile));
        txtVerifyCode=((EditText)findViewById(R.id.txtVerifyCode));
        txtPassword=(EditText)findViewById(R.id.txtPassword);
    }


    @Override
    public void onClick(View v) {
        String mobile;
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
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
                RegisterLoader loader=new RegisterLoader(this,mobile,verifycode,password);
                loader.setCallBack(this);
                loader.start();
                threadstatus="register";
                break;
        }
    }
    ResultObj sendcodeResult;
    MemberObj memberObj;


    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

    }
}
