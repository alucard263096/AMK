package com.helpfooter.steve.amklovebaby;

import com.helpfooter.steve.amklovebaby.DataObjs.PaymentTypeObj;
import com.helpfooter.steve.amklovebaby.DataObjs.SexObj;

/**
 * Created by Steve on 2015/10/25.
 */
public class PaymentTypeSelectActivity extends SelectActivity {
    @Override
    public void InitData() {
        lst.add(new PaymentTypeObj("支付宝钱包","ALIPAY",true,R.drawable.alipay_logo));
        lst.add(new PaymentTypeObj("微信钱包","WEIXIN",true,R.drawable.weixin_logo));
        lst.add(new PaymentTypeObj("银联支付","UNIONPAY",true,R.drawable.unionpay));
        lst.add(new PaymentTypeObj("信通宝","XTB",true,R.drawable.unionpay));
    }
}
