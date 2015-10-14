package com.helpfooter.steve.amklovebaby;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals(ACTION))
        {
            context.startService(new Intent(context,MessageService.class));//启动倒计时服务
            Toast.makeText(context, "MessageService service has started!", Toast.LENGTH_LONG).show();
        }
    }



}
