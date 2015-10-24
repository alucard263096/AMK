package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.NewsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.NewsLoader;
import com.helpfooter.steve.amklovebaby.Loader.WebXmlLoader;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;

public class NewsDetailActivity extends Activity implements View.OnClickListener {
    NewsObj news;
    DoctorObj doctor;
    ImageView btnBack;
    ScrollView idBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        InitData();
        InitUI();
    }

    private void InitUI() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        idBody=(ScrollView)findViewById(R.id.idBody);

        WebView webView=(WebView)findViewById(R.id.txtContext);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setBackgroundColor(0);

        ((TextView)findViewById(R.id.txtTitle)).setText(news.getTitle());
        TextPaint tp= ((TextView)findViewById(R.id.txtTitle)).getPaint();
        tp.setFakeBoldText(true);
        ((TextView)findViewById(R.id.publish_date)).setText(news.getPublish_date());
        ImageView imgPhoto=(ImageView)findViewById(R.id.imgPhoto);
        if(news.getPhoto().length()>3){
            UrlImageLoader imagePhotoLoad=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"news/"+news.getPhoto());
            imagePhotoLoad.start();
        }else {
            imgPhoto.setVisibility(View.GONE);
        }
        NewsLoader newsLoader=new NewsLoader(this);
        newsLoader.setGetNewsContext(news.getId());
        webView.getSettings().setDefaultTextEncodingName("UTF -8");
        NewsContentLoad contentLoad=new NewsContentLoad(webView);
        newsLoader.setCallBack(contentLoad);
        newsLoader.start();

        LinearLayout layoutDoctor=(LinearLayout)findViewById(R.id.layoutDoctor);
        if(doctor==null){
            layoutDoctor.setVisibility(View.GONE);
            //addHeightForWebView(0.15f);
            PercentLinearLayout.LayoutParams vidt=ToolsUtil.getLayoutParam();
            idBody.setLayoutParams(vidt);
        }else{
            layoutDoctor.setTag(doctor);
            layoutDoctor.setOnClickListener(this);
            ImageView imgDoctorPhoto=(ImageView)findViewById(R.id.imgDoctorPhoto);
            UrlImageLoader imagePhotoLoad=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto());
            imagePhotoLoad.start();
            ((TextView)findViewById(R.id.txtDoctorName)).setText(doctor.getName());
            ((TextView)findViewById(R.id.txtDoctorOfficeTitle)).setText(doctor.getOffice()+"/"+doctor.getTitle());
        }

    }

    public  void addHeightForWebView(float h){
        //float orih=((PercentLinearLayout.LayoutParams)idBody.getLayoutParams()).mPercentLayoutInfo.heightPercent.percent;
        //((PercentLinearLayout.LayoutParams)idBody.getLayoutParams()).mPercentLayoutInfo.heightPercent.percent=100;
                //new PercentLayoutHelper.PercentLayoutInfo.PercentVal(orih+0.9f,true);

        //float c =((PercentLinearLayout.LayoutParams)idBody.getLayoutParams()).mPercentLayoutInfo.heightPercent.percent;

    }

    class NewsContentLoad implements IWebLoaderCallBack{
        WebView webView;
        NewsObj news;
        public NewsContentLoad(WebView webview){
            this.webView=webview;
        }
        @Override
        public void CallBack(ArrayList<AbstractObj> lstObjs) {
            if(lstObjs.size()>0){
                NewsObj obj=(NewsObj)lstObjs.get(0);
                news=obj;
                mHandler.sendEmptyMessageDelayed(0, 0);
            }
        }
        private Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                try {
                    String content=news.getContent();
                    Log.i("webview_cont",content);
                    webView.loadData(ToolsUtil.FormatString(content), "text/html; charset=UTF-8", null);
                }catch (Exception ex){
                    Log.i("webview_err",ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
                return;
            case R.id.layoutDoctor:
                DoctorObj obj=(DoctorObj)v.getTag();
                Intent intent = new Intent(this, DoctorDetailActivity.class);
                intent.putExtra("Id", obj.getId());
                startActivity(intent);
                return;
        }

    }

    private void InitData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        NewsDao dao=new NewsDao(this);
        news=(NewsObj)dao.getObj(id);

        if(news.getDoctor_id()>0){
            DoctorDao doctorDao=new DoctorDao(this);
            doctor=(DoctorObj)doctorDao.getObj(news.getDoctor_id());
        }

    }
}
