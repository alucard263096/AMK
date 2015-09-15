package com.helpfooter.steve.amklovebaby.Common;

import android.content.Context;
import android.content.Intent;

import com.helpfooter.steve.amklovebaby.DAO.MemberDao;
import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.LoginActivity;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/9/9.
 */
public class MemberMgr {
    public static boolean CheckIsLogin(Context ctx){
        if(StaticVar.Member==null){

            Intent intent = new Intent();
            intent.setClass(ctx, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
            ctx.startActivity(intent);
            
            return false;
        }
        return true;
    }

    public static void GetMemberInfoFromDb(Context ctx){
        MemberDao memberDao=new MemberDao(ctx);
        ArrayList<AbstractObj> lstMember=memberDao.getList("");
        if(lstMember.size()>0){
            MemberObj member=(MemberObj)lstMember.get(0);
            StaticVar.Member=member;
        }
    }

}
