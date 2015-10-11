package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;


public class GeneralTextActivity extends Activity implements View.OnClickListener{

    String code;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_text);

        InitData();
        InitUI();
    }

    private void InitData() {

        Intent intent = getIntent();
        this.code = intent.getStringExtra("code");
        this.title = intent.getStringExtra("title");
    }

    private void InitUI() {
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText(title);

        ((WebView) findViewById(R.id.txtContext)).loadUrl(StaticVar.GeneralTextUrl+code);

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
