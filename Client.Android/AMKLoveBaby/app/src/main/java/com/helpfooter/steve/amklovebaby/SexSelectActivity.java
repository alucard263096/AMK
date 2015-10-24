package com.helpfooter.steve.amklovebaby;

import android.content.Intent;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;
import com.helpfooter.steve.amklovebaby.DataObjs.SexObj;
import com.helpfooter.steve.amklovebaby.DataObjs.WorktimeObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amklovebaby.Loader.WorktimeLoader;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/10/24.
 */
public class SexSelectActivity extends SelectActivity {

    @Override
    public void InitData() {
        lst.add(new SexObj(true));
        lst.add(new SexObj(false));

    }


}
