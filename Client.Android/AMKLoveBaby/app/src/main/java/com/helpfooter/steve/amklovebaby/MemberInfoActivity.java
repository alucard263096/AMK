package com.helpfooter.steve.amklovebaby;

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
import com.helpfooter.steve.amklovebaby.CustomControlView.MemberPhotoLoadView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DAO.MemberDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.MemberLoader;
import com.helpfooter.steve.amklovebaby.Loader.MemberUpdateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


public class MemberInfoActivity extends MyActivity implements View.OnClickListener,View.OnFocusChangeListener,IWebLoaderCallBack {

    EditText txtName,txtBirth,txtHistory;
    Button btnLogout;
    TextView txtMobile,btnUploadPhotos;
    TextView txtSex;
    LinearLayout lstMemberPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

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
        ((ImageView)findViewById(R.id.btnBack)).setOnClickListener(this);

        TextView txtTitle = (TextView)findViewById(R.id.title);
        txtTitle.getPaint().setFakeBoldText(true);

        txtName=(EditText)findViewById(R.id.txtName);
        txtName.setOnFocusChangeListener(this);

        txtMobile=(TextView)findViewById(R.id.txtMobile);
        txtMobile.setOnFocusChangeListener(this);

        txtBirth=(EditText)findViewById(R.id.txtBirth);
        txtBirth.setOnFocusChangeListener(this);

        txtHistory=(EditText)findViewById(R.id.txtHistory);
        txtHistory.setOnFocusChangeListener(this);


        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);


        txtSex=(TextView)findViewById(R.id.txtSex);
        txtSex.setOnClickListener(this);

        btnUploadPhotos=(TextView)findViewById(R.id.btnUploadPhotos);
        btnUploadPhotos.setOnClickListener(this);

        lstMemberPhotos=(LinearLayout)findViewById(R.id.lstMemberPhotos);


        LoadMemberData();
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
    boolean hasload=false;

    private void LoadMemberData(){
        if(StaticVar.Member!=null){
            txtName.setText(StaticVar.Member.getName());
            txtBirth.setText(StaticVar.Member.getBirth());
            txtHistory.setText(StaticVar.Member.getHistory());
            txtMobile.setText(StaticVar.Member.getMobile());
            setTxtSex(StaticVar.Member.getSex());
            if(hasload==false){
                MemberPhotoLoadView loadView=new MemberPhotoLoadView(this,StaticVar.Member.getId(),lstMemberPhotos);
                loadView.LoadPhotoList();
                hasload=true;
            }

        }
    }


    private void InitData() {
        if(StaticVar.Member!=null){

            MemberLoader loader=new MemberLoader(this,StaticVar.Member.getMobile());
            loader.setCallBack(this);
            loader.start();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnLogout:
                MemberDao dao=new MemberDao(this);
                dao.deleteMember();

                StaticVar.Member=null;
                try{
                    StaticVar.MainForm.RefreshMember();
                    StaticVar.MainForm.SetToHome();
                }catch (Exception e){
                    e.printStackTrace();
                }
                this.finish();
                return;
            case R.id.txtSex:
                Intent intent = new Intent(this, SexSelectActivity.class);
                startActivityForResult(intent, 2);
                return;
            case R.id.btnUploadPhotos:
                Intent intenta = new Intent(this, MemberPhotoUploadActivity.class);
                startActivityForResult(intenta, 3);
                return;
        }
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
            MemberUpdateLoader loader = new MemberUpdateLoader(this, StaticVar.Member.id, "sex", retstr);
            UpdateResultCallBack callBack = new UpdateResultCallBack();
            loader.setCallBack(callBack);
            loader.start();
            StaticVar.Member.setSex(retstr);
        }
        if(requestCode == 3){
            MemberPhotoLoadView loadView=new MemberPhotoLoadView(this,StaticVar.Member.getId(),lstMemberPhotos);
            loadView.LoadPhotoList();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus==false){
            switch (v.getId()) {
                case R.id.txtName:
                    StaticVar.Member.setName(((EditText) v).getText().toString());
                    updateToMember(v);
                    return;
                case R.id.txtBirth:
                    updateToMember(v);
                    StaticVar.Member.setBirth(((EditText) v).getText().toString());
                    return;
                case R.id.txtHistory:
                    updateToMember(v);
                    StaticVar.Member.setHistory(((EditText) v).getText().toString());
                    return;
            }
        }
    }
    public void updateToMember(View v){


        MemberDao dao=new MemberDao(this);
        dao.updateObj(StaticVar.Member);

        String field = String.valueOf(v.getTag());
        String value = ((EditText) v).getText().toString();

        MemberUpdateLoader loader = new MemberUpdateLoader(this, StaticVar.Member.id, field, value);
        UpdateResultCallBack callBack = new UpdateResultCallBack();
        loader.setCallBack(callBack);
        loader.start();
    }

    class UpdateResultCallBack implements IWebLoaderCallBack{

        private android.os.Handler handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                Toast.makeText(MemberInfoActivity.this,"更新失败，请检查网络",Toast.LENGTH_LONG).show();
            }
        };

        @Override
        public void CallBack(ArrayList<AbstractObj> lstObjs) {
            if(lstObjs.size()>0){
                ResultObj resultObj=(ResultObj)lstObjs.get(0);
                if(resultObj.getId()!=0){
                    handler.sendEmptyMessage(0);
                }
            }else {
                handler.sendEmptyMessage(0);
            }
        }
    }

    private android.os.Handler loadDataHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            LoadMemberData();
        }
    };

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(lstObjs.size()>0){
            StaticVar.Member=(MemberObj)lstObjs.get(0);
            loadDataHandler.sendEmptyMessage(0);
        }
    }








}
