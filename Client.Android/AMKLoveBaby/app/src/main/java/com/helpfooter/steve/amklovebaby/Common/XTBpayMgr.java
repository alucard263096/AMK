package com.helpfooter.steve.amklovebaby.Common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Utils.MD5;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.Util;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import org.open.sdk.java.common.Constants;
import org.open.sdk.java.common.enums.OpenSdkSupportHttpMethod;
import org.open.sdk.java.service.OpenApiSinatureAccessService;
import org.open.sdk.java.tools.MyStringUtil;
import org.open.sdk.java.tools.SignatureUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by Steve on 2015/9/24.
 */
public class XTBpayMgr {
    Activity ctx;
    OrderObj order;
    public XTBpayMgr(Activity ctx, OrderObj order){
        this.ctx=ctx;
        this.order=order;



    }
    private static final String loginName = "18610536079";
    private static final String appId = "0f447dda99da4c6288524100d4d19382";
    private static final String keySecret = "f6fec505f325419a8b6d748d4d15ac07";
    private static final String sourceNo = "1151000000201308";
    private static final String mobile ="18610536079";
    private static final String outCustomerId ="18503054520";
    private static final String merchantNo="mer500220160000011801093094151";
    private static final String channel = "H5";

    private static final String request_url="https://demo-openapi.wjjr.cc";


   TextView show;
    Map<String,String> resultunifiedorder;
    StringBuffer sb;
    public void pay()
    {
        StaticVar.orderid=order.getId();

        Object result = testAccessToken();
        if (result == null) {
            System.exit(0);
        }
        JSONObject json = JSON.parseObject(result.toString());
        int code = json.getIntValue("code");
        if (code == 1) {
            JSONObject json1 = json.getJSONObject("data");
            String accessToken = json1.getString("access_token");
            if (MyStringUtil.isNotEmpty(accessToken)) {
                Toast.makeText(ctx, "当前手机号还未开通易信通,支付失败", Toast.LENGTH_LONG).show();
            }
        }
       /* resultunifiedorder=doInBackground();*/

    }

    /**
     * 获取收银台
     * @param accessToken

     * @return
     */
    public  JSONObject testCreateCashTokenAndGetWidgetPage(String accessToken) {
        Map<String, String> params = new HashMap<String, String>();

        String outTradeNo = order.getOrder_no();//"ZXH" + System.currentTimeMillis();
        params.put("mobile", "");
        params.put("appId", appId);
        params.put("accessToken", accessToken);

        params.put("channel", channel);

        params.put("productNo", order.getOrder_no());
        params.put("scenesCode", "020200002");
        params.put("orderDesc", order.getDescription());
        params.put("childMerchantNo", merchantNo);
        params.put("orderName", "性能测试的订单");
        params.put("urlKey", "cash_desk");
        params.put("outTradeNo", outTradeNo);
        params.put("currency", "CNY");
        params.put("paySource", "ANDROID");
        params.put("amount", "100");
        params.put("merchantNo", merchantNo);
        params.put("sourceNo", sourceNo);
        params.put("productName", "医生咨询订单");
        params.put("loginName", loginName);

        params.put("orderBeginTime", order.getOrder_date());// 2015-09-22
        params.put("orderNotifyUrl", "http://www.baidu.com/notify");
        params.put("orderFrontNotifyUrl", "http://yxs.im/sJOrs4");

        params.put("extendParams", "121511120000?thsds=1234");
        params.put("outCustomerId", outCustomerId);
        String url = request_url + "/service/cash_desk";
        Object result = OpenApiSinatureAccessService.openApiSinatureAccess(params, url, OpenSdkSupportHttpMethod.HTTP_POST_METHOD.getMehtod(), keySecret);
        System.out.println(result);
        JSONObject json = JSON.parseObject(result.toString());
        int code = json.getIntValue("code");
        if (code == 1) {
            JSONObject json1 = json.getJSONObject("data");
            json1 = json1.getJSONObject("resultInfo");
            return json1;
        }
        return null;
    }





    /**
     * 获取accessToken
     * @return
     */
    public static Object testAccessToken() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", appId);
        params.put("scope", "ACCESSTOKEN");
        params.put("sourceNo", sourceNo);
        params.put("channel", channel);
        String url = request_url + "/auth/service_access_token";
        Object result = OpenApiSinatureAccessService.openApiSinatureAccess(params, url, OpenSdkSupportHttpMethod.HTTP_GET_METHOD.getMehtod(), keySecret);
        System.out.println(result);
        return result;
    }

}
