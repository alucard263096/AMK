package com.helpfooter.steve.amklovebaby.CustomObject;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
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
        LinearLayout.LayoutParams imageParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imageParam.gravity= Gravity.CENTER_HORIZONTAL;
        imageParam.width=100;
        imageParam.height=100;
        imageParam.topMargin=10;
        imgView.setImageResource(this.imageResId);
        //img.setBackgroundColor(Color.parseColor("#ee00ee"));
        imgView.setLayoutParams(imageParam);

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
        LinearLayout.LayoutParams txtParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //txtParam.addRule(RelativeLayout.BELOW, GetImageView().getId());
        txtView.setGravity(Gravity.CENTER);
        //txtParam.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        txtView.setText(displayName);
        txtView.setTextColor(Color.parseColor("#949494"));
        txtView.setTextSize(12);
        //txtView.setBackgroundColor(Color.BLUE);
        return  txtView;
    }
    public LinearLayout GetEnteryLayout(){
        if(entryLayout!=null){
            return  entryLayout;
        }
        entryLayout=new LinearLayout(ctx);
//        <RelativeLayout
//        android:id="@+id/rl_know"
//        android:layout_width="0dp"
//        android:layout_height="wrap_content"
//        android:layout_weight="1.0" >


        entryLayout.setId(MyResourceIdUtil.GetMyResourceId("bottom_bar_layout_" + name));
        entryLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.weight=1;
        entryLayout.setLayoutParams(params);
        entryLayout.addView(GetImageView());
        entryLayout.addView(GetTextView());

        return  entryLayout;
    }

    public static void CreateEnteryBottomBar(LinearLayout layout,ArrayList<BottomBarButton> lstBottomBar) {

        for (BottomBarButton barButton : lstBottomBar) {
            LinearLayout barLayout=barButton.GetEnteryLayout();
            barLayout.setTag(barButton);
            ClickBottomBarEvent clickevent=new ClickBottomBarEvent();
            clickevent.lstBottomBar=lstBottomBar;
            barLayout.setOnClickListener(clickevent.clickBottomBar);
            layout.addView(barLayout);
        }



    }
    public static class  ClickBottomBarEvent{

        public ArrayList<BottomBarButton> lstBottomBar;

        public LinearLayout.OnClickListener clickBottomBar = new LinearLayout.OnClickListener() {
            public void onClick(View v) {

                for (BottomBarButton barButton : lstBottomBar) {
                    barButton.SetDefault();
                }

                BottomBarButton barButton=(BottomBarButton)v.getTag();
                barButton.SetActive();
            }
        };
    }

}
