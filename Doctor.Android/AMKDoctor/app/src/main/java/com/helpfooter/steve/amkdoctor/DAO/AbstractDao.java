package com.helpfooter.steve.amkdoctor.DAO;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.Utils.DBUtil;

import java.util.ArrayList;

public abstract class AbstractDao {
	protected DBUtil util;
	protected String TableName;
	public AbstractDao(Context ctx,String tablename){
		util=new DBUtil(ctx);
		util.open();
		this.TableName=tablename;
	}


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
		util.open();

		util.beginTransaction();
		try {

			for(AbstractObj obj:lstObj){

				if(checkExists(obj.getId())){
					Log.i("act eventa", "update");
					updateObj(obj);
				}else{
					Log.i("act event", "insert");
					insertObj(obj);
				}

			}

			util.setTransactionSuccessful();
		} finally{
			util.endTransaction();
		}
		util.close();
	}

	abstract void insertObj(AbstractObj obj);

	abstract void updateObj(AbstractObj obj);
}
