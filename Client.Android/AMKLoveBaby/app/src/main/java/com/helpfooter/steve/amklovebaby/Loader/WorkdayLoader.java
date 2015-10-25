package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorkDayObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorktimeObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/6.
 */
public class WorkdayLoader extends WebXmlLoader {
    int doctor_id;
    public WorkdayLoader(Context ctx, int doctor_id) {
        super(ctx, StaticVar.WorkdayApi);
        this.doctor_id=doctor_id;
    }



    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
       String url=StaticVar.dictHashMap.get(callApi);
       url= (url+"?doctor_id="+String.valueOf(doctor_id)).replace(" ", "%20");
       return url;
    }

    @Override
    public void doXml(ArrayList<HashMap<String, String>> lstRow) {
        ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
        for(HashMap<String,String> cols:lstRow){
            WorkDayObj obj=new WorkDayObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }

        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }
}
