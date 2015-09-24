package com.helpfooter.steve.amkdoctor;



import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.helpfooter.steve.amkdoctor.DAO.DoctorDao;
import com.helpfooter.steve.amkdoctor.DAO.BookerDao;
import com.helpfooter.steve.amkdoctor.DataObjs.DoctorObj;
import com.helpfooter.steve.amkdoctor.DataObjs.BookerObj;
import com.helpfooter.steve.amkdoctor.R;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;


public class VideoChatActivity extends Activity implements AnyChatBaseEvent {
	private final int UPDATEVIDEOBITDELAYMILLIS = 200; //监听音频视频的码率的间隔刷新时间（毫秒）
	private final int LOCALVIDEOAUTOROTATION = 1; // 本地视频自动旋转控制
	int userID;
	boolean bOnPaused = false;
	private boolean bSelfVideoOpened = false; // 本地视频是否已打开
	private boolean bOtherVideoOpened = false; // 对方视频是否已打开
	private Boolean mFirstGetVideoBitrate = false; //"第一次"获得视频码率的标致
	private Boolean mFirstGetAudioBitrate = false; //"第一次"获得音频码率的标致

	private SurfaceView mOtherView;
	private SurfaceView mMyView;
	private ImageButton mImgSwitchVideo;
	private Button mEndCallBtn;
	private ImageButton mBtnCameraCtrl; // 控制视频的按钮
	private ImageButton mBtnSpeakCtrl; // 控制音频的按钮
    private int myUserId;
	public AnyChatCoreSDK anychatSDK;
	BookerObj booker;
	DoctorObj doctor;
	int doctor_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_activity);

		Intent intent = getIntent();
		int order_id = Integer.parseInt(intent.getStringExtra("orderId"));
		BookerDao bookerDao=new BookerDao(this);
		booker=(BookerObj)bookerDao.getObj(order_id);
		 doctor_id=Integer.parseInt(intent.getStringExtra("docId"));
		DoctorDao doctorDao=new DoctorDao(this);
		doctor=(DoctorObj)doctorDao.getObj(doctor_id);



			InitSDK();
			InitLogin();
			InitLayout();

			// 如果视频流过来了，则把背景设置成透明的
			handler.postDelayed(runnable, UPDATEVIDEOBITDELAYMILLIS);

	}

	private void InitLogin() {
		anychatSDK.Connect(StaticVar.VideoChatServerIp, StaticVar.VideoChatServerPort);
		//anychatSDK.Login(String.valueOf(doctor_id), "");
	}

	private void InitSDK() {

		anychatSDK = new AnyChatCoreSDK();
		anychatSDK.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,
				LOCALVIDEOAUTOROTATION);
		anychatSDK.SetBaseEvent(this);
		anychatSDK.mSensorHelper.InitSensor(this);
		AnyChatCoreSDK.mCameraHelper.SetContext(this);

	}

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	private void InitLayout() {
		//this.setTitle("在" + doctor.getName() + "的诊室中");
		mMyView = (SurfaceView) findViewById(R.id.surface_local);
		mOtherView = (SurfaceView) findViewById(R.id.surface_remote);
		mImgSwitchVideo = (ImageButton) findViewById(R.id.ImgSwichVideo);
		mEndCallBtn = (Button) findViewById(R.id.endCall);
		mBtnSpeakCtrl = (ImageButton) findViewById(R.id.btn_speakControl);
		mBtnCameraCtrl = (ImageButton) findViewById(R.id.btn_cameraControl);
		mBtnSpeakCtrl.setOnClickListener(onClickListener);
		mBtnCameraCtrl.setOnClickListener(onClickListener);
		mImgSwitchVideo.setOnClickListener(onClickListener);
		mEndCallBtn.setOnClickListener(onClickListener);
		// 如果是采用Java视频采集，则需要设置Surface的CallBack
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
			mMyView.getHolder().addCallback(AnyChatCoreSDK.mCameraHelper);
		}

		// 如果是采用Java视频显示，则需要设置Surface的CallBack
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
			int index = anychatSDK.mVideoHelper.bindVideo(mOtherView
					.getHolder());
			anychatSDK.mVideoHelper.SetVideoUser(index, userID);
		}

		mMyView.setZOrderOnTop(true);

		anychatSDK.UserCameraControl(userID, 1);
		anychatSDK.UserSpeakControl(userID, 1);

		// 判断是否显示本地摄像头切换图标
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
			if (AnyChatCoreSDK.mCameraHelper.GetCameraNumber() > 1) {
				// 默认打开前置摄像头
				AnyChatCoreSDK.mCameraHelper
						.SelectVideoCapture(AnyChatCoreSDK.mCameraHelper.CAMERA_FACING_FRONT);
			}
		} else {
			String[] strVideoCaptures = anychatSDK.EnumVideoCapture();
			if (strVideoCaptures != null && strVideoCaptures.length > 1) {
				// 默认打开前置摄像头
				for (int i = 0; i < strVideoCaptures.length; i++) {
					String strDevices = strVideoCaptures[i];
					if (strDevices.indexOf("Front") >= 0) {
						anychatSDK.SelectVideoCapture(strDevices);
						break;
					}
				}
			}
		}

		// 根据屏幕方向改变本地surfaceview的宽高比
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			adjustLocalVideo(true);
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			adjustLocalVideo(false);
		}

		anychatSDK.UserCameraControl(-1, 1);// -1表示对本地视频进行控制，打开本地视频
		anychatSDK.UserSpeakControl(-1, 1);// -1表示对本地音频进行控制，打开本地音频

	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {
				int videoBitrate = anychatSDK.QueryUserStateInt(userID,
						AnyChatDefine.BRAC_USERSTATE_VIDEOBITRATE);
				int audioBitrate = anychatSDK.QueryUserStateInt(userID,
						AnyChatDefine.BRAC_USERSTATE_AUDIOBITRATE);
				if (videoBitrate > 0)
				{
					//handler.removeCallbacks(runnable);
					mFirstGetVideoBitrate = true;
					mOtherView.setBackgroundColor(Color.TRANSPARENT);
				}

				if(audioBitrate > 0){
					mFirstGetAudioBitrate = true;
				}

				if (mFirstGetVideoBitrate)
				{
					if (videoBitrate <= 0){
						Toast.makeText(VideoChatActivity.this, "对方视频中断了!", Toast.LENGTH_SHORT).show();
						// 重置下，如果对方退出了，有进去了的情况
						mFirstGetVideoBitrate = false;
					}
				}

				if (mFirstGetAudioBitrate){
					if (audioBitrate <= 0){
						Toast.makeText(VideoChatActivity.this, "对方音频中断了!", Toast.LENGTH_SHORT).show();
						// 重置下，如果对方退出了，有进去了的情况
						mFirstGetAudioBitrate = false;
					}
				}

				handler.postDelayed(runnable, UPDATEVIDEOBITDELAYMILLIS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case (R.id.ImgSwichVideo): {

					// 如果是采用Java视频采集，则在Java层进行摄像头切换
					if (AnyChatCoreSDK
							.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
						AnyChatCoreSDK.mCameraHelper.SwitchCamera();
						return;
					}

					String strVideoCaptures[] = anychatSDK.EnumVideoCapture();
					String temp = anychatSDK.GetCurVideoCapture();
					for (int i = 0; i < strVideoCaptures.length; i++) {
						if (!temp.equals(strVideoCaptures[i])) {
							anychatSDK.UserCameraControl(-1, 0);
							bSelfVideoOpened = false;
							anychatSDK.SelectVideoCapture(strVideoCaptures[i]);
							anychatSDK.UserCameraControl(-1, 1);
							break;
						}
					}
				}
				break;
				case (R.id.endCall): {
					exitVideoDialog();
				}
				case R.id.btn_speakControl:
					if ((anychatSDK.GetSpeakState(-1) == 1)) {
						mBtnSpeakCtrl.setImageResource(R.drawable.speak_off);
						anychatSDK.UserSpeakControl(-1, 0);
					} else {
						mBtnSpeakCtrl.setImageResource(R.drawable.speak_on);
						anychatSDK.UserSpeakControl(-1, 1);
					}

					break;
				case R.id.btn_cameraControl:
					if ((anychatSDK.GetCameraState(-1) == 2)) {
						mBtnCameraCtrl.setImageResource(R.drawable.camera_off);
						anychatSDK.UserCameraControl(-1, 0);
					} else {
						mBtnCameraCtrl.setImageResource(R.drawable.camera_on);
						anychatSDK.UserCameraControl(-1, 1);
					}
					break;
				default:
					break;
			}
		}
	};

	private void refreshAV() {
		anychatSDK.UserCameraControl(userID, 1);
		anychatSDK.UserSpeakControl(userID, 1);
		anychatSDK.UserCameraControl(-1, 1);
		anychatSDK.UserSpeakControl(-1, 1);
		mBtnSpeakCtrl.setImageResource(R.drawable.speak_on);
		mBtnCameraCtrl.setImageResource(R.drawable.camera_on);
		bOtherVideoOpened = false;
		bSelfVideoOpened = false;
	}

	private void exitVideoDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("你希望退出吗 ?")
				.setCancelable(false)
				.setPositiveButton("是",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								destroyCurActivity();
							}
						})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	private void destroyCurActivity() {
		onPause();
		onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitVideoDialog();
		}

		return super.onKeyDown(keyCode, event);
	}

	protected void onRestart() {
		super.onRestart();
		// 如果是采用Java视频显示，则需要设置Surface的CallBack
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
			int index = anychatSDK.mVideoHelper.bindVideo(mOtherView
					.getHolder());
			anychatSDK.mVideoHelper.SetVideoUser(index, userID);
		}

		refreshAV();
		bOnPaused = false;
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
		bOnPaused = true;
		anychatSDK.UserCameraControl(userID, 0);
		anychatSDK.UserSpeakControl(userID, 0);
		anychatSDK.UserCameraControl(-1, 0);
		anychatSDK.UserSpeakControl(-1, 0);
	}

	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(runnable);
		anychatSDK.mSensorHelper.DestroySensor();
		anychatSDK.LeaveRoom(-1);
		anychatSDK.Logout();
		finish();
	}

	public void adjustLocalVideo(boolean bLandScape) {
		float width;
		float height = 0;
		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		width = (float) dMetrics.widthPixels / 4;
		LinearLayout layoutLocal = (LinearLayout) this
				.findViewById(R.id.frame_local_area);
		FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) layoutLocal
				.getLayoutParams();
		if (bLandScape) {

			if (AnyChatCoreSDK
					.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL) != 0)
				height = width
						* AnyChatCoreSDK
						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL)
						/ AnyChatCoreSDK
						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL)
						+ 5;
			else
				height = (float) 3 / 4 * width + 5;
		} else {

			if (AnyChatCoreSDK
					.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL) != 0)
				height = width
						* AnyChatCoreSDK
						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL)
						/ AnyChatCoreSDK
						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL)
						+ 5;
			else
				height = (float) 4 / 3 * width + 5;
		}
		layoutParams.width = (int) width;
		layoutParams.height = (int) height;
		layoutLocal.setLayoutParams(layoutParams);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			adjustLocalVideo(true);
			AnyChatCoreSDK.mCameraHelper.setCameraDisplayOrientation();
		} else {
			adjustLocalVideo(false);
			AnyChatCoreSDK.mCameraHelper.setCameraDisplayOrientation();
		}

	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (!bSuccess) {
			Toast.makeText(this, "连接服务器失败，自动重连，请稍后...", Toast.LENGTH_LONG).show();
		}
		anychatSDK.Login(String.valueOf(doctor_id),"");
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		if (dwErrorCode == 0) {
			myUserId=dwUserId;
			anychatSDK.EnterRoom(booker.getId(), "");
		} else {
			Toast.makeText(this, "登录失败，errorCode：", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		int[] userIDArray = anychatSDK.GetOnlineUser();
		if(userIDArray.length>0){
			userID=userIDArray[0];
			if(myUserId==userID){
				Log.i("myUserId==userID", "yes");
			}


			int index = anychatSDK.mVideoHelper.bindVideo(mOtherView
					.getHolder());
			anychatSDK.mVideoHelper.SetVideoUser(index, userID);

			anychatSDK.UserCameraControl(userID, 1);
			anychatSDK.UserSpeakControl(userID, 1);
		}
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		int[] userIDArray = anychatSDK.GetOnlineUser();
		if(userIDArray.length>0){
			userID=userIDArray[0];
			if(myUserId==userID){
				Log.i("myUserId==userID","yes");
			}


			int index = anychatSDK.mVideoHelper.bindVideo(mOtherView
					.getHolder());
			anychatSDK.mVideoHelper.SetVideoUser(index, userID);

			anychatSDK.UserCameraControl(userID, 1);
			anychatSDK.UserSpeakControl(userID, 1);
		}
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		if (!bEnter) {
			if (dwUserId == userID) {
				Toast.makeText(this, "对方已离开！", Toast.LENGTH_SHORT).show();
				userID = 0;
				anychatSDK.UserCameraControl(dwUserId, 0);
				anychatSDK.UserSpeakControl(dwUserId, 0);
				bOtherVideoOpened = false;
			}
		} else {
			if (userID != 0)
				return;

			int index = anychatSDK.mVideoHelper.bindVideo(mOtherView
					.getHolder());
			anychatSDK.mVideoHelper.SetVideoUser(index, dwUserId);

			anychatSDK.UserCameraControl(dwUserId, 1);
			anychatSDK.UserSpeakControl(dwUserId, 1);
			userID = dwUserId;
		}
	}


	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// 网络连接断开之后，上层需要主动关闭已经打开的音视频设备
		if (bOtherVideoOpened) {
			anychatSDK.UserCameraControl(userID, 0);
			anychatSDK.UserSpeakControl(userID, 0);
			bOtherVideoOpened = false;
		}
		if (bSelfVideoOpened) {
			anychatSDK.UserCameraControl(-1, 0);
			anychatSDK.UserSpeakControl(-1, 0);
			bSelfVideoOpened = false;
		}

		// 销毁当前界面
		destroyCurActivity();
		this.finish();
	}
}
