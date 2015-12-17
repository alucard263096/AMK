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
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DoctorDetailActivity;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;

/**
 * Created by scai on 2015/9/1.
 */
public class FollowDoctorListLoadView extends DoctorListLoadView  {
    public FollowDoctorListLoadView(Context ctx, LinearLayout layout) {
        super(ctx, layout," and 1=2 ");
        DoctorDao dao=new DoctorDao(this.ctx);
        ArrayList<AbstractObj> lst=dao.getDoctorListWithFollow();
        lstDoctor=new ArrayList<DoctorObj>();
        for(AbstractObj obj:lst){
            lstDoctor.add((DoctorObj)obj);
        }
    }

    public void LoadDoctorListData(){
        if(lstDoctor.size()==0){
            TextView txtEmpty=new MyTextView(this.ctx);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParam();
            param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.9f,true);
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f, true);
            param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f, true);
            txtEmpty.setLayoutParams(param);
            txtEmpty.setText("你还没有关注任何医生，请前往医生列表进行关注");
            txtEmpty.setTextColor(Color.parseColor("#cccccc"));

            this.mainlayout.addView(txtEmpty);

        }else {
            super.LoadDoctorListData();
        }

    }
}
