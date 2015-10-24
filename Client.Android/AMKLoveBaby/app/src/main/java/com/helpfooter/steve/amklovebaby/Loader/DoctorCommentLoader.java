package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.CommentObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by scai on 2015/9/15.
 */
public class DoctorCommentLoader extends WebXmlLoader {
    int doctor_id;
    int count;
    public DoctorCommentLoader(Context ctx,int doctor_id,int count) {
        super(ctx, StaticVar.DoctorCommentApi);

        this.doctor_id=doctor_id;
        this.count=count;


    }
    @Override
    public String getCallUrl() {
        //String url=super.getCallUrl();
        String url=StaticVar.dictHashMap.get(callApi);
        url+="?doctor_id="+String.valueOf(doctor_id);
        url+="&count="+String.valueOf(count);
        url=url.replace("\n","%20");
        return url;
    }


    @Override
    public void doXml(ArrayList<HashMap<String, String>> lstRow) {

        ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
        for(HashMap<String,String> cols:lstRow){
            CommentObj obj=new CommentObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }


            if(callBack!=null){
                callBack.CallBack(lsObj);
            }
    }
}
