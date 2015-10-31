package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Steve on 2015/9/9.
 */
public class MemberPhotoObj extends AbstractObj {

    String title;
    String description;
    String photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public void parseCursor(Cursor cursor) {
       id= cursor.getInt(cursor.getColumnIndex("id"));
        title= cursor.getString(cursor.getColumnIndex("title"));
        description= cursor.getString(cursor.getColumnIndex("description"));
        photo= cursor.getString(cursor.getColumnIndex("photo"));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.title=lstRowValue.get("title");
        this.description=lstRowValue.get("description");
        this.photo=lstRowValue.get("photo");
    }
}
