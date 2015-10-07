package com.helpfooter.steve.amklovebaby.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.PaymentSuccActivity;
import com.helpfooter.steve.amklovebaby.Utils.SignUtils;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Steve on 2015/9/24.
 */
public class AlipayMgr {
    Activity ctx;
    OrderObj order;
    public AlipayMgr(Activity ctx,OrderObj order){
        this.ctx=ctx;
        this.order=order;
    }

    public static final String PARTNER = StaticVar.AlipayPartnerId;
    public static final String SELLER = StaticVar.AlipaySellerId;
    public static final String RSA_PRIVATE = StaticVar.AlipayRSA;
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent = new Intent(ctx, PaymentSuccActivity.class);
                        ctx.startActivity(intent);
                    } else {

                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ctx, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ctx, "支付失败,请检查支付宝状态和网络",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(ctx, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this.ctx)
                    .setTitle("警告")
                    .setMessage("需要配置")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    ctx.finish();
                                }
                            }).show();
            return;
        }

        String orderInfo = getOrderInfo();

        String sign = sign(orderInfo);
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    PayTask alipay = new PayTask(ctx);
                    String result = alipay.pay(payInfo);

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }catch (Exception ex){
                 ex.printStackTrace();
                }
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    public void check(View v) {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask payTask = new PayTask(ctx);
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    public void getSDKVersion() {
        PayTask payTask = new PayTask(ctx);
        String version = payTask.getVersion();
        Toast.makeText(ctx, version, Toast.LENGTH_SHORT).show();
    }

    public String getOrderInfo() {

        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        orderInfo += "&out_trade_no=" + "\"" + order.getOrder_no() + "\"";

        orderInfo += "&subject=" + "\"" + order.getActName() + "\"";

        orderInfo += "&body=" + "\"" + order.getActDescription() + "\"";

        orderInfo += "&total_fee=" + "\"" + "0.01" + "\"";

        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        orderInfo += "&service=\"mobile.securitypay.pay\"";

        orderInfo += "&payment_type=\"1\"";

        orderInfo += "&_input_charset=\"utf-8\"";


        orderInfo += "&it_b_pay=\"30m\"";

        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        //orderInfo += "&return_url=\"m.alipay.com\"";

        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


}
