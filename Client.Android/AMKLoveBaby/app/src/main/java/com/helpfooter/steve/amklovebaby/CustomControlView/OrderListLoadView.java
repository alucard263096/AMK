package com.helpfooter.steve.amklovebaby.CustomControlView;

import android.content.Context;
import android.view.View;

import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.OrderListLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scai on 2015/9/15.
 */
public class OrderListLoadView  implements View.OnClickListener,IWebLoaderCallBack {

    Context ctx;
    PercentLinearLayout layout;
    ArrayList<OrderObj> lstOrder;
    public OrderListLoadView(Context ctx,PercentLinearLayout layout){
        this.ctx=ctx;
        this.layout=layout;
    }

    public void LoadList(){
        OrderListLoader loader=new OrderListLoader(this.ctx);
        loader.setCallBack(this);
        loader.start();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {
        OrderDao dao=new OrderDao(this.ctx);
        lstOrder=dao.getOrderList();

        


    }
}
