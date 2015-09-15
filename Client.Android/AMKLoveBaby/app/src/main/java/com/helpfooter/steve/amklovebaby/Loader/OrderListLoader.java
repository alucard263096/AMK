package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DAO.NewsDao;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by scai on 2015/9/15.
 */
public class OrderListLoader extends WebXmlLoader {
    public OrderListLoader(Context ctx) {
        super(ctx, StaticVar.OrderListApi);
    }
    @Override
    public String getCallUrl() {
        String url=super.getCallUrl();
        int member_id=0;
        if(StaticVar.Member!=null){
            member_id=StaticVar.Member.getId();
        }
        url+="&member_id="+String.valueOf(member_id);
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
            OrderObj obj=new OrderObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }

        if(lsObj.size()>0){
            OrderDao dao=new OrderDao(ctx);
            dao.batchUpdate(lsObj);
            if(callBack!=null){
                callBack.CallBack(lsObj);
            }

            ParamsDao paramdao=new ParamsDao(this.ctx);
            paramdao.updateParam(this.callApi,StaticVar.GetSystemTimeString());
        }
    }
}
