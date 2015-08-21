#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QString>
#include <QRect>
#include <QTimer>
#include "BRAnyChatCoreSDK.h"

#include "AnyChatCoreSDK.h"

#define DEMO_SHOW_USER_NUM		4						//< ������demo��������ʾ�û���������

namespace Ui {
    class Widget;
}

class Widget : public QWidget
{
    Q_OBJECT

public:
    explicit Widget(QWidget *parent = 0);
    ~Widget();

    CAnyChatCoreSDK m_AnyChatSDK;

    QString str;
     int x1,x2,y1,y2;
    QString m_strLogInfo;
    bool m_bEnableAGC;    //AGC���Զ����棩
    bool m_bEnableEcho;   //AEC ������������
    bool m_bEnableNS;      //NS���������ƣ�
    bool m_bEnableVAD;   //VAD��������⣩

    QRect  m_UserRect[DEMO_SHOW_USER_NUM];     //�û���ʾλ��
    int m_iUserID[DEMO_SHOW_USER_NUM];              //�û�ID��

    int m_dwTransTaskId;                                                       // ����������

    void AppendLogString(QString logstr);
    void DrawUserVideo(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, BITMAPINFOHEADER bmiHeader);
    void OnWaveInVolumeChange(DWORD dwVolume);
    void OnWaveOutVolumeChange(DWORD dwVolume);



    LRESULT OnGVClientConnect(WPARAM wParam, LPARAM lParam);
    LRESULT OnGVClientLogin(WPARAM wParam, LPARAM lParam);
    LRESULT OnGVClientEnterRoom(WPARAM wParam, LPARAM lParam);
    LRESULT OnGVClientOnlineUser(WPARAM wParam, LPARAM lParam);
    LRESULT OnGVClientMicStateChange(WPARAM wParam, LPARAM lParam);
    LRESULT OnGVClientUserAtRoom(WPARAM wParam, LPARAM lParam);
    LRESULT OnGVClientLinkClose(WPARAM wParam, LPARAM lParam);
    LRESULT OnGVClientChatModeChange(WPARAM wParam, LPARAM lParam);




    // �û�����ͷ״̬�ı���Ϣ
    virtual void OnAnyChatCameraStateChgMessage(DWORD dwUserId, DWORD dwState) {}
    // �û��״̬�����仯��Ϣ
    virtual void OnAnyChatActiveStateChgMessage(DWORD dwUserId, DWORD dwState) {}
    // P2P����״̬�仯��Ϣ
    virtual void OnAnyChatP2PConnectStateMessage(DWORD dwUserId, DWORD dwState){}
    // �û�˽��������Ϣ
    virtual void OnAnyChatPrivateRequestMessage(DWORD dwUserId, DWORD dwRequestId) {}
    // �û�˽������ظ���Ϣ
    virtual void OnAnyChatPrivateEchoMessage(DWORD dwUserId, DWORD dwErrorCode) {}
    // �û��˳�˽����Ϣ
    virtual void OnAnyChatPrivateExitMessage(DWORD dwUserId, DWORD dwErrorCode) {}
    // SDK������Ϣ
    virtual void OnAnyChatSDKWarningMessage(DWORD dwErrorCode, DWORD dwReserved) {}
    // �첽��Ϣ֪ͨ�ص�����
    virtual void OnAnyChatNotifyMessageCallBack(DWORD dwNotifyMsg, DWORD wParam, DWORD lParam) {}

    void static CALLBACK VideoData_CallBack(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, BITMAPINFOHEADER bmiHeader, LPVOID lpUserValue);
    void static CALLBACK AudioData_CallBack(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, WAVEFORMATEX waveFormatEx, LPVOID lpUserValue);
    void static CALLBACK VolumeChange_CallBack(BRAC_AudioDevice device, DWORD dwCurrentVolume, LPVOID lpUserValue);
    void static CALLBACK TransBufferEx_CallBack(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
    void static CALLBACK TransBuffer_CallBack(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue);
    void static CALLBACK TransFile_CallBack(DWORD dwUserid, LPCTSTR lpFileName, LPCTSTR lpTempFilePath, DWORD dwFileLength, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
    void static CALLBACK RecordSnapShot_CallBack(DWORD dwUserid, LPCTSTR lpFileName, DWORD dwParam, BOOL bRecordType, LPVOID lpUserValue);
    void static CALLBACK SDKFilterData_CallBack(LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue);
    void static CALLBACK NotifyMessage_CallBack(DWORD dwNotifyMsg, DWORD wParam, DWORD lParam, LPVOID lpUserValue);
    void static CALLBACK TextMessage_CallBack(DWORD dwFromUserid, DWORD dwToUserid, BOOL bSecret, LPCTSTR lpMsgBuf, DWORD dwLen, LPVOID lpUserValue);
    void static CALLBACK TimerProc_CallBack(HWND hwnd, UINT uMsg, UINT nIDEvent, DWORD dwTime);

private:
    Ui::Widget *ui;

private slots:

    void on_Snapshotctrl3Btn_clicked();
    void on_Snapshotctrl2Btn_clicked();
    void on_Snapshotctrl1Btn_clicked();
    void on_Snapshotctrl0Btn_clicked();
    void on_Recordctrl3Btn_clicked();
    void on_Recordctrl2Btn_clicked();
    void on_Recordctrl1Btn_clicked();
    void on_Recordctrl0Btn_clicked();
    void on_Audioctrl3Btn_clicked();
    void on_Audioctrl2Btn_clicked();
    void on_Audioctrl1Btn_clicked();
    void on_Audioctrl0Btn_clicked();
    void on_Videoctrl3Btn_clicked();
    void on_Videoctrl2Btn_clicked();
    void on_Videoctrl1Btn_clicked();
    void on_Videoctrl0Btn_clicked();
    void on_sendMessageBtn_clicked();
    void on_InVolumeChangeSlider_valueChanged(int value);
    void on_OutVolumeChangeSlider_valueChanged(int value);
    void on_RefreshBtn_clicked();
    void on_AudiocomboBox_activated(QString );
    void on_VideocomboBox_activated(QString );
    void on_NScheckBox_clicked();
    void on_AGCcheckBox_clicked();
    void on_AECcheckBox_clicked();
    void on_VADcheckBox_clicked();
    void on_VideopropertyBtn_clicked();
    void on_TransbufBtn_clicked();
    void on_sendFileBtn_clicked();
    void on_releaseBtn_clicked();
    void on_logoutBtn_clicked();
    void on_leaveRoomBtn_clicked();
    void on_enterRoomBtn_clicked();
    void on_loginBtn_clicked();
    void on_initBtn_clicked();





};

#endif // WIDGET_H
