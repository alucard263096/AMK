package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/7.
 */
public class OrderLoader extends WebXmlLoader {
    int order_id,member_id;
    public OrderLoader(Context ctx,int order_id,int member_id) {
        super(ctx, StaticVar.OrderGetApi);
        this.order_id=order_id;
        this.member_id=member_id;
    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);
        url= (url+"?order_id="+String.valueOf(order_id)
                +"&member_id="+String.valueOf(member_id)).replace(" ", "%20").replace("\n","%20");
        Log.i("orderget_url", url);
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
        }

        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }
}
