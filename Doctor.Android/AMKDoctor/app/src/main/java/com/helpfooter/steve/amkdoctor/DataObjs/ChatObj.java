package com.helpfooter.steve.amkdoctor.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class ChatObj extends AbstractObj {
    @Override
    public int getId() {
        return id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getMember_id() {
        return member_id;
    }


    public String getLast_one() {
        return last_one;
    }

    public String getContent() {
        return content;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    String updated_date;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }



    public void setLast_one(String last_one) {
        this.last_one = last_one;
    }

    public void setContent(String content) {
        this.content = content;
    }

    int id;
    int doctor_id;
    String member_id;
    String content;
    String last_one;


    @Override
    public void parseCursor(Cursor cursor) {

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setMember_id(cursor.getString(cursor.getColumnIndex("member_id")));
        setDoctor_id(cursor.getInt(cursor.getColumnIndex("doctor_id")));
        setLast_one(cursor.getString(cursor.getColumnIndex("last_one")));
        setContent(cursor.getString(cursor.getColumnIndex("content")));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.member_id=lstRowValue.get("member_id");
        this.doctor_id=Integer.parseInt(lstRowValue.get("doctor_id"));
        this.last_one = lstRowValue.get("last_one");
        this.content = lstRowValue.get("content");
    }

}
