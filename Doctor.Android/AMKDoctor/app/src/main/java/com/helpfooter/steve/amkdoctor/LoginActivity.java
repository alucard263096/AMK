package com.helpfooter.steve.amkdoctor;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.helpfooter.steve.amkdoctor.Common.VersionUpdateMgr;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.DoctorLoader;
import com.helpfooter.steve.amkdoctor.MainActivity;
import com.helpfooter.steve.amkdoctor.R;

import com.helpfooter.steve.amkdoctor.Utils.ConfigEntity;
import com.helpfooter.steve.amkdoctor.Utils.ConfigService;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements 	LoaderManager.LoaderCallbacks<Cursor>,
		IWebLoaderCallBack,
		 OnClickListener {
	private Button loginBtn;
	private CheckBox mCheckRemember;
	private ConfigEntity configEntity;
	private EditText mEditAccount;
	private EditText mEditPassWord;
	private ProgressDialog mProgressLogin;
	private String threadstatus;

    private VersionUpdateMgr versionUpdateMgr;
	private interface ProfileQuery {
		String[] PROJECTION = {
				ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
		};

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE +
						" = ?", new String[]{ContactsContract.CommonDataKinds.Email
				.CONTENT_ITEM_TYPE},

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

	}



	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}
	DoctorObj doj;
	@Override
	public void CallBack(ArrayList<AbstractObj> lstObjs) {
		if(threadstatus.equals("Login")) {

			if (lstObjs.size() >0) {
				doj = (DoctorObj) lstObjs.get(0);
			}
			else
			{
				doj=null;
			}
			FirstcodeHandler.sendEmptyMessage(0);

		}
	}

	private android.os.Handler FirstcodeHandler = new android.os.Handler() {
		@Override
		public void handleMessage(Message msg) {
			String strUserName = mEditAccount.getEditableText().toString();
			String strPassWord = mEditPassWord.getEditableText().toString();

			if(doj==null){

				Toast.makeText(LoginActivity.this, "用户不存在。", Toast.LENGTH_LONG).show();
			}else  if (!doj.getPassWord().equals(ToolsUtil.Encryption(strPassWord))) {

				Toast.makeText(LoginActivity.this, "密码错误。", Toast.LENGTH_LONG).show();
			}
			else {

				if (mCheckRemember.isChecked()) {
					configEntity.IsSaveNameAndPw = true;
					configEntity.name = strUserName;
					configEntity.password=strPassWord;
				} else {
					configEntity.IsSaveNameAndPw = false;
				}
				StaticVar.Doctor=doj;
				ConfigService.SaveConfig(LoginActivity.this, configEntity);

				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(intent);
				loginBtn.setClickable(false);
				mProgressLogin.show();


			}




		}
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intParams();
		initView();
		initLoginProgress();
		versionUpdateMgr=new VersionUpdateMgr(this);
		versionUpdateMgr.startCheckVersion();

	}

	protected void intParams() {
		configEntity = ConfigService.LoadConfig(this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		return super.onKeyDown(keyCode, event);
	}



	private void initView() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.setContentView(R.layout.login_layout);
		mEditAccount = (EditText) findViewById(R.id.edit_account);
		mEditPassWord=(EditText)findViewById((R.id.edit_pasword));
		mCheckRemember = (CheckBox) findViewById(R.id.check_issavepass);
		mCheckRemember.setTextColor(Color.BLACK);
		loginBtn = (Button) findViewById(R.id.btn_login);
		loginBtn.setOnClickListener(this);

		if (configEntity.IsSaveNameAndPw) {
			mCheckRemember.setChecked(true);
			if (configEntity.name != null)
				mEditAccount.setText(configEntity.name);
			if (configEntity.password != null)
				mEditPassWord.setText(configEntity.password);
		} else
			mCheckRemember.setChecked(false);
	}


	private void initLoginProgress() {
		mProgressLogin = new ProgressDialog(this);
		mProgressLogin.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				loginBtn.setClickable(true);
			}
		});
		mProgressLogin.setMessage(this.getString(R.string.login_progress));
	}

	private void Login() {
		String strUserName = mEditAccount.getEditableText().toString();
		String strPassWord = mEditPassWord.getEditableText().toString();
		DoctorLoader dl = new DoctorLoader(this.getApplicationContext(),strUserName);
		threadstatus="Login";
		dl.setCallBack(this);
		dl.start();

	}

	protected void onDestroy() {
		super.onDestroy();

	}

	protected void onResume() {
		super.onResume();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			Login();
			break;

		default:
			break;
		}
	}

}