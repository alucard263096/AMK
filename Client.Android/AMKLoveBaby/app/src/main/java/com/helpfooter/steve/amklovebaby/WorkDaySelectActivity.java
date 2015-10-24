package com.helpfooter.steve.amklovebaby;

import android.content.Intent;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorkDayObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.WorkdayLoader;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/10/24.
 */
public class WorkDaySelectActivity extends SelectActivity implements IWebLoaderCallBack {

    @Override
    public void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        WorkdayLoader loader=new WorkdayLoader(this,id);
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        for(AbstractObj obj:lstObjs){
            WorkDayObj wd=(WorkDayObj)obj;
            if(wd.getTotalCount()>wd.getUsedCount()){
                lst.add(wd);
            }
        }
        DiaplayListHandler.sendEmptyMessage(0);
    }
}
