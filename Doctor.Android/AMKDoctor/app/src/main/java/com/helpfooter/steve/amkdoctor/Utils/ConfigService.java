package com.helpfooter.steve.amkdoctor.Utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigService {

	public static ConfigEntity LoadConfig(Context context)
	{
		ConfigEntity configEntity = new ConfigEntity();
    	SharedPreferences  share = context.getSharedPreferences("perference", Context.MODE_WORLD_WRITEABLE);  

        configEntity.name = share.getString("name", "");
        configEntity.password = share.getString("password", "");
        configEntity.IsSaveNameAndPw = share.getString("IsSaveNameAndPw", "").equals("1") ? true : false;
      
        configEntity.ip = share.getString("ip", "demo.anychat.cn");
        configEntity.port = share.getInt("port", 8906);
        

		return configEntity;
	}
	
	public static void SaveConfig(Context context,ConfigEntity configEntity)
	{
    	SharedPreferences  share = context.getSharedPreferences("perference",  Context.MODE_WORLD_WRITEABLE);  
        Editor editor = share.edit();//取得编辑器
        
        editor.putString("name", configEntity.name);
        editor.putString("password", configEntity.password);
        editor.putString("IsSaveNameAndPw", configEntity.IsSaveNameAndPw ? "1" : "0");
        
        editor.putString("ip", configEntity.ip);
        editor.putInt("port", configEntity.port);
        

    	editor.commit();
		
	}
	
}
