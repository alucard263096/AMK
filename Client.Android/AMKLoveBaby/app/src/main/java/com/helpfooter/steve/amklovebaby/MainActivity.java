package com.helpfooter.steve.amklovebaby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.MyTextView;
import com.helpfooter.steve.amklovebaby.CustomObject.BottomBarButton;
import com.helpfooter.steve.amklovebaby.CustomObject.MyFragmentActivity;

import java.util.ArrayList;


public class MainActivity extends MyFragmentActivity implements OnClickListener {

    // 三个tab布局
    private RelativeLayout knowLayout, iWantKnowLayout, meLayout;

    // 底部标签切换的Fragment
    private Fragment knowFragment, iWantKnowFragment, meFragment,
            currentFragment;
    // 底部标签图片
    private ImageView knowImg, iWantKnowImg, meImg;
    // 底部标签的文本
    private TextView knowTv, iWantKnowTv, meTv;

    private LinearLayout bottomTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        //initTab();
        InitBottomBar();
    }


    /**
     * 初始化UI
     */
    private void initUI() {
//        knowLayout = (RelativeLayout) findViewById(R.id.rl_know);
//        iWantKnowLayout = (RelativeLayout) findViewById(R.id.rl_want_know);
//        meLayout = (RelativeLayout) findViewById(R.id.rl_me);
//        knowLayout.setOnClickListener(this);
//        iWantKnowLayout.setOnClickListener(this);
//        meLayout.setOnClickListener(this);
//
//        knowImg = (ImageView) findViewById(R.id.iv_know);
//        iWantKnowImg = (ImageView) findViewById(R.id.iv_i_want_know);
//        meImg = (ImageView) findViewById(R.id.iv_me);
//        knowTv = (TextView) findViewById(R.id.tv_know);
//        iWantKnowTv = (TextView) findViewById(R.id.tv_i_want_know);
//        meTv = (TextView) findViewById(R.id.tv_me);

        bottomTabLayout=(LinearLayout)findViewById(R.id.ll_bottom_tab);
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (knowFragment == null) {
           return;
        }

        if (!knowFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, knowFragment).commit();

            // 记录当前Fragment
            currentFragment = knowFragment;
            // 设置图片文本的变化
            knowImg.setImageResource(R.drawable.btn_know_pre);
            knowTv.setTextColor(getResources()
                    .getColor(R.color.bottomtab_press));
            iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
            iWantKnowTv.setTextColor(getResources().getColor(
                    R.color.bottomtab_normal));
            meImg.setImageResource(R.drawable.btn_my_nor);
            meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

        }

    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.rl_know: // 知道
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
    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        if (knowFragment == null) {
            return;
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), knowFragment);

        // 设置底部tab变化
        knowImg.setImageResource(R.drawable.btn_know_pre);
        knowTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
        iWantKnowTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.btn_my_nor);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (iWantKnowFragment == null) {
           return;
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), iWantKnowFragment);

        knowImg.setImageResource(R.drawable.btn_know_nor);
        knowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        iWantKnowImg.setImageResource(R.drawable.btn_wantknow_pre);
        iWantKnowTv.setTextColor(getResources().getColor(
                R.color.bottomtab_press));
        meImg.setImageResource(R.drawable.btn_my_nor);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        if (meFragment == null) {
            return;
        }

        addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);
        knowImg.setImageResource(R.drawable.btn_know_nor);
        knowTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        iWantKnowImg.setImageResource(R.drawable.btn_wantknow_nor);
        iWantKnowTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.btn_my_pre);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_press));

    }


    public void InitBottomBar(){

        ArrayList<BottomBarButton> lstBottomBar=new ArrayList<BottomBarButton>();
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "home", R.drawable.bar_home, R.drawable.bar_home_active, "首页", null));
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "news", R.drawable.bar_news, R.drawable.bar_news_active, "新闻", null));
        lstBottomBar.add(new BottomBarButton(this.getApplicationContext(), "doctor", R.drawable.bar_doctor, R.drawable.bar_doctor_active, "医生", null));

        BottomBarButton.CreateEnteryBottomBar(bottomTabLayout,lstBottomBar);


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
