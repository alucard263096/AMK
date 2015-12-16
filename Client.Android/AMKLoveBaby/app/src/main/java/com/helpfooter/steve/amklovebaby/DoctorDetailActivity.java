package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
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
import com.helpfooter.steve.amklovebaby.Loader.MemberFollowDoctorLoader;
import com.helpfooter.steve.amklovebaby.Loader.UpdateFollowDoctorLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class DoctorDetailActivity extends MyActivity implements View.OnClickListener,IWebLoaderCallBack {
    DoctorObj doctor;
    ImageView btnBack,imgPhoto;
    TextView txtName,txtOfficeTitle,txtWorktime,txtVideoQuerycount,txtCharQuerycount,txtGeneralScore,btnVedioChat,btnCharChat;
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
        txtName.getPaint().setFakeBoldText(true);;

        txtOfficeTitle=(TextView)findViewById(R.id.txtOfficeTitle);
        txtOfficeTitle.setText(doctor.getOffice() + "  " + doctor.getTitle());
        txtOfficeTitle.setTextColor(Color.parseColor("#919191"));
        txtOfficeTitle.getPaint().setFakeBoldText(true);

        /*txtWorktime=(TextView)findViewById(R.id.txtOpenHour);
        txtWorktime.setText("坐诊时间:"+doctor.getBookingtime());

        txtVideoQuerycount=(TextView)findViewById(R.id.txtVideoQueryCount);
        txtVideoQuerycount.setText(String.valueOf("预约通话:" + doctor.getVideoquerycount())+"次");

        txtCharQuerycount=(TextView)findViewById(R.id.txtCharQueryCount);
        txtCharQuerycount.setText(String.valueOf("图文咨询:"+doctor.getCharquerycount())+"次");

        txtGeneralScore=(TextView)findViewById(R.id.txtGeneralScore);
        txtGeneralScore.setText(String.valueOf(doctor.getRealGeneralScore()));*/

        if(!doctor.getEnableVideochat().equals("Y")&&!doctor.getEnableCharchat().equals("Y")){
            layoutBusiness=(LinearLayout)findViewById(R.id.layoutBusinee);
            layoutBusiness.setVisibility(LinearLayout.GONE);
        }else {
            btnVedioChat=(TextView)findViewById(R.id.btnVedioChat);
            btnCharChat=(TextView)findViewById(R.id.btnCharChat);
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
        }

        btnFollow=(TextView)findViewById(R.id.btnFollow);
        btnFollow.setOnClickListener(this);
        if(StaticVar.Member!=null){
            MemberFollowDoctorDao followdao=new MemberFollowDoctorDao(this);
            hasFollow=followdao.hasFollow(doctor.getId());
            setFollow();
        }

        txtIntroduce=(TextView)findViewById(R.id.txtIntroduce);
        txtIntroduce.setText(doctor.getIntroduce());

        txtCredentials=(TextView)findViewById(R.id.txtCredentials);
        txtCredentials.setText(doctor.getCredentials());

        txtExpert=(TextView)findViewById(R.id.txtExpert);
        txtExpert.setText(doctor.getExpert());


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
            btnFollow.setBackgroundColor(getResources().getColor(R.color.myyello));
            btnFollow.setText("已关注");
        }else {
            btnFollow.setBackgroundColor(getResources().getColor(R.color.myblue));
            btnFollow.setText("+关注");
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
            case R.id.btnVedioChat:
                Intent intent = new Intent(this, VideoChatOrderActivity.class);
                intent.putExtra("Id", doctor.getId());
                startActivity(intent);
                break;
            case R.id.btnCharChat:
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
