package com.helpfooter.steve.amklovebaby.DAO;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;

/**
 * Created by Steve on 2015/9/9.
 */
public class MemberDao extends AbstractDao {
    public MemberDao(Context ctx) {
        super(ctx, "tb_member");
    }

    @Override
    void gotoCreateTableSql() {
        util.open();
        StringBuffer sql = new StringBuffer();
        sql.append("create table IF NOT EXISTS  tb_member " +
                "(id int,name varchar ,mobile varchar )");
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
    void insertObj(AbstractObj abobj) {
        util.open();

        MemberObj obj=(MemberObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("insert into tb_member (id,mobile  ,name ) values (?,?,?)");
        Object[] bindArgs = {obj.getId(),obj.getMobile(),obj.getName()};
        util.execSQL(sql.toString(), bindArgs);

        util.close();
    }

    @Override
    void updateObj(AbstractObj abobj) {
        util.open();

        MemberObj obj=(MemberObj)abobj;

        StringBuffer sql = new StringBuffer();
        sql.append("update tb_member set mobile=?  ,name=? where id=? ");
        Object[] bindArgs = {obj.getMobile(),obj.getName(),obj.getId()};
        util.execSQL(sql.toString(),bindArgs);

        util.close();
    }

    @Override
    AbstractObj newRealObj() {
        return new MemberObj();
    }

    public void refreshMember(MemberObj memberObj) {
        util.open();
        StringBuffer sql = new StringBuffer();
        sql.append("delete from " + TableName);
        util.execSQL(sql.toString(), new Object[]{});

        sql = new StringBuffer();
        sql.append("delete from tb_order");
        util.execSQL(sql.toString(), new Object[]{});

        insertObj(memberObj);

    }
}
