package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.R;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/28.
 */
public class ImageSilderView extends ViewFlipper {

    ArrayList<ImageView> lstImageView=new ArrayList<ImageView>();

    public ImageSilderView(Context context) {
        super(context);
    }

    public ImageSilderView(Context context,AttributeSet set){
        super(context,set);
    }

    public void AddImage(ImageView imageView){
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        lstImageView.add(imageView);
    }

    public ImageView AddImage(Context ctx,String url){
        ImageView imageView=new ImageView(ctx);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        UrlImageLoader urlImageLoader=new UrlImageLoader(imageView,url);
        urlImageLoader.start();
        lstImageView.add(imageView);
        return  imageView;
    }

    public ArrayList<ImageView> GetImageViewList(){
        return  lstImageView;
    }

    public void StartCircle(Context ctx){

        for (ImageView img:GetImageViewList()){
            this.addView(img);
        }

        this.setInAnimation(AnimationUtils.loadAnimation(ctx,
                R.anim.push_left_in));
        this.setOutAnimation(AnimationUtils.loadAnimation(ctx,
                R.anim.push_left_out));
        this.startFlipping();
    }


}
