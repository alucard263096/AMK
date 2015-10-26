package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.Interfaces.ISelectObj;

import java.util.ArrayList;


public class MemberPhotoUploadActivity extends Activity implements View.OnClickListener {
    GridLayout photolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_photo_upload);

        ((TextView)findViewById(R.id.btnOK)).setOnClickListener(this);
        ((TextView)findViewById(R.id.btnCancel)).setOnClickListener(this);
        ((TextView)findViewById(R.id.txtPhotoSelect)).setOnClickListener(this);

        photolist= ((GridLayout)findViewById(R.id.photolist));



    }

    ArrayList<String> lst=new ArrayList<String>();


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnOK:

                Intent intent = new Intent();
                //intent.putExtra("return", currentSelect.SelectedValue());
                //intent.putExtra("returnName", currentSelect.DisplayName());
                setResult(RESULT_OK, intent);
                this.finish();
                return;
            case R.id.btnCancel:
                this.finish();
                return;
            case R.id.txtPhotoSelect:
                try
                {
                    Intent intentimg = new Intent(Intent.ACTION_GET_CONTENT);
                    intentimg.setType("image/*");
                    startActivityForResult(Intent.createChooser(intentimg,"选择病历图片"), 0);
                } catch (ActivityNotFoundException e) {

                }
            default:
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        String picturePath;
        // 拍照
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = resolver.query(selectedImage, filePathColumn, null, null, null);
            int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            picturePath = cursor.getString(colunm_index);
            cursor.close();
            if(picturePath!=null && !picturePath.isEmpty()) {
                //this.uploadFile(picturePath,requestCode);

                lst.add(picturePath);

                ImageView imageView=new ImageView(this);
                GridLayout.LayoutParams params=new GridLayout.LayoutParams();
                imageView.setLayoutParams(params);
                params.setMargins(20, 20, 20, 20);
                params.width=100;
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
                imageView.setAdjustViewBounds(true);
                Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
                imageView.setImageBitmap(bitmap);

                photolist.addView(imageView);
            }
        }


    }


}
