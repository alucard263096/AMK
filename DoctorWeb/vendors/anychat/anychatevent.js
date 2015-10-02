// AnyChat for Web SDK

/********************************************
 *				事件回调部分				*
 *******************************************/
 
 // 异步消息通知，包括连接服务器、登录系统、进入房间等消息
function OnAnyChatNotifyMessage(dwNotifyMsg, wParam, lParam) {
	switch(dwNotifyMsg) {
		case WM_GV_CONNECT:			OnAnyChatConnect(wParam, lParam);			break;
		case WM_GV_LOGINSYSTEM:		OnAnyChatLoginSystem(wParam, lParam);		break;
		case WM_GV_ENTERROOM:		OnAnyChatEnterRoom(wParam, lParam);			break;
		case WM_GV_ONLINEUSER:		OnAnyChatRoomOnlineUser(wParam, lParam);	break;
		case WM_GV_USERATROOM:		OnAnyChatUserAtRoom(wParam, lParam);		break;
		//case WM_GV_LINKCLOSE:		OnAnyChatLinkClose(wParam, lParam);			break;
		//case WM_GV_MICSTATECHANGE:	OnAnyChatMicStateChange(wParam, lParam);	break;
		//case WM_GV_CAMERASTATE:		OnAnyChatCameraStateChange(wParam, lParam);	break;
		//case WM_GV_P2PCONNECTSTATE:	OnAnyChatP2PConnectState(wParam, lParam);	break;
		//case WM_GV_PRIVATEREQUEST:	OnAnyChatPrivateRequest(wParam, lParam);	break;
		//case WM_GV_PRIVATEECHO:		OnAnyChatPrivateEcho(wParam, lParam);		break;
		//case WM_GV_PRIVATEEXIT:		OnAnyChatPrivateExit(wParam, lParam);		break;
		//case WM_GV_USERINFOUPDATE:	OnAnyChatUserInfoUpdate(wParam, lParam);	break;
		//case WM_GV_FRIENDSTATUS:	OnAnyChatFriendStatus(wParam, lParam);		break;
		//case WM_GV_VIDEOSIZECHG:	OnAnyChatVideoSizeChange(wParam, lParam);	break;
		default:
			break;
	}
}

// 收到文字消息
function OnAnyChatTextMessage(dwFromUserId, dwToUserId, bSecret, lpMsgBuf, dwLen) {
	DisplayTextMessage(dwFromUserId, lpMsgBuf);
}

// 收到透明通道传输数据
function OnAnyChatTransBuffer(dwUserId, lpBuf, dwLen) {

}

// 收到透明通道（扩展）传输数据
function OnAnyChatTransBufferEx(dwUserId, lpBuf, dwLen, wParam, lParam, dwTaskId) {

}

// 文件传输完成通知
function OnAnyChatTransFile(dwUserId, lpFileName, lpTempFilePath, dwFileLength, wParam, lParam, dwTaskId) {

}

// 系统音量改变通知
function OnAnyChatVolumeChange(device, dwCurrentVolume) {

}

// 收到服务器发送的SDK Filter数据
function OnAnyChatSDKFilterData(lpBuf, dwLen) {

}

// 收到录像或拍照完成事件
function OnAnyChatRecordSnapShot(dwUserId, lpFileName, dwParam, bRecordType) {

}

// 收到录像或拍照完成事件（扩展）
function OnAnyChatRecordSnapShotEx(dwUserId, lpFileName, dwElapse, dwFlags, dwParam, lpUserStr) {

}


/********************************************
 *		AnyChat SDK核心业务流程				*
 *******************************************/
 






// 网络连接已关闭，该消息只有在客户端连接服务器成功之后，网络异常中断之时触发，reason表示连接断开的原因
function OnAnyChatLinkClose(reason, errorcode) {
	AddLog("OnAnyChatLinkClose(reason=" + reason + ", errorcode=" + errorcode + ")", LOG_TYPE_EVENT);
	reVideoDivSize();
	DisplayLoadingDiv(false);
	ShowRoomDiv(false);
	ShowHallDiv(false);
	ShowLoginDiv(true);
}

// 用户的音频设备状态变化消息，dwUserId表示用户ID号，State表示该用户是否已打开音频采集设备（0：关闭，1：打开）
function OnAnyChatMicStateChange(dwUserId, State) {

}

// 用户摄像头状态发生变化，dwUserId表示用户ID号，State表示摄像头的当前状态（0：没有摄像头，1：有摄像头但没有打开，2：打开）
function OnAnyChatCameraStateChange(dwUserId, State) {
    if (State == 0) GetID(dwUserId + "_CameraTag").src = "";
    if (State == 1) GetID(dwUserId + "_CameraTag").src = "./images/advanceset/camera_false.png";
    if (State == 2) GetID(dwUserId + "_CameraTag").src = "./images/advanceset/camera_true.png";
	if(dwUserId==mTargetUserId){
		if(State==1){
			reVideoDivSize();
			BRAC_SetVideoPos(0, GetID("AnyChatRemoteVideoDiv"), "ANYCHAT_VIDEO_REMOTE");
		}
		else
		{
			BRAC_SetVideoPos(dwUserId, GetID("AnyChatRemoteVideoDiv"), "ANYCHAT_VIDEO_REMOTE");
		}
		
	}
	
}

// 本地用户与其它用户的P2P网络连接状态发生变化，dwUserId表示其它用户ID号，State表示本地用户与其它用户的当前P2P网络连接状态（0：没有连接，1：仅TCP连接，2：仅UDP连接，3：TCP与UDP连接）
function OnAnyChatP2PConnectState(dwUserId, State) {

}

// 用户发起私聊请求，dwUserId表示发起者的用户ID号，dwRequestId表示私聊请求编号，标识该请求
function OnAnyChatPrivateRequest(dwUserId, dwRequestId) {

}

// 用户回复私聊请求，dwUserId表示回复者的用户ID号，errorcode为出错代码
function OnAnyChatPrivateEcho(dwUserId, errorcode) {

}

// 用户退出私聊，dwUserId表示退出者的用户ID号，errorcode为出错代码
function OnAnyChatPrivateExit(dwUserId, errorcode) {

}

// 视频通话消息通知回调函数
function OnAnyChatVideoCallEvent(dwEventType, dwUserId, dwErrorCode, dwFlags, dwParam, szUserStr) {
	AddLog("OnAnyChatVideoCallEvent(dwEventType=" + dwEventType + ", dwUserId=" + dwUserId + ", dwErrorCode=" + dwErrorCode + ", dwFlags=" + dwFlags + ", dwParam=" + dwParam + ", szUserStr=" + szUserStr + ")", LOG_TYPE_EVENT);
	
}

// 用户信息更新通知，dwUserId表示用户ID号，dwType表示更新类别
function OnAnyChatUserInfoUpdate(dwUserId, dwType) {
	AddLog("OnAnyChatUserInfoUpdate(dwUserId=" + dwUserId + ", dwType=" + dwType + ")", LOG_TYPE_EVENT);
}

// 好友在线状态变化，dwUserId表示好友用户ID号，dwStatus表示用户的当前活动状态：0 离线， 1 上线
function OnAnyChatFriendStatus(dwUserId, dwStatus) {
	AddLog("OnAnyChatFriendStatus(dwUserId=" + dwUserId + ", dwStatus=" + dwStatus + ")", LOG_TYPE_EVENT);
	
}

// 用户视频分辩率发生变化，dwUserId（INT）表示用户ID号，dwResolution（INT）表示用户的视频分辨率组合值（低16位表示宽度，高16位表示高度）
function OnAnyChatVideoSizeChange(dwUserId,dwResolution){
	if(dwUserId!=mTargetUserId)
		return;
	var height=dwResolution>>16;
	var width=dwResolution&0x0000ffff;
	var divWidth=GetID("AnyChatRemoteVideoDiv").offsetWidth;
	var divHeight=GetID("AnyChatRemoteVideoDiv").offsetHeight;
	//如果采用视频显示裁剪模式是动态模式，可根据分辨率的情况，动态改变div布局，使得画面不变形。
	if(width>height){
		if(divWidth<divHeight){
			//竖屏切换到横屏情况
			GetID("AnyChatRemoteVideoDiv").style.width=(4.0/3*divHeight)+"px";
			GetID("AnyChatRemoteVideoDiv").style.height=divHeight+"px";
		}
	}
	else{
		if(divWidth>divHeight){
			//横屏切换到竖屏情况
			GetID("AnyChatRemoteVideoDiv").style.width=(3.0/4*divHeight)+"px";
			GetID("AnyChatRemoteVideoDiv").style.height=divHeight+"px";
		}
	}	
}

