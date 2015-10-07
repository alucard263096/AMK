package com.helpfooter.steve.amkdoctor.DAO;


import android.content.Context;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.ChatObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MessageObj;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/31.
 */
public class ChatDao extends AbstractDao {


    public ChatDao(Context ctx) {
        super(ctx, "tb_chat");
    }


    public ArrayList<AbstractObj> getChatList(){
      return   super.getList("");
    }

    public ChatObj getObj(int doctorId,String memberId){
        ArrayList<AbstractObj> lst=  super.getList("  doctor_id= "+doctorId +" and member_id="+memberId);
           for(AbstractObj abobj:lst){
            return (ChatObj)abobj;
        }
        return null;

    }

    @Override
    void gotoCreateTableSql() {
        util.open();
       StringBuffer sql = new StringBuffer();
        sql.append("create table IF NOT EXISTS  tb_chat " +
                "(id int," +
                "member_id varchar," +
                 "doctor_id int," +
                "last_one varchar," +
                "content varchar)");
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

        ChatObj obj=(ChatObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_chat (id,member_id,doctor_id,last_one,content" +
                ") values (?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getMember_id(),obj.getDoctor_id(),obj.getLast_one(),obj.getContent()};
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
        return new ChatObj();
    }

}
