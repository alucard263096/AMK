package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

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
public class PaymentLoader extends WebXmlLoader {
    int order_id,member_id;
    String payment_type;
    public PaymentLoader(Context ctx, int order_id, int member_id,String payment_type) {
        super(ctx, StaticVar.PaymentApi);
        this.order_id=order_id;
        this.member_id=member_id;
        this.payment_type=payment_type;
    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);
        url= (url+"?order_id="+String.valueOf(order_id)
                +"&member_id="+String.valueOf(member_id)
                +"&payment_type="+payment_type).replace(" ", "%20").replace("\n","%20");
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
            ResultObj obj=new ResultObj();
            obj.parseXmlDataTable(cols);
            lsObj.add(obj);
        }

        if(callBack!=null){
            callBack.CallBack(lsObj);
        }
    }
}
