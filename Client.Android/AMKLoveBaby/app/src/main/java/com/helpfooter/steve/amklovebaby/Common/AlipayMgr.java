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

    // �̻�PID
    public static final String PARTNER = StaticVar.AlipayPartnerId;
    // �̻��տ��˺�
    public static final String SELLER = StaticVar.AlipaySellerId;
    // �̻�˽Կ��pkcs8��ʽ
    public static final String RSA_PRIVATE = "";
    // ֧������Կ
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent = new Intent(ctx, PaymentSuccActivity.class);
                        ctx.startActivity(intent);
                    } else {
                        // �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
                        // ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ctx, "֧�����ȷ����",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
                            Toast.makeText(ctx, "֧��ʧ��",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(ctx, "�����Ϊ��" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };
    /**
     * call alipay sdk pay. ����SDK֧��
     *
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this.ctx)
                    .setTitle("����")
                    .setMessage("Need to deploy")
                    .setPositiveButton("ȷ��",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    ctx.finish();
                                }
                            }).show();
            return;
        }
        // ����
        String orderInfo = getOrderInfo();

        // �Զ�����RSA ǩ��
        String sign = sign(orderInfo);
        try {
            // �����sign ��URL����
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // �����ķ���֧���������淶�Ķ�����Ϣ
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // ����PayTask ����
                PayTask alipay = new PayTask(ctx);
                // ����֧���ӿڣ���ȡ֧�����
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // �����첽����
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * ��ѯ�ն��豸�Ƿ����֧������֤�˻�
     *
     */
    public void check(View v) {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // ����PayTask ����
                PayTask payTask = new PayTask(ctx);
                // ���ò�ѯ�ӿڣ���ȡ��ѯ���
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

    /**
     * get the sdk version. ��ȡSDK�汾��
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(ctx);
        String version = payTask.getVersion();
        Toast.makeText(ctx, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. ����������Ϣ
     *
     */
    public String getOrderInfo() {

        // ǩԼ���������ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // ǩԼ����֧�����˺�
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // �̻���վΨһ������
        orderInfo += "&out_trade_no=" + "\"" + order.getOrder_no() + "\"";

        // ��Ʒ����
        orderInfo += "&subject=" + "\"" + order.getActName() + "\"";

        // ��Ʒ����
        orderInfo += "&body=" + "\"" + order.getActDescription() + "\"";

        // ��Ʒ���
        orderInfo += "&total_fee=" + "\"" + "0.01" + "\"";

        // �������첽֪ͨҳ��·��
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // ����ӿ����ƣ� �̶�ֵ
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // ֧�����ͣ� �̶�ֵ
        orderInfo += "&payment_type=\"1\"";

        // �������룬 �̶�ֵ
        orderInfo += "&_input_charset=\"utf-8\"";

        // ����δ����׵ĳ�ʱʱ��
        // Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
        // ȡֵ��Χ��1m��15d��
        // m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
        // �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
        //orderInfo += "&return_url=\"m.alipay.com\"";

        // �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. �����̻������ţ���ֵ���̻���Ӧ����Ψһ�����Զ����ʽ�淶��
     *
     */
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

    /**
     * sign the order info. �Զ�����Ϣ����ǩ��
     *
     * @param content
     *            ��ǩ��������Ϣ
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. ��ȡǩ����ʽ
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


}
