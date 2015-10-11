package com.helpfooter.steve.amklovebaby.DAO;


import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MessageObj;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/31.
 */
public class MessageDao extends AbstractDao {


    public MessageDao(Context ctx) {
        super(ctx, "tb_message");
    }


    public ArrayList<AbstractObj> getMessageList(int doctorId){
      return   super.getList("doctor_id= "+doctorId+"  order by created_time desc");
    }

    public MessageObj getObj(int doctorId){
        ArrayList<AbstractObj> lst=  super.getList("  doctor_id= "+doctorId);
           for(AbstractObj abobj:lst){
            return (MessageObj)abobj;
        }
        return null;

    }

    @Override
    void gotoCreateTableSql() {
        util.open();
       StringBuffer sql = new StringBuffer();
        sql.append("create table IF NOT EXISTS  tb_message " +
                "(id int," +
                "doctor_id int," +
                "member_id varchar," +
                "name varchar," +
                "created_time varchar," +
                "last_one varchar," +
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

        MessageObj obj=(MessageObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_message (id,member_id,name,created_time,doctor_id,last_one,description" +
                ") values (?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getMember_id(),obj.getName(),obj.getCreated_time(),obj.getDoctor_id(),obj.getLast_one(),obj.getDescription()};
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
        return new MessageObj();
    }

}
