package com.helpfooter.steve.amkdoctor.DAO;


import android.content.Context;
import android.database.Cursor;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MemberObj;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/8/31.
 */
public class MemberDao extends AbstractDao {


    public MemberDao(Context ctx) {
        super(ctx, "tb_member");
    }


    public ArrayList<AbstractObj> getMemberList(){
      return   super.getList(" status='A' ");
    }

    public MemberObj getObj(int memberId){
       /* ArrayList<AbstractObj> lst=  super.getList("  id= "+memberId);*/
        ArrayList<AbstractObj> lst=  super.getList("   ");
        for(AbstractObj abobj:lst){
            return (MemberObj)abobj;
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
        sql.append("create table IF NOT EXISTS  tb_member " +
                "(id int," +
                 "name varchar," +
                "sex varchar," +
                "photo varchar," +
                "age varchar)");
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

        MemberObj obj=(MemberObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_member (id, name,photo,sex, age" +
                " values (?,?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getName(),obj.getPhoto(),obj.getSex(),obj.getAge()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {
        // TODO Auto-generated method stub
        util.open();

        MemberObj obj=(MemberObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_member set name=?,photo=?,sex=?, age=? where id=? ");
        Object[] bindArgs = {obj.getName(),obj.getPhoto(),obj.getSex(),obj.getAge(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }




    @Override
    AbstractObj newRealObj() {
        return new MemberObj();
    }

}
