package com.helpfooter.steve.amklovebaby.DAO;

import com.helpfooter.steve.amklovebaby.Utils.DBUtil;
import android.content.Context;
import android.util.Log;

public abstract class AbstractDao {
	protected DBUtil util;
	public AbstractDao(Context ctx){
		util=new DBUtil(ctx);
		util.open();
	}
	
	 protected void finalize(){
		 util.close();
		 Log.i("a", "run close");
	 }
}
