package com.helpfooter.steve.amklovebaby.DataObjs;


import android.database.Cursor;

import java.util.HashMap;

public class NewsObj extends  AbstractObj {
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}

	public String getNews_type() {
		return news_type;
	}

	public void setNews_type(String news_type) {
		this.news_type = news_type;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUpvote() {
		return upvote;
	}

	public void setUpvote(int upvote) {
		this.upvote = upvote;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	String title;
	String publish_date;
	String news_type;
	int doctor_id;
	String summary;
	String thumbnail;
	String status;
	int upvote;

	String content;


	@Override
	public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
		this.id=Integer.parseInt(lstRowValue.get("id"));
		this.title=lstRowValue.get("title");
		this.publish_date=lstRowValue.get("publish_date");
		this.news_type=lstRowValue.get("news_type");
		this.doctor_id=Integer.parseInt(lstRowValue.get("doctor_id"));
		this.summary=lstRowValue.get("summary");
		this.thumbnail=lstRowValue.get("thumbnail");
		this.status=lstRowValue.get("status");
		this.upvote=Integer.parseInt(lstRowValue.get("upvote"));
		this.content=lstRowValue.get("content");
	}


	@Override
	public void parseCursor(Cursor cursor) {
		setId(cursor.getInt(cursor.getColumnIndex("id")));
		this.title=cursor.getString(cursor.getColumnIndex("title"));
		this.publish_date=cursor.getString(cursor.getColumnIndex("publish_date"));
		this.news_type=cursor.getString(cursor.getColumnIndex("news_type"));
		this.doctor_id=cursor.getInt(cursor.getColumnIndex("doctor_id"));
		this.summary=cursor.getString(cursor.getColumnIndex("summary"));
		this.thumbnail=cursor.getString(cursor.getColumnIndex("thumbnail"));
		this.status=cursor.getString(cursor.getColumnIndex("status"));
		this.upvote=cursor.getInt(cursor.getColumnIndex("upvote"));
		this.content=cursor.getString(cursor.getColumnIndex("content"));
	}



}
