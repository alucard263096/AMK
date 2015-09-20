package com.bairuitech.callcenter;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.bussinesscenter.BussinessCenter;
import com.helpfooter.steve.amkdoctor.DataObjs.AbstractObj;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.Interfaces.IWebLoaderCallBack;
import com.helpfooter.steve.amkdoctor.Loader.DoctorLoader;
import com.helpfooter.steve.amkdoctor.MainActivity;
import com.helpfooter.steve.amkdoctor.R;

import com.bairuitech.util.*;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements AnyChatBaseEvent,
		LoaderManager.LoaderCallbacks<Cursor>,
		IWebLoaderCallBack,
		 OnClickListener {
	private Button configBtn;
	private Button loginBtn;
	private CheckBox mCheckRemember;
	private ConfigEntity configEntity;
	private EditText mEditAccount;
	private EditText mEditPassWord;
	private ProgressDialog mProgressLogin;
	private Dialog dialog;
	private AnyChatCoreSDK anychat;
	private boolean bNeedRelease = false;
	private final String MAK = "innoview";
	private String threadstatus;


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
				BaseMethod.showToast(
						"用户不存在。", LoginActivity.this);
				//Toast.makeText(LoginActivity.this, "用户不存在。", Toast.LENGTH_LONG).show();
			}else  if (!doj.getPassWord().equals(ToolsUtil.Encryption(strPassWord))) {
				BaseMethod.showToast(
						"密码错误。", LoginActivity.this);

			}
			else {

				if (mCheckRemember.isChecked()) {
					configEntity.IsSaveNameAndPw = true;
					configEntity.name = strUserName;
					configEntity.password=strPassWord;
				} else {
					configEntity.IsSaveNameAndPw = false;
				}
				ConfigService.SaveConfig(LoginActivity.this, configEntity);
				if (mEditAccount.getText().length() == 0) {
					BaseMethod.showToast(getString(R.string.str_account_input_hint), LoginActivity.this);
					return;
				}
				anychat.Connect(configEntity.ip, configEntity.port);
				anychat.Login(strUserName, "123");

				loginBtn.setClickable(false);
				mProgressLogin.show();
			}




		}
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSdk();
		intParams();
		initView();
		initLoginProgress();

	}

	protected void intParams() {
		configEntity = ConfigService.LoadConfig(this);
		BussinessCenter.getBussinessCenter();
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
		int tag=intent.getIntExtra("INTENT", BaseConst.AGAIGN_LOGIN);
		if(tag==BaseConst.AGAIGN_LOGIN)
		{
			if(anychat!=null)
			{
				anychat.Logout();
				anychat.SetBaseEvent(this);
			}
		}
		else if(tag==BaseConst.APP_EXIT)
		{
			this.finish();
		}
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

	private void initSdk() {
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
			anychat.SetBaseEvent(this);
			anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
			bNeedRelease = true;
		}
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
		configBtn = (Button) findViewById(R.id.btn_setting);
		configBtn.setOnClickListener(this);
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
		if (bNeedRelease) {
			anychat.Logout();
			anychat.Release();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		BussinessCenter.getBussinessCenter().realseData();
	}

	protected void onResume() {
		super.onResume();
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (!bSuccess) {
			BaseMethod.showToast(getString(R.string.server_connect_error), this);
			mProgressLogin.dismiss();
		} else {
		}
	}

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// Toast.makeText(this, "���ӹرգ�error��" + dwErrorCode,
		// Toast.LENGTH_SHORT)
		// .show();
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
			if (dwErrorCode == 0) {
			BussinessCenter.selfUserId = dwUserId;
			BussinessCenter.selfUserName=mEditAccount.getText()
					.toString();

					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					this.startActivity(intent);


		} else if (dwErrorCode == 200) {
			BaseMethod.showToast(
					getString(R.string.str_lggin_failed), this);
		}
		mProgressLogin.dismiss();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			Login();






			break;
		case R.id.btn_setting:
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_CONFIG, configEntity,this);
			dialog.show();
			break;
		default:
			break;
		}
	}

}