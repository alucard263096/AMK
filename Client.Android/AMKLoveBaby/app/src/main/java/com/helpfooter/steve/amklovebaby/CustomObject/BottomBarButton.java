package com.helpfooter.steve.amklovebaby.CustomObject;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.MyTextView;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.MainActivity;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.MyResourceIdUtil;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/26.
 */
public class BottomBarButton {
    String name;
    int imageResId;
    int activeImageResId;
    Fragment fragment;
    Context ctx;
    ImageView imgView;
    TextView txtView;
    LinearLayout entryLayout;
    String displayName;


    public BottomBarButton(Context ctx,String name, int imageResId,int activeImageResId,String displayName,Fragment fragment){
        this.ctx=ctx;
        this.name=name;
        this.imageResId=imageResId;
        this.activeImageResId=activeImageResId;
        this.fragment=fragment;
        this.displayName=displayName;
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
        if(imgView!=null){
            return imgView;
        }
        imgView=new ImageView(ctx);
        imgView.setId(MyResourceIdUtil.GetMyResourceId("bottom_bar_image_" + name));

        PercentLinearLayout.LayoutParams param=new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT,PercentLinearLayout.LayoutParams.MATCH_PARENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo=new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.6f,true);
        layoutinfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.1f,true);
        layoutinfo.fillLayoutParams(param,PercentLinearLayout.LayoutParams.MATCH_PARENT,PercentLinearLayout.LayoutParams.MATCH_PARENT);
        param.mPercentLayoutInfo=layoutinfo;

        param.gravity= Gravity.CENTER_HORIZONTAL;
        imgView.setImageResource(this.imageResId);
        //img.setBackgroundColor(Color.parseColor("#ee00ee"));
        imgView.setLayoutParams(param);

        return  imgView;
    }

    public  void SetDefault(){
        ImageView img=GetImageView();
        img.setImageResource(this.imageResId);
        LinearLayout layout=GetEnteryLayout();
        layout.setBackgroundColor(Color.WHITE);
        TextView txt=GetTextView();
        txt.setTextColor(Color.parseColor("#949494"));
    }

    public  void SetActive(){
        ImageView img=GetImageView();
        img.setImageResource(this.activeImageResId);
        LinearLayout layout=GetEnteryLayout();
        layout.setBackgroundColor(Color.parseColor("#EA7598"));
        TextView txt=GetTextView();
        txt.setTextColor(Color.WHITE);
    }


    public TextView GetTextView(){
//        GetTextView<TextView
//        android:id="@+id/tv_know1"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_below="@id/iv_know1"
//        android:layout_centerHorizontal="true"
//        android:text="首页"
//        android:textColor="@color/bottomtab_normal"
//        android:textSize="12sp" />
        if(txtView!=null){
            return txtView;
        }

        txtView=new MyTextView(ctx);
        txtView.setId(MyResourceIdUtil.GetMyResourceId("bottom_bar_txt_" + name));
        //LinearLayout.LayoutParams txtParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,20);

        PercentLinearLayout.LayoutParams param=new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT,PercentLinearLayout.LayoutParams.MATCH_PARENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo=new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,true);
        layoutinfo.fillLayoutParams(param,PercentLinearLayout.LayoutParams.MATCH_PARENT,PercentLinearLayout.LayoutParams.MATCH_PARENT);
        param.mPercentLayoutInfo=layoutinfo;

        //txtParam.addRule(RelativeLayout.BELOW, GetImageView().getId());
        txtView.setGravity(Gravity.CENTER);
        //txtParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        txtView.setText(displayName);
        txtView.setTextColor(Color.parseColor("#949494"));
        txtView.setTextSize(12);
        txtView.setLayoutParams(param);
        //txtView.setBackgroundColor(Color.BLUE);
        return  txtView;
    }

    public String GetLayoutIdName(){
        return  "bottom_bar_layout_" + name;
    }

    public LinearLayout GetEnteryLayout(){
        if(entryLayout!=null){
            return  entryLayout;
        }
        entryLayout=new PercentLinearLayout(ctx,null);
//        <RelativeLayout
//        android:id="@+id/rl_know"
//        android:layout_width="0dp"
//        android:layout_height="wrap_content"
//        android:layout_weight="1.0" >


        entryLayout.setId(MyResourceIdUtil.GetMyResourceId(GetLayoutIdName()));
        entryLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.weight=1;
        entryLayout.setLayoutParams(params);
        entryLayout.addView(GetImageView());
        entryLayout.addView(GetTextView());
        entryLayout.setBackgroundColor(Color.WHITE);

        return  entryLayout;
    }

    public static void CreateEnteryBottomBar(LinearLayout layout, ArrayList<BottomBarButton> lstBottomBar, View.OnClickListener clickLinstenerActivity) {

        for (BottomBarButton barButton : lstBottomBar) {
            LinearLayout barLayout=barButton.GetEnteryLayout();
            barLayout.setTag(barButton);
            barLayout.setOnClickListener(clickLinstenerActivity);
            layout.addView(barLayout);
        }

    }


}
