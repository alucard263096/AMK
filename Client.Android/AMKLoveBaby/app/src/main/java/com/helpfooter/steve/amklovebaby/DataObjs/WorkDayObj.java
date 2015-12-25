package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;
import android.widget.ImageView;

import com.helpfooter.steve.amklovebaby.Interfaces.ISelectObj;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;

import java.util.HashMap;

/**
 * Created by Steve on 2015/10/24.
 */
public class WorkDayObj extends AbstractObj implements ISelectObj {

    String date;
    int w;
    int totalCount;
    int usedCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    @Override
    public void parseCursor(Cursor cursor) {

    }
    @Override
    public boolean IsDisabled() {
        return false;
    }

    @Override
    public void parseXmlDataTable(HashMap<String, String> lstRowValue) {
        this.date=lstRowValue.get("date");
        this.w=Integer.parseInt(lstRowValue.get("w"));
        this.totalCount=Integer.parseInt(lstRowValue.get("totalcount"));
        this.usedCount=Integer.parseInt(lstRowValue.get("usedcount"));
    }

    @Override
    public String DisplayName() {
        String weekname="";
        switch (this.w){
            case 1:
                weekname="星期一";
                break;
            case 2:
                weekname="星期二";
                break;
            case 3:
                weekname="星期三";
                break;
            case 4:
                weekname="星期四";
                break;
            case 5:
                weekname="星期五";
                break;
            case 6:
                weekname="星期六";
                break;
            case 7:
                weekname="星期天";
                break;
        }
        return date+" ("+weekname+")"+((this.totalCount-this.getUsedCount())<3?" 少量":"");
    }

    @Override
    public String SelectedValue() {
        return date;
    }

    @Override
    public boolean ShowLogo() {
        return false;
    }

    @Override
    public void LoadImage(ImageView img) {

    }
}
