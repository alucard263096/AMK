package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;

/**
 * Created by scai on 2015/10/26.
 */
public class ChartPhotoCancelLoader extends  ResultLoader {
    int order_id;
    int doctor_id;
    String old_message;
    public ChartPhotoCancelLoader(Context ctx, int orderid, int doctorid,String oldmessage) {
        super(ctx, StaticVar.PhotoCancelApi);
        this.order_id=orderid;
        this.doctor_id=doctorid;
        this.old_message=oldmessage;
    }

    @Override
    public String getCallUrl() {
        String url= StaticVar.dictHashMap.get(callApi);
        try {
            url= (url+"?order_id="+String.valueOf(order_id)
                    +"&doctor_id="+String.valueOf(doctor_id)
                    +"&old="+old_message
                    +"&new="+java.net.URLEncoder.encode("C:TXT:撤回一条记录","utf-8")).replace(" ", "%20").replace("\n","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }
}
