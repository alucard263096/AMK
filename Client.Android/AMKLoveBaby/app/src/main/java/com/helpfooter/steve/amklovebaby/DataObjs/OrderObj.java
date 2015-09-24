package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/9/7.
 */
public class OrderObj extends AbstractObj {

    @Override
    public void parseCursor(Cursor cursor) {
        this.id=cursor.getInt(cursor.getColumnIndex("id"));
        this.order_no= cursor.getString(cursor.getColumnIndex("order_no"));
        this.guid=cursor.getString(cursor.getColumnIndex("guid"));
        this.member_id=cursor.getInt(cursor.getColumnIndex("member_id"));
        this.name= cursor.getString(cursor.getColumnIndex("name"));
        this.mobile= cursor.getString(cursor.getColumnIndex("mobile"));
        this.price=cursor.getInt(cursor.getColumnIndex("price"));
        this.act= cursor.getString(cursor.getColumnIndex("act"));
        this.created_time= cursor.getString(cursor.getColumnIndex("created_time"));
        this.status= cursor.getString(cursor.getColumnIndex("status"));
        this.process_status= cursor.getString(cursor.getColumnIndex("process_status"));
        this.order_date= cursor.getString(cursor.getColumnIndex("order_date"));
        this.order_time= cursor.getString(cursor.getColumnIndex("order_time"));
        this.description= cursor.getString(cursor.getColumnIndex("description"));
        this.payment= cursor.getString(cursor.getColumnIndex("payment"));
        this.payment_type= cursor.getString(cursor.getColumnIndex("payment_type"));
        this.payment_time= cursor.getString(cursor.getColumnIndex("payment_time"));
        this.tag= cursor.getString(cursor.getColumnIndex("tag"));
    }

    String order_no;
    String guid;
    int member_id;
    String name;
    String mobile;
    int price;
    int discount;
    String act;
    String created_time;
    String status;
    String process_status;
    String order_date;
    String order_time;
    String description;

    String payment;
    String payment_type;
    String payment_time;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    String tag;


    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.order_no= lstRowValue.get("order_no");
        this.guid= lstRowValue.get("guid");
        this.member_id=Integer.parseInt(lstRowValue.get("member_id"));
        this.name= lstRowValue.get("name");
        this.mobile= lstRowValue.get("mobile");
        this.price=Integer.parseInt(lstRowValue.get("price"));
        //this.discount=Integer.parseInt(lstRowValue.get("discount"));
        this.act= lstRowValue.get("act");
        this.created_time= lstRowValue.get("created_time");
        this.status= lstRowValue.get("status");
        this.process_status= lstRowValue.get("process_status");
        this.order_date= lstRowValue.get("order_date");
        this.order_time= lstRowValue.get("order_time");
        this.description= lstRowValue.get("description");
        this.payment= lstRowValue.get("payment");
        this.payment_type= lstRowValue.get("payment_type");
        this.payment_time= lstRowValue.get("payment_time");
        this.tag= lstRowValue.get("tag");
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcess_status() {
        return process_status;
    }

    public void setProcess_status(String process_status) {
        this.process_status = process_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getActName() {
        if(act.equals("VC")){
            return "视频会诊";
        }else if(act.equals("CQ")) {
            return "图文咨询";
        }
        return "未知类型";
    }
    public String getStatusName(){
        if(status.equals("T")){
            return "未付款";
        }else if(status.equals("P")) {
            return "已付款";
        }else if (status.equals("F")){
            return "已完成";
        }
        return "未知状态";
    }

    public String getStatusAction() {
        if(status.equals("T")){
            return "立即付款";
        }else if(status.equals("P")) {
            return "开始视频会诊";
        }else if (status.equals("F")){
            return "立即评价";
        }
        return "未知状态";
    }

    public String getActDescription() {
        if(act.equals("VC")){
            return "用于在指定时间与医生启动视频对话咨询";
        }else if(act.equals("CQ")) {
            return "用于在利用文字和图片与医生进行咨询";
        }
        return "未知类型";
    }
}
