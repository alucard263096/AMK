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
 * Created by Steve on 2015/9/8.
 */
public class LoginSendLoader extends WebXmlLoader {

String mobile;
    public LoginSendLoader(Context ctx, String mobile) {
        super(ctx, StaticVar.LoginSendApi);
        this.mobile=mobile;
    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);
        url= (url+"?mobile="+mobile).replace(" ", "%20").replace("\n","%20");
        Log.i("ordercreate_url", url);

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
