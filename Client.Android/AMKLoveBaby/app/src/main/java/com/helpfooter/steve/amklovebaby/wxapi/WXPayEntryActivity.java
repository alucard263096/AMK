package com.helpfooter.steve.amklovebaby.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Loader.PaymentLoader;
import com.helpfooter.steve.amklovebaby.OrderPaymentActivity;
import com.helpfooter.steve.amklovebaby.PaymentSuccActivity;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private static final String APP_ID = "wxb8e507142a48adf4";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }



    @Override
    public void onResp(BaseResp resp) {
        //Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.errCode==0) {
            PaymentLoader loader = new PaymentLoader(this, StaticVar.orderid, StaticVar.Member.getId(), "WEIXIN");
            //loader.setCallBack(this);
            loader.start();
            Toast.makeText(WXPayEntryActivity.this, resp.errCode + "", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WXPayEntryActivity.this, PaymentSuccActivity.class);
            intent.putExtra("Id", StaticVar.orderid);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(WXPayEntryActivity.this,"支付失败。。。",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WXPayEntryActivity.this, OrderPaymentActivity.class);
            intent.putExtra("Id", StaticVar.orderid);
            startActivity(intent);
        }
    }

    public void onReq(BaseReq baseReq) {

    }


}
