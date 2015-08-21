using System;
using System.Text;
using System.Runtime.InteropServices;

namespace ANYCHATAPI
{
 /***********************************************************************************
    * ** AnyChat Platform Server SDK
    * *********************************************************************************/
    public class AnyChatServerSDK
    {
        public const string AnyChatServerSDKDll = "AnyChatServerSDK.dll";

        //#define BRAS_SERVERAPPMSG_CONNECTED		1		///< ����AnyChat�������ɹ�
		public const int BRAS_SERVERAPPMSG_CONNECTED 	= 1;
        //#define BRAS_SERVERAPPMSG_DISCONNECT		2		///< ��AnyChat�������Ͽ�����
		public const int BRAS_SERVERAPPMSG_DISCONNECT 	= 2;
		
		// ��������Ϣ���Ͷ��壨�ص��¼���BRAS_OnServerAppMessageEx_CallBack ������
		// #define BRAS_MESSAGE_CORESERVERCONN		10		///< ����ķ�������������Ϣ��wParamΪerrorcode
		public const int BRAS_MESSAGE_CORESERVERCONN	= 10;
		// #define BRAS_MESSAGE_RECORDSERVERCONN	11		///< ��¼���������������Ϣ��wParamΪerrorcode��lParamΪrecordserverid
		public const int BRAS_MESSAGE_RECORDSERVERCONN	= 11;
		// #define BRAS_MESSAGE_LOGINSERVERCONN		12		///< ���¼��������������Ϣ��wParamΪerrorcode��lParamΪloginserverid
		public const int BRAS_MESSAGE_LOGINSERVERCONN	= 12;
		// #define BRAS_MESSAGE_ROOMSERVERCONN		13		///< �뷿���������������Ϣ��wParamΪerrorcode��lParamΪroomserverid
		public const int BRAS_MESSAGE_ROOMSERVERCONN	= 13;
		// #define BRAS_MESSAGE_MEDIASERVERCONN		14		///< ����ý���������������Ϣ��wParamΪerrorcode��lParamΪmediaserverid
		public const int BRAS_MESSAGE_MEDIASERVERCONN	= 14;
		
		// ��Ƶ�����¼����Ͷ��壨API��BRAS_VideoCallControl ���������OnVideoCallEvent�ص�������
		//#define BRAS_VIDEOCALL_EVENT_REQUEST    1        ///< ��������
		public const int BRAS_VIDEOCALL_EVENT_REQUEST 	= 1;
		//#define BRAS_VIDEOCALL_EVENT_REPLY      2        ///< ��������ظ�
		public const int BRAS_VIDEOCALL_EVENT_REPLY 	= 2;
		//#define BRAS_VIDEOCALL_EVENT_START      3        ///< ��Ƶ���лỰ��ʼ�¼�
		public const int BRAS_VIDEOCALL_EVENT_START 	= 3;
		//#define BRAS_VIDEOCALL_EVENT_FINISH     4        ///< �Ҷϣ����������лỰ
		public const int BRAS_VIDEOCALL_EVENT_FINISH 	= 4;
		
        // �û���Ϣ�������Ͷ��壨API��BRAS_UserInfoControl ���������OnUserInfoControl�ص�������
        //#define BRAS_USERINFO_CTRLCODE_KICKOUT		1	///< ��ָ���û���ϵͳ���ߵ�
        public const int BRAS_USERINFO_CTRLCODE_KICKOUT =   1;
        //#define BRAS_USERINFO_CTRLCODE_SYNCDATA		2	///< ��ָ���û�������ͬ�����ͻ���
        public const int BRAS_USERINFO_CTRLCODE_SYNCDATA=	2;
        //#define BRAS_USERINFO_CTRLCODE_BLOCKIP		4	///< ��ֹIP��ַ���ӷ�������lpStrValueΪIP��ַ�ַ�����֧��ͨ�����*��
        public const int BRAS_USERINFO_CTRLCODE_BLOCKIP	=	4;
        //#define BRAS_USERINFO_CTRLCODE_UNBLOCKIP	    5	///< ����IP��ַ���ӷ�������lpStrValueΪIP��ַ�ַ�����֧��ͨ�����*��
        public const int BRAS_USERINFO_CTRLCODE_UNBLOCKIP=  5;
        //#define BRAS_USERINFO_CTRLCODE_ADDGROUP		20	///< ����û����飬wParamΪ����Id��lpStrValueΪ��������
        public const int BRAS_USERINFO_CTRLCODE_ADDGROUP=   20;
        //#define BRAS_USERINFO_CTRLCODE_DELGROUP		21	///< ɾ���û����飬wParamΪ����Id
        public const int BRAS_USERINFO_CTRLCODE_DELGROUP=   21;
        //#define BRAS_USERINFO_CTRLCODE_ADDFRIEND	    22	///< ����û����ѣ�wParamΪ����Id
        public const int BRAS_USERINFO_CTRLCODE_ADDFRIEND=  22;
        //#define BRAS_USERINFO_CTRLCODE_DELFRIEND	    23	///< ɾ���û����ѣ�wParamΪ����Id
        public const int BRAS_USERINFO_CTRLCODE_DELFRIEND=  23;
        //#define BRAS_USERINFO_CTRLCODE_SETGROUPRELATION	24	///< ���ú��������Ĺ�����ϵ��wParamΪ����Id��lParamΪ����Id����ʾ��������ĳ������
        public const int BRAS_USERINFO_CTRLCODE_SETGROUPRELATION=   24;
        //#define BRAS_USERINFO_CTRLCODE_APPDEFINE	    100	///< Ӧ�ò��Զ�����ʼָ��
        public const int BRAS_USERINFO_CTRLCODE_APPDEFINE = 100;

		// �ں˲������ó������壨API��BRAS_SetSDKOption ���������
		public const int BRAS_SO_RECORD_VIDEOBR			= 2;	// ¼����Ƶ�������ã�����Ϊ��int�ͣ���λ��bps��
		public const int BRAS_SO_RECORD_AUDIOBR			= 3;	// ¼����Ƶ�������ã�����Ϊ��int�ͣ���λ��bps��
		public const int BRAS_SO_RECORD_FILETYPE		= 4;	// ¼���ļ��������ã�����Ϊ��int�ͣ� 0 MP4[Ĭ��], 1 WMV, 2 FLV, 3 MP3��
		public const int BRAS_SO_RECORD_WIDTH			= 5;	// ¼����Ƶ������ã�����Ϊ��int�ͣ��磺320��
		public const int BRAS_SO_RECORD_HEIGHT			= 6;	// ¼���ļ��߶����ã�����Ϊ��int�ͣ��磺240��
		public const int BRAS_SO_RECORD_FILENAMERULE	= 7;	// ¼���ļ����������򣨲���Ϊ��int�ͣ�
		public const int BRAS_SO_RECORD_CLIPMODE		= 8;	// ¼����Ƶ�ü�ģʽ������Ϊ��int�ͣ�
		public const int BRAS_SO_CORESDK_WRITELOG		= 20;	// д�������Ϣ����־�ļ���

		// ��ʼ����־��API��BRAS_InitSDK ���������
		public const int BRAS_INITFLAGS_MULTITHREADS	= 1;	//���߳�ģʽ

        // ͸��ͨ����չ�����������־���ں�ʹ�ã�
        public const int BRAS_TRANSBUFFER_FLAGS_RECORDSERVER = 0x010;	//���ݸ�¼�������

        // �ص��������Ͷ��壨API��BRAS_SetCallBack ���������
        public const int BRAS_CBTYPE_APPMESSAGE			= 1;	// ������Ӧ�ó�����Ϣ�ص�
        public const int BRAS_CBTYPE_APPMESSAGEEX		= 2;	// ������Ӧ�ó�����Ϣ����չ���ص�
        public const int BRAS_CBTYPE_ONTIMER			= 3;	// SDK��ʱ���ص�
        public const int BRAS_CBTYPE_VERIFYUSER			= 4;	// �û������֤�ص�
        public const int BRAS_CBTYPE_PERPAREENTERROOM	= 5;	// �û�������뷿��ص�
        public const int BRAS_CBTYPE_USERLOGIN			= 6;	// �û���¼�ɹ��ص�
        public const int BRAS_CBTYPE_USERLOGOUT			= 7;	// �û�ע���ص�
        public const int BRAS_CBTYPE_USERLOGOUTEX		= 8;	// �û�ע������չ���ص�
        public const int BRAS_CBTYPE_USERENTERROOM		= 9;	// �û����뷿��ص�
        public const int BRAS_CBTYPE_USERLEAVEROOM		= 10;	// �û��뿪����ص�
        public const int BRAS_CBTYPE_FILTERDATA			= 11;	// �ϲ�ҵ���Զ������ݻص�
        public const int BRAS_CBTYPE_TEXTMESSAGE		= 12;	// ��������ͨ�����ݻص�
        public const int BRAS_CBTYPE_TRANSBUFFER		= 13;	// ͸��ͨ�����ݻص�
        public const int BRAS_CBTYPE_TRANSBUFFEREX		= 14;	// ͸��ͨ��������չ�ص�
        public const int BRAS_CBTYPE_TRANSFILE			= 15;	// �ļ�����ص�����
        public const int BRAS_CBTYPE_SERVERRECORD		= 16;	// ������¼��ص�
        public const int BRAS_CBTYPE_SERVERRECORDEX		= 17;	// ������¼����չ���ص�
        public const int BRAS_CBTYPE_VIDEOCALL			= 18;	// ��Ƶͨ����Ϣ֪ͨ�ص�
        public const int BRAS_CBTYPE_USERINFOCTRL		= 19;	// �û���Ϣ���ƻص�
        public const int BRAS_CBTYPE_OBJECTEVENT        = 20;	// ҵ������¼��ص�

        // ¼���ܱ�־���壨API��BRAS_StreamRecordCtrl ���������
        public const int ANYCHAT_RECORD_FLAGS_VIDEO		= 0x001;// ¼����Ƶ
        public const int ANYCHAT_RECORD_FLAGS_AUDIO		= 0x002;// ¼����Ƶ
        public const int ANYCHAT_RECORD_FLAGS_SERVER	= 0x004;// ��������¼��
        public const int ANYCHAT_RECORD_FLAGS_MIXAUDIO	= 0x010;// ¼����Ƶʱ���������˵�����������¼��
        public const int ANYCHAT_RECORD_FLAGS_MIXVIDEO	= 0x020;// ¼����Ƶʱ���������˵���Ƶ���Ӻ�¼��
        public const int ANYCHAT_RECORD_FLAGS_ABREAST	= 0x100;// ¼����Ƶʱ���������˵���Ƶ����¼��
        public const int ANYCHAT_RECORD_FLAGS_STEREO	= 0x200;// ¼����Ƶʱ���������˵��������Ϊ��������¼��
        public const int ANYCHAT_RECORD_FLAGS_SNAPSHOT	= 0x400;// ����
        public const int ANYCHAT_RECORD_FLAGS_LOCALCB   = 0x800;// �������ػص�
        public const int ANYCHAT_RECORD_FLAGS_STREAM	= 0x1000;// ����Ƶ������¼�ƣ�Ч�ʸߣ������ܴ�����Ƶ������ת�����⣩
        public const int ANYCHAT_RECORD_FLAGS_USERFILENAME = 0x2000;// �û��Զ����ļ���
		
        // ��Ƶ�ü�ģʽ����
        public const int ANYCHAT_VIDEOCLIPMODE_AUTO         = 0;	// Ĭ��ģʽ�������������вü���Ȼ�����������죬���汣�ֱ����������ü�����ϴ�
        public const int ANYCHAT_VIDEOCLIPMODE_OVERLAP		= 1;	// �ص�ģʽ��ֻȡ�����Ч���֣��Ա�Ե���вü�
        public const int ANYCHAT_VIDEOCLIPMODE_SHRINK		= 2;	// ��Сģʽ����С�����ʵı����������вü�
        public const int ANYCHAT_VIDEOCLIPMODE_STRETCH		= 3;	// ƽ��ģʽ�������вü��������ܵ��»��治�ɱ���
        public const int ANYCHAT_VIDEOCLIPMODE_DYNAMIC		= 4;    // ��̬ģʽ�����ϲ�Ӧ�ø��ݷֱ�����������ʾ���棬���ֻ��治����

        #region AnyChatҵ�����������

        #region �������Ͷ���

        /// <summary>
        /// ��������
        /// </summary>
        public const int ANYCHAT_OBJECT_TYPE_AREA = 4;
        /// <summary>
        /// ���ж���
        /// </summary>
        public const int ANYCHAT_OBJECT_TYPE_QUEUE = 5;
        /// <summary>
        /// �ͷ�����
        /// </summary>
        public const int ANYCHAT_OBJECT_TYPE_AGENT = 6;
        /// <summary>
        /// �ͻ����û�����
        /// </summary>
        public const int ANYCHAT_OBJECT_TYPE_CLIENTUSER = 8;

        #endregion

        #region ͨ�ñ�ʶ��������

        /// <summary>
        /// ��ͨ�ͻ�
        /// </summary>
        public const int ANYCHAT_OBJECT_FLAGS_CLIENT = 0;
        /// <summary>
        /// ��ϯ�û�
        /// </summary>
        public const int ANYCHAT_OBJECT_FLAGS_AGENT = 2;
        /// <summary>
        /// �����û�
        /// </summary>
        public const int ANYCHAT_OBJECT_FLAGS_MANANGER = 4;
        /// <summary>
        /// ��Ч�Ķ���ID
        /// </summary>
        public const int ANYCHAT_INVALID_OBJECT_ID = -1;
        /// <summary>
        /// ҵ�������ݳ�ʼ��
        /// </summary>
        public const int BRAC_SO_OBJECT_INITFLAGS = 200;

        #endregion

        #region �������Զ���_���󹫹�

        /// <summary>
        /// �������Ա�־
        /// </summary>
        public const int ANYCHAT_OBJECT_INFO_FLAGS = 7;
        /// <summary>
        /// ��������
        /// </summary>
        public const int ANYCHAT_OBJECT_INFO_NAME = 8;
        /// <summary>
        /// �������ȼ�
        /// </summary>
        public const int ANYCHAT_OBJECT_INFO_PRIORITY = 9;
        /// <summary>
        /// ����ҵ������
        /// </summary>
        public const int ANYCHAT_OBJECT_INFO_ATTRIBUTE = 10;
        /// <summary>
        /// ��������
        /// </summary>
        public const int ANYCHAT_OBJECT_INFO_DESCRIPTION = 11;
        /// <summary>
        /// �����ǩ�����ͣ��ϲ�Ӧ���Զ���
        /// </summary>
        public const int ANYCHAT_OBJECT_INFO_INTTAG = 12;
        /// <summary>
        /// �����ǩ���ַ������ϲ�Ӧ���Զ���
        /// </summary>
        public const int ANYCHAT_OBJECT_INFO_STRINGTAG = 13;

        #endregion

        #region �������Զ���_��������

        /// <summary>
        /// ��������ͷ�����ϯ������
        /// </summary>
        public const int ANYCHAT_AREA_INFO_AGENTCOUNT = 401;
        /// <summary>
        /// ���������ڷÿ͵��û�����û��������е��û���
        /// </summary>
        public const int ANYCHAT_AREA_INFO_GUESTCOUNT = 402;
        /// <summary>
        /// �����������Ŷӵ��û���
        /// </summary>
        public const int ANYCHAT_AREA_INFO_QUEUEUSERCOUNT = 403;
        /// <summary>
        /// ���������ڶ��е�����
        /// </summary>
        public const int ANYCHAT_AREA_INFO_QUEUECOUNT = 404;

        #endregion

        #region �������Զ���_ҵ�����

        /// <summary>
        /// �Լ��ڸö����е����
        /// </summary>
        public const int ANYCHAT_QUEUE_INFO_MYSEQUENCENO = 501;
        /// <summary>
        /// �����Լ�ǰ����û���
        /// </summary>
        public const int ANYCHAT_QUEUE_INFO_BEFOREUSERNUM = 502;
        /// <summary>
        /// ������е�ʱ��
        /// </summary>
        public const int ANYCHAT_QUEUE_INFO_MYENTERQUEUETIME = 503;
        /// <summary>
        /// ҵ����г��ȣ��ж��������Ŷӣ�
        /// </summary>
        public const int ANYCHAT_QUEUE_INFO_LENGTH = 504;
        /// <summary>
        /// �Լ��ڶ����еĵȴ�ʱ�䣨��λ���룩
        /// </summary>
        public const int ANYCHAT_QUEUE_INFO_WAITTIMESECOND = 508;

        #endregion

        #region �������Զ���_�ͷ�����ϯ��

        /// <summary>
        /// ����״̬
        /// </summary>
        public const int ANYCHAT_AGENT_INFO_SERVICESTATUS = 601;
        /// <summary>
        /// ��ǰ������û�ID
        /// </summary>
        public const int ANYCHAT_AGENT_INFO_SERVICEUSERID = 602;
        /// <summary>
        /// ��ǰ����Ŀ�ʼʱ��
        /// </summary>
        public const int ANYCHAT_AGENT_INFO_SERVICEBEGINTIME = 603;
        /// <summary>
        /// �ۼƷ���ʱ�䣨��λ���룩
        /// </summary>
        public const int ANYCHAT_AGENT_INFO_SERVICETOTALTIME = 604;
        /// <summary>
        /// �ۼƷ�����û���
        /// </summary>
        public const int ANYCHAT_AGENT_INFO_SERVICETOTALNUM = 605;

        #endregion

        #region �ͷ�����ϯ������״̬

        /// <summary>
        /// �رգ��������ṩ����
        /// </summary>
        public const int ANYCHAT_AGENT_STATUS_CLOSEED = 0;
        /// <summary>
        /// �ȴ��У�����ʱ�����û�����
        /// </summary>
        public const int ANYCHAT_AGENT_STATUS_WAITTING = 1;
        /// <summary>
        /// �����У�����Ϊ�û�����
        /// </summary>
        public const int ANYCHAT_AGENT_STATUS_WORKING = 2;
        /// <summary>
        /// ��ͣ����
        /// </summary>
        public const int ANYCHAT_AGENT_STATUS_PAUSED = 3;

        #endregion

        #endregion

        #region AnyChatҵ�񷽷���������

        #region �����������塪��������

        /// <summary>
        /// ����һ������
        /// </summary>
        public const int ANYCHAT_OBJECT_CTRL_CREATE = 2;
        /// <summary>
        /// ͬ���������ݸ�ָ���û�
        /// </summary>
        public const int ANYCHAT_OBJECT_CTRL_SYNCDATA = 3;
        /// <summary>
        /// ���������Ϣ���
        /// </summary>
        public const int ANYCHAT_OBJECT_CTRL_DEBUGOUTPUT = 4;

        #endregion

        #region �����������塪��������

        /// <summary>
        /// �����������
        /// </summary>
        public const int ANYCHAT_AREA_CTRL_USERENTER = 401;
        /// <summary>
        /// �뿪��������
        /// </summary>
        public const int ANYCHAT_AREA_CTRL_USERLEAVE = 402;

        #endregion

        #region �����������塪ҵ�����

        /// <summary>
        /// ����ҵ�����
        /// </summary>
        public const int ANYCHAT_QUEUE_CTRL_USERENTER = 501;
        /// <summary>
        /// �뿪ҵ�����
        /// </summary>
        public const int ANYCHAT_QUEUE_CTRL_USERLEAVE = 502;

        #endregion

        #region �����������塪�ͷ�����ϯ��

        /// <summary>
        /// ��ϯ����״̬����
        /// </summary>
        public const int ANYCHAT_AGENT_CTRL_SERVICESTATUS = 601;
        /// <summary>
        /// ��������
        /// </summary>
        public const int ANYCHAT_AGENT_CTRL_SERVICEREQUEST = 602;
        /// <summary>
        /// ��������
        /// </summary>
        public const int ANYCHAT_AGENT_CTRL_FINISHSERVICE = 604;
        /// <summary>
        /// ��������
        /// </summary>
        public const int ANYCHAT_AGENT_CTRL_EVALUATION = 605;

        #endregion

        #endregion

        #region AnyChatҵ���첽�¼���������

        #region �첽�¼��������塪�����¼�

        /// <summary>
        /// �������ݸ���
        /// </summary>
        public const int ANYCHAT_OBJECT_EVENT_UPDATE = 1;
        /// <summary>
        /// ��������ͬ������
        /// </summary>
        public const int ANYCHAT_OBJECT_EVENT_SYNCDATAFINISH = 2;

        #endregion

        #region �첽�¼��������塪��������

        /// <summary>
        /// ��������״̬�仯
        /// </summary>
        public const int ANYCHAT_AREA_EVENT_STATUSCHANGE = 401;
        /// <summary>
        /// �������������
        /// </summary>
        public const int ANYCHAT_AREA_EVENT_ENTERRESULT = 402;
        /// <summary>
        /// �û������������
        /// </summary>
        public const int ANYCHAT_AREA_EVENT_USERENTER = 403;
        /// <summary>
        /// �û��뿪��������
        /// </summary>
        public const int ANYCHAT_AREA_EVENT_USERLEAVE = 404;
        /// <summary>
        /// �뿪����������
        /// </summary>
        public const int ANYCHAT_AREA_EVENT_LEAVERESULT = 405;

        #endregion

        #region �첽�¼��������塪ҵ�����

        /// <summary>
        /// ҵ�����״̬�仯
        /// </summary>
        public const int ANYCHAT_QUEUE_EVENT_STATUSCHANGE = 501;
        /// <summary>
        /// ����ҵ����н��
        /// </summary>
        public const int ANYCHAT_QUEUE_EVENT_ENTERRESULT = 502;
        /// <summary>
        /// �û�����ҵ�����
        /// </summary>
        public const int ANYCHAT_QUEUE_EVENT_USERENTER = 503;
        /// <summary>
        /// �û��뿪ҵ�����
        /// </summary>
        public const int ANYCHAT_QUEUE_EVENT_USERLEAVE = 504;
        /// <summary>
        /// �뿪ҵ����н��
        /// </summary>
        public const int ANYCHAT_QUEUE_EVENT_LEAVERESULT = 505;

        #endregion

        #region �첽�¼��������塪�ͷ�����ϯ��

        /// <summary>
        /// ��ϯ״̬�仯
        /// </summary>
        public const int ANYCHAT_AGENT_EVENT_STATUSCHANGE = 601;
        /// <summary>
        /// ��ϯ����֪ͨ���ĸ��û����ĸ���ϯ����ҵ��
        /// </summary>
        public const int ANYCHAT_AGENT_EVENT_SERVICENOTIFY = 602;
        /// <summary>
        /// ��ʱû�пͻ�����ȴ�
        /// </summary>
        public const int ANYCHAT_AGENT_EVENT_WAITINGUSER = 603;

        #endregion

        #endregion


		
        /**
		 *    �ص���������
		 */

        // ������Ӧ�ó�����Ϣ�ص���������
        //typedef void (CALLBACK* BRAS_OnServerAppMessage_CallBack)(DWORD dwMsg, LPVOID lpUserValue);
        public delegate void OnServerAppMessageCallBack(int msg, int userValue);
		
		// ������Ӧ�ó�����Ϣ����չ���ص���������
        //typedef void (CALLBACK* BRAS_OnServerAppMessageEx_CallBack)(DWORD dwNotifyMessage, DWORD wParam, DWORD lParam, LPVOID lpUserValue);
        public delegate void OnServerAppMessageExCallBack(int dwNotifyMessage, int wParam, int lParam, int userValue);

		// SDK��ʱ���ص��������壨�ϲ�Ӧ�ÿ����ڸûص��д���ʱ���񣬶�����Ҫ���⿪���̣߳����Ƕ�ʱ����
		//typedef void (CALLBACK* BRAS_OnTimerEvent_CallBack)(LPVOID lpUserValue);
        public delegate void OnTimerEventCallBack(int userValue);

		// �û������֤�ص���������
		// typedef DWORD (CALLBACK* BRAS_VerifyUser_CallBack)(IN LPCTSTR lpUserName,IN LPCTSTR lpPassword, OUT LPDWORD lpUserID, OUT LPDWORD lpUserLevel, OUT LPTSTR lpNickName,IN DWORD dwNCLen, LPVOID lpUserValue);
        public delegate int VerifyUserCallBack(string userName, string password, ref int userID, ref int userLevel, IntPtr nickName, int len,int userValue);

        // �û�������뷿��ص���������
        //typedef DWORD (CALLBACK* BRAS_PrepareEnterRoom_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPCTSTR lpRoomName,LPCTSTR lpPassword, LPVOID lpUserValue);
        public delegate int PrepareEnterRoomCallBack(int userId, int roomId, string roomName, string password, int userValue);

        // �û���¼�ɹ��ص���������
        //typedef void (CALLBACK* BRAS_OnUserLoginAction_CallBack)(DWORD dwUserId, LPCTSTR szUserName, DWORD dwLevel, LPCTSTR szIpAddr, LPVOID lpUserValue);
        public delegate void OnUserLoginActionCallBack(int userId, string userName, int level, string addr, int userValue);

        // �û�ע���ص���������
        //typedef void (CALLBACK* BRAS_OnUserLogoutAction_CallBack)(DWORD dwUserId, LPVOID lpUserValue);
        public delegate void OnUserLogoutActionCallBack(int userId, int userValue);
		
		// �û�ע���ص��������壨��չ��
        //typedef void (CALLBACK* BRAS_OnUserLogoutActionEx_CallBack)(DWORD dwUserId, DWORD dwErrorCode, LPVOID lpUserValue);
        public delegate void OnUserLogoutActionExCallBack(int userId, int errorcode, int userValue);

        // �û����뷿��ص���������
        //typedef void (CALLBACK* BRAS_OnUserEnterRoomAction_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPVOID lpUserValue);
        public delegate void OnUserEnterRoomActionCallBack(int userId, int roomId, int userValue);

        // �û��뿪����ص���������
        //typedef void (CALLBACK* BRAS_OnUserLeaveRoomAction_CallBack)(DWORD dwUserId, DWORD dwRoomId, LPVOID lpUserValue);
        public delegate void OnUserLeaveRoomActionCallBack(int userId, int roomId, int userValue);

        // �ϲ�ҵ���Զ������ݻص���������
        //typedef void (CALLBACK* BRAS_OnRecvUserFilterData_CallBack)(DWORD dwUserId, LPCTSTR lpBuf, DWORD dwLen, LPVOID lpUserValue);
        public delegate void OnRecvUserFilterDataCallBack(int userId, IntPtr buf, int len, int userValue);

        // �յ��û���������ͨ�����ݻص���������
        //typedef void (CALLBACK* BRAS_OnRecvUserTextMsg_CallBack)(DWORD dwRoomId, DWORD dwSrcUserId, DWORD dwTarUserId, BOOL bSecret, LPCTSTR lpTextMessage, DWORD dwLen, LPVOID lpUserValue);
        public delegate void OnRecvUserTextMsgCallBack(int roomId, int srcUserId, int tarUserId, bool bSecret, string textMessage, int len, int userValue);

		// ͸��ͨ�����ݻص���������
		//typedef void (CALLBACK * BRAS_OnTransBuffer_CallBack)(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, LPVOID lpUserValue);
		public delegate void OnTransBufferCallBack(int dwUserId, IntPtr lpBuf, int dwLen, int lpUserValue);
		
		// ͸��ͨ��������չ�ص���������
		//typedef void (CALLBACK * BRAS_OnTransBufferEx_CallBack)(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
		public delegate void OnTransBufferExCallBack(int dwUserId, IntPtr lpBuf, int dwLen, int wParam, int lParam, int dwTaskId, int lpUserValue);
		
		// �ļ�����ص���������
		//typedef void (CALLBACK * BRAS_OnTransFile_CallBack)(DWORD dwUserId, LPCTSTR lpFileName, LPCTSTR lpTempFilePath, DWORD dwFileLength, DWORD wParam, DWORD lParam, DWORD dwTaskId, LPVOID lpUserValue);
		public delegate void OnTransFileCallBack(int dwUserId, string lpFileName, string lpTempFilePath, int dwFileLength, int wParam, int lParam, int dwTaskId, int lpUserValue);

		// ������¼��ص���������
		//typedef void (CALLBACK * BRAS_OnServerRecord_CallBack)(DWORD dwUserId, DWORD dwParam, DWORD dwRecordServerId, DWORD dwElapse, LPCTSTR lpRecordFileName, LPVOID lpUserValue);
		public delegate void OnServerRecordCallBack(int dwUserId, int dwParam, int dwRecordServerId, int dwElapse, string lpRecordFileName, int lpUserValue);

        // ������¼��ص��������壨��չ��
        //typedef void (CALLBACK * BRAS_OnServerRecordEx_CallBack)(DWORD dwUserId, LPCTSTR lpFileName, DWORD dwElapse, DWORD dwFlags, DWORD dwParam, LPCTSTR lpUserStr, DWORD dwRecordServerId, LPVOID lpUserValue);
        public delegate void OnServerRecordExCallBack(int dwUserId, string lpFileName, int dwElapse, int dwFlags, int dwParam, string lpUserStr, int dwRecordServerId, IntPtr lpCallBackUserValue);

		// ��Ƶͨ����Ϣ֪ͨ�ص���������
		//typedef DWORD (CALLBACK * BRAS_OnVideoCallEvent_CallBack)(DWORD dwEventType, DWORD dwSrcUserId, DWORD dwTarUserId, DWORD dwErrorCode, DWORD dwFlags, DWORD dwParam, LPCTSTR lpUserStr, LPVOID lpUserValue);
		public delegate int OnVideoCallEventCallBack(int dwEventType, int dwSrcUserId, int dwTarUserId, int dwErrorCode, int dwFlags, int dwParam, string lpUserStr, int lpUserValue);
		
        // �û���Ϣ���ƻص���������
        //typedef DWORD (CALLBACK * BRAS_OnUserInfoControl_CallBack)(DWORD dwSendUserId, DWORD dwUserId, DWORD dwCtrlCode, DWORD wParam, DWORD lParam, LPCTSTR lpStrValue, LPVOID lpUserValue); 
        public delegate int OnUserInfoControlCallBack(int dwSendUserId, int dwUserId, int dwCtrlCode, int wParam, int lParam, string lpStrValue, int lpUserValue);

        // ҵ������¼��ص���������
        //typedef void (CALLBACK * BRAS_ObjectEventNotify_CallBack)(DWORD dwObjectType, DWORD dwObjectId, DWORD dwEventType, DWORD dwParam1, DWORD dwParam2, DWORD dwParam3, DWORD dwParam4, LPCTSTR lpStrParam, LPVOID lpUserValue);
        public delegate void ObjectEventNotifyCallBack(int dwObjectType, int dwObjectId, int dwEventType, int dwParam1, int dwParam2, int dwParam3, int dwParam4, string lpStrParam, int lpUserValue);


		/**
		 *    API ��������
		 */

        // ���÷�����Ӧ�ó�����Ϣ�ص�����
        //BRAS_API DWORD BRAS_SetOnServerAppMessageCallBack(BRAS_OnServerAppMessage_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnServerAppMessageCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnServerAppMessageCallBack(OnServerAppMessageCallBack function, int userValue);
		
		// ���÷�����Ӧ�ó�����Ϣ����չ���ص�����
        //BRAS_API DWORD BRAS_SetOnServerAppMessageExCallBack(BRAS_OnServerAppMessageEx_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnServerAppMessageExCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnServerAppMessageExCallBack(OnServerAppMessageExCallBack function, int userValue);

        // ����SDK��ʱ���ص�������dwElapse����ʱ���������λ��ms��
        // BRAS_API DWORD BRAS_SetTimerEventCallBack(DWORD dwElapse, BRAS_OnTimerEvent_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetTimerEventCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetTimerEventCallBack(int elapse, OnTimerEventCallBack function, int userValue);

        // �����û������֤�ص�����
        //BRAS_API DWORD BRAS_SetVerifyUserCallBack(BRAS_VerifyUser_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetVerifyUserCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetVerifyUserCallBack(VerifyUserCallBack function, int userValue);

        // �����û�������뷿��ص�����
        // BRAS_API DWORD BRAS_SetPrepareEnterRoomCallBack(BRAS_PrepareEnterRoom_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetPrepareEnterRoomCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetPrepareEnterRoomCallBack(PrepareEnterRoomCallBack function, int userValue);

        // �����û���¼�ɹ��ص�����
        //BRAS_API DWORD BRAS_SetOnUserLoginActionCallBack(BRAS_OnUserLoginAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnUserLoginActionCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnUserLoginActionCallBack(OnUserLoginActionCallBack function, int userValue);

        // �����û�ע���ص�����
        //BRAS_API DWORD BRAS_SetOnUserLogoutActionCallBack(BRAS_OnUserLogoutAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnUserLogoutActionCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnUserLogoutActionCallBack(OnUserLogoutActionCallBack function, int userValue);
		
		// �����û�ע���ص���������չ��
        //BRAS_API DWORD BRAS_SetOnUserLogoutActionExCallBack(BRAS_OnUserLogoutActionEx_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnUserLogoutActionExCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnUserLogoutActionExCallBack(OnUserLogoutActionExCallBack function, int userValue);

        // �����û����뷿��ص�����
        //BRAS_API DWORD BRAS_SetOnUserEnterRoomActionCallBack(BRAS_OnUserEnterRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnUserEnterRoomActionCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnUserEnterRoomActionCallBack(OnUserEnterRoomActionCallBack function, int userValue);

        // �����û��뿪����ص�����
        //BRAS_API DWORD BRAS_SetOnUserLeaveRoomActionCallBack(BRAS_OnUserLeaveRoomAction_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnUserLeaveRoomActionCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnUserLeaveRoomActionCallBack(OnUserLeaveRoomActionCallBack function, int userValue);

        // �����û��ϲ�ҵ���Զ������ݻص�����
        // BRAS_API DWORD BRAS_SetOnRecvUserFilterDataCallBack(BRAS_OnRecvUserFilterData_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnRecvUserFilterDataCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnRecvUserFilterDataCallBack(OnRecvUserFilterDataCallBack function, int userValue);

        // �����û���������ͨ�����ݻص�����
        //BRAS_API DWORD BRAS_SetOnRecvUserTextMsgCallBack(BRAS_OnRecvUserTextMsg_CallBack lpFunction, LPVOID lpUserValue=NULL);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnRecvUserTextMsgCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetOnRecvUserTextMsgCallBack(OnRecvUserTextMsgCallBack function, int userValue);
		
		
		// ����͸��ͨ�����ݻص�����
		//BRAS_API DWORD BRAS_SetOnTransBufferCallBack(BRAS_OnTransBuffer_CallBack lpFunction, LPVOID lpUserValue=NULL);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnTransBufferCallBack", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_SetOnTransBufferCallBack(OnTransBufferCallBack function, int lpUserValue);
		
		// ����͸��ͨ��������չ�ص�����
		//BRAS_API DWORD BRAS_SetOnTransBufferExCallBack(BRAS_OnTransBufferEx_CallBack lpFunction, LPVOID lpUserValue=NULL);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnTransBufferExCallBack", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_SetOnTransBufferExCallBack(OnTransBufferExCallBack function, int lpUserValue);
		
		// �����ļ�����ص�����
		//BRAS_API DWORD BRAS_SetOnTransFileCallBack(BRAS_OnTransFile_CallBack lpFunction, LPVOID lpUserValue=NULL);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnTransFileCallBack", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_SetOnTransFileCallBack(OnTransFileCallBack function, int lpUserValue);
		
		// ���÷�����¼��֪ͨ�ص�����
		//BRAS_API DWORD BRAS_SetOnServerRecordCallBack(BRAS_OnServerRecord_CallBack lpFunction, LPVOID lpUserValue=NULL);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnServerRecordCallBack", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_SetOnServerRecordCallBack(OnServerRecordCallBack function, int lpUserValue);

		// ������Ƶͨ����Ϣ֪ͨ�ص�����
		//BRAS_API DWORD BRAS_SetOnVideoCallEventCallBack(BRAS_OnVideoCallEvent_CallBack lpFunction, LPVOID lpUserValue=NULL);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetOnVideoCallEventCallBack", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_SetOnVideoCallEventCallBack(OnVideoCallEventCallBack function, int lpUserValue);

        // ���ûص�����
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetCallBack", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetCallBack(int dwCBType, IntPtr lpFuncPtr, IntPtr lpCallBackUserValue);
		
		
		// ��ȡSDK�汾��Ϣ
		//BRAS_API DWORD BRAS_GetSDKVersion(DWORD& dwMainVer, DWORD& dwSubVer, TCHAR* lpCompileTime, DWORD dwBufLen);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_GetSDKVersion", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_GetSDKVersion(ref int dwMainVer, ref int dwSubVer, StringBuilder lpCompileTime, int bufLen);
		
        // ��ʼ��SDK
        //BRAS_API DWORD BRAS_InitSDK(DWORD dwReserved);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_InitSDK", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_InitSDK(int reserved);

        // �ͷ���Դ
        //BRAS_API DWORD BRAS_Release(void);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_Release", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_Release();

        // ��ָ���û���������
        //BRAS_API DWORD BRAS_SendBufToUser(DWORD dwUserId, LPCTSTR lpBuf, DWORD dwLen);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SendBufToUser", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SendBufToUser(int userId, byte[] buf, int len);

        // ��ָ������������û���������
        //BRAS_API DWORD BRAS_SendBufToRoom(DWORD dwRoomId, LPCTSTR lpBuf, DWORD dwLen);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SendBufToRoom", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SendBufToRoom(int dwRoomId, byte[] lpBuf, int dwLen);
		
		// ��ָ���û�����͸��ͨ������
		//BRAS_API DWORD BRAS_TransBuffer(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_TransBuffer", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_TransBuffer(int dwUserId, byte[] lpBuf, int dwLen);
		
		// ��ָ���û�������չ����������
		//BRAS_API DWORD BRAS_TransBufferEx(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_TransBufferEx", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_TransBufferEx(int dwUserId, byte[] lpBuf, int dwLen, int wParam, int lParam, int dwFlags, ref int dwTaskId);
		
		// ��ָ���û������ļ�
		//BRAS_API DWORD BRAS_TransFile(DWORD dwUserId, LPCTSTR lpLocalPathName, DWORD wParam, DWORD lParam, DWORD dwFlags, DWORD& dwTaskId);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_TransFile", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_TransFile(int dwUserId, string lpLocalPathName, int wParam, int lParam, int dwFlags, ref int dwTaskId);
		
		// ���Ķ�¼�����
		//BRAS_API DWORD BRAS_StreamRecordCtrl(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam, DWORD dwRecordServerId);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_StreamRecordCtrl", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_StreamRecordCtrl(int dwUserId, int bStartRecord, int dwFlags, int dwParam, int dwRecordServerId);

        // ���Ķ�¼����ƣ���չ��
        //BRAS_API DWORD BRAS_StreamRecordCtrlEx(DWORD dwUserId, BOOL bStartRecord, DWORD dwFlags, DWORD dwParam, LPCTSTR lpUserStr=NULL, DWORD dwRecordServerId=-1);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_StreamRecordCtrlEx", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_StreamRecordCtrl(int dwUserId, int bStartRecord, int dwFlags, int dwParam, string lpUserStr, int dwRecordServerId);
		
		// ����͸��ͨ�����ݸ�¼�������
		//BRAS_API DWORD BRAS_TransBuffer2RecordServer(DWORD dwUserId, LPBYTE lpBuf, DWORD dwLen, DWORD dwParam, DWORD dwRecordServerId);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_TransBuffer2RecordServer", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_TransBuffer2RecordServer(int dwUserId, byte[] lpBuf, int dwLen, int dwParam, int dwRecordServerId);
		
		// ��Ƶ�����¼����ƣ����󡢻ظ����Ҷϵȣ�
		//BRAS_API DWORD BRAS_VideoCallControl(DWORD dwEventType, DWORD dwUserId, DWORD dwErrorCode, DWORD dwFlags=0, DWORD dwParam=0, LPCTSTR lpUserStr=NULL);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_VideoCallControl", CallingConvention = CallingConvention.Cdecl)]
		public static extern int BRAS_VideoCallControl(int dwEventType, int dwUserId, int dwErrorCode, int dwFlags, int dwParam, string lpUserStr);
		

        // �����û�����ϸ��Ϣ
		//BRAS_API DWORD BRAS_SetUserInfo(DWORD dwUserId, DWORD dwInfoId, LPCTSTR lpInfoValue, DWORD dwFlags=0);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetUserInfo", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetUserInfo(int dwUserId, int dwInfoId, string lpInfoValue, int dwFlags);

        // ��ȡ�û�����ϸ��Ϣ
		//BRAS_API DWORD BRAS_GetUserInfo(DWORD dwUserId, DWORD dwInfoId, TCHAR* lpInfoValue, DWORD dwSize);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_GetUserInfo", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_GetUserInfo(int dwUserId, int dwInfoId, StringBuilder lpInfoValue, int dwSize);

        // �û���Ϣ����
		//BRAS_API DWORD BRAS_UserInfoControl(DWORD dwUserId, DWORD dwCtrlCode, DWORD wParam=0, DWORD lParam=0, LPCTSTR lpStrValue=NULL);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_UserInfoControl", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_UserInfoControl(int dwUserId, int dwCtrlCode, int wParam, int lParam, string lpStrValue);

        // SDK�ں˲������ã��ַ������ͣ�
		//BRAS_API DWORD BRAS_SetSDKOption(DWORD optname, CHAR* optval, DWORD optlen);
		[DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetSDKOption", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetSDKOption(int optname, StringBuilder optval, int optlen);

        // SDK�ں˲������ã����ͣ�
        //BRAS_API DWORD BRAS_SetSDKOption(DWORD optname, CHAR* optval, DWORD optlen);
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_SetSDKOption", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_SetSDKOption(int optname, ref int optval, int optlen);

        /// <summary>
        /// ��ȡ���������û��б�
        /// <param name="dwRoomId">����ID��dwRoomId=-1ʱ����ʾ��ȡ�ѵ�¼ϵͳ���û�ID�б�</param>
        /// <param name="lpIdArray">�û�ID����</param>
        /// <param name="objNumber">�û�����</param>
        /// <returns></returns>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_GetOnlineUsers", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_GetOnlineUsers(int dwRoomId, int[] lpIdArray, ref int dwCount);

        /// <summary>
        /// ��ȡ����ID�б�ϵͳ���л�ķ��䣩
        /// <param name="lpIdArray">����ID����</param>
        /// <param name="objNumber">��������</param>
        /// <returns></returns>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_GetRoomIdList", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_GetRoomIdList(int[] lpIdArray, ref int dwCount);

        /// <summary>
        /// ����ķ�������̬��ѯ�����Ϣ
        /// </summary>
        /// <param name="dwInfoName"></param>
        /// <param name="lpInParam"></param>
        /// <param name="dwInSize"></param>
        /// <param name="lpResult"></param>
        /// <param name="dwOutSize"></param>
        /// <param name="dwFlags"></param>
        /// <returns></returns>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_QueryInfoFromServer", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_QueryInfoFromServer(int dwInfoName, StringBuilder lpInParam, int dwInSize, StringBuilder lpResult, ref int dwOutSize, int dwFlags);
        
        #region ҵ�����API��������

        /// <summary>
        /// ����ĳ�������͵Ķ���ID����
        /// <param name="dwObjectType">��������</param>
        /// <param name="objIDArray">����ID����</param>
        /// <param name="objNumber">���󳤶�</param>
        /// <returns></returns>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_ObjectGetIdList", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_ObjectGetIdList(int dwObjectType, int[] objIDArray, ref int objNumber);

        /// <summary>
        /// ��ȡ���������ֵ(����)
        /// <param name="dwObjectType">��������</param>
        /// <param name="dwObjectId">����ID</param>
        /// <param name="dwInfoName">������</param>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_ObjectGetValue", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_ObjectGetValue(int dwObjectType, int dwObjectId, int dwInfoName, ref int value);

        /// <summary>
        /// ��ȡ���������ֵ(�ַ���)
        /// <param name="dwObjectType">��������</param>
        /// <param name="dwObjectId">����ID</param>
        /// <param name="dwInfoName">������</param>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_ObjectGetValue", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_ObjectGetValue(int dwObjectType, int dwObjectId, int dwInfoName, StringBuilder value);

        /// <summary>
        /// ���ö��������ֵ(����)
        /// <param name="dwObjectType">��������</param>
        /// <param name="dwObjectId">����ID</param>
        /// <param name="dwInfoName">������</param>
        /// <param name="value">����ֵ</param>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_ObjectSetValue", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_ObjectSetValue(int dwObjectType, int dwObjectId, int dwInfoName, ref int value, int optlen);

        /// <summary>
        /// ���ö��������ֵ(�ַ���)
        /// <param name="dwObjectType">��������</param>
        /// <param name="dwObjectId">����ID</param>
        /// <param name="dwInfoName">������</param>
        /// <param name="value">����ֵ</param>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_ObjectSetValue", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_ObjectSetValue(int dwObjectType, int dwObjectId, int dwInfoName, string value, int optlen);

        /// <summary>
        /// ҵ��������ָ��
        /// <param name="dwObjectType">��������</param>
        /// <param name="dwObjectId">����ID</param>
        /// <param name="dwCtrlCode">���Ʒ���ָ��</param>
        /// <param name="dwParam1"></param>
        /// <param name="dwParam2"></param>
        /// <param name="dwParam3"></param>
        /// <param name="dwParam4"></param>
        /// <param name="strParam"></param>
        /// </summary>
        [DllImport(AnyChatServerSDKDll, EntryPoint = "BRAS_ObjectControl", CallingConvention = CallingConvention.Cdecl)]
        public static extern int BRAS_ObjectControl(int dwObjectType, int dwObjectId, int dwCtrlCode, int dwParam1, int dwParam2, int dwParam3, int dwParam4, string strParam);

        #endregion

    }
}