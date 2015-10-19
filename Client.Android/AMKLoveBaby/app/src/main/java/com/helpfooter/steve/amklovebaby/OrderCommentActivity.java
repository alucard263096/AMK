package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.MemberFollowDoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.OrderCommentLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class OrderCommentActivity extends Activity implements View.OnClickListener,
        IWebLoaderCallBack{

    DoctorObj doctor;
    OrderObj order;
    TextView btnFollow;
    boolean hasFollow=false;
    RatingBar rbService,rbAbility;
    EditText txtComment;
    Button btnSubmit;

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
        if(StaticVar.Member!=null){
            MemberFollowDoctorDao followdao=new MemberFollowDoctorDao(this);
            hasFollow=followdao.hasFollow(doctor.getId());
            setFollow();
        }

        rbService=(RatingBar)findViewById(R.id.rbService);
        rbAbility=(RatingBar)findViewById(R.id.rbAbility);
        txtComment=(EditText)findViewById(R.id.txtComment);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        if(order.getHascomment().equals("Y")){
            rbService.setRating(order.getService());
            rbService.setIsIndicator(true);
            rbAbility.setRating(order.getAbility());
            rbService.setIsIndicator(true);
            txtComment.setText(order.getComment());
            txtComment.setEnabled(false);
            btnSubmit.setText("返回");
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
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.btnSubmit:

                if(order.getHascomment().equals("Y")){
                    this.finish();
                    return;
                }else {
                    int service=(int)(rbService.getRating());
                    int ability=(int)(rbAbility.getRating());
                    if(service==0&&ability==0){
                        Toast.makeText(this,"请为我们勤劳的医生评个分吧",Toast.LENGTH_LONG).show();
                        return;
                    }
                    String comment=txtComment.getText().toString();
                    order.setService(service);
                    order.setAbility(ability);
                    order.setComment(comment);
                    OrderCommentLoader loader=new OrderCommentLoader(this,order.getId(),doctor.getId(),service,ability,comment);
                    loader.setCallBack(this);
                    loader.start();
                }

                return;
        }
    }

    ResultObj res;
    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(lstObjs.size()>0){
            res=(ResultObj)lstObjs.get(0);
            resultHandler.sendEmptyMessage(0);
        }
    }

    private android.os.Handler resultHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (res.getId()){
                case 0:
                    order.setHascomment("Y");
                    OrderDao dao=new OrderDao(OrderCommentActivity.this);
                    dao.updateObj(order);

                    rbService.setIsIndicator(true);
                    rbService.setIsIndicator(true);
                    txtComment.setEnabled(false);
                    btnSubmit.setText("返回");


                    Toast.makeText(OrderCommentActivity.this, "评价成功，谢谢", Toast.LENGTH_LONG).show();
                    return;
                default:
                    Toast.makeText(OrderCommentActivity.this, "评价失败，请联系管理员", Toast.LENGTH_LONG).show();
                    return;
            }
        }
    };
}
