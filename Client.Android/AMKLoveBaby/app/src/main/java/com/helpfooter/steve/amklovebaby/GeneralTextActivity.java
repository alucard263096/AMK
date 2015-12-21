package com.helpfooter.steve.amklovebaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;


public class GeneralTextActivity extends MyActivity implements View.OnClickListener{

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
        ((TextView) findViewById(R.id.title)).getPaint().setFakeBoldText(true);
        ((WebView) findViewById(R.id.txtContext)).loadUrl(StaticVar.GeneralTextUrl + code);

        ((WebView) findViewById(R.id.txtContext)).setBackgroundColor(0);
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
