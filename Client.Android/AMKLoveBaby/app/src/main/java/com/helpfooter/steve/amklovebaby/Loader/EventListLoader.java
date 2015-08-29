package com.helpfooter.steve.amklovebaby.Loader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.widget.Toast;


public class EventListLoader extends WebXmlLoader {
	public EventListLoader(Context ctx, String defaultCallApi) {
		super(ctx, defaultCallApi);
	}

	@Override
	public String getCallUrl() {
		return null;
	}

	@Override
	public void doXml(ArrayList<HashMap<String, String>> lstRow) {

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
