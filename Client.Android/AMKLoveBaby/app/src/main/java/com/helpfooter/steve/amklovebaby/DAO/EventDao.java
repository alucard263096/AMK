package com.helpfooter.steve.amklovebaby.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.EventObj;

public class EventDao extends AbstractDao {
	
	public EventDao(Context ctx) {
		super(ctx,"");
		// TODO Auto-generated constructor stub
	}

	public List<EventObj> getEventList(){
		List<EventObj> eventList=new ArrayList<EventObj>();
		
		Cursor cursor = null;
		try {
			util.open();
			Log.i("getlist", "aaa");
			cursor = util
					.rawQuery(
							"select  eventId,title,summary,publishedDate,imageUrl from tb_event where status=0 and datetime(publishedDate)<=datetime('now','localtime') order by publishedDate desc ",new String[] {  });
			while (cursor.moveToNext()) {
				int eventId=cursor.getInt(cursor.getColumnIndex("eventId"));
				String title=cursor.getString(cursor.getColumnIndex("title"));
				String summary=cursor.getString(cursor.getColumnIndex("summary"));
				String publishedDate=cursor.getString(cursor.getColumnIndex("publishedDate"));
				String imageUrl=cursor.getString(cursor.getColumnIndex("imageUrl"));
				
				EventObj event=new EventObj(eventId,title,summary,publishedDate);
				event.setImageUrl(imageUrl);
				eventList.add(event);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			util.close();
		}
		return eventList;
	}

	public EventObj getEvent(int eventId) {
		// TODO Auto-generated method stub
		EventObj event=null;
		Cursor cursor = null;
		try {
			cursor = util
					.rawQuery(
							"select  eventId,title,summary, publishedDate,imageUrl,content from tb_event where eventId=?  ",new String[] {String.valueOf(eventId)  });
			while (cursor.moveToNext()) {
				String title=cursor.getString(cursor.getColumnIndex("title"));
				String summary=cursor.getString(cursor.getColumnIndex("summary"));
				String publishedDate=cursor.getString(cursor.getColumnIndex("publishedDate"));
				String imageUrl=cursor.getString(cursor.getColumnIndex("imageUrl"));
				String content=cursor.getString(cursor.getColumnIndex("content"));
				
				event=new EventObj(eventId,title,summary,publishedDate);
				event.setImageUrl(imageUrl);
				event.setContent(content);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return event;
	}

	public void batchUpdateEvent(List<EventObj> lsEvent) {
		// TODO Auto-generated method stub
		util.open();
		
		util.beginTransaction();
        try { 
        	
        	for(EventObj event:lsEvent){
        		
        		if(checkExistsEvent(event.getEventId())){
        			Log.i("act eventa", "update");
        			updateEvent(event);
        		}else{
        			Log.i("act event", "insert");
        			insertEvent(event);
        		}
        		
        	}
        	
        	util.setTransactionSuccessful();
        } finally{
        	util.endTransaction();
		}
		
		
		util.close();
	}
	private void insertEvent(EventObj event) {
		// TODO Auto-generated method stub
		util.open();
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into tb_event (eventId  ,title  ,summary ,content ,publishedDate ,imageUrl ,status ) values (?,?,?,?,?,?,?)");
        Object[] bindArgs = {event.getEventId(),event.getTitle(),event.getSummary(),event.getContent(),event.getPublishedDate(),event.getImageUrl(),event.getStatus()};
        util.execSQL(sql.toString(),bindArgs);
        
		util.close();
	}

	private void updateEvent(EventObj event) {
		// TODO Auto-generated method stub
		util.open();

		StringBuffer sql = new StringBuffer();
		sql.append("update tb_event set title=?  ,summary=? ,content=? ,publishedDate=? ,imageUrl=? ,status=? where eventId=? ");
        Object[] bindArgs = {event.getTitle(),event.getSummary(),event.getContent(),event.getPublishedDate(),event.getImageUrl(),event.getStatus(),event.getEventId()};
        util.execSQL(sql.toString(),bindArgs);
        
		util.close();
	}

	public boolean checkExistsEvent(int eventId){
		boolean haveData=false;
    	Cursor cursor = util.rawQuery("select 1  from tb_event where eventId="+String.valueOf(eventId),null);  
         if(cursor.moveToFirst())
        	 haveData=true;
         
         cursor.close();
         
         return haveData;
	}

	@Override
	void insertObj(AbstractObj obj) {

	}

	@Override
	void updateObj(AbstractObj obj) {

	}
}
