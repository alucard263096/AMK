package com.helpfooter.steve.amkdoctor.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amkdoctor.Utils.StaticVar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


public class NoticeUpdateLoader extends WebXmlLoader {

	private int NoticeId;

	public NoticeUpdateLoader(Context ctx) {
		super(ctx, "");
	}
    public NoticeUpdateLoader(Context ctx, int noticeid)
	{
		super(ctx,"");
		NoticeId=noticeid;

	}



	@Override
	public void doXml(ArrayList<HashMap<String,String>> lstRows) {
		/*String updatedate="";
		ArrayList<AbstractObj> lsObj=new ArrayList<AbstractObj>();
		for(HashMap<String,String> cols:lstRows){
			ChatObj obj=new ChatObj();
			obj.parseXmlDataTable(cols);
			lsObj.add(obj);
		}
		if(lsObj.size()>0){

			if(callBack!=null){
				callBack.CallBack(lsObj);
			}

	        ParamsDao paramdao=new ParamsDao(this.ctx);
			paramdao.updateParam(this.callApi,StaticVar.GetSystemTimeString());
		}*/
	}

	@Override
	public String getCallUrl() {
		// TODO Auto-generated method stub
		String url=StaticVar.dictHashMap.get("NoticeRead");
		int doc_id=0;
		if(StaticVar.Doctor!=null){
			doc_id=StaticVar.Doctor.getId();
		}


			url= url+"?doctor_id="+String.valueOf(doc_id)
					+"&id="+String.valueOf(NoticeId);


	    Log.i("callurl", url);
		return url;
	}
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
