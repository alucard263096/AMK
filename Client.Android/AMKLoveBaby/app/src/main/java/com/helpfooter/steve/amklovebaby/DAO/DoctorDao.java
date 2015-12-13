package com.helpfooter.steve.amklovebaby.DAO;


import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;

import java.util.ArrayList;

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

    public DoctorObj GetDoctor(int type)
    {
        Cursor cursor = null;
        AbstractObj obj = null;
        try {
            util.open();
            String sql = "";
            if (type == 0) //获取图文会诊用户
            {
                sql = "select * from tb_doctor a where status='A' and enable_charchat = 'Y' order by general_score desc limit 1";
            }
            else //视频会诊用户
            {
                sql = "select * from tb_doctor a where status='A' and enable_videochat = 'Y' order by general_score desc limit 1";
            }

            cursor = util.rawQuery(sql,new String[] {  });
            while (cursor.moveToNext()) {
                obj=newRealObj();
                obj.parseCursor(cursor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            util.close();
        }
        return (DoctorObj)obj;
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
                "videoquerycount int," +
                "charquerycount int," +
                "chat_time int," +
                "service_score int," +
                "ability_score int," +
                "general_score int)");
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
                ",enable_videochat,videochat_price, enable_charchat, charchat_price, status " +
                ",general_score,service_score,ability_score,videoquerycount,charquerycount,chat_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getLicense(),obj.getName(),obj.getPhoto(),obj.getTitle(),obj.getOffice(),obj.getBookingtime(),obj.getIntroduce(),obj.getCredentials(),obj.getExpert()
        ,obj.getEnableVideochat(),obj.getVideochatPrice(),obj.getEnableCharchat(),obj.getCharchatPrice(),obj.getStatus(),obj.getGeneralScore(),obj.getService_score(),obj.getAbility_score(),obj.getVideoquerycount(),obj.getChat_time()};
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
                ",enable_videochat=?,videochat_price=?, enable_charchat=?, charchat_price=?, status=?,general_score=?,service_score=?,ability_score=?,videoquerycount=?,charquerycount=?,chat_time=? where id=? ");
        Object[] bindArgs = {obj.getLicense(),obj.getName(),obj.getPhoto(),obj.getTitle(),obj.getOffice(),obj.getBookingtime(),obj.getIntroduce(),obj.getCredentials(),obj.getExpert()
                ,obj.getEnableVideochat(),obj.getVideochatPrice(),obj.getEnableCharchat(),obj.getCharchatPrice(),obj.getStatus(),obj.getGeneralScore(),obj.getService_score(),obj.getAbility_score(),obj.getVideoquerycount(),obj.getCharquerycount(),obj.getChat_time(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    public void updateStatistic(DoctorObj obj){
        util.open();


        StringBuffer sql = new StringBuffer();
        sql.append("update tb_doctor set general_score=?,service_score=?,ability_score=?,videoquerycount=?,charquerycount=?,chat_time=? where id=? ");
        Object[] bindArgs = {obj.getGeneralScore(),obj.getService_score(),obj.getAbility_score(),obj.getVideoquerycount(),obj.getCharquerycount(),obj.getChat_time(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();

    }

    @Override
    AbstractObj newRealObj() {
        return new DoctorObj();
    }

    public ArrayList<AbstractObj> getDoctorListWithFollow() {
        ArrayList<AbstractObj> lst=new ArrayList<AbstractObj>();

        Cursor cursor = null;
        try {
            util.open();
            String sql="select * from tb_doctor a " +
                    "inner join tb_member_follow_doctor b on a.id=b.doctor_id " +
                    "where status='A' order by general_score desc ";

            cursor = util.rawQuery(sql,new String[] {  });
            while (cursor.moveToNext()) {
                AbstractObj obj=newRealObj();
                obj.parseCursor(cursor);
                lst.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            util.close();
        }
        return lst;
    }
}
