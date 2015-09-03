package com.helpfooter.steve.amklovebaby;

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

import com.helpfooter.steve.amklovebaby.CustomObject.BottomBarButton;
import com.helpfooter.steve.amklovebaby.CustomObject.MyFragmentActivity;
import com.helpfooter.steve.amklovebaby.DAO.BannerDao;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.ParamsDao;
import com.helpfooter.steve.amklovebaby.DataObjs.BannerObj;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IMyFragment;
import com.helpfooter.steve.amklovebaby.Loader.BannerLoader;
import com.helpfooter.steve.amklovebaby.Loader.DoctorLoader;
import com.helpfooter.steve.amklovebaby.Utils.MyResourceIdUtil;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


public class MainActivity extends MyFragmentActivity implements View.OnClickListener
        ,HomeFragment.OnFragmentInteractionListener
        ,DoctorListFragment.OnFragmentInteractionListener {

    private LinearLayout bottomTabLayout,contentLayout;
    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private DoctorListFragment doctorListFragment;
    private TextView titleTextView;
    ArrayList<BottomBarButton> lstBottomBar;

    private BottomBarButton homeBarButton,newsBarButton,doctorBarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        InitBottomBar();
        InitData();
//        ParamsDao dao=new ParamsDao(this);
//        BannerDao bdao=new BannerDao(this);
//        ArrayList<BannerObj> lstBanner=bdao.getIndexBanner();
//        Log.i("indexbannercount",String.valueOf(lstBanner.size()));
//
//        DoctorLoader loader=new DoctorLoader(this);
//        loader.start();
//        DoctorDao ddao=new DoctorDao(this);
//        ArrayList<DoctorObj> lstDoctor=ddao.getDoctorList();
//        Log.i("indexbannercount",String.valueOf(lstDoctor.size()));

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

//        DoctorDao dao=new DoctorDao(this);
//        dao.deleteTable();
    }

    private void InitData() {
        DoctorLoader loader=new DoctorLoader(this);
        loader.start();

        BannerLoader bannerLoader=new BannerLoader(this);
        bannerLoader.setCallCode(StaticVar.BannerApi);
        bannerLoader.start();
    }


    /**
     * 初始化UI
     */
    private void initUI() {

        titleTextView=(TextView)findViewById(R.id.title);
        bottomTabLayout=(LinearLayout)findViewById(R.id.ll_bottom_tab);
        contentLayout=(LinearLayout)findViewById(R.id.content_layout);

        homeFragment=new HomeFragment();
        doctorListFragment=new DoctorListFragment();


        homeBarButton=new BottomBarButton(this.getApplicationContext(), "home", R.drawable.bar_home, R.drawable.bar_home_active, "首页", homeFragment );
        newsBarButton=new BottomBarButton(this.getApplicationContext(), "news", R.drawable.bar_news, R.drawable.bar_news_active, "新闻", null);
        doctorBarButton=new BottomBarButton(this.getApplicationContext(), "doctor", R.drawable.bar_doctor, R.drawable.bar_doctor_active, "医生", doctorListFragment);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.doctor){
            buttonBarClick(doctorBarButton);
        }
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
        lstBottomBar.add(homeBarButton);
        lstBottomBar.add(newsBarButton);
        lstBottomBar.add(doctorBarButton);

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
