package com.helpfooter.steve.amkdoctor.CustomControlView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.DAO.MemberPhotoDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MemberPhotoObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.ImageShower;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.MemberLoader;
import com.helpfooter.steve.amkdoctor.Loader.MemberPhotoLoader;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;

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
        try {
            for (AbstractObj obj : readlist) {
                MemberPhotoObj photo = (MemberPhotoObj) obj;

                PercentLinearLayout layout = new PercentLinearLayout(ctx);
                PercentLinearLayout.LayoutParams param = ToolsUtil.getLayoutParamHeightWrap();
                param.topMargin = 10;
                param.bottomMargin = 10;
                param.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f, true);
                param.mPercentLayoutInfo.rightMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f, true);
                layout.setLayoutParams(param);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                ImageView img = getImage(photo);
                layout.addView(img);

                TextView title = getTitleText(photo);
                layout.addView(title);


                mainlayout.addView(layout);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }



    private TextView getTitleText(MemberPhotoObj photo) {
        TextView txt=new MyTextView(ctx);
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
       /* UrlImageLoader imageLoader=new UrlImageLoader(img,url);
        imageLoader.start();*/
        Bitmap bitmap=UrlImageLoader.GetBitmap(url);
        img.setImageBitmap(bitmap);
        try {
            img.setOnClickListener(this);
        }
        catch (Exception ex)
        {
            throw ex;
        }
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

        }
    }
}
