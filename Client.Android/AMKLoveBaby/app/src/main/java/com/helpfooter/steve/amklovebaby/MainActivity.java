package com.helpfooter.steve.amklovebaby;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.helpfooter.steve.amklovebaby.CustomObject.BottomBarButton;
import com.helpfooter.steve.amklovebaby.CustomObject.MyFragmentActivity;

import java.util.ArrayList;


public class MainActivity extends MyFragmentActivity{

    private LinearLayout bottomTabLayout;
    private Fragment currentFragment;

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


        bottomTabLayout=(LinearLayout)findViewById(R.id.ll_bottom_tab);
    }

//    /**
//     * 初始化底部标签
//     */
//    private void initTab() {
//        if (knowFragment == null) {
//           return;
//        }
//
//        if (!knowFragment.isAdded()) {
//            // 提交事务
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.content_layout, knowFragment).commit();
//
//            // 记录当前Fragment
//            currentFragment = knowFragment;
//            // 设置图片文本的变化
//            knowImg.setImageResource(R.drawable.btn_know_pre);
//            knowTv.setTextColor(getResources()
//                    .getColor(R.color.bottomtab_press));
//            iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
//            iWantKnowTv.setTextColor(getResources().getColor(
//                    R.color.bottomtab_normal));
//            meImg.setImageResource(R.drawable.btn_my_nor);
//            meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
//
//        }
//
//    }




    public void InitBottomBar(){

        ArrayList<BottomBarButton> lstBottomBar=new ArrayList<BottomBarButton>();
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "home", R.drawable.bar_home, R.drawable.bar_home_active, "首页", new HomeFragment() ));
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "news", R.drawable.bar_news, R.drawable.bar_news_active, "新闻", null));
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "doctor", R.drawable.bar_doctor, R.drawable.bar_doctor_active, "医生", null));

        BottomBarButton.CreateEnteryBottomBar(bottomTabLayout, lstBottomBar);
        Fragment fragment= lstBottomBar.get(0).GetFragment();
        FragmentManager fm=getSupportFragmentManager();
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), fragment);
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
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
}
