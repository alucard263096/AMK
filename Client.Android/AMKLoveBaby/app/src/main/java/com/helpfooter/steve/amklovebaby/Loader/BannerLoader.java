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

	IWebLoaderCallBack callBack;
	public void setCallBack(IWebLoaderCallBack val){
		callBack=val;
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
				callBack.CallBack();
			}

	        ParamsDao paramdao=new ParamsDao(this.ctx);
			paramdao.updateParam(StaticVar.IndexBannerApi,StaticVar.GetSystemTimeString());
		}
	}

//	EventListFragment eventListFragment;
//
//	public EventListLoader(EventListFragment _eventListFragment){
//		this.eventListFragment=_eventListFragment;
//	}
//
//	Handler mThirdHandler = new Handler(){
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what){
//            case 0:
//            	Log.i("doit", "1");
//            	eventListFragment.initCommonList();
//            	Log.i("doit", "2");
//            	eventListFragment.init();
//            	Log.i("doit", "3");
//            	break;
//            }
//        };
//    };
//
//
//	@Override
//	public String getCallUrl() {
//		// TODO Auto-generated method stub
//		ParamsDao dao=new ParamsDao(eventListFragment.getActivity());
//		String update_date=dao.getParam("last_event_update_time", "1991-1-1");
//		String url= StaticVar.CMSURL+"event_interface.aspx?type=geteventlist&request_date="+update_date.replace(" ", "%20");
//		Log.i("callurl", url);
//		return url;
//	}
//
//	@Override
//	public void doXml(InputStream is) {
//		// TODO Auto-generated method stub
//		try
//        {
//			String updatedate="";
//	        List<EventObj> lsEvent=new ArrayList<EventObj>();
//
//
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	        DocumentBuilder db = factory.newDocumentBuilder();
//	        Document doc = db.parse(is);
//	        Element elmtInfo = doc.getDocumentElement();
//	        NodeList StartNode = elmtInfo.getChildNodes();
//	        //XmlReader.ShowLog(StartNode);
//	        for (int i = 0; i < StartNode.getLength(); i++)
//	        {
//				 Node result = StartNode.item(i);
//
//				 if(result.getNodeName().equals("request_date")){
//					 updatedate=result.getTextContent();
//				 }else if(result.getNodeName().equals("events")){
//					 NodeList nsEvent = result.getChildNodes();
//					 for (int j = 0; j < nsEvent.getLength(); j++)
//				     {
//						 Node nodeEvent = nsEvent.item(j);
//						 NodeList potEvent = nodeEvent.getChildNodes();
//
//						 int eventId=0;
//						 String title="";
//						 String summary="";
//						 String imageurl="";
//						 String content="";
//						 String publisheddate="";
//						 int status=0;
//						 for (int k = 0; k < potEvent.getLength(); k++){
//							 Node eventpot = potEvent.item(k);
//							 Log.i(eventpot.getNodeName(),eventpot.getTextContent());
//
//							 if(eventpot.getNodeName().equals("eventid")){
//								 eventId=Integer.valueOf(eventpot.getTextContent());
//							 }else if(eventpot.getNodeName().equals("title")){
//								 title=eventpot.getTextContent();
//							 }else if(eventpot.getNodeName().equals("summary")){
//								 summary=eventpot.getTextContent();
//							 }else if(eventpot.getNodeName().equals("imageurl")){
//								 imageurl=eventpot.getTextContent();
//							 }else if(eventpot.getNodeName().equals("content")){
//								 content=eventpot.getTextContent();
//							 }else if(eventpot.getNodeName().equals("publisheddate")){
//								 publisheddate=eventpot.getTextContent();
//							 }else if(eventpot.getNodeName().equals("status")){
//								 status=Integer.valueOf(eventpot.getTextContent());
//							 }
//						 }
//
//						 EventObj event=new EventObj(eventId,title,summary,publisheddate);
//						 event.setContent(content);
//						 event.setImageUrl(StaticVar.CMSURL+imageurl);
//						 event.setStatus(status);
//
//						 lsEvent.add(event);
//
//				     }
//				 }
////				 if (result.getNodeType() == Node.ELEMENT_NODE){
////					 NodeList ns = result.getChildNodes();
////					 XmlReader xml=new XmlReader(ns);
////					 XmlReader.ShowLog(ns);
////				 }
//	        }
//	        if(lsEvent.size()>0){
//
//		        EventDao dao=new EventDao(this.eventListFragment.getActivity());
//		        dao.batchUpdateEvent(lsEvent);
//
//		        mThirdHandler.sendEmptyMessage(0);
//	        }
//
//	        Log.i("updatedate", updatedate);
//	        if(updatedate.equals("")==false){
//	        	ParamsDao paramdao=new ParamsDao(eventListFragment.getActivity());
//		        paramdao.updatefield("last_event_update_time",updatedate);
//	        }
//
//
//        }
//        catch (ParserConfigurationException e)
//        {
//            e.printStackTrace();
//        }
//        catch (SAXException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//	}
	
	
	
	
}
