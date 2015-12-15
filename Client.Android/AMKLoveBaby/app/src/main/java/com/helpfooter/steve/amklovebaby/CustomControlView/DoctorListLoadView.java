package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DoctorDetailActivity;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;

/**
 * Created by scai on 2015/9/1.
 */
public class DoctorListLoadView implements View.OnClickListener {
    public LinearLayout mainlayout;
    public ArrayList<DoctorObj> lstDoctor;
    public Context ctx;
    public  DoctorListLoadView(Context ctx,LinearLayout layout){
        this.ctx=ctx;
        this.mainlayout=layout;
        DoctorDao dao=new DoctorDao(this.ctx);
        ArrayList<AbstractObj> lst=dao.getDoctorList();
        lstDoctor=new ArrayList<DoctorObj>();
        for(AbstractObj obj:lst){
            lstDoctor.add((DoctorObj)obj);
        }
    }

    public void LoadDoctorListData(){
        int i=1;
        for(DoctorObj obj:lstDoctor){
            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            param.mPercentLayoutInfo.bottomMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            //String backgroundcolor=getBackgroundColor(i);
            //layout.setBackgroundColor(Color.parseColor(backgroundcolor));
            layout.setLayoutParams(param);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imgPhoto=getPhotoView(obj);
            layout.addView(imgPhoto);

            LinearLayout infolayout=getInfoLayout(obj);
            layout.addView(infolayout);

            layout.setTag(obj);
            layout.setOnClickListener(this);
            this.mainlayout.addView(layout);

            LinearLayout line=ToolsUtil.GenPLine(this.ctx);
            this.mainlayout.addView(line);

            i++;
        }
    }
    public ImageView getPhotoView(DoctorObj doctor){
        ImageView img=new ImageView(this.ctx);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setAdjustViewBounds(true);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f,true);
        String url= StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto();
        Log.i("doctor_photo",url);
        UrlImageLoader imgLoad=new UrlImageLoader(img,url);
        imgLoad.start();;
        img.setLayoutParams(param);

        return img;
    }

    public LinearLayout getInfoLayout(DoctorObj doctor){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.VERTICAL);

        PercentLinearLayout layout0=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param1=ToolsUtil.getLayoutParamHeightWrap();
        layout0.setLayoutParams(param1);
        layout0.setOrientation(LinearLayout.HORIZONTAL);

        PercentLinearLayout layout1=new PercentLinearLayout(this.ctx);
        param1=ToolsUtil.getLayoutParamHeightWrap();
        param1.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.70f, true);
        layout1.setLayoutParams(param1);
        layout1.setOrientation(LinearLayout.VERTICAL);

        //医生名称
        TextView txtName=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramName = ToolsUtil.getLayoutParamHeightWrap();
        paramName.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        paramName.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        txtName.setLayoutParams(paramName);
        txtName.setTextSize(17);
        txtName.setText(doctor.getName());
        TextPaint tp= txtName.getPaint();
        tp.setFakeBoldText(true);

        //医生单位
        PercentLinearLayout layoutOrganization=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams paramsOrganization = ToolsUtil.getLayoutParamHeightWrap();
        paramsOrganization.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f, true);
        layoutOrganization.setLayoutParams(paramsOrganization);
        layoutOrganization.setBackgroundColor(Color.DKGRAY);
        layoutOrganization.setOrientation(LinearLayout.HORIZONTAL);

        //医生信息
        TextView txtDoctorInfo = new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramOrganization = ToolsUtil.getLayoutParam();
        paramOrganization.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);

        txtDoctorInfo.setLayoutParams(paramOrganization);
        txtDoctorInfo.setTextSize(15);
        txtDoctorInfo.setText(doctor.getOffice() + "   " + doctor.getTitle());

        layout1.addView(txtName);
        layout1.addView(txtDoctorInfo);

        //分数，推荐指数
        PercentLinearLayout layoutScore = new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams paramScore = ToolsUtil.getLayoutParam();
        paramScore.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);
        paramScore.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);
        layoutScore.setBackgroundColor(Color.parseColor("#FDB32B"));
        layoutScore.setLayoutParams(paramScore);
        layoutScore.setOrientation(LinearLayout.VERTICAL);

        //分数
        TextView txtScore = new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramScore1 = ToolsUtil.getLayoutParamHeightWrap();
        paramScore1.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.97f,true);
        paramScore1.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
        paramScore1.setMargins(3, 3, 3, 3);
        txtScore.setLayoutParams(paramScore1);
        txtScore.setTextSize(17);
        txtScore.setGravity(Gravity.CENTER);
        txtScore.setText(String.valueOf(doctor.getRealGeneralScore()));
        txtScore.setBackgroundColor(Color.parseColor("#FFFFFF"));
        txtScore.setTextColor(Color.parseColor("#FDB32B"));
        tp = txtScore.getPaint();
        tp.setFakeBoldText(true);
        layoutScore.addView(txtScore);

        //推荐指数
        txtScore = new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramScore2 =ToolsUtil.getLayoutParam();
        paramScore2.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        paramScore2.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
        txtScore.setLayoutParams(paramScore2);
        txtScore.setBackgroundColor(Color.parseColor("#FDB32B"));
        txtScore.setTextColor(Color.WHITE);
        txtScore.setTextSize(13);
        txtScore.setText("推荐指数");
        txtScore.setGravity(Gravity.CENTER);
        tp = txtScore.getPaint();
        tp.setFakeBoldText(true);

        layoutScore.addView(txtScore);

        layout0.addView(layout1);
        layout0.addView(layoutScore);
        //描述
        TextView txtExpert=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramExpert = ToolsUtil.getLayoutParam();
        //paramExpert.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.1f,true);
        //paramExpert.mPercentLayoutInfo.topMarginPercent  = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        paramExpert.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        //paramExpert.topMargin = 2;
        txtExpert.setPadding(0,20,0,0);
        txtExpert.setLayoutParams(paramExpert);
        txtExpert.setSingleLine(true);
        txtExpert.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        txtExpert.setText(doctor.getExpert());
        //layoutDesc.addView(txtExpert);

        //价格
        PercentLinearLayout layoutPrice = new PercentLinearLayout(this.ctx);
        param = ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.heightPercent  = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.1f,true);
        param.topMargin = 10;
        layoutPrice.setLayoutParams(param);
        layoutPrice.setOrientation(LinearLayout.HORIZONTAL);

        //是否支持图文会诊
        ImageView img=new ImageView(this.ctx);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setAdjustViewBounds(true);
        param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.10f,true);

        if(doctor.getEnableCharchat().equals("Y")) {
            img.setImageResource(R.drawable.chart1);
        }
        else{
            img.setImageResource(R.drawable.chart2);
        }
        img.setLayoutParams(param);
        layoutPrice.addView(img);

        //是否支持视频会诊
        img=new ImageView(this.ctx);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setAdjustViewBounds(true);
        param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.10f,true);

        if(doctor.getEnableVideochat().equals("Y")) {
            img.setImageResource(R.drawable.video1);
        }
        else{
            img.setImageResource(R.drawable.video2);
        }
        img.setLayoutParams(param);
        layoutPrice.addView(img);

        TextView txtPrice = new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramPrice = ToolsUtil.getLayoutParamHeightWrap();
        paramPrice.rightMargin = 40;
        txtPrice.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        txtPrice.setLayoutParams(paramPrice);
        tp = txtPrice.getPaint();
        tp.setFakeBoldText(true);
        txtPrice.setText("￥" + String.valueOf(doctor.getMaxPrice())+"起");
        layoutPrice.addView(txtPrice);

        /*TextView txtOfficePosition=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams officeparam=ToolsUtil.getLayoutParamHeightWrap();
        txtOfficePosition.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
        txtOfficePosition.setLayoutParams(officeparam);
        txtOfficePosition.setText(doctor.getOffice() + "/" + doctor.getTitle());
        nameRatiolayout.addView(txtOfficePosition);*/

        layout.addView(layout0);
        layout.addView(txtExpert);

        LinearLayout line=ToolsUtil.GenPLine(this.ctx);
        layout.addView(line);

        layout.addView(layoutPrice);

        return  layout;
    }

    private String getBackgroundColor(int number) {
        switch (number%4){
            case 0:
                return "#FD7CAD";
            case 2:
                return "#F7CB20";
            case 3:
                return "#B9E5C3";
            default:
                return "#37A4D4";
        }
    }

    @Override
    public void onClick(View v) {
        DoctorObj obj=(DoctorObj)v.getTag();
        Intent intent = new Intent(this.ctx, DoctorDetailActivity.class);
        intent.putExtra("Id", obj.getId());
        this.ctx.startActivity(intent);
    }
}
