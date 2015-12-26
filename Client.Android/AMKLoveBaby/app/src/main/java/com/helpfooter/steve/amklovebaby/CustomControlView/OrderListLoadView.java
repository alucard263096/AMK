package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IOrderListView;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.OrderListLoader;
import com.helpfooter.steve.amklovebaby.OrderDetailActivity;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.ArrayList;

/**
 * Created by scai on 2015/9/15.
 */
public class OrderListLoadView  implements View.OnClickListener,IWebLoaderCallBack {

    Activity ctx;
    PercentLinearLayout layout;
    ImageView imgNoOrder;
    TextView txtNoOrder;
    ArrayList<OrderObj> lstOrder;
    String filterstatus="";

    ArrayList<TextView> filterTV=new ArrayList<TextView>();

    ArrayList<LinearLayout> lstLayout=new ArrayList<LinearLayout>();

    public OrderListLoadView(Activity ctx,PercentLinearLayout layout,ImageView imgNoOrder,TextView txtNoOrder,String status){
        this.ctx=ctx;
        this.layout=layout;
        filterstatus=status;
        this.imgNoOrder=imgNoOrder;
        this.txtNoOrder=txtNoOrder;
    }

    public void AddFilterTV(TextView tv){
        filterTV.add(tv);
    }

    public void setfilterstatus(String status) {
        this.filterstatus = status;
    }


    public void LoadList(){
        OrderListLoader loader=new OrderListLoader(this.ctx);
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    public void onClick(View v) {
        OrderObj obj=(OrderObj)v.getTag();
        Intent intent = new Intent(ctx, OrderDetailActivity.class);
        intent.putExtra("Id", obj.getId());
        ctx.startActivity(intent);
    }

    private Handler onloadAllHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            OnloadOrder();
            Filter(filterstatus);
        }
    };

    int i=0;
    private void OnloadOrder() {
        i=0;
        for(OrderObj obj:lstOrder){
            try {
                obj.LoadDoctorObj(ctx);
                LinearLayout sublayout=getOrderLayout(obj);
                i++;
                if (sublayout != null) {
                    sublayout.setTag(obj);
                    sublayout.setOnClickListener(this);
                    //layout.addView(sublayout);
                    lstLayout.add(sublayout);
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private LinearLayout getOrderLayout(IOrderListView order){
        PercentLinearLayout fullLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams fullLayoutparam= ToolsUtil.getLayoutParamHeightWrap();
        fullLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        fullLayout.setOrientation(LinearLayout.VERTICAL);
        fullLayout.setLayoutParams(fullLayoutparam);




        PercentLinearLayout mainLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.topMargin=20;
        param.mPercentLayoutInfo.leftMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setLayoutParams(param);

        LinearLayout leftLayout=getLeftLayout(order);
        mainLayout.addView(leftLayout);


        LinearLayout rightLayout=getRightLayout(order);
        mainLayout.addView(rightLayout);

        fullLayout.addView(mainLayout);
        LinearLayout line=ToolsUtil.GenPLine(ctx);
        fullLayout.addView(line);
        return fullLayout;
    }

    private LinearLayout getRightLayout(IOrderListView order) {
        PercentLinearLayout mainLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.75f,true);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(param);

        TextView txtTitle=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtTitleparam= ToolsUtil.getLayoutParamHeightWrap();
        txtTitle.setLayoutParams(txtTitleparam);
        txtTitle.setTextSize(17);
        txtTitle.setText(order.OrderTitle());
        mainLayout.addView(txtTitle);


        TextView txtPrice=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtPriceparam= ToolsUtil.getLayoutParamHeightWrap();
        txtPrice.setLayoutParams(txtPriceparam);
        txtPrice.setTextSize(14);
        txtPrice.setText("预约价格: " + order.OrderPrice());
        mainLayout.addView(txtPrice);



        TextView txtBooking=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtBookingparam= ToolsUtil.getLayoutParamHeightWrap();
        txtBooking.setLayoutParams(txtBookingparam);
        txtBooking.setText("预约时间: " + order.OrderBookingTime());
        mainLayout.addView(txtBooking);

        if(!order.OrderTips().isEmpty()) {
            TextView txtTips = new MyTextView(ctx);
            PercentLinearLayout.LayoutParams txtTipsparam = ToolsUtil.getLayoutParamHeightWrap();
            txtTips.setLayoutParams(txtTipsparam);
            txtTips.setTextColor(Color.RED);
            txtTips.setGravity(Gravity.RIGHT);
            txtTips.setText(order.OrderTips());
            mainLayout.addView(txtTips);
        }
        return mainLayout;
    }

    private LinearLayout getLeftLayout(IOrderListView order) {

        PercentLinearLayout mainLayout=new PercentLinearLayout(this.ctx);
        PercentLinearLayout.LayoutParams param= ToolsUtil.getLayoutParamHeightWrap();
        param.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.21f,true);
        param.mPercentLayoutInfo.rightMarginPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.04f,true);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(param);

/*        CircleImageView1 img = new CircleImageView1(this.ctx);
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
        img.setLayoutParams(param);*/

        CircleImageView1 img=new CircleImageView1(ctx);
        img.setScaleType(CircleImageView.ScaleType.CENTER_CROP);
        img.setAdjustViewBounds(false);

        PercentLinearLayout.LayoutParams imgparam= ToolsUtil.getLayoutParamHeightWrap();
        //imgparam.mPercentLayoutInfo.widthPercent=new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.20f,true);
        imgparam.height = 200;
        imgparam.width = 200;
        imgparam.topMargin=30;

        /*img.setAdjustViewBounds(true);
        img.setScaleType(ImageView.ScaleType.FIT_START);*/
        img.setLayoutParams(imgparam);
        String url=order.GetPhotoUrl();
        UrlImageLoader imageLoader=new UrlImageLoader(img,url);
        imageLoader.start();

        TextView txt=new MyTextView(ctx);
        PercentLinearLayout.LayoutParams txtparam= ToolsUtil.getLayoutParamHeightWrap();
        txt.setLayoutParams(txtparam);
        txt.setGravity(Gravity.CENTER);
        txt.setText(order.PhotoName());

        mainLayout.addView(img);
        mainLayout.addView(txt);
        return mainLayout;
    }
    private Handler ccaHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            for(TextView tv:filterTV){
                GetOrderCount(tv);
            }
        }
    };

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        OrderDao dao=new OrderDao(this.ctx);
        lstOrder=dao.getOrderList();
        onloadAllHandler.sendEmptyMessage(0);
        ccaHandler.sendEmptyMessage(0);
    }

    public void GetOrderCount(TextView tv){
        int count=0;
        boolean haveNotice=false;
        String filter=String.valueOf(tv.getTag());
        for(LinearLayout lt:lstLayout){
            IOrderListView obj=(IOrderListView)lt.getTag();
            if(filter.equals(obj.GetStatus())){
                count++;
            }

             if(!obj.OrderTips().isEmpty()){
                 haveNotice=true;
             }
        }
        String tvStr=tv.getText().toString();
        String ret=tvStr+"("+String.valueOf(count)+")";
        tv.setText(ret);
//        if(haveNotice){
//            tv.setTextColor(Color.RED);
//        }
    }

    public void Filter(String filter) {
        boolean haveone=false;
        layout.removeAllViews();


        for(LinearLayout lt:lstLayout){
            OrderObj obj=(OrderObj)lt.getTag();

            if(filter.isEmpty()||filter.equals(obj.getStatus())){
                layout.addView(lt);
                haveone=true;
            }
        }
        if(haveone==false){
            layout.addView(imgNoOrder);
            layout.addView(txtNoOrder);
        }
        //((ScrollView)this.ctx.findViewById(R.id.scrollView)).setTop(0);
    }
}
