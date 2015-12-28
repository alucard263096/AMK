package com.helpfooter.steve.amkdoctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.CustomControlView.MemberPhotoLoadView;
import com.helpfooter.steve.amkdoctor.DAO.DoctorDao;
import com.helpfooter.steve.amkdoctor.DAO.MemberDao;
import com.helpfooter.steve.amkdoctor.DAO.MessageDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MemberObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MessageObj;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.MemberLoader;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MemberInfoActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack {
    MemberObj member;
    ImageView btnBack,imgPhoto;
    TextView txtName,txtSex,txtAge;
    TextView txtIntroduce,txtCredentials,txtExpert,btnFollow;
    LinearLayout layoutBusiness;
    LinearLayout lstMemberPhotos;
    boolean hasload=false;
    int memberid;
    String age;
    String sex;
    boolean hasFollow=false;
    MemberLoader ml;
    public ArrayList<MemberObj> lstMembers=new ArrayList<MemberObj>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.member_info);

        InitData();
        InitUI();
    }

    private void InitUI() {
        if(member==null)
        {
            return;
        }
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        UrlImageLoader il=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"member/"+member.getPhoto());
        il.start();
        txtName=(TextView)findViewById(R.id.txtName);
        txtName.setText(member.getName());
        txtName.getPaint().setFakeBoldText(true);;

        txtSex=(TextView)findViewById(R.id.txtSex);
        if(sex.trim().equals("M"))
        {
            txtSex.setText("男");
        }
        else
        {
            txtSex.setText("女");
        }


        txtAge=(TextView)findViewById(R.id.txtAge);
        txtAge.setText("年龄:" + age);

        lstMemberPhotos=(LinearLayout)findViewById(R.id.lstMemberPhotos);
        if(hasload==false) {
            try {
                MemberPhotoLoadView loadView = new MemberPhotoLoadView(this, memberid, lstMemberPhotos);
                loadView.LoadPhotoList();
                hasload = true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        MemberDao dao = new MemberDao(this);
        if(lstObjs!=null && lstObjs.size()>0)
        member=(MemberObj)lstObjs.toArray()[0];



        if (member !=null) {
            onloadMemberHandler.sendEmptyMessage(0);
        }
    }

    private Handler onloadMemberHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            OnloadMember();
        }
    };
    private void OnloadMember() {
        InitUI();
    }



        private void InitData() {
        Intent intent = getIntent();
        memberid = Integer.parseInt(intent.getStringExtra("id"));
        age = intent.getStringExtra("age");
         sex = intent.getStringExtra("sex");
        MemberDao dao=new MemberDao(this);
        member=(MemberObj)dao.getObj(memberid);
        if(member==null)
        {

            ml = new MemberLoader(this,String.valueOf(memberid));
            ml.setCallBack(MemberInfoActivity.this);
            MemberListThread  memberlistThread = new MemberListThread();
            memberlistThread.start();

        }
    }

   class MemberListThread extends Thread{
        //运行状态，下一步骤有大用

        public void run() {

                try {
                    ml.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

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
