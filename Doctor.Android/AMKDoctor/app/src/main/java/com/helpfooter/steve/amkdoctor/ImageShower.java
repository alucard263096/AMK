package com.helpfooter.steve.amkdoctor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;


public class ImageShower extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageshower);

		final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		ImageView image=(ImageView)findViewById(R.id.bigImage);
		/*UrlImageLoader imgLoad = new UrlImageLoader(image, url);
		imgLoad.start();*/
		Bitmap bitmap=UrlImageLoader.GetBitmap(url);
		image.setImageBitmap(bitmap);

		// �����رպ�dialog
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		}, 1000 * 2);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}

}
