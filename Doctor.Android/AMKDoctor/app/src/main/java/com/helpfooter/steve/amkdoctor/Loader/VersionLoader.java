
package com.helpfooter.steve.amkdoctor.Loader;

import android.content.Context;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.VersionObj;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/10/8.
 */
public class VersionLoader extends  WebXmlLoader {

    public VersionLoader(Context ctx) {
        super(ctx, StaticVar.VersionApi);
    }



    @Override
    public void doXml(ArrayList<HashMap<String, String>> lstRow) {
        ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
        for(HashMap<String,String> cols:lstRow){
            VersionObj obj=new VersionObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }

        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }
}
