package com.helpfooter.steve.amklovebaby.DataObjs;

import android.database.Cursor;
import android.widget.ImageView;

import com.helpfooter.steve.amklovebaby.Interfaces.ISelectObj;

import java.util.HashMap;

/**
 * Created by Steve on 2015/10/24.
 */
public class SexObj  implements ISelectObj {

    boolean isMale=false;
    public SexObj(boolean isMale){
        this.isMale=isMale;
    }

    @Override
    public String DisplayName() {
        return isMale?"男":"女";
    }

    @Override
    public String SelectedValue() {
        return isMale?"M":"F";
    }

    @Override
    public boolean ShowLogo() {
        return false;
    }

    @Override
    public void LoadImage(ImageView img) {

    }
}
