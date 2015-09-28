package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

/**
 * Created by Steve on 2015/9/28.
 */
public class OrderSubmitInfoView  {

    private  Activity ctx;
    private  LinearLayout mainLayout;

    int insert_index=3;

    public OrderSubmitInfoView(Activity ctx,LinearLayout mainLayout){
        this.ctx=ctx;
        this.mainLayout=mainLayout;
    }

    public void AddDisplayField(String field,String value){
        PercentLinearLayout layout = new PercentLinearLayout(ctx);
        PercentLinearLayout.LayoutParams layoutParams = ToolsUtil.getLayoutParam();
        layoutParams.mPercentLayoutInfo.heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.07f, false);
        layoutParams.mPercentLayoutInfo.topMarginPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.03f, false);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(layoutParams);

        MyTextView txtField=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtFieldlayoutParams = ToolsUtil.getLayoutParam();
        txtFieldlayoutParams.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);
        txtField.setBackgroundResource(R.color.mylightblue);
        txtField.setTextColor(Color.parseColor("#ffffff"));
        txtField.setText(field);
        txtField.setLayoutParams(txtFieldlayoutParams);
        txtField.setGravity(Gravity.CENTER);
        layout.addView(txtField);

        MyTextView txtValue=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtValuelayoutParams = ToolsUtil.getLayoutParam();
        txtValuelayoutParams.mPercentLayoutInfo.widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.75f, true);
        txtValue.setBackgroundColor(Color.parseColor("#cccccc"));
        txtValue.setTextColor(Color.parseColor("#ffffff"));
        txtValue.setText(value);
        txtValue.setGravity(Gravity.CENTER);
        txtValue.setLayoutParams(txtValuelayoutParams);
        layout.addView(txtValue);

        this.mainLayout.addView(layout,insert_index++);
    }


}
