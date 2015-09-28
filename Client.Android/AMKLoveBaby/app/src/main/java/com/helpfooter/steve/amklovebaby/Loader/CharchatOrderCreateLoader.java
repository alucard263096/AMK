package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/7.
 */
public class CharchatOrderCreateLoader extends WebXmlLoader {

    int doctor_id;
    int member_id;
    String name;
    String mobile;
    String description;

    public CharchatOrderCreateLoader(Context ctx, int doctor_id, int member_id, String name, String mobile, String description) {
        super(ctx, StaticVar.CharchatOrderCreateApi);
        this.doctor_id=doctor_id;
        this.member_id=member_id;
        this.name=name;
        this.mobile=mobile;
        this.description=description;
    }


    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url=StaticVar.dictHashMap.get(callApi);
        url= (url+"?doctor_id="+String.valueOf(doctor_id)
                +"&member_id="+String.valueOf(member_id)
                +"&name="+name
                +"&mobile="+mobile
                +"&description="+description).replace(" ", "%20").replace("\n","%20");
        Log.i("ordercreate_url",url);

        return url;
    }

    IWebLoaderCallBack callBack;
    public void setCallBack(IWebLoaderCallBack val){
        callBack=val;
    }

    @Override
    public void doXml(ArrayList<HashMap<String, String>> lstRow) {
        ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
        for(HashMap<String,String> cols:lstRow){
            ResultObj obj=new ResultObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }

        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }
}
