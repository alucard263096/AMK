<<<<<<< HEAD
package com.helpfooter.steve.amkdoctor.Loader;

import android.content.Context;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.VersionObj;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
=======
package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.DataObjs.VersionObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
>>>>>>> 485f68885b01d957d28846c7dc10760c56958746

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/10/8.
 */
public class VersionLoader extends  WebXmlLoader {

    public VersionLoader(Context ctx) {
        super(ctx, StaticVar.VersionApi);
    }

    IWebLoaderCallBack callBack;
    public void setCallBack(IWebLoaderCallBack val){
        callBack=val;
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
