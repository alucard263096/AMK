package com.helpfooter.steve.amkdoctor.DAO;


import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.BannerObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class DoctorDao extends AbstractDao {


    public DoctorDao(Context ctx) {
        super(ctx, "tb_doctor");
    }


    public ArrayList<AbstractObj> getDoctorList(){
      return   super.getList(" status='A' order by general_score desc");
    }

    public DoctorObj getObj(int docId){
        ArrayList<AbstractObj> lst=  super.getList("  userid= "+docId);

        for(AbstractObj abobj:lst){
            return (DoctorObj)abobj;
        }
        return null;

    }

    @Override
    void gotoCreateTableSql() {
        util.open();
       StringBuffer sql = new StringBuffer();
        //sql.append("drop table tb_doctor");
        //util.execSQL(sql.toString(), new Object[]{});
        //sql=new StringBuffer();
//        sql.append("delete table tb_param where id='doctor'");
//        util.execSQL(sql.toString(), new Object[]{});
//        sql=new StringBuffer();
        sql.append("create table IF NOT EXISTS  tb_doctor " +
                "(id int," +
                "userid int," +
                "license varchar," +
                "name varchar," +
                "title varchar," +
                "photo varchar," +
                "office varchar," +
                "bookingtime varchar," +
                "introduce varchar," +
                "credentials varchar," +
                "expert varchar," +
                "enable_videochat varchar," +
                "videochat_price int," +
                "enable_charchat varchar," +
                "charchat_price int," +
                "status varchar," +
                "querycount int," +
                "general_score double)");
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

        DoctorObj obj=(DoctorObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_doctor (id, license, name,photo,title, office, bookingtime, introduce, credentials, expert" +
                ",enable_videochat,videochat_price, enable_charchat, charchat_price, status ,general_score,querycount) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getLicense(),obj.getName(),obj.getPhoto(),obj.getTitle(),obj.getOffice(),obj.getBookingtime(),obj.getIntroduce(),obj.getCredentials(),obj.getExpert()
        ,obj.getEnableVideochat(),obj.getVideochatPrice(),obj.getEnableCharchat(),obj.getCharchatPrice(),obj.getStatus(),obj.getGeneralScore(),obj.getQuerycount()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
        util.open();

        DoctorObj obj=(DoctorObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_doctor set license=?, name=?,photo=?,title=?, office=?, bookingtime=?, introduce=?, credentials=?, expert=?" +
                ",enable_videochat=?,videochat_price=?, enable_charchat=?, charchat_price=?, status=?,general_score=?,querycount=? where id=? ");
        Object[] bindArgs = {obj.getLicense(),obj.getName(),obj.getPhoto(),obj.getTitle(),obj.getOffice(),obj.getBookingtime(),obj.getIntroduce(),obj.getCredentials(),obj.getExpert()
                ,obj.getEnableVideochat(),obj.getVideochatPrice(),obj.getEnableCharchat(),obj.getCharchatPrice(),obj.getStatus(),obj.getGeneralScore(),obj.getQuerycount(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    public boolean CheckLogin(String userName,String passWord)
    {
        boolean bResult =false;
        StringBuffer sql=new StringBuffer();
        sql.append("select 1 from tb_doctor where username=? and password=?");
        String[] bindArgs={userName,passWord};

        Cursor cursor = util.rawQuery(sql.toString(),bindArgs);
        while (cursor.moveToNext()) {
            bResult=true;

        }
        cursor.close();
        return  bResult;

    }



    @Override
    AbstractObj newRealObj() {
        return new DoctorObj();
    }

}
