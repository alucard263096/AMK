// AnyChatInputVideoDlg.cpp : implementation file
//

#include "stdafx.h"
#include "AnyChatInputVideo.h"
#include "AnyChatInputVideoDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif


#define ANYCHAT_INPUTVIDEO_TIMER		1

#define INPUTVIDEO_WIDTH				320
#define INPUTVIDEO_HEIGHT				240
#define INPUTVIDEO_FPS					20

CAnyChatInputVideoDlg::CAnyChatInputVideoDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CAnyChatInputVideoDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CAnyChatInputVideoDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CAnyChatInputVideoDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAnyChatInputVideoDlg)
	DDX_Control(pDX, IDC_EDIT_LOG, m_ctrlEditLog);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CAnyChatInputVideoDlg, CDialog)
	//{{AFX_MSG_MAP(CAnyChatInputVideoDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_BN_CLICKED(IDC_BUTTON_INIT, OnButtonInit)
	ON_WM_DESTROY()
	ON_BN_CLICKED(IDC_BUTTON_START, OnButtonStart)
	ON_BN_CLICKED(IDC_BUTTON_CONNECT, OnButtonConnect)
	ON_WM_TIMER()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CAnyChatInputVideoDlg message handlers

BOOL CAnyChatInputVideoDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	m_bSuccessEnterRoom = FALSE;
	m_lpVideoBuf = NULL;
	m_dwRemoteUserId = -1;
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CAnyChatInputVideoDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

HCURSOR CAnyChatInputVideoDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}



void CAnyChatInputVideoDlg::OnDestroy() 
{
	CDialog::OnDestroy();
	// �ͷ���Դ
	BRAC_Release();	
	if(m_lpVideoBuf)
	{
		free(m_lpVideoBuf);
	}
}

/**
 *	��Ƶ������չ�ص�������ʱ�����
 *	@param dwUserid �û�ID��
 *	@param lpBuf ��Ƶ���ݻ�����
 *	@param dwLen ��������С
 *	@param bmiHeader ��Ƶ��������ͷ��Ϣ����������Ƶ�Ĵ�С����ɫλ������Ϣ
 *	@param dwTimeStamp ʱ���
 */
void CALLBACK OnAnyChatVideoDataExCallBack(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, BITMAPINFOHEADER bmiHeader, DWORD dwTimeStamp, LPVOID lpUserValue)
{
	TRACE("VideoDataExCallBack: userid:%d, timestamp:%d\r\n", (int)dwUserid, dwTimeStamp);
	CAnyChatInputVideoDlg* lpDlg = (CAnyChatInputVideoDlg*)lpUserValue;
	CRect dispRect;
	HWND hWnd;
	if(lpDlg->m_dwRemoteUserId == dwUserid && dwUserid != -1)					// Զ���û�
	{
		hWnd = lpDlg->GetDlgItem(IDC_STATIC_REMOTEVIDEO)->GetSafeHwnd();
		lpDlg->GetDlgItem(IDC_STATIC_REMOTEVIDEO)->GetClientRect(dispRect);
	}
	else																// �����û�
	{
		hWnd = lpDlg->GetDlgItem(IDC_STATIC_LOCALVIDEO)->GetSafeHwnd();
		lpDlg->GetDlgItem(IDC_STATIC_LOCALVIDEO)->GetClientRect(dispRect);
	}
	
	//����Bitmap  
	BITMAPINFOHEADER  *lpbmi = (BITMAPINFOHEADER*)malloc(sizeof(BITMAPINFOHEADER)+dwLen);  
	ZeroMemory(lpbmi,sizeof(BITMAPINFOHEADER)+dwLen);
    memcpy((LPBYTE)lpbmi,&bmiHeader,sizeof(BITMAPINFOHEADER));
	memcpy(((LPBYTE)lpbmi+sizeof(BITMAPINFOHEADER)),lpBuf,dwLen);  
	
	HDC hdc = ::GetDC(hWnd);
	HDC dcMem = ::CreateCompatibleDC(hdc);  
	HBITMAP hBitmap = CreateDIBitmap(hdc,lpbmi,CBM_INIT,(LPBYTE)lpbmi+sizeof(BITMAPINFOHEADER),(LPBITMAPINFO)lpbmi,DIB_RGB_COLORS);  
	//��ͼ  
	HBITMAP holdbm = (HBITMAP)SelectObject(dcMem,hBitmap);  
	::SetStretchBltMode(hdc,COLORONCOLOR);
	::StretchBlt(hdc,dispRect.left,dispRect.top,dispRect.Width(),dispRect.Height(),dcMem,0,0,bmiHeader.biWidth,bmiHeader.biHeight,SRCCOPY);
	
	SelectObject(dcMem,holdbm);  
	::DeleteDC(dcMem);
	::DeleteObject(hBitmap);
	::ReleaseDC(hWnd,hdc);
	free(lpbmi);
	lpbmi = NULL;
}

// ��Ƶ������չ�ص��������壨����ʱ�����
void CALLBACK OnAnyChatAudioDataExCallBack(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, WAVEFORMATEX waveFormatEx, DWORD dwTimeStamp, LPVOID lpUserValue)
{

}

void CAnyChatInputVideoDlg::OnButtonInit() 
{
	// ��ȡSDK�İ汾��Ϣ
	DWORD dwMainVer,dwSubVer;
	CHAR szCompileTime[100] = {0};
	BRAC_GetSDKVersion(dwMainVer,dwSubVer,szCompileTime,sizeof(szCompileTime));
	
	CString logstr;
	logstr.Format("AnyChat Core SDK Version:%d.%d(%s)",dwMainVer,dwSubVer,szCompileTime);
	AppendLogString(logstr);
	
	// �򿪣��رգ�SDK����־��¼����
	BRAC_ActiveCallLog(TRUE);
	
	// ����SDK�����������Ŀ¼��ע��demo����ֻ������Ϊ��ǰĿ¼����Ŀ����Ҫ����Ϊʵ��·����
	CHAR szCoreSDKPath[MAX_PATH] = {0};
	GetModuleFileName(NULL,szCoreSDKPath,sizeof(szCoreSDKPath));
	(strrchr(szCoreSDKPath,'\\'))[1] = 0; 
	BRAC_SetSDKOption(BRAC_SO_CORESDK_PATH,szCoreSDKPath,strlen(szCoreSDKPath));
	
	// ����BRAC_InitSDK�ĵڶ���������dwFuncMode��������SDK����δ�����ص�����������ο������ĵ���
	DWORD dwFuncMode = BRAC_FUNC_VIDEO_CBDATA/*BRAC_FUNC_VIDEO_AUTODISP*/ | BRAC_FUNC_AUDIO_AUTOPLAY /*BRAC_FUNC_AUDIO_CBDATA*/ | BRAC_FUNC_CHKDEPENDMODULE | 
		BRAC_FUNC_AUDIO_VOLUMECALC | BRAC_FUNC_NET_SUPPORTUPNP | BRAC_FUNC_FIREWALL_OPEN |
		BRAC_FUNC_AUDIO_AUTOVOLUME | BRAC_FUNC_CONFIG_LOCALINI;
	BRAC_InitSDK(this->GetSafeHwnd(),dwFuncMode);
	
	
	// �����ⲿ��Ƶ����Ƶ����ģʽ
	BOOL bExtVideoInput = 1;
	BRAC_SetSDKOption(BRAC_SO_CORESDK_EXTVIDEOINPUT, (CHAR*)&bExtVideoInput, sizeof(DWORD));
//	BOOL bExtAudioInput = 1;
//	BRAC_SetSDKOption(BRAC_SO_CORESDK_EXTAUDIOINPUT, (CHAR*)&bExtAudioInput, sizeof(DWORD));

	// ������չ�ص���������ʱ�����
	BRAC_SetVideoDataExCallBack(BRAC_PIX_FMT_RGB24, OnAnyChatVideoDataExCallBack, this);
	BRAC_SetAudioDataExCallBack(OnAnyChatAudioDataExCallBack, this);
}

void CAnyChatInputVideoDlg::OnButtonConnect() 
{
	BRAC_Connect("demo.anychat.cn", 8906);
	BRAC_Login("demo-user", "", 0);
	BRAC_EnterRoom(1, "", 0);
}

void CAnyChatInputVideoDlg::OnButtonStart() 
{
	if(!m_bSuccessEnterRoom)
		return;
	// ����������Ƶ��ʽ
	BRAC_SetInputVideoFormat(BRAC_PIX_FMT_RGB24, INPUTVIDEO_WIDTH, INPUTVIDEO_HEIGHT, INPUTVIDEO_FPS, 0);
	// ���仺������Դ
	if(!m_lpVideoBuf)
	{
		m_lpVideoBuf = (BYTE*)malloc(INPUTVIDEO_WIDTH * INPUTVIDEO_HEIGHT * 3);
		if(!m_lpVideoBuf)
		{
			AppendLogString("���仺����ʧ�ܣ�");
			return;
		}
	}
	// ������Ƶ���붨ʱ��
	SetTimer(ANYCHAT_INPUTVIDEO_TIMER, 1000/INPUTVIDEO_FPS, NULL);
}

// ��¼��־���б�
void CAnyChatInputVideoDlg::AppendLogString(CString str)
{
	m_strLog += (str + "\r\n");
	m_ctrlEditLog.SetWindowText(m_strLog);
	m_ctrlEditLog.LineScroll(m_ctrlEditLog.GetLineCount());
}


// ���ӷ�������Ϣ
void CAnyChatInputVideoDlg::OnAnyChatConnectMessage(BOOL bSuccess)
{
	if(bSuccess)
		AppendLogString("����AnyChat�������ɹ���");
	else
		AppendLogString("����AnyChat������ʧ�ܣ�");
}
// �û���½��Ϣ
void CAnyChatInputVideoDlg::OnAnyChatLoginMessage(DWORD dwUserId, DWORD dwErrorCode)
{
	CString str;
	if(dwErrorCode == 0)
		str.Format("��¼AnyChat�������ɹ����û�ID��%d", (int)dwUserId);
	else
		str.Format("��¼AnyChat������ʧ�ܣ�������룺%d", dwErrorCode);
	AppendLogString(str);
}
// �û����뷿����Ϣ
void CAnyChatInputVideoDlg::OnAnyChatEnterRoomMessage(DWORD dwRoomId, DWORD dwErrorCode)
{
	CString str;
	if(dwErrorCode == 0)
	{
		CWnd* pWnd = GetDlgItem(IDC_STATIC_LOCALVIDEO);
		CRect rc;
		pWnd->GetClientRect(rc);
		BRAC_SetVideoPos(-1, pWnd->GetSafeHwnd(), rc.left, rc.top, rc.right, rc.bottom);
		
		str.Format("����AnyChat����ɹ�������ID��%d", (int)dwRoomId);
		m_bSuccessEnterRoom = TRUE;

		//����Ƶ�豸
		BRAC_UserSpeakControl(-1, TRUE);
		//����Ƶ�豸�����������ⲿ��Ƶ����ģʽ���������Ҫ�򿪱�����Ƶ�豸��
		//BRAC_UserCameraControl(-1, TRUE);
	}
	else
	{
		str.Format("����AnyChat����ʧ�ܣ�������룺%d", dwErrorCode);
		m_bSuccessEnterRoom = FALSE;
	}
	AppendLogString(str);
}
//����Ͽ���Ϣ
void CAnyChatInputVideoDlg::OnAnyChatLinkCloseMessage(DWORD dwErrorCode)
{
	CString str;
	str.Format("��AnyChat���������ӶϿ���������룺%d", dwErrorCode);
	AppendLogString(str);
	m_bSuccessEnterRoom = FALSE;
	m_dwRemoteUserId = -1;
}


void CAnyChatInputVideoDlg::OnTimer(UINT nIDEvent) 
{
	switch (nIDEvent)
	{
	case ANYCHAT_INPUTVIDEO_TIMER:
		if(m_lpVideoBuf)
		{
			static int f = 0;
			DWORD dwImageSize = INPUTVIDEO_WIDTH*INPUTVIDEO_HEIGHT*3;
			memset(m_lpVideoBuf, f, dwImageSize);
			f += 5;
			// ����������Ƶ֡����AnyChat
			DWORD dwTimeStamp = GetTickCount();			// ��������timestampΪ0�������AnyChat�ں˵�ʱ���
			BRAC_InputVideoData(m_lpVideoBuf, dwImageSize, dwTimeStamp);
		}
		break;
	default:
		break;
	}
	CDialog::OnTimer(nIDEvent);
}

/**
 *	�յ���ǰ����������û���Ϣ
 *	���뷿��󴥷�һ��
 *	@param dwUserNum ��INT����ʾ�����û������������Լ���
 *	@param dwRoomId ��INT����ʾ����ID
 */
void CAnyChatInputVideoDlg::OnAnyChatOnlineUserMessage(DWORD dwUserNum, DWORD dwRoomId)
{
	CString logstr;
	logstr.Format(_T("�ڱ��Ϊ��%d �ķ��乲�� %d λ�����û�"),dwRoomId, dwUserNum);
	AppendLogString(logstr);
		
	LPDWORD lpdwUserList = (LPDWORD)malloc(sizeof(DWORD)*dwUserNum);
	BRAC_GetOnlineUser(lpdwUserList,dwUserNum);	///< ������ȡ�����û��б�
	
	ASSERT(m_dwRemoteUserId == -1);
	for(INT i=0; i< (INT)dwUserNum; i++)
	{
		m_dwRemoteUserId = lpdwUserList[i];
//		BRAC_SetVideoPos(m_dwRemoteUserId,m_hWnd,m_UserRect[site].left,m_UserRect[site].top,m_UserRect[site].right,m_UserRect[site].bottom);
		BRAC_UserCameraControl(m_dwRemoteUserId,TRUE);
		BRAC_UserSpeakControl(m_dwRemoteUserId,TRUE);
		break;
	}
	free(lpdwUserList);
}

// �û�����/�˳�������Ϣ
void CAnyChatInputVideoDlg::OnAnyChatUserAtRoomMessage(DWORD dwUserId, BOOL bEnter)
{
	if(!bEnter || m_dwRemoteUserId != -1)		// �û��뿪���䣬�����Ѿ�������ĳ���û�����Ƶ������Ը���Ϣ
		return;
	m_dwRemoteUserId = dwUserId;
	BRAC_UserCameraControl(m_dwRemoteUserId,TRUE);
	BRAC_UserSpeakControl(m_dwRemoteUserId,TRUE);
}

/**
 *	��Ƶ���ݻص�
 *	@param dwUserid �û�ID��
 *	@param lpBuf ��Ƶ���ݻ�����
 *	@param dwLen ��������С
 *	@param bmiHeader ��Ƶ��������ͷ��Ϣ����������Ƶ�Ĵ�С����ɫλ������Ϣ
 */
void CAnyChatInputVideoDlg::OnAnyChatVideoDataCallBack(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, LPBITMAPINFOHEADER lpbmiHeader)
{
	// ��������OnAnyChatVideoDataExCallBack���洦��
}

