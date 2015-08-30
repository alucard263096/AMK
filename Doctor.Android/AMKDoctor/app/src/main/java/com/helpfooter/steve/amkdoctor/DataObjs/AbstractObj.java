package com.helpfooter.steve.amkdoctor.DataObjs;

import java.util.HashMap;

public abstract class AbstractObj {
    //SELECT count(*) FROM sqlite_master WHERE type='table' AND name='tableName';
    public int id=0;
    public int getId(){
        return  id;
    }

    abstract void parseXmlDataTable(HashMap<String,String> lstRowValue);
}
