package com.helpfooter.steve.amkdoctor.DataObjs;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractObj {
    //SELECT count(*) FROM sqlite_master WHERE type='table' AND name='tableName';
    public int id=0;
    public int getId(){
        return  id;
    }
    public void setId(int val){
        this.id=val;
    }
    public abstract void parseCursor(Cursor cursor);
    public abstract void parseXmlDataTable(HashMap<String, String> lstRowValue);
}
