package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.AlipayMgr;
import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.DataObjs.ResultObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.OrderLoader;
import com.helpfooter.steve.amklovebaby.Loader.PaymentLoader;
import com.helpfooter.steve.amklovebaby.Loader.VideochatOrderCreateLoader;
import com.helpfooter.steve.amklovebaby.Utils.MD5;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class OrderPaymentActivity extends MyActivity implements View.OnClickListener,IWebLoaderCallBack{
    int order_id=0;
    OrderObj order;
    ResultObj res;

    TextView txtPaymentType;

    private IWXAPI api;
    //微信公众平台id;
    private String APP_ID="wxd930ea5d5a258f4f";
    //微信开放平台和商户约定的密钥
    private String APP_SECRET="db426a9829e4b49a0dcac7b4162da6b6";
    //微信公众平台商户模块和商户约定的密钥
    private String PARTNER_KEY="8934e7d15453e97507ef794cf7b0519d";
    //微信公众平台商户模块和商户约定的支付密钥
    private String APP_KEY="L8LrMqqeGRxST5reouB0K66CaYAWpqhAVsq7ggKkxHCOastWksvuX1uvmvQclxaHoYd3ElNBrNO2DHnnzgfVG9Qs473M3DTOZug5er46FhuGofumV8H2FVR9qkjSlC5K";
    // 商家向财付通申请的商家id */
    private String app_tx_parent_key = "1900000109";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);
        if(MemberMgr.CheckIsLogin(this)) {
            ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
            InitData();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(StaticVar.Member==null){
            this.finish();
            return;
        }
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        InitData();
    }

    private void InitUI() {

        Button btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setBackgroundResource(R.color.myblue);

        ((TextView)findViewById(R.id.txtPrice)).setText(String.valueOf(order.getPrice()) + "元");

        txtPaymentType=((TextView)findViewById(R.id.txtPaymentType));
        txtPaymentType.setOnClickListener(this);
    }

    private void InitData() {
        Intent intent = getIntent();
        order_id = intent.getIntExtra("Id", 0);

        OrderLoader loader=new OrderLoader(this,order_id, StaticVar.Member.getId());
        loader.setCallBack(this);
        loader.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.txtPaymentType:
                Intent intent = new Intent(this, PaymentTypeSelectActivity.class);
                startActivityForResult(intent, 2);
                return;
            case R.id.btnSubmit:
                //AlipayMgr mgr=new AlipayMgr(this,order);
                //mgr.pay(v);

                String payment_type=(String)txtPaymentType.getTag();
                if(payment_type.length()<1){
                    Toast.makeText(this,"请选择一种支付方式",Toast.LENGTH_LONG).show();
                    return;
                }
                if(payment_type.equals("TEST")) {
                    action = "PAYMENT";
                    PaymentLoader loader = new PaymentLoader(this, order_id, StaticVar.Member.getId(), "ALIPAY");
                    loader.setCallBack(this);
                    loader.start();
                }
                else if(payment_type.equals("WEIXIN"))
                {
                    api = WXAPIFactory.createWXAPI(this, APP_KEY);
                    new GetAccessTokenTask().execute();
                }
                else {
                    Toast.makeText(this,"此支付方式暂未开通",Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=-1){
            return;
        }
        String retstr=data.getStringExtra("return");
        String returnName=data.getStringExtra("returnName");
        if(requestCode == 2){
            txtPaymentType.setText(returnName);
            txtPaymentType.setTag(retstr);
        }
    }


    private android.os.Handler uiInitHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(order==null){
                Toast.makeText(OrderPaymentActivity.this, "订单加载失败，请检查网络", Toast.LENGTH_LONG).show();
            }else {
                InitUI();
            }
        }
    };

    private android.os.Handler paymentHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(res==null){
                Toast.makeText(OrderPaymentActivity.this, "订单支付失败", Toast.LENGTH_LONG).show();
            }else {
                switch (res.getId()){
                    case -103:
                        Toast.makeText(OrderPaymentActivity.this, "订单已经支付", Toast.LENGTH_LONG).show();
                        return;
                    case 0:
                        Intent intent = new Intent(OrderPaymentActivity.this, PaymentSuccActivity.class);
                        intent.putExtra("Id", order_id);
                        startActivity(intent);
                        return;
                    default:
                        Toast.makeText(OrderPaymentActivity.this, "订单支付失败", Toast.LENGTH_LONG).show();
                        return;
                }
            }
        }
    };

    String action="LOADORDER";
    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        if(action.equals("LOADORDER")) {
            if (lstObjs.size() > 0) {
                order = (OrderObj) lstObjs.get(0);
            }
            uiInitHandler.sendEmptyMessage(0);
        }else if(action.equals("PAYMENT")){
            if (lstObjs.size() > 0) {
                res = (ResultObj) lstObjs.get(0);
            }
            paymentHandler.sendEmptyMessage(0);
        }
    }

<<<<<<< HEAD
    //微信支付
    private class GetAccessTokenTask extends AsyncTask<Void, Void, GetAccessTokenResult> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(GetAccessTokenResult result) {
            if (dialog != null) {
                dialog.dismiss();
            }

            if (result.localRetCode == LocalRetCode.ERR_OK) {



                GetPrepayIdTask getPrepayId = new GetPrepayIdTask(result.accessToken);
                getPrepayId.execute();
            } else {
                Toast.makeText(OrderPaymentActivity.this,"获取access token失败", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected GetAccessTokenResult doInBackground(Void... params) {
            GetAccessTokenResult result = new GetAccessTokenResult();

            String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                    APP_ID, APP_SECRET);


            byte[] buf = ToolsUtil.httpGet(url);
            if (buf == null || buf.length == 0) {
                result.localRetCode = LocalRetCode.ERR_HTTP;
                return result;
            }

            String content = new String(buf);
            result.parseFrom(content);
            return result;
        }
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, GetPrepayIdResult> {

        private ProgressDialog dialog;
        private String accessToken;

        public GetPrepayIdTask(String accessToken) {
            this.accessToken = accessToken;
        }

        @Override
        protected void onPreExecute() {
            //dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(GetPrepayIdResult result) {
            if (dialog != null) {
                dialog.dismiss();
            }

            if (result.localRetCode == LocalRetCode.ERR_OK) {
               // Toast.makeText(PayActivity.this, R.string.get_prepayid_succ, Toast.LENGTH_LONG).show();
                sendPayReq(result);
            } else {
                Toast.makeText(OrderPaymentActivity.this, "获取prepayid失败", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected GetPrepayIdResult doInBackground(Void... params) {

            String url = String.format("https://api.weixin.qq.com/pay/genprepay?access_token=%s", accessToken);
            String entity = genProductArgs();

            Log.d("", "doInBackground, url = " + url);
            Log.d("", "doInBackground, entity = " + entity);

            GetPrepayIdResult result = new GetPrepayIdResult();

            byte[] buf = ToolsUtil.httpPost(url, entity);
            if (buf == null || buf.length == 0) {
                result.localRetCode = LocalRetCode.ERR_HTTP;
                return result;
            }

            String content = new String(buf);
            Log.d("", "doInBackground, content = " + content);
            result.parseFrom(content);
            return result;
        }
    }

    private static enum LocalRetCode {
        ERR_OK, ERR_HTTP, ERR_JSON, ERR_OTHER
    }

    private static class GetAccessTokenResult {

        private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetAccessTokenResult";

        public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
        public String accessToken;
        public int expiresIn;
        public int errCode;
        public String errMsg;

        public void parseFrom(String content) {

            if (content == null || content.length() <= 0) {
                Log.e(TAG, "parseFrom fail, content is null");
                localRetCode = LocalRetCode.ERR_JSON;
                return;
            }

            try {
                JSONObject json = new JSONObject(content);
                if (json.has("access_token")) { // success case
                    accessToken = json.getString("access_token");
                    expiresIn = json.getInt("expires_in");
                    localRetCode = LocalRetCode.ERR_OK;
                } else {
                    errCode = json.getInt("errcode");
                    errMsg = json.getString("errmsg");
                    localRetCode = LocalRetCode.ERR_JSON;
                }

            } catch (Exception e) {
                localRetCode = LocalRetCode.ERR_JSON;
            }
        }
    }

    private static class GetPrepayIdResult {

        private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetPrepayIdResult";

        public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
        public String prepayId;
        public int errCode;
        public String errMsg;

        public void parseFrom(String content) {

            if (content == null || content.length() <= 0) {
                Log.e("", "parseFrom fail, content is null");
                localRetCode = LocalRetCode.ERR_JSON;
                return;
            }

            try {
                JSONObject json = new JSONObject(content);
                if (json.has("prepayid")) { // success case
                    prepayId = json.getString("prepayid");
                    localRetCode = LocalRetCode.ERR_OK;
                } else {
                    localRetCode = LocalRetCode.ERR_JSON;
                }

                errCode = json.getInt("errcode");
                errMsg = json.getString("errmsg");

            } catch (Exception e) {
                localRetCode = LocalRetCode.ERR_JSON;
            }
        }
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * ���� traceid �ֶΰ����û���Ϣ��������Ϣ����������Զ���״̬�Ĳ�ѯ�͸���
     */
    private String getTraceId() {
        return "crestxu_" + genTimeStamp();
    }

    /**
     * ע�⣺�̻�ϵͳ�ڲ��Ķ�����,32���ַ��ڡ��ɰ�����ĸ,ȷ�����̻�ϵͳΨһ
     */
    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long timeStamp;
    private String nonceStr, packageValue;

    private String genSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (; i < params.size() - 1; i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append(params.get(i).getName());
        sb.append('=');
        sb.append(params.get(i).getValue());

        String sha1 = ToolsUtil.sha1(sb.toString());
        Log.d("", "genSign, sha1 = " + sha1);
        return sha1;
    }

    private String genProductArgs() {
        JSONObject json = new JSONObject();

        try {
            json.put("appid", APP_ID);
            String traceId = getTraceId();  // traceId �ɿ������Զ��壬�����ڶ����Ĳ�ѯ����٣��������֧���û���Ϣ���ɴ�id
            json.put("traceid", traceId);
            nonceStr = genNonceStr();
            json.put("noncestr", nonceStr);

            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("bank_type", "WX"));
            packageParams.add(new BasicNameValuePair("body", "阿米卡支付测试"));
            packageParams.add(new BasicNameValuePair("fee_type", "1"));
            packageParams.add(new BasicNameValuePair("input_charset", "UTF-8"));
            packageParams.add(new BasicNameValuePair("notify_url", "http://weixin.qq.com"));
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("partner", "1900000109"));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "196.168.1.1"));
            packageParams.add(new BasicNameValuePair("total_fee", "1"));
            packageValue = genPackage(packageParams);

            json.put("package", packageValue);
            timeStamp = genTimeStamp();
            json.put("timestamp", timeStamp);

            List<NameValuePair> signParams = new LinkedList<NameValuePair>();
            signParams.add(new BasicNameValuePair("appid", APP_ID));
            signParams.add(new BasicNameValuePair("appkey", APP_KEY));
            signParams.add(new BasicNameValuePair("noncestr", nonceStr));
            signParams.add(new BasicNameValuePair("package", packageValue));
            signParams.add(new BasicNameValuePair("timestamp", String.valueOf(timeStamp)));
            signParams.add(new BasicNameValuePair("traceid", traceId));
            json.put("app_signature", genSign(signParams));

            json.put("sign_method", "sha1");
        } catch (Exception e) {
            Log.e("", "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }

        return json.toString();
    }

    private void sendPayReq(GetPrepayIdResult result) {

        PayReq req = new PayReq();
        req.appId = APP_ID;
        req.partnerId = PARTNER_KEY;
        req.prepayId = result.prepayId;
        req.nonceStr = nonceStr;
        req.timeStamp = String.valueOf(timeStamp);
        req.packageValue = "Sign=" + packageValue;

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("appkey", APP_KEY));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genSign(signParams);

        // ��֧��֮ǰ�����Ӧ��û��ע�ᵽ΢�ţ�Ӧ���ȵ���IWXMsg.registerApp��Ӧ��ע�ᵽ΢��
        api.sendReq(req);
    }

    private String genPackage(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(PARTNER_KEY); // ע�⣺����hardcode�ڿͻ��ˣ�����genPackage������̶��ɷ����������

        // ����md5ժҪǰ��params����Ϊԭʼ���ݣ�δ����url encode����
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();

        return URLEncodedUtils.format(params, "utf-8") + "&sign=" + packageSign;
=======
    public boolean PopupNotice(){
        return false;
>>>>>>> 970b7c7344d4788bdb2b0def47bc3fedf3507cd7
    }
}
