package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
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

        PercentLinearLayout nameRatiolayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams nameRatioparam=ToolsUtil.getLayoutParamHeightWrap();
        nameRatiolayout.setLayoutParams(nameRatioparam);
        nameRatiolayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtName=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams nameparam=ToolsUtil.getLayoutParamHeightWrap();
        nameparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.35f,true);
        nameparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        txtName.setLayoutParams(nameparam);
        txtName.setTextSize(17);
        txtName.setText(doctor.getName());
        TextPaint tp= txtName.getPaint();
        tp.setFakeBoldText(true);
        nameRatiolayout.addView(txtName);



//        RatingBar ratio=new RatingBar(this.ctx);
//        ratio.setBackgroundColor(Color.RED);
//        PercentLinearLayout.LayoutParams ratioparam=getLayoutParam();
//        ratioparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.40f,true);
//        ratioparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
//        ratio.setNumStars(5);
//        ratio.setRating(Float.parseFloat(String.valueOf(doctor.getGeneralScore())));
//        ratio.setLayoutParams(ratioparam);
//        nameRatiolayout.addView(ratio);


        TextView txtOfficePosition=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams officeparam=ToolsUtil.getLayoutParamHeightWrap();
        txtOfficePosition.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
        txtOfficePosition.setLayoutParams(officeparam);
        txtOfficePosition.setText(doctor.getOffice() + "/" + doctor.getTitle());
        nameRatiolayout.addView(txtOfficePosition);

        TextView txtExpert=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams expertparam= ToolsUtil.getLayoutParamHeightWrap();
        expertparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        txtExpert.setLayoutParams(expertparam);
        txtExpert.setText(doctor.getExpert());

        layout.addView(nameRatiolayout);
        layout.addView(txtExpert);
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
