package com.helpfooter.steve.amklovebaby.DAO;

import android.content.Context;
import android.database.Cursor;

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
                "mobile varchar," +
                "age varchar," +
                "sex varchar," +
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
                "payment_time varchar," +
                "hascomment varchar," +
                "service int," +
                "ability int," +
                "comment varchar," +
                "tag varchar," +
                "sendmessage varchar )");
        util.execSQL(sql.toString(), new Object[]{});
    }

    @Override
    void insertObj(AbstractObj abobj) {
        util.open();

        OrderObj obj=(OrderObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_order (id,order_no,guid,member_id,name,mobile,sex,age,price,act,created_time,status,process_status," +
                "order_date,order_time,description,payment,payment_type,payment_time,tag,sendmessage" +
                ",hascomment,service,ability,comment ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getOrder_no(),obj.getGuid(),obj.getMember_id(),obj.getName(),obj.getMobile(),obj.getAge(),obj.getSex(),
                obj.getPrice(),obj.getAct(),obj.getCreated_time(),obj.getStatus(),obj.getProcess_status(),
                obj.getOrder_date(),obj.getOrder_time(),obj.getDescription(),obj.getPayment(),obj.getPayment_type(),obj.getPayment_time(),obj.getTag(),
                '0',obj.getHascomment(),obj.getService(),obj.getAbility(),obj.getComment()};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {

        util.open();

        OrderObj obj=(OrderObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_order set order_no=?,guid=?,member_id=?,name=?,mobile=?,age=?,sex=?,price=?,act=?,created_time=?,status=?,process_status=?," +
                "order_date=?,order_time=?,description=?,payment=?,payment_type=?,payment_time=?,tag=?" +
                ",hascomment=?,service=?,ability=?,comment=? where id=?");
        Object[] bindArgs = {obj.getOrder_no(),obj.getGuid(),obj.getMember_id(),obj.getName(),obj.getMobile(),obj.getAge(),obj.getSex(),
                obj.getPrice(),obj.getAct(),obj.getCreated_time(),obj.getStatus(),obj.getProcess_status(),
                obj.getOrder_date(),obj.getOrder_time(),obj.getDescription(),obj.getPayment(),obj.getPayment_type(),obj.getPayment_time(),obj.getTag()
                ,obj.getHascomment(),obj.getService(),obj.getAbility(),obj.getComment(), obj.getId()};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }

    public void updateSendMessage(OrderObj abobj) {

        util.open();

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_order set sendmessage='1' where id=?");
        Object[] bindArgs = {abobj.getId()};
        try {
            util.execSQL(sql.toString(), bindArgs);
        }
        catch (Exception ex)
        {
            throw ex;
        }

        util.close();
    }

    @Override
    AbstractObj newRealObj() {
        return new OrderObj();
    }




    public ArrayList<OrderObj> getOrderList(){
        ArrayList<AbstractObj> lst=  super.getList("  status<>'D' order by created_time desc ");
        ArrayList<OrderObj> lstr=new ArrayList<OrderObj>();
        for(AbstractObj abobj:lst){
            lstr.add((OrderObj)abobj);
        }
        return  lstr;

    }

    public Boolean getPaySucess(int id){
        ArrayList<AbstractObj> lst=  super.getList("  status='P' and id="+id );
        if(lst.size()==1)
        {
            return true;
        }
        else {
            return false;
        }

    }

    public OrderObj getLatestOrder(boolean isGetSent) {
        Cursor cursor = null;
        try {
            util.open();
            cursor = util
                    .rawQuery(
                            "select *," +
                                    "(strftime('%s',order_date|| ' ' ||order_time) - strftime('%s','now')) timespan from "+TableName+" " +
                                    "where 1=1 " +
                                    "and (strftime('%s',order_date|| ' ' ||order_time) - strftime('%s','now')) between 0 and 300 " +
                                    "and act='VC'" +
                                    "and status='P' " +
                                    (isGetSent?" and sendmessage<>'1'":"")+
                                    " order by order_date,order_time  " +
                                    "limit 0,1",new String[] { });
            while (cursor.moveToNext()) {
                int second=cursor.getInt(cursor.getColumnIndex("timespan"));
                OrderObj obj=new OrderObj();
                obj.parseCursor(cursor);
                return obj;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }



    public void setPaymentSuccess(int id) {
        util.open();
        String sql="update tb_order set status='P' where id=? ";
        Object[] bindArgs = {id};
        util.execSQL(sql.toString(), bindArgs);
        util.close();
    }
}
