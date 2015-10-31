package com.helpfooter.steve.amklovebaby.CustomObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.OrderDetailActivity;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UnknownFormatConversionException;

/**
 * Created by Steve on 2015/10/31.
 */
public class VideoChatNotice extends Thread {

    ArrayList<Integer> alertedIdList=new ArrayList<Integer>();
    boolean inPopup=false;

    public void run(){
        while (true){
            try{
                if(StaticVar.Member!= null){
                    if(StaticVar.CurrentActivity!=null){
                        if(StaticVar.CurrentActivity.PopupNotice()){
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                }
                Thread.sleep(1000*60);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            RealDo();
        }
    };

    private void RealDo() {
        OrderDao dao=new OrderDao(StaticVar.CurrentActivity.GetMyActivity());
        final OrderObj order= dao.getLatestOrder();
        if(order!=null){
            final int id=order.getId();
            boolean hastip=false;
            for(Integer oid:alertedIdList){
                if(oid==id){
                    hastip=true;
                    break;
                }
            }
            if(hastip==false){
                inPopup=true;
//                Toast.makeText(StaticVar.CurrentActivity.GetMyActivity(),
//                        "你有一个视频会诊将于"+order.getOrder_date()+" "+order.getOrder_time()+"开始。",
//                        Toast.LENGTH_LONG).show();

                AlertDialog dialog;
                final AlertDialog.Builder builer = new AlertDialog.Builder(StaticVar.CurrentActivity.GetMyActivity()) ;
                builer.setTitle("消息提醒");
                builer.setMessage("你有一个视频会诊将于" + order.getOrder_date() + " " + order.getOrder_time() + "开始，是否立刻打开预约?");
                //当点确定按钮时从服务器上下载 新的apk 然后安装
                builer.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(StaticVar.CurrentActivity.GetMyActivity(), OrderDetailActivity.class);
                        intent.putExtra("Id", id);
                        StaticVar.CurrentActivity.GetMyActivity().startActivity(intent);
                        inPopup = false;
                    }
                });
                //当点取消按钮时进行登录
                builer.setNegativeButton("等会", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        inPopup=false;
                    }
                });
                dialog = builer.create();
                dialog.show();
                alertedIdList.add(id);
            }
        }
    }
}
