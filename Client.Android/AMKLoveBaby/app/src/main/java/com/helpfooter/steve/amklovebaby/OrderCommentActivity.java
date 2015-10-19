package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.MemberFollowDoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import org.w3c.dom.Text;


public class OrderCommentActivity extends Activity implements View.OnClickListener {

    DoctorObj doctor;
    OrderObj order;
    TextView btnFollow;
    boolean hasFollow=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_comment);

        InitData();
        InitUI();

    }

    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);

       ImageView imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        UrlImageLoader il=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());
        //il.run();

        TextView txtName=(TextView)findViewById(R.id.txtName);
        txtName.setText(doctor.getName());
        txtName.getPaint().setFakeBoldText(true);;

        TextView txtOfficeTitle=(TextView)findViewById(R.id.txtOfficeTitle);
        txtOfficeTitle.setText(doctor.getOffice() + "/" + doctor.getTitle());

        TextView txtWorktime=(TextView)findViewById(R.id.txtOpenHour);
        txtWorktime.setText("坐诊时间:"+doctor.getBookingtime());

        TextView txtVideoQuerycount=(TextView)findViewById(R.id.txtVideoQueryCount);
        txtVideoQuerycount.setText(String.valueOf("预约通话:" + doctor.getVideoquerycount())+"次");

        TextView txtCharQuerycount=(TextView)findViewById(R.id.txtCharQueryCount);
        txtCharQuerycount.setText(String.valueOf("图文咨询:"+doctor.getCharquerycount())+"次");

        TextView txtGeneralScore=(TextView)findViewById(R.id.txtGeneralScore);
        txtGeneralScore.setText(String.valueOf(doctor.getGeneralScore()));


        btnFollow=(TextView)findViewById(R.id.btnFollow);
        btnFollow.setOnClickListener(this);
        if(StaticVar.Member!=null){
            MemberFollowDoctorDao followdao=new MemberFollowDoctorDao(this);
            hasFollow=followdao.hasFollow(doctor.getId());
            setFollow();
        }

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
        OrderDao dao=new OrderDao(this);
        order=(OrderObj)dao.getObj(id);

        DoctorDao doctorDao=new DoctorDao(this);
        doctor=(DoctorObj)doctorDao.getObj(Integer.parseInt( order.getTag()));

    }

    @Override
    public void onClick(View v) {

    }
}
