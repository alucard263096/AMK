package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;

/**
 * Created by scai on 2015/10/26.
 */
public class MemberPhotoDeleteLoader extends  ResultLoader {
    int member_id;
    int id;
    public MemberPhotoDeleteLoader(Context ctx,int member_id,int id) {
        super(ctx, StaticVar.MemberPhotoDeleteApi);
        this.member_id=member_id;
        this.id=id;
    }

    @Override
    public String getCallUrl() {
        String url= StaticVar.dictHashMap.get(callApi);

            url= (url+"?member_id="+String.valueOf(member_id)
                    +"?id="+String.valueOf(id)).replace(" ", "%20").replace("\n","%20");

        return url;
    }
}
