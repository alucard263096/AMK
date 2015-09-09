package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

/**
 * Created by Steve on 2015/9/9.
 */
public class RegSendLoader extends ResultLoader {
    String mobile;
    public RegSendLoader(Context ctx, String mobile) {
        super(ctx, StaticVar.RegisterSendApi);
        this.mobile=mobile;
    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);
        url= (url+"?mobile="+mobile).replace(" ", "%20").replace("\n","%20");
        Log.i("ordercreate_url", url);

        return url;
    }
}
