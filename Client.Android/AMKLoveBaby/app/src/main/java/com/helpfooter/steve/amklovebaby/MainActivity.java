package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.Common.VersionUpdateMgr;
import com.helpfooter.steve.amklovebaby.CustomObject.BottomBarButton;
import com.helpfooter.steve.amklovebaby.CustomObject.MyFragmentActivity;
import com.helpfooter.steve.amklovebaby.CustomObject.VideoChatNotice;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IMyActivity;
import com.helpfooter.steve.amklovebaby.Interfaces.IMyFragment;
import com.helpfooter.steve.amklovebaby.Loader.BannerLoader;
import com.helpfooter.steve.amklovebaby.Loader.DoctorLoader;
import com.helpfooter.steve.amklovebaby.Loader.MemberUpdateLoader;
import com.helpfooter.steve.amklovebaby.Loader.NewsLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends MyFragmentActivity implements View.OnClickListener,IMyActivity
        ,HomeFragment.OnFragmentInteractionListener
        ,DoctorSearchFragment.OnFragmentInteractionListener
        ,NewsListFragment.OnFragmentInteractionListener
        ,MemberMainFragment.OnFragmentInteractionListener{

    private LinearLayout bottomTabLayout,contentLayout;
    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private DoctorSearchFragment doctorSearchFragment;
    private NewsListFragment newsListFragment;
    private MemberMainFragment memberMainFragment;
    private TextView titleTextView;
    ArrayList<BottomBarButton> lstBottomBar;
    VersionUpdateMgr versionUpdateMgr;
    private MemberUpdateLoader updateloader;

    private BottomBarButton homeBarButton,newsBarButton,doctorSearchBarButton,memberMainBarButton;

    static boolean hasload=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        InitBottomBar();
        if(hasload==false){
            InitData();
            hasload=true;
        }
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


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        StaticVar.width = metric.widthPixels;     // 屏幕宽度（像素）
        StaticVar.height = metric.heightPixels;   // 屏幕高度（像素）
        StaticVar.density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        StaticVar.densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Log.i("screen_info_width", String.valueOf(StaticVar.width));
        Log.i("screen_info_height", String.valueOf(StaticVar.height));
        Log.i("screen_info_density", String.valueOf(StaticVar.density));
        Log.i("screen_info_Dpi", String.valueOf(StaticVar.densityDpi));






        StaticVar.MainForm=this;
//        DoctorDao dao=new DoctorDao(this);
//        dao.deleteTable();

        SetCurrentActivity();
    }

    private void InitData() {

        WindowManager wm = this.getWindowManager();
      /*  Intent service = new Intent(this.getApplicationContext(),MessageService.class);
        this.getApplicationContext().startService(service);
*/
        MemberMgr.GetMemberInfoFromDb(this);

        versionUpdateMgr=new VersionUpdateMgr(this);
        versionUpdateMgr.startCheckVersion();

        DoctorLoader loader=new DoctorLoader(this);
        loader.start();

        BannerLoader bannerLoader=new BannerLoader(this);
        bannerLoader.setCallCode(StaticVar.BannerApi);
        bannerLoader.start();

        NewsLoader newsLoader=new NewsLoader(this);
        newsLoader.start();


        VideoChatNotice videoChatNotice=new VideoChatNotice();
        videoChatNotice.start();

        MemberMgr.InitMemberData(this);
    }


    /**
     * 初始化UI
     */
    private void initUI() {

        titleTextView=(TextView)findViewById(R.id.title);
        bottomTabLayout=(LinearLayout)findViewById(R.id.ll_bottom_tab);
        contentLayout=(LinearLayout)findViewById(R.id.content_layout);

        homeFragment=new HomeFragment();
        doctorSearchFragment=new DoctorSearchFragment();
        newsListFragment=new NewsListFragment();
        memberMainFragment=new MemberMainFragment();

        homeBarButton=new BottomBarButton(this.getApplicationContext(), "home", R.drawable.bar_home, R.drawable.bar_home_active, "首页", homeFragment );
        newsBarButton=new BottomBarButton(this.getApplicationContext(), "news", R.drawable.bar_news, R.drawable.bar_news_active, "新闻", newsListFragment);
        doctorSearchBarButton=new BottomBarButton(this.getApplicationContext(), "doctor", R.drawable.bar_doctor, R.drawable.bar_doctor_active, "找医生", doctorSearchFragment);
        memberMainBarButton=new BottomBarButton(this.getApplicationContext(), "member", R.drawable.bar_member,R.drawable.bar_member_active,  "个人中心", memberMainFragment);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View view) {
        DoctorLoader loader = null;
        DoctorObj obj = null;
        Intent intent2 = null;
        int id = view.getId();
        switch (id)
        {
            case R.id.lvCharDoctor:
                intent2 = new Intent(this, DoctorListActivity.class);
                intent2.putExtra("name","图文咨询");
                intent2.putExtra("search", " enable_charchat='Y' ");
                this.startActivity(intent2);
                break;

            case R.id.lvTaiwanDoctor:
                intent2 = new Intent(this, DoctorListActivity.class);
                intent2.putExtra("name","海外医生");
                intent2.putExtra("search", " is_taiwan='Y' ");
                this.startActivity(intent2);
                break;
            case R.id.lvVideoDoctor:
                intent2 = new Intent(this, DoctorListActivity.class);
                intent2.putExtra("name","视频会诊");
                intent2.putExtra("search", " enable_videochat='Y' ");
                this.startActivity(intent2);
                break;
            case R.id.doctor:
                buttonBarClick(doctorSearchBarButton);
                break;
            case R.id.pregnancy:
            case R.id.earlierStudy:
            case R.id.askForHealth:
                newsListFragment.SetCategory("0");
                buttonBarClick(newsBarButton);
                break;
            default:
                break;
        }

        for(BottomBarButton barButton:lstBottomBar){
            if(view == barButton.GetEnteryLayout()){
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
            addOrShowFragment(getFragmentManager().beginTransaction(), barButton.GetFragment());
        }
    }
    public void InitBottomBar(){
        lstBottomBar=new ArrayList<BottomBarButton>();
        lstBottomBar.add(homeBarButton);
        lstBottomBar.add(newsBarButton);
        lstBottomBar.add(doctorSearchBarButton);
        lstBottomBar.add(memberMainBarButton);

        BottomBarButton.CreateEnteryBottomBar(bottomTabLayout, lstBottomBar, this);
        this.onClick(lstBottomBar.get(0).GetEnteryLayout());

    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {

        if(currentFragment==null){
            if(!fragment.isAdded()) {
                transaction .add(R.id.content_layout, fragment).commit();
            }
            if(fragment instanceof IMyFragment){
                IMyFragment myFragment=(IMyFragment)fragment;
                titleTextView.setText(myFragment.getTitle());
                titleTextView.getPaint().setFakeBoldText(true);
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
            titleTextView.getPaint().setFakeBoldText(true);
        }
        currentFragment = fragment;
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void SetToHome() {
        buttonBarClick(homeBarButton);
    }






    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        String picturePath;
        // 拍照
        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = resolver.query(selectedImage, filePathColumn, null, null, null);
            int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            picturePath = cursor.getString(colunm_index);
            cursor.close();
            if(picturePath!=null && !picturePath.isEmpty()) {
                uploadFile(picturePath,requestCode);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this,"i am resun",Toast.LENGTH_LONG).show();
        SetCurrentActivity();
    }
    public void SetCurrentActivity(){
        StaticVar.CurrentActivity=this;
    }
    private void uploadFile(final String path,int fileType)
    {
        //获取上传文件的路径

        //判断上次路径是否为空
        if (path.isEmpty()) {

        } else {
            //异步的客户端对象
            AsyncHttpClient client = new AsyncHttpClient();
            //指定url路径
            String url = StaticVar.UPLOADFILEURL4Member;
            //封装文件上传的参数
            RequestParams params = new RequestParams();
            //根据路径创建文件
            File file = new File(path);
            try {
                //放入文件
                params.put("uploadfile", file);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("文件不存在----------");
            }
            //图片上传
            if(fileType==1) {
                //执行post请求
                client.post(url, params, new TextHttpResponseHandler() {

                    @Override
                    public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, String result) {
                        if (i == 200) {
                            String[] arrResult = result.split("\\|");
                            if (arrResult != null && arrResult.length == 3) {
                                String filename = arrResult[2];
                                String url=StaticVar.ImageFolderURL+"member/"+filename;
                                UpdatePhoto(filename);
                                String cacheurl= UrlImageLoader.GetImageCacheFileName(url);
                                ToolsUtil.copyFile(path, cacheurl);
                                memberMainFragment.LoadMember(url);

                            }
                        }
                    }

                    @Override
                    public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String result, Throwable throwable) {

                    }

                });
            }
        }

    }

    private void UpdatePhoto(String FileName) {
       updateloader=new MemberUpdateLoader(this.getApplicationContext(),StaticVar.Member.getId(),"Photo",FileName);

        new Thread(){
            public void run()
            {
                try {
                    updateloader.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void RefreshMember() {
        memberMainFragment.LoadMember("");
    }

    @Override
    public boolean PopupNotice() {
        return true;
    }

    @Override
    public Activity GetMyActivity() {
        return this;
    }


}
