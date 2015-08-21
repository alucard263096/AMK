unit GVSDK;
(****************************************************************************
               GVSDK.h
****************************************************************************)

interface
uses
  SysUtils,Messages,Classes,Types,Graphics,GVMessageDefine,GVErrorCodeDefine;


(**
 *	�ͻ���״̬�����
 *)
type GV_STATE_TYPE_DEFINE=
    (
    	GV_STATE_TYPE_ACTIVE = 0,			///< �ͻ��˻����״̬
    	GV_STATE_TYPE_CAMERA,				///< �ͻ��������״̬
    	GV_STATE_TYPE_AUDIO,				///< �ͻ�����Ƶ�豸״̬
    	GV_STATE_TYPE_CHATMODE				///< �ͻ�������ģʽ
    );
type GV_STATE_TYPE=GV_STATE_TYPE_DEFINE;

const
  //����ģʽ����
  GV_MEETING_MODE_FREE	=0;					///< ����ģʽ������û�����ƣ����ɷ��ԣ���Mic
  GV_MEETING_MODE_CHAIR	=1;					///< ����ģʽ�����������ƣ�ֻ�������˸�Mic���ܷ���
  GV_MEETING_MODE_ORDER	=2;					///< ��Ѳģʽ�����������ƣ�ֻ���ֵ��Լ����ܷ���


//�״̬����
  GV_ACTIVE_STATE_NORMAL	=0;				///< ����
  GV_ACTIVE_STATE_BUSY		=1;				///< ��æ
  GV_ACTIVE_STATE_AWAY		=2;				///< �뿪

//����ͷ״̬����
  GV_CAMERA_STATE_NULL		=0;				///< û������ͷ
  GV_CAMERA_STATE_NORMAL	=1;				///< ������ͷ��û�д�
  GV_CAMERA_STATE_OPEN		=2;				///< ����ͷ�Ѵ�

// Mic�״̬����
  GV_AUDIO_STATE_NULL		=0;				///< û�а�װ����
  GV_AUDIO_STATE_NORMAL		=1;				///< ����������û�д�Mic
  GV_AUDIO_STATE_OPEN		=2;				///< Mic�Ѵ�

// �ͻ�������ģʽ����
  GV_CHATMODE_PUBLIC		=0;				///< ����ģʽ
  GV_CHATMODE_PRIVATE		=1;				///< ˽��ģʽ�����ղ��������û�����������Ƶ���ݣ����ܽ��յ��Լ�˽�Ķ������������Ƶ���ݣ�������Ϣ����ģʽӰ��




implementation

end.
