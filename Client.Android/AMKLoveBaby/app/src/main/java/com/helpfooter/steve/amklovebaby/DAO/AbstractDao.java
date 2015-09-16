package com.helpfooter.steve.amklovebaby.DAO;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.Utils.DBUtil;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public abstract class AbstractDao {
	protected DBUtil util;
	protected String TableName;
	Context ctx;
	public AbstractDao(Context ctx,String tablename){
		this.ctx=ctx;
		util=new DBUtil(ctx);
		util.open();
		this.TableName=tablename;
		try {
			createTable();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	abstract void createTable();
	abstract void gotoCreateTableSql();


	public boolean checkExists(int id){
		boolean haveData=false;
		Cursor cursor = util.rawQuery("select 1  from "+TableName+" where id="+String.valueOf(id),null);
		if(cursor.moveToFirst())
			haveData=true;

		cursor.close();

		return haveData;
	}
	
	 protected void finalize(){
		 util.close();
		 Log.i("a", "run close");
	 }


	public void batchUpdate(ArrayList<AbstractObj> lstObj) {
		// TODO Auto-generated method stub

		Log.i("batchupdate_row",TableName+":"+String.valueOf(lstObj.size()));
		try {

			util.beginTransaction();
			for(AbstractObj obj:lstObj){

				if(checkExists(obj.getId())){
					Log.i("act eventa", "update");
					updateObj(obj);
				}else{
					Log.i("act event", "insert");
					insertObj(obj);
				}
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

	public void deleteTable(){
		util.execSQL("delete from "+TableName+"",new Object[]{});
	}

	public ArrayList<AbstractObj> getList(String condition){
		ArrayList<AbstractObj> lst=new ArrayList<AbstractObj>();

		Cursor cursor = null;
		try {
			util.open();
			String sql="select * from "+TableName+" ";
			if(condition.length()>1){
				sql+=" where "+condition;
			}
			cursor = util.rawQuery(sql,new String[] {  });
			while (cursor.moveToNext()) {
				AbstractObj obj=newRealObj();
				obj.parseCursor(cursor);
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

	public AbstractObj getObj(int id){
		Cursor cursor = null;
		try {
			util.open();
			cursor = util
					.rawQuery(
							"select * from "+TableName+" where id=? ",new String[] { String.valueOf(id) });
			while (cursor.moveToNext()) {

				AbstractObj obj=this.newRealObj();
				obj.parseCursor(cursor);
				return obj;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	abstract void insertObj(AbstractObj obj);
	abstract void updateObj(AbstractObj obj);
	abstract AbstractObj newRealObj();



}
