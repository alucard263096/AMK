// AnyChatVideoMixerDlg.h : header file
//

#if !defined(AFX_ANYCHATVIDEOMIXERDLG_H__F175C8C7_32D2_4587_9B87_C4BC146C5754__INCLUDED_)
#define AFX_ANYCHATVIDEOMIXERDLG_H__F175C8C7_32D2_4587_9B87_C4BC146C5754__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#pragma warning(disable:4819)
#pragma warning(disable:4786)

#include <map>
#include <process.h>

#define MAX_MIX_VIDEO_NUM		16			///< �������Ƶ����

typedef struct USER_ITEM_STRUCT
{
	DWORD dwUserId;
	DWORD dwWidth;
	DWORD dwHeight;
	DWORD dwImageSize;
	CHAR* lpImageBuf;
	DWORD dwVideoActiveTime;

	DWORD dwAudioBufSize;
	CHAR* lpAudioBuf;
	DWORD dwAudioOffset;

}USER_ITEM, *LPUSER_ITEM;

typedef struct MIX_RECT_STRUCT
{
	DOUBLE X;
	DOUBLE Y;
	DOUBLE Width;
	DOUBLE Height;
}MIX_RECT, *LPMIX_RECT;

typedef struct MIX_SCHEME_STRUCT  
{
	MIX_RECT	rect[MAX_MIX_VIDEO_NUM];
}MIX_SCHEME, *LPMIX_SCHEME;


class CAnyChatVideoMixerDlg : public CDialog
{
// Construction
public:
	CAnyChatVideoMixerDlg(CWnd* pParent = NULL);	// standard constructor


public:
	CString		m_strLogInfo;			///< ������־��Ϣ
	BOOL		m_bInRoom;				///< �Ƿ��ѽ��뷿��

	std::map<DWORD, LPUSER_ITEM>	m_UserItemMap;
	CRITICAL_SECTION	m_hMapSection;

	HANDLE		m_hThread;
	BOOL		m_bStopThread;

	DWORD		m_dwMixImageSize;
	CHAR*		m_lpMixVideoBuf;

	DWORD		m_dwLastVideoInputTime;

	DWORD		m_dwAudioStartTime;
	DWORD		m_dwAlreadInputAudioSamples;

	MIX_SCHEME	m_MixSchemes[MAX_MIX_VIDEO_NUM];
	DWORD		m_dwSelectScheme;		///< ��ǰָ���ĺϳɷ�����Ϊ-1��ʾ�Զ�


public:
	// ��ʾ��־��Ϣ
	void AppendLogString(CString logstr);
	// �ͷ��û���Ԫ��Դ
	void ReleaseUserItem(LPUSER_ITEM lpUserItem);
	// ����һ���µ��û���Ԫ
	void CreateNewUserItem(DWORD dwUserId);
	// ��ָ���û�����Ƶ��ָ���ķ����ϳ�
	void MixUserVideoByScheme(LPUSER_ITEM lpUserItem, DWORD dwSchemeIndex, DWORD dwSiteIndex);
	// ��ʼ����Ƶ��Ϸ���
	void InitMixScheme(void);

	// ���ñ�����Ƶ����
	void SetLocalVideoParamters(void);
	// ��������û���Ԫ����
	void ClearAllUserItem(void);

	// ��Ƶ���ݻ���
	void AudioBufferMixUpdate(LONG* lpMixBuffer, DWORD dwFrameNum, SHORT* lpDesBuffer);


// Dialog Data
	//{{AFX_DATA(CAnyChatVideoMixerDlg)
	enum { IDD = IDD_ANYCHATVIDEOMIXER_DIALOG };
	CEdit	m_ctrlEditLog;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAnyChatVideoMixerDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CAnyChatVideoMixerDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnButtonStart();
	afx_msg void OnDestroy();
	afx_msg void OnButtonConfig();
	afx_msg LRESULT OnGVClientConnect(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientLogin(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientEnterRoom(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientOnlineUser(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientUserAtRoom(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientLinkClose(WPARAM wParam, LPARAM lParam);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ANYCHATVIDEOMIXERDLG_H__F175C8C7_32D2_4587_9B87_C4BC146C5754__INCLUDED_)
