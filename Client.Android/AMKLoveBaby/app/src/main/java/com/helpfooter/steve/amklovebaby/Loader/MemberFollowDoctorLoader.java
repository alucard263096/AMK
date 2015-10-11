package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.MemberFollowDoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberFollowDoctorObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/10/11.
 */
public class MemberFollowDoctorLoader extends WebXmlLoader  {

    int member_id;
    public MemberFollowDoctorLoader(Context ctx,int member_id) {
        super(ctx, StaticVar.DoctorFollowAPI);
        this.member_id=member_id;
    }


    @Override
    public String getCallUrl() {
        String url=super.getCallUrl();
        url+="&member_id="+String.valueOf(member_id);
        return url;
    }

    @Override
    public void doXml(ArrayList<HashMap<String, String>> lstRow) {
        String updatedate="";
        ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
        for(HashMap<String,String> cols:lstRow){
            MemberFollowDoctorObj obj=new MemberFollowDoctorObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }
        if(lsObj.size()>0){
            MemberFollowDoctorDao dao=new MemberFollowDoctorDao(ctx);
            dao.batchUpdate(lsObj);
        }
        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }
}
