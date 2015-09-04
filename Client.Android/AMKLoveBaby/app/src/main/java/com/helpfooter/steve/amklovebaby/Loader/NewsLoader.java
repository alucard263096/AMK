package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.NewsObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;
import java.util.HashMap;


public class NewsLoader extends WebXmlLoader {

	public NewsLoader(Context ctx) {
		super(ctx, StaticVar.NewsApi);
	}

	IWebLoaderCallBack callBack;
	public void setCallBack(IWebLoaderCallBack val){
		callBack=val;
	}

	int newsId;
	public void  setGetNewsContext(int newsId){
		this.callApi=StaticVar.NewsContentApi;
		this.newsId=newsId;
	}
	public String getCallUrl() {
		// TODO Auto-generated method stub
		if(callApi==StaticVar.NewsContentApi){
			String url=StaticVar.dictHashMap.get(callApi);
			url= (url+String.valueOf(newsId)).replace(" ", "%20");
			return url;
		}else{
			return super.getCallUrl();
		}
	}

	@Override
	public void doXml(ArrayList<HashMap<String,String>> lstRows) {
		ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
		for(HashMap<String,String> cols:lstRows){
			NewsObj obj=new NewsObj();
			obj.parseXmlDataTable(cols);
			lsObj.add(obj);
		}
		if(callApi==StaticVar.NewsContentApi){
			if(lsObj.size()>0){
				if(callBack!=null){
					callBack.CallBack(lsObj);
				}
			}
		}else {
			String updatedate="";
			if(lsObj.size()>0){
				DoctorDao dao=new DoctorDao(ctx);
				dao.batchUpdate(lsObj);
				if(callBack!=null){
					callBack.CallBack(lsObj);
				}

				ParamsDao paramdao=new ParamsDao(this.ctx);
				paramdao.updateParam(this.callApi,StaticVar.GetSystemTimeString());
			}
		}
	}

	
	
	
}
