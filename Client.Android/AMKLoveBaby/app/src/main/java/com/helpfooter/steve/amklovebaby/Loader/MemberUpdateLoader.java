package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/9.
 */
public class MemberUpdateLoader extends ResultLoader {
     int member_id;
    String field;
    String value;
    public MemberUpdateLoader(Context ctx, int member_id,String field,String value) {
        super(ctx, StaticVar.MemberUpdateApi);
        this.member_id=member_id;
        this.field=field;
        this.value=value;
    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);
        try {
            url= (url+"?member_id="+String.valueOf(member_id)
                    +"&field="+java.net.URLEncoder.encode(field, "utf-8")
                    +"&value="+java.net.URLEncoder.encode(value,"utf-8")).replace(" ", "%20").replace("\n","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("ordercreate_url", url);

        return url;
    }

}
