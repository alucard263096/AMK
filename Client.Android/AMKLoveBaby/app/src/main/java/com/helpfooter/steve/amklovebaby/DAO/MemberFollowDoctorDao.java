package com.helpfooter.steve.amklovebaby.DAO;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberFollowDoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/9/9.
 */
public class MemberFollowDoctorDao extends AbstractDao {
    public MemberFollowDoctorDao(Context ctx) {
        super(ctx, "tb_member_follow_doctor");
    }

    @Override
    void gotoCreateTableSql() {
        util.open();
        StringBuffer sql = new StringBuffer();
        sql.append("create table IF NOT EXISTS  tb_member_follow_doctor " +
                "(member_id int,doctor_id int )");
        util.execSQL(sql.toString(), new Object[]{});
    }

    static boolean hascheckcreate = false;

    public void createTable(){
        if(hascheckcreate==false){
            gotoCreateTableSql();
            hascheckcreate=true;
        }
    }

    @Override
    public void insertObj(AbstractObj abobj) {
        util.open();

        MemberFollowDoctorObj obj=(MemberFollowDoctorObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_member_follow_doctor (member_id,doctor_id ) values (?,?)");
        Object[] bindArgs = {obj.getMember_id(),obj.getDoctor_id()};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        //util.open();

//        MemberFollowDoctorObj obj=(MemberFollowDoctorObj)abobj;
//
//        StringBuffer sql = new StringBuffer();
//        sql.append("update tb_member set mobile=?,name=?,photo=?,sex=?,birth=?,history=? where id=? ");
//        Object[] bindArgs = {obj.getMobile(),obj.getName(),obj.getPhoto(),obj.getSex(), obj.getBirth(),obj.getHistory(),obj.getId()};
//        util.execSQL(sql.toString(),bindArgs);

        //util.close();
    }

    @Override
    AbstractObj newRealObj() {
        return new MemberFollowDoctorObj();
    }


    public void batchUpdate(ArrayList<AbstractObj> lstObj) {
        // TODO Auto-generated method stub

        Log.i("batchupdate_row", TableName + ":" + String.valueOf(lstObj.size()));
        try {

            util.beginTransaction();
            deleteTable();
            for(AbstractObj obj:lstObj){
                insertObj(obj);
            }
            Log.i("batchupdate_succ",TableName+":"+String.valueOf(lstObj.size()));
            util.setTransactionSuccessful();
        }catch (Exception ex) {
            ex.printStackTrace();
            Log.i("batchupdate_err", TableName + ":" + ex.getMessage());
        }finally
        {
            util.endTransaction();
        }
    }

    public boolean hasFollow(int doctor_id) {

        boolean haveData=false;
        Cursor cursor = util.rawQuery("select 1  from "+TableName+" where doctor_id="+String.valueOf(doctor_id),null);
        if(cursor.moveToFirst())
            haveData=true;

        cursor.close();

        return haveData;

    }

    public void removeFollow(int doctor_id) {
        util.open();

        StringBuffer sql = new StringBuffer();
        sql.append("delete from tb_member_follow_doctor where doctor_id=? ");
        Object[] bindArgs = {doctor_id};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }
}
