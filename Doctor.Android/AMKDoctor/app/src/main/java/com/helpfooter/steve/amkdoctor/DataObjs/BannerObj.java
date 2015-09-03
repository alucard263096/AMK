package com.helpfooter.steve.amklovebaby.DataObjs;


import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BannerObj extends  AbstractObj {
	String code;
	String title;
	String link;
	String pic;
	String status;


	@Override
	public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
		this.id=Integer.parseInt(lstRowValue.get("id"));
		this.code=lstRowValue.get("code");
		this.title=lstRowValue.get("title");
		this.link=lstRowValue.get("link");
		this.pic=lstRowValue.get("pic");
		this.status=lstRowValue.get("status");
	}

	public String getCode(){
		return code;
	}
	public String getTitle(){
		return  title;
	}
	public String getLink(){
		return link;
	}
	public String getPic(){
		return pic;
	}
	public String getStatus(){
		return status;
	}
	public void  setId(int val){
		id=val;
	}

	@Override
	public void parseCursor(Cursor cursor) {
		setId(cursor.getInt(cursor.getColumnIndex("id")));
		setCode(cursor.getString(cursor.getColumnIndex("code")));
		setTitle(cursor.getString(cursor.getColumnIndex("title")));
		setLink(cursor.getString(cursor.getColumnIndex("link")));
		setPic(cursor.getString(cursor.getColumnIndex("pic")));
		setStatus(cursor.getString(cursor.getColumnIndex("status")));
	}

	public void setCode(String val){
		code=val;
	}
	public void setTitle(String val){
		title=val;
	}
	public void setLink(String val){
		link=val;
	}
	public void setPic(String val){
		pic=val;
	}
	public void setStatus(String val){
		status=val;
	}


}
