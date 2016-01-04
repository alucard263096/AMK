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
import com.helpfooter.steve.amklovebaby.DAO.NewsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.NewsDetailActivity;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;

/**
 * Created by scai on 2015/9/1.
 */
public class NewsListLoadView implements View.OnClickListener {
    public LinearLayout mainlayout;
    public ArrayList<NewsObj> lstNews;
    public Context ctx;
    public NewsListLoadView(Context ctx, LinearLayout layout){
        this.ctx=ctx;
        this.mainlayout=layout;
        NewsDao dao=new NewsDao(this.ctx);
        ArrayList<AbstractObj> lst=dao.getNewsList();
        Log.i("news_number",String.valueOf(lst.size()));
        lstNews=new ArrayList<NewsObj>();
        for(AbstractObj obj:lst){
            lstNews.add((NewsObj)obj);
        }
    }

    public void LoadNewsListData(){
        int i=1;
        for(NewsObj obj:lstNews){
            PercentLinearLayout layout=new PercentLinearLayout(ctx);
            layout.setBackgroundColor(Color.parseColor("#ffffff"));
            layout.setPadding(40, 20, 40, 20);
            PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
            param.setMargins(0, 15, 0, 0);
            layout.setLayoutParams(param);
            layout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout top=GetTopLayout(obj);
            layout.addView(top);
            LinearLayout line=ToolsUtil.GenPLine(this.ctx);
            layout.addView(line);

            TextView txtGood=new MyTextView(this.ctx);
            PercentLinearLayout.LayoutParams txtGoodparam=ToolsUtil.getLayoutParamHeightWrap();
            txtGoodparam.setMargins(0,20,0,0);
            //txtSummary.setBackgroundColor(Color.parseColor("#ffccee"));
            txtGood.setGravity(Gravity.RIGHT);
            txtGood.setLayoutParams(txtGoodparam);
            txtGood.setTextColor(Color.GRAY);
            txtGood.setText("赞(" + obj.getUpvote() + ")");
            txtGood.setTextSize(10);
            layout.addView(txtGood);

            layout.setTag(obj);
            layout.setOnClickListener(this);
            this.mainlayout.addView(layout);

            i++;
        }
    }

    private LinearLayout GetTopLayout(NewsObj obj) {

        PercentLinearLayout layout=new PercentLinearLayout(ctx);
        layout.setPadding(20, 20, 20, 20);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.setMargins(0, 20, 0, 20);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imgPhoto=getPhotoView(obj);
        layout.addView(imgPhoto);
        LinearLayout infolayout=getInfoLayout(obj);
        layout.addView(infolayout);

        return layout;
    }

    public ImageView getPhotoView(NewsObj news){
        ImageView img=new ImageView(this.ctx);
        img.setScaleType(ImageView.ScaleType.FIT_START);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.3f,true);
        String url= StaticVar.ImageFolderURL+"news/"+news.getThumbnail();
        img.setAdjustViewBounds(true);
        UrlImageLoader imgLoad=new UrlImageLoader(img,url);
        imgLoad.start();;
        img.setLayoutParams(param);

        return img;
    }

    public LinearLayout getInfoLayout(NewsObj news){
        PercentLinearLayout layout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param=ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.03f,true);
        layout.setLayoutParams(param);
        layout.setOrientation(LinearLayout.VERTICAL);


        TextView txtTitle=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams titleparam=ToolsUtil.getLayoutParamHeightWrap();
        txtTitle.setTextSize(15);
        //txtTitle.setBackgroundColor(Color.parseColor("#ffccaa"));
        //txtTitle.setGravity(Gravity.CENTER_VERTICAL);
        txtTitle.setLayoutParams(titleparam);
        txtTitle.setText(news.getTitle());
        TextPaint tp= txtTitle.getPaint();
        tp.setFakeBoldText(true);
        Log.i("news_title", news.getTitle());

        TextView txtSummary=new MyTextView(this.ctx);
        PercentLinearLayout.LayoutParams summaryparam=ToolsUtil.getLayoutParamHeightWrap();
        //txtSummary.setBackgroundColor(Color.parseColor("#ffccee"));
        txtSummary.setGravity(Gravity.CENTER_VERTICAL);
        txtSummary.setLayoutParams(summaryparam);
        txtSummary.setTextColor(Color.GRAY);
        txtSummary.setText(news.getSummary());
        txtSummary.setTextSize(10);
        txtSummary.setPadding(0, -9, 0, -9);
        Log.i("news_summary", news.getSummary());

//
//        PercentLinearLayout tipsLayout=new PercentLinearLayout(this.ctx);
//        //tipsLayout.setBackgroundColor(Color.parseColor("#ffaaee"));
//        PercentLinearLayout.LayoutParams tipsparam=ToolsUtil.getLayoutParamHeightWrap();
//        tipsparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1f,true);
//        tipsLayout.setLayoutParams(tipsparam);
//        tipsLayout.setOrientation(LinearLayout.HORIZONTAL);

//        TextView txtPublishDate=new MyTextView(this.ctx);
//        PercentLinearLayout.LayoutParams publishdateparam= ToolsUtil.getLayoutParamHeightWrap();
//        publishdateparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
//        txtPublishDate.setLayoutParams(publishdateparam);
//        txtPublishDate.setText(news.getPublish_date());
//        txtPublishDate.setTextSize(10);
//        tipsLayout.addView(txtPublishDate);
//
//        TextView txtUpvote=new MyTextView(this.ctx);
//        PercentLinearLayout.LayoutParams upvoteparam= ToolsUtil.getLayoutParamHeightWrap();
//        upvoteparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f,true);
//        txtUpvote.setLayoutParams(upvoteparam);
//        txtUpvote.setGravity(Gravity.RIGHT);
//        txtUpvote.setText("赞(" + news.getUpvote() + ")");
//        txtUpvote.setTextSize(10);
//        tipsLayout.addView(txtUpvote);

        layout.addView(txtTitle);
        layout.addView(txtSummary);
        //layout.addView(tipsLayout);
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

    public void FilterByCategory(String category){

        int count=mainlayout.getChildCount();
        for(int i=0;i<count;i++){
           LinearLayout layout=(LinearLayout)mainlayout.getChildAt(i);
            NewsObj obj=(NewsObj)layout.getTag();
            if(obj.getCategory().equals(category)){
                layout.setVisibility(LinearLayout.VISIBLE);
            }else {
                layout.setVisibility(LinearLayout.GONE);
            }
        }

    }




    @Override
    public void onClick(View v) {
        NewsObj obj=(NewsObj)v.getTag();
        Intent intent = new Intent(this.ctx, NewsDetailActivity.class);
        intent.putExtra("Id", obj.getId());
        this.ctx.startActivity(intent);
    }
}
