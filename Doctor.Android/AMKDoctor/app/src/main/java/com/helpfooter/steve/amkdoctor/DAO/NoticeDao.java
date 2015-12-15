package com.helpfooter.steve.amkdoctor.DAO;


import android.content.Context;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MemberObj;
import com.helpfooter.steve.amkdoctor.DataObjs.NoticeObj;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/31.
 */
public class NoticeDao extends AbstractDao {


    public NoticeDao(Context ctx) {
        super(ctx, "tb_notice");
    }


    public ArrayList<AbstractObj> getNoticeList(){
      return   super.getList(" status='A' ");
    }

    public NoticeObj getObj(int noticeId){
       /* ArrayList<AbstractObj> lst=  super.getList("  id= "+memberId);*/
        ArrayList<AbstractObj> lst=  super.getList("   ");
        for(AbstractObj abobj:lst){
            return (NoticeObj)abobj;
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
        sql.append("create table IF NOT EXISTS  tb_notice " +
                "(id int," +
                "doctor_id int," +
                 "haveread varchar," +
                "sent_type varchar," +
                "status varchar," +
                "notice_type varchar," +
                "publish_date varchar," +
                "title varchar," +
                "context varchar)");
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

        NoticeObj obj=(NoticeObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_notice (id, doctor_id,haveread,sent_type, status,notice_type,publish_date,title,context" +
                " values (?,?,?,?,?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getDoctor_id(),obj.getHaveread(),obj.getSent_type(),obj.getStatus(),obj.getNotice_type(),obj.getPublish_date(),obj.getTitle(),obj.getContext()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
        util.open();

        NoticeObj obj=(NoticeObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_notice set haveread=?,sent_type=?,status=?, notice_type=?,title=?,context=? where id=? ");
        Object[] bindArgs = {obj.getHaveread(),obj.getSent_type(),obj.getStatus(),obj.getNotice_type(),obj.getTitle(),obj.getContext(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }




    @Override
    AbstractObj newRealObj() {
        return new NoticeObj();
    }

}
