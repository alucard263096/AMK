#include "widget.h"
#include "ui_widget.h"
#include <QString>
#include <QTextCursor>
#include <QMessageBox>
#include "BRAnyChatCoreSDK.h"
#include <QTextCodec>
#include <QLibrary>
#include <QFileDialog>
#include <QTimer>
#include <QDateTime>


#define REFRESH_TRANSTASK_STATUS_TIMER 	1	//< ˢ�´�������״̬��ʱ��
#define REFRESH_SPEAKVOLUME_TIMER	2       //< �����û�˵��������ʱ��
#define REFRESH_RECORDSTATE_TIMER	3       //< ����¼��״̬��ʱ��

#ifdef __AFX_H__
        #define	BR_AFX_MANAGE_STATE	AFX_MANAGE_STATE(AfxGetStaticModuleState())
#else
        #define	BR_AFX_MANAGE_STATE
#endif //__AFX_H__


typedef int(*pcom_open)(int,int,int);   //���庯��ָ��
#pragma comment(lib,"BRAnyChatCore.lib")


Widget*  pthis;

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);

    pthis = this;

    //ui->IplineEdit->setInputMask("000.000.000.000");        //����IP��ַ�������ʽ
    ui->IplineEdit->setText("demo.anychat.cn");               //����IP��ַ
    ui->PortLineEdit->setText("8906");                      //���ö˿ں�
    ui->UserLineEdit->setText("AnyChatQt");                 //�����û���
    ui->RoomIDlineEdit->setText("1");                       //���÷����
    ui->Speak0ProgressBar->setValue(0);                     //��������ʼֵ��Ϊ0
    ui->Speak1ProgressBar->setValue(0);
    ui->Speak2ProgressBar->setValue(0);
    ui->Speak3ProgressBar->setValue(0);

    m_dwTransTaskId = -1;

    for(int i=0; i <DEMO_SHOW_USER_NUM; i++)
    {
             m_iUserID[i] = -1;
    }

     ui->VideocomboBox->insertItem(1,"this is a test");
     ui->VideocomboBox->insertItem(2,"Video Play");

     ui->RecordState0->hide();
     ui->RecordState1->hide();
     ui->RecordState2->hide();
     ui->RecordState3->hide();


    QTextCodec::setCodecForLocale(QTextCodec::codecForName("system"));    //�����ܹ��������
    QTextCodec::setCodecForCStrings(QTextCodec::codecForName("system"));
    QTextCodec::setCodecForTr(QTextCodec::codecForName("system"));
}

Widget::~Widget()
{
    m_AnyChatSDK.Release();       //�ͷ���Դ
    delete ui;
}

void Widget::AppendLogString(QString logstr)
{
    m_strLogInfo += (logstr + "\r\n");
    ui->m_ctrlEditLog->setText(m_strLogInfo);
    ui->m_ctrlEditLog->moveCursor(QTextCursor::End);  //�Զ�����

}

 //��ʼ��
void Widget::on_initBtn_clicked()
{
    m_AnyChatSDK.LoadFilterDll(L"BRAnyChatCore.dll");  //���ö�����ض�̬��

    //��ȡSDK�İ汾��Ϣ
    DWORD dwMainVer,dwSubVer;
    char szCompileTime[100] = {0};
    m_AnyChatSDK.GetSDKVersion(dwMainVer,dwSubVer,szCompileTime,sizeof(szCompileTime));

    QString logstr;
    logstr.sprintf("AnyChat Core SDK Version:%d.%d(%s)",dwMainVer,dwSubVer,szCompileTime);
    AppendLogString(logstr);   //����AppendLogString������textEdit����ʾ��ǰSDK�İ汾��Ϣ

    //�򿪣��رգ�SDK����־��¼����
    m_AnyChatSDK.ActiveCallLog(true);

    //����SDK�����������Ŀ¼��ע��demo����ֻ������Ϊ��ǰĿ¼����Ŀ����Ҫ����Ϊʵ��·����
    QString szCoreSDKPath;
    szCoreSDKPath = QCoreApplication::applicationDirPath();   //��ȡ��ǰӦ�ó���·��
    (strrchr((char*)szCoreSDKPath.toStdString().c_str(),'/'))[1] = 0;
    m_AnyChatSDK.SetSDKOption(BRAC_SO_CORESDK_PATH,(char*)szCoreSDKPath.toStdString().c_str(),strlen((char*)szCoreSDKPath.toStdString().c_str()));

    // ����BRAC_InitSDK�ĵڶ���������dwFuncMode��������SDK����δ�����ص�����������ο������ĵ���
    DWORD dwFuncMode = BRAC_FUNC_VIDEO_AUTODISP | BRAC_FUNC_AUDIO_AUTOPLAY | BRAC_FUNC_CHKDEPENDMODULE |
                       BRAC_FUNC_AUDIO_VOLUMECALC | BRAC_FUNC_NET_SUPPORTUPNP | BRAC_FUNC_FIREWALL_OPEN |
                       BRAC_FUNC_AUDIO_AUTOVOLUME | BRAC_FUNC_AUDIO_VOLUMECALC | BRAC_FUNC_CONFIG_LOCALINI;

    m_AnyChatSDK.InitSDK(this->winId(), dwFuncMode);
    m_AnyChatSDK.SetVideoDataCallBack(BRAC_PIX_FMT_RGB24,VideoData_CallBack, this);
    m_AnyChatSDK.SetAudioDataCallBack(AudioData_CallBack, this);
    m_AnyChatSDK.SetVolumeChangeCallBack(VolumeChange_CallBack, this);
    m_AnyChatSDK.SetTextMessageCallBack(TextMessage_CallBack,this);
    m_AnyChatSDK.SetTransBufferExCallBack(TransBufferEx_CallBack, this);
    m_AnyChatSDK.SetTransBufferCallBack(TransBuffer_CallBack, this);
    m_AnyChatSDK.SetTransFileCallBack(TransFile_CallBack, this);
    m_AnyChatSDK.SetRecordSnapShotCallBack(RecordSnapShot_CallBack, this);
    m_AnyChatSDK.SetSDKFilterDataCallBack(SDKFilterData_CallBack, this);
    m_AnyChatSDK.SetNotifyMessageCallBack(NotifyMessage_CallBack,this);
   // m_AnyChatSDK.SetTimerProcCallBack(TimerProc_CallBack, this);


    m_bEnableVAD = ui->VADcheckBox->checkState();   //����ĸ���ѡ���ֵ
    m_bEnableAGC = ui->AGCcheckBox->checkState();
    m_bEnableEcho = ui->AECcheckBox->checkState();
    m_bEnableNS = ui->NScheckBox->checkState();

    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_AGCCTRL,(PCHAR)&m_bEnableAGC,sizeof(m_bEnableAGC));
    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_VADCTRL,(PCHAR)&m_bEnableVAD,sizeof(m_bEnableVAD));
    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_NSCTRL,(PCHAR)&m_bEnableNS,sizeof(m_bEnableNS));
    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_ECHOCTRL,(PCHAR)&m_bEnableEcho,sizeof(m_bEnableEcho));

    // ����¼����ʱ�ļ�����·��
    QString szRecordDirectory;
    szRecordDirectory = QCoreApplication::applicationDirPath();   //��ȡ��ǰ���ó���·��
    (strrchr((char*)szRecordDirectory.toStdString().c_str(),'/'))[1] = 0;
    szRecordDirectory.append("/Record");
    m_AnyChatSDK.SetSDKOption(BRAC_SO_RECORD_TMPDIR, (char*)szRecordDirectory.toStdString().c_str(), strlen((char*)szRecordDirectory.toStdString().c_str()));

     // ����¼���ļ���������
     DWORD dwVideoBitrate = 200 * 1000;	// 200kbps
     m_AnyChatSDK.SetSDKOption(BRAC_SO_RECORD_VIDEOBR,(PCHAR)&dwVideoBitrate,sizeof(DWORD));
     DWORD dwAudioBitrate = 96 * 1000;	// 96kbps
     m_AnyChatSDK.SetSDKOption(BRAC_SO_RECORD_AUDIOBR,(PCHAR)&dwAudioBitrate,sizeof(DWORD));

     // ���ÿ�����ʱ�ļ�����·��
     QString szSnapShotDirectory;
     szSnapShotDirectory = QCoreApplication::applicationDirPath();   //��ȡ��ǰӦ�ó���·��
     (strrchr((char*)szSnapShotDirectory.toStdString().c_str(),'/'))[1] = 0;
     szSnapShotDirectory.append("/SnapShot");
     m_AnyChatSDK.SetSDKOption(BRAC_SO_SNAPSHOT_TMPDIR, (char*)szSnapShotDirectory.toStdString().c_str(), strlen((char*)szSnapShotDirectory.toStdString().c_str()));

     // ����SDK��ʱ�ļ�·��
     QString szTempPath;
     szTempPath = QCoreApplication::applicationDirPath();   //��ȡ��ǰ���ó���·��
     (strrchr((char*)szTempPath.toStdString().c_str(),'/'))[1] = 0;
     szTempPath.append("/SnapShot");
     m_AnyChatSDK.SetSDKOption(BRAC_SO_CORESDK_TMPDIR, (char*)szTempPath.toStdString().c_str(), strlen((char*)szTempPath.toStdString().c_str()));

    // ���÷�������֤����
    QString pwd = "BaiRuiTech";
    m_AnyChatSDK.SetServerAuthPass((LPCTSTR)pwd.toStdString().c_str());

    DWORD dwVolume = 0;
    m_AnyChatSDK.AudioGetVolume(BRAC_AD_WAVEOUT, dwVolume);
    ui->OutVolumeChangeSlider->setSliderPosition(dwVolume);//������λ�õ�����

    dwVolume = 0;
    m_AnyChatSDK.AudioGetVolume(BRAC_AD_WAVEIN, dwVolume);
    ui->InVolumeChangeSlider->setSliderPosition(dwVolume);//������λ�õ�����

//    SetTimer(this->winId(), REFRESH_SPEAKVOLUME_TIMER,100,(TIMERPROC)TimerProc_CallBack);  //����Ϊ�����ھ������ʱ����ʱ�������ص�����
//    SetTimer(this->winId(), REFRESH_RECORDSTATE_TIMER,1000,(TIMERPROC)TimerProc_CallBack);


    m_UserRect[0].setRect(9, 380, 170, 140);
    m_UserRect[1].setRect(190, 380, 170, 140);
    m_UserRect[2].setRect(370, 380, 170, 140);
    m_UserRect[3].setRect(550, 380, 170, 140);
}

//�û���¼
void Widget::on_loginBtn_clicked()
{
    bool ok;
    QString strServerIp = ui->IplineEdit->text();           //IP��ַ
    QString strUserName = ui->UserLineEdit->text();         //�û���
    QString strPassword = ui->PwdLineEdit->text();          //����
    int intPort = ui->PortLineEdit->text().toInt(&ok);      //�˿ں����ַ�����ʽת��Ϊ���͸�ʽ

    if(strServerIp == "..." || intPort <= 0)    //�����������ַΪ�ջ�˿ں�С��0�Ļ�
    {
        QMessageBox::warning(this,tr("warning"),tr("Invalid server IP address or port!"));
        return;
    }
    if(strUserName.isEmpty())                       //�û���Ϊ��
    {
         QMessageBox::warning(this,tr("warning"),tr("Must input username for login system!"));
    }

    m_AnyChatSDK.Connect((LPCTSTR)strServerIp.toStdString().c_str(), intPort);   //����GetBuffer
    m_AnyChatSDK.Login((LPCTSTR)strUserName.toStdString().c_str(), (LPCTSTR)strPassword.toStdString().c_str(), 0);
}

//���뷿��
void Widget::on_enterRoomBtn_clicked()
{
    QString roomId = ui->RoomIDlineEdit->text();
    QString pwd = "";
    m_AnyChatSDK.EnterRoom(roomId.toInt(), (LPCTSTR)pwd.toStdString().c_str() , 0);
}

//�뿪����
void Widget::on_leaveRoomBtn_clicked()
{
    m_AnyChatSDK.LeaveRoom(1);
    //�����Լ���userid, ��Ϊ���ֻ��leaveroom, ����logout
    for(int i = 1; i < DEMO_SHOW_USER_NUM; i ++)
    {
        m_iUserID[i] = -1;
    }
}

//�û�ע��
void Widget::on_logoutBtn_clicked()
{
    m_AnyChatSDK.Logout();
    for(int i = 0; i < DEMO_SHOW_USER_NUM; i++)
    {
        m_iUserID[i] = -1;
    }
}

//�ͷ���Դ
void Widget::on_releaseBtn_clicked()
{
    m_AnyChatSDK.Release();
    for(int i = 0; i < DEMO_SHOW_USER_NUM; i++)
    {
        m_iUserID[i] = -1;
    }
    m_dwTransTaskId = -1;
}

//�����ļ�
void Widget::on_sendFileBtn_clicked()
{
    if(m_dwTransTaskId != -1)
        return;

    QString path = QFileDialog::getOpenFileName(this);  //NULL, tr("��"), "/", tr("Any files(*)")
    if(! path.isEmpty())
    {
        DWORD dwTaskId = 0 ;
        DWORD wParam = 1 ;
        DWORD lParam = 2;
        DWORD dwFlags = 0;
        AppendLogString(path);
        DWORD ret = m_AnyChatSDK.TransFile(m_iUserID[1], (LPCTSTR)path.toStdString().c_str(), wParam, lParam, dwFlags, dwTaskId);
        if(ret == GV_ERR_SUCCESS)      //0��ʾ�������ɹ�
        {
            m_dwTransTaskId = dwTaskId;
            QString str;
            str.sprintf("%d", m_dwTransTaskId);
            AppendLogString(str);
            SetTimer(this->winId(), REFRESH_TRANSTASK_STATUS_TIMER,100,(TIMERPROC)TimerProc_CallBack);
        }
        else
        {
            QString strError;
            strError.sprintf("�����ļ���������ʧ�ܣ�������룺%d", ret);
            AppendLogString(strError);
            ui->fileStatuslabel->setText("�ļ�����ʧ�ܣ�");
        }
    }
}

//���仺����
void Widget::on_TransbufBtn_clicked()
{
    if(m_dwTransTaskId != -1)	// ��ʾ����Ϊ�˼�࣬ͬʱֻ����һ������ʵ����SDK�ǿ��Զ����񲢷���
        return;

    PCHAR lpBuf = (PCHAR)malloc(10*1024);		// Ϊ����ʾ������һ��10KByte��С�Ļ�����
    if(!lpBuf)
        return;
    DWORD dwLen = 10*1024;
    lpBuf[1000] = 'Y';			// ��ʾ����Ҫ��Ϊ����֤�յ��������Ƿ���ȷ���ڻ������м�ĳ��λ������һ����־���Է����պ󣬿��жϸñ�־�Ƿ�Ϊ�趨ֵ

    DWORD dwTaskId = 0;
    DWORD wParam = 3;			// �ϲ�Ӧ�ó�����Զ���wParam��lParam��ֵ��������������ֵ�����ص����Է�
    DWORD lParam = 4;
    DWORD dwFlags = 0;			// �ñ�־����Ϊ0����SDK���Զ���������״̬ѡ����ʵĴ���;����TCP��UDP or P2P��
    DWORD ret = m_AnyChatSDK.TransBufferEx(m_iUserID[1],(PBYTE)lpBuf,dwLen,wParam,lParam,dwFlags,dwTaskId);
    if(ret == GV_ERR_SUCCESS)
    {
            m_dwTransTaskId = dwTaskId;
            SetTimer(this->winId(), REFRESH_TRANSTASK_STATUS_TIMER,100,(TIMERPROC)TimerProc_CallBack);

    }
    else
    {
            QString strError;
            strError.sprintf("������������������ʧ�ܣ��������:%d",ret);
            AppendLogString(strError);
            ui->fileStatuslabel->setText("");
    }
    // ������������ɺ� ���������ͷŵ�
    free(lpBuf);
}

//������Ϣ
void Widget::on_sendMessageBtn_clicked()
{
    QString message = ui->messagelineEdit->text();
    if((m_AnyChatSDK.SendTextMessage(-1, NULL, (LPCTSTR)message.toStdString().c_str(), message.toStdString().length()))== 0)  //���ͳɹ�
    {
        QDateTime time = QDateTime::currentDateTime();    //��ȡϵͳ��ǰʱ��
        QString strTime = time.toString("  yyyy-MM-dd hh:mm:ss ");
        CHAR username[30];
        m_AnyChatSDK.GetUserName(m_iUserID[0],username,sizeof(username));
        AppendLogString(username + strTime);
        AppendLogString(message);
    }
}

//���ʵ���
void Widget::on_VideopropertyBtn_clicked()
{
    QString str = "AnyChat ������Ƶ���ʵ���";
    m_AnyChatSDK.ShowLVProperty(this->winId(), (LPCTSTR)str.toStdString().c_str(), 0, 0);
}

//�������
void Widget::on_VADcheckBox_clicked()
{
    m_bEnableVAD = ui->VADcheckBox->checkState();
    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_VADCTRL, (PCHAR)&m_bEnableVAD, sizeof(m_bEnableVAD));
}

//��������
void Widget::on_AECcheckBox_clicked()
{
    m_bEnableEcho = ui->AECcheckBox->checkState();
    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_ECHOCTRL, (PCHAR)&m_bEnableEcho, sizeof(m_bEnableEcho));
}

//�Զ�����
void Widget::on_AGCcheckBox_clicked()
{
    m_bEnableAGC = ui->AGCcheckBox->checkState();
    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_AGCCTRL, (PCHAR)&m_bEnableAGC, sizeof(m_bEnableAGC));
}

//��������
void Widget::on_NScheckBox_clicked()
{
    m_bEnableNS = ui->NScheckBox->checkState();
    m_AnyChatSDK.SetSDKOption(BRAC_SO_AUDIO_NSCTRL, (PCHAR)&m_bEnableNS, sizeof(m_bEnableNS));
}

//ѡ����Ƶ�豸
void Widget::on_VideocomboBox_activated(QString logstr )    //logstr�ǽ�ѡ�е��ַ���
{
    m_AnyChatSDK.SelectVideoCapture((LPCTSTR)logstr.toStdString().c_str());
}

//ѡ����Ƶ�豸
void Widget::on_AudiocomboBox_activated(QString logstr)
{
    m_AnyChatSDK.SelectAudioCapture((LPCTSTR)logstr.toStdString().c_str());
}

//ˢ���豸�б�
void Widget::on_RefreshBtn_clicked()
{
    // ˢ����Ƶ�ɼ��豸�����б�
    PCHAR DeviceName[10] = {0};
    DWORD DeviceNum = 10;
    m_AnyChatSDK.EnumVideoCapture(DeviceName, DeviceNum);

    ui->VideocomboBox->clear();
    int i = 0;
    for(i = 0; i < (int)DeviceNum; i ++)
    {
        if(DeviceName[i])
        {
            ui->VideocomboBox->insertItem(i+1, DeviceName[i]);
            ::GlobalFree((HGLOBAL) DeviceName[i]);
            DeviceName[i] = NULL;
        }
    }

    // ѡ��ǰ�򿪵���Ƶ�ɼ��豸
    CHAR szVideoDeviceName[100] = {0};
    m_AnyChatSDK.GetCurVideoCapture(szVideoDeviceName, sizeof(szVideoDeviceName));

    //ˢ����Ƶ�ɼ��豸�����б�
    DeviceNum = 10;
    m_AnyChatSDK.EnumAudioCapture(DeviceName, DeviceNum);

    ui->AudiocomboBox->clear();
    for(i = 0; i < (int)DeviceNum; i++)
    {
        if(DeviceName[i])
        {
            ui->AudiocomboBox->insertItem(i+1, DeviceName[i]);
            ::GlobalFree((HGLOBAL)DeviceName[i]);
            DeviceName[i] = NULL;
        }
    }

    //ѡ��ǰ�򿪵���Ƶ�ɼ��豸
    CHAR szAudioDeviceName[100] = {0};
    m_AnyChatSDK.GetCurAudioCapture(szAudioDeviceName, sizeof(szAudioDeviceName));
}

//������������ı仯
void Widget::on_OutVolumeChangeSlider_valueChanged(int value)
{
     m_AnyChatSDK.AudioSetVolume(BRAC_AD_WAVEOUT, value);
}

//�������������ı仯
void Widget::on_InVolumeChangeSlider_valueChanged(int value)
{
    m_AnyChatSDK.AudioSetVolume(BRAC_AD_WAVEIN, value);
}

//��Ƶ����0
void Widget::on_Videoctrl0Btn_clicked()
{
    // Ϊ����ʾ����򵥣�������þ�̬����������һ�ε�״̬�����ܲ�׼ȷ����Ϊ�����ڱ�ĵط��д򿪣����ǹرյĲ�������ͬ
    // �ر��ǵ�ǰ�û��뿪���䣬����û��ٽ���ʱ����ʼ״̬����׼ȷ
    static BOOL bLastState = TRUE;
    if(m_iUserID[0] != -1)
    {
        m_AnyChatSDK.UserCameraControl(m_iUserID[0], !bLastState);
        bLastState = !bLastState;
    }
}

//��Ƶ����1
void Widget::on_Videoctrl1Btn_clicked()
{
    static BOOL bLastState = TRUE;
    if(m_iUserID[1] != -1)     //����-1����ʾ�Ա��ؽ��п��ƣ������ڵĻ�����Է�����һ����Ƶ����
    {
        m_AnyChatSDK.UserCameraControl(m_iUserID[1], !bLastState);
        bLastState = !bLastState;
    }
}

//��Ƶ����2
void Widget::on_Videoctrl2Btn_clicked()
{
    static BOOL bLastState = TRUE;
    if(m_iUserID[2] != -1)     //����-1����ʾ�Ա��ؽ��п��ƣ������ڵĻ�����Է�����һ����Ƶ����
    {
        m_AnyChatSDK.UserCameraControl(m_iUserID[2], !bLastState);
        bLastState = !bLastState;
    }
}

//��Ƶ����3
void Widget::on_Videoctrl3Btn_clicked()
{
    static BOOL bLastState = TRUE;
    if(m_iUserID[3] != -1)     //����-1����ʾ�Ա��ؽ��п��ƣ������ڵĻ�����Է�����һ����Ƶ����
    {
        m_AnyChatSDK.UserCameraControl(m_iUserID[3], !bLastState);
        bLastState = !bLastState;
    }
}

//��Ƶ����0
void Widget::on_Audioctrl0Btn_clicked()
{
    static BOOL bLastState = TRUE;
    if(m_iUserID[0] != -1)    //ͬ��Ϊ-1�Ļ������ǶԱ����û����Խ��п��ƣ���Ϊ-1�Ļ�����Է����һ����Ƶ����
    {
            m_AnyChatSDK.UserSpeakControl(m_iUserID[0],!bLastState);  //bLastStateΪtrue,��ʾ�����ԣ�Ϊfalse����ʾֹͣ����
            bLastState = !bLastState;
    }
}

//��Ƶ����1
void Widget::on_Audioctrl1Btn_clicked()
{
    static BOOL bLastState = TRUE;
    if(m_iUserID[1] != -1)    //ͬ��Ϊ-1�Ļ������ǶԱ����û����Խ��п��ƣ���Ϊ-1�Ļ�����Է����һ����Ƶ����
    {
            m_AnyChatSDK.UserSpeakControl(m_iUserID[1],!bLastState);  //bLastStateΪtrue,��ʾ�����ԣ�Ϊfalse����ʾֹͣ����
            bLastState = !bLastState;
    }
}

//��Ƶ����2
void Widget::on_Audioctrl2Btn_clicked()
{
    static BOOL bLastState = TRUE;
    if(m_iUserID[2] != -1)    //ͬ��Ϊ-1�Ļ������ǶԱ����û����Խ��п��ƣ���Ϊ-1�Ļ�����Է����һ����Ƶ����
    {
            m_AnyChatSDK.UserSpeakControl(m_iUserID[2],!bLastState);  //bLastStateΪtrue,��ʾ�����ԣ�Ϊfalse����ʾֹͣ����
            bLastState = !bLastState;
    }
}

//��Ƶ����3
void Widget::on_Audioctrl3Btn_clicked()
{
    static BOOL bLastState = TRUE;
    if(m_iUserID[3] != -1)    //ͬ��Ϊ-1�Ļ������ǶԱ����û����Խ��п��ƣ���Ϊ-1�Ļ�����Է����һ����Ƶ����
    {
            m_AnyChatSDK.UserSpeakControl(m_iUserID[3],!bLastState);  //bLastStateΪtrue,��ʾ�����ԣ�Ϊfalse����ʾֹͣ����
            bLastState = !bLastState;
    }
}

//¼�����0
void Widget::on_Recordctrl0Btn_clicked()
{
    static BOOL bLastState = FALSE;
    if(m_iUserID[0] != -1)
    {
            m_AnyChatSDK.StreamRecordCtrl(m_iUserID[0],!bLastState,0,0);
            bLastState = !bLastState;
    }
}

//¼�����1
void Widget::on_Recordctrl1Btn_clicked()
{
    static BOOL bLastState = FALSE;
    if(m_iUserID[1] != -1)
    {
            m_AnyChatSDK.StreamRecordCtrl(m_iUserID[1],!bLastState,0,0);
            bLastState = !bLastState;
    }
}

//¼�����2
void Widget::on_Recordctrl2Btn_clicked()
{
    static BOOL bLastState = FALSE;
    if(m_iUserID[2] != -1)
    {
            m_AnyChatSDK.StreamRecordCtrl(m_iUserID[2],!bLastState,0,0);
            bLastState = !bLastState;
    }
}

//¼�����3
void Widget::on_Recordctrl3Btn_clicked()
{
    static BOOL bLastState = FALSE;
    if(m_iUserID[3] != -1)
    {
            m_AnyChatSDK.StreamRecordCtrl(m_iUserID[3],!bLastState,0,0);
            bLastState = !bLastState;
    }
}

//����0
void Widget::on_Snapshotctrl0Btn_clicked()     //���գ��൱������Ƶ��������һ����Ƭ
{
    if(m_iUserID[0] != -1)
        m_AnyChatSDK.SnapShot(m_iUserID[0], 0, 0);
}

//����1
void Widget::on_Snapshotctrl1Btn_clicked()
{
    if(m_iUserID[1] != -1)
        m_AnyChatSDK.SnapShot(m_iUserID[1], 0, 0);
}

//����2
void Widget::on_Snapshotctrl2Btn_clicked()
{
    if(m_iUserID[2] != -1)
        m_AnyChatSDK.SnapShot(m_iUserID[2], 0, 0);
}

//����3
void Widget::on_Snapshotctrl3Btn_clicked()
{
    if(m_iUserID[3] != -1)
        m_AnyChatSDK.SnapShot(m_iUserID[3], 0, 0);
}


// �����û���Ƶ
void Widget::DrawUserVideo(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, BITMAPINFOHEADER bmiHeader)
{
    //
}


// ¼�������������ı�
void Widget::OnWaveInVolumeChange(DWORD dwVolume)
{
    ui->InVolumeChangeSlider->setValue(dwVolume);
}

// ���������������ı�
void Widget::OnWaveOutVolumeChange(DWORD dwVolume)
{
    ui->OutVolumeChangeSlider->setValue(dwVolume);
}


// �յ���Ϣ���ͻ������ӷ����� wParam��BOOL����ʾ�Ƿ����ӳɹ�
LRESULT Widget::OnGVClientConnect(WPARAM wParam, LPARAM lParam)
{
    bool bSuccess = (bool)wParam;
    AppendLogString(bSuccess ? "���ӷ������ɹ�......" : "���ӷ�����ʧ��......");
    return 0;
}

// �յ���Ϣ���ͻ��˵�¼ϵͳ wParam ��INT����ʾ�Լ����û�ID��, lParam ��INT����ʾ��¼�����0 �ɹ�������Ϊ������룬�ο�������붨��
LRESULT Widget::OnGVClientLogin(WPARAM wParam, LPARAM lParam)
{
    QString logstr;
    DWORD dwUserID = wParam;
    if(lParam == 0)
    {
            CHAR username[30];
            m_AnyChatSDK.GetUserName(dwUserID,username,sizeof(username));
            logstr.sprintf("��¼�������ɹ����Լ����û����Ϊ��%d(%s)",wParam,username);

            // ���û���ID�����ڵ�һ��λ�ã������ڻص�������������Ҳ������Ƶ�λ��
            //m_iUserID[0] = dwUserID;
    }
    else
            logstr.sprintf("��¼������ʧ�ܣ��������Ϊ�� %d ",lParam);

    AppendLogString(logstr);
    return 0;
}

// �յ���Ϣ���ͻ��˽��뷿��  wParam ��INT����ʾ�����뷿���ID��,lParam ��INT����ʾ�Ƿ���뷿�䣺0�ɹ����룬����Ϊ�������
LRESULT Widget::OnGVClientEnterRoom(WPARAM wParam, LPARAM lParam)
{
        QString logstr;
        INT roomid = (INT)wParam;
        if(lParam == 0)
        {
                logstr.sprintf("�ɹ�������Ϊ��%d �ķ���",roomid);

                m_AnyChatSDK.SetVideoPos(-1, this->winId(), m_UserRect[0].left(), m_UserRect[0].top(), m_UserRect[0].right(), m_UserRect[0].bottom());
                m_AnyChatSDK.UserCameraControl(-1,TRUE);
                m_AnyChatSDK.UserSpeakControl(-1,TRUE);
        }
        else
                logstr.sprintf("������뷿��ʧ�ܣ��������Ϊ�� %d ",lParam);

        AppendLogString(logstr);
        return 0;
}

// �յ���ǰ����������û���Ϣ wParam ��INT����ʾ�����û������������Լ���,lParam ��INT����ʾ����ID
LRESULT Widget::OnGVClientOnlineUser(WPARAM wParam, LPARAM lParam)
 {
     QString logstr;
     int onlinenum = (int)wParam;
     logstr.sprintf("�ڱ��Ϊ��%d�ķ��乲��%dλ�����û�",lParam, onlinenum);
     AppendLogString(logstr);

     DWORD dwUserNum = 0;
     m_AnyChatSDK.GetOnlineUser(NULL, dwUserNum);      //���Ȼ�ȡ��������������
     if(!dwUserNum)
         return 0;

     LPDWORD lpdwUserList = (LPDWORD)malloc(sizeof(DWORD) *dwUserNum);
     m_AnyChatSDK.GetOnlineUser(lpdwUserList, dwUserNum);

     for(int i = 0; i < (int)dwUserNum; i++)
     {
         DWORD userid = lpdwUserList[i];
         // ���û���һ���յ�λ����ʾ��壨��0��λ�������Լ���
         INT site = -1;
         for(INT i=1; i<DEMO_SHOW_USER_NUM; i++)
        {
            if(m_iUserID[i] == -1)
            {
                site = i;
                break;
             }
         }
         if(site != -1)
         {
             m_AnyChatSDK.SetVideoPos(userid, this->winId(), m_UserRect[site].left(), m_UserRect[site].top(), m_UserRect[site].right(), m_UserRect[site].bottom());
             m_AnyChatSDK.UserCameraControl(userid, true);
             m_AnyChatSDK.UserSpeakControl(userid, true);

             DWORD dwState = 0;
             m_AnyChatSDK.GetCameraState(userid, dwState);
             logstr.sprintf("�û�%d����Ƶ״̬�ǣ�%d", userid, dwState);
             AppendLogString(logstr);

             m_iUserID[site] = userid;
         }
         else
             break;
     }
     free(lpdwUserList);
     return 0;
 }

// �յ���Ϣ���û�����Ƶ�豸״̬�仯��Ϣ  wParam ��INT����ʾ�û�ID�� lParam ��BOOL����ʾ���û��Ƿ��Ѵ���Ƶ�ɼ��豸
LRESULT Widget::OnGVClientMicStateChange(WPARAM wParam, LPARAM lParam)
{
    QString logstr;
    logstr.sprintf("���Ϊ��%d ���û� ", wParam);
    logstr.append( lParam ? "��":"�ر�");
    logstr.append(" ����Mic�豸");
    AppendLogString(logstr);
    return 0;
}

// �յ���Ϣ���û����루�뿪������ wParam ��INT����ʾ�û�ID��,lParam ��BOOL����ʾ���û��ǽ��루TRUE�����뿪��FALSE������
LRESULT Widget::OnGVClientUserAtRoom(WPARAM wParam, LPARAM lParam)
{
    QString logstr;
     int userid = (int)wParam;
     bool bEnter = (bool)lParam;

     char username[30]={0};
     m_AnyChatSDK.GetUserNameW(userid,username,sizeof(username));
     logstr.sprintf("���Ϊ��%d ���û�(%s) ",userid,username);
     logstr.append(bEnter ? "����" : "�뿪");
     logstr.append("����");
     AppendLogString(logstr);

     if(bEnter)
     {
            // ���û���һ���յ�λ����ʾ��壨��0��λ�������Լ���
            int site = -1;
            for(INT i=1; i<DEMO_SHOW_USER_NUM; i++)
            {
                if(m_iUserID[i] == -1)
                {
                        site = i;
                        break;
                 }
             }
            if(site != -1)
            {
                m_AnyChatSDK.SetVideoPos(userid,this->winId(),m_UserRect[site].left(),m_UserRect[site].top(),m_UserRect[site].right(),m_UserRect[site].bottom());
                m_AnyChatSDK.UserCameraControl(userid,TRUE);
                m_AnyChatSDK.UserSpeakControl(userid,TRUE);
                m_iUserID[site] = userid;
            }
        }
        else
        {
            //������û�ռ��λ�õ���Ϣ
            for(INT i=1; i<DEMO_SHOW_USER_NUM; i++)
            {
                 if(m_iUserID[i] == userid)
                 {
                        m_iUserID[i] = -1;
                        break;
                  }
            }
      }
      return 0;
}

//�յ���Ϣ�����������ѹر�    (����Ϣֻ���ڿͻ������ӷ������ɹ�֮�������쳣�ж�֮ʱ����)
LRESULT Widget::OnGVClientLinkClose(WPARAM wParam, LPARAM lParam)
{
    AppendLogString("���������ѹرգ��Ͽ���");

    for(INT i=0; i<DEMO_SHOW_USER_NUM; i++)
    {
            m_iUserID[i] = -1;
    }
    return 0;
}

//�û�����ģʽ�����仯  wParam ��INT����ʾ�û�ID��,lParam ��INT����ʾ�û��ĵ�ǰ����ģʽ
LRESULT Widget::OnGVClientChatModeChange(WPARAM wParam, LPARAM lParam)
{
    QString logstr;
    int userid = (int)wParam;
    int chatmode = (int)lParam;

    if(m_iUserID[0] == userid)   //����ģʽΪ0����ʾ���ġ�Ϊ1����ʾ˽��
    {
            logstr.sprintf("�Լ���%d ��������ģʽ�ı䣬��ǰ����ģʽΪ��",userid);
            logstr.append( chatmode == 0 ? "����ģʽ" : "˽��ģʽ");
    }
    else
    {
            logstr.sprintf("���Ϊ��%d ���û�����ģʽ�ı䣬��ǰ����ģʽΪ��",userid);
            logstr.append(chatmode == 0 ? "����ģʽ" : "˽��ģʽ");
    }
    AppendLogString(logstr);
    return 0;
}

//��ʱ���ص�����
void CALLBACK Widget::TimerProc_CallBack(HWND hwnd, UINT uMsg, UINT nIDEvent, DWORD dwTime)
{
    switch(nIDEvent)
    {
    case REFRESH_TRANSTASK_STATUS_TIMER:     // ˢ�»�������������״̬
        {
            if(pthis->m_dwTransTaskId == -1)
            {
                KillTimer(pthis->winId(), REFRESH_TRANSTASK_STATUS_TIMER);
                break;
            }
            DWORD ret;
            DWORD dwStatus = 0;
            ret = pthis->m_AnyChatSDK.QueryTransTaskInfo(-1, pthis->m_dwTransTaskId,BRAC_TRANSTASK_STATUS,(PCHAR)&dwStatus,sizeof(DWORD));
            if(ret != GV_ERR_SUCCESS)
            {
                KillTimer(pthis->winId(), REFRESH_TRANSTASK_STATUS_TIMER);
                pthis->m_dwTransTaskId = -1;
                pthis->ui->fileStatuslabel->setText("");
                if(ret == GV_ERR_TRANSBUF_NOTASK)
                    pthis->AppendLogString("�����������ڣ����ܶԷ����뿪���䣡");
                else
                   pthis-> AppendLogString("��ѯ������Ϣʧ�ܣ�");
                break;
            }
            double dbProgress = 0.0;
            pthis->m_AnyChatSDK.QueryTransTaskInfo(-1,pthis->m_dwTransTaskId,BRAC_TRANSTASK_PROGRESS,(PCHAR)&dbProgress,sizeof(DOUBLE));
            DWORD dwBitrate  = 0;
            pthis->m_AnyChatSDK.QueryTransTaskInfo(-1,pthis->m_dwTransTaskId,BRAC_TRANSTASK_BITRATE,(PCHAR)&dwBitrate,sizeof(DWORD));

            QString strStatus;
            switch(dwStatus)
            {
            case 1:		strStatus = "Ready";        break;
            case 2:		strStatus = "Process";      break;
            case 3:		strStatus = "Finish";       break;
            case 4:		strStatus = "Cancel";       break;
            case 5:		strStatus = "Reject";       break;
            default:            strStatus = "Unkonw";       break;
            }
            if(dwStatus == 3)           //finish
            {
                KillTimer(pthis->winId(), REFRESH_TRANSTASK_STATUS_TIMER);
                pthis->m_dwTransTaskId = -1;
                pthis->AppendLogString("������������ɣ�");
            }
           QString strBitrate;
            if(dwBitrate >= 1000 * 1000)
                    strBitrate.sprintf("%.2f Mbps",(float)dwBitrate/1000/1000);
            else if(dwBitrate >= 1000)
                    strBitrate.sprintf("%.2f Kbps",(float)dwBitrate/1000);
            else
                    strBitrate.sprintf("%.2f bps",(float)dwBitrate);

           QString strNotify;
           strNotify.sprintf("-%.2f%-", dbProgress);
           pthis->ui->fileStatuslabel->setText(strStatus + strNotify + strBitrate);   //QString���͵�����ӱ�ʾ�ϲ�
      }
    case  REFRESH_SPEAKVOLUME_TIMER:		// ˢ���û�˵������
        {
            double fSpeakVolume = 0.0;
            if(pthis->m_iUserID[0] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[0],BRAC_USERSTATE_SPEAKVOLUME,(PCHAR)&fSpeakVolume,sizeof(DOUBLE)) == GV_ERR_SUCCESS)
                    pthis->ui->Speak0ProgressBar->setValue((INT)fSpeakVolume);
            else
                    pthis->ui->Speak0ProgressBar->setValue(0);

            fSpeakVolume = 0.0;
            if(pthis->m_iUserID[1] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[1],BRAC_USERSTATE_SPEAKVOLUME,(PCHAR)&fSpeakVolume,sizeof(DOUBLE)) == GV_ERR_SUCCESS)
                    pthis->ui->Speak1ProgressBar->setValue((INT)fSpeakVolume);
            else
                   pthis->ui->Speak1ProgressBar->setValue(0);

            fSpeakVolume = 0.0;
            if(pthis->m_iUserID[2] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[2],BRAC_USERSTATE_SPEAKVOLUME,(PCHAR)&fSpeakVolume,sizeof(DOUBLE)) == GV_ERR_SUCCESS)
                    pthis->ui->Speak2ProgressBar->setValue((INT)fSpeakVolume);
            else
                    pthis->ui->Speak2ProgressBar->setValue(0);

            fSpeakVolume = 0.0;
            if(pthis->m_iUserID[3] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[3],BRAC_USERSTATE_SPEAKVOLUME,(PCHAR)&fSpeakVolume,sizeof(DOUBLE)) == GV_ERR_SUCCESS)
                    pthis->ui->Speak3ProgressBar->setValue((INT)fSpeakVolume);
            else
                    pthis->ui->Speak3ProgressBar->setValue(0);
        }
        break;
    case REFRESH_RECORDSTATE_TIMER:		// ˢ��¼��״̬
        {
                DWORD dwRecordState = 0;
                if(pthis->m_iUserID[0] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[0],BRAC_USERSTATE_RECORDING,(PCHAR)&dwRecordState,sizeof(DWORD)) == GV_ERR_SUCCESS)
                {
                        static BOOL bShow = FALSE;
                        bShow = dwRecordState ? !bShow : FALSE;
                        if(bShow)
                        {
                            pthis->ui->RecordState0->show();
                        }
                        else
                            pthis->ui->RecordState0->hide();
                }

                dwRecordState = 0;
                if(pthis->m_iUserID[1] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[1],BRAC_USERSTATE_RECORDING,(PCHAR)&dwRecordState,sizeof(DWORD)) == GV_ERR_SUCCESS)
                {
                        static BOOL bShow = FALSE;
                        bShow = dwRecordState ? !bShow : FALSE;
                        if(bShow)
                        {
                            pthis->ui->RecordState1->show();
                        }
                        else
                            pthis->ui->RecordState1->hide();
                }

                dwRecordState = 0;
                if(pthis->m_iUserID[2] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[2],BRAC_USERSTATE_RECORDING,(PCHAR)&dwRecordState,sizeof(DWORD)) == GV_ERR_SUCCESS)
                {
                        static BOOL bShow = FALSE;
                        bShow = dwRecordState ? !bShow : FALSE;
                        if(bShow)
                        {
                            pthis->ui->RecordState2->show();
                        }
                        else
                            pthis->ui->RecordState2->hide();
                }
                dwRecordState = 0;
                if(pthis->m_iUserID[3] != -1 && pthis->m_AnyChatSDK.QueryUserState(pthis->m_iUserID[3],BRAC_USERSTATE_RECORDING,(PCHAR)&dwRecordState,sizeof(DWORD)) == GV_ERR_SUCCESS)
                {
                        static BOOL bShow = FALSE;
                        bShow = dwRecordState ? !bShow : FALSE;
                        if(bShow)
                        {
                            pthis->ui->RecordState3->show();
                        }
                        else
                            pthis->ui->RecordState3->hide();
                }
        }
        break;
        default:
        break;
    }

}

// ��Ƶ���ݻص�����
void CALLBACK Widget::VideoData_CallBack(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, BITMAPINFOHEADER bmiHeader, LPVOID lpUserValue)
{
    Widget *pDemoDlg = (Widget*)lpUserValue;
    if(pDemoDlg)
    {
          pDemoDlg->DrawUserVideo(dwUserid,lpBuf,dwLen,bmiHeader);
    }
}

// ��Ƶ���ݻص�����
void CALLBACK Widget::AudioData_CallBack(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, WAVEFORMATEX waveFormatEx, LPVOID lpUserValue)
{
    Widget *pDemoDlg = (Widget*)lpUserValue;
    if(pDemoDlg)
    {
        // do something ...
    }
}

// �������Ļص�����
void CALLBACK Widget::VolumeChange_CallBack(BRAC_AudioDevice device, DWORD dwCurrentVolume, LPVOID lpUserValue)
{
    Widget *pDemoDlg = (Widget*)lpUserValue;
    if(pDemoDlg)
    {
        if(device == BRAC_AD_WAVEIN)
                pDemoDlg->OnWaveInVolumeChange(dwCurrentVolume);
        else
                pDemoDlg->OnWaveOutVolumeChange(dwCurrentVolume);
    }
}

// ͸��ͨ��������չ�ص���������
void CALLBACK Widget::TransBufferEx_CallBack(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue)
{
        Widget *pDemoDlg = (Widget*)lpUserValue;
        if(pDemoDlg)
        {
                BOOL bSuccess = FALSE;
                if(dwLen > 1000)
                {
                        bSuccess = lpBuf[1000] == 'Y' ? TRUE : FALSE;
                }
                QString strNotify;
                strNotify.sprintf("TransBuffer_CallBack:dwUserid-%d, bufSize-%d",dwUserid,dwLen,bSuccess ? "Success" : "Fail");
                pDemoDlg->AppendLogString(strNotify);
        }
}

// ͸��ͨ�����ݻص���������
void CALLBACK Widget::TransBuffer_CallBack(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue)
{
        Widget *pDemoDlg = (Widget*)lpUserValue;
        if(pDemoDlg)
        {
                QString strNotify;
                strNotify.sprintf("TransBuffer_CallBack:dwUserid-%d, bufSize-%d",dwUserid,dwLen);
                pDemoDlg->AppendLogString(strNotify);
        }
}

// �ļ�����ص���������
void CALLBACK Widget::TransFile_CallBack(DWORD dwUserid, LPCTSTR lpFileName, LPCTSTR lpTempFilePath, DWORD dwFileLength, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue)
{
        Widget *pDemoDlg = (Widget*)lpUserValue;
        if(pDemoDlg)
        {
                QString strNotify;
                strNotify.sprintf("TransFile_CallBack:dwUserid-%d, lpFileName-%s, lpTempFilePath-%s",dwUserid,lpFileName,lpTempFilePath);
                pDemoDlg->AppendLogString(strNotify);
        }
}

// ¼�񡢿���������ɻص���������
void CALLBACK Widget::RecordSnapShot_CallBack(DWORD dwUserid, LPCTSTR lpFileName, DWORD dwParam, BOOL bRecordType, LPVOID lpUserValue)
{
        Widget *pDemoDlg = (Widget*)lpUserValue;
        if(pDemoDlg)
        {
                QString strNotify;
                strNotify.sprintf("%s CallBack:dwUserid-%d, FilePathName-%s",bRecordType?"Record":"SnapShot",(int)dwUserid,lpFileName);
                pDemoDlg->AppendLogString(strNotify);
        }
}

// SDK Filter ͨ�����ݻص���������
void CALLBACK Widget::SDKFilterData_CallBack(LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue)
{
        Widget *pDemoDlg = (Widget*)lpUserValue;
        if(pDemoDlg)
        {
                QString strNotify;
                strNotify.sprintf("SDK Filter CallBack:%s",lpBuf);
                pDemoDlg->AppendLogString(strNotify);
        }
}

// ������Ϣ�ص���������
void CALLBACK Widget::TextMessage_CallBack(DWORD dwFromUserid, DWORD dwToUserid, BOOL bSecret, LPCTSTR lpMsgBuf, DWORD dwLen, LPVOID lpUserValue)
{
        BR_AFX_MANAGE_STATE;
        Widget*	pDemoDlg = (Widget*)lpUserValue;
        QString message;
        if(pDemoDlg)
        {
            QDateTime time = QDateTime::currentDateTime();    //��ȡϵͳ��ǰʱ��
            QString strTime = time.toString("  yyyy-MM-dd hh:mm:ss ");
            CHAR username[30];
            pDemoDlg->m_AnyChatSDK.GetUserName(dwFromUserid,username,sizeof(username));
            pDemoDlg->AppendLogString(username + strTime);
            message.sprintf("%s", lpMsgBuf);
            pDemoDlg->AppendLogString(message);
        }
}

// �첽��Ϣ֪ͨ�ص���������
void CALLBACK Widget::NotifyMessage_CallBack(DWORD dwNotifyMsg, DWORD wParam, DWORD lParam, LPVOID lpUserValue)
{
        BR_AFX_MANAGE_STATE;
        Widget*	pAnyChatSDKProc = (Widget*)lpUserValue;
        if(!pAnyChatSDKProc)
                return;
        switch(dwNotifyMsg)
        {
        case WM_GV_CONNECT:		pAnyChatSDKProc->OnGVClientConnect(wParam,NULL);		break;
        case WM_GV_LOGINSYSTEM:		pAnyChatSDKProc->OnGVClientLogin(wParam,lParam);                break;
        case WM_GV_ENTERROOM:           pAnyChatSDKProc->OnGVClientEnterRoom(wParam,lParam);            break;
        case WM_GV_MICSTATECHANGE:	pAnyChatSDKProc->OnGVClientMicStateChange(wParam,lParam);       break;
        case WM_GV_USERATROOM:		pAnyChatSDKProc->OnGVClientUserAtRoom(wParam,lParam);           break;
        case WM_GV_LINKCLOSE:           pAnyChatSDKProc->OnGVClientLinkClose(wParam, lParam);           break;
        case WM_GV_ONLINEUSER:		pAnyChatSDKProc->OnGVClientOnlineUser(wParam,lParam);           break;

        case WM_GV_CAMERASTATE:		pAnyChatSDKProc->OnAnyChatCameraStateChgMessage(wParam,lParam);	break;
        case WM_GV_CHATMODECHG:		pAnyChatSDKProc->OnGVClientChatModeChange(wParam,lParam);       break;
        case WM_GV_ACTIVESTATE:		pAnyChatSDKProc->OnAnyChatActiveStateChgMessage(wParam,lParam);	break;
        case WM_GV_P2PCONNECTSTATE:	pAnyChatSDKProc->OnAnyChatP2PConnectStateMessage(wParam,lParam);break;

        case WM_GV_PRIVATEREQUEST:	pAnyChatSDKProc->OnAnyChatPrivateRequestMessage(wParam,lParam);	break;
        case WM_GV_PRIVATEECHO:		pAnyChatSDKProc->OnAnyChatPrivateEchoMessage(wParam,lParam);	break;
        case WM_GV_PRIVATEEXIT:		pAnyChatSDKProc->OnAnyChatPrivateExitMessage(wParam,lParam);	break;

        case WM_GV_SDKWARNING:		pAnyChatSDKProc->OnAnyChatSDKWarningMessage(wParam,lParam);     break;

        default:
                break;
        }
        pAnyChatSDKProc->OnAnyChatNotifyMessageCallBack(dwNotifyMsg,wParam,lParam);
};








