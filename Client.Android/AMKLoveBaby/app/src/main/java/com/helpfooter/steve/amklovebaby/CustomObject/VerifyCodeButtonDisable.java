package com.helpfooter.steve.amklovebaby.CustomObject;

import android.os.Message;
import android.widget.Button;

/**
 * Created by Steve on 2015/9/9.
 */
public class VerifyCodeButtonDisable extends Thread {
    Button btn;
    int second=60;
    public VerifyCodeButtonDisable(Button btn){
        this.btn=btn;

    }

    @Override
    public void run() {
        try {
            while (second-->0) {
                Thread.sleep(1000);
                waitHandler.sendEmptyMessage(0);
            }
        }catch (Exception ex){

        }
        activeHandler.sendEmptyMessage(0);
    }
    private android.os.Handler waitHandler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            btn.setText("重新发送(" + String.valueOf(second) + ")");
            btn.setEnabled(false);
        }
    };

    private android.os.Handler activeHandler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            btn.setText("获取验证码");
            btn.setEnabled(true);
        }
    };


}
