package com.helpfooter.steve.amklovebaby.Interfaces;

import android.widget.ImageView;

import com.helpfooter.steve.amklovebaby.DataObjs.AbstractObj;

import java.util.ArrayList;

/**
 * Created by Steve on 2015/10/24.
 */
public interface ISelectObj {
    String DisplayName();
    String SelectedValue();
    boolean ShowLogo();
    boolean IsDisabled();
    void LoadImage(ImageView img);
}
