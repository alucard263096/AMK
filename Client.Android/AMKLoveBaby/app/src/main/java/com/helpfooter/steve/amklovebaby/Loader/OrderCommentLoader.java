package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/7.
 */
public class OrderCommentLoader extends ResultLoader {
    int order_id,service,ability,doctor_id;
    String comment;
    public OrderCommentLoader(Context ctx,int order_id,int doctor_id, int service, int ability,String comment) {
        super(ctx, StaticVar.OrderCommentApi);
        this.order_id=order_id;
        this.service=service;
        this.ability=ability;
        this.comment=comment;
    }



    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);
        try {
            url= (url+"?order_id="+String.valueOf(order_id)
                    +"&doctor_id="+String.valueOf(doctor_id)
                    +"&service="+String.valueOf(service)
                    +"&ability="+String.valueOf(ability)
                    +"&comment="+java.net.URLEncoder.encode(comment, "utf-8")).replace(" ", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("orderget_url", url);
        return url;
    }

}
