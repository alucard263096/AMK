package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;

import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.HashMap;

/**
 * Created by Steve on 2015/10/20.
 */
public class CommentObj extends AbstractObj {

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }

    String member_name;
    int service;
    int ability;
    String comment;
    String comment_date;
    String reply;
    String reply_date;

    @Override
    public void parseCursor(Cursor cursor) {

    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {

        this.member_name= lstRowValue.get("member_name");
        this.comment= lstRowValue.get("comment");
        this.comment_date= lstRowValue.get("comment_date");
        this.reply= lstRowValue.get("reply");
        this.reply_date= lstRowValue.get("reply_date");
        this.service= ToolsUtil.StrParseToInt(lstRowValue.get("service"));
        this.ability= ToolsUtil.StrParseToInt(lstRowValue.get("ability"));

    }
}
