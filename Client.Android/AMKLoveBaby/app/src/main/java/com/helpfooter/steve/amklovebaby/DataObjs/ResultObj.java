package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/9/6.
 */
public class ResultObj extends  AbstractObj {
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    @Override
    public void parseCursor(Cursor cursor) {


    }

    String result;
    String ret;

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.result=lstRowValue.get("result");
        this.ret=lstRowValue.get("return");
    }
}
