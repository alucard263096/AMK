package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorktimeObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/6.
 */
public class WorktimeLoader extends WebXmlLoader {
    int doctor_id;
    String date;
    public WorktimeLoader(Context ctx,int doctor_id,String date) {
        super(ctx, StaticVar.WorktimeApi);
        this.doctor_id=doctor_id;
        this.date=date;
    }



    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
       String url=StaticVar.dictHashMap.get(callApi);
       url= (url+"?doctor_id="+String.valueOf(doctor_id)+"&date="+date).replace(" ", "%20");
       return url;
    }

    @Override
    public void doXml(ArrayList<HashMap<String, String>> lstRow) {
        ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
        for(HashMap<String,String> cols:lstRow){
            WorktimeObj obj=new WorktimeObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }

        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }
}
