package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        StartActivityWaitThread close=new StartActivityWaitThread();
        close.start();
    }
    class StartActivityWaitThread extends Thread{
        public void run(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            StartActivity.this.finish();
        }
    }

}
