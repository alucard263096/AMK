package com.helpfooter.steve.amkdoctor.Utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolsUtil {
    public static String Encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
    public static PercentLinearLayout.LayoutParams getLayoutParam(){
        PercentLinearLayout.LayoutParams param=new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT,PercentLinearLayout.LayoutParams.MATCH_PARENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo=new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.fillLayoutParams(param, PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.MATCH_PARENT);
        param.mPercentLayoutInfo=layoutinfo;
        return param;
    }
    public static  String FormatString(String str){
        String notice = "";
        try{
            notice = URLEncoder.encode(str, "utf-8");
        }catch(UnsupportedEncodingException ex){
        }
        return str;
    }
    public static boolean IsAfternoon(){
        Date curDate    =   new    Date(System.currentTimeMillis());
        Date in12AM=new Date(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,12,0,0);
        return (curDate.getTime()>in12AM.getTime());
    }
    public static boolean isMobileNO(String mobiles){

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }
    public static  LinearLayout GenPLine(Context ctx){

        LinearLayout pLine=new LinearLayout(ctx);
        LinearLayout.LayoutParams pLineLayout=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
        pLineLayout.topMargin=1;
        pLine.setLayoutParams(pLineLayout);
        pLine.setBackgroundColor(Color.parseColor("#cccccc"));

        return pLine;
    }
}
