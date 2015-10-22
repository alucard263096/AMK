package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/9.
 */
public class MemberObj extends AbstractObj {

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String mobile;
    String name;
    String password;
    String verifycode;
    String status;

    String sex;
    String birth;
    String photo;
    String history;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAge(){
        String age="";
        try{
            if(birth!=null&&birth.trim().length()>0){
                SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date=new Date();
                java.util.Date mydate= myFormatter.parse(birth);
                long day=(date.getTime()-mydate.getTime())/(24*60*60*1000) + 1;
                age=new java.text.DecimalFormat("#.00").format(day/365f);
            }
        }catch (Exception ex){

        }
        return age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public void parseCursor(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
        setName(cursor.getString(cursor.getColumnIndex("name")));

        setSex(cursor.getString(cursor.getColumnIndex("sex")));
        setBirth(cursor.getString(cursor.getColumnIndex("birth")));
        setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
        setHistory(cursor.getString(cursor.getColumnIndex("history")));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.mobile=lstRowValue.get("mobile");
        this.password=lstRowValue.get("password");
        this.verifycode=lstRowValue.get("verifycode");
        this.status=lstRowValue.get("status");
        this.name=lstRowValue.get("name");

        this.sex=lstRowValue.get("sex");
        this.birth=lstRowValue.get("birth");
        this.photo=lstRowValue.get("photo");
        this.history=lstRowValue.get("history");
    }
}
