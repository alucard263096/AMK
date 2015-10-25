package com.helpfooter.steve.amklovebaby.DataObjs;

import com.helpfooter.steve.amklovebaby.Interfaces.ISelectObj;

/**
 * Created by Steve on 2015/10/25.
 */
public class PaymentTypeObj implements ISelectObj {

    String paymentType;
    String paymentCode;

    public PaymentTypeObj(String paymentType, String paymentCode) {
        this.paymentType = paymentType;
        this.paymentCode = paymentCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    @Override
    public String DisplayName() {
        return paymentType;
    }

    @Override
    public String SelectedValue() {
        return paymentCode;
    }
}
