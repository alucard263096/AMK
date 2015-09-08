package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

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

    @Override
    public void parseCursor(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.mobile=lstRowValue.get("mobile");
        this.password=lstRowValue.get("password");
        this.verifycode=lstRowValue.get("verifycode");
        this.status=lstRowValue.get("status");
        this.name=lstRowValue.get("name");
    }
}
