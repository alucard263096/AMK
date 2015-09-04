package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.NewsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.WebXmlLoader;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;

public class NewsDetailActivity extends Activity implements View.OnClickListener {
    NewsObj news;
    DoctorObj doctor;
    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        InitData();
        InitUI();
    }

    private void InitUI() {
        ((TextView)findViewById(R.id.txtTitle)).setText(news.getTitle());
        ImageView imgPhoto=(ImageView)findViewById(R.id.imgPhoto);
        if(news.getPhoto().length()>3){
            UrlImageLoader imagePhotoLoad=new UrlImageLoader(imgPhoto, StaticVar.ImageFolderURL+"news/"+news.getPhoto());
            imagePhotoLoad.start();
        }

    }
    class NewsContentLoad implements IWebLoaderCallBack{

        @Override
        public void CallBack(ArrayList<AbstractObj> lstObjs) {
            if(lstObjs.size()>0){
                NewsObj obj=
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                this.finish();
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
