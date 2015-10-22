package com.helpfooter.steve.amklovebaby.DataObjs;

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

    //该方法用于解析从本地数据库读取数据时的方法
    public abstract void parseCursor(Cursor cursor);
    //该方法是用于解析从数据访问接口访问到某一行使加载数据的接口
    public abstract void parseXmlDataTable(HashMap<String, String> lstRowValue);


}
