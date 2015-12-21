package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.MessageListLoadView;


public class CharListActivity extends Activity implements View.OnClickListener {
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_list);InitUI();

        InitData();
        InitUI();
    }

    private void InitData() {

    }
    MessageListLoadView lstLoad;
    boolean hasNewView=false;
    private void InitUI() {
        ((TextView)findViewById(R.id.title)).getPaint().setFakeBoldText(true);
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        if(hasNewView==false){
            lstLoad = new MessageListLoadView(this, this.getApplicationContext(), (LinearLayout) findViewById(R.id.normal_chat_list));
            lstLoad.LoadList();
            hasNewView=true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                lstLoad.UnloadList();
                return;
        }
    }





}
