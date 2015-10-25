package com.helpfooter.steve.amkdoctor.DataObjs;

import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by Steve on 2015/8/31.
 */
public class BookerObj extends AbstractObj {
    int id;
    String bookno;
    String custid;
    String custname;
    String mobile;
    String price;
    String createtime;



    String sendmessage;
    String clicktype;
    String member_photo;
    String status;
    String precessstatus;
    String payment;
    String orderdate;
    String ordertime;
    int doctorid;
    String chattime;
    String description;
    String age;
    String sex;
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSendmessage() {
        return sendmessage;
    }

    public void setSendmessage(String sendmessage) {
        this.sendmessage = sendmessage;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    public String getClicktype() {
        return clicktype;
    }

    public void setClicktype(String clicktype) {
        this.clicktype = clicktype;
    }
    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public String getBookno() {
        return bookno;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPrecessstatus() {
        return precessstatus;
    }

    public void setPrecessstatus(String precessstatus) {
        this.precessstatus = precessstatus;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    public String getChattime() {
        return chattime;
    }

    public void setChattime(String chattime) {
        this.chattime = chattime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




    @Override
    public void parseCursor(Cursor cursor) {

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setBookno(cursor.getString(cursor.getColumnIndex("bookno")));
        setCustid(cursor.getString(cursor.getColumnIndex("custid")));
        setCustname(cursor.getString(cursor.getColumnIndex("custname")));
        setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
        setPrice(cursor.getString(cursor.getColumnIndex("price")));
        setCreatetime(cursor.getString(cursor.getColumnIndex("createtime")));
        setStatus(cursor.getString(cursor.getColumnIndex("status")));
        setPrecessstatus(cursor.getString(cursor.getColumnIndex("precessstatus")));
        setPayment(cursor.getString(cursor.getColumnIndex("payment")));
        setOrderdate(cursor.getString(cursor.getColumnIndex("orderdate")));
        setOrdertime(cursor.getString(cursor.getColumnIndex("ordertime")));
        setDoctorid(cursor.getInt(cursor.getColumnIndex("doctorid")));
        setChattime(cursor.getString(cursor.getColumnIndex("chattime")));
        setDescription(cursor.getString(cursor.getColumnIndex("description")));
        setMember_photo(cursor.getString(cursor.getColumnIndex("member_photo")));
        setAge(cursor.getString(cursor.getColumnIndex("age")));
        setSex(cursor.getString(cursor.getColumnIndex("sex")));
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.id=Integer.parseInt(lstRowValue.get("id"));
        this.bookno=lstRowValue.get("order_no");
        this.custid=lstRowValue.get("member_id");
        this.custname=lstRowValue.get("name");
        this.mobile=lstRowValue.get("mobile");
        this.price=lstRowValue.get("price");
        this.createtime=lstRowValue.get("created_time");
        this.status=lstRowValue.get("status");
        this.precessstatus=lstRowValue.get("process_status");
        this.payment=lstRowValue.get("payment");
        this.orderdate=lstRowValue.get("order_date");
        this.ordertime = lstRowValue.get("order_time");
        this.doctorid=Integer.parseInt(lstRowValue.get("doctor_id"));
        this.chattime = lstRowValue.get("chat_time");
        this.description = lstRowValue.get("description");
        this.member_photo=lstRowValue.get("member_photo");
        this.age = lstRowValue.get("age");
        this.sex=lstRowValue.get("sex");
    }



}
