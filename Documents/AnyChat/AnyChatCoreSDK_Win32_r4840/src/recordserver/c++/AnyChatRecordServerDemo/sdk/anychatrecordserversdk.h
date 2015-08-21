#if !defined(BR_ANYCHAT_RECORDSERVER_SDK_H__INCLUDED_)
#define BR_ANYCHAT_RECORDSERVER_SDK_H__INCLUDED_

#include "typedef.h"

/**
 *	AnyChat Record Server SDK Include File
 */


#pragma once


#define BRRS_API extern "C"

#define BRRS_SERVERAPPMSG_CONNECTED		1		///< ����AnyChat�������ɹ�
#define BRRS_SERVERAPPMSG_DISCONNECT	2		///< ��AnyChat�������Ͽ�����

// ��ѯ��Ϣ���Ͷ��壨API��BRRS_QueryUserInfo ���������
#define BRRS_USERINFO_TYPE_NICKNAME		1		///< �û���
#define BRRS_USERINFO_TYPE_VCODEC		10		///< ��Ƶ������
#define BRRS_USERINFO_TYPE_WIDTH		11		///< ��Ƶ���
#define BRRS_USERINFO_TYPE_HEIGHT		12		///< ��Ƶ�߶�
#define BRRS_USERINFO_TYPE_FRAMERATE	13		///< ��Ƶ֡��
#define BRRS_USERINFO_TYPE_ACODEC		20		///< ��Ƶ������
#define BRRS_USERINFO_TYPE_SAMPLERATE	21		///< ��Ƶ������
#define BRRS_USERINFO_TYPE_CHANNELS		22		///< ��Ƶ����ͨ����
#define BRRS_USERINFO_TYPE_SAMPLEBITS	23		///< ��Ƶ����λ��

// �����������Ͷ��壨API��BRRS_SetSDKOption ���������
#define BRRS_SO_USESERVERTIMESTAMP		1		///< ʹ�÷�����ʱ���

// �������ͱ�־���ص�������OnAnyChatRecordBufferCallBack ������
#define BRRS_RECORD_FLAGS_AUDIO			0x01	///< ��Ƶ����
#define BRRS_RECORD_FLAGS_VIDEO			0x02	///< ��Ƶ����
#define BRRS_RECORD_FLAGS_BUFFER		0x04	///< ͸��ͨ������������
#define BRRS_RECORD_FLAGS_BUFFEREX		0x08	///< ͸��ͨ����չ������
#define BRRS_RECORD_FLAGS_KEYFRAME		0x10	///< ��Ƶ�ؼ�֡
#define BRRS_RECORD_FLAGS_SILENCE		0x20	///< ��Ƶ����֡


// ������Ӧ�ó�����Ϣ�ص���������
typedef void (CALLBACK* BRRS_OnServerAppMessage_CallBack)(DWORD dwMsg, DWORD wParam, DWORD lParam, LPVOID lpUserValue);
// ��ʼ¼���¼��ص���������
typedef DWORD (CALLBACK* BRRS_OnRecordStart_CallBack)(DWORD dwUserId, LPVOID lpUserValue);
// ¼�������¼��ص���������
typedef DWORD (CALLBACK* BRRS_OnRecordBuffer_CallBack)(DWORD dwUserId, CHAR* lpBuf, DWORD dwSize, DWORD dwTimeStamp, DWORD dwFlags, LPVOID lpUserValue);
// ����¼���¼��ص���������
typedef DWORD (CALLBACK* BRRS_OnRecordFinish_CallBack)(DWORD dwUserId, CHAR* lpFileName, DWORD dwSize, DWORD* lpRecordSeconds, LPVOID lpUserValue);

/**
 *	API ��������
 */
// ���÷�����Ӧ�ó�����Ϣ�ص�����
BRRS_API DWORD BRRS_SetOnServerAppMessageCallBack(BRRS_OnServerAppMessage_CallBack lpFunction, LPVOID lpUserValue=NULL);
// ���ÿ�ʼ¼���¼��ص�����
BRRS_API DWORD BRRS_SetOnRecordStartCallBack(BRRS_OnRecordStart_CallBack lpFunction, LPVOID lpUserValue=NULL);
// ����¼�������¼��ص�����
BRRS_API DWORD BRRS_SetOnRecordBufferCallBack(BRRS_OnRecordBuffer_CallBack lpFunction, LPVOID lpUserValue=NULL);
// ����¼������¼��ص�����
BRRS_API DWORD BRRS_SetOnRecordFinishCallBack(BRRS_OnRecordFinish_CallBack lpFunction, LPVOID lpUserValue=NULL);

// ��ȡSDK�汾��Ϣ
BRRS_API DWORD BRRS_GetSDKVersion(DWORD& dwMainVer, DWORD& dwSubVer, TCHAR* lpCompileTime, DWORD dwBufLen);
// ��ʼ��SDK
BRRS_API DWORD BRRS_InitSDK(DWORD dwReserved);
// �ͷ���Դ
BRRS_API DWORD BRRS_Release(void);


// ��ѯ�û���Ϣ
BRRS_API DWORD BRRS_QueryUserInfo(DWORD dwUserId, DWORD dwInfoName, CHAR* lpValue, DWORD dwSize);
// SDK�ں˲�������
BRRS_API DWORD BRRS_SetSDKOption(DWORD optname, CHAR* optval, DWORD optlen);




#endif //BR_ANYCHAT_RECORDSERVER_SDK_H__INCLUDED_
