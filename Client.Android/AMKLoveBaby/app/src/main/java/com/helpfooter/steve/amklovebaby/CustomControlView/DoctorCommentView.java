package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.CommentObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/10/20.
 */
public class DoctorCommentView {
    Context ctx;
    ArrayList<AbstractObj> lstComment;
    PercentLinearLayout sv;
    public DoctorCommentView(Context ctx,PercentLinearLayout sv){
        this.ctx=ctx;
        this.sv=sv;
    }

    public void showCommentAll(ArrayList<AbstractObj> lstComment){
        this.lstComment=lstComment;
        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            realShowCommentAll();
        }
    };

    private void realShowCommentAll(){
        if(lstComment.size()==0){
            MyTextView txtMemberName=new MyTextView(ctx);
            PercentLinearLayout.LayoutParams txtMemberNameparam= ToolsUtil.getLayoutParamHeightWrap();
            txtMemberName.setLayoutParams(txtMemberNameparam);
            txtMemberName.setText("暂无用户评论");
            sv.addView(txtMemberName);
        }
        for(AbstractObj obj:lstComment){
            CommentObj comment=(CommentObj)obj;

            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
            param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.9f,true);
            param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.05f,true);
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
            param.mPercentLayoutInfo.bottomMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,true);
            layout.setLayoutParams(param);
            layout.setOrientation(LinearLayout.VERTICAL);

            MyTextView txtMemberName=new MyTextView(ctx);
            PercentLinearLayout.LayoutParams txtMemberNameparam= ToolsUtil.getLayoutParamHeightWrap();
            txtMemberName.setLayoutParams(txtMemberNameparam);
            txtMemberName.setText(ToolsUtil.earseMobileNo(comment.getMember_name()));
            txtMemberName.setTextSize(15);
            layout.addView(txtMemberName);


            PercentLinearLayout layoutld=new PercentLinearLayout(ctx);
            PercentLinearLayout.LayoutParams layoutldparam= ToolsUtil.getLayoutParamHeightWrap();
            param.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.02f,false);
            layoutld.setLayoutParams(layoutldparam);
            //layoutld.setBackgroundColor(Color.parseColor("#ccaaee"));
            layoutld.setOrientation(LinearLayout.HORIZONTAL);


            MyTextView txtService=new MyTextView(ctx);
            PercentLinearLayout.LayoutParams txtServiceNameparam= ToolsUtil.getLayoutParamHeightWrap();
            txtServiceNameparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,true);
            txtService.setLayoutParams(txtServiceNameparam);
            txtService.setText("服务态度:  "+String.valueOf(comment.getService()));
            txtService.setTextSize(12);
            layoutld.addView(txtService);

            MyTextView txtAbility=new MyTextView(ctx);
            PercentLinearLayout.LayoutParams txtAbilityparam= ToolsUtil.getLayoutParamHeightWrap();
            txtAbilityparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,true);
            txtAbility.setLayoutParams(txtAbilityparam);
            txtAbility.setText(ToolsUtil.earseMobileNo(comment.getMember_name()));
            txtAbility.setTextSize(12);
            txtAbility.setText("医疗水平:  " + String.valueOf(comment.getAbility()));
            layoutld.addView(txtAbility);

            MyTextView txtCommentDate=new MyTextView(ctx);
            PercentLinearLayout.LayoutParams txtCommentDateparam= ToolsUtil.getLayoutParamHeightWrap();
            txtCommentDateparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.6f,true);
            txtCommentDate.setLayoutParams(txtCommentDateparam);
            txtCommentDate.setText(ToolsUtil.earseMobileNo(comment.getMember_name()));
            txtCommentDate.setTextSize(12);
            txtCommentDate.setGravity(Gravity.RIGHT);
            txtCommentDate.setText(comment.getComment_date());
            layoutld.addView(txtCommentDate);

            layout.addView(layoutld);


            MyTextView txtComment=new MyTextView(ctx);
            PercentLinearLayout.LayoutParams txtCommentparam= ToolsUtil.getLayoutParamHeightWrap();
            txtCommentparam.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.06f,false);
            txtComment.setLayoutParams(txtCommentparam);
            txtComment.setText(comment.getComment());
            if(comment.getComment().trim().length()==0){
                txtComment.setText("无");
                txtComment.setTextColor(Color.parseColor("#cccccc"));
            }
            layout.addView(txtComment);

            if(comment.getReply().trim().length()>0){

                MyTextView txtReply=new MyTextView(ctx);
                PercentLinearLayout.LayoutParams txtReplyparam= ToolsUtil.getLayoutParamHeightWrap();
                txtReplyparam.mPercentLayoutInfo.topMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.06f,false);
                txtReply.setLayoutParams(txtReplyparam);
                txtReply.setText("医生回复:"+comment.getReply());
                txtReply.setTextSize(12);
                layout.addView(txtReply);
            }

            sv.addView(layout);
            LinearLayout line=ToolsUtil.GenPLine(ctx);
            sv.addView(line);
        }
    }

}
