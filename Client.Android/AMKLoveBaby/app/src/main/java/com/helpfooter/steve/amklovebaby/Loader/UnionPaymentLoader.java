package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

/**
 * Created by Steve on 2015/9/7.
 */
public class UnionPaymentLoader extends ResultLoader {
    String order_no;
    String payment_type;
    public UnionPaymentLoader(Context ctx, String orderno) {
        super(ctx,"UNIONPAY");
        this.order_no=orderno;

    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.UNIONPAYURL;
        url= (url+"?order_no="+order_no);
        Log.i("orderget_url", url);
        return url;
    }

}
