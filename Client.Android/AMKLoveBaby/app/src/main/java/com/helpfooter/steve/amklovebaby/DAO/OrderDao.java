package com.helpfooter.steve.amklovebaby.DAO;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scai on 2015/9/15.
 */
public class OrderDao extends AbstractDao {

    static boolean hascheckcreate=false;

    public OrderDao(Context ctx) {
        super(ctx, "tb_order");
    }

    public void createTable(){
        if(hascheckcreate==false){
            gotoCreateTableSql();
            hascheckcreate=true;
        }
    }

    @Override
    void gotoCreateTableSql() {
        util.open();
        StringBuffer sql = new StringBuffer();



        sql.append("create table IF NOT EXISTS  tb_order " +
                "(id int," +
                "order_no varchar ," +
                "guid varchar," +
                "member_id int," +
                "name varchar," +
                "mobile int," +
                "price int," +
                "act varchar," +
                "created_time varchar," +
                "status varchar," +
                "process_status varchar," +
                "order_date varchar," +
                "order_time varchar," +
                "description varchar," +
                "payment varchar," +
                "payment_type varchar," +
                "payment_time varchar )");
        util.execSQL(sql.toString(), new Object[]{});
    }

    @Override
    void insertObj(AbstractObj abobj) {
        util.open();

        OrderObj obj=(OrderObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_order (id,order_no,guid,member_id,name,mobile,price,act,created_time,status,process_status," +
                "order_date,order_time,description,payment,payment_type,payment_time ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getOrder_no(),obj.getGuid(),obj.getMember_id(),obj.getName(),obj.getMobile(),
                obj.getPrice(),obj.getAct(),obj.getCreated_time(),obj.getProcess_status(),
                obj.getOrder_date(),obj.getOrder_time(),obj.getDescription(),obj.getPayment(),obj.getPayment_type(),obj.getPayment_time()};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }

    @Override
    void updateObj(AbstractObj abobj) {

        util.open();

        OrderObj obj=(OrderObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_order set order_no=?,guid=?,member_id=?,name=?,mobile=?,price=?,act=?,created_time=?,status=?,process_status=?," +
                "order_date=?,order_time=?,description=?,payment=?,payment_type=?,payment_time=? where id=?");
        Object[] bindArgs = {obj.getOrder_no(),obj.getGuid(),obj.getMember_id(),obj.getName(),obj.getMobile(),
                obj.getPrice(),obj.getAct(),obj.getCreated_time(),obj.getProcess_status(),
                obj.getOrder_date(),obj.getOrder_time(),obj.getDescription(),obj.getPayment(),obj.getPayment_type(),obj.getPayment_time(),obj.getId()};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }

    @Override
    AbstractObj newRealObj() {
        return new OrderObj();
    }

    public ArrayList<OrderObj> getOrderList(){
        ArrayList<AbstractObj> lst=  super.getList(" where status!='D' order by desc created_date");
        ArrayList<OrderObj> lstr=new ArrayList<OrderObj>();
        for(AbstractObj abobj:lst){
            lstr.add((OrderObj)abobj);
        }
        return  lstr;

    }
}
