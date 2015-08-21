unit AnyChatServerSDK;
(****************************************************************************
     AnyChat Platform ServerSDK.h
****************************************************************************)

interface
uses
  Windows,MMSystem,SysUtils,Messages,Classes,Types;

const
  C_AnyChatServerSDKLib = 'AnyChatServerSDK.dll';
  
const
	//#define BRAS_SERVERAPPMSG_CONNECTED	1		///< 连接AnyChat服务器成功
	BRAS_SERVERAPPMSG_CONNECTED		= 1;
	//#define BRAS_SERVERAPPMSG_DISCONNECT	2		///< 与AnyChat服务器断开连接
	BRAS_SERVERAPPMSG_DISCONNECT	= 2;
	
type
	// 服务器应用程序消息回调函数定义
	//typedef void (CALLBACK* BRAS_OnServerAppMessage_CallBack)(DWORD dwMsg, LPVOID lpUserValue);
	BRAS_OnServerAppMessage_CallBack=procedure(dwMsg:DWORD;lpUserValue:Pointer);stdcall;
	
	// SDK定时器回调函数定义（上层应用可以在该回调中处理定时任务，而不需要额外开启线程，或是定时器）
	//typedef void (CALLBACK* BRAS_OnTimerEvent_CallBack)(LPVOID lpUserValue);
	BRAS_OnTimerEvent_CallBack=procedure(lpUserValue:Pointer);stdcall;

	// 用户身份验证回调函数定义
	//typedef DWORD (CALLBACK* BRAS_VerifyUser_CallBack)(IN LPCTSTR lpUserName,IN LPCTSTR lpPassword, OUT LPDWORD lpUserID, OUT LPDWORD lpUserLevel, OUT LPTSTR lpNickName,IN DWORD dwNCLen, LPVOID lpUserValue);
	BRAS_VerifyUser_CallBack=procedure(lpUserName:LPCTSTR;lpPassword:LPCTSTR;lpUserID:LPDWORD;lpUserLevel:LPDWORD;lpNickName:LPTSTR;dwNCLen:DWORD;lpUserValue:Pointer);stdcall;
	
	// 用户申请进入房间回调函数定义
	//typedef DWORD (CALLBACK* BRAS_PrepareEnterRoom_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPCTSTR lpRoomName,LPCTSTR lpPassword, LPVOID lpUserValue);
	BRAS_PrepareEnterRoom_CallBack=procedure(dwUserId:DWORD;dwRoomId:DWORD;lpRoomName:LPCTSTR;lpPassword:LPCTSTR;lpUserValue:Pointer);stdcall;
	
	// 用户登录成功回调函数定义
	//typedef void (CALLBACK* BRAS_OnUserLoginAction_CallBack)(DWORD dwUserId, LPCTSTR szUserName, DWORD dwLevel, LPCTSTR szIpAddr, LPVOID lpUserValue);
	BRAS_OnUserLoginAction_CallBack=procedure(dwUserId:DWORD;szUserName:LPCTSTR;dwLevel:DWORD;szIpAddr:LPCTSTR;lpUserValue:Pointer);stdcall;
	
	// 用户注销回调函数定义
	//typedef void (CALLBACK* BRAS_OnUserLogoutAction_CallBack)(DWORD dwUserId, LPVOID lpUserValue);
	BRAS_OnUserLogoutAction_CallBack=procedure(dwUserId:DWORD;lpUserValue:Pointer);stdcall;
	
	// 用户进入房间回调函数定义
	//typedef void (CALLBACK* BRAS_OnUserEnterRoomAction_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPVOID lpUserValue);
	BRAS_OnUserEnterRoomAction_CallBack=procedure(dwUserId:DWORD;dwRoomId:DWORD;lpUserValue:Pointer);stdcall;
	
	// 用户离开房间回调函数定义
	//typedef void (CALLBACK* BRAS_OnUserLeaveRoomAction_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPVOID lpUserValue);
	BRAS_OnUserLeaveRoomAction_CallBack=procedure(dwUserId:DWORD;dwRoomId:DWORD;lpUserValue:Pointer);stdcall;
	
	// 上层业务自定义数据回调函数定义
	//typedef void (CALLBACK* BRAS_OnRecvUserFilterData_CallBack)(DWORD dwUserId, LPCTSTR lpBuf, DWORD dwLen, LPVOID lpUserValue);
	BRAS_OnRecvUserFilterData_CallBack=procedure(dwUserId:DWORD;lpBuf:LPCTSTR;dwLen:DWORD;lpUserValue:Pointer);stdcall;
	
	// 收到用户文字聊天通信数据回调函数定义
	//typedef void (CALLBACK* BRAS_OnRecvUserTextMsg_CallBack)(DWORD dwRoomId, DWORD dwSrcUserId, DWORD dwTarUserId, BOOL bSecret, LPCTSTR lpTextMessage, DWORD dwLen, LPVOID lpUserValue);
	BRAS_OnRecvUserTextMsg_CallBack=procedure(dwRoomId:DWORD;dwSrcUserId:DWORD;dwTarUserId:DWORD;bSecret:BOOL;lpTextMessage:LPCTSTR;dwLen:DWORD;lpUserValue:Pointer);stdcall;

	// 透明通道数据回调函数定义
	//typedef void (CALLBACK * BRAS_OnTransBuffer_CallBack)(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue);
	BRAS_OnTransBuffer_CallBack=procedure(dwUserId:DWORD; lpBuf:LPCTSTR; dwLen:DWORD; lpUserValue:Pointer);stdcall;
	
	// 透明通道数据扩展回调函数定义
	//typedef void (CALLBACK * BRAS_OnTransBufferEx_CallBack)(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
	BRAS_OnTransBufferEx_CallBack=procedure(dwUserId:DWORD; lpBuf:LPCTSTR; dwLen:DWORD; wParam:DWORD; lParam:DWORD; dwTaskId:DWORD; lpUserValue:Pointer);stdcall;
	
	// 文件传输回调函数定义
	//typedef void (CALLBACK * BRAS_OnTransFile_CallBack)(DWORD dwUserId, LPCTSTR lpFileName, LPCTSTR lpTempFilePath, DWORD dwFileLength, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
	BRAS_OnTransFile_CallBack=procedure(dwUserId:DWORD; lpFileName:LPCTSTR; lpTempFilePath:LPCTSTR; dwFileLength:DWORD; wParam:DWORD; lParam:DWORD; dwTaskId:DWORD; lpUserValue:Pointer);stdcall;

	// 服务器录像回调函数定义
	//typedef void (CALLBACK * BRAS_OnServerRecord_CallBack)(DWORD dwUserId, DWORD dwParam, DWORD dwRecordServerId, DWORD dwElapse, LPCTSTR lpRecordFileName, LPVOID lpUserValue);
	BRAS_OnServerRecord_CallBack=procedure(dwUserId:DWORD; dwParam:DWORD; dwRecordServerId:DWORD; dwElapse:DWORD; lpRecordFileName:LPCTSTR; lpUserValue:Pointer);stdcall;


	// API 方法定义

	// 设置服务器应用程序消息回调函数
	//BRAS_API DWORD BRAS_SetOnServerAppMessageCallBack(BRAS_OnServerAppMessage_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnServerAppMessageCallBack(lpFunction:BRAS_OnServerAppMessage_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	
	// 设置SDK定时器回调函数（dwElapse：定时器间隔，单位：ms）
	//BRAS_API DWORD BRAS_SetTimerEventCallBack(DWORD dwElapse, BRAS_OnTimerEvent_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetTimerEventCallBack(dwElapse:DWORD;lpFunction:BRAS_OnTimerEvent_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	
	// 设置用户身份验证回调函数
	//BRAS_API DWORD BRAS_SetVerifyUserCallBack(BRAS_VerifyUser_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetVerifyUserCallBack(lpFunction:BRAS_VerifyUser_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
			
	// 设置用户申请进入房间回调函数
	//BRAS_API DWORD BRAS_SetPrepareEnterRoomCallBack(BRAS_PrepareEnterRoom_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetPrepareEnterRoomCallBack(lpFunction:BRAS_PrepareEnterRoom_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
			
	// 设置用户登录成功回调函数
	//BRAS_API DWORD BRAS_SetOnUserLoginActionCallBack(BRAS_OnUserLoginAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserLoginActionCallBack(lpFunction:BRAS_OnUserLoginAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	
	// 设置用户注销回调函数
	//BRAS_API DWORD BRAS_SetOnUserLogoutActionCallBack(BRAS_OnUserLogoutAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserLogoutActionCallBack(lpFunction:BRAS_OnUserLogoutAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;	
	
	// 设置用户进入房间回调函数
	//BRAS_API DWORD BRAS_SetOnUserEnterRoomActionCallBack(BRAS_OnUserEnterRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserEnterRoomActionCallBack(lpFunction:BRAS_OnUserEnterRoomAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
						
	// 设置用户离开房间回调函数
	//BRAS_API DWORD BRAS_SetOnUserLeaveRoomActionCallBack(BRAS_OnUserLeaveRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserLeaveRoomActionCallBack(lpFunction:BRAS_OnUserLeaveRoomAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	
	// 设置用户上层业务自定义数据回调函数
	//BRAS_API DWORD BRAS_SetOnRecvUserFilterDataCallBack(BRAS_OnRecvUserFilterData_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnRecvUserFilterDataCallBack(lpFunction:BRAS_OnRecvUserFilterData_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
			
	// 设置用户文字聊天通信数据回调函数
	//BRAS_API DWORD BRAS_SetOnRecvUserTextMsgCallBack(BRAS_OnRecvUserTextMsg_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnRecvUserTextMsgCallBack(lpFunction:BRAS_OnRecvUserTextMsg_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;

	// 设置透明通道数据回调函数
	//BRAS_API DWORD BRAS_SetOnTransBufferCallBack(BRAS_OnTransBuffer_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnTransBufferCallBack(lpFunction:BRAS_OnTransBuffer_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;

	// 设置透明通道数据扩展回调函数
	//BRAS_API DWORD BRAS_SetOnTransBufferExCallBack(BRAS_OnTransBufferEx_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnTransBufferExCallBack(lpFunction:BRAS_OnTransBufferEx_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;
	
	// 设置文件传输回调函数
	//BRAS_API DWORD BRAS_SetOnTransFileCallBack(BRAS_OnTransFile_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnTransFileCallBack(lpFunction:BRAS_OnTransFile_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;
	
	// 设置服务器录像通知回调函数
	//BRAS_API DWORD BRAS_SetOnServerRecordCallBack(BRAS_OnServerRecord_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnServerRecordCallBack(lpFunction:BRAS_OnServerRecord_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;
	
	
	// 初始化SDK
	//BRAS_API DWORD BRAS_InitSDK(DWORD dwReserved);
	function BRAS_InitSDK(dwReserved:DWORD):DWORD;cdecl;
			
	// 释放资源
	//BRAS_API DWORD BRAS_Release(void);
	function BRAS_Release():DWORD;cdecl;
			
	// 向指定用户发送数据
	//BRAS_API DWORD BRAS_SendBufToUser(DWORD dwUserId, LPCTSTR lpBuf, DWORD dwLen);
	function BRAS_SendBufToUser(dwUserId:DWORD;lpBuf:LPCTSTR;dwLen:DWORD):DWORD;cdecl;
			
	// 向指定房间的所有用户发送数据
	//BRAS_API DWORD BRAS_SendBufToRoom(DWORD dwRoomId, LPCTSTR lpBuf, DWORD dwLen);
	function BRAS_SendBufToRoom(dwRoomId:DWORD;lpBuf:LPCTSTR;dwLen:DWORD):DWORD;cdecl;
	
	// 向指定用户发送透明通道数据
	//BRAS_API DWORD BRAS_TransBuffer(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen);
	function BRAS_TransBuffer(dwUserId:DWORD; lpBuf:LPCTSTR; dwLen:DWORD):DWORD;cdecl;
	
	// 向指定用户发送扩展缓冲区数据
	//BRAS_API DWORD BRAS_TransBufferEx(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
	function BRAS_TransBufferEx(dwUserId:DWORD; lpBuf:LPCTSTR; dwLen:DWORD; wParam:DWORD; lParam:DWORD; dwFlags:DWORD; dwTaskId:PDWORD):DWORD;cdecl;
	
	// 向指定用户发送文件
	//BRAS_API DWORD BRAS_TransFile(DWORD dwUserId, LPCTSTR lpLocalPathName, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
	function BRAS_TransFile(dwUserId:DWORD; lpLocalPathName:LPCTSTR; wParam:DWORD; lParam:DWORD; dwFlags:DWORD; dwTaskId:PDWORD):DWORD;cdecl;
	
	// 中心端录像控制
	//BRAS_API DWORD BRAS_StreamRecordCtrl(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam, DWORD dwRecordServerId);
	function BRAS_StreamRecordCtrl(dwUserId:DWORD; bStartRecord:DWORD; dwFlags:DWORD; dwParam:DWORD; dwRecordServerId:DWORD):DWORD;

			
implementation
	 
	// 设置服务器应用程序消息回调函数
	//BRAS_API DWORD BRAS_SetOnServerAppMessageCallBack(BRAS_OnServerAppMessage_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnServerAppMessageCallBack(lpFunction:BRAS_OnServerAppMessage_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnServerAppMessageCallBack';
	
	// 设置SDK定时器回调函数（dwElapse：定时器间隔，单位：ms）
	//BRAS_API DWORD BRAS_SetTimerEventCallBack(DWORD dwElapse, BRAS_OnTimerEvent_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetTimerEventCallBack(dwElapse:DWORD;lpFunction:BRAS_OnTimerEvent_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetTimerEventCallBack';
	
	// 设置用户身份验证回调函数
	//BRAS_API DWORD BRAS_SetVerifyUserCallBack(BRAS_VerifyUser_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetVerifyUserCallBack(lpFunction:BRAS_VerifyUser_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetVerifyUserCallBack';
	
	// 设置用户申请进入房间回调函数
	//BRAS_API DWORD BRAS_SetPrepareEnterRoomCallBack(BRAS_PrepareEnterRoom_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetPrepareEnterRoomCallBack(lpFunction:BRAS_PrepareEnterRoom_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetPrepareEnterRoomCallBack';
			
	// 设置用户登录成功回调函数
	//BRAS_API DWORD BRAS_SetOnUserLoginActionCallBack(BRAS_OnUserLoginAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserLoginActionCallBack(lpFunction:BRAS_OnUserLoginAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnUserLoginActionCallBack';
	
	// 设置用户注销回调函数
	//BRAS_API DWORD BRAS_SetOnUserLogoutActionCallBack(BRAS_OnUserLogoutAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserLogoutActionCallBack(lpFunction:BRAS_OnUserLogoutAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnUserLogoutActionCallBack';	
	
	// 设置用户进入房间回调函数
	//BRAS_API DWORD BRAS_SetOnUserEnterRoomActionCallBack(BRAS_OnUserEnterRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserEnterRoomActionCallBack(lpFunction:BRAS_OnUserEnterRoomAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnUserEnterRoomActionCallBack';
	
	// 设置用户离开房间回调函数
	//BRAS_API DWORD BRAS_SetOnUserLeaveRoomActionCallBack(BRAS_OnUserLeaveRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnUserLeaveRoomActionCallBack(lpFunction:BRAS_OnUserLeaveRoomAction_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnUserLeaveRoomActionCallBack';
	
	// 设置用户上层业务自定义数据回调函数
	//BRAS_API DWORD BRAS_SetOnRecvUserFilterDataCallBack(BRAS_OnRecvUserFilterData_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnRecvUserFilterDataCallBack(lpFunction:BRAS_OnRecvUserFilterData_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnRecvUserFilterDataCallBack';
	
	// 设置用户文字聊天通信数据回调函数
	//BRAS_API DWORD BRAS_SetOnRecvUserTextMsgCallBack(BRAS_OnRecvUserTextMsg_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnRecvUserTextMsgCallBack(lpFunction:BRAS_OnRecvUserTextMsg_CallBack;lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnRecvUserTextMsgCallBack';
	
	// 设置透明通道数据回调函数
	//BRAS_API DWORD BRAS_SetOnTransBufferCallBack(BRAS_OnTransBuffer_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnTransBufferCallBack(lpFunction:BRAS_OnTransBuffer_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnTransBufferCallBack';

	// 设置透明通道数据扩展回调函数
	//BRAS_API DWORD BRAS_SetOnTransBufferExCallBack(BRAS_OnTransBufferEx_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnTransBufferExCallBack(lpFunction:BRAS_OnTransBufferEx_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnTransBufferExCallBack';
	
	// 设置文件传输回调函数
	//BRAS_API DWORD BRAS_SetOnTransFileCallBack(BRAS_OnTransFile_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnTransFileCallBack(lpFunction:BRAS_OnTransFile_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnTransFileCallBack';
	
	// 设置服务器录像通知回调函数
	//BRAS_API DWORD BRAS_SetOnServerRecordCallBack(BRAS_OnServerRecord_CallBack lpFunction, LPVOID lpUserValue=NULL);
	function BRAS_SetOnServerRecordCallBack(lpFunction:BRAS_OnServerRecord_CallBack; lpUserValue:Pointer=nil):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SetOnServerRecordCallBack';
	
	// 初始化SDK
	//BRAS_API DWORD BRAS_InitSDK(DWORD dwReserved);
	function BRAS_InitSDK(dwReserved:DWORD):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_InitSDK';
	
	// 释放资源
	//BRAS_API DWORD BRAS_Release(void);
	function BRAS_Release():DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_Release';
		
	// 向指定用户发送数据
	//BRAS_API DWORD BRAS_SendBufToUser(DWORD dwUserId, LPCTSTR lpBuf, DWORD dwLen);
	function BRAS_SendBufToUser(dwUserId:DWORD;lpBuf:LPCTSTR;dwLen:DWORD):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SendBufToUser';
		
	// 向指定房间的所有用户发送数据
	//BRAS_API DWORD BRAS_SendBufToRoom(DWORD dwRoomId, LPCTSTR lpBuf, DWORD dwLen);
	function BRAS_SendBufToRoom(dwRoomId:DWORD;lpBuf:LPCTSTR;dwLen:DWORD):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_SendBufToRoom';
	
	// 向指定用户发送透明通道数据
	//BRAS_API DWORD BRAS_TransBuffer(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen);
	function BRAS_TransBuffer(dwUserId:DWORD; lpBuf:LPCTSTR; dwLen:DWORD):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_TransBuffer';
	
	// 向指定用户发送扩展缓冲区数据
	//BRAS_API DWORD BRAS_TransBufferEx(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
	function BRAS_TransBufferEx(dwUserId:DWORD; lpBuf:LPCTSTR; dwLen:DWORD; wParam:DWORD; lParam:DWORD; dwFlags:DWORD; dwTaskId:PDWORD):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_TransBufferEx';
	
	// 向指定用户发送文件
	//BRAS_API DWORD BRAS_TransFile(DWORD dwUserId, LPCTSTR lpLocalPathName, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
	function BRAS_TransFile(dwUserId:DWORD; lpLocalPathName:LPCTSTR; wParam:DWORD; lParam:DWORD; dwFlags:DWORD; dwTaskId:PDWORD):DWORD;cdecl;
	external C_AnyChatServerSDKLib name 'BRAS_TransFile';
	
	// 中心端录像控制
	//BRAS_API DWORD BRAS_StreamRecordCtrl(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam, DWORD dwRecordServerId);
	function BRAS_StreamRecordCtrl(dwUserId:DWORD; bStartRecord:DWORD; dwFlags:DWORD; dwParam:DWORD; dwRecordServerId:DWORD):DWORD;
	external C_AnyChatServerSDKLib name 'BRAS_StreamRecordCtrl';
	
	
end.
