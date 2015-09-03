package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.BannerDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.BannerObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/28.
 */
public class IndexBannerSilderView extends BannerSilderView {


    public IndexBannerSilderView(Context context, AttributeSet set) {
        super(context,set);
    }

    public IndexBannerSilderView(Context context) {
        super(context);
    }

    Context ctx;
    public ImageView AddImage(String url, final String openurl){
        ImageView img=super.AddImage(url);
        if(ctx==null){
            ctx=this.getContext();
        }
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(openurl);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);

                ctx.startActivity(it);
            }
        });
        return  img;
    }

    public void LoadBanner(){
        super.LoadBanner();

        BannerDao dao=new BannerDao(this.getContext());
        ArrayList<AbstractObj> lstBannerObj=dao.getIndexBanner();
        Log.i("IndexBanner_debugCount",String.valueOf(lstBannerObj.size()));
        for(AbstractObj abobj:lstBannerObj){
            BannerObj obj=(BannerObj)abobj;
            this.AddImage(StaticVar.ImageFolderURL + "banner/" + obj.getPic(), obj.getLink());
        }

    }

}
