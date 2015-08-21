// Hello AnyChatDlg.h : header file
//

#if !defined(AFX_HELLOANYCHATDLG_H__AA05E38F_DB33_48AD_B0D1_59FFB4A63E1F__INCLUDED_)
#define AFX_HELLOANYCHATDLG_H__AA05E38F_DB33_48AD_B0D1_59FFB4A63E1F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "AnyChatSDK/include/BRAnyChatCoreSDK.h"
#include "AnyChatSDK/include/GVMessageDefine.h"
#pragma comment(lib,"AnyChatSDK/lib/BRAnyChatCore.lib")

#include <vector>
#include "SkinBtn.h"
#include "VideoSetDlg.h"
using namespace std;

#define DEMO_SHOW_USER_NUM 2
#define USERNAMELENGTH_MAX 30
struct CUserMsg
{
	INT uerID;

	CHAR userName[USERNAMELENGTH_MAX];
};

class CHelloAnyChatDlg : public CDialog
{
// Construction
public:
	// index����0��ʾ�û��Լ���1��ʾ���Լ�����Ƶ�ĶԷ�
	INT m_iUserID[2]; 
	// index����0��ʾ�û��Լ���1��ʾ���Լ�����Ƶ�ĶԷ�
	CRect m_UserRect[2];
	// ���Ե��û�
	vector <CUserMsg> m_vecAllUser;
	CBitmap mBitmap[4];
	CImageList mImagelist;
    VideoSetDlg* mVideoSetDlg;
	bool mbAudioSwitch;    // �������ƿ��ر���(0��ʾ���ˣ�1��ʾ�ر���)
public:
	// д���¼����
	void writeLoginDataFile();

	CHelloAnyChatDlg(CWnd* pParent = NULL);	// standard constructor
	// ��ʼ��UI
	void initUI();	
	// ��ʼ��SDK
	void initSDK(); 
	// �����û���Ƶ
	void DrawUserVideo(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, BITMAPINFOHEADER bmiHeader);
	// ���뷿��
	void EnterRoom();
	// ˢ���û��б�
	void updateUserList();

// Dialog Data
	//{{AFX_DATA(CHelloAnyChatDlg)
	enum { IDD = IDD_HELLOANYCHAT_DIALOG };
	CButton	m_videoBtn;
	CButton	m_audioBtn;
	CListCtrl	m_userList;
	CButton		m_logoutBtn;
	CButton		m_loginBtn;
	int			m_port;
	int			m_iRoomID;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CHelloAnyChatDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CHelloAnyChatDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg LRESULT OnGVClientConnect(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientLogin(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientEnterRoom(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientOnlineUser(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientUserAtRoom(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnGVClientLinkClose(WPARAM wParam, LPARAM lParam);
	afx_msg void OnLogin();
	afx_msg void OnLogout();
	afx_msg void OnClickListUserList(NMHDR* pNMHDR, LRESULT* pResult);
	afx_msg void OnButtonSetting();
	afx_msg void OnButtonVideoCtrl();
	afx_msg void OnButtonAudioCtrl();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_HELLOANYCHATDLG_H__AA05E38F_DB33_48AD_B0D1_59FFB4A63E1F__INCLUDED_)
