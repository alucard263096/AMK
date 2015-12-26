package com.helpfooter.steve.amklovebaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.DoctorListLoadView;
import com.helpfooter.steve.amklovebaby.CustomControlView.SlowScrollView;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;

import java.util.ArrayList;

public class DoctorListActivity extends MyActivity implements View.OnClickListener {

    String name="";
    String search="";
    SlowScrollView scrollview;
    DoctorListLoadView lstLoad;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_doctor_list);
        }
        catch(Exception ex)
        {
            throw ex;
        }

        InitData();
        InitUI();
    }

    private void InitData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        search = intent.getStringExtra("search");
    }

    private void InitUI() {

        if(name.length() >= 2){
            TextView txtTitle = ((TextView) findViewById(R.id.title));
            txtTitle.setText(name);
            txtTitle.getPaint().setFakeBoldText(true);
        }
        if(search.length()>2){
            search=" and "+search;
        }

        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        scrollview=(SlowScrollView)findViewById(R.id.myscrollview);
        lstLoad=new DoctorListLoadView(DoctorListActivity.this,(LinearLayout)findViewById(R.id.doctor_list),search);
        lstLoad.LoadDoctorListData(lstLoad.lstDoctor);


// 滑动加载
        scrollview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:
                        index++;
                        break;
                    default:
                        break;
                }
                if (event.getAction() == MotionEvent.ACTION_UP && index > 0) {
                    index = 0;
                    View view = ((SlowScrollView) v).getChildAt(0);
                    if (view.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
                        //加载数据代码
                        ArrayList<DoctorObj> addlstDoctor= new ArrayList<DoctorObj>();
                        int iCount=0;
                        for(AbstractObj obj:lstLoad.lst){

                            if(iCount<lstLoad.rowCount)
                            {
                                iCount++;
                                continue;
                            }
                            iCount++;
                            addlstDoctor.add((DoctorObj)obj);
                            lstLoad.rowCount++;
                            if(addlstDoctor.size()>10 || lstLoad.lst.size()==lstLoad.rowCount)
                            {
                                lstLoad.LoadDoctorListData(addlstDoctor);
                                break;
                            }
                        }
                    }
                }
                return false;
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                return;
        }
    }
}
