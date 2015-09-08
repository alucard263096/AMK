package com.helpfooter.steve.amklovebaby;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.DAO.MemberDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.LoginSendLoader;
import com.helpfooter.steve.amklovebaby.Loader.MemberLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity  implements OnClickListener,IWebLoaderCallBack {

    String threadstatus;
    Button btnSendVerifyCode,btnLogin;
    EditText txtMobile,txtVerifyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitUI();

    }

    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);

        btnSendVerifyCode=((Button)findViewById(R.id.btnSendVerifyCode));
        btnSendVerifyCode.setOnClickListener(this);


        btnLogin=((Button)findViewById(R.id.btnLogin));
        btnLogin.setOnClickListener(this);

        txtMobile=((EditText)findViewById(R.id.txtMobile));
        txtVerifyCode=((EditText)findViewById(R.id.txtVerifyCode));

        if(StaticVar.Member!=null){
            txtMobile.setText(StaticVar.Member.getMobile());
        }


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
                    LoginSendLoader loader=new LoginSendLoader(this,mobile);
                    loader.setCallBack(this);
                    loader.start();
                    threadstatus="sendcode";
                }else {
                    Toast.makeText(this, "你输入的手机号码不正确", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnLogin:
                String verifycode=txtVerifyCode.getText().toString();
                if(verifycode.length()!=6){
                    Toast.makeText(this, "请输入6位的验证码", Toast.LENGTH_LONG).show();
                    return;
                }


                mobile=txtMobile.getText().toString();
                MemberLoader loader=new MemberLoader(this,mobile);
                loader.setCallBack(this);
                loader.start();
                threadstatus="verifymember";
                break;
        }
    }
    ResultObj sendcodeResult;
    MemberObj memberObj;
    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(threadstatus=="sendcode"){
            if(lstObjs.size()>0){
                sendcodeResult=(ResultObj)lstObjs.get(0);
            }
            sendcodeHandler.sendEmptyMessage(0);
        }else if(threadstatus=="verifymember"){
            if(lstObjs.size()>0){
                memberObj=(MemberObj)lstObjs.get(0);
            }
            memberVerifyHandler.sendEmptyMessage(0);
        }
    }

    private android.os.Handler sendcodeHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(sendcodeResult==null||sendcodeResult.getId()!=0){
                Toast.makeText(LoginActivity.this, "发送验证码失败，请检查网络或者手机号码。", Toast.LENGTH_LONG).show();
            }else {
                if(sendcodeResult.getId()==-2){
                    Toast.makeText(LoginActivity.this, "该手机号码尚未注册，请先注册。", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_LONG).show();
                }
            }
        }
    };


    private android.os.Handler memberVerifyHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(memberObj==null){
                Toast.makeText(LoginActivity.this, "该手机号码注册的用户不存在", Toast.LENGTH_LONG).show();
            }else {
                String verifycode=txtVerifyCode.getText().toString();
                if(verifycode.equals(memberObj.getVerifycode())){
                    StaticVar.Member=memberObj;
                    MemberDao memberDao=new MemberDao(LoginActivity.this);
                    memberDao.refreshMember(memberObj);
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(LoginActivity.this, "验证码不正确或者已过期", Toast.LENGTH_LONG).show();
                }
            }
        }
    };


    public class ThreadSendVerifyCode extends Thread{
        Button btn;
        public ThreadSendVerifyCode(Button btn){
            this.btn=btn;
        }

        @Override
        public void run() {

        }
    }

}

