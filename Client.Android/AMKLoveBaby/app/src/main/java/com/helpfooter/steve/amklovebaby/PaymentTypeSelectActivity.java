package com.helpfooter.steve.amklovebaby;

import com.helpfooter.steve.amklovebaby.DataObjs.PaymentTypeObj;
import com.helpfooter.steve.amklovebaby.DataObjs.SexObj;

/**
 * Created by Steve on 2015/10/25.
 */
public class PaymentTypeSelectActivity extends SelectActivity {
    @Override
    public void InitData() {
        lst.add(new PaymentTypeObj("支付宝钱包","ALIPAY"));
        lst.add(new PaymentTypeObj("微信钱包","WEIXIN"));
        lst.add(new PaymentTypeObj("测试","TEST"));
    }
}
