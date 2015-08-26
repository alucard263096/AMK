package com.helpfooter.steve.amklovebaby.Utils;

import java.util.Hashtable;

/**
 * Created by Steve on 2015/8/27.
 */
public class MyResourceIdUtil {
    private  static Hashtable<String,Integer> dictIdMap=new Hashtable<String,Integer>();
    private  static int startNumber=13455;

    public  static int  GetMyResourceId(String resourceName){
        if(dictIdMap.contains(resourceName)){
            return dictIdMap.get(startNumber);
        }else {
            int ret=startNumber++;
            dictIdMap.put(resourceName,ret);
            return ret;
        }
    }

}
