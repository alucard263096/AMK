package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.CustomControlView.WorkTimeTableView;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CancellationException;


public class VideoChatOrderActivity extends Activity implements View.OnClickListener {

    DoctorObj doctor;
    ImageView btnBack,imgPhoto;
    TextView txtName,txtVideoPrice,txtOfficeTitle,txtVideoQuerycount,txtGeneralScore;
    TextView pickDate;
    Button btnSubmit;

    WorkTimeTableView timeTableView;

    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID=0;
    boolean firstrun=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat_order);

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
        txtName.getPaint().setFakeBoldText(true);

        txtOfficeTitle=(TextView)findViewById(R.id.txtOfficeTitle);
        txtOfficeTitle.setText(doctor.getOffice() + "/" + doctor.getTitle());

        txtVideoQuerycount=(TextView)findViewById(R.id.txtVideoQueryCount);
        txtVideoQuerycount.setText("预约通话:" +String.valueOf( doctor.getVideoquerycount())+"次");


        txtVideoPrice=(TextView)findViewById(R.id.txtPrice);
        txtVideoPrice.setText(String.valueOf(doctor.getVideochatPrice())+"元");

        txtGeneralScore=(TextView)findViewById(R.id.txtGeneralScore);
        txtGeneralScore.setText("综合评分："+String.valueOf(doctor.getGeneralScore()));

        pickDate=(TextView)findViewById(R.id.pickDate);
        pickDate.setOnClickListener(this);

        timeTableView=new WorkTimeTableView(this,doctor.getId(),(PercentLinearLayout)findViewById(R.id.layoutTimeTable));
        Calendar cal= getStartDateCalendar();
        mYear=cal.get(Calendar.YEAR);
        mMonth=cal.get(Calendar.MONTH);
        mDay=cal.get(Calendar.DAY_OF_MONTH);
        UpdateWorkTable();

        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }
    long lasttime=0;
    private void UpdateWorkTable() {
        if(System.currentTimeMillis()-lasttime>2000) {
            String date = String.valueOf(mYear) + "-" + String.valueOf(mMonth + 1) + "-" + String.valueOf(mDay);
            pickDate.setText("选择日期：" + date);
            timeTableView.LoadData(date);
            lasttime=System.currentTimeMillis();
        }
    }

    private void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
        doctor = (DoctorObj) dao.getObj(id);
    }

    private  DatePickerDialog.OnDateSetListener mDateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            Log.i("datapick","yes");
            UpdateWorkTable();
        }
    };

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG_ID:
                DatePickerDialog dialog=  new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);

                Calendar cal=getStartDateCalendar();
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                cal.add(Calendar.DAY_OF_MONTH, 30);
                dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());

                return dialog;
        }
        return null;
    }
    Calendar cal;
    public Calendar getStartDateCalendar(){
        if(cal!=null){
            return  cal;
        }
        cal=Calendar.getInstance();
        if(ToolsUtil.IsAfternoon()){
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return  cal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.pickDate:
                showDialog(DATE_DIALOG_ID);
                return;
            case R.id.btnSubmit:
                if(timeTableView.getOrderTime().length()<2){
                    Toast.makeText(this, "请选择预约时间", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(this, VedioOrderSubmitActivity.class);
                    intent.putExtra("Id", timeTableView.getDoctorId());
                    intent.putExtra("order_date", timeTableView.getOrderDate());
                    intent.putExtra("order_time", timeTableView.getOrderTime());
                    startActivity(intent);
                }
                return;
        }
    }
}
