#if !defined(ANYCHAT_ERROR_CODE_DEFINE_)
#define ANYCHAT_ERROR_CODE_DEFINE_

#pragma once

#define GV_ERR_SUCCESS			0		///< �ɹ�

#define GV_ERR_DB_ERROR			1		///< ���ݿ����
#define GV_ERR_NOTINIT			2		///< ϵͳû�г�ʼ��
#define	GV_ERR_NOTINROOM		3		///< ��δ���뷿��
#define GV_ERR_MEMORYFAIL		4		///< �ڴ治��
#define GV_ERR_EXCEPTION		5		///< �����쳣

#define GV_ERR_FUNCNOTALLOW		20		///< �������ܲ�����
#define GV_ERR_FUNCOPTERROR		21		///< ������������
#define GV_ERR_DEVICEOPENFAIL	22		///< device open failed or device no install
#define GV_ERR_NOENOUGHRESOURCE	23		///< û���㹻����Դ
#define GV_ERR_PIXFMTNOTSUPPORT	24		///< ָ���ĸ�ʽ���ܱ���ʾ�豸��֧��
#define GV_ERR_NOTMULTICASTADDR	25		///< ָ����IP��ַ������Ч���鲥��ַ
#define GV_ERR_MULTIRUNERROR	26		///< ��֧�ֶ�ʵ������

#define GV_ERR_MASTERISSLAVE	30		///< ��ָ���������������ȱ�����������֧���ٴ��ȱ�
#define GV_ERR_MASTERNOTCREDIT	31		///< ��������û�о�����Ȩ��֤����֧���ȱ�
#define GV_ERR_VERSIONNOTMATCH	32		///< �汾��ƥ��

//���Ӳ���
#define GV_ERR_CONNECT_TIMEOUT	100		///< ���ӷ�������ʱ
#define GV_ERR_CONNECT_ABORT	101		///< ��������������ж�
#define GV_ERR_CONNECT_AUTHFAIL	102		///< ���ӷ�������֤ʧ�ܣ���������������֤���룩
#define GV_ERR_CONNECT_DNSERROR	103		///< ��������ʧ��
#define GV_ERR_CONNECT_OVERFLOW	104		///< ������Ȩ�û���
#define	GV_ERR_CONNECT_FUNCLIMIT 	105	///< ���������������ƣ���ʾģʽ��
#define	GV_ERR_CONNECT_INTRANET		106	///< ֻ��������ʹ��
#define	GV_ERR_CONNECT_OLDVERSION 	107	///< �汾̫�ɣ�����������
#define	GV_ERR_CONNECT_DEVICELIMIT 	109	///< Ƕ��ʽ�豸�������ƣ�û����Ȩ��
#define GV_ERR_CONNECT_PAUSED		110	///< �����ѱ���ͣ
#define GV_ERR_CONNECT_HOTSERVER	111	///< �ȱ���������֧�����ӣ�������������״̬��
#define GV_ERR_CONNECT_ERRCERUSER	112	///< ��Ȩ�û���У����������ڴ汻�޸�
#define GV_ERR_CONNECT_IPFORBID	113		///< IP����ֹ����

//��¼����
#define GV_ERR_CERTIFY_FAIL		200		///< ��֤ʧ�ܣ��û�������������
#define GV_ERR_ALREADY_LOGIN	201		///< ���û��ѵ�¼
#define GV_ERR_ACCOUNT_LOCK		202		///< �ʻ��ѱ���ʱ����
#define GV_ERR_IPADDR_LOCK		203		///< IP��ַ�ѱ���ʱ����
#define GV_ERR_VISITOR_DENY		204		///< �ο͵�¼����ֹ����¼ʱû���������룩
#define GV_ERR_INVALID_USERID	205		///< ��Ч���û�ID���û������ڣ�
#define GV_ERR_SERVERSDK_FAIL	206		///< ��ҵ�����������ʧ�ܣ���֤����ʧЧ
#define GV_ERR_SERVERSDK_TIMEOUT 207	///< ҵ�������ִ������ʱ
#define GV_ERR_NOTLOGIN			208		///< û�е�¼
#define	GV_ERR_LOGIN_NEWLOGIN	209		///< ���û�������������ϵ�¼
#define GV_ERR_LOGIN_EMPTYNAME	210		///< �û���Ϊ��
#define GV_ERR_KICKOUT			211		///< ���������ߵ�
#define GV_ERR_SERVER_RESTART	212		///< ҵ�����������

//���뷿��
#define GV_ERR_ROOM_LOCK		300		///< �����ѱ���ס����ֹ����
#define GV_ERR_ROOM_PASSERR		301		///< ����������󣬽�ֹ����
#define GV_ERR_ROOM_FULLUSER	302		///< ��������Ա�����ܽ���
#define GV_ERR_ROOM_INVALID		303		///< ���䲻����
#define GV_ERR_ROOM_EXPIRE		304		///< �������ʱ���ѵ���
#define GV_ERR_ROOM_REJECT		305		///< �����ܾ�����
#define GV_ERR_ROOM_OWNERBEOUT	306		///< �������ڣ����ܽ��뷿��
#define GV_ERR_ROOM_ENTERFAIL	307		///< ���ܽ��뷿��
#define GV_ERR_ROOM_ALREADIN	308		///< �Ѿ��ڷ��������ˣ����ν��뷿���������


//˽��
#define GV_ERR_ROOM_PRINULL		401		///< �û��Ѿ��뿪����
#define GV_ERR_ROOM_REJECTPRI	402		///< �û��ܾ���˽������
#define GV_ERR_ROOM_PRIDENY		403		///< ����������û�˽�ģ������û���ֹ˽��

#define GV_ERR_ROOM_PRIREQIDERR	420		///< ˽������ID�Ŵ��󣬻����󲻴���
#define GV_ERR_ROOM_PRIALRCHAT	421		///< �Ѿ���˽���б���

#define GV_ERR_ROOM_PRITIMEOUT	431		///< ˽������ʱ
#define GV_ERR_ROOM_PRICHATBUSY	432		///< �Է�����˽���У���æ״̬
#define GV_ERR_ROOM_PRIUSERCLOSE 433	///< �Է��û��ر�˽��
#define GV_ERR_ROOM_PRISELFCLOSE 434	///< �û��Լ��ر�˽��
#define GV_ERR_ROOM_PRIREQCANCEL 435	///< ˽������ȡ��

// ��Ƶ����
#define GV_ERR_VIDEOCALL_INCHAT	440		///< ����ͨ����

//Mic����Ȩ
#define	GV_ERR_MICLOSE_TIMEOUT	500		///< ˵��ʱ��̫��������Ϣһ��
#define GV_ERR_MICLOSE_HIGHUSER	501		///< �и߼����û���Ҫ���ԣ�����Ϣһ��


//�޸��ǳ�
#define GV_ERR_NAMECHG_ONUSE	600		///< ���ǳ��ѱ�ʹ�ã��뻻���������ǳ�

// ���䲿��
#define GV_ERR_TRANSBUF_CREATEFAIL	700	///< ��������ʧ��
#define GV_ERR_TRANSBUF_NOTASK		701	///< û�и����񣬻������������

#define GV_ERR_TRANSFILE_OPENFAIL	710	///< ���ļ�����
#define GV_ERR_TRANSFILE_ZEROLEN	711	///< �ļ�����Ϊ0
#define GV_ERR_TRANSFILE_TOOLARGE	712	///< �ļ�����̫��
#define GV_ERR_TRANSFILE_READFAIL	713	///< ���ļ�����

// ¼�񲿷�
#define GV_ERR_RECORD_NOTASK		720	///< û��¼������
#define GV_ERR_RECORD_CREATEFAIL	721	///< ����¼������ʧ��
#define GV_ERR_RECORD_WAITINFO		722	///< �ȴ��û������Ϣ����ʱ����¼��

// SDK����
#define GV_ERR_WARNING_UDPFAIL		800	///< ���������UDPͨ���쳣����ý����񽫲�����������
#define GV_ERR_WARNING_MISCUTILFAIL	801	///< SDK����brMiscUtil.dll��̬��ʧ�ܣ����ֹ��ܽ�ʧЧ
#define GV_ERR_WARNING_MEDIAUTILFAIL	802	///< SDK����brMediaUtil.dll��̬��ʧ�ܣ����ֹ��ܽ�ʧЧ


// ��Ƶ����
#define GV_ERR_VIDEOCALL_CANCEL		100101	///< Դ�û����������Ự
#define GV_ERR_VIDEOCALL_OFFLINE	100102	///< Ŀ���û�������
#define	GV_ERR_VIDEOCALL_BUSY		100103	///< Ŀ���û�æ
#define GV_ERR_VIDEOCALL_REJECT		100104	///< Ŀ���û��ܾ��Ự
#define GV_ERR_VIDEOCALL_TIMEOUT	100105	///< �Ự����ʱ
#define GV_ERR_VIDEOCALL_DISCONNECT	100106	///< �������


#endif