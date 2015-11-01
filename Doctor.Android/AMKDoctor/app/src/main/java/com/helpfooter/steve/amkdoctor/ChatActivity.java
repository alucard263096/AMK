package com.helpfooter.steve.amkdoctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amkdoctor.CustomControlView.BookListLoadView;
import com.helpfooter.steve.amkdoctor.CustomControlView.ChatListLoadView;
import com.helpfooter.steve.amkdoctor.CustomObject.BottomBarButton;
import com.helpfooter.steve.amkdoctor.DAO.BookerDao;
import com.helpfooter.steve.amkdoctor.DAO.MessageDao;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amkdoctor.Extents.PercentLayout.PercentLinearLayout;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.BannerLoader;
import com.helpfooter.steve.amkdoctor.Loader.BookerLoader;
import com.helpfooter.steve.amkdoctor.Loader.ChatEndLoader;
import com.helpfooter.steve.amkdoctor.Loader.ChatLoader;
import com.helpfooter.steve.amkdoctor.Loader.ChatUpdateLoader;
import com.helpfooter.steve.amkdoctor.Loader.MessageLoader;
import com.helpfooter.steve.amkdoctor.Utils.ChatMsgEntity;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import org.apache.http.Header;
import java.util.ArrayList;

public class ChatActivity extends Activity implements View.OnClickListener,IWebLoaderCallBack {

    private Button mBtnSendtxt;// 发送文本
    private Button mBtnSendpic;// 发送图片
    private Button mBtnSendfile;// 发送文件
    private ImageView mBtnBack;// 返回btn
    private EditText mEditTextContent;
    private TextView mTextViewRecevier; //聊天对象
    private TextView mTextEnd;//结束聊天
    private ScrollView mScrollView;
    private String context; //聊天内容
    private int order_id;
    public Activity mActivity;
    private static int IMAGE_CODE = 1;
    private static int FILE_CODE = 2;
    private static String SENDERTYPE = "D"; //消息发送方
    private ChatUpdateLoader loader=null;
    private ChatEndLoader endloader=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        setContentView(R.layout.activity_chat);
        mActivity=this;
        Intent intent = getIntent();
        order_id = Integer.parseInt(intent.getStringExtra("orderId"));
       initUI(order_id);
        InitData();
    }



    private void InitData() {



    }
    /**
     * 初始化UI
     */
    private void initUI(int orderid) {
        mBtnSendtxt =(Button)findViewById(R.id.btn_sendTxt);
        mBtnSendpic =(Button)findViewById(R.id.btn_sendPic);
        mBtnSendfile =(Button)findViewById(R.id.btn_sendFile);
        mBtnBack =(ImageView)findViewById(R.id.btn_back);
        mBtnSendtxt.setOnClickListener(this);
        mBtnSendpic.setOnClickListener(this);
        mBtnSendfile.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mEditTextContent=(EditText)findViewById(R.id.et_sendmessage);
        mTextViewRecevier=(TextView)findViewById(R.id.txt_Receiver);
        mTextEnd=(TextView)findViewById(R.id.txt_End);
        mTextEnd.setOnClickListener(this);
        DoctorObj doc = StaticVar.Doctor;
        mTextViewRecevier.setText(doc.getName());

        ChatListLoadView chatListLoadView=new ChatListLoadView(this,(PercentLinearLayout)findViewById(R.id.message_chat_list),orderid,"D");
        chatListLoadView.LoadList();
    }


    @Override
    public void onClick(View v) {
        String mobile;
        switch (v.getId()) {
            case R.id.btn_back:
                this.finish();
                return;
            case R.id.btn_sendTxt:
                String strContent = mEditTextContent.getEditableText().toString();
                SendMessage(StaticVar.TxtType, strContent);
                //新开一线程更新数据库

                mEditTextContent.setText("");
                mTextViewRecevier.setFocusableInTouchMode(true);
                break;
            case R.id.btn_sendPic:
                SendFile("image/*",IMAGE_CODE);
                mTextViewRecevier.setFocusableInTouchMode(true);
                break;
            case R.id.btn_sendFile:
                SendFile("*/*",FILE_CODE);
                mTextViewRecevier.setFocusableInTouchMode(true);
                break;
            case R.id.txt_End:
                endChatDialog();


                break;


        }
    }

    private void endChatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("你希望结束聊天吗 ?")
                .setCancelable(false)
                .setPositiveButton("是",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                EndChat();
                            }
                        })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
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
        loader=new ChatUpdateLoader(mActivity,order_id,SENDERTYPE,txtType,strContent);
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

    private void EndChat() {
        endloader=new ChatEndLoader(mActivity,order_id);
        endloader.setCallBack(this);
        new Thread(){
            public void run()
            {
                try {
                    endloader.run();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void CallBack(ArrayList<AbstractObj> lstObjs) {

        this.finish();

       /* if (lstObj.size() > 0) {
            onloadAllHandler.sendEmptyMessage(0);
        }*/
    }

    private void uploadFile(String path,int fileType)
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
                 if(fileType==1) {
                     //执行post请求
                     client.post(url, params, new TextHttpResponseHandler() {

                         @Override
                         public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, String result) {
                             if (i == 200) {
                                 String[] arrResult = result.split("\\|");
                                 if (arrResult != null && arrResult.length == 3) {
                                     String filename = arrResult[2];
                                     SendMessage(StaticVar.IMGType, filename);
                                 }
                             }
                         }

                         @Override
                         public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String result, Throwable throwable) {

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        String picturePath;
        // 拍照
       if ((requestCode == IMAGE_CODE || requestCode == FILE_CODE) && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = resolver.query(selectedImage, filePathColumn, null, null, null);
           int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            picturePath = cursor.getString(colunm_index);
            cursor.close();
            if(picturePath!=null && !picturePath.isEmpty()) {
                this.uploadFile(picturePath,requestCode);
            }
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
}
