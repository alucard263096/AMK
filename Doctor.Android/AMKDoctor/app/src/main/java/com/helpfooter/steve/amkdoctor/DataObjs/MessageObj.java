package com.helpfooter.steve.amkdoctor.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class MessageObj extends AbstractObj {
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

    public String getName() {
        return name;
    }

    public String getCreated_time() {
        return created_time;
    }

    public String getLast_one() {
        return last_one;
    }

    public String getDescription() {
        return description;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public void setLast_one(String last_one) {
        this.last_one = last_one;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    int id;
    int doctor_id;
    String member_id;
    String name;
    String created_time;
    String last_one;
    String description;

    public String getSendmessage() {
        return sendmessage;
    }

    public void setSendmessage(String sendmessage) {
        this.sendmessage = sendmessage;
    }

    String sendmessage;
    public String getSendside() {
        return sendside;
    }

    public void setSendside(String sendside) {
        this.sendside = sendside;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    String sendside;
    String member_photo;
    @Override
    public void parseCursor(Cursor cursor) {

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setMember_id(cursor.getString(cursor.getColumnIndex("member_id")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
        setCreated_time(cursor.getString(cursor.getColumnIndex("created_time")));
        setLast_one(cursor.getString(cursor.getColumnIndex("last_one")));
        setDescription(cursor.getString(cursor.getColumnIndex("description")));
        setSendside(cursor.getString(cursor.getColumnIndex("sendside")));
        setMember_photo(cursor.getString(cursor.getColumnIndex("member_photo")));
        setSendmessage(cursor.getString(cursor.getColumnIndex("sendmessage")));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.member_id=lstRowValue.get("member_id");
        this.name=lstRowValue.get("name");
        this.created_time=lstRowValue.get("created_time");
        this.doctor_id=Integer.parseInt(lstRowValue.get("doctor_id"));
        this.last_one = lstRowValue.get("last_one");
        this.description = lstRowValue.get("description");
        this.sendside = lstRowValue.get("sendside");
        this.member_photo = lstRowValue.get("member_photo");
        this.sendmessage = lstRowValue.get("sendmessage");

    }

}
