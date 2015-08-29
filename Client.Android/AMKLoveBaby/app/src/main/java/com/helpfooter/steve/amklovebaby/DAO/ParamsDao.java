package com.helpfooter.steve.amklovebaby.DAO;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;

public class ParamsDao extends AbstractDao {

	public ParamsDao(Context ctx) {
		super(ctx,"tb_param");
		// TODO Auto-generated constructor stub
	}
	
	public String getParam(String id,String defaultvalue) {
		// TODO Auto-generated method stub

		Cursor cursor = null;
		try {
			util.open();
			cursor = util
					.rawQuery(
							"select val  from tb_param where id='"+id+"'",new String[] { });
			while (cursor.moveToNext()) {
				return cursor.getString(cursor.getColumnIndex("val"));
			}
			insertPram(id,defaultvalue);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			util.close();
		}
		return defaultvalue;
	}

	public void insertPram(String id,String val){
		StringBuffer sql_insert = new StringBuffer();
		sql_insert.append("insert into  tb_param (id,val) values (?,?) ");
		Object[] bindArgs = {id,val};
		util.execSQL(sql_insert.toString(),bindArgs);
	}

	public void updateParam(String id, String updatevalue) {
		// TODO Auto-generated method stub
		
        StringBuffer sql_insert = new StringBuffer();
        sql_insert.append("UPDATE  tb_param set val=? where id=?");
        Object[] bindArgs = {updatevalue,id};
        util.execSQL(sql_insert.toString(),bindArgs);
	}

	@Override
	void insertObj(AbstractObj obj) {

	}

	@Override
	void updateObj(AbstractObj obj) {

	}
}
