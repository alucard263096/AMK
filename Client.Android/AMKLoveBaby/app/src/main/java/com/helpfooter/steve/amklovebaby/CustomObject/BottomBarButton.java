package com.helpfooter.steve.amklovebaby.CustomObject;

import android.app.Fragment;
import android.content.Context;
import android.media.Image;
import android.media.ImageReader;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.helpfooter.steve.amklovebaby.Utils.MyResourceIdUtil;

/**
 * Created by Steve on 2015/8/26.
 */
public class BottomBarButton {
    String name;
    int imageResId;
    int activeImageResId;
    Fragment fragment;
    Context ctx;



    public BottomBarButton(Context ctx,String name, int imageResId,int activeImageResId,Fragment fragment){
        this.ctx=ctx;
        this.name=name;
        this.imageResId=imageResId;
        this.activeImageResId=activeImageResId;
        this.fragment=fragment;
    }

    public String GetName(){
        return name;
    }

    public Fragment GetFragment(){
        return  fragment;
    }

    public ImageView GetImageView(){
//        <ImageView
//        android:id="@+id/iv_know1"
//        android:layout_width="wrap_content"
//        android:layout_height="35dp"
//        android:layout_centerHorizontal="true"
//        android:src="@drawable/bar_home"
//        android:contentDescription="@null" />
        ImageView iv=new ImageView(ctx);
        iv.setId(MyResourceIdUtil.GetMyResourceId("bottonbar_"+name+"_image"));
        ViewGroup.LayoutParams layoutParam=new ViewGroup.LayoutParams();
    }

    private  ImageView getView(int resid){

    }

}
