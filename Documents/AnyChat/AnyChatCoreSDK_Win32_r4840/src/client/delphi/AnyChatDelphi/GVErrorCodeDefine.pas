unit GVErrorCodeDefine;
(****************************************************************************
     GVErrorCodeDefine.h
****************************************************************************)

interface

uses
  SysUtils,Classes,Types,Graphics;

const
  GV_ERR_SUCCESS				=0;			///< �ɹ�

  GV_ERR_DB_ERROR				=1;			///< ���ݿ����
  GV_ERR_NOTINIT				=2;			///< ϵͳû�г�ʼ��
  GV_ERR_NOTINROOM				=3;			///< ��δ���뷿��
  GV_ERR_MEMORYFAIL				=4;			///< �ڴ治��

  GV_ERR_FUNCNOTALLOW			=20;  		///< �������ܲ�����
  GV_ERR_FUNCOPTERROR			=21;		///< ������������ 

  //���Ӳ���
  GV_ERR_CONNECT_TIMEOUT		=100;		///< ���ӷ�������ʱ
  GV_ERR_CONNECT_ABORT			=101;		///< ��������������ж�
  GV_ERR_CONNECT_AUTHFAIL		=102;		///< δ��ͨ������������֤�����ڷǷ�����
  GV_ERR_CONNECT_DNSERROR		=103;		///< ��������ʧ��  ///

  //��¼����
  GV_ERR_CERTIFY_FAIL			=200;		///< ��֤ʧ�ܣ��û�������������
  GV_ERR_ALREADY_LOGIN			=201;		///< ���û��ѵ�¼
  GV_ERR_ACCOUNT_LOCK			=202;		///< �ʻ��ѱ���ʱ����
  GV_ERR_IPADDR_LOCK			=203;		///< IP��ַ�ѱ���ʱ����
  GV_ERR_VISITOR_DENY			=204;		///< �ο͵�¼����ֹ����¼ʱû���������룩
  GV_ERR_INVALID_USERID			=205;		///< ��Ч���û�ID���û������ڣ�

  //���뷿��
  GV_ERR_ROOM_LOCK		 		=300;		///< �����ѱ���ס����ֹ����
  GV_ERR_ROOM_PASSERR			=301;		///< ����������󣬽�ֹ����
  GV_ERR_ROOM_FULLUSER			=302;		///< ��������Ա�����ܽ���
  GV_ERR_ROOM_INVALID			=303;		///< ���䲻����
  GV_ERR_ROOM_EXPIRE			=304;		///< �������ʱ���ѵ���
  GV_ERR_ROOM_REJECT			=305;		///< �����ܾ�����
  GV_ERR_ROOM_OWNERBEOUT		=306;		///< �������ڣ����ܽ��뷿��
  GV_ERR_ROOM_ENTERFAIL			=307;		///< ���ܽ��뷿��
  GV_ERR_ROOM_ALREADIN			=308;		///< �Ѿ��ڷ��������ˣ����ν��뷿���������


  //˽��
  GV_ERR_ROOM_PRINULL			=401;		///< �û��Ѿ��뿪����
  GV_ERR_ROOM_REJECTPRI			=402;		///< �û��ܾ���˽������
  GV_ERR_ROOM_PRIDENY			=403;		///< ����������û�˽�ģ������û���ֹ˽��

  GV_ERR_ROOM_PRIREQIDERR		=420;		///< ˽������ID�Ŵ��󣬻����󲻴���
  GV_ERR_ROOM_PRIALRCHAT		=421;		///< �Ѿ���˽���б���

  GV_ERR_ROOM_PRITIMEOUT		=431;		///< ˽������ʱ
  GV_ERR_ROOM_PRICHATBUSY		=432;		///< �Է�����˽���У���æ״̬
  GV_ERR_ROOM_PRIUSERCLOSE		=433;		///< �Է��û��ر�˽��
  GV_ERR_ROOM_PRISELFCLOSE		=434;		///< �û��Լ��ر�˽��

  //Mic����Ȩ
  GV_ERR_MICLOSE_TIMEOUT		=500;		///< ˵��ʱ��̫��������Ϣһ��
  GV_ERR_MICLOSE_HIGHUSER		=501;		///< �и߼����û���Ҫ���ԣ�����Ϣһ��


  //�޸��ǳ�
  GV_ERR_NAMECHG_ONUSE			=600;		///< ���ǳ��ѱ�ʹ�ã��뻻���������ǳ�

  // ���䲿��
  GV_ERR_TRANSBUF_CREATEFAIL	=700;		///< ��������ʧ��
  GV_ERR_TRANSBUF_NOTASK		=701;		///< û�и����񣬻������������

  GV_ERR_TRANSFILE_OPENFAIL		=710;		///< ���ļ�����
  GV_ERR_TRANSFILE_ZEROLEN		=711;		///< �ļ�����Ϊ0
  GV_ERR_TRANSFILE_TOOLARGE		=712;		///< �ļ�����̫��
  GV_ERR_TRANSFILE_READFAIL		=713;		///< ���ļ�����

  // ¼�񲿷�
  GV_ERR_RECORD_NOTASK			=720;		///< û��¼������
  GV_ERR_RECORD_CREATEFAIL		=721;		///< ����¼������ʧ��

  // SDK����
  GV_ERR_WARNING_UDPFAIL		=800;		///< ���������UDPͨ���쳣����ý����񽫲�����������
  GV_ERR_WARNING_MISCUTILFAIL	=801;		///< SDK����brMiscUtil.dll��̬��ʧ�ܣ����ֹ��ܽ�ʧЧ
  GV_ERR_WARNING_MEDIAUTILFAIL	=802;		///< SDK����brMediaUtil.dll��̬��ʧ�ܣ����ֹ��ܽ�ʧЧ
  
  
implementation

end.
