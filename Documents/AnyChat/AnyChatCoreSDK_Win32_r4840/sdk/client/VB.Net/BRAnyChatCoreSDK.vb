Option Strict On
Option Explicit On
Imports System.Runtime.InteropServices

Module BRAnyChatCoreSDK
	Public Const AnyChatSDKDLL As String = "BRAnyChatCore.dll"
	
	'// ����ģʽ����
	Public Const BRAC_FUNC_VIDEO_CBDATA 	As Integer = &H1
	'#define BRAC_FUNC_VIDEO_CBDATA      0x00000001L ///< ͨ���ص����������Ƶ����
	Public Const BRAC_FUNC_VIDEO_AUTODISP 	As Integer = &H2
	'#define BRAC_FUNC_VIDEO_AUTODISP    0x00000002L ///< ��SDK��������Ƶ������Ƶ��ʾ��ָ���Ĵ�����
	Public Const BRAC_FUNC_AUDIO_CBDATA 	As Integer = &H4
	'#define BRAC_FUNC_AUDIO_CBDATA      0x00000004L ///< ͨ���ص����������Ƶ����
	Public Const BRAC_FUNC_AUDIO_AUTOPLAY 	As Integer = &H8
	'#define BRAC_FUNC_AUDIO_AUTOPLAY    0x00000008L ///< ��SDK��������Ƶ��ֱ�Ӳ���
	Public Const BRAC_FUNC_CONFIG_LOCALINI 	As Integer = &H10
	'#define BRAC_FUNC_CONFIG_LOCALINI   0x00000010L ///< ���ɱ��������ļ���AnyChatSDK.ini��
	Public Const BRAC_FUNC_FIREWALL_OPEN 	As Integer = &H20
	'#define BRAC_FUNC_FIREWALL_OPEN     0x00000020L ///< ����SDK����Windows����ǽ������ǰӦ�ó���������ǽ�����б�����Windows��ʾ�û��Ƿ���ֹ��ǰӦ�ó���
	Public Const BRAC_FUNC_CHKDEPENDMODULE 	As Integer = &H40
	'#define BRAC_FUNC_CHKDEPENDMODULE   0x00000040L ///< �Զ����SDK����������������Զ�ע��
	Public Const BRAC_FUNC_AUDIO_VOLUMECALC As Integer = &H80
	'#define BRAC_FUNC_AUDIO_VOLUMECALC  0x00000080L ///< ��SDK�Զ���������������
	Public Const BRAC_FUNC_AUDIO_AUTOVOLUME As Integer = &H100
	'#define BRAC_FUNC_AUDIO_AUTOVOLUME  0x00000100L ///< ����SDK�Զ�����Mic¼������
	Public Const BRAC_FUNC_NET_SUPPORTUPNP 	As Integer = &H200
	'#define BRAC_FUNC_NET_SUPPORTUPNP   0x00000200L ///< ����SDK���û������е�UPNP�豸������û���·�������Ƿ���ǽ֧��UPNPЭ�飬������P2P�򶴵ĳɹ���
	
	
	'// �ں˲�������
	Public Const BRAC_SO_AUDIO_VADCTRL 		As Integer = 1
	'#define BRAC_SO_AUDIO_VADCTRL               1   ///< ��Ƶ���������ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	Public Const BRAC_SO_AUDIO_NSCTRL 		As Integer = 2
	'#define BRAC_SO_AUDIO_NSCTRL                2   ///< ��Ƶ�������ƿ��ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	Public Const BRAC_SO_AUDIO_ECHOCTRL 	As Integer = 3
	'#define BRAC_SO_AUDIO_ECHOCTRL              3   ///< ��Ƶ�����������ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	Public Const BRAC_SO_AUDIO_AGCCTRL 		As Integer = 4
	'#define BRAC_SO_AUDIO_AGCCTRL               4   ///< ��Ƶ�Զ�������ƣ�����Ϊ��int�ͣ�1�򿪣�0�رգ�
	Public Const BRAC_SO_AUDIO_SOFTVOLMODE	As Integer = 73
	'#define BRAC_SO_AUDIO_SOFTVOLMODE			73	///< �����������ģʽ���ƣ�����Ϊint�ͣ�1�򿪣�0�ر�[Ĭ��]����ʹ���������ģʽ��������ı�ϵͳ
	Public Const BRAC_SO_AUDIO_RECORDDRVCTRL As Integer= 74
	'#define BRAC_SO_AUDIO_RECORDDRVCTRL			74	///< ��Ƶ�ɼ��������ƣ�����Ϊint�ͣ�0Ĭ�������� 1 DSound������ 2 WaveIn������ 3 Java�ɼ�[Androidƽ̨ʹ��]��
	
	Public Const BRAC_SO_RECORD_VIDEOBR 	As Integer = 10
	'#define BRAC_SO_RECORD_VIDEOBR              10  ///< ¼����Ƶ�������ã�����Ϊ��int�ͣ���λ��bps��
	Public Const BRAC_SO_RECORD_AUDIOBR 	As Integer = 11
	'#define BRAC_SO_RECORD_AUDIOBR              11  ///< ¼����Ƶ�������ã�����Ϊ��int�ͣ���λ��bps��
	Public Const BRAC_SO_RECORD_TMPDIR 		As Integer = 12
	'#define BRAC_SO_RECORD_TMPDIR               12  ///< ¼���ļ���ʱĿ¼���ã�����Ϊ�ַ���PCHAR���ͣ�
	Public Const BRAC_SO_SNAPSHOT_TMPDIR 	As Integer = 13
	'#define BRAC_SO_SNAPSHOT_TMPDIR             13  ///< �����ļ���ʱĿ¼���ã�����Ϊ�ַ���PCHAR���ͣ�
	Public Const BRAC_SO_CORESDK_TMPDIR 	As Integer = 14
	'#define BRAC_SO_CORESDK_TMPDIR              14  ///< ����AnyChat Core SDK��ʱĿ¼������Ϊ�ַ���PCHAR���ͣ�
	
	Public Const BRAC_SO_CORESDK_PATH 		As Integer = 20
    '#define BRAC_SO_CORESDK_PATH                20  ///< ����AnyChat Core SDK������·��������Ϊ�ַ���PCHAR���ͣ�

    Public Const BRAC_SO_NETWORK_P2PPOLITIC As Integer = 40
	'#define BRAC_SO_NETWORK_P2PPOLITIC			40	///< ��������P2P���Կ��ƣ�����Ϊ��int�ͣ�0 ��ֹ����P2P��1 ����������P2P[Ĭ��]��2 �ϲ�Ӧ�ÿ���P2P���ӣ�3 ���轨��P2P���ӣ�
	Public Const BRAC_SO_NETWORK_P2PCONNECT As Integer = 41
	'#define BRAC_SO_NETWORK_P2PCONNECT			41	///< ������ָ���û�����P2P���ӣ�����Ϊint�ͣ���ʾĿ���û�ID�������ӽ����ɹ��󣬻�ͨ����Ϣ�������ϲ�Ӧ�ã�P2P���Ʋ���=2ʱ��Ч
	Public Const BRAC_SO_NETWORK_P2PBREAK 	As Integer = 42
	'#define BRAC_SO_NETWORK_P2PBREAK			42	///< �Ͽ���ָ���û���P2P���ӣ�����Ϊint�ͣ���ʾĿ���û�ID��[�ݲ�֧�֣�����]

    Public Const BRAC_SOPROXY_FUNCTIONCTRL 	As Integer = 50
    '#define BRAC_SO_PROXY_FUNCTIONCTRL			50	///< �����û������ܿ��ƣ�������Ϊ��int�ͣ�1��������0�رմ���[Ĭ��]��

    Public Const BRAC_SO_PROXY_VIDEOCTRL 	As Integer = 51
    '#define BRAC_SO_PROXY_VIDEOCTRL			51	///< �����û�������Ƶ���ƣ���������Ƶ��Ϊָ���û�����Ƶ���ⷢ��������Ϊint�ͣ���ʾ�����û���userid��

    Public Const BRAC_SO_PROXY_AUDIOCTRL 	As Integer = 52
    '#define BRAC_SO_PROXY_AUDIOCTRL			52	///< �����û�������Ƶ���ƣ���������Ƶ��Ϊָ���û�����Ƶ���ⷢ��������ͬBRAC_SO_PROXY_VIDEOCTRL��

    Public Const BRAC_SO_STREAM_BUFFERTIME 	As Integer = 60
    '#define BRAC_SO_STREAM_BUFFERTIME			60	///< ������ʱ�䣨����Ϊint�ͣ���λ�����룬ȡֵ��Χ��500 ~ 5000��Ĭ�ϣ�800��
	Public Const BRAC_SO_STREAM_SMOOTHPLAYMODE As Integer = 61
	'#define BRAC_SO_STREAM_SMOOTHPLAYMODE		61	///< ƽ������ģʽ������Ϊint�ͣ�0 �ر�[Ĭ��], 1 �򿪣�����״̬��������Ƶ��֡ʱ��������ţ����ܳ��������ˣ������Ῠס
	
	Public Const BRAC_SO_CORESDK_TICKOUTUSER 	As Integer = 110
	'#define BRAC_SO_CORESDK_TICKOUTUSER		110	///< �ӷ��������ߵ�ָ���û�������Ϊint�ͣ���ʾĿ���û�ID��
	Public Const BRAC_SO_CORESDK_DEVICEMODE 	As Integer = 130
	'#define BRAC_SO_CORESDK_DEVICEMODE			130	///< �豸ģʽ���ƣ��������豸֮����Ի���ͨ�ţ�������������������Ϊint�ͣ�0 �ر�[Ĭ��]��1 ������
	Public Const BRAC_SO_CORESDK_SCREENCAMERACTRL 	As Integer = 131
	'#define BRAC_SO_CORESDK_SCREENCAMERACTRL	131	///< ���湲���ܿ��ƣ�����Ϊ��int�ͣ� 0 �ر�[Ĭ��]�� 1 ������
	Public Const BRAC_SO_CORESDK_DATAENCRYPTION As Integer = 132
	'#define BRAC_SO_CORESDK_DATAENCRYPTION		132	///< ���ݼ��ܿ��ƣ�����Ϊ��int�ͣ� 0 �ر�[Ĭ��]�� 1 ������

	'// ����������Ϣ��������
	Public Const BRAC_TRANSTASK_PROGRESS 	As Short = 1
	'#define BRAC_TRANSTASK_PROGRESS             1   ///< ����������Ȳ�ѯ������Ϊ��DOUBLE�ͣ�0.0 ~ 100.0����
	Public Const BRAC_TRANSTASK_BITRATE 	As Short = 2
	'#define BRAC_TRANSTASK_BITRATE              2   ///< ��������ǰ�������ʣ�����Ϊ��int�ͣ���λ��bps��
	Public Const BRAC_TRANSTASK_STATUS 		As Short = 3
	'#define BRAC_TRANSTASK_STATUS               3   ///< ��������ǰ״̬������Ϊ��int�ͣ�
	
	
	'// ¼���ܱ�־����
	Public Const BRAC_RECORD_FLAGS_VIDEO 	As Integer = &H1
	'#define BRAC_RECORD_FLAGS_VIDEO     0x00000001L ///< ¼����Ƶ
	Public Const BRAC_RECORD_FLAGS_AUDIO 	As Integer = &H2
	'#define BRAC_RECORD_FLAGS_AUDIO     0x00000002L ///< ¼����Ƶ
	Public Const BRAC_RECORD_FLAGS_SERVER 	As Integer = &H4
	'#define BRAC_RECORD_FLAGS_SERVER	0x00000004L	///< ��������¼��
	Public Const BRAC_RECORD_FLAGS_MIXAUDIO As Integer = &H10
	'#define BRAC_RECORD_FLAGS_MIXAUDIO	0x00000010L	///< ¼����Ƶʱ���������˵�����������¼��
	Public Const BRAC_RECORD_FLAGS_MIXVIDEO As Integer = &H20
	'#define BRAC_RECORD_FLAGS_MIXVIDEO	0x00000020L	///< ¼����Ƶʱ���������˵���Ƶ���Ӻ�¼��
	Public Const BRAC_RECORD_FLAGS_ABREAST As Integer = &H100
	'#define BRAC_RECORD_FLAGS_ABREAST	0x00000100L	///< ¼����Ƶʱ���������˵���Ƶ����¼��
	
	
	'// �û�״̬��־����
	Public Const BRAC_USERSTATE_CAMERA 		As Integer = 1
	'#define BRAC_USERSTATE_CAMERA          1   ///< �û�����ͷ״̬������Ϊint�ͣ�
	Public Const BRAC_USERSTATE_HOLDMIC 	As Integer = 2
	'#define BRAC_USERSTATE_HOLDMIC         2   ///< �û���Ƶ�豸״̬������ΪDWORD�ͣ�����ֵ��0 ��Ƶ�ɼ��رգ� 1 ��Ƶ�ɼ�������
	Public Const BRAC_USERSTATE_SPEAKVOLUME As Integer = 3
	'#define BRAC_USERSTATE_SPEAKVOLUME		3   ///< �û���ǰ˵������������ΪDOUBLE���ͣ�0.0 ~ 100.0����
	Public Const BRAC_USERSTATE_RECORDING 	As Integer = 4
	'#define BRAC_USERSTATE_RECORDING 		4   ///< �û�¼������״̬������Ϊint�ͣ�
	Public Const BRAC_USERSTATE_LEVEL 		As Integer = 5
	'#define BRAC_USERSTATE_LEVEL			5   ///< �û����𣨲���Ϊint�ͣ�
	Public Const BRAC_USERSTATE_NICKNAME 	As Integer = 6
	'#define BRAC_USERSTATE_NICKNAME		6   ///< �û��ǳƣ�����Ϊ�ַ���PCHAR���ͣ�
	Public Const BRAC_USERSTATE_LOCALIP 	As Integer = 7
	'#define BRAC_USERSTATE_LOCALIP			7   ///< �û�����IP��ַ������������Ϊ�ַ���PCHAR���ͣ�
	Public Const BRAC_USERSTATE_INTERNETIP 	As Integer = 8
	'#define BRAC_USERSTATE_INTERNETIP		8   ///< �û�������IP��ַ������Ϊ�ַ���PCHAR���ͣ�
	Public Const BRAC_USERSTATE_VIDEOBITRATE As Integer = 9
	'#define BRAC_USERSTATE_VIDEOBITRATE	9	///< �û���ǰ����Ƶ���ʣ�����ΪDWORD���ͣ�Bps��
	Public Const BRAC_USERSTATE_AUDIOBITRATE As Integer = 10
	'#define BRAC_USERSTATE_AUDIOBITRATE	10	///< �û���ǰ����Ƶ���ʣ�����ΪDWORD���ͣ�Bps��
	Public Const BRAC_USERSTATE_P2PCONNECT As Integer = 11
	'#define BRAC_USERSTATE_P2PCONNECT		11	///< ��ѯ�����û���ָ���û��ĵ�ǰP2P����״̬������ΪDWORD���ͣ�����ֵ��0 P2P��ͨ�� 1 P2P���ӳɹ�[TCP]��2 P2P���ӳɹ�[UDP]��3 P2P���ӳɹ�[TCP��UDP]��
	Public Const BRAC_USERSTATE_NETWORKSTATUS As Integer = 12
	'#define BRAC_USERSTATE_NETWORKSTATUS	12	///< ��ѯָ���û�������״̬������ΪDWORD���ͣ�����ֵ��0 ������1 �Ϻã�2 һ�㣬3 �ϲ4 �ǳ����ע����ѯ�����Ҫ>1s
	Public Const BRAC_USERSTATE_VIDEOSIZE As Integer = 13
	'#define BRAC_USERSTATE_VIDEOSIZE		13	///< ��ѯָ���û�����Ƶ�ֱ��ʣ�����ΪDWORD���ͣ�����ֵ����16λ��ʾ��ȣ���16λ��ʾ�߶ȣ�
	Public Const BRAC_USERSTATE_PACKLOSSRATE As Integer = 14
	'#define BRAC_USERSTATE_PACKLOSSRATE	14	///< ��ѯָ���û���������ý�����ݶ����ʣ�����ΪDWORD���ͣ�����ֵ��0 - 100���磺����ֵΪ5����ʾ������Ϊ5%��
	Public Const BRAC_USERSTATE_DEVICETYPE As Integer = 15
	'#define BRAC_USERSTATE_DEVICETYPE		15	///< ��ѯָ���û����ն����ͣ�����ΪDWORD���ͣ�����ֵ��0 Unknow�� 1 Windows��2 Android��3 iOS��4 Web��5 Linux��6 Mac��7 Win Phone��8 WinCE��	
	Public Const BRAC_USERSTATE_SELFUSERSTATUS As Integer =	16
	'#define BRAC_USERSTATE_SELFUSERSTATUS		16	///< ��ѯ�����û��ĵ�ǰ״̬������ΪDWORD���ͣ�����ֵ��0 Unknow��1 Connected��2 Logined��3 In Room��4 Logouted��5 Link Closed��
	Public Const BRAC_USERSTATE_SELFUSERID As Integer =	17
	'#define BRAC_USERSTATE_SELFUSERID			17	///< ��ѯ�����û���ID������ΪDWORD���ͣ����û���¼�ɹ��������û�ʵ�ʵ�userid�����򷵻�-1��
	
	'// ��Ƶ�����¼����Ͷ��壨API��BRAC_VideoCallControl ���������VideoCallEvent�ص�������
	'#define BRAC_VIDEOCALL_EVENT_REQUEST		1	///< ��������
	Public Const BRAC_VIDEOCALL_EVENT_REQUEST As Integer = 1
	'#define BRAC_VIDEOCALL_EVENT_REPLY			2	///< ��������ظ�
	Public Const BRAC_VIDEOCALL_EVENT_REPLY As Integer = 2
	'#define BRAC_VIDEOCALL_EVENT_START			3	///< ��Ƶ���лỰ��ʼ�¼�
	Public Const BRAC_VIDEOCALL_EVENT_START As Integer = 3
	'#define BRAC_VIDEOCALL_EVENT_FINISH			4	///< �Ҷϣ����������лỰ
	Public Const BRAC_VIDEOCALL_EVENT_FINISH As Integer = 4

	'// ��Ƶ���б�־���壨API��BRAC_VideoCallControl ���������
	'#define BRAC_VIDEOCALL_FLAGS_AUDIO		0x01	///< ����ͨ��
	Public Const BRAC_VIDEOCALL_FLAGS_AUDIO As Integer = &H1
	'#define BRAC_VIDEOCALL_FLAGS_VIDEO		0x02	///< ��Ƶͨ��
	Public Const BRAC_VIDEOCALL_FLAGS_VIDEO As Integer = &H2
	'#define BRAC_VIDEOCALL_FLAGS_FBSRCAUDIO	0x10	///< ��ֹԴ�����жˣ���Ƶ
	Public Const BRAC_VIDEOCALL_FLAGS_FBSRCAUDIO As Integer = &H10
	'#define BRAC_VIDEOCALL_FLAGS_FBSRCVIDEO	0x20	///< ��ֹԴ�����жˣ���Ƶ
	Public Const BRAC_VIDEOCALL_FLAGS_FBSRCVIDEO As Integer = &H20
	'#define BRAC_VIDEOCALL_FLAGS_FBTARAUDIO	0x40	///< ��ֹĿ�꣨�����жˣ���Ƶ
	Public Const BRAC_VIDEOCALL_FLAGS_FBTARAUDIO As Integer = &H40
	'#define BRAC_VIDEOCALL_FLAGS_FBTARVIDEO	0x80	///< ��ֹĿ�꣨�����жˣ���Ƶ
	Public Const BRAC_VIDEOCALL_FLAGS_FBTARVIDEO As Integer = &H80
	
	'// Զ����Ƶ����������־����
	'#define BRAC_ROTATION_FLAGS_MIRRORED	0x1000	///< ͼ����Ҫ����ת
	Public Const BRAC_ROTATION_FLAGS_MIRRORED As Integer = &H1000
	'#define BRAC_ROTATION_FLAGS_ROTATION90	0x2000	///< ˳ʱ����ת90��
	Public Const BRAC_ROTATION_FLAGS_ROTATION90 As Integer = &H2000
	'#define BRAC_ROTATION_FLAGS_ROTATION180	0x4000	///< ˳ʱ����ת180��
	Public Const BRAC_ROTATION_FLAGS_ROTATION180 As Integer = &H4000
	'#define BRAC_ROTATION_FLAGS_ROTATION270	0x8000	///< ˳ʱ����ת270��
	Public Const BRAC_ROTATION_FLAGS_ROTATION270 As Integer = &H8000

	'// �û���Ϣ�������Ͷ��壨API��BRAC_UserInfoControl ���������
	'#define BRAC_USERINFO_CTRLCODE_ROTATION		8	///< ��ָ�����û���Ƶ����ʾʱ��ת��wParamΪ��ת�ǶȲ���
	Public Const BRAC_USERINFO_CTRLCODE_ROTATION As Integer = 8

	'// ���ݼӣ��⣩�ܱ�־���壨DataEncDec�ص�������
	'#define BRAC_DATAENCDEC_FLAGS_ENCMODE	0x01	///< ����ģʽ
	Public Const BRAC_DATAENCDEC_FLAGS_ENCMODE As Integer = &H1
	'#define BRAC_DATAENCDEC_FLAGS_DECMODE	0x02	///< ����ģʽ
	Public Const BRAC_DATAENCDEC_FLAGS_DECMODE As Integer = &H2
	'#define BRAC_DATAENCDEC_FLAGS_AUDIO		0x10	///< ��Ƶ��������
	Public Const BRAC_DATAENCDEC_FLAGS_AUDIO As Integer = &H10
	'#define BRAC_DATAENCDEC_FLAGS_VIDEO		0x20	///< ��Ƶ��������
	Public Const BRAC_DATAENCDEC_FLAGS_VIDEO As Integer = &H20
	'#define BRAC_DATAENCDEC_FLAGS_BUFFER	0x40	///< ͸��ͨ������
	Public Const BRAC_DATAENCDEC_FLAGS_BUFFER As Integer = &H40
	'#define BRAC_DATAENCDEC_FLAGS_TXTMSG	0x80	///< ������������
	Public Const BRAC_DATAENCDEC_FLAGS_TXTMSG As Integer = &H80
	

    'ע�⣺���е�BOOL���Ͷ�����Ϊinteger����

	Public Structure BITMAPINFOHEADER
        Public biSize As Integer
        Public biWidth As Integer
        Public biHeight As Integer
        Public biPlanes As Short
        Public biBitCount As Short
        Public biCompression As Integer
        Public biSizeImage As Integer
        Public biXPelsPerMeter As Integer
        Public biyPelsPerMeter As Integer
        Public biClrUsed As Integer
        Public biClrImportant As Integer
    End Structure
    '// ��Ƶ���ݻص���������
    'typedef void (CALLBACK * BRAC_VideoData_CallBack)(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, BITMAPINFOHEADER bmiHeader, LPVOID lpUserValue);
    Public Delegate Sub BRAC_VideoData_CallBack(ByVal dwUserid As Integer, ByVal lpBuf As IntPtr, ByVal dwLen As Integer, ByVal bmiHeader As BITMAPINFOHEADER, ByVal lpUserValue As IntPtr)

    Public Structure WAVEFORMATEX
        Public wFormatTag As Short
        Public nChannels As Short
        Public nSamplesPesSec As Integer
        Public nAvgBytesPerSec As Integer
        Public nBlockAlign As Short
        Public wBitsPerSample As Short
        Public cbSize As Short
    End Structure
    '// ��Ƶ���ݻص���������
    'typedef void (CALLBACK * BRAC_AudioData_CallBack)(DWORD dwUserid, LPVOID lpBuf, DWORD dwLen, WAVEFORMATEX waveFormatEx, LPVOID lpUserValue);
    Public Delegate Sub BRAC_AudioData_CallBack(ByVal dwUserid As Integer, ByVal lpBuf As IntPtr, ByVal dwLen As Integer, ByVal waveFormatEx As WAVEFORMATEX, ByVal lpUserValue As IntPtr)

    '// ������Ϣ�ص���������
    'typedef void (CALLBACK * BRAC_TextMessage_CallBack)(DWORD dwFromUserid, DWORD dwToUserid, BOOL bSecret, LPCTSTR lpMsgBuf, DWORD dwLen, LPVOID lpUserValue);
    Public Delegate Sub BRAC_TextMessage_CallBack(ByVal dwFromUserid As Integer, ByVal dwTouserid As Integer, ByVal bSecret As Integer, ByVal lpMsgbuf As IntPtr, ByVal dwLen As Integer, ByVal lpUserValue As IntPtr)

    '// ͸��ͨ�����ݻص���������
    'typedef void (CALLBACK * BRAC_TransBuffer_CallBack)(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue);
    Public Delegate Sub BRAC_TransBuffer_CallBack(ByVal dwUserid As Integer, ByVal lpBuf As Byte(), ByVal dwLen As Integer, ByVal lpUserValue As IntPtr)

    '// ͸��ͨ��������չ�ص���������
    'typedef void (CALLBACK * BRAC_TransBufferEx_CallBack)(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
    Public Delegate Sub BRAC_TransBufferEx_CallBack(ByVal dwUserid As Integer, ByVal lpBuf As Byte(), ByVal dwLen As Integer, ByVal wParam As Integer, ByVal lParam As Integer, ByVal dwTaskId As Integer, ByVal lpUserValue As IntPtr)

    '// �ļ�����ص���������
    'typedef void (CALLBACK * BRAC_TransFile_CallBack)(DWORD dwUserid, LPCTSTR lpFileName, LPCTSTR lpTempFilePath, DWORD dwFileLength, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
    Public Delegate Sub BRAC_TransFile_CallBack(ByVal dwUserid As Integer, ByVal lpFileName As String, ByVal lpTempFilePath As String, ByVal dwFileLength As Integer, ByVal wParam As Integer, ByVal lParam As Integer, ByVal dwTaskId As Integer, ByVal lpUserValue As IntPtr)

    'enum BRAC_AudioDevice{
    'BRAC_AD_WAVEIN = 0,							///< �����豸��Mic
    'BRAC_AD_WAVEOUT,							///< ����豸��Wave
    '};
    Public Enum BRAC_AudioDevice
        BRAC_AD_WAVEIN = 0
        BRAC_AD_WAVEOUT
    End Enum
    '// �����仯�ص���������
    'typedef void (CALLBACK * BRAC_VolumeChange_CallBack)(BRAC_AudioDevice device, DWORD dwCurrentVolume, LPVOID lpUserValue);
    Public Delegate Sub BRAC_VolumeChange_CallBack(ByVal device As BRAC_AudioDevice, ByVal dwCurrentVolume As Integer, ByVal lpUserValue As IntPtr)

    '// SDK Filter ͨ�����ݻص���������
    'typedef void (CALLBACK * BRAC_SDKFilterData_CallBack)(LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue);
    Public Delegate Sub BRAC_SDKFilterData_CallBack(ByVal lpBuf As Byte(), ByVal dwLen As Integer, ByVal lpUserValue As IntPtr)

    '// ¼�񡢿���������ɻص���������
    'typedef void (CALLBACK * BRAC_RecordSnapShot_CallBack)(DWORD dwUserid, LPCTSTR lpFileName, DWORD dwParam, BOOL bRecordType, LPVOID lpUserValue);
    Public Delegate Sub BRAC_RecordSnapShot_CallBack(ByVal dwUserid As Integer, ByVal lpFileName As String, ByVal dwParam As Integer, ByVal bRecordType As Integer, ByVal lpUserValue As IntPtr)

    '// �첽��Ϣ֪ͨ�ص���������
    'typedef void (CALLBACK* BRAC_NotifyMessage_CallBack)(DWORD dwNotifyMsg, DWORD wParam, DWORD lParam, LPVOID lpUserValue);
    Public Delegate Sub BRAC_NotifyMessage_CallBack(ByVal dwNotifyMsg As Integer, ByVal wParam As Integer, ByVal lParam As Integer, ByVal lpUserValue As IntPtr)
	
	'// ��Ƶͨ����Ϣ֪ͨ�ص���������
	'typedef void (CALLBACK * BRAC_VideoCallEvent_CallBack)(DWORD dwEventType, DWORD dwUserId, DWORD dwErrorCode, DWORD dwFlags, DWORD dwParam, LPCTSTR lpUserStr, LPVOID lpUserValue);
    Public Delegate Sub BRAC_VideoCallEvent_CallBack(ByVal dwEventType As Integer, ByVal dwUserId As Integer, ByVal dwErrorCode As Integer, ByVal dwFlags As Integer, ByVal dwParam As Integer, ByVal lpUserStr As String, ByVal lpUserValue As IntPtr)
	
	'// ���ݼ��ܡ����ܻص���������
	'typedef DWORD (CALLBACK * BRAC_DataEncDec_CallBack)(DWORD dwUserId, DWORD dwFlags, LPBYTE lpInBuf, DWORD dwInSize, LPBYTE lpOutBuf, LPDWORD lpOutSize, LPVOID lpUserValue);
    Public Delegate Sub BRAC_DataEncDec_CallBack(ByVal dwUserId As Integer, ByVal dwFlags As Integer, ByVal lpInBuf As Byte(), ByVal dwInSize As Integer, ByVal lpOutBuf As Byte(), ByVal lpOutSize As IntPtr, ByVal lpUserValue As IntPtr)

    '/**
    ' *  API��������
    ' */
    'ע�⣺���ｫLPSTR���͵Ĳ���lpCompileTime����Ϊ����IntPtr����ʹ��Marshal.StringToHGlobalAnsi������һ���йܵ�String�ַ������Ʋ�����һ�����йܵĻ�����ָ�롣ע�⣬��ǵ�ʹ��Marshal.FreeHGlobal�ͷŸ�ָ�롣
    '// ��ȡSDK�汾��Ϣ
    'BRAC_API DWORD BRAC_GetSDKVersion(DWORD& dwMainVer, DWORD& dwSubVer, LPSTR lpCompileTime, DWORD dwBufLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetSDKVersion", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetSDKVersion(ByRef dwMainVer As Integer, ByRef dwSubVer As Integer, ByVal lpCompileTime As IntPtr, ByVal dwBufLen As Integer) As Integer
    End Function

    '// ����رգ�SDK������־
    'BRAC_API DWORD BRAC_ActiveCallLog(BOOL bActive);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_ActiveCallLog", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_ActiveCallLog(ByVal bActive As Integer) As Integer
    End Function

    '// ���÷�������֤���루��������ֹ�Ƿ��û���SDK���ӷ��������Ϸ��û����������ӣ�
    'BRAC_API DWORD BRAC_SetServerAuthPass(LPCTSTR lpPassword);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetServerAuthPass", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetServerAuthPass(ByVal lpPassword As String) As Integer
    End Function

    '// ��ʼ��ϵͳ
    'BRAC_API DWORD BRAC_InitSDK(HWND hWnd, DWORD dwFuncMode);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_InitSDK", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_InitSDK(ByVal hwnd As IntPtr, ByVal dwFuncMode As Integer) As Integer
    End Function

    '// ��Ƶͼ���ʽ����
    'enum BRAC_PixelFormat{
    'BRAC_PIX_FMT_RGB24 = 0,						///< Packed RGB 8:8:8, 24bpp, RGBRGB...��MEDIASUBTYPE_RGB24��
    'BRAC_PIX_FMT_RGB32,							///< ��Ӧ�ڣ�MEDIASUBTYPE_RGB32��Packed RGB 8:8:8, 32bpp, (msb)8A 8R 8G 8B(lsb), in cpu endianness
    'BRAC_PIX_FMT_YV12,							///< ��Ӧ�ڣ�MEDIASUBTYPE_YV12��Planar YUV 4:2:0, 12bpp, (1 Cr & Cb sample per 2x2 Y samples)
    'BRAC_PIX_FMT_YUY2,							///< ��Ӧ�ڣ�MEDIASUBTYPE_YUY2��Packed YUV 4:2:2, 16bpp, Y0 Cb Y1 Cr
    '};
    Public Enum BRAC_PixelFormat
        BRAC_PIX_FMT_RGB24 = 0
        BRAC_PIX_FMT_RGB32
        BRAC_PIX_FMT_YV12
        BRAC_PIX_FMT_YUY2
    End Enum
    '// ������Ƶ���ݻص�����
    'BRAC_API DWORD BRAC_SetVideoDataCallBack(BRAC_PixelFormat pixFmt, BRAC_VideoData_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetVideoDataCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetVideoDataCallBack(ByVal pixFmt As BRAC_PixelFormat, ByVal lpFunction As BRAC_VideoData_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// ������Ƶ���ݻص�����
    'BRAC_API DWORD BRAC_SetAudioDataCallBack(BRAC_AudioData_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetAudioDataCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetAudioDataCallBack(ByVal lpFunction As BRAC_AudioData_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function
    

    '// ����������Ϣ�ص�����
    'BRAC_API DWORD BRAC_SetTextMessageCallBack(BRAC_TextMessage_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetTextMessageCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetTextMessageCallBack(ByVal lpFunction As BRAC_TextMessage_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// ����͸��ͨ�����ݻص�����
    'BRAC_API DWORD BRAC_SetTransBufferCallBack(BRAC_TransBuffer_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetTransBufferCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetTransBufferCallBack(ByVal lpFunction As BRAC_TransBuffer_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// ����͸��ͨ��������չ�ص�����
    'BRAC_API DWORD BRAC_SetTransBufferExCallBack(BRAC_TransBufferEx_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetTransBufferExCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetTransBufferExCallBack(ByVal lpFunction As BRAC_TransBufferEx_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// �����ļ�����ص�����
    'BRAC_API DWORD BRAC_SetTransFileCallBack(BRAC_TransFile_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetTransFileCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetTransFileCallBack(ByVal lpFunction As BRAC_TransFile_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// ���������仯�ص�����
    'BRAC_API DWORD BRAC_SetVolumeChangeCallBack(BRAC_VolumeChange_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetVolumeChangeCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetVolumeChangeCallBack(ByVal lpFunction As BRAC_VolumeChange_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// ����SDK Filterͨ�����ݻص���������
    'BRAC_API DWORD BRAC_SetSDKFilterDataCallBack(BRAC_SDKFilterData_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetSDKFilterDataCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetSDKFilterDataCallBack(ByVal lpFunction As BRAC_SDKFilterData_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// ����¼������������֪ͨ�ص�����
    'BRAC_API DWORD BRAC_SetRecordSnapShotCallBack(BRAC_RecordSnapShot_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetRecordSnapShotCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetRecordSnapShotCallBack(ByVal lpFunction As BRAC_RecordSnapShot_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

    '// �����첽��Ϣ֪ͨ�ص�����
    'BRAC_API DWORD BRAC_SetNotifyMessageCallBack(BRAC_NotifyMessage_CallBack lpFunction, LPVOID lpUserValue=NULL);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetNotifyMessageCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetNotifyMessageCallBack(ByVal lpFunction As BRAC_NotifyMessage_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function

	'// ������Ƶͨ����Ϣ֪ͨ�ص�����
	'BRAC_API DWORD BRAC_SetVideoCallEventCallBack(BRAC_VideoCallEvent_CallBack lpFunction, LPVOID lpUserValue=NULL);
	<DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetVideoCallEventCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetVideoCallEventCallBack(ByVal lpFunction As BRAC_VideoCallEvent_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function
	
	'// �������ݼ��ܡ����ܻص�����
	'BRAC_API DWORD BRAC_SetDataEncDecCallBack(BRAC_DataEncDec_CallBack lpFunction, LPVOID lpUserValue=NULL);
	<DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetDataEncDecCallBack", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetDataEncDecCallBack(ByVal lpFunction As BRAC_DataEncDec_CallBack, ByVal lpUserValue As IntPtr) As Integer
    End Function


    '// ���ӷ�����
    'BRAC_API DWORD BRAC_Connect(LPCTSTR lpServerAddr, DWORD dwPort);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_Connect", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_Connect(ByVal lpServerAddr As String, ByVal dwPort As Integer) As Integer
    End Function

    '// ��¼ϵͳ
    'BRAC_API DWORD BRAC_Login(LPCTSTR lpUserName, LPCTSTR lpPassword, DWORD dwPassEncType);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_Login", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_Login(ByVal lpUserName As String, ByVal lpPassword As String, ByVal dwPassEntype As Integer) As Integer
    End Function

    '// ���뷿��
    'BRAC_API DWORD BRAC_EnterRoom(DWORD dwRoomid, LPCTSTR lpRoomPass, DWORD dwPassEncType);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_EnterRoom", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_EnterRoom(ByVal dwRoomId As Integer, ByVal lpRoomPass As String, ByVal dwPassEncType As Integer) As Integer
    End Function

    '// ���뷿��
    'BRAC_API DWORD BRAC_EnterRoomEx(LPCTSTR lpRoomName, LPCTSTR lpRoomPass);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_EnterRoomEx", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_EnterRoomEx(ByVal lpRoomName As String, ByVal lpRoomPass As String) As Integer
    End Function

    '// �뿪����
    'BRAC_API DWORD BRAC_LeaveRoom(DWORD dwRoomid);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_LeaveRoom", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_LeaveRoom(ByVal dwRoomId As Integer) As Integer
    End Function

    '// ע��ϵͳ
    'BRAC_API DWORD BRAC_Logout(VOID);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_Logout", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_Logout() As Integer
    End Function

    '// �ͷ�������Դ
    'BRAC_API DWORD BRAC_Release(VOID);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_Release", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_Release() As Integer
    End Function

    '// ��ȡ��ǰ���������û��б�
    'BRAC_API DWORD BRAC_GetOnlineUser(LPDWORD lpUserIDArray, DWORD& dwUserNum);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetOnlineUser", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetOnlineUser(ByRef lpUserIDArray As Integer, ByRef dwUserNum As Integer) As Integer
    End Function

    '// ��ѯ�û�����ͷ��״̬
    'BRAC_API DWORD BRAC_GetCameraState(DWORD dwUserid, DWORD& dwState);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetCameraState", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetCameraState(ByVal dwUserid As Integer, ByRef dwState As Integer) As Integer
    End Function

    '// ��ѯ�û�����״̬
    'BRAC_API DWORD BRAC_GetSpeakState(DWORD dwUserid, DWORD& dwState);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetSpeakState", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetSpeakState(ByVal dwUserid As Integer, ByRef dwState As Integer) As Integer
    End Function

    '// ��ѯ�û�����
    'BRAC_API DWORD BRAC_GetUserLevel(DWORD dwUserid, DWORD& dwLevel);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetUserLevel", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetUserLevel(ByVal dwUserid As Integer, ByRef dwLevel As Integer) As Integer
    End Function

    'ע�⣺���ｫCHAR*���͵Ĳ���lpUserName����Ϊ����IntPtr����ʹ��Marshal.StringToHGlobalAnsi������һ���йܵ�String�ַ������Ʋ�����һ�����йܵĻ�����ָ�롣ע�⣬��ǵ�ʹ��Marshal.FreeHGlobal�ͷŸ�ָ�롣
    '// ��ѯ�û�����
    'BRAC_API DWORD BRAC_GetUserName(DWORD dwUserid, CHAR* lpUserName, DWORD dwLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetUserName", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetUserName(ByVal dwUserid As Integer, ByVal lpUserName As IntPtr, ByVal dwLen As Integer) As Integer
    End Function

    '// ��ѯ��������
    'BRAC_API DWORD BRAC_GetRoomName(DWORD dwRoomId, CHAR* lpRoomName, DWORD dwLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetRoomName", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetRoomName(ByVal dwRoomId As Integer, ByVal lpRoomName As String, ByVal dwLen As Integer) As Integer
    End Function

    '// ��ʾ������Ƶ������ڶԻ���
    'BRAC_API DWORD BRAC_ShowLVProperty(HWND hParent, LPCTSTR szCaption=NULL, DWORD dwX=0, DWORD dwY=0);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_ShowLVProperty", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_ShowLVProperty(ByVal hParent As IntPtr, ByVal szCaption As String, ByVal dwX As Integer, ByVal dwY As Integer) As Integer
    End Function

    'ע�⣺���ｫchar FAR*���͵Ĳ���infoval����Ϊ����IntPtr����ʹ��Marshal.StringToHGlobalAnsi������һ���йܵ�String�ַ������Ʋ�����һ�����йܵĻ�����ָ�롣ע�⣬��ǵ�ʹ��Marshal.FreeHGlobal�ͷŸ�ָ�롣
    '// ��ѯָ���û����״̬
    'BRAC_API DWORD BRAC_QueryUserState(DWORD dwUserId, int infoname, char FAR* infoval, int infolen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_QueryUserState", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_QueryUserState(ByVal dwUserid As Integer, ByVal infoname As Integer, ByVal infoval As IntPtr, ByVal infolen As Integer) As Integer
    End Function

    'ע�⣺SDK�ﲢû��BRAC_QueryuserStateEx�������������ֻ��Ϊ�˷����DWORD���͵�infoval������BRAC_QueryUserState�����ĵ���������ĸú�������һ��ǩ��
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_QueryUserState", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_QueryUserStateEx(ByVal dwUserid As Integer, ByVal infoname As Integer, ByRef infoval As Integer, ByVal infolen As Integer) As Integer
    End Function

    'ע�⣺���ｫCHAR**���͵Ĳ���lpDeviceName����Ϊ���ô��ݵ�IntPtr����,��ע�����ﴫ�ݻ�����CHAR*ָ����ָ������Ϊ�������ڲ������䣬Ӧע���ֹ��ͷŸû�������������μ���ӦSDK�ĵ�
    '// ö�ٱ�����Ƶ�ɼ��豸
    'BRAC_API DWORD BRAC_EnumVideoCapture(CHAR** lpDeviceName, DWORD& dwDeviceNum);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_EnumVideoCapture", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_EnumVideoCapture(ByRef lpDeviceName As IntPtr, ByRef dwDeviceNum As Integer) As Integer
    End Function

    '// ѡ��ָ������Ƶ�ɼ��豸
    'BRAC_API DWORD BRAC_SelectVideoCapture(LPCTSTR szCaptureName);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SelectVideoCapture", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SelectVideoCapture(ByVal szCaptureName As Integer) As Integer
    End Function


    'ע�⣺���ｫCHAR*���͵Ĳ���lpDeviceName����ΪIntPtr���ͣ���ʹ��Marshal.StringToHGlobalAnsi������һ���йܵ�String�ַ������Ʋ�����һ�����йܵĻ�����ָ�롣ע�⣬��ǵ�ʹ��Marshal.FreeHGlobal�ͷŸ�ָ�롣
    '// ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
    'BRAC_API DWORD BRAC_GetCurVideoCapture(CHAR* lpDeviceName, DWORD dwLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetCurVideoCapture", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetCurVideoCapture(ByVal lpDeviceName As IntPtr, ByVal dwLen As Integer) As Integer
    End Function

    'ע�⣺���ｫCHAR**���͵Ĳ���lpDeviceName����Ϊ���ô��ݵ�IntPtr����,��ע�����ﴫ�ݻ�����CHAR*ָ����ָ������Ϊ�������ڲ������䣬Ӧע���ֹ��ͷŸû�������������μ���ӦSDK�ĵ�
    '// ö�ٱ�����Ƶ�ɼ��豸
    'BRAC_API DWORD BRAC_EnumAudioCapture(CHAR** lpDeviceName, DWORD& dwDeviceNum);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_EnumAudioCapture", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_EnumAudioCapture(ByRef lpDeviceName As IntPtr, ByRef dwDeviceNum As Integer) As Integer
    End Function

    '// ѡ��ָ������Ƶ�ɼ��豸
    'BRAC_API DWORD BRAC_SelectAudioCapture(LPCTSTR szCaptureName);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SelectAudioCapture", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SelectAudioCapture(ByVal szCaptureName As String) As Integer
    End Function

    'ע�⣺���ｫCHAR*���͵Ĳ���lpDeviceName����ΪIntPtr���ͣ���ʹ��Marshal.StringToHGlobalAnsi������һ���йܵ�String�ַ������Ʋ�����һ�����йܵĻ�����ָ�롣ע�⣬��ǵ�ʹ��Marshal.FreeHGlobal�ͷŸ�ָ�롣
    '// ��ȡ��ǰʹ�õ���Ƶ�ɼ��豸
    'BRAC_API DWORD BRAC_GetCurAudioCapture(CHAR* lpDeviceName, DWORD dwLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetCurAudioCapture", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetCurAudioCapture(ByVal lpDeviceName As IntPtr, ByVal dwLen As Integer) As Integer
    End Function

    '// �����û���Ƶ
    'BRAC_API DWORD BRAC_UserCameraControl(DWORD dwUserid, BOOL bOpen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_UserCameraControl", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_UserCameraControl(ByVal dwUserid As Integer, ByVal bOpen As Integer) As Integer
    End Function

    '// �����û�����
    'BRAC_API DWORD BRAC_UserSpeakControl(DWORD dwUserid, BOOL bOpen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_UserSpeakControl", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_UserSpeakControl(ByVal dwUserid As Integer, ByVal bOpen As Integer) As Integer
    End Function

    '// ������Ƶ��ʾλ��
    'BRAC_API DWORD BRAC_SetVideoPos(DWORD dwUserid, HWND hWnd, DWORD dwLeft, DWORD dwTop, DWORD dwRight, DWORD dwBottom);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetVideoPos", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetVideoPos(ByVal dwUserid As Integer, ByVal hwnd As IntPtr, ByVal dwLeft As Integer, ByVal dwTop As Integer, ByVal dwRight As Integer, ByVal dwBottom As Integer) As Integer
    End Function

    '// ��ȡָ����Ƶ�豸�ĵ�ǰ����
    'BRAC_API DWORD BRAC_AudioGetVolume(BRAC_AudioDevice device, DWORD& dwVolume);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_AudioGetVolume", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_AudioGetVolume(ByVal device As Integer, ByRef dwVolume As Integer) As Integer
    End Function

    '// ����ָ����Ƶ�豸������
    'BRAC_API DWORD BRAC_AudioSetVolume(BRAC_AudioDevice device, DWORD dwVolume);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_AudioSetVolume", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_AudioSetVolume(ByVal device As BRAC_AudioDevice, ByVal dwVolume As Integer) As Integer
    End Function


    '// �û�������Ƶ¼��
    'BRAC_API DWORD BRAC_StreamRecordCtrl(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_StreamRecordCtrl", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_StreamRecordCtrl(ByVal dwuserId As Integer, ByVal bStartRecord As Integer, ByVal dwFlags As Integer, ByVal dwParam As Integer) As Integer
    End Function


    '// ���û�����Ƶ����ץ�ģ����գ�
    'BRAC_API DWORD BRAC_SnapShot(DWORD dwUserId, DWORD dwFlags, DWORD dwParam);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SnapShot", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SnapShot(ByVal dwuserId As Integer, ByVal dwFlags As Integer, ByVal dwParam As Integer) As Integer
    End Function

    '// ͸��ͨ�����ͻ�����
    'BRAC_API DWORD BRAC_TransBuffer(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_TransBuffer", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_TransBuffer(ByVal dwUserid As Integer, ByVal lpBuf As Byte(), ByVal dwLen As Integer) As Integer
    End Function

    '// ͸��ͨ�����ͻ�������չ
    'BRAC_API DWORD BRAC_TransBufferEx(DWORD dwUserid, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_TransBufferEx", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_TransBufferEx(ByVal dwUserid As Integer, ByVal lpBuf As Byte(), ByVal dwLen As Integer, ByVal wParam As Integer, ByVal lParam As Integer, ByVal dwFlags As Integer, ByRef dwTaskId As Integer) As Integer
    End Function

    '// �����ļ�
    'BRAC_API DWORD BRAC_TransFile(DWORD dwUserid, LPCTSTR lpLocalPathName, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_TransFile", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_TransFile(ByVal dwUserid As Integer, ByVal lpLocalPathName As String, ByVal wParam As Integer, ByVal lParam As Integer, ByVal dwFlags As Integer, ByRef dwTaskId As Integer) As Integer
    End Function

    'ע�⣺���������������Ҫ��֤
    '// ��ѯ�������������Ϣ
    'BRAC_API DWORD BRAC_QueryTransTaskInfo(DWORD dwUserid, DWORD dwTaskId, int infoname, char FAR* infoval, int infolen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_QueryTransTaskInfo", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_QueryTransTaskInfo(ByVal dwUserid As Integer, ByVal dwTaskId As Integer, ByVal infoname As Integer, ByVal infoval As Byte(), ByVal infolen As Integer) As Integer
    End Function

    '// ȡ����������
    'BRAC_API DWORD BRAC_CancelTransTask(DWORD dwUserid, DWORD dwTaskId);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_CancelTransTask", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_CancelTransTask(ByVal dwUserid As Integer, ByVal dwTaskId As Integer) As Integer
    End Function

    '// �����ı���Ϣ
    'BRAC_API DWORD BRAC_SendTextMessage(DWORD dwUserid, BOOL bSecret, LPCTSTR lpMsgBuf, DWORD dwLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SendTextMessage", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SendTextMessage(ByVal dwUserid As Integer, ByVal bSecret As Integer, ByVal lpMsgBuf As String, ByVal dwLen As Integer) As Integer
    End Function

    '// ����SDK Filter ͨ������
    'BRAC_API DWORD BRAC_SendSDKFilterData(LPBYTE lpBuf, DWORD dwLen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SendSDKFilterData", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SendSDKFilterData(ByVal lpbuf As Byte(), ByVal dwLen As Integer) As Integer
    End Function

    '// ���ĵ�ǰ������ģʽ
    'BRAC_API DWORD BRAC_ChangeChatMode(DWORD dwChatMode);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_ChangeChatMode", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_ChangeChatMode(ByVal dwChatMode As Integer) As Integer
    End Function

    '// ��ȡָ���û���ǰ������ģʽ
    'BRAC_API DWORD BRAC_GetUserChatMode(DWORD dwUserid, DWORD& dwChatMode);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetUserChatMode", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetUserChatMode(ByVal dwUserid As Integer, ByRef dwChatMode As Integer) As Integer
    End Function

    '// ������Է�˽�ģ���Է�����˽������
    'BRAC_API DWORD BRAC_PrivateChatRequest(DWORD dwUserid);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_PrivateChatRequest", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_PrivateChatRequest(ByVal dwUserid As Integer) As Integer
    End Function

    '// �ظ��Է���˽������
    'BRAC_API DWORD BRAC_PrivateChatEcho(DWORD dwUserid, DWORD dwRequestid,BOOL bAccept);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_PrivateChatEcho", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_PrivateChatEcho(ByVal dwUserid As Integer, ByVal dwRequestid As Integer, ByVal bAccept As Integer) As Integer
    End Function

    '// �ظ��Է���˽��������չ�����Ը���������룩
    'BRAC_API DWORD BRAC_PrivateChatEchoEx(DWORD dwUserid, DWORD dwRequestid,DWORD dwErrorCode);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_PrivateChatEchoEx", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_PrivateChatEchoEx(ByVal dwUserid As Integer, ByVal dwRequestid As Integer, ByVal dwErrorCode As Integer) As Integer
    End Function

    '// �˳���ĳ�û���˽�ģ����߽�ĳ�û����Լ���˽���б������
    'BRAC_API DWORD BRAC_PrivateChatExit(DWORD dwUserid);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_PrivateChatExit", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_PrivateChatExit(ByVal dwUserid As Integer) As Integer
    End Function

    '// SDK�ں˲�������
    'BRAC_API DWORD BRAC_SetSDKOption(int optname, const char FAR* optval, int optlen);
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetSDKOption", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetSDKOption(ByVal optname As Integer, ByVal optval As String, ByVal optlen As Integer) As Integer
    End Function

    'ע�⣺SDK�ﲢû��BRAC_SetSDKOptionsEx�������������ֻ��Ϊ�˷����������ΪDWORD�Ĳ���optval�ĺ���BRAC_SetSDKOption�����������һ��ǩ��
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_SetSDKOption", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_SetSDKOptionEx(ByVal optname As Integer, ByRef optval As Integer, ByVal optlen As Integer) As Integer
    End Function

    'ע�⣺���ｫ����Ϊchar FAR*�Ĳ���optval����Ϊ����IntPrt,��ʹ��Marshal.StringToHGlobalAnsi������һ���йܵ�String�ַ������Ʋ�����һ�����йܵĻ�����
    '// SDK�ں˲���״̬��ѯ
    'BRAC_API DWORD BRAC_GetSDKOption(int optname, char FAR* optval, int optlen);
    <DllImport(AnyChatSDKDLL, entryPoint:="BRAC_GetSDKOption", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetSDKOption(ByVal optname As Integer, ByVal optval As IntPtr, ByVal optlen As Integer) As Integer
    End Function

    'ע�⣺SDK�ﲢû��BRAC_GetSDKOptionsEx�������������ֻ��Ϊ�˷����������ΪDWORD�Ĳ���optval�ĺ���BRAC_GetSDKOption�����������һ��ǩ��
    <DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_GetSDKOption", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_GetSDKOptionEx(ByVal optname As Integer, ByRef optval As Integer, ByVal optlen As Integer) As Integer
    End Function
	
	'// ��Ƶ�����¼����ƣ����󡢻ظ����Ҷϵȣ�
	'BRAC_API DWORD BRAC_VideoCallControl(DWORD dwEventType, DWORD dwUserId, DWORD dwErrorCode, DWORD dwFlags=0, DWORD dwParam=0, LPCTSTR lpUserStr=NULL);
	<DllImport(AnyChatSDKDLL, EntryPoint:="BRAC_VideoCallControl", CallingConvention:=CallingConvention.Cdecl)> _
    Function BRAC_VideoCallControl(ByVal dwEventType As Integer, ByVal dwUserId As Integer, ByVal dwErrorCode As Integer, ByVal dwFlags As Integer, ByVal dwParam As Integer, ByVal lpUserStr As String) As Integer
    End Function
	
End Module