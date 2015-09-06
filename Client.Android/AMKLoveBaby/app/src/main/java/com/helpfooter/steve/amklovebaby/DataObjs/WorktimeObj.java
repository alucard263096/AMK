package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/9/6.
 */
public class WorktimeObj extends AbstractObj {
    @Override
    public void parseCursor(Cursor cursor) {
        //
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    String time;
    String used;


    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.time=lstRowValue.get("time");
        this.used=lstRowValue.get("used");
    }
}
