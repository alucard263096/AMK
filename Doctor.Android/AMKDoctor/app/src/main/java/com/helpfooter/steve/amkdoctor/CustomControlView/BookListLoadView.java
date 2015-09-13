package com.helpfooter.steve.amkdoctor.CustomControlView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.helpfooter.steve.amkdoctor.DAO.BookerDao;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.DAO.DoctorDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.R;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;
//import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by scai on 2015/9/1.
 */
public class BookListLoadView implements View.OnClickListener {
    public LinearLayout mainlayout;
    public ArrayList<BookerObj> lstBooker;
    public Context ctx;
    public  BookListLoadView(Context ctx,LinearLayout layout){
        this.ctx=ctx;
        this.mainlayout=layout;
        BookerDao dao=new BookerDao(this.ctx);
        ArrayList<AbstractObj> lst=dao.getBookerList();
        lstBooker=new ArrayList<BookerObj>();
        for(AbstractObj obj:lst){
            lstBooker.add((BookerObj)obj);
        }
    }

    public void LoadDoctorListData(){
        int i=1;
        for(BookerObj obj:lstBooker){
            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
            param.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.16f,false);
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,false);
            param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,false);
            //String backgroundcolor=getBackgroundColor(i);
            //layout.setBackgroundColor(Color.parseColor(backgroundcolor));
            layout.setLayoutParams(param);
            layout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout infofirstlayout=getFirstInfoLayout(obj);
            layout.addView(infofirstlayout);
            LinearLayout infosecondlayout=getSecondInfolayout(obj);
            layout.addView(infosecondlayout);

            layout.setTag(obj);
            layout.setOnClickListener(this);
            this.mainlayout.addView(layout);
            i++;
        }
    }


    public LinearLayout getFirstInfoLayout(BookerObj booker){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtName=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams nameparam=ToolsUtil.getLayoutParam();
        nameparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.33f,true);
        nameparam.mPercentLayoutInfo.textSizePercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.08f,true);
        nameparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        txtName.setGravity(Gravity.CENTER_VERTICAL);
        txtName.setText(booker.getCustname());
        txtName.setLayoutParams(nameparam);

        Button btnChart = new Button(this.ctx);
        PercentLinearLayout.LayoutParams chatparam=ToolsUtil.getLayoutParam();
        chatparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.33f,true);
        chatparam.mPercentLayoutInfo.textSizePercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.08f,true);
        chatparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        btnChart.setGravity(Gravity.CENTER_VERTICAL);
        btnChart.setLayoutParams(chatparam);
        btnChart.setText("开始视频");
        btnChart.setTag(booker);
        btnChart.setOnClickListener(this) ;

        Button btnCustInfo = new Button(this.ctx);
        PercentLinearLayout.LayoutParams custparam=ToolsUtil.getLayoutParam();
        custparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.34f,true);
        custparam.mPercentLayoutInfo.textSizePercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.08f,true);
        custparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        btnCustInfo.setGravity(Gravity.CENTER_VERTICAL);
        btnCustInfo.setLayoutParams(custparam);
        btnCustInfo.setText("查看资料");
        btnCustInfo.setTag(booker);
        btnCustInfo.setOnClickListener(this) ;



//        RatingBar ratio=new RatingBar(this.ctx);
//        ratio.setBackgroundColor(Color.RED);
//        PercentLinearLayout.LayoutParams ratioparam=getLayoutParam();
//        ratioparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.40f,true);
//        ratioparam.mPercentLayoutInfo.heightPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
//        ratio.setNumStars(5);
//        ratio.setRating(Float.parseFloat(String.valueOf(doctor.getGeneralScore())));
//        ratio.setLayoutParams(ratioparam);
//        nameRatiolayout.addView(ratio);

        layout.addView(txtName);
        layout.addView(btnChart);
        layout.addView(btnCustInfo);
        return  layout;
    }

    public LinearLayout getSecondInfolayout(BookerObj booker){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtName=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams nameparam=ToolsUtil.getLayoutParam();
        nameparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.33f,true);
        nameparam.mPercentLayoutInfo.textSizePercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.08f,true);
        nameparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        txtName.setGravity(Gravity.CENTER_VERTICAL);
        txtName.setText("预约时间");
        txtName.setLayoutParams(nameparam);

        TextView txtTime=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams timeparam=ToolsUtil.getLayoutParam();
        timeparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.67f,true);
        timeparam.mPercentLayoutInfo.textSizePercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.08f,true);
        timeparam.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
        txtTime.setGravity(Gravity.CENTER_VERTICAL);
        txtTime.setText(booker.getOrderdate() + booker.getOrdertime());
        txtTime.setLayoutParams(timeparam);


        layout.addView(txtName);
        layout.addView(txtTime);
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

       // DoctorObj obj=(DoctorObj)v.getTag();
       // Intent intent = new Intent(this.ctx, DoctorDetailActivity.class);
        //intent.putExtra("Id", obj.getId());
       // this.ctx.startActivity(intent);
    }
}
