package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

/**
 * Created by Steve on 2015/9/9.
 */
public class RegisterLoader extends ResultLoader {
    String mobile,password,verifycode;
    public RegisterLoader(Context ctx, String mobile,String verifycode,String password) {
        super(ctx, StaticVar.RegisterApi);
        this.mobile=mobile;
        this.verifycode=verifycode;
        this.password=password;
    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);
        url= (url+"?mobile="+mobile
        +"&verifycode="+verifycode
        +"&password="+ ToolsUtil.Encryption(password)
        +"&name="+mobile).replace(" ", "%20").replace("\n","%20");
        Log.i("ordercreate_url", url);

        return url;
    }
}
