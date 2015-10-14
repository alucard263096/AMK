package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/9.
 */
public abstract class ResultLoader extends WebXmlLoader {
    public ResultLoader(Context ctx, String defaultCallApi) {
        super(ctx, defaultCallApi);
    }

    @Override
    abstract public String getCallUrl();


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
