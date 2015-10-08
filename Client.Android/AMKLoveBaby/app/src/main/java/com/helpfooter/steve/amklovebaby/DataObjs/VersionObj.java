package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/10/7.
 */
public class VersionObj extends AbstractObj {
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String version;
    String url;

    @Override
    public void parseCursor(Cursor cursor) {

    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.url=lstRowValue.get("url");
        this.version=lstRowValue.get("version");
    }
}
