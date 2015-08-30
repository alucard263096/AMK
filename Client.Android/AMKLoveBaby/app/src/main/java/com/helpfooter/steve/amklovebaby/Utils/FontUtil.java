package com.helpfooter.steve.amklovebaby.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;

import java.io.File;

/**
 * Created by Steve on 2015/8/26.
 */
public class FontUtil {
    static String fongUrlLight = "fonts/SourceHanSansCN-Light.ttf";
    static String fongUrlNormal = "fonts/SourceHanSansCN-Normal.otf";
    static String fongUrlMedium = "fonts/SourceHanSansCN-Medium.otf";
    static Typeface tfLight;
    static Typeface tfNormal;
    static Typeface tfMedium;

    public static Typeface setFont(Context context) {
        if(tfLight==null){
            tfLight = Typeface.createFromAsset(context.getAssets(), fongUrlLight);
        }
        return tfLight;
    }
    
    public static Typeface setFontNormal(Context context) {
        if(tfNormal==null){
            tfNormal = Typeface.createFromAsset(context.getAssets(), fongUrlNormal);
        }
        return tfNormal;
    }

    public static Typeface setFontMedium(Context context) {
        if(tfMedium==null){
            tfMedium = Typeface.createFromAsset(context.getAssets(), fongUrlMedium);
        }
        return tfMedium;
    }
}
