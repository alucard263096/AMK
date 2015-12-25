package com.helpfooter.steve.amklovebaby;

import android.content.Intent;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorkDayObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorktimeObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.WorkdayLoader;
import com.helpfooter.steve.amklovebaby.Loader.WorktimeLoader;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/10/24.
 */
public class WorkTimeSelectActivity extends SelectActivity implements IWebLoaderCallBack {

    @Override
    public void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        String date = intent.getStringExtra("Date");
        WorktimeLoader loader=new WorktimeLoader(this,id,date);
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        for(AbstractObj obj:lstObjs){
            WorktimeObj wd=(WorktimeObj)obj;
            lst.add(wd);
        }
        DiaplayListHandler.sendEmptyMessage(0);
    }
}
