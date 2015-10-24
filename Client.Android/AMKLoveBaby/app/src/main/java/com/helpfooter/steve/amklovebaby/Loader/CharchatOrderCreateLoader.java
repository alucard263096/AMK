package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/7.
 */
public class CharchatOrderCreateLoader extends ResultLoader {

    int doctor_id;
    int member_id;
    String name;
    String age;
    String sex;
    String description;

    public CharchatOrderCreateLoader(Context ctx, int doctor_id, int member_id, String name,String age,String sex, String description) {
        super(ctx, StaticVar.CharchatOrderCreateApi);
        this.doctor_id=doctor_id;
        this.member_id=member_id;
        this.name=name;
        this.age=age;
        this.sex=sex;
        this.description=description;
    }


    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url=StaticVar.dictHashMap.get(callApi);
        try {
            url= (url+"?doctor_id="+String.valueOf(doctor_id)
                    +"&member_id="+String.valueOf(member_id)
                    +"&name="+java.net.URLEncoder.encode(name,"utf-8")
                    +"&age="+age
                    +"&sex="+sex
                    +"&description="+java.net.URLEncoder.encode(description,"utf-8")).replace(" ", "%20").replace("\n","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("ordercreate_url",url);

        return url;
    }


}
