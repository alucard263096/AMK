Option Strict On
Option Explicit On
Imports System.Runtime.InteropServices

Module BRAnyChatServerSDK

    Public Const AnyChatServerSDKDll As String = "AnyChatServerSDK.dll"



    '#define BRAS_SERVERAPPMSG_CONNECTED		1		///< 连接AnyChat服务器成功
    Public Const BRAS_SERVERAPPMSG_CONNECTED As Integer = 1

    '#define BRAS_SERVERAPPMSG_DISCONNECT	2		    ///< 与AnyChat服务器断开连接
    Public Const BRAS_SERVERAPPMSG_DISCONNECT As Integer = 2


    '// 服务器应用程序消息回调函数定义
    'typedef void (CALLBACK* BRAS_OnServerAppMessage_CallBack)(DWORD dwMsg, LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnServerAppMessage_CallBack(ByVal dwMsg As Integer, ByVal lpUserValue As IntPtr)


    '// SDK定时器回调函数定义（上层应用可以在该回调中处理定时任务，而不需要额外开启线程，或是定时器）
    'ypedef void (CALLBACK* BRAS_OnTimerEvent_CallBack)(LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnTimerEvent_CallBack(ByVal lpUserValue As IntPtr)


    '// 用户身份验证回调函数定义
    'typedef DWORD (CALLBACK* BRAS_VerifyUser_CallBack)(IN LPCTSTR lpUserName,IN LPCTSTR lpPassword, OUT LPDWORD lpUserID, OUT LPDWORD lpUserLevel, OUT LPTSTR lpNickName,IN DWORD dwNCLen, LPVOID lpUserValue);
    Public Delegate Sub BRAS_VerifyUser_CallBack(ByVal lpUserName As String, ByVal IpPassword As String, ByRef IpUserID As Integer, ByRef IpUserLevel As Integer, ByRef IpNickName As IntPtr, ByVal dwNCLen As Integer, ByVal IpUserValue As IntPtr)


    '// 用户申请进入房间回调函数定义
    'typedef DWORD (CALLBACK* BRAS_PrepareEnterRoom_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPCTSTR lpRoomName,LPCTSTR lpPassword, LPVOID lpUserValue);
    Public Delegate Sub BRAS_PrepareEnterRoom_CallBack(ByVal dwUserId As Integer, ByVal dwRoomId As Integer, ByVal IpRoomName As String, ByVal IpUserValue As IntPtr)


    '// 用户登录成功回调函数定义
    'typedef void (CALLBACK* BRAS_OnUserLoginAction_CallBack)(DWORD dwUserId, LPCTSTR szUserName, DWORD dwLevel, LPCTSTR szIpAddr, LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnUserLoginAction_CallBack(ByVal dwUserId As Integer, ByVal szUserName As String, ByVal dwLevel As Integer, ByVal szIpAddr As String, ByVal IpUserValue As IntPtr)


    '// 用户注销回调函数定义
    'typedef void (CALLBACK* BRAS_OnUserLogoutAction_CallBack)(DWORD dwUserId, LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnUserLogoutAction_CallBack(ByVal dwUserId As Integer, ByVal IpUserValue As IntPtr)


    '// 用户进入房间回调函数定义
    'typedef void (CALLBACK* BRAS_OnUserEnterRoomAction_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnUserEnterRoomAction_CallBack(ByVal dwUserId As Integer, ByVal dwRoomId As Integer, ByVal IpUserValue As IntPtr)


    '// 用户离开房间回调函数定义
    ' typedef void (CALLBACK* BRAS_OnUserLeaveRoomAction_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnUserLeaveRoomAction_CallBack(ByVal dwUserId As Integer, ByVal dwRoomId As Integer, ByVal IpUserValue As IntPtr)


    '// 上层业务自定义数据回调函数定义
    'typedef void (CALLBACK* BRAS_OnRecvUserFilterData_CallBack)(DWORD dwUserId, LPCTSTR lpBuf, DWORD dwLen, LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnRecvUserFilterData_CallBack(ByVal dwUserId As Integer, ByVal IpBuf As String, ByVal dwLen As Integer, ByVal IpUserValue As IntPtr)


    '// 收到用户文字聊天通信数据回调函数定义
    'typedef void (CALLBACK* BRAS_OnRecvUserTextMsg_CallBack)(DWORD dwRoomId, DWORD dwSrcUserId, DWORD dwTarUserId, BOOL bSecret, LPCTSTR lpTextMessage, DWORD dwLen, LPVOID lpUserValue);
    Public Delegate Sub BRAS_OnRecvUserTextMsg_CallBack(ByVal dwRoomId As Integer, ByVal dwSrcUserId As Integer, ByVal dwTarUserId As Integer, ByVal bSecret As Integer, ByVal IpTextMessage As String, ByVal dwLen As Integer, ByVal IpUserValue As IntPtr)




    '/**
    '*	API 方法定义
    '*/
    '// 设置服务器应用程序消息回调函数
    'BRAS_API DWORD BRAS_SetOnServerAppMessageCallBack(BRAS_OnServerAppMessage_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetOnServerAppMessageCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetOnServerAppMessageCallBack(ByVal lpFunction As BRAS_OnServerAppMessage_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    '// 设置SDK定时器回调函数（dwElapse：定时器间隔，单位：ms）
    ' BRAS_API DWORD BRAS_SetTimerEventCallBack(DWORD dwElapse, BRAS_OnTimerEvent_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetTimerEventCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetTimerEventCallBack(ByVal dwElapse As Integer, ByVal IpFunction As BRAS_OnTimerEvent_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    '// 设置用户身份验证回调函数
    'BRAS_API DWORD BRAS_SetVerifyUserCallBack(BRAS_VerifyUser_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetVerifyUserCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetVerifyUserCallBack(ByVal lpFunction As BRAS_VerifyUser_CallBack, ByVal IpUserValue As IntPtr) As Integer
    End Function



    '// 设置用户申请进入房间回调函数
    'BRAS_API DWORD BRAS_SetPrepareEnterRoomCallBack(BRAS_PrepareEnterRoom_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetPrepareEnterRoomCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetPrepareEnterRoomCallBack(ByVal lpFunction As BRAS_PrepareEnterRoom_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    ' // 设置用户登录成功回调函数
    'BRAS_API DWORD BRAS_SetOnUserLoginActionCallBack(BRAS_OnUserLoginAction_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetOnUserLoginActionCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetOnUserLoginActionCallBack(ByVal lpFunction As BRAS_OnUserLoginAction_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    '// 设置用户注销回调函数
    'BRAS_API DWORD BRAS_SetOnUserLogoutActionCallBack(BRAS_OnUserLogoutAction_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetOnUserLogoutActionCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetOnUserLogoutActionCallBack(ByVal lpFunction As BRAS_OnUserLogoutAction_CallBack, ByVal lpUser As IntPtr) As Integer
    End Function



    '// 设置用户进入房间回调函数
    'BRAS_API DWORD BRAS_SetOnUserEnterRoomActionCallBack(BRAS_OnUserEnterRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetOnUserEnterRoomActionCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetOnUserEnterRoomActionCallBack(ByVal lpFunction As BRAS_OnUserEnterRoomAction_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    '// 设置用户离开房间回调函数
    'BRAS_API DWORD BRAS_SetOnUserLeaveRoomActionCallBack(BRAS_OnUserLeaveRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetOnUserLeaveRoomActionCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetOnUserLeaveRoomActionCallBack(ByVal lpFunction As BRAS_OnUserLeaveRoomAction_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    '// 设置用户上层业务自定义数据回调函数
    'BRAS_API DWORD BRAS_SetOnRecvUserFilterDataCallBack(BRAS_OnRecvUserFilterData_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetOnRecvUserFilterDataCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetOnRecvUserFilterDataCallBack(ByVal lpFunction As BRAS_OnRecvUserFilterData_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    '// 设置用户文字聊天通信数据回调函数
    'BRAS_API DWORD BRAS_SetOnRecvUserTextMsgCallBack(BRAS_OnRecvUserTextMsg_CallBack lpFunction, LPVOID lpUserValue=NULL);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SetOnRecvUserTextMsgCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SetOnRecvUserTextMsgCallBack(ByVal lpFunction As BRAS_OnRecvUserTextMsg_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function



    '// 初始化SDK
    'BRAS_API DWORD BRAS_InitSDK(DWORD dwReserved);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_InitSDK", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_InitSDK(ByVal dwReserved As Integer) As Integer
    End Function



    ' // 释放资源
    'BRAS_API DWORD BRAS_Release(void);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_Release", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_Release() As Integer
    End Function




    '// 向指定用户发送数据
    'BRAS_API DWORD BRAS_SendBufToUser(DWORD dwUserId, LPCTSTR lpBuf, DWORD dwLen);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SendBufToUser", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SendBufToUser(ByVal dwUserId As Integer, ByVal lpBuf As String, ByVal dwLen As Integer) As Integer
    End Function



    ' // 向指定房间的所有用户发送数据
    ' BRAS_API DWORD BRAS_SendBufToRoom(DWORD dwRoomId, LPCTSTR lpBuf, DWORD dwLen);

    <DllImport(AnyChatServerSDKDll, EntryPoint:="BRAS_SendBufToRoom", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAS_SendBufToRoom(ByVal dwRoomId As Integer, ByVal lpBuf As String, ByVal dwLen As Integer) As Integer
    End Function


End Module
