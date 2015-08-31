package com.helpfooter.steve.amklovebaby.DataObjs;

import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class DoctorObj extends AbstractObj {
    String license;
    String name;
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
    double general_score;

    public String getLicense(){
        return license;
    }
    public String getName(){
        return name;
    }
    public String getOffice(){
        return office;
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
    public String getStatus(){
        return status;
    }

    public void  setLicense(String val){
        license=val;
    }
    public void  setName(String val){
        name=val;
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
    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.license=lstRowValue.get("license");
        this.name=lstRowValue.get("name");
        this.office=lstRowValue.get("office");
        this.bookingtime=lstRowValue.get("bookingtime");
        this.introduce=lstRowValue.get("introduce");
        this.credentials=lstRowValue.get("credentials");
        this.expert=lstRowValue.get("expert");
        this.enable_videochat=lstRowValue.get("enable_videochat");
        this.videochat_price=Integer.parseInt(lstRowValue.get("videochat_price"));
        this.enable_charchat = lstRowValue.get("enable_charchat");
        this.charchat_price=Integer.parseInt(lstRowValue.get("charchat_price"));
        this.status=lstRowValue.get("status");
        this.general_score=Double.parseDouble(lstRowValue.get("general_score"));

    }

    public double getGeneralScore() {
        return general_score;
    }
    public void  setGeneralScore(double val){
        this.general_score=val;
    }
}
