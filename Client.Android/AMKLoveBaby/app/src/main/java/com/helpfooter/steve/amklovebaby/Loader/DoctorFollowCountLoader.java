package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;

import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;


public class DoctorFollowCountLoader extends WebXmlLoader {
	int doctor_id;
	public DoctorFollowCountLoader(Context ctx, int doctor_id) {
		super(ctx, StaticVar.DoctorFollowCountApi);
		this.doctor_id=doctor_id;
	}

	@Override
	public String getCallUrl() {
		// TODO Auto-generated method stub
		String url=StaticVar.dictHashMap.get(StaticVar.DoctorFollowCountApi);
		url+="?doctor_id="+String.valueOf(doctor_id);
		return url;
	}


	@Override
	public void doXml(ArrayList<HashMap<String,String>> lstRows) {
		DoctorDao dao=new DoctorDao(ctx);
		ArrayList<DoctorObj> lsObj=new ArrayList<DoctorObj>();
		for(HashMap<String,String> cols:lstRows){
			int followCount=DoctorObj.parseXmlDataTableForFollowCount(cols);
			dao.updateFollowCount(doctor_id, followCount);
		}
	}
	
}
