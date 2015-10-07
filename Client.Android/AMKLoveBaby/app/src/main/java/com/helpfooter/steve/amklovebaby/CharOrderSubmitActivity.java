package com.helpfooter.steve.amklovebaby;

import android.content.Intent;
import android.widget.LinearLayout;

import com.helpfooter.steve.amklovebaby.CustomControlView.OrderSubmitInfoView;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Loader.CharchatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;


public class CharOrderSubmitActivity extends OrderSubmitActivity {

    DoctorObj doctor;

    protected void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        DoctorDao dao=new DoctorDao(this);
        doctor=(DoctorObj)dao.getObj(id);
    }

    boolean haveinit=false;
    protected void InitUI() {
        super.InitUI();
        LinearLayout linearLayout=(LinearLayout)this.findViewById(R.id.layoutMainInfo);
        if(haveinit==false) {
            OrderSubmitInfoView infoView = new OrderSubmitInfoView(this, linearLayout);

            infoView.AddDisplayField("预约医生", doctor.getName());
            infoView.AddDisplayField("预约费用", doctor.getCharchatPrice()+"元/次");
        }

        haveinit=true;

    }

    @Override
    void ClickSubmit() {
        CharchatOrderCreateLoader loader=new CharchatOrderCreateLoader(this,doctor.getId(), StaticVar.Member.getId(),txtName.getText().toString(),txtMobile.getText().toString(),txtDescription.getText().toString());
        loader.setCallBack(this);
        loader.start();
    }
}
