package com.helpfooter.steve.amkdoctor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.CustomObject.MyActivity;
import com.helpfooter.steve.amkdoctor.DAO.DoctorDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;

import java.util.ArrayList;


public class DoctorDetailActivity extends MyActivity implements View.OnClickListener,IWebLoaderCallBack {
    DoctorObj doctor;
    ImageView btnBack,imgPhoto;
    TextView txtName,txtOfficeTitle,btnVedioChat,btnCharChat;
    TextView txtIntroduce,txtCredentials,txtExpert,btnFollow;
    LinearLayout layoutBusiness;

    boolean hasFollow=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        InitData();
        InitUI();
    }
    private void InitUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        TextView text = (TextView)findViewById(R.id.title);
        text.setText(doctor.getName() + doctor.getOffice() + "诊所");
        text.getPaint().setFakeBoldText(true);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.height = 200;
        param.width = 200;
        param.leftMargin = 20;
        param.topMargin = 20;
        imgPhoto.setLayoutParams(param);
        //UrlImageLoader il=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());
        Bitmap bitmap=UrlImageLoader.GetBitmap(StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());
        imgPhoto.setImageBitmap(bitmap);
        txtName=(TextView)findViewById(R.id.txtName);
        txtName.setText(doctor.getName());

        txtName.setTextColor(Color.BLACK);
        txtName.getPaint().setFakeBoldText(true);




        txtOfficeTitle=(TextView)findViewById(R.id.txtOfficeTitle);
        txtOfficeTitle.setText(doctor.getOffice() + "  " + doctor.getTitle());
        txtOfficeTitle.setTextColor(Color.parseColor("#919191"));
        txtOfficeTitle.getPaint().setFakeBoldText(true);



        //医生介绍
        text = (TextView)findViewById(R.id.txtIntroductionTitle);
        text.getPaint().setFakeBoldText(true);



        /*txtWorktime=(TextView)findViewById(R.id.txtOpenHour);
        txtWorktime.setText("坐诊时间:"+doctor.getBookingtime());

        txtVideoQuerycount=(TextView)findViewById(R.id.txtVideoQueryCount);
        txtVideoQuerycount.setText(String.valueOf("预约通话:" + doctor.getVideoquerycount())+"次");

        txtCharQuerycount=(TextView)findViewById(R.id.txtCharQueryCount);
        txtCharQuerycount.setText(String.valueOf("图文咨询:"+doctor.getCharquerycount())+"次");

        txtGeneralScore=(TextView)findViewById(R.id.txtGeneralScore);
        txtGeneralScore.setText(String.valueOf(doctor.getRealGeneralScore()));*/

        /*if(!doctor.getEnableVideochat().equals("Y")&&!doctor.getEnableCharchat().equals("Y")){
            //layoutBusiness=(LinearLayout)findViewById(R.id.layoutBusinee);
            layoutBusiness.setVisibility(LinearLayout.GONE);
        }else {
            //btnVedioChat=(TextView)findViewById(R.id.btnVedioChat);
            //btnCharChat=(TextView)findViewById(R.id.btnCharChat);
            if(doctor.getEnableVideochat().equals("Y")){
                btnVedioChat.setText("视频咨询/" + String.valueOf(doctor.getVideochatPrice()) + "元");
                btnVedioChat.setOnClickListener(this);
            }else{
                btnVedioChat.setVisibility(LinearLayout.GONE);
            }
            if(doctor.getEnableCharchat().equals("Y")){
                btnCharChat.setText("图文咨询/" + String.valueOf(doctor.getCharchatPrice()) + "元");
                btnCharChat.setOnClickListener(this);
            }else{
                btnCharChat.setVisibility(LinearLayout.GONE);
            }
        }*/



        txtIntroduce=(TextView)findViewById(R.id.txtIntroduce);
        txtIntroduce.setText(doctor.getIntroduce());
        txtIntroduce.setTextSize(14);
        txtIntroduce.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtCredentialsTitle);
        text.getPaint().setFakeBoldText(true);

        txtCredentials=(TextView)findViewById(R.id.txtCredentials);
        txtCredentials.setText(doctor.getCredentials());
        txtCredentials.setTextSize(14);
        txtCredentials.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtExpertTitle);
        text.getPaint().setFakeBoldText(true);

        txtExpert=(TextView)findViewById(R.id.txtExpert);
        txtExpert.setText(doctor.getExpert());
        txtExpert.setTextSize(14);
        txtExpert.getPaint().setFakeBoldText(true);

       /* text = (TextView)findViewById(R.id.txtCommentTitle);
        text.getPaint().setFakeBoldText(true);*/

        ((LinearLayout)findViewById(R.id.btnOpenComment)).setOnClickListener(this);



    }

    boolean hasload=false;


    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

    }




    private void InitData() {

        DoctorDao dao=new DoctorDao(this);
        doctor=StaticVar.Doctor;


    }

    @Override
    public void onClick(View v) {
        ;
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;


        }
    }

}
