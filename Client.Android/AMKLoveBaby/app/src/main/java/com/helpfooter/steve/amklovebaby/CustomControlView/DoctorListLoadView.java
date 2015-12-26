package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
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
    public ArrayList<AbstractObj> lst;
    public Context ctx;
    public int rowCount;
    public  DoctorListLoadView(Context ctx,LinearLayout layout,String search){
        this.ctx=ctx;
        this.mainlayout=layout;
        //this.mainlayout.addView(getBannerView());
        DoctorDao dao=new DoctorDao(this.ctx);
         lst=dao.getDoctorList(search);
        lstDoctor=new ArrayList<DoctorObj>();
        rowCount=0;
        for(AbstractObj obj:lst){
            lstDoctor.add((DoctorObj)obj);
            rowCount++;
            if(lstDoctor.size()>=10)
            {
                break;
            }
        }
    }

    public void LoadDoctorListData(ArrayList<DoctorObj> lstDoctor){
        int i=1;
        for(DoctorObj obj:lstDoctor){
            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.01f,false);
            param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            param.mPercentLayoutInfo.bottomMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0f,false);
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

            LinearLayout line=ToolsUtil.GenPLine2(this.ctx);
            this.mainlayout.addView(line);

            i++;
        }
    }

    public ImageView getBannerView()
    {
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        layout.setLayoutParams(param);
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imgView = new ImageView(this.ctx);
        //imgView.setScaleType(ImageView.ScaleType.FIT_START);
        imgView.setAdjustViewBounds(true);
        param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.1f,true);
        imgView.setImageResource(R.drawable.personaldoctor);

        return  imgView;
    }

    public ImageView getPhotoView(DoctorObj doctor){

        CircleImageView1 img = new CircleImageView1(this.ctx);
        img.setScaleType(CircleImageView.ScaleType.CENTER_CROP);
        img.setAdjustViewBounds(false);

        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.20f,true);
        param.height = 200;
        param.width = 200;
        param.topMargin=30;
        String url= StaticVar.ImageFolderURL+"doctor/"+doctor.getPhoto();
        UrlImageLoader imgLoad=new UrlImageLoader(img,url);
        imgLoad.start();
        //img.setImageResource(R.drawable.doctor_demo);
        img.setBorderColor(Color.parseColor("#919191"));
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
        //layout0.setBackgroundColor(Color.BLUE);
        layout0.setLayoutParams(param1);
        layout0.setOrientation(LinearLayout.HORIZONTAL);

        PercentLinearLayout layout1=new PercentLinearLayout(this.ctx);
        param1 = ToolsUtil.getLayoutParamHeightWrap();
        param1.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.80f, true);
        layout1.setLayoutParams(param1);
        layout1.setOrientation(LinearLayout.VERTICAL);


        PercentLinearLayout layout2=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param2 = ToolsUtil.getLayoutParamHeightWrap();
        param2.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        layout2.setLayoutParams(param2);
        layout2.setOrientation(LinearLayout.HORIZONTAL);


        //医生名称
        TextView txtName=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramName = ToolsUtil.getLayoutParamWidthHeightWrap();
        paramName.leftMargin = 27;
        txtName.setLayoutParams(paramName);
        txtName.setTextSize(17);
        txtName.setText(doctor.getName());
        txtName.setTextColor(Color.BLACK);
        TextPaint tp= txtName.getPaint();
        tp.setFakeBoldText(true);
        //tp.setStrokeWidth(10f);

        layout2.addView(txtName);

        if(doctor.getIsTaiwan().equals("Y")){
            TextView txtOutDoc=new MyTextView(this.ctx);
            PercentLinearLayout.LayoutParams txtOutDocParam = ToolsUtil.getLayoutParamHeightWrap();
            txtOutDocParam.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
            //paramName.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
            txtOutDocParam.leftMargin = 27;
            txtOutDoc.setLayoutParams(txtOutDocParam);
            txtOutDoc.setTextSize(12);
            txtOutDoc.setText("海外");
            txtOutDoc.setTextColor(Color.GRAY);
            layout2.addView(txtOutDoc);
        }





        //医生信息
        TextView txtDoctorInfo = new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramInfo = ToolsUtil.getLayoutParam();
        //paramInfo.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        paramInfo.leftMargin = 27;
        txtDoctorInfo.setLayoutParams(paramInfo);
        txtDoctorInfo.setTextSize(15);
        txtDoctorInfo.setText(doctor.getOffice() + "   " + doctor.getTitle());
        txtDoctorInfo.setTextColor(Color.BLACK);
        tp= txtDoctorInfo.getPaint();
        tp.setFakeBoldText(true);
        layout1.addView(layout2);
        layout1.addView(txtDoctorInfo);

        //分数，推荐指数
        PercentLinearLayout layoutScore = new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams paramScore = ToolsUtil.getLayoutParam();
        paramScore.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.18f, true);
        paramScore.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.20f, true);
        paramScore.mPercentLayoutInfo.rightMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0f, true);

        layoutScore.setBackgroundColor(Color.parseColor("#FDB32B"));
        layoutScore.setLayoutParams(paramScore);
        layoutScore.setOrientation(LinearLayout.VERTICAL);

        //分数
        TextView txtScore = new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams paramScore1 = ToolsUtil.getLayoutParamHeightWrap();
        paramScore1.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.96f,true);
        paramScore1.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.6f,true);
        paramScore1.setMargins(3, 3, 3, 2);
        txtScore.setLayoutParams(paramScore1);
        txtScore.setTextSize(15);
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
        paramScore2.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.4f,true);
        txtScore.setLayoutParams(paramScore2);
        txtScore.setBackgroundColor(Color.parseColor("#FDB32B"));
        txtScore.setTextColor(Color.WHITE);
        txtScore.setTextSize(10);
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
        paramExpert.leftMargin = 27;
        //paramExpert.mPercentLayoutInfo.leftMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);

        txtExpert.setPadding(0, 25, 0, 0);
        txtExpert.setLayoutParams(paramExpert);
        txtExpert.setSingleLine(true);
        txtExpert.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        txtExpert.setText(doctor.getExpert());
        //txtExpert.setTextSize(14);
        tp = txtExpert.getPaint();
        tp.setFakeBoldText(true);
        //layoutDesc.addView(txtExpert);

        //价格
        PercentLinearLayout layoutPrice = new PercentLinearLayout(this.ctx);
        param = ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.heightPercent  = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f,true);
        param.mPercentLayoutInfo.rightMarginPercent  = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
        //param.topMargin = 20;
        //layoutPrice.setPadding(0,15,0,0);

        layoutPrice.setLayoutParams(param);
        layoutPrice.setOrientation(LinearLayout.HORIZONTAL);

        //是否支持图文会诊
        ImageView img=new ImageView(this.ctx);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        img.setAdjustViewBounds(true);
        //img.setPadding(20, 15, 0, 0);
        param.leftMargin = 27;
        param=ToolsUtil.getLayoutParam();
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
        //img.setPadding(10, 15, 10, 0);
        param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.10f,true);
        param.leftMargin = 10;

        if(doctor.getEnableVideochat().equals("Y")) {
            img.setImageResource(R.drawable.video1);
        }
        else{
            img.setImageResource(R.drawable.video2);
        }
        img.setLayoutParams(param);
        layoutPrice.addView(img);

        //价格
        TextView txtPrice = new TextView(this.ctx);
        PercentLinearLayout.LayoutParams paramPrice = ToolsUtil.getLayoutParam();
        txtPrice.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        //txtPrice.setPadding(0,0,10,0);
        txtPrice.setLayoutParams(paramPrice);
        tp = txtPrice.getPaint();
        tp.setFakeBoldText(true);
        txtPrice.setText("￥" + String.valueOf(doctor.getMaxPrice()) + "起");

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
