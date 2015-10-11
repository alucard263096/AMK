package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.helpfooter.steve.amklovebaby.CustomControlView.MessageListLoadView;
import com.helpfooter.steve.amklovebaby.DAO.MemberDao;
import com.helpfooter.steve.amklovebaby.Loader.MemberUpdateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;


public class CharListActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_list);InitUI();

        InitData();
        InitUI();
    }

    private void InitData() {

    }

    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        final MessageListLoadView lstLoad=new MessageListLoadView(this,this.getApplicationContext(),(LinearLayout)findViewById(R.id.normal_chat_list));
        lstLoad.LoadList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                return;
        }
    }





}
