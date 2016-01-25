package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.CustomControlView.ChatListLoadView;
import com.helpfooter.steve.amklovebaby.CustomControlView.ProcessImageView;
import com.helpfooter.steve.amklovebaby.CustomObject.BottomBarButton;
import com.helpfooter.steve.amklovebaby.CustomObject.MyActivity;
import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DAO.OrderDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorObj;
import com.helpfooter.steve.amklovebaby.DataObjs.MemberObj;
import com.helpfooter.steve.amklovebaby.DataObjs.OrderObj;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amklovebaby.Loader.BannerLoader;
import com.helpfooter.steve.amklovebaby.Loader.ChatLoader;
import com.helpfooter.steve.amklovebaby.Loader.ChatUpdateLoader;
import com.helpfooter.steve.amklovebaby.Utils.ChatMsgEntity;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import org.apache.http.Header;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ChatActivity extends MyActivity implements View.OnClickListener {

    private Button mBtnSendtxt;// 发送文本
    private ImageView mBtnSendpic;// 发送图片
    private ImageView mBtnSendfile;// 发送文件
    private ImageView mBtnTakePhoto;// 拍照
    private ImageView mBtnBack;// 返回btn
    private EditText mEditTextContent;
    private TextView mTextViewRecevier; //聊天对象
    private ScrollView mScrollView;
    private String context; //聊天内容
    private String Type; //聊天类型
    private int order_id;
    private DoctorObj doctor;
    public Activity mActivity;
    private static int IMAGE_CODE = 1;
    private static int FILE_CODE = 2;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static String SENDERTYPE = "C"; //消息发送方
    private ChatUpdateLoader loader=null;
    ChatListLoadView chatListLoadView;
    private String temppath;
    private String capturePath;
    private ProcessImageView piv;
    LinearLayout sublayout = null;
    OrderObj order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        setContentView(R.layout.activity_chat);
        mActivity=this;


        InitData();
       initUI();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {//给主线程设置一个处理运行时异常的handler

            @Override
            public void uncaughtException(Thread thread, final Throwable ex) {

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);

                StringBuilder sb = new StringBuilder();

                sb.append("Version code is ");
                // sb.append(Build.VERSION.SDK_INT + "\n");//设备的Android版本号
                sb.append("Model is ");
                // sb.append(Build.MODEL + "\n");//设备型号
                sb.append(sw.toString());

               /* Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:csdn@csdn.com"));//发送邮件异常到csdn@csdn.com邮箱
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "bug report");//邮件主题
                sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());//堆栈信息
                startActivity(sendIntent);
                finish();*/
            }
        });
    }



    private void InitData() {


        Intent intent = getIntent();
        order_id = Integer.parseInt(intent.getStringExtra("orderId"));
        int doctor_id = Integer.parseInt(intent.getStringExtra("tag"));
        DoctorDao doctorDao=new DoctorDao(this);
        doctor=(DoctorObj)doctorDao.getObj(doctor_id);
        OrderDao orderDao=new OrderDao(this);
        order=(OrderObj)orderDao.getObj(order_id);



    }
    /**
     * 初始化UI
     */

    boolean hasNewView=false;

    private void initUI() {
        mBtnSendtxt =(Button)findViewById(R.id.btn_sendTxt);
        mBtnSendpic =(ImageView)findViewById(R.id.btn_sendPic);
        mBtnSendfile =(ImageView)findViewById(R.id.btn_sendFile);
        mBtnTakePhoto=(ImageView)findViewById(R.id.btnTakePic);
        mBtnBack =(ImageView)findViewById(R.id.btnBack);
        mBtnSendtxt.setOnClickListener(this);
        mBtnSendpic.setOnClickListener(this);
        mBtnSendfile.setOnClickListener(this);
        mBtnTakePhoto.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mEditTextContent=(EditText)findViewById(R.id.et_sendmessage);
        mTextViewRecevier=(TextView)findViewById(R.id.txt_Receiver);
        mScrollView =(ScrollView)findViewById(R.id.chatScroll);
        mTextViewRecevier.setText(doctor.getName());

        if(hasNewView==false) {
            chatListLoadView = new ChatListLoadView(this, (PercentLinearLayout) findViewById(R.id.message_chat_list), order_id, doctor.getId(), "C");
            chatListLoadView.LoadContent();
        }

        if(order.getStatus().equals("F")){
            mBtnSendtxt.setEnabled(false);
            mBtnSendtxt.setVisibility(View.GONE);
            mBtnSendpic.setEnabled(false);
            mBtnSendpic.setVisibility(View.GONE);
            mBtnSendfile.setEnabled(false);
            mBtnSendfile.setVisibility(View.GONE);
            mBtnTakePhoto.setEnabled(false);
            mBtnTakePhoto.setVisibility(View.GONE);
            mEditTextContent.setEnabled(false);
            mEditTextContent.setVisibility(View.GONE);

            ((RelativeLayout)findViewById(R.id.rl_bottom)).setVisibility(View.GONE);

        }

    }


    @Override
    public void onClick(View v) {
        String mobile;
        switch (v.getId()) {
            case R.id.btnBack:
                chatListLoadView.UnloadContent();
                this.finish();
                return;
            case R.id.btn_sendTxt:
                String strContent = mEditTextContent.getEditableText().toString();
                SendMessage(StaticVar.TxtType, strContent);
                //新开一线程更新数据库

                mEditTextContent.setText("");
                mTextViewRecevier.setFocusableInTouchMode(true);
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.btn_sendPic:
                SendFile("image/*", IMAGE_CODE);
                mTextViewRecevier.setFocusableInTouchMode(true);
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.btnTakePic:
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                    String out_file_path = "/sdcard/AMKChat/";
                    File dir = new File(out_file_path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    capturePath =out_file_path + System.currentTimeMillis() + ".jpg";
                    getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
                    getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(getImageByCamera, REQUEST_CODE_PICK_IMAGE);

                }
                else {
                    Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                }
                mTextViewRecevier.setFocusableInTouchMode(true);
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.btn_sendFile:
                SendFile("*/*",FILE_CODE);
                mTextViewRecevier.setFocusableInTouchMode(true);
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;

        }
    }


    private void SendFile(String filetype,int iCode) {
        try
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType(filetype);
            startActivityForResult(intent, iCode);
        } catch (ActivityNotFoundException e) {

        }
    }

    private void SendMessage(String txtType, String strContent) {

        context=strContent;
        Type = txtType;
        if(Type!=StaticVar.IMGType) {
            new Thread() {
                public void run() {
                    LinearLayout sublayout = null;
                    ChatMsgEntity obj = new ChatMsgEntity();
                    obj.setMsgType(false);
                    obj.setMessage(context);
                    obj.setContextType(Type);
                    sublayout = chatListLoadView.LoadChatListData(obj);
                    if (sublayout != null) {
                        sublayout.setTag(obj);
                        try {
                            chatListLoadView.mainlayout.addView(sublayout);
                        } catch (Exception ex) {

                            Log.i("ERROR", ex.toString());
                        }

                    }
                }
            }.start();
        }

        loader=new ChatUpdateLoader(mActivity,order_id,doctor.getId(),SENDERTYPE,txtType,strContent);
        if(strContent.isEmpty())
        {
            return;
        }
        new Thread(){
            public void run()
            {
                try {
                     loader.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public String getCharset() {
        return "UTF-8";
    }
    public static String getResponseString(byte[] stringBytes, String charset) {
        try {
            String e = stringBytes == null?null:new String(stringBytes, charset);
            return e != null && e.startsWith("\ufeff")?e.substring(1):e;
        } catch (UnsupportedEncodingException var3) {
            AsyncHttpClient.log.e("TextHttpRH", "Encoding response into string failed", var3);
            return null;
        }
    }
    private void uploadFile(final String path,int fileType)
    {
        //获取上传文件的路径

            //判断上次路径是否为空
             if (path.isEmpty()) {
                 Toast.makeText(this, "文件路径不能为空", Toast.LENGTH_SHORT).show();
             } else {
                 //异步的客户端对象
                 AsyncHttpClient client = new AsyncHttpClient();
                 //指定url路径
                 String url = StaticVar.UPLOADFILEURL;
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
                 if(fileType==1||fileType==0) {
                     //执行post请求
                     temppath=path;
                     buildLocalImgView();
                     client.post(url, params, new AsyncHttpResponseHandler() {


                         @Override
                         public void onProgress(long bytesWritten, long totalSize) {
                             // TODO Auto-generated method stub
                             super.onProgress(bytesWritten, totalSize);
                             int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                             // 上传进度显示
                             piv.setProgress(count);
                             Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
                         }

                         @Override
                         public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                             if (i == 200) {
                                 String result=getResponseString(bytes,getCharset());
                                 String[] arrResult = result.split("\\|");
                                 if (arrResult != null && arrResult.length == 3) {
                                     String filename = arrResult[2];

                                     String url = StaticVar.ImageFolderURL + "charchat/" + filename;
                                     String cacheurl = UrlImageLoader.GetImageCacheFileName(url);
                                     ToolsUtil.copyFile(path, cacheurl);
                                     piv.setProgress(0);
                                     removeSubMessage();
                                     SendMessage(StaticVar.IMGType, filename);

                                 }
                             }
                         }

                         @Override
                         public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

                         }

                         @Override
                         public void onRetry(int retryNo) {
                             // TODO Auto-generated method stub
                             super.onRetry(retryNo);
                             // 返回重试次数
                         }



                     });
                 }

                 //文件上传
                 if(fileType==2)
                 {
                     //执行post请求
                     client.post(url, params, new TextHttpResponseHandler() {

                         @Override
                         public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, String result) {
                             if (i == 200) {
                                 String[] arrResult = result.split("\\|");
                                 if (arrResult != null && arrResult.length == 3) {
                                     String filename = arrResult[2];
                                     SendMessage(StaticVar.DOCType, filename);
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

    private void buildLocalImgView()
    {
        Type = StaticVar.IMGType;
        new Thread(){
            public void run()
            {

                ChatMsgEntity obj = new ChatMsgEntity();
                obj.setMsgType(false);
                obj.setMessage(context);
                obj.setContextType(Type);
                sublayout = chatListLoadView.LoadChatListImgData(obj,temppath);
                PercentLinearLayout pL=(PercentLinearLayout)sublayout.getChildAt(1);
                piv=(ProcessImageView)pL.getChildAt(0);
                if (sublayout != null) {
                    sublayout.setTag(obj);
                    try {

                        addSubMessageHandler.sendEmptyMessage(0);

                    } catch (Exception ex) {

                        Log.i("ERROR", ex.toString());
                    }

                }
            }
        }.start();
    }

    private Handler addSubMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            addSubMessage();
        }
    };

    private void addSubMessage() {
        chatListLoadView.mainlayout.addView(sublayout);
    }

    private void removeSubMessage() {
        if(sublayout!=null) {
            chatListLoadView.mainlayout.removeView(sublayout);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        String picturePath=null;
        // 拍照
        if ((requestCode == IMAGE_CODE || requestCode == FILE_CODE) && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = resolver.query(selectedImage, filePathColumn, null, null, null);
            int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            picturePath = cursor.getString(colunm_index);
            cursor.close();
            if (picturePath != null && !picturePath.isEmpty()) {
                this.uploadFile(picturePath, requestCode);
            }
        }
        else if (requestCode == REQUEST_CODE_PICK_IMAGE) {
           /* Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    //spath :生成图片取个名字和路径包含类型
                    String dirName = "/sdcard/AMKChat/";
                    String fileName = System.currentTimeMillis() + ".jpg";
                    //本地存放路径
                    picturePath = dirName + fileName;
                    //文件网路路径
                    String urlDownloadAll = StaticVar.IMGCHATURL + fileName;
                    //判断本地存放目录是否存，不存在则新建文件夹
                    File f = new File(dirName);
                    if (!f.exists()) {
                        f.mkdir();
                    }
                    saveImage(photo, picturePath);
                }


            }
            else
            {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = resolver.query(uri, filePathColumn, null, null, null);
                int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                picturePath = cursor.getString(colunm_index);
                cursor.close();
            }*/
            if (capturePath != null && !capturePath.isEmpty()) {
                this.uploadFile(capturePath, requestCode);
            }
        }
    }

        public static void saveImage(Bitmap photo, String spath) {
            try {
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(spath, false));
                photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean PopupNotice(){
        return false;
    }
}
