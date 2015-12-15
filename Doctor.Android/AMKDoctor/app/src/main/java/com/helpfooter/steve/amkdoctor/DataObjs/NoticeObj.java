package com.helpfooter.steve.amkdoctor.DataObjs;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class NoticeObj extends AbstractObj {

    int id;
    String publish_date;
    String notice_type;
    String title;
    String context;
    String sent_type;
    String haveread;
    int doctor_id;


    String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(String notice_type) {
        this.notice_type = notice_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSent_type() {
        return sent_type;
    }

    public void setSent_type(String sent_type) {
        this.sent_type = sent_type;
    }

    public String getHaveread() {
        return haveread;
    }

    public void setHaveread(String haveread) {
        this.haveread = haveread;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }








    @Override
    public void parseCursor(Cursor cursor) {

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setTitle(cursor.getString(cursor.getColumnIndex("title")));
        setPublish_date(cursor.getString(cursor.getColumnIndex("publish_date")));
        setNotice_type(cursor.getString(cursor.getColumnIndex("notice_type")));
        setContext(cursor.getString(cursor.getColumnIndex("context")));
        setStatus(cursor.getString(cursor.getColumnIndex("status")));
        setSent_type(cursor.getString(cursor.getColumnIndex("sent_type")));
        setDoctor_id(cursor.getInt(cursor.getColumnIndex("doctor_id")));
        setHaveread(cursor.getString(cursor.getColumnIndex("haveread")));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.doctor_id=Integer.parseInt(lstRowValue.get("doctor_id"));
        this.title=lstRowValue.get("title");
        this.publish_date=lstRowValue.get("publish_date");
        this.notice_type=lstRowValue.get("notice_type");
        this.context=lstRowValue.get("context");
        this.status=lstRowValue.get("status");
        this.sent_type=lstRowValue.get("sent_type");
        this.haveread=lstRowValue.get("haveread");


    }



}
