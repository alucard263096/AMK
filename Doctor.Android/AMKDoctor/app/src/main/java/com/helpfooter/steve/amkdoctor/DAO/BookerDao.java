package com.helpfooter.steve.amkdoctor.DAO;


import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Loader.BookerLoader;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/31.
 */
public class BookerDao extends AbstractDao {


    public BookerDao(Context ctx) {
        super(ctx, "tb_booker");
    }


    public ArrayList<AbstractObj> getBookerList(){
      return   super.getList(" status='P' order by orderdate desc,ordertime desc");
    }

    @Override
    void gotoCreateTableSql() {
        util.open();
       StringBuffer sql = new StringBuffer();
        sql.append("create table IF NOT EXISTS  tb_booker " +
                "(id int," +
                "bookno varchar," +
                "custid varchar," +
                "custname varchar," +
                "mobile varchar," +
                "price varchar," +
                "createtime varchar," +
                "status varchar," +
                "precessstatus varchar," +
                "payment varchar," +
                "orderdate varchar," +
                "ordertime varchar," +
                "doctorid int," +
                "chattime varchar," +
                "description varchar)");
        util.execSQL(sql.toString(), new Object[]{});


    }
    static boolean hascheckcreate=false;
    public void createTable(){
        if(hascheckcreate==false){
            gotoCreateTableSql();
            hascheckcreate=true;
        }
    }

    @Override
    public void insertObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
        util.open();

        BookerObj obj=(BookerObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_booker (id,bookno,custid,custname,mobile,price,createtime,status,precessstatus,payment,orderdate,ordertime,doctorid,chattime,description" +
                ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getBookno(),obj.getCustid(),obj.getCustname(),obj.getMobile(),obj.getPrice(),obj.getCreatetime(),obj.getStatus(),obj.getPrecessstatus(),obj.getPayment()
        ,obj.getOrderdate(),obj.getOrdertime(),obj.getDoctorid(),obj.getChattime(),obj.getDescription()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
       /*
        util.open();

        DoctorObj obj=(DoctorObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_booker set license=?, name=?,photo=?,title=?, office=?, bookingtime=?, introduce=?, credentials=?, expert=?" +
                ",enable_videochat=?,videochat_price=?, enable_charchat=?, charchat_price=?, status=?,general_score=?,querycount=? where id=? ");
        Object[] bindArgs = {obj.getLicense(),obj.getName(),obj.getPhoto(),obj.getTitle(),obj.getOffice(),obj.getBookingtime(),obj.getIntroduce(),obj.getCredentials(),obj.getExpert()
                ,obj.getEnableVideochat(),obj.getVideochatPrice(),obj.getEnableCharchat(),obj.getCharchatPrice(),obj.getStatus(),obj.getGeneralScore(),obj.getQuerycount(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
        */
    }





    @Override
    AbstractObj newRealObj() {
        return new BookerObj();
    }

}
