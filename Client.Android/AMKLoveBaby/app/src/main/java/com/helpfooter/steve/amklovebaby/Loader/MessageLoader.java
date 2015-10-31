package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.MessageDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MessageObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.XmlDataTableReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class MessageLoader extends WebXmlLoader {

	private int DoctorId;
	public MessageLoader(Context ctx) {
		super(ctx, "");
	}

	@Override
	public void doXml(ArrayList<HashMap<String,String>> lstRows) {
		String updatedate = "";
		ArrayList<AbstractObj> lsObj = new ArrayList<AbstractObj>();
		for (HashMap<String, String> cols : lstRows) {
			MessageObj obj = new MessageObj();
			obj.parseXmlDataTable(cols);
			lsObj.add(obj);
		}
		MessageDao dao = new MessageDao(ctx);
		dao.deleteNoInList(lsObj);
		dao.batchUpdate(lsObj);
		if (callBack != null) {
			callBack.CallBack(lsObj);
		}
	}

	@Override
	public String getCallUrl() {
		// TODO Auto-generated method stub
		String url=StaticVar.dictHashMap.get("Message");
		int member_id=0;
		if(StaticVar.Member!=null){
			member_id=StaticVar.Member.getId();
		}
		url+="?member_id="+String.valueOf(member_id)+
			"&status=P";
	    Log.i("callurl", url);
		return url;
	}
	
}
