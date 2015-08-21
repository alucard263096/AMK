// SDKFilterPlus.cpp : Defines the entry point for the DLL application.
//

#include "stdafx.h"
#include <stdlib.h>
#include <stdio.h>


#define BRFP_API extern "C" __declspec(dllexport)

#define BRFP_VERSION	"2.0"

// SDK Filter Plus ���ݴ���ص��������壨�������Ὣ�������ڵ����ݴ����ָ�����û���
typedef void (CALLBACK * BRFP_FilterDataCallBack)(DWORD dwUserId, DWORD dwHandle, LPCSTR lpBuf, DWORD dwLen, LPVOID lpUserValue);

BRFP_FilterDataCallBack	g_fnSendFilterData = NULL;
LPVOID	g_lpFilterUserData = NULL;

BOOL APIENTRY DllMain( HANDLE hModule, 
                       DWORD  ul_reason_for_call, 
                       LPVOID lpReserved
					 )
{
    return TRUE;
}

/**
 *	��ȡSDK����汾��Ϣ
 *	@param lpVersion ���ز���İ汾��Ϣ
 *	@param dwVSLen �汾��Ϣ����������
 */
BRFP_API void BRFP_GetVersion(OUT LPTSTR lpVersion,IN DWORD dwVSLen)
{
#if(_MFC_VER >= 0x0700)
	_snprintf_s(lpVersion,dwVSLen,_TRUNCATE,"%s",BRFP_VERSION);
#else
	_snprintf(lpVersion,dwVSLen,"%s",BRFP_VERSION);
#endif
}


/**
 *	��ʼ��SDK Filter
 *	�����ڸ÷����ж�ȡ��ص����ò������������ݿ����ӵȳ�ʼ������
 *	@param lpFunc �ص�������ַ��SDK Filter��������Ҫ����ʱ����ͨ���ûص��������ݸ�Ӧ�ò�
 *	@param lpUserValue �ص�������������
 *	@return ��ʼ���ɹ�����TRUE�����򷵻�FALSE
 */
BRFP_API BOOL BRFP_InitFilter(BRFP_FilterDataCallBack lpFunc, LPVOID lpUserValue)
{
	g_fnSendFilterData = lpFunc;
	g_lpFilterUserData = lpUserValue;
	return TRUE;
}

/**
 *	��֤�û����
 *	@param lpUserName �����û���
 *	@param lpPassword �����û�����
 *	@param lpUserID ������û���ID�ţ�ע�⣺��ID�ű���Ψһ�������ظ���
 *	@param lpUserLevel ������û��ļ���
 *	@param lpNickName ������û����ǳ�
 *	@param dwNCLen �����û��ǳƻ������ĳ���
 *	@return ���س����룬�ɹ����� GV_ERR_SUCCESS�����򷵻ض�Ӧ�Ĵ������
 */
BRFP_API DWORD BRFP_VerifyUser(IN LPCTSTR lpUserName,IN LPCTSTR lpPassword, OUT LPDWORD lpUserID, OUT LPDWORD lpUserLevel, OUT LPTSTR lpNickName,IN DWORD dwNCLen)
{
	static DWORD dwUserIdSeed = 0;
	DWORD dwCurrentId = ++dwUserIdSeed;
	*lpUserID = dwCurrentId;
	*lpUserLevel = 0;
	
#if(_MFC_VER >= 0x0700)
	_snprintf_s(lpNickName,dwNCLen,_TRUNCATE,"DemoUser_%d",dwCurrentId);
#else
	_snprintf(lpNickName,dwNCLen,"DemoUser_%d",dwCurrentId);
#endif

	return 0;
}

/**
 *	�û����뷿������
 *	SDK Filter��Ҫ�жϸ�����ĺϷ��ԣ����������ݷ���ֵ�����Ƿ����û����뷿��
 *	���ͻ��˵���BRAC_EnterRoom�����ݷ���ID���뷿�䣩���뷿��ʱ��dwRoomId��lpPassword������Ч
 *	���ͻ��˵���BRAC_EnterRoomEx�����ݷ��������뷿�䣩���뷿��ʱ��lpRoomName��lpPassword������Ч��Щʱ��dwRoomId���ɷ������Զ������
 *	@param dwUserId �������û�ID
 *	@param dwRoomId �������ķ�����
 *	@param lpRoomName �������ķ�����
 *	@param lpPassword ��������
 *	@return  ���س����룬�ɹ���������뷿�䣩����GV_ERR_SUCCESS�����򷵻ض�Ӧ�Ĵ������
 */
BRFP_API DWORD BRFP_PrepareEnterRoom(DWORD dwUserId, DWORD dwRoomId, LPCTSTR lpRoomName,LPCTSTR lpPassword)
{
	return 0;
}


/**
 *	�û���¼����
 *	�û���¼ϵͳ�ɹ�֮�󣬷���������ø÷�����֪ͨSDK Filter���û���¼
 */
BRFP_API void BRFP_OnUserLoginAction(DWORD dwUserId, LPCTSTR szUserName, DWORD dwLevel, LPCTSTR szIpAddr, DWORD dwHandle)
{
	// �����Ҫ����dwUserId��dwHandle�Ķ�Ӧ��ϵ����Ϊ���û��������ݣ�ʹ�ûص����������ʱ��Ҫ����ò���


	// ��Ϊ��ʾ�����û���¼ʱ������һ����ӭ��Ϣ���û�
	CHAR szWelcomeMsg[] = "Welcome to use BaiRuiTech products!";
	if(g_fnSendFilterData)
		g_fnSendFilterData(dwUserId,dwHandle,szWelcomeMsg,strlen(szWelcomeMsg),g_lpFilterUserData);
}


/**
 *	�û�ע������
 *	�û���ϵͳע������������Ͽ�֮ʱ������������ø÷�����֪ͨSDK Filter���û��뿪ϵͳ
 */
BRFP_API void BRFP_OnUserLogoutAction(DWORD dwUserId)
{

}


/**
 *	�û����뷿�䶯��
 *	�û����뷿��ɹ�֮�󣬷���������ø÷�����֪ͨSDK Filter���û����뷿��
 */
BRFP_API void BRFP_OnUserEnterRoomAction(DWORD dwUserId, DWORD dwRoomId)
{

}


/**
 *	�û��뿪���䶯��
 *	�û��ӷ����뿪֮ʱ������������ø÷�����֪ͨSDK Filter���û��뿪����
 */
BRFP_API void BRFP_OnUserLeaveRoomAction(DWORD dwUserId, DWORD dwRoomId)
{

}


/**
 *	�յ��û����͹�����ͨ�����ݰ�
 */
BRFP_API void BRFP_OnRecvUserFilterData(DWORD dwUserId, LPCSTR lpBuf, DWORD dwLen)
{

}
/**
 *	�յ��û���������ͨ������
 */
BRFP_API void BRFP_OnRecvUserTextMsg(DWORD dwRoomId, DWORD dwSrcUserId, DWORD dwTarUserId, BOOL bSecret, LPCTSTR lpTextMessage, DWORD dwLen)
{

}


/**
 *	�ͷ�SDK Filter��Դ
 *	�ڷ������˳�ʱ����
 */
BRFP_API void BRFP_ReleaseFilter(void)
{
	g_fnSendFilterData = NULL;
	g_lpFilterUserData = NULL;
}
