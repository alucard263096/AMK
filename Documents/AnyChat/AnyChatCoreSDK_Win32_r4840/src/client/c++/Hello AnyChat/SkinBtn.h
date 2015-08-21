/************************************************************************ 
* �ļ�����    SkinBtn.h 
* �ļ�������  ͼƬ��ť 
* �����ˣ�    ������(Nuk), 2006��08��22��
* �汾�ţ�    1.0 
************************************************************************/ 

#pragma once
//#include <atlimage.h>       

#define BUTTON_SCROLL  1
#define BUTTON_TITLE   2
#define BUTTON_NOPICTURE  3
#define BUTTON_DEFAULT   0


// CSkinBtn
typedef BOOL (WINAPI *fListMoveWnd)(CPoint&,int,LPARAM);
class CSkinBtn : public CButton
{
	DECLARE_DYNAMIC(CSkinBtn)

public:
	CSkinBtn();
	virtual ~CSkinBtn();

	typedef enum state
	{
		NORMAL,
		HOVER,
		DOWN,
		DISABLE
	}state;
    void SetParentWnd(HWND hWnd);
	BOOL MoveWndBegin(fListMoveWnd lpfListMoveWnd,LPARAM lPARAM);	
	void SetRantageDraw(BOOL bDraw);
	void SetScrollDraw(int bDrawint ,int nNum);
	void SetMovePain(BOOL bDraw);


	void SetCurrentShow(long nCode)
	{
		m_ShowState = nCode;
	}

protected:
	DECLARE_MESSAGE_MAP()

	CBitmap  m_imgNormal;
	CBitmap  m_imgHover;
	CBitmap  m_imgDown;
	CBitmap  m_imgDisable;
	CBitmap  m_imgScroll;
    BOOL     m_IconDraw;
	HICON    m_IconIn,m_IconOut;
	int      m_BtnNum;
private:
	int m_ShowState;//��ʾ��״̬��ѡȡ���Ժ�ǿ����ʾ
	int m_state;
	COLORREF m_fg, m_bg;
	bool m_bMouseOver;
	bool m_bEnabled;
	CFont *m_pFont;
	
	bool m_bDCStored;//�Ƿ��Ѿ����汳��ͼ
	
//	CBitmap* m_hMouseInIcon;
//	CBitmap* m_hMouseOutIcon;
	CPoint m_textPos;
	CRect m_iconRect;

	CDC m_memDC;

public:
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg BOOL OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message);
//	void SetRGBColor(int R,int G,int B);//���ñ�������ɫ��
protected:
	BOOL m_bDrawRantage;
	int m_bDrawScroll;
	BOOL m_bRePain;
	HWND m_ParentWnd;
	int m_SendTime;
	virtual void PreSubclassWindow();
	virtual void DrawItem(LPDRAWITEMSTRUCT lpDIS);
protected:	
	//�ڰ�ť�������ɫ
	void DrawFilledRect(CDC *DC, CRect R, COLORREF color);
	//���ð�ť�ϵ�������ɫ
	void DrawButtonText(CDC *DC, CRect R, CString str, COLORREF TextColor);
	void DrawButton();
	void DrawButtonScroll();
	void DrawButtonTitle();
	void DrawButtonNullPicture();
	
//	int m_ColorR,m_ColorG,m_ColorB;
	CString m_strTextOut;
public:
	void SetButtonText(CString str);
	void SetOwnerDraw(bool IsDraw);
	void SetIcons(int hIconIn, int hIconOut,BOOL bDrawIcon);
	void SetImage(UINT nNormalID, UINT nHoverID, UINT nDownID, UINT nDisableID);
	void SetImage(HBITMAP nNormalID, HBITMAP nHoverID, HBITMAP nDownID, HBITMAP nDisableID);
	void SetImage(CString strNormal, CString strHover, CString strDown, CString strDisable);
	void SetIcons(CString  strMouseOut,CString strMouseIn,BOOL bDrawIcon);
	void SetColor(COLORREF fgcolor,COLORREF bgcolor);
	void SetTextPos(CPoint point);
//	CImage* GetPaintImage(){return &m_imgNormal;}
//	CImage* GetPaintIcon(){return &m_hMouseOutIcon;}
	CPoint GetTextPos(){return m_textPos;}
	COLORREF GetFGColor() { return m_fg; }	
	COLORREF GetBGColor() { return m_bg; }
	CRect GetRectInParent();
	CRect GetIconRect(){return m_iconRect;}
public:
	afx_msg void OnEnable(BOOL bEnable);
};

