package com.helpfooter.steve.amklovebaby.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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

    public static PercentLinearLayout.LayoutParams getLayoutParam() {
        PercentLinearLayout.LayoutParams param = new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.MATCH_PARENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo = new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.fillLayoutParams(param, PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.MATCH_PARENT);
        param.mPercentLayoutInfo = layoutinfo;
        return param;
    }
    public static PercentLinearLayout.LayoutParams getLayoutParamHeightWrap() {
        PercentLinearLayout.LayoutParams param = new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.WRAP_CONTENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo = new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.fillLayoutParams(param, PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.WRAP_CONTENT);
        param.mPercentLayoutInfo = layoutinfo;
        return param;
    }
    public static PercentLinearLayout.LayoutParams getLayoutParamWidthHeightWrap() {
        PercentLinearLayout.LayoutParams param = new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.WRAP_CONTENT, PercentLinearLayout.LayoutParams.WRAP_CONTENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo = new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.fillLayoutParams(param, PercentLinearLayout.LayoutParams.WRAP_CONTENT, PercentLinearLayout.LayoutParams.WRAP_CONTENT);
        param.mPercentLayoutInfo = layoutinfo;
        return param;
    }

    public static LinearLayout GenPLine(Context ctx) {

        LinearLayout pLine = new LinearLayout(ctx);
        LinearLayout.LayoutParams pLineLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        pLine.setLayoutParams(pLineLayout);
        pLine.setBackgroundColor(Color.parseColor("#cccccc"));

        return pLine;
    }

    public static String FormatString(String str) {
        String notice = "";
        try {
            notice = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return str;
    }

    public static boolean IsAfternoon() {
        Date curDate = new Date(System.currentTimeMillis());
        Date in12AM = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 0, 0);
        return (curDate.getTime() > in12AM.getTime());
    }

    public static boolean isMobileNO(String mobiles) {


        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    public static String earseMobileNo(String name){
        if(isMobileNO(name)){
            return name.substring(0,4)+"****"+name.substring(8);
        }
        return name;
    }


    static  int IO_BUFFER_SIZE=1024;
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public static int  StrParseToInt(String str){
        if(str.trim().length()==0){
            return 0;
        }else {
            try{
               return Integer.parseInt(str);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return 0;
    }

}