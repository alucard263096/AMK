package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class DoctorObj extends AbstractObj {
    String license;
    String name;
    String photo;
    String title;
    String office;
    String bookingtime;
    String introduce;
    String credentials;
    String expert;
    String enable_videochat;
    int videochat_price;
    String enable_charchat;
    int charchat_price;
    String status;
    int general_score;

    public int getChat_time() {
        return chat_time;
    }

    public void setChat_time(int chat_time) {
        this.chat_time = chat_time;
    }

    int chat_time;

    public int getService_score() {
        return service_score;
    }

    public void setService_score(int service_score) {
        this.service_score = service_score;
    }

    public int getAbility_score() {
        return ability_score;
    }

    public void setAbility_score(int ability_score) {
        this.ability_score = ability_score;
    }

    int service_score;
    int ability_score;


    public int getCharquerycount() {
        return charquerycount;
    }

    public void setCharquerycount(int charquerycount) {
        this.charquerycount = charquerycount;
    }

    public int getVideoquerycount() {
        return videoquerycount;
    }

    public void setVideoquerycount(int videoquerycount) {
        this.videoquerycount = videoquerycount;
    }

    int charquerycount;
    int videoquerycount;

    public String getLicense(){
        return license;
    }
    public String getName(){
        return name;
    }
    public String getOffice(){
        return office;
    }
    public String getPhoto(){
        return photo;
    }
    public String getBookingtime(){
        return bookingtime;
    }
    public String getIntroduce(){
        return introduce;
    }
    public String getCredentials(){
        return credentials;
    }
    public String getExpert(){
        return expert;
    }
    public String getEnableVideochat(){
        return enable_videochat;
    }
    public int getVideochatPrice(){
        return videochat_price;
    }
    public String getEnableCharchat(){
        return enable_charchat;
    }
    public int getCharchatPrice(){
        return charchat_price;
    }
    public int getMaxPrice()
    {
        int price = 0;
        if(enable_charchat.equals("Y") && enable_videochat.equals("Y"))
        {
            price = charchat_price > videochat_price ? charchat_price : videochat_price;
        }
        else if(enable_charchat.equals("N") && enable_videochat.equals("Y"))
        {
            price = videochat_price;
        }
        else if (enable_charchat.equals("Y") && enable_videochat.equals("N"))
        {
            price = videochat_price;
        }
        else
        {
            price = 0;
        }
        return price;
    }
    public String getStatus(){
        return status;
    }

    public void  setLicense(String val){
        license=val;
    }
    public void  setName(String val){
        name=val;
    }
    public void  setPhoto(String val){
        photo=val;
    }
    public void  setOffice(String val){
        office=val;
    }
    public void  setBookingtime(String val){
        bookingtime=val;
    }
    public void  setIntroduce(String val){
        introduce=val;
    }
    public void  setCredentials(String val){
        credentials=val;
    }
    public void  setExpert(String val){
        expert=val;
    }
    public void  setEnableVideochat(String val){
        enable_videochat=val;
    }
    public void  setVideochatPrice(int val){
        videochat_price=val;
    }
    public void  setEnable_charchat(String val){
        enable_charchat=val;
    }
    public void  setCharchat_price(int val){
        charchat_price=val;
    }
    public void  setStatus(String val){
        status=val;
    }

    String is_taiwan;
    public void  setIsTaiwan(String val){
        is_taiwan=val;
    }
    public String  getIsTaiwan(){
       return is_taiwan;
    }

    @Override
    public void parseCursor(Cursor cursor) {

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setLicense(cursor.getString(cursor.getColumnIndex("license")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
        setTitle(cursor.getString(cursor.getColumnIndex("title")));
        setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
        setOffice(cursor.getString(cursor.getColumnIndex("office")));
        setBookingtime(cursor.getString(cursor.getColumnIndex("bookingtime")));
        setIntroduce(cursor.getString(cursor.getColumnIndex("introduce")));
        setCredentials(cursor.getString(cursor.getColumnIndex("credentials")));
        setExpert(cursor.getString(cursor.getColumnIndex("expert")));
        setEnableVideochat(cursor.getString(cursor.getColumnIndex("enable_videochat")));
        setEnable_charchat(cursor.getString(cursor.getColumnIndex("enable_charchat")));
        setVideochatPrice(cursor.getInt(cursor.getColumnIndex("videochat_price")));
        setCharchat_price(cursor.getInt(cursor.getColumnIndex("charchat_price")));
        setStatus(cursor.getString(cursor.getColumnIndex("status")));
        setGeneralScore(cursor.getInt(cursor.getColumnIndex("general_score")));
        setService_score(cursor.getInt(cursor.getColumnIndex("service_score")));
        setAbility_score(cursor.getInt(cursor.getColumnIndex("ability_score")));
        setVideoquerycount(cursor.getInt(cursor.getColumnIndex("videoquerycount")));
        setCharquerycount(cursor.getInt(cursor.getColumnIndex("charquerycount")));
        setChat_time(cursor.getInt(cursor.getColumnIndex("chat_time")));
        is_taiwan=cursor.getString(cursor.getColumnIndex("is_taiwan"));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.license=lstRowValue.get("license");
        this.name=lstRowValue.get("name");
        this.office=lstRowValue.get("office");
        this.title=lstRowValue.get("title");
        this.photo=lstRowValue.get("photo");
        this.bookingtime=lstRowValue.get("bookingtime");
        this.introduce=lstRowValue.get("introduce");
        this.credentials=lstRowValue.get("credentials");
        this.expert=lstRowValue.get("expert");
        this.enable_videochat=lstRowValue.get("enable_videochat");
        this.videochat_price=Integer.parseInt(lstRowValue.get("videochat_price"));
        this.enable_charchat = lstRowValue.get("enable_charchat");
        this.charchat_price=Integer.parseInt(lstRowValue.get("charchat_price"));
        this.status=lstRowValue.get("status");
        this.general_score=Integer.parseInt(lstRowValue.get("general_score"));
        this.service_score=Integer.parseInt(lstRowValue.get("service_score"));
        this.ability_score=Integer.parseInt(lstRowValue.get("ability_score"));
        this.videoquerycount=Integer.parseInt(lstRowValue.get("videoquerycount"));
        this.charquerycount=Integer.parseInt(lstRowValue.get("charquerycount"));
        this.chat_time=Integer.parseInt(lstRowValue.get("chat_time"));

        is_taiwan=lstRowValue.get("is_taiwan");
    }

    public void parseXmlDataTableForStatistics(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.general_score=Integer.parseInt(lstRowValue.get("general_score"));
        this.service_score=Integer.parseInt(lstRowValue.get("service_score"));
        this.ability_score=Integer.parseInt(lstRowValue.get("ability_score"));
        this.videoquerycount=Integer.parseInt(lstRowValue.get("videoquerycount"));
        this.charquerycount=Integer.parseInt(lstRowValue.get("charquerycount"));
        this.chat_time=Integer.parseInt(lstRowValue.get("chat_time"));

    }
    public int getGeneralScore() {
        return general_score;
    }
    public void  setGeneralScore(int val){
        this.general_score=val;
    }

    public String getTitle() {
        return title;
    }
    public  void setTitle(String val){
        this.title=val;
    }


    public String getRealGeneralScore(){
        float gene=general_score*1.0f/(charquerycount+videoquerycount);
        DecimalFormat fnum   =   new   DecimalFormat("##0.0");
        String   dd=fnum.format(gene);
        return dd;
    }
}
