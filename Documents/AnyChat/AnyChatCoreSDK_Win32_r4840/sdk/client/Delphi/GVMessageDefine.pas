unit GVMessageDefine;
(****************************************************************************
          GVMessageDefine.h
****************************************************************************)

interface
uses
  SysUtils,Messages,Classes,Types,Graphics;

const
  WM_GV=WM_USER + 200;

  WM_GV_CONNECT				=WM_GV + 1;		///< �ͻ������ӷ�������wParam��BOOL����ʾ�Ƿ����ӳɹ�
  WM_GV_LOGINSYSTEM			=WM_GV + 2;		///< �ͻ��˵�¼ϵͳ��wParam��INT����ʾ�Լ����û�ID�ţ�lParam��INT����ʾ��¼�����0 �ɹ�������Ϊ������룬�ο�������붨��
  WM_GV_ENTERROOM			=WM_GV + 3;		///< �ͻ��˽��뷿�䣬wParam��INT����ʾ�����뷿���ID�ţ�lParam��INT����ʾ�Ƿ���뷿�䣺0�ɹ����룬����Ϊ�������
  WM_GV_MICSTATECHANGE		=WM_GV + 4;		///< �û�����Ƶ�豸״̬�仯��Ϣ��wParam��INT����ʾ�û�ID�ţ�lParam��BOOL����ʾ���û��Ƿ��Ѵ���Ƶ�ɼ��豸
  WM_GV_USERATROOM			=WM_GV + 5;		///< �û����루�뿪�����䣬wParam��INT����ʾ�û�ID�ţ�lParam��BOOL����ʾ���û��ǽ��루TRUE�����뿪��FALSE������
  WM_GV_LINKCLOSE			=WM_GV + 6;		///< ���������ѹرգ�����Ϣֻ���ڿͻ������ӷ������ɹ�֮�������쳣�ж�֮ʱ����
  WM_GV_ONLINEUSER			=WM_GV + 7;		///< �յ���ǰ����������û���Ϣ�����뷿��󴥷�һ�Σ�wParam��INT����ʾ�����û����������Լ�����lParam��INT����ʾ����ID
  WM_GV_FORTUNEMENU			=WM_GV + 8;		///< �û�ѡ����һ��Ƹ��˵��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ�Ƹ��˵���ǣ�ָʾ��ѡ������һ��˵�
  WM_GV_ROOMWAITQUEUE		=WM_GV + 9;		///< �û��յ���ǰ����ȴ�������Ϣ��wParam��INT����ʾ�û�ǰ��Ķ��г��ȣ�lParam��INT����ʾ��ǰ�����ܵĵȴ����г���
  WM_GV_ENTERREQUEST		=WM_GV + 10;	///< �û�������뷿����Ϣ��wParam��INT����ʾ�û�ID�ţ�lParam��BOOL����ʾ���û���������루TRUE��������뿪��FALSE������ȴ�����

  WM_GV_CAMERASTATE			=WM_GV + 11;	///< �û�����ͷ״̬�����仯��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ����ͷ�ĵ�ǰ״̬������Ϊ��GV_CAMERA_STATE_XXXX
  WM_GV_CHATMODECHG			=WM_GV + 12;	///< �û�����ģʽ�����仯��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ�û��ĵ�ǰ����ģʽ
  WM_GV_ACTIVESTATE			=WM_GV + 13;	///< �û��״̬�����仯��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ�û��ĵ�ǰ�״̬
  WM_GV_P2PCONNECTSTATE		=WM_GV + 14;	///< �����û��������û���P2P��������״̬�����仯��wParam��INT����ʾ�����û�ID�ţ�lParam��INT����ʾ�����û��������û��ĵ�ǰP2P��������״̬
  
  WM_GV_PRIVATEREQUEST		=WM_GV + 21;	///< �û�����˽������wParam��INT����ʾ�����ߵ��û�ID�ţ�lParam��INT����ʾ˽�������ţ���ʶ������
  WM_GV_PRIVATEECHO			=WM_GV + 22;	///< �û��ظ�˽������wParam��INT����ʾ�ظ��ߵ��û�ID�ţ�lParam��INT��Ϊ�������
  WM_GV_PRIVATEEXIT			=WM_GV + 23;	///< �û��˳�˽�ģ�wParam��INT����ʾ�˳��ߵ��û�ID�ţ�lParam��INT��Ϊ�������

  WM_GV_EXTENDBTNPRESS		=WM_GV + 31;	///< �û�������չ��ť��wParam��INT����ʾ��ť����Ӧ���û�ID�ţ�lParam��DWORD��ָʾ��ť�����½ǣ�������Ļλ��(x,y)���û��������øò�����ʾ�˵���
  WM_GV_SDKWARNING			=WM_GV + 41;	///< SDK������Ϣ����SDK�����й������Լ췢���쳣״̬ʱ�������ϲ㷢�͸���Ϣ��wParam��INT����ʾ������룬����Ϊ��GV_ERR_WARNING_XXXX

implementation

end.
