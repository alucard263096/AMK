package com.helpfooter.steve.amkdoctor.DAO;

import android.content.Context;


import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MemberObj;
import com.helpfooter.steve.amkdoctor.DataObjs.MemberPhotoObj;

/**
 * Created by Steve on 2015/9/9.
 */
public class MemberPhotoDao extends AbstractDao {
    public MemberPhotoDao(Context ctx) {
        super(ctx, "tb_member_photo");
    }

    @Override
    void gotoCreateTableSql() {
        util.open();
        StringBuffer sql = new StringBuffer();

        sql.append("create table IF NOT EXISTS  tb_member_photo " +
                "(id int,title varchar ,description varchar,photo varchar )");
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

        MemberPhotoObj obj=(MemberPhotoObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_member_photo (id,title  ,description,photo ) values (?,?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getTitle(),obj.getDescription(),obj.getPhoto()};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }

    @Override
    public void updateObj(AbstractObj abobj) {

    }

    @Override
    AbstractObj newRealObj() {
        return new MemberPhotoObj();
    }
}
