package com.helpfooter.steve.amkdoctor.DataObjs;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class MemberObj extends AbstractObj {
    int id;
    String name;
    String photo;
    String sex;
    int age;


    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSex(){
        return sex;
    }
    public String getPhoto(){
        return photo;
    }
    public int getAge(){
        return getDateYears(String.valueOf(age));
    }

    public void  setId(int val){
        id=val;
    }
    public void  setName(String val){
        name=val;
    }
    public void  setPhoto(String val){
        photo=val;
    }
    public void  setSex(String val){

        sex=val;
    }
    public void  setAge(String val){
        age=getDateYears(val);
    }

    public int getDateYears (String orderTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate    =   new    Date(System.currentTimeMillis());
        try {
            Date date = sdf.parse(orderTime);// 通过日期格式的parse()方法将字符串转换成日期              Date dateBegin = sdf.parse(date2);
            long betweenTime = curDate.getTime() - date.getTime();
            betweenTime  = betweenTime  / 1000 / 60/60/24/365;
            return (int)betweenTime;
        } catch(Exception e)
        {
        }
        return 0;

    }


    @Override
    public void parseCursor(Cursor cursor) {

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setSex(cursor.getString(cursor.getColumnIndex("sex")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
        setAge(cursor.getString(cursor.getColumnIndex("birth")));
        setPhoto(cursor.getString(cursor.getColumnIndex("photo")));

    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.sex=lstRowValue.get("sex");
        this.name=lstRowValue.get("name");
        this.age=Integer.parseInt(lstRowValue.get("birth"));
        this.photo=lstRowValue.get("photo");

    }



}
