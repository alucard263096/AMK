package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.BannerDao;
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

    public void LoadBanner(){
        super.LoadBanner();

        BannerDao dao=new BannerDao(this.getContext());
        ArrayList<BannerObj> lstBannerObj=dao.getIndexBanner();
        Log.i("IndexBanner_debugCount",String.valueOf(lstBannerObj.size()));
        for(BannerObj obj:lstBannerObj){
            super.AddImage(StaticVar.ImageFolderURL+"banner/"+obj.getPic());
        }

    }

}
