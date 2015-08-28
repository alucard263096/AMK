package com.helpfooter.steve.amklovebaby;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomObject.BottomBarButton;
import com.helpfooter.steve.amklovebaby.CustomObject.MyFragmentActivity;
import com.helpfooter.steve.amklovebaby.Utils.MyResourceIdUtil;

import java.util.ArrayList;


public class MainActivity extends MyFragmentActivity implements View.OnClickListener,HomeFragment.OnFragmentInteractionListener {

    private LinearLayout bottomTabLayout,contentLayout;
    private Fragment currentFragment;
    private TextView titleTextView;
    ArrayList<BottomBarButton> lstBottomBar;

    private BottomBarButton homeBarButton,newsBarButton,doctorBarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        InitBottomBar();
    }


    /**
     * 初始化UI
     */
    private void initUI() {

        titleTextView=(TextView)findViewById(R.id.title);
        titleTextView.setOnClickListener(this);
        bottomTabLayout=(LinearLayout)findViewById(R.id.ll_bottom_tab);
        contentLayout=(LinearLayout)findViewById(R.id.content_layout);

        homeBarButton=new BottomBarButton(this.getApplicationContext(), "home", R.drawable.bar_home, R.drawable.bar_home_active, "首页", new HomeFragment() );
        newsBarButton=new BottomBarButton(this.getApplicationContext(), "news", R.drawable.bar_news, R.drawable.bar_news_active, "新闻", null);
        doctorBarButton=new BottomBarButton(this.getApplicationContext(), "doctor", R.drawable.bar_doctor, R.drawable.bar_doctor_active, "医生", null);
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
            titleTextView.setText("No in?");

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
                titleTextView.setText("c2");
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

        currentFragment = fragment;
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
