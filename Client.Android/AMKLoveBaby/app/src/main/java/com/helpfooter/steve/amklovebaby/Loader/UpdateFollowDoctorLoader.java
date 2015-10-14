package com.helpfooter.steve.amklovebaby.Loader;

import android.content.Context;
import android.util.Log;

import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.io.UnsupportedEncodingException;

/**
 * Created by Steve on 2015/9/9.
 */
public class UpdateFollowDoctorLoader extends ResultLoader {
     int member_id;
    int doctor_id;
    String is_follow;
    public UpdateFollowDoctorLoader(Context ctx, int member_id, int doctor_id, String is_follow) {
        super(ctx, StaticVar.UploadDoctorFollowAPI);
        this.member_id=member_id;
        this.doctor_id=doctor_id;
        this.is_follow=is_follow;
    }

    @Override
    public String getCallUrl() {
        // TODO Auto-generated method stub
        String url= StaticVar.dictHashMap.get(callApi);

        url= (url+"?member_id="+String.valueOf(member_id)
                    +"&doctor_id="+String.valueOf(doctor_id)
                    +"&is_follow="+is_follow).replace(" ", "%20").replace("\n","%20");

        Log.i("ordercreate_url", url);

        return url;
    }

}
