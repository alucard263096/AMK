package com.helpfooter.steve.amklovebaby;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.CustomControlView.DoctorCommentView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.MemberFollowDoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberFollowDoctorObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.DoctorCommentLoader;
import com.helpfooter.steve.amklovebaby.Loader.DoctorFollowCountLoader;
import com.helpfooter.steve.amklovebaby.Loader.DoctorStatisticsLoader;
import com.helpfooter.steve.amklovebaby.Loader.UpdateFollowDoctorLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

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
        UrlImageLoader il=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());

        txtName=(TextView)findViewById(R.id.txtName);
        txtName.setText(doctor.getName());

        txtName.setTextColor(Color.BLACK);
        txtName.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtNameCategory);
        if(doctor.getIsTaiwan().equals("Y")) {
            text.setVisibility(View.VISIBLE);
        }
        else {
            text.setVisibility(View.INVISIBLE);
        }

        btnFollow = (TextView)findViewById(R.id.btnFollow);
        btnFollow.getPaint().setFakeBoldText(true);

        txtOfficeTitle=(TextView)findViewById(R.id.txtOfficeTitle);
        txtOfficeTitle.setText(doctor.getOffice() + "  " + doctor.getTitle());
        txtOfficeTitle.setTextColor(Color.parseColor("#919191"));
        txtOfficeTitle.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtFansText);
        text.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtFansNum);
        text.setText(String.valueOf(doctor.getFollowCount()));
        text.getPaint().setFakeBoldText(true);
        text.setTextColor(Color.BLACK);

        text = (TextView)findViewById(R.id.txtServiceText);
        text.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtServiceNum);
        text.setText(String.valueOf(doctor.getCharquerycount() + doctor.getVideoquerycount()));
        text.getPaint().setFakeBoldText(true);
        text.setTextColor(Color.BLACK);

        text = (TextView)findViewById(R.id.txtRecomandText);
        text.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtRecomandNum);
        text.setText(doctor.getRealGeneralScore());
        text.getPaint().setFakeBoldText(true);
        text.setTextColor(Color.BLACK);

        float fScore = Float.parseFloat(doctor.getRealGeneralScore());
        ImageView imgHigh = (ImageView)findViewById(R.id.imgHigh);
        if(fScore >= 4.0f)
        {
            imgHigh.setImageResource(R.drawable.height);
            imgHigh.setVisibility(View.VISIBLE);
            param= ToolsUtil.getLayoutParamHeightWrap();
            param.height = 80;
            param.width = 80;
            param.topMargin = 10;
            imgHigh.setLayoutParams(param);
        }
        else
        {
            imgHigh.setVisibility(View.INVISIBLE);
        }

        //图文咨询
        text = (TextView)findViewById(R.id.txtPictChat);
        text.getPaint().setFakeBoldText(true);

        ImageView img = (ImageView)findViewById(R.id.imgPictChat);

        if(doctor.getEnableCharchat().equals("Y")) {
            img.setImageResource(R.drawable.chart3);
            text = (TextView) findViewById(R.id.txtChatPrice);
            text.setBackgroundResource(R.drawable.text_view_border5);
            text.setText("￥" + String.valueOf(doctor.getCharchatPrice()) + "/次 >");
            text.getPaint().setFakeBoldText(true);
            text.setTextSize(15);
            ((LinearLayout)findViewById(R.id.layoutChat)).setOnClickListener(this);
        }
        else {
            img.setImageResource(R.drawable.chart4);
            text = (TextView) findViewById(R.id.txtChatPrice);
            text.setBackgroundResource(R.drawable.text_view_border7);
            text.setText("尚未开通");
            text.setTextColor(getResources().getColor(R.color.deepgray));
            text.getPaint().setFakeBoldText(true);
        }

        //视频咨询
        text = (TextView)findViewById(R.id.txtVedioChat);
        text.getPaint().setFakeBoldText(true);

        img = (ImageView)findViewById(R.id.imgVideo);

        if(doctor.getEnableVideochat().equals("Y")) {
            img.setImageResource(R.drawable.video3);
            text = (TextView) findViewById(R.id.txtVedioPrice);
            text.setBackgroundResource(R.drawable.text_view_border5);
            text.setText("￥" + String.valueOf(doctor.getVideochatPrice()) + "/次 >");
            text.getPaint().setFakeBoldText(true);
            text.setTextSize(15);
            ((LinearLayout)findViewById(R.id.layoutVideo)).setOnClickListener(this);
        }
        else {
            img.setImageResource(R.drawable.video3);
            text = (TextView) findViewById(R.id.txtVedioPrice);
            text.setBackgroundResource(R.drawable.text_view_border7);
            text.setText("尚未开通");
            text.setTextColor(getResources().getColor(R.color.deepgray));
            text.getPaint().setFakeBoldText(true);
        }

        //门诊挂号
        text = (TextView)findViewById(R.id.txtRegister);
        text.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtRegisterPrice);
        text.getPaint().setFakeBoldText(true);

        //医生介绍
        text = (TextView)findViewById(R.id.txtIntroductionTitle);
        text.getPaint().setFakeBoldText(true);

        text = (TextView)findViewById(R.id.txtRegisterPrice);
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

        btnFollow=(TextView)findViewById(R.id.btnFollow);
        btnFollow.setOnClickListener(this);
        if(StaticVar.Member!=null){
            MemberFollowDoctorDao followdao=new MemberFollowDoctorDao(this);
            hasFollow=followdao.hasFollow(doctor.getId());
            setFollow();
        }

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

        text = (TextView)findViewById(R.id.txtCommentTitle);
        text.getPaint().setFakeBoldText(true);

        ((LinearLayout)findViewById(R.id.btnOpenComment)).setOnClickListener(this);

        if(hasload==false){
            PercentLinearLayout svBody=(PercentLinearLayout)findViewById(R.id.CommentList);
            view=new DoctorCommentView(this,svBody);
            DoctorCommentLoader loader=new DoctorCommentLoader(this,doctor.getId(),2);
            loader.setCallBack(this);
            loader.start();
            hasload=true;
        }


    }
    DoctorCommentView view;
    boolean hasload=false;


    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        view.showCommentAll(lstObjs);
    }

    private void setFollow() {
        if(hasFollow){
            //btnFollow.setBackgroundColor(Color.parseColor("#D5C9B8"));
            btnFollow.setBackground(getResources().getDrawable(R.drawable.text_view_border6_1));
            btnFollow.setTextColor(Color.WHITE);
            btnFollow.setText("取消关注");
        }else {
            //btnFollow.setBackgroundColor(getResources().getColor(R.color.myblue));
            btnFollow.setBackground(getResources().getDrawable(R.drawable.text_view_border6));
            btnFollow.setTextColor(getResources().getColor(R.color.newblue));
            btnFollow.setText("+ 关注");
        }
    }


    private void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
        doctor=(DoctorObj)dao.getObj(id);

        DoctorStatisticsLoader statisticsLoader=new DoctorStatisticsLoader(this,id);
        statisticsLoader.start();

        DoctorFollowCountLoader followCountLoader=new DoctorFollowCountLoader(this,id);
        followCountLoader.start();
    }

    @Override
    public void onClick(View v) {
        ;
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnOpenComment:
                Intent intent3 = new Intent(this, DoctorCommentActivity.class);
                intent3.putExtra("Id", doctor.getId());
                startActivity(intent3);
                break;
            case R.id.layoutVideo:
                Intent intent = new Intent(this, VideoChatOrderActivity.class);
                intent.putExtra("Id", doctor.getId());
                startActivity(intent);
                break;
            case R.id.layoutChat:
                Intent intent2 = new Intent(this, CharOrderSubmitActivity.class);
                intent2.putExtra("Id", doctor.getId());
                startActivity(intent2);
                break;
            case R.id.btnFollow:
                if(MemberMgr.CheckIsLogin(this)){
                    hasFollow=!hasFollow;
                    setFollow();

                    MemberFollowDoctorDao dao=new MemberFollowDoctorDao(this);
                    if(hasFollow) {
                        MemberFollowDoctorObj obj = new MemberFollowDoctorObj();
                        obj.setDoctor_id(doctor.getId());
                        obj.setMember_id(StaticVar.Member.getId());
                        dao.insertObj(obj);
                    }else {
                        dao.removeFollow(doctor.getId());
                    }
                    UpdateFollowDoctorLoader loader=
                            new UpdateFollowDoctorLoader(this,StaticVar.Member.getId(),doctor.getId(),hasFollow?"Y":"N");
                    loader.start();
                }
                break;
        }
    }

}
