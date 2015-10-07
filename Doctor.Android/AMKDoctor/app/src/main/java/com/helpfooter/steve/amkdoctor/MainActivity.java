package com.helpfooter.steve.amkdoctor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amkdoctor.CustomObject.BottomBarButton;
import com.helpfooter.steve.amkdoctor.CustomObject.MyFragmentActivity;
import com.helpfooter.steve.amkdoctor.Interfaces.IMyFragment;
import com.helpfooter.steve.amkdoctor.Loader.BannerLoader;
import com.helpfooter.steve.amkdoctor.Loader.BookerLoader;
import com.helpfooter.steve.amkdoctor.Loader.MessageLoader;
import com.helpfooter.steve.amkdoctor.Utils.MyResourceIdUtil;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;

import java.util.ArrayList;


public class MainActivity extends MyFragmentActivity implements View.OnClickListener
        ,VedioChatListFragment.OnFragmentInteractionListener
        ,NormalChatFragment.OnFragmentInteractionListener {

    private LinearLayout bottomTabLayout,contentLayout;
    private Fragment currentFragment;
    private TextView titleTextView;
    ArrayList<BottomBarButton> lstBottomBar;

    private BottomBarButton VedioChatListBarButton,NormalChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        InitBottomBar();
        InitData();

        WindowManager wm = this.getWindowManager();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Log.i("screen_info_width",String.valueOf(width));
        Log.i("screen_info_height",String.valueOf(height));
        Log.i("screen_info_density",String.valueOf(density));
        Log.i("screen_info_Dpi",String.valueOf(densityDpi));
    }

    private void InitData() {
        BannerLoader bannerLoader=new BannerLoader(this);
        bannerLoader.setCallCode(StaticVar.BannerApi);
        bannerLoader.start();

        BookerLoader bkLoader = new BookerLoader(this);
        bkLoader.start();

        MessageLoader messageLoader = new MessageLoader(this);
        messageLoader.start();

    }


    /**
     * 初始化UI
     */
    private void initUI() {

        titleTextView=(TextView)findViewById(R.id.title);
        bottomTabLayout=(LinearLayout)findViewById(R.id.ll_bottom_tab);
        contentLayout=(LinearLayout)findViewById(R.id.content_layout);

        VedioChatListBarButton=new BottomBarButton(this.getApplicationContext(), "chatVideo", 0, 0, "视频会诊", new VedioChatListFragment(this) );
        NormalChatButton=new BottomBarButton(this.getApplicationContext(), "chatNormal", 0, 0, "图文咨询", new NormalChatFragment(this));
    }


    @Override
    public void onClick(View view) {
        for(BottomBarButton barButton:lstBottomBar){
            if(view==barButton.GetEnteryLayout()){
                buttonBarClick(barButton);
                return;
            }
        }

    }

    private void buttonBarClick(BottomBarButton barButton) {
        for (BottomBarButton otherBarButton:lstBottomBar){
            otherBarButton.SetDefault();
        }
        barButton.SetActive();
        if(barButton.GetFragment()!=null){
            addOrShowFragment(getFragmentManager().beginTransaction(),barButton.GetFragment());
        }
    }


    public void InitBottomBar(){
        lstBottomBar=new ArrayList<BottomBarButton>();
        lstBottomBar.add(VedioChatListBarButton);
        lstBottomBar.add(NormalChatButton);

        BottomBarButton.CreateEnteryBottomBar(bottomTabLayout, lstBottomBar, this);
        this.onClick(lstBottomBar.get(0).GetEnteryLayout());

    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {

        if(currentFragment==null){
            if(!fragment.isAdded()) {
                transaction .add(R.id.content_layout, fragment).commit();
            }
            if(fragment instanceof IMyFragment){
                IMyFragment myFragment=(IMyFragment)fragment;
                titleTextView.setText(myFragment.getTitle());
            }
            currentFragment = fragment;
            return;
        }

        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        if(fragment instanceof IMyFragment){
            IMyFragment myFragment=(IMyFragment)fragment;
            titleTextView.setText(myFragment.getTitle());
        }
        currentFragment = fragment;
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
