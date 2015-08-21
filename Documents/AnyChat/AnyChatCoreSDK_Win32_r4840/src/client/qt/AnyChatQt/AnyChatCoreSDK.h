#ifndef ANYCHATCORESDK_H
#define ANYCHATCORESDK_H

#include <windows.h>
#include <stdlib.h>
#include <stdio.h>
#include "BRAnyChatCoreSDK.h"

// ��ʼ��ϵͳ
BRAC_API DWORD BRAC_InitSDK(HWND hWnd, DWORD dwFuncMode);
typedef DWORD (_cdecl * BRAC_InitSDK_Func)(HWND hWnd, DWORD dwFuncMode);

// ��ȡSDK�汾��Ϣ
BRAC_API DWORD BRAC_GetSDKVersion(DWORD& dwMainVer, DWORD& dwSubVer, LPSTR lpCompileTime, DWORD dwBufLen);
typedef DWORD(_cdecl * BRAC_GetSDKVersion_Func)(DWORD& dwMainVer, DWORD& dwSubVer, LPSTR lpCompileTime, DWORD dwBufLen);

// ����رգ�SDK������־
BRAC_API DWORD BRAC_ActiveCallLog(BOOL bActive);
typedef DWORD(_cdecl * BRAC_ActiveCallLog_Func)(BOOL bActive);

// SDK�ں˲�������
BRAC_API DWORD BRAC_SetSDKOption(int optname, const char FAR* optval, int optlen);
typedef DWORD(_cdecl * BRAC_SetSDKOption_Func)(int optname, const char FAR* optval, int optlen);

// ���÷�������֤���루��������ֹ�Ƿ��û���SDK���ӷ��������Ϸ��û����������ӣ�
BRAC_API DWORD BRAC_SetServerAuthPass(LPCTSTR lpPassword);
typedef DWORD(_cdecl * BRAC_SetServerAuthPass_Func)(LPCTSTR lpPassword);


// ������Ƶ���ݻص�����
BRAC_API DWORD BRAC_SetVideoDataCallBack(BRAC_PixelFormat pixFmt, BRAC_VideoData_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetVideoDataCallBack_Func)(BRAC_PixelFormat pixFmt, BRAC_VideoData_CallBack lpFunction, LPVOID lpUserValue);
// ������Ƶ���ݻص�����
BRAC_API DWORD BRAC_SetAudioDataCallBack(BRAC_AudioData_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetAudioDataCallBack_Func)(BRAC_AudioData_CallBack lpFunction, LPVOID lpUserValue);
// ���������仯�ص�����
BRAC_API DWORD BRAC_SetVolumeChangeCallBack(BRAC_VolumeChange_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetVolumeChangeCallBack_Func)(BRAC_VolumeChange_CallBack lpFunction, LPVOID lpUserValue);
// ����͸��ͨ��������չ�ص�����
BRAC_API DWORD BRAC_SetTransBufferExCallBack(BRAC_TransBufferEx_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetTransBufferExCallBack_Func)(BRAC_TransBufferEx_CallBack lpFunction, LPVOID lpUserValue);
// ����͸��ͨ�����ݻص�����
BRAC_API DWORD BRAC_SetTransBufferCallBack(BRAC_TransBuffer_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetTransBufferCallBack_Func)(BRAC_TransBuffer_CallBack lpFunction, LPVOID lpUserValue);
// �����ļ�����ص�����
BRAC_API DWORD BRAC_SetTransFileCallBack(BRAC_TransFile_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetTransFileCallBack_Func)(BRAC_TransFile_CallBack lpFunction, LPVOID lpUserValue);
// ����¼������������֪ͨ�ص�����
BRAC_API DWORD BRAC_SetRecordSnapShotCallBack(BRAC_RecordSnapShot_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetRecordSnapShotCallBack_Func)(BRAC_RecordSnapShot_CallBack lpFunction, LPVOID lpUserValue);
// ����SDK Filterͨ�����ݻص���������
BRAC_API DWORD BRAC_SetSDKFilterDataCallBack(BRAC_SDKFilterData_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetSDKFilterDataCallBack_Func)(BRAC_SDKFilterData_CallBack lpFunction, LPVOID lpUserValue);
// �����첽��Ϣ֪ͨ�ص�����
BRAC_API DWORD BRAC_SetNotifyMessageCallBack(BRAC_NotifyMessage_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetNotifyMessageCallBack_Func)(BRAC_NotifyMessage_CallBack lpFunction, LPVOID lpUserValue);
// ����������Ϣ�ص�����
BRAC_API DWORD BRAC_SetTextMessageCallBack(BRAC_TextMessage_CallBack lpFunction, LPVOID lpUserValue);
typedef DWORD(_cdecl * BRAC_SetTextMessageCallBack_Func)(BRAC_TextMessage_CallBack lpFunction, LPVOID lpUserValue);




// ���ӷ�����
BRAC_API DWORD BRAC_Connect(LPCTSTR lpServerAddr, DWORD dwPort);
typedef DWORD(_cdecl * BRAC_Connect_Func)(LPCTSTR lpServerAddr, DWORD dwPort);
// ��¼ϵͳ
BRAC_API DWORD BRAC_Login(LPCTSTR lpUserName, LPCTSTR lpPassword, DWORD dwPassEncType);
typedef DWORD(_cdecl * BRAC_Login_Func)(LPCTSTR lpUserName, LPCTSTR lpPassword, DWORD dwPassEncType);
// ���뷿��
BRAC_API DWORD BRAC_EnterRoom(DWORD dwRoomid, LPCTSTR lpRoomPass, DWORD dwPassEncType);
typedef DWORD(_cdecl * BRAC_EnterRoom_Func)(DWORD dwRoomid, LPCTSTR lpRoomPass, DWORD dwPassEncType);
// ���뷿��
BRAC_API DWORD BRAC_EnterRoomEx(LPCTSTR lpRoomName, LPCTSTR lpRoomPass);
typedef DWORD(_cdecl * BRAC_EnterRoomEx_Func)(LPCTSTR lpRoomName, LPCTSTR lpRoomPass);
// �뿪����
BRAC_API DWORD BRAC_LeaveRoom(DWORD dwRoomid);
typedef DWORD(_cdecl * BRAC_LeaveRoom_Func)(DWORD dwRoomid);
// ע��ϵͳ
BRAC_API DWORD BRAC_Logout(VOID);
typedef DWORD(_cdecl * BRAC_Logout_Func)(VOID);


// �����û���Ƶ
BRAC_API DWORD BRAC_UserCameraControl(DWORD dwUserid, BOOL bOpen);
typedef DWORD(_cdecl * BRAC_UserCameraControl_Func)(DWORD dwUserid, BOOL bOpen);
// �����û�����
BRAC_API DWORD BRAC_UserSpeakControl(DWORD dwUserid, BOOL bOpen);
typedef DWORD(_cdecl * BRAC_UserSpeakControl_Func)(DWORD dwUserid, BOOL bOpen);
// ������Ƶ��ʾλ��
BRAC_API DWORD BRAC_SetVideoPos(DWORD dwUserid, HWND hWnd, DWORD dwLeft, DWORD dwTop, DWORD dwRight, DWORD dwBottom);
typedef DWORD(_cdecl * BRAC_SetVideoPos_Func)(DWORD dwUserid, HWND hWnd, DWORD dwLeft, DWORD dwTop, DWORD dwRight, DWORD dwBottom);
// ��ȡָ����Ƶ�豸�ĵ�ǰ����
BRAC_API DWORD BRAC_AudioGetVolume(BRAC_AudioDevice device, DWORD& dwVolume);
typedef DWORD(_cdecl * BRAC_AudioGetVolume_Func)(BRAC_AudioDevice device, DWORD& dwVolume);
// ����ָ����Ƶ�豸������
BRAC_API DWORD BRAC_AudioSetVolume(BRAC_AudioDevice device, DWORD dwVolume);
typedef DWORD(_cdecl * BRAC_AudioSetVolume_Func)(BRAC_AudioDevice device, DWORD dwVolume);

// ��ȡ��ǰ���������û��б�
BRAC_API DWORD BRAC_GetOnlineUser(LPDWORD lpUserIDArray, DWORD& dwUserNum);
typedef DWORD(_cdecl * BRAC_GetOnlineUser_Func)(LPDWORD lpUserIDArray, DWORD& dwUserNum);
// ��ѯ�û�����ͷ��״̬
BRAC_API DWORD BRAC_GetCameraState(DWORD dwUserid, DWORD& dwState);
typedef DWORD(_cdecl * BRAC_GetCameraState_Func)(DWORD dwUserid, DWORD& dwState);
// ��ѯ�û�����
BRAC_API DWORD BRAC_GetUserName(DWORD dwUserid, CHAR* lpUserName, DWORD dwLen);
typedef DWORD(_cdecl * BRAC_GetUserName_Func)(DWORD dwUserid, CHAR* lpUserName, DWORD dwLen);
// ��ѯ��������
BRAC_API DWORD BRAC_GetRoomName(DWORD dwRoomId, CHAR* lpRoomName, DWORD dwLen);
typedef DWORD(_cdecl * BRAC_GetRoomName_Func)(DWORD dwRoomId, CHAR* lpRoomName, DWORD dwLen);
// ��ʾ������Ƶ������ڶԻ���
BRAC_API DWORD BRAC_ShowLVProperty(HWND hParent, LPCTSTR szCaption, DWORD dwX ,DWORD dwY);
typedef DWORD(_cdecl * BRAC_ShowLVProperty_Func)(HWND hParent, LPCTSTR szCaption, DWORD dwX ,DWORD dwY);

// ö�ٱ�����Ƶ�ɼ��豸
BRAC_API DWORD BRAC_EnumVideoCapture(CHAR** lpDeviceName, DWORD& dwDeviceNum);
typedef DWORD(_cdecl * BRAC_EnumVideoCapture_Func)(CHAR** lpDeviceName, DWORD& dwDeviceNum);
// ö�ٱ�����Ƶ�ɼ��豸
BRAC_API DWORD BRAC_EnumAudioCapture(CHAR** lpDeviceName, DWORD& dwDeviceNum);
typedef DWORD(_cdecl * BRAC_EnumAudioCapture_Func)(CHAR** lpDeviceName, DWORD& dwDeviceNum);
// ѡ��ָ������Ƶ�ɼ��豸
BRAC_API DWORD BRAC_SelectVideoCapture(LPCTSTR szCaptureName);
typedef DWORD(_cdecl *BRAC_SelectVideoCapture_Func)(LPCTSTR szCaptureName);
// ѡ��ָ������Ƶ�ɼ��豸
BRAC_API DWORD BRAC_SelectAudioCapture(LPCTSTR szCaptureName);
typedef DWORD(_cdecl * BRAC_SelectAudioCapture_Func)(LPCTSTR szCaptureName);
// ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
BRAC_API DWORD BRAC_GetCurVideoCapture(CHAR* lpDeviceName, DWORD dwLen);
typedef DWORD(_cdecl * BRAC_GetCurVideoCapture_Func)(CHAR* lpDeviceName, DWORD dwLen);
// ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
BRAC_API DWORD BRAC_GetCurAudioCapture(CHAR* lpDeviceName, DWORD dwLen);
typedef DWORD(_cdecl * BRAC_GetCurAudioCapture_Func)(CHAR* lpDeviceName, DWORD dwLen);

// �û�������Ƶ¼��
BRAC_API DWORD BRAC_StreamRecordCtrl(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam);
typedef DWORD(_cdecl* BRAC_StreamRecordCtrl_Func)(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam);
// ���û�����Ƶ����ץ�ģ����գ�
BRAC_API DWORD BRAC_SnapShot(DWORD dwUserId, DWORD dwFlags, DWORD dwParam);
typedef DWORD(_cdecl* BRAC_SnapShot_Func)(DWORD dwUserId, DWORD dwFlags, DWORD dwParam);

// ͸��ͨ�����ͻ�����
BRAC_API DWORD BRAC_TransBuffer(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen);
typedef DWORD(_cdecl * BRAC_TransBuffer_Func)(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen);
// ͸��ͨ�����ͻ�������չ
BRAC_API DWORD BRAC_TransBufferEx(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
typedef DWORD(_cdecl * BRAC_TransBufferEx_Func)(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
// �����ļ�
BRAC_API DWORD BRAC_TransFile(DWORD dwUserid, LPCTSTR lpLocalPathName, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
typedef DWORD(_cdecl * BRAC_TransFile_Func)(DWORD dwUserid, LPCTSTR lpLocalPathName, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
// ȡ����������
BRAC_API DWORD BRAC_CancelTransTask(DWORD dwUserid, DWORD dwTaskId);
typedef DWORD(_cdecl * BRAC_CancelTransTask_Func)(DWORD dwUserid, DWORD dwTaskId);
// �����ı���Ϣ
BRAC_API DWORD BRAC_SendTextMessage(DWORD dwUserid, BOOL bSecret, LPCTSTR lpMsgBuf, DWORD dwLen);
typedef DWORD(_cdecl * BRAC_SendTextMessage_Func)(DWORD dwUserid, BOOL bSecret, LPCTSTR lpMsgBuf, DWORD dwLen);
// ��ѯ�������������Ϣ
BRAC_API DWORD BRAC_QueryTransTaskInfo(DWORD dwUserid, DWORD dwTaskId, int infoname, char FAR* infoval, int infolen);
typedef DWORD(_cdecl * BRAC_QueryTransTaskInfo_Func)(DWORD dwUserid, DWORD dwTaskId, int infoname, char FAR* infoval, int infolen);
// ����SDK Filter ͨ������
BRAC_API DWORD BRAC_SendSDKFilterData(LPBYTE lpBuf, DWORD dwLen);
typedef DWORD(_cdecl * BRAC_SendSDKFilterData_Func)(LPBYTE lpBuf, DWORD dwLen);

// ��ѯָ���û����״̬
BRAC_API DWORD BRAC_QueryUserState(DWORD dwUserId, int infoname, char FAR* infoval, int infolen);
typedef DWORD(_cdecl * BRAC_QueryUserState_Func)(DWORD dwUserId, int infoname, char FAR* infoval, int infolen);

// �ͷ�������Դ
BRAC_API DWORD BRAC_Release(VOID);
typedef DWORD(_cdecl * BRAC_Release_Func)(VOID);


#ifdef __cplusplus
extern "C"
{
#endif /* __cplusplus */

        static HINSTANCE  g_BRACModule=NULL;
        static DWORD	g_dwBRACModule_Count = 0;

        static BRAC_InitSDK_Func					g_BRAC_InitSDK_Func = NULL;
        static BRAC_GetSDKVersion_Func				g_BRAC_GetSDKVersion_Func = NULL;
        static BRAC_ActiveCallLog_Func				g_BRAC_ActiveCallLog_Func = NULL;
        static BRAC_SetSDKOption_Func				g_BRAC_SetSDKOption_Func = NULL;
        static BRAC_SetVideoDataCallBack_Func		g_BRAC_SetVideoDataCallBack_Func = NULL;
        static BRAC_SetAudioDataCallBack_Func		g_BRAC_SetAudioDataCallBack_Func = NULL;
        static BRAC_SetVolumeChangeCallBack_Func    g_BRAC_SetVolumeChangeCallBack_Func = NULL;
        static BRAC_SetTransBufferExCallBack_Func	g_BRAC_SetTransBufferExCallBack_Func = NULL;
        static BRAC_SetTransBufferCallBack_Func		g_BRAC_SetTransBufferCallBack_Func = NULL;
        static BRAC_SetTransFileCallBack_Func		g_BRAC_SetTransFileCallBack_Func = NULL;
        static BRAC_SetRecordSnapShotCallBack_Func  g_BRAC_SetRecordSnapShotCallBack_Func = NULL;
        static BRAC_SetSDKFilterDataCallBack_Func   g_BRAC_SetSDKFilterDataCallBack_Func = NULL;
        static BRAC_AudioGetVolume_Func				g_BRAC_AudioGetVolume_Func = NULL;
        static BRAC_SetServerAuthPass_Func			g_BRAC_SetServerAuthPass_Func = NULL;
        static BRAC_Connect_Func					g_BRAC_Connect_Func = NULL;
        static BRAC_Login_Func						g_BRAC_Login_Func = NULL;
        static BRAC_SetNotifyMessageCallBack_Func	g_BRAC_SetNotifyMessageCallBack_Func = NULL;
        static BRAC_SetTextMessageCallBack_Func		g_BRAC_SetTextMessageCallBack_Func = NULL;
        static BRAC_GetUserName_Func				g_BRAC_GetUserName_Func = NULL;
        static BRAC_GetRoomName_Func				g_BRAC_GetRoomName_Func = NULL;
        static BRAC_EnterRoom_Func					g_BRAC_EnterRoom_Func = NULL;
        static BRAC_SetVideoPos_Func				g_BRAC_SetVideoPos_Func = NULL;
        static BRAC_UserCameraControl_Func			g_BRAC_UserCameraControl_Func = NULL;
        static BRAC_UserSpeakControl_Func			g_BRAC_UserSpeakControl_Func = NULL;
        static BRAC_AudioSetVolume_Func				g_BRAC_AudioSetVolume_Func = NULL;
        static BRAC_Release_Func					g_BRAC_Release_Func = NULL;
        static BRAC_EnterRoomEx_Func				g_BRAC_EnterRoomEx_Func = NULL;
        static BRAC_LeaveRoom_Func					g_BRAC_LeaveRoom_Func = NULL;
        static BRAC_GetOnlineUser_Func				g_BRAC_GetOnlineUser_Func = NULL;
        static BRAC_GetCameraState_Func				g_BRAC_GetCameraState_Func = NULL;
        static BRAC_Logout_Func						g_BRAC_Logout_Func = NULL;
        static BRAC_TransFile_Func					g_BRAC_TransFile_Func = NULL;
        static BRAC_QueryTransTaskInfo_Func			g_BRAC_QueryTransTaskInfo_Func = NULL;
        static BRAC_QueryUserState_Func				g_BRAC_QueryUserState_Func = NULL;
        static BRAC_TransBufferEx_Func				g_BRAC_TransBufferEx_Func = NULL;
        static BRAC_TransBuffer_Func				g_BRAC_TransBuffer_Func = NULL;
        static BRAC_CancelTransTask_Func			g_BRAC_CancelTransTask_Func = NULL;
        static BRAC_SendTextMessage_Func			g_BRAC_SendTextMessage_Func = NULL;
        static BRAC_ShowLVProperty_Func				g_BRAC_ShowLVProperty_Func = NULL;
        static BRAC_SelectVideoCapture_Func			g_BRAC_SelectVideoCapture_Func = NULL;
        static BRAC_SelectAudioCapture_Func			g_BRAC_SelectAudioCapture_Func = NULL;
        static BRAC_EnumVideoCapture_Func			g_BRAC_EnumVideoCapture_Func = NULL;
        static BRAC_GetCurVideoCapture_Func			g_BRAC_GetCurVideoCapture_Func = NULL;
        static BRAC_EnumAudioCapture_Func			g_BRAC_EnumAudioCapture_Func = NULL;
        static BRAC_GetCurAudioCapture_Func			g_BRAC_GetCurAudioCapture_Func = NULL;
        static BRAC_SendSDKFilterData_Func			g_BRAC_SendSDKFilterData_Func = NULL;
        static BRAC_StreamRecordCtrl_Func			g_BRAC_StreamRecordCtrl_Func = NULL;
        static BRAC_SnapShot_Func					g_BRAC_SnapShot_Func = NULL;



#ifdef WIN32
    #define BRAC_DLL_ASSIGN(_Name) \
    g_BRAC_##_Name##_Func = (BRAC_##_Name##_Func)GetProcAddress(g_BRACModule,"BRAC_"#_Name); \
        if (g_BRAC_##_Name##_Func == NULL) Errors++;
#else
    #define BRAC_DLL_ASSIGN(_Name) \
    g_BRAC_##_Name##_Func = (BRAC_##_Name##_Func)dlsym(g_BRACModule,"BRAC_"#_Name); \
        if (g_BRAC_##_Name##_Func == NULL) Errors++;
#endif

        static BOOL BRAC_DLL_Load(LPCTSTR szPathName)
        {
                if (g_dwBRACModule_Count > 0)
                {
                        g_dwBRACModule_Count++;
                        return TRUE;
                }

                /* Load library */
#ifdef WIN32
                g_BRACModule = LoadLibraryEx(szPathName,NULL,LOAD_WITH_ALTERED_SEARCH_PATH);
#else
        g_BRACModule = dlopen(szPathName,RTLD_NOW);
#endif
                if (!g_BRACModule)
                {
                        CHAR szErrorMsg[1024] = {0};
#ifdef WIN32
                        _snprintf(szErrorMsg,sizeof(szErrorMsg),"LoadLibrary %s Error:%d",szPathName,GetLastError());
#else
                        _snprintf(szErrorMsg,sizeof(szErrorMsg),"LoadLibrary %s Error:%s",szPathName,dlerror());
#endif
//...			TRACE(szErrorMsg);
                        return FALSE;
                }

                DWORD Errors=0;
                BRAC_DLL_ASSIGN(InitSDK);
                BRAC_DLL_ASSIGN(GetSDKVersion);
                BRAC_DLL_ASSIGN(ActiveCallLog);
                BRAC_DLL_ASSIGN(SetSDKOption);
                BRAC_DLL_ASSIGN(SetVideoDataCallBack);
                BRAC_DLL_ASSIGN(SetAudioDataCallBack);
                BRAC_DLL_ASSIGN(SetVolumeChangeCallBack);
                BRAC_DLL_ASSIGN(SetTransBufferExCallBack);
                BRAC_DLL_ASSIGN(SetTransBufferCallBack);
                BRAC_DLL_ASSIGN(SetTransFileCallBack);
                BRAC_DLL_ASSIGN(SetRecordSnapShotCallBack);
                BRAC_DLL_ASSIGN(SetSDKFilterDataCallBack);
                BRAC_DLL_ASSIGN(AudioGetVolume);
                BRAC_DLL_ASSIGN(SetServerAuthPass);
                BRAC_DLL_ASSIGN(Connect);
                BRAC_DLL_ASSIGN(Login);
                BRAC_DLL_ASSIGN(SetNotifyMessageCallBack);
                BRAC_DLL_ASSIGN(SetTextMessageCallBack);
                BRAC_DLL_ASSIGN(GetUserName);
                BRAC_DLL_ASSIGN(GetRoomName);
                BRAC_DLL_ASSIGN(EnterRoom);
                BRAC_DLL_ASSIGN(SetVideoPos);
                BRAC_DLL_ASSIGN(UserCameraControl);
                BRAC_DLL_ASSIGN(UserSpeakControl);
                BRAC_DLL_ASSIGN(AudioSetVolume);
                BRAC_DLL_ASSIGN(Release);
                BRAC_DLL_ASSIGN(EnterRoomEx);
                BRAC_DLL_ASSIGN(LeaveRoom);
                BRAC_DLL_ASSIGN(GetOnlineUser);
                BRAC_DLL_ASSIGN(GetCameraState);
                BRAC_DLL_ASSIGN(Logout);
                BRAC_DLL_ASSIGN(TransFile);
                BRAC_DLL_ASSIGN(QueryTransTaskInfo);
                BRAC_DLL_ASSIGN(QueryUserState);
                BRAC_DLL_ASSIGN(TransBufferEx);
                BRAC_DLL_ASSIGN(TransBuffer);
                BRAC_DLL_ASSIGN(CancelTransTask);
                BRAC_DLL_ASSIGN(SendTextMessage);
                BRAC_DLL_ASSIGN(ShowLVProperty);
                BRAC_DLL_ASSIGN(SelectVideoCapture);
                BRAC_DLL_ASSIGN(SelectAudioCapture);
                BRAC_DLL_ASSIGN(EnumVideoCapture);
                BRAC_DLL_ASSIGN(GetCurVideoCapture);
                BRAC_DLL_ASSIGN(EnumAudioCapture);
                BRAC_DLL_ASSIGN(GetCurAudioCapture);
                BRAC_DLL_ASSIGN(SendSDKFilterData);
                BRAC_DLL_ASSIGN(StreamRecordCtrl);
                BRAC_DLL_ASSIGN(SnapShot);

                if(Errors>0)
					return FALSE;

                g_dwBRACModule_Count++;
                return TRUE;
        }

        static BOOL BRAC_DLL_IsLoaded()
        {
                return g_BRACModule ? TRUE : FALSE;
        }

        static void BRAC_DLL_UnLoad()
        {
                g_dwBRACModule_Count--;
                if (g_dwBRACModule_Count > 0)
                        return;
#ifdef WIN32
                FreeLibrary(g_BRACModule);
#else
				dlclose(g_BRACModule);
#endif
                g_BRACModule=NULL;
        }

#ifdef __cplusplus
}
#endif /*__cplusplus*/


#ifdef __cplusplus


#define BRAC_TEST_VOID \
        if (!IsReady()) return
#define BRAC_TEST_INT \
        if (!IsReady()) return -1
#define BRAC_TEST_BOOL \
        if (!IsReady()) return FALSE


class CAnyChatCoreSDK
{
public:
        CAnyChatCoreSDK()
        {
			m_bSuccessLoadDll = FALSE;
        }
        CAnyChatCoreSDK(LPCTSTR szPathName)
        {
			m_bSuccessLoadDll = BRAC_DLL_Load(szPathName);
        }
        ~CAnyChatCoreSDK()
        {
            BRAC_TEST_VOID;
            BRAC_DLL_UnLoad();
            m_bSuccessLoadDll = FALSE;
        }
        BOOL LoadFilterDll(LPCTSTR szPathName)
        {
			return (m_bSuccessLoadDll = BRAC_DLL_Load(szPathName));
        }
        BOOL IsReady()
        {
			return m_bSuccessLoadDll;
        }

        // ��ʼ��ϵͳ
        DWORD InitSDK(HWND hWnd, DWORD dwFuncMode)
        {
			BRAC_TEST_INT;
            return g_BRAC_InitSDK_Func(hWnd, dwFuncMode);
        }
        // ��ȡ�汾��Ϣ
        DWORD GetSDKVersion(DWORD& dwMainVer, DWORD& dwSubVer, LPSTR lpCompileTime, DWORD dwBufLen)
        {
            BRAC_TEST_INT;
            return g_BRAC_GetSDKVersion_Func(dwMainVer, dwSubVer, lpCompileTime, dwBufLen);
        }
        // ����رգ�SDK������־
        DWORD ActiveCallLog(BOOL bActive)
        {
			BRAC_TEST_INT;
            return g_BRAC_ActiveCallLog_Func(bActive);
        }
        // SDK�ں˲�������
        DWORD SetSDKOption(int optname, const char FAR* optval, int optlen)
        {
			BRAC_TEST_INT;
            return g_BRAC_SetSDKOption_Func(optname, optval, optlen);
        }
        // ���÷�������֤���루��������ֹ�Ƿ��û���SDK���ӷ��������Ϸ��û����������ӣ�
        DWORD SetServerAuthPass(LPCTSTR lpPassword)
        {
			BRAC_TEST_INT;
            return g_BRAC_SetServerAuthPass_Func(lpPassword);
        }

        // ������Ƶ���ݻص�����
        DWORD SetVideoDataCallBack(BRAC_PixelFormat pixFmt, BRAC_VideoData_CallBack lpFunction, LPVOID lpUserValue)
        {
			BRAC_TEST_INT;
            return g_BRAC_SetVideoDataCallBack_Func(pixFmt, lpFunction, lpUserValue);
        }
        // ������Ƶ���ݻص�����
        DWORD SetAudioDataCallBack(BRAC_AudioData_CallBack lpFunction, LPVOID lpUserValue)
        {
			BRAC_TEST_INT;
            return g_BRAC_SetAudioDataCallBack_Func(lpFunction, lpUserValue);
        }
        // ���������仯�ص�����
        DWORD SetVolumeChangeCallBack(BRAC_VolumeChange_CallBack lpFunction, LPVOID lpUserValue)
        {
			BRAC_TEST_INT;
            return g_BRAC_SetVolumeChangeCallBack_Func(lpFunction, lpUserValue);
        }
        // ����͸��ͨ��������չ�ص�����
       DWORD SetTransBufferExCallBack(BRAC_TransBufferEx_CallBack lpFunction, LPVOID lpUserValue)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetTransBufferExCallBack_Func(lpFunction, lpUserValue);
       }
       //����͸��ͨ�����ݻص�����
       DWORD SetTransBufferCallBack(BRAC_TransBuffer_CallBack lpFunction, LPVOID lpUserValue)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetTransBufferCallBack_Func(lpFunction, lpUserValue);
       }
       // �����ļ�����ص�����
       DWORD SetTransFileCallBack(BRAC_TransFile_CallBack lpFunction, LPVOID lpUserValue)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetTransFileCallBack_Func(lpFunction, lpUserValue);
       }
       // ����¼������������֪ͨ�ص�����
       DWORD SetRecordSnapShotCallBack(BRAC_RecordSnapShot_CallBack lpFunction, LPVOID lpUserValue)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetRecordSnapShotCallBack_Func(lpFunction, lpUserValue);
       }
       // ����SDK Filterͨ�����ݻص���������
       DWORD SetSDKFilterDataCallBack(BRAC_SDKFilterData_CallBack lpFunction, LPVOID lpUserValue)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetSDKFilterDataCallBack_Func(lpFunction, lpUserValue);
       }
       // �����첽��Ϣ֪ͨ�ص�����
       DWORD SetNotifyMessageCallBack(BRAC_NotifyMessage_CallBack lpFunction, LPVOID lpUserValue)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetNotifyMessageCallBack_Func(lpFunction, lpUserValue);
       }
       // ����������Ϣ�ص�����
       DWORD SetTextMessageCallBack(BRAC_TextMessage_CallBack lpFunction, LPVOID lpUserValue)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetTextMessageCallBack_Func(lpFunction, lpUserValue);
       }


       // ���ӷ�����
       DWORD Connect(LPCTSTR lpServerAddr, DWORD dwPort)
       {
		   BRAC_TEST_INT;
           return g_BRAC_Connect_Func(lpServerAddr, dwPort);
       }
       // ��¼ϵͳ
       DWORD Login(LPCTSTR lpUserName, LPCTSTR lpPassword, DWORD dwPassEncType)
       {
		   BRAC_TEST_INT;
           return g_BRAC_Login_Func(lpUserName, lpPassword, dwPassEncType);
       }
       // ���뷿��
       DWORD EnterRoom(DWORD dwRoomid, LPCTSTR lpRoomPass, DWORD dwPassEncType)
       {
		   BRAC_TEST_INT;
           return g_BRAC_EnterRoom_Func(dwRoomid, lpRoomPass, dwPassEncType);
       }
       // ���뷿��
       DWORD EnterRoomEx(LPCTSTR lpRoomName, LPCTSTR lpRoomPass)
       {
		   BRAC_TEST_INT;
           return g_BRAC_EnterRoomEx_Func(lpRoomName, lpRoomPass);
       }
       // �뿪����
       DWORD LeaveRoom(DWORD dwRoomid)
       {
		   BRAC_TEST_INT;
           return g_BRAC_LeaveRoom_Func(dwRoomid);
       }
       // ע��ϵͳ
       DWORD Logout(VOID)
       {
		   BRAC_TEST_INT;
           return g_BRAC_Logout_Func();
       }

       // �����û���Ƶ
       DWORD UserCameraControl(DWORD dwUserid, BOOL bOpen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_UserCameraControl_Func(dwUserid, bOpen);
       }
       // �����û�����
       DWORD UserSpeakControl(DWORD dwUserid, BOOL bOpen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_UserSpeakControl_Func(dwUserid, bOpen);
       }
       // ������Ƶ��ʾλ��
       DWORD SetVideoPos(DWORD dwUserid, HWND hWnd, DWORD dwLeft, DWORD dwTop, DWORD dwRight, DWORD dwBottom)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SetVideoPos_Func(dwUserid, hWnd, dwLeft, dwTop, dwRight,dwBottom);
       }
       // ��ȡָ����Ƶ�豸�ĵ�ǰ����
       DWORD AudioGetVolume(BRAC_AudioDevice device, DWORD& dwVolume)
       {
		   BRAC_TEST_INT;
           return g_BRAC_AudioGetVolume_Func(device, dwVolume);
       }
       // ����ָ����Ƶ�豸������
       DWORD AudioSetVolume(BRAC_AudioDevice device, DWORD dwVolume)
       {
		   BRAC_TEST_INT;
           return g_BRAC_AudioSetVolume_Func(device,dwVolume);
       }

       // ö�ٱ�����Ƶ�ɼ��豸
       DWORD EnumVideoCapture(CHAR** lpDeviceName, DWORD& dwDeviceNum)
       {
		   BRAC_TEST_INT;
           return g_BRAC_EnumVideoCapture_Func(lpDeviceName, dwDeviceNum);
       }
       // ö�ٱ�����Ƶ�ɼ��豸
       DWORD EnumAudioCapture(CHAR** lpDeviceName, DWORD& dwDeviceNum)
       {
		   BRAC_TEST_INT;
           return g_BRAC_EnumAudioCapture_Func(lpDeviceName, dwDeviceNum);
       }
       // ѡ��ָ������Ƶ�ɼ��豸
       DWORD SelectVideoCapture(LPCTSTR szCaptureName)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SelectVideoCapture_Func(szCaptureName);
       }
       // ѡ��ָ������Ƶ�ɼ��豸
       DWORD SelectAudioCapture(LPCTSTR szCaptureName)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SelectAudioCapture_Func(szCaptureName);
       }
       // ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
       DWORD GetCurVideoCapture(CHAR* lpDeviceName, DWORD dwLen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_GetCurVideoCapture_Func(lpDeviceName, dwLen);
       }
       // ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
       DWORD GetCurAudioCapture(CHAR* lpDeviceName, DWORD dwLen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_GetCurAudioCapture_Func(lpDeviceName, dwLen);
       }

       // ��ȡ��ǰ���������û��б�
       DWORD GetOnlineUser(LPDWORD lpUserIDArray, DWORD& dwUserNum)
       {
		   BRAC_TEST_INT;
           return g_BRAC_GetOnlineUser_Func(lpUserIDArray, dwUserNum);
       }
       // ��ѯ�û�����ͷ��״̬
       DWORD GetCameraState(DWORD dwUserid, DWORD& dwState)
       {
		   BRAC_TEST_INT;
           return g_BRAC_GetCameraState_Func(dwUserid, dwState);
       }
       // ��ѯ�û�����
       DWORD GetUserName(DWORD dwUserid, CHAR* lpUserName, DWORD dwLen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_GetUserName_Func(dwUserid, lpUserName, dwLen);
       }
       // ��ѯ��������
       DWORD GetRoomName(DWORD dwRoomId, CHAR* lpRoomName, DWORD dwLen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_GetRoomName_Func(dwRoomId, lpRoomName, dwLen);
       }
       // ��ʾ������Ƶ������ڶԻ���
       DWORD ShowLVProperty(HWND hParent, LPCTSTR szCaption, DWORD dwX ,DWORD dwY)
       {
		   BRAC_TEST_INT;
           return g_BRAC_ShowLVProperty_Func(hParent, szCaption, dwX, dwY);
       }

       // �û�������Ƶ¼��
       DWORD StreamRecordCtrl(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam)
       {
		   BRAC_TEST_INT;
           return g_BRAC_StreamRecordCtrl_Func(dwUserId, bStartRecord, dwFlags, dwParam);
       }
       // ���û�����Ƶ����ץ�ģ����գ�
       DWORD SnapShot(DWORD dwUserId, DWORD dwFlags, DWORD dwParam)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SnapShot_Func(dwUserId, dwFlags, dwParam);
       }

       // ͸��ͨ�����ͻ�����
       DWORD TransBuffer(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_TransBuffer_Func(dwUserid, lpBuf, dwLen);
       }
       // ͸��ͨ�����ͻ�������չ
       DWORD TransBufferEx(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId)
       {
		   BRAC_TEST_INT;
           return g_BRAC_TransBufferEx_Func(dwUserid, lpBuf, dwLen, wParam, lParam, dwFlags, dwTaskId);
       }
       // �����ļ�
       DWORD TransFile(DWORD dwUserid, LPCTSTR lpLocalPathName, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId)
       {
		   BRAC_TEST_INT;
           return g_BRAC_TransFile_Func(dwUserid, lpLocalPathName, wParam, lParam, dwFlags, dwTaskId);
       }
       // ȡ����������
       DWORD  CancelTransTask(DWORD dwUserid, DWORD dwTaskId)
       {
		   BRAC_TEST_INT;
           return g_BRAC_CancelTransTask_Func(dwUserid, dwTaskId);
       }
       // �����ı���Ϣ
       DWORD SendTextMessage(DWORD dwUserid, BOOL bSecret, LPCTSTR lpMsgBuf, DWORD dwLen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SendTextMessage_Func(dwUserid, bSecret, lpMsgBuf, dwLen);
       }
       // ��ѯ�������������Ϣ
       DWORD QueryTransTaskInfo(DWORD dwUserid, DWORD dwTaskId, int infoname, char FAR* infoval, int infolen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_QueryTransTaskInfo_Func(dwUserid, dwTaskId, infoname, infoval, infolen);
       }
       // ����SDK Filter ͨ������
       DWORD SendSDKFilterData(LPBYTE lpBuf, DWORD dwLen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_SendSDKFilterData_Func(lpBuf, dwLen);
       }

       // ��ѯָ���û����״̬
       DWORD QueryUserState(DWORD dwUserId, int infoname, char FAR* infoval, int infolen)
       {
		   BRAC_TEST_INT;
           return g_BRAC_QueryUserState_Func(dwUserId, infoname, infoval, infolen);
       }

       // �ͷ�������Դ
       DWORD Release(VOID)
       {
		   BRAC_TEST_INT;
           return g_BRAC_Release_Func();
       }

private:
        BOOL	m_bSuccessLoadDll;
};




#endif /*__cplusplus*/






#endif // ANYCHATCORESDK_H
