package com.helpfooter.steve.amkdoctor.DataObjs;


import java.util.HashMap;

public class BannerObj extends  AbstractObj {
	int id;
	String code;
	String title;
	String link;
	String pic;
	String status;
	String updated_date;

	public int getId(){
		return  id;
	}

	@Override
	public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
		this.id=Integer.parseInt(lstRowValue.get("id"));
		this.code=lstRowValue.get("code");
		this.title=lstRowValue.get("title");
		this.link=lstRowValue.get("link");
		this.pic=lstRowValue.get("pic");
		this.status=lstRowValue.get("status");
		this.updated_date=lstRowValue.get("updated_date");
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
	public String getUpdated_date(){
		return  updated_date;
	}
	public void  setId(int val){
		id=val;
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
	public void setUpdated_date(String val){
		updated_date=val;
	}


}
