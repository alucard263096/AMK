package com.helpfooter.steve.amklovebaby.Utils;

import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
