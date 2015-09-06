package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import org.w3c.dom.Text;


public class DoctorDetailActivity extends Activity implements View.OnClickListener {
    DoctorObj doctor;
    ImageView btnBack,imgPhoto;
    TextView txtName,txtFocus,txtOfficeTitle,txtWorktime,txtVideoQuerycount,txtCharQuerycount,txtGeneralScore,btnVedioChat,btnCharChat;
    TextView txtIntroduce,txtCredentials,txtExpert;
    LinearLayout layoutBusiness;

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
        UrlImageLoader il=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());

        txtName=(TextView)findViewById(R.id.txtName);
        txtName.setText(doctor.getName());
        txtName.getPaint().setFakeBoldText(true);;

        txtOfficeTitle=(TextView)findViewById(R.id.txtOfficeTitle);
        txtOfficeTitle.setText(doctor.getOffice() + "/" + doctor.getTitle());

        txtWorktime=(TextView)findViewById(R.id.txtOpenHour);
        txtWorktime.setText("坐诊时间:"+doctor.getBookingtime());

        txtVideoQuerycount=(TextView)findViewById(R.id.txtVideoQueryCount);
        txtVideoQuerycount.setText(String.valueOf("预约通话:" + doctor.getVideoquerycount())+"次");

        txtCharQuerycount=(TextView)findViewById(R.id.txtCharQueryCount);
        txtCharQuerycount.setText(String.valueOf("图文咨询:"+doctor.getCharquerycount())+"次");

        txtGeneralScore=(TextView)findViewById(R.id.txtGeneralScore);
        txtGeneralScore.setText(String.valueOf(doctor.getGeneralScore()));

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
            }else{
                btnCharChat.setVisibility(LinearLayout.GONE);
            }
        }

        txtIntroduce=(TextView)findViewById(R.id.txtIntroduce);
        txtIntroduce.setText(doctor.getIntroduce());

        txtCredentials=(TextView)findViewById(R.id.txtCredentials);
        txtCredentials.setText(doctor.getCredentials());

        txtExpert=(TextView)findViewById(R.id.txtExpert);
        txtExpert.setText(doctor.getExpert());
    }

    private void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
        doctor=(DoctorObj)dao.getObj(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnVedioChat:
                Intent intent = new Intent(this, VideoChatOrderActivity.class);
                intent.putExtra("Id", doctor.getId());
                startActivity(intent);
        }
    }
}
