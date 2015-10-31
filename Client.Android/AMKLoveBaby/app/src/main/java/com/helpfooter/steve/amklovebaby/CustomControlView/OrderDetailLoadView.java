package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.ChatActivity;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.OrderCommentActivity;
import com.helpfooter.steve.amklovebaby.OrderPaymentActivity;
import com.helpfooter.steve.amklovebaby.R;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;
import com.helpfooter.steve.amklovebaby.VideoChatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Steve on 2015/9/22.
 */
public class OrderDetailLoadView implements View.OnClickListener {
    Activity ctx;
    OrderObj order;
    public OrderDetailLoadView(Activity ctx, OrderObj order) {
        this.ctx = ctx;
        this.order = order;
    }


    public void AddSetOrderNext(){
        Button btnSubmit=(Button)ctx.findViewById(R.id.btnSubmit);
        btnSubmit.setText(order.getStatusAction());
        if(order.getStatus().equals("P")&&order.getAct().equals("VC")){
            //SetGoVideoChat st=new SetGoVideoChat(order,btnSubmit);
            //st.start();

            btnSubmit.setOnClickListener(this);
        }else  if(order.getStatus().equals("T")&&order.getAct().equals("VC")){
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm");
            try {
                Date date=format.parse(order.getOrder_date()+" "+order.getOrder_time());
                long withouttime= date.getTime()-(new    Date(System.currentTimeMillis())).getTime();
                if(withouttime<3600*1000){
                    btnSubmit.setText("已超过预约时间");
                    btnSubmit.setBackgroundColor(Color.parseColor("#cccccc"));
                }else {
                    btnSubmit.setOnClickListener(this);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(order.getStatus().equals("F")){
            if(!order.getHascomment().equals("Y")){
                btnSubmit.setText("立即评价");
            }else {
                btnSubmit.setText("查看评价");
            }
            btnSubmit.setOnClickListener(this);
        }
        else {
            btnSubmit.setOnClickListener(this);
        }
    }

    public class SetGoVideoChat extends Thread{
        OrderObj order;
        Button btn;
        public SetGoVideoChat(OrderObj order,Button btn){
            this.order=order;
            this.btn=btn;
        }
        public void run(){
            try {
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date date=format.parse(order.getOrder_date()+" "+order.getOrder_time());
                while (true){
                    long withouttime= date.getTime()-(new    Date(System.currentTimeMillis())).getTime();
                    if(withouttime<=15*1000*60&&withouttime>30*1000*60){
                        e1Handler.sendEmptyMessage(0);
                        return;
                    }else if(withouttime<=30*1000*60){
                        e2Handler.sendEmptyMessage(0);
                        return;
                    } else {
                        e3Handler.sendEmptyMessage(0);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        private android.os.Handler e1Handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                btn.setOnClickListener(OrderDetailLoadView.this);
                btn.setText("进入视频会诊室");
            }
        };
        private android.os.Handler e2Handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                btn.setText("已经超过视频会诊时间");
                btn.setBackgroundColor(Color.parseColor("#cccccc"));
            }
        };
        private android.os.Handler e3Handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                btn.setText("等待进入视频会诊室");
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(order.getStatus().equals("T")){
            Intent intent = new Intent(ctx, OrderPaymentActivity.class);
            intent.putExtra("Id", order.getId());
            ctx.startActivity(intent);
        }else if(order.getStatus().equals("P")&&order.getAct().equals("VC")) {
            //
            Intent intent = new Intent(ctx, VideoChatActivity.class);
            intent.putExtra("Id", order.getId());
            ctx.startActivity(intent);
        }else if(order.getStatus().equals("P")&&order.getAct().equals("CC")) {
            Intent intent = new Intent(this.ctx, ChatActivity.class);
            intent.putExtra("orderId", String.valueOf(order.getId()));
            intent.putExtra("tag", String.valueOf(order.getTag()));
            this.ctx.startActivity(intent);
        }else if(order.getStatus().equals("F")){
            Intent intent = new Intent(this.ctx, OrderCommentActivity.class);
            intent.putExtra("Id", order.getId());
            this.ctx.startActivity(intent);
        }
    }


}
