package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;

import java.util.ArrayList;

/**
 * Created by scai on 2015/9/1.
 */
public class DoctorListLoadView {
    public LinearLayout mainlayout;
    public ArrayList<DoctorObj> lstDoctor;
    public Context ctx;
    public  DoctorListLoadView(Context ctx,LinearLayout layout,ArrayList<DoctorObj> lst){
        this.ctx=ctx;
        this.mainlayout=layout;
        this.lstDoctor=lst;
    }

    public void LoadDoctorListData(){
        int i=0;
        for(DoctorObj obj:lstDoctor){

            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param=getLayoutParam();
            param.mPercentLayoutInfo.heightPercent=0.2f;
            param.mPercentLayoutInfo.topMarginPercent=0.02f;
            param.mPercentLayoutInfo.bottomMarginPercent=0.02f;
            layout.setBackgroundColor(Color.GRAY);
            layout.setLayoutParams(param);
            this.mainlayout.addView(layout);

            i++;
        }
    }

    public PercentLinearLayout.LayoutParams getLayoutParam(){
        PercentLinearLayout.LayoutParams param=new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT,PercentLinearLayout.LayoutParams.MATCH_PARENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo=new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.fillLayoutParams(param, PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.MATCH_PARENT);
        param.mPercentLayoutInfo=layoutinfo;
        return param;
    }


}
