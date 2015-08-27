package com.helpfooter.steve.amklovebaby;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomObject.BottomBarButton;
import com.helpfooter.steve.amklovebaby.CustomObject.MyFragmentActivity;
import com.helpfooter.steve.amklovebaby.Utils.MyResourceIdUtil;

import java.util.ArrayList;


public class MainActivity extends MyFragmentActivity implements View.OnClickListener {

    private LinearLayout bottomTabLayout;
    private Fragment currentFragment;
    private TextView titleTextView;
    ArrayList<BottomBarButton> lstBottomBar;
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

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case MyResourceIdUtil.GetMyResourceId("bar_"): // 知道
//                clickTab1Layout();
//                break;
//            case R.id.rl_want_know: // 我想知道
//                clickTab2Layout();
//                break;
//            case R.id.rl_me: // 我的
//                clickTab3Layout();
//                break;
//            default:
//                break;
//        }
        Fragment fragment= new HomeFragment();
        if(!fragment.isAdded()){
            FragmentManager fm=getFragmentManager();
            FragmentTransaction transaction=fm.beginTransaction();
            transaction.add(R.id.content_layout, fragment).commit();
            titleTextView.setText("in");
        }else {
            titleTextView.setText("out");
        }
    }


    public void InitBottomBar(){

        lstBottomBar=new ArrayList<BottomBarButton>();
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "home", R.drawable.bar_home, R.drawable.bar_home_active, "首页", new HomeFragment() ));
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "news", R.drawable.bar_news, R.drawable.bar_news_active, "新闻", null));
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "doctor", R.drawable.bar_doctor, R.drawable.bar_doctor_active, "医生", null));

        BottomBarButton.CreateEnteryBottomBar(bottomTabLayout, lstBottomBar,this);
//        lstBottomBar.get(0).GetEnteryLayout().callOnClick();
//        Fragment fragment= lstBottomBar.get(0).GetFragment();
//        FragmentManager fm=getFragmentManager();
//        FragmentTransaction transaction=fm.beginTransaction();
//        //addOrShowFragment(transaction, fragment);
////        if(!fragment.isAdded()){
////            transaction.add(R.id.content_layout, fragment).commit();
////        }
//        String str="a";
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
