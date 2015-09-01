package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.content.Loader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.R;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/28.
 */
public class BannerSilderView extends ImageSilderView implements IWebLoaderCallBack {


    public BannerSilderView(Context context,AttributeSet set) {
        super(context,set);
    }

    public BannerSilderView(Context context) {
        super(context);
    }

    public void LoadBanner(){
        this.GetImageViewList().clear();
    }

    @Override
    public void CallBack() {
        LoadBanner();
        mHandler.sendEmptyMessageDelayed(0, 0);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            StartCircle();
        }
    };
}
