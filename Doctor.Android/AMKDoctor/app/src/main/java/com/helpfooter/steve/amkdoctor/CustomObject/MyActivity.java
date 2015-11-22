package com.helpfooter.steve.amkdoctor.CustomObject;

import android.app.Activity;
import android.widget.Toast;

import com.helpfooter.steve.amkdoctor.Interfaces.IMyActivity;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;

/**
 * Created by Steve on 2015/10/31.
 */
public class MyActivity extends Activity implements IMyActivity {
    public MyActivity(){
        SetCurrentActivity();
    }

    public void SetCurrentActivity(){
        StaticVar.CurrentActivity=this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this,"i am resun"+getMyName(),Toast.LENGTH_LONG).show();
        SetCurrentActivity();
    }

    protected String getMyName(){
        return this.toString();
    }

    public boolean PopupNotice(){
        return true;
    }

    @Override
    public Activity GetMyActivity() {
        return this;
    }
}
