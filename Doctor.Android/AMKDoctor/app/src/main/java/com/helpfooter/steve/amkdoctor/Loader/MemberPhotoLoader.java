package com.helpfooter.steve.amkdoctor.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amkdoctor.DAO.MemberPhotoDao;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MemberPhotoObj;

import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by scai on 2015/10/26.
 */
public class MemberPhotoLoader extends WebXmlLoader {
    int member_id;
    public MemberPhotoLoader(Context ctx,int member_id) {
        super(ctx, StaticVar.MemberPhotoApi);
        this.member_id=member_id;
    }

    @Override
    public void doXml(ArrayList<HashMap<String, String>> lstRow) {
        ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
        for(HashMap<String,String> cols:lstRow){
            MemberPhotoObj obj=new MemberPhotoObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }
        MemberPhotoDao dao=new MemberPhotoDao(ctx);
        dao.deleteTable();


        if(lsObj.size()>0){
            dao.batchUpdate(lsObj);
        }
        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }


    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);

        url= (url+"?member_id="+String.valueOf(member_id)).replace(" ", "%20").replace("\n","%20");
        return url;
    }


}
