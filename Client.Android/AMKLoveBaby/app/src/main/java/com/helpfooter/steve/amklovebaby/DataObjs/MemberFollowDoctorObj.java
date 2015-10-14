package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/10/11.
 */
public class MemberFollowDoctorObj extends AbstractObj {

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    int member_id;
    int doctor_id;

    @Override
    public void parseCursor(Cursor cursor) {
        setMember_id(cursor.getInt(cursor.getColumnIndex("member_id")));
        setDoctor_id(cursor.getInt(cursor.getColumnIndex("doctor_id")));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.member_id=Integer.parseInt(lstRowValue.get("member_id"));
        this.doctor_id=Integer.parseInt(lstRowValue.get("doctor_id"));
    }
}
