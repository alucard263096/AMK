package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.AbstractDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.BannerObj;
import com.helpfooter.steve.amklovebaby.DAO.BannerDao;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.XmlDataTableReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class BannerLoader extends WebXmlLoader {

	public BannerLoader(Context ctx) {
		super(ctx, "");
	}



	@Override
	public void doXml(ArrayList<HashMap<String,String>> lstRows) {
		String updatedate="";
		ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
		for(HashMap<String,String> cols:lstRows){
			BannerObj obj=new BannerObj();
			obj.parseXmlDataTable(cols);
			lsObj.add(obj);
		}
		if(lsObj.size()>0){
			BannerDao dao=new BannerDao(ctx);
			dao.batchUpdate(lsObj);
			if(callBack!=null){
				callBack.CallBack(lsObj);
			}

	        ParamsDao paramdao=new ParamsDao(this.ctx);
			paramdao.updateParam(this.callApi,StaticVar.GetSystemTimeString());
		}
	}

	
	
	
	
}
