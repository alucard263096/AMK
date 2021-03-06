package com.helpfooter.steve.amklovebaby.DAO;


import android.content.Context;
import android.text.format.DateFormat;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MessageObj;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Steve on 2015/8/31.
 */
public class MessageDao extends AbstractDao {


    public MessageDao(Context ctx) {
        super(ctx, "tb_message");
    }



    public ArrayList<AbstractObj> getNoticeOrder(){
        return   super.getList("sendside='D' and last_one<>sendmessage ");

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
                 "sendside varchar," +
                "sendmessage varchar," +
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

    public void updateSendMessage(MessageObj abobj) {

        util.open();

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_message set sendmessage=? where id=?");
        Object[] bindArgs = {abobj.getLast_one(),abobj.getId()};
        try {
            util.execSQL(sql.toString(), bindArgs);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //throw ex;
        }

        util.close();
    }


    @Override
    public void insertObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
        util.open();

        MessageObj obj=(MessageObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_message (id,member_id,name,created_time,doctor_id,last_one,description,sendside,sendmessage" +
                ") values (?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getMember_id(),obj.getName(),obj.getCreated_time(),obj.getDoctor_id(),obj.getLast_one(),obj.getDescription(),obj.getSendside(),obj.getSendside()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        // TODO Auto-generated method stub

        util.open();

        MessageObj obj=(MessageObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_message set last_one=?  ,sendside=? where id=? ");
        Object[] bindArgs = {obj.getLast_one(),obj.getSendside(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();

    }





    @Override
    AbstractObj newRealObj() {
        return new MessageObj();
    }

}
