package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.R;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/28.
 */
public class ImageSilderView extends ViewFlipper  {

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

    public ImageView AddImage(String url){
        ImageView imageView=new ImageView(this.getContext());
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageView.setImageURI(UrlImageLoader);
       /* UrlImageLoader urlImageLoader=new UrlImageLoader(imageView,url);
        urlImageLoader.start();*/
        Bitmap bitmap=UrlImageLoader.GetBitmap(url);
        imageView.setImageBitmap(bitmap);
        lstImageView.add(imageView);
        return  imageView;
    }

    public ArrayList<ImageView> GetImageViewList(){
        return  lstImageView;
    }

    public void StartCircle(){
        this.stopFlipping();
        this.removeAllViews();
        for (ImageView img:GetImageViewList()){
            this.addView(img);
        }
        this.setInAnimation(AnimationUtils.loadAnimation(this.getContext(),
                R.anim.push_left_in));
        this.setOutAnimation(AnimationUtils.loadAnimation(this.getContext(),
                R.anim.push_left_out));
        this.startFlipping();
    }


}
