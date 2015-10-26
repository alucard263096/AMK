package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.MemberPhotoDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberPhotoObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.ImageShower;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.MemberLoader;
import com.helpfooter.steve.amklovebaby.Loader.MemberPhotoDeleteLoader;
import com.helpfooter.steve.amklovebaby.Loader.MemberPhotoLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;

/**
 * Created by scai on 2015/10/26.
 */
public class MemberPhotoLoadView implements IWebLoaderCallBack,View.OnClickListener {
    Activity ctx;
    int member_id;
    LinearLayout mainlayout;

    public MemberPhotoLoadView(Activity ctx, int member_id, LinearLayout mainlayout) {
        this.ctx = ctx;
        this.member_id = member_id;
        this.mainlayout = mainlayout;
    }

    public void LoadPhotoList(){
        MemberPhotoLoader loader=new MemberPhotoLoader(ctx,member_id);
        loader.setCallBack(this);
        loader.start();
    }


    private Handler downhandler = new Handler() {
        public void handleMessage(Message msg) {
            RealLoadData();
        }
    };

    @Override
    public void CallBack(ArrayList<AbstractObj> nouse) {
        downhandler.sendEmptyMessage(0);
    }

    public void RealLoadData(){

        MemberPhotoDao dao=new MemberPhotoDao(ctx);

        ArrayList<AbstractObj> readlist=dao.getList("1=1");

        mainlayout.removeAllViews();
        for(AbstractObj obj:readlist){
            MemberPhotoObj photo=(MemberPhotoObj)obj;

            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
            param.topMargin=10;
            param.bottomMargin=10;
            param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
            param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
            layout.setLayoutParams(param);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView img=getImage(photo);
            layout.addView(img);

            TextView title=getTitleText(photo);
            layout.addView(title);

            TextView btnDelete=getDeleteButton(photo);
            layout.addView(btnDelete);

            mainlayout.addView(layout);
        }
    }

    private TextView getDeleteButton(MemberPhotoObj photo) {
        TextView txt=new TextView(ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.18f,true);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
        txt.setLayoutParams(param);
        txt.setTextColor(Color.parseColor("#81CAE6"));
        txt.setOnClickListener(this);
        txt.setGravity(Gravity.CENTER);


        txt.setText("删除");
        txt.setTag(photo);
        return txt;
    }

    private TextView getTitleText(MemberPhotoObj photo) {
        TextView txt=new TextView(ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.48f,true);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
        txt.setLayoutParams(param);

        txt.setText(photo.getTitle());
        return txt;
    }

    private ImageView getImage(MemberPhotoObj photo) {
        ImageView img=new ImageView(ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.28f,true);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
        img.setAdjustViewBounds(true);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setLayoutParams(param);

        String url= StaticVar.ImageFolderURL+"member/"+photo.getPhoto();
        UrlImageLoader imageLoader=new UrlImageLoader(img,url);
        imageLoader.start();

        img.setOnClickListener(this);
        img.setTag(photo);
        return img;
    }

    @Override
    public void onClick(View v) {
        MemberPhotoObj photo=(MemberPhotoObj)v.getTag();
        if(v instanceof ImageView){
            ImageView imageview=(ImageView)v;
            String url= StaticVar.ImageFolderURL+"member/"+photo.getPhoto();
            Intent intent = new Intent(ctx, ImageShower.class);
            intent.putExtra("url", url);
            this.ctx.startActivity(intent);
        }else {
            MemberPhotoDeleteLoader loader=new MemberPhotoDeleteLoader(ctx,member_id,photo.getId());
            loader.start();

            ((View)v.getParent()).setVisibility(View.GONE);
        }
    }
}
