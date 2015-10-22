package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;


public class DoctorStatisticsLoader extends WebXmlLoader {
	int doctor_id;
	public DoctorStatisticsLoader(Context ctx,int doctor_id) {
		super(ctx, StaticVar.DoctorStatisticsApi);
		this.doctor_id=doctor_id;
	}

	@Override
	public String getCallUrl() {
		// TODO Auto-generated method stub
		String url=StaticVar.dictHashMap.get(StaticVar.DoctorStatisticsApi);
		url+="?doctor_id="+String.valueOf(doctor_id);
		return url;
	}


	@Override
	public void doXml(ArrayList<HashMap<String,String>> lstRows) {
		String updatedate="";
		DoctorDao dao=new DoctorDao(ctx);
		ArrayList<DoctorObj> lsObj=new ArrayList<DoctorObj>();
		for(HashMap<String,String> cols:lstRows){
			DoctorObj obj=new DoctorObj();
			obj.parseXmlDataTableForStatistics(cols);
			dao.updateStatistic(obj);
		}

	}
	
}
