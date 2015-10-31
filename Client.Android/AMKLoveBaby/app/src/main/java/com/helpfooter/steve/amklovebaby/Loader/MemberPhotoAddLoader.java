package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.EventLogTags;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by scai on 2015/10/26.
 */
public class MemberPhotoAddLoader extends  ResultLoader {
    int member_id;
    String title,description,photo;
    public MemberPhotoAddLoader(Context ctx,int member_id,String title,String description,String photo) {
        super(ctx, StaticVar.MemberPhotoAddApi);
        this.member_id=member_id;
        this.title=title;
        this.description=description;
        this.photo=photo;
    }

    public void NewFailResult(){
        
    }


    @Override
    public String getCallUrl() {
        String url= StaticVar.dictHashMap.get(callApi);

        try {
            url= (url+"?member_id="+String.valueOf(member_id)
                    +"&title="+java.net.URLEncoder.encode(title, "utf-8")
                    +"&description="+java.net.URLEncoder.encode(description, "utf-8")
                    +"&photo="+java.net.URLEncoder.encode(photo,"utf-8")).replace(" ", "%20").replace("\n","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("ordercreate_url", url);

        return url;
    }
}
