package com.helpfooter.steve.amklovebaby.DAO;


import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.BannerObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class DoctorDao extends AbstractDao {


    public DoctorDao(Context ctx) {
        super(ctx, "tb_doctor");
    }


    public ArrayList<DoctorObj> getDoctorList(){
        ArrayList<DoctorObj> lst=new ArrayList<DoctorObj>();

        Cursor cursor = null;
        try {
            util.open();
            cursor = util
                    .rawQuery(
                            "select * from tb_doctor where status='A' order by general_score desc ",new String[] {  });
            while (cursor.moveToNext()) {
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String license=cursor.getString(cursor.getColumnIndex("license"));
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String office=cursor.getString(cursor.getColumnIndex("office"));
                String bookingtime=cursor.getString(cursor.getColumnIndex("bookingtime"));
                String introduce=cursor.getString(cursor.getColumnIndex("introduce"));
                String credentials=cursor.getString(cursor.getColumnIndex("credentials"));
                String expert=cursor.getString(cursor.getColumnIndex("expert"));
                String enable_videochat=cursor.getString(cursor.getColumnIndex("enable_videochat"));
                String enable_charchat=cursor.getString(cursor.getColumnIndex("enable_charchat"));
                int videochat_price=cursor.getInt(cursor.getColumnIndex("videochat_price"));
                int charchat_price=cursor.getInt(cursor.getColumnIndex("charchat_price"));
                String status=cursor.getString(cursor.getColumnIndex("status"));
                double general_score=cursor.getDouble(cursor.getColumnIndex("general_score"));


                DoctorObj obj=new DoctorObj();
                obj.setId(id);
                obj.setLicense(license);
                obj.setName(name);
                obj.setOffice(office);
                obj.setBookingtime(bookingtime);
                obj.setIntroduce(introduce);
                obj.setCredentials(credentials);
                obj.setExpert(expert);
                obj.setEnableVideochat(enable_videochat);
                obj.setEnable_charchat(enable_charchat);
                obj.setVideochatPrice(videochat_price);
                obj.setCharchat_price(charchat_price);
                obj.setStatus(status);
                obj.setGeneralScore(general_score);

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

    @Override
    public void insertObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
        util.open();

        DoctorObj obj=(DoctorObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_doctor (id, license, name, office, bookingtime, introduce, credentials, expert" +
                ",enable_videochat,videochat_price, enable_charchat, charchat_price, status ,general_score) values (?,?,?,?,?,?,?.?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getLicense(),obj.getName(),obj.getOffice(),obj.getBookingtime(),obj.getIntroduce(),obj.getCredentials(),obj.getExpert()
        ,obj.getEnableVideochat(),obj.getVideochatPrice(),obj.getEnableCharchat(),obj.getCharchatPrice(),obj.getStatus(),obj.getGeneralScore()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
        util.open();

        DoctorObj obj=(DoctorObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_banner set license=?, name=?, office=?, bookingtime=?, introduce=?, credentials=?, expert=?" +
                ",enable_videochat=?,videochat_price=?, enable_charchat=?, charchat_price=?, status=? where id=? ");
        Object[] bindArgs = {obj.getLicense(),obj.getName(),obj.getOffice(),obj.getBookingtime(),obj.getIntroduce(),obj.getCredentials(),obj.getExpert()
                ,obj.getEnableVideochat(),obj.getVideochatPrice(),obj.getEnableCharchat(),obj.getCharchatPrice(),obj.getStatus(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }
}
