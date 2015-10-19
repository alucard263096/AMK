package com.helpfooter.steve.amkdoctor.DAO;


import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Loader.BookerLoader;

import java.sql.Date;
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

    public BookerObj getObj(int bookId){
        ArrayList<AbstractObj> lst=  super.getList("  id= "+bookId);
           for(AbstractObj abobj:lst){
            return (BookerObj)abobj;
        }
        return null;

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
                "orderdate TEXT," +
                "ordertime TEXT," +
                "doctorid int," +
                "chattime varchar," +
                "member_photo varchar," +
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
        sql.append("insert into tb_booker (id,bookno,custid,custname,mobile,price,createtime,status,precessstatus,payment,orderdate,ordertime,doctorid,chattime,description,member_photo" +
                ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getBookno(),obj.getCustid(),obj.getCustname(),obj.getMobile(),obj.getPrice(),obj.getCreatetime(),obj.getStatus(),obj.getPrecessstatus(),obj.getPayment()
        ,obj.getOrderdate(),obj.getOrdertime(),obj.getDoctorid(),obj.getChattime(),obj.getDescription(),obj.getMember_photo()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        // TODO Auto /*
        util.open();

        BookerObj obj=(BookerObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_booker set status=?, precessstatus=?,orderdate=?,ordertime=?, doctorid=?, chattime=?, description=?, member_photo=?" +
                " where id=? ");
        Object[] bindArgs = {obj.getStatus(),obj.getPrecessstatus(),obj.getOrderdate(),obj.getOrdertime(),obj.getDoctorid(),obj.getChattime(),obj.getDescription(),obj.getMember_photo(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();

    }





    @Override
    AbstractObj newRealObj() {
        return new BookerObj();
    }

}
