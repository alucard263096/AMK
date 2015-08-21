Option Strict On
Option Explicit On
Module Module3
	'#define WM_USER                         0x0400
	Public Const WM_USER As Integer = &H400
	
	'#define WM_GV   WM_USER + 200
	Public Const WM_GV As Integer = WM_USER + 200
	
	'#define WM_GV_CONNECT               	WM_GV + 1       ///< �ͻ������ӷ�������wParam��BOOL����ʾ�Ƿ����ӳɹ�
	Public Const WM_GV_CONNECT As Integer = WM_GV + 1
	'#define WM_GV_LOGINSYSTEM           	WM_GV + 2       ///< �ͻ��˵�¼ϵͳ��wParam��INT����ʾ�Լ����û�ID�ţ�lParam��INT����ʾ��¼�����0 �ɹ�������Ϊ������룬�ο�������붨��
	Public Const WM_GV_LOGINSYSTEM As Integer = WM_GV + 2
	'#define WM_GV_ENTERROOM            	WM_GV + 3       ///< �ͻ��˽��뷿�䣬wParam��INT����ʾ�����뷿���ID�ţ�lParam��INT����ʾ�Ƿ���뷿�䣺0�ɹ����룬����Ϊ�������
	Public Const WM_GV_ENTERROOM As Integer = WM_GV + 3
	'#define WM_GV_MICSTATECHANGE	WM_GV + 4       ///< �û�����Ƶ�豸״̬�仯��Ϣ��wParam��INT����ʾ�û�ID�ţ�lParam��BOOL����ʾ���û��Ƿ��Ѵ���Ƶ�ɼ��豸
	Public Const WM_GV_MICSTATECHANGE As Integer = WM_GV + 4
	'#define WM_GV_USERATROOM		WM_GV + 5       ///< �û����루�뿪�����䣬wParam��INT����ʾ�û�ID�ţ�lParam��BOOL����ʾ���û��ǽ��루TRUE�����뿪��FALSE������
	Public Const WM_GV_USERATROOM As Integer = WM_GV + 5
	'#define WM_GV_LINKCLOSE		WM_GV + 6       ///< ���������ѹرգ�����Ϣֻ���ڿͻ������ӷ������ɹ�֮�������쳣�ж�֮ʱ������wParam��INT����ʾ���ӶϿ���ԭ��
	Public Const WM_GV_LINKCLOSE As Integer = WM_GV + 6
	'#define WM_GV_ONLINEUSER		WM_GV + 7       ///< �յ���ǰ����������û���Ϣ�����뷿��󴥷�һ�Σ�wParam��INT����ʾ�����û����������Լ�����lParam��INT����ʾ����ID
	Public Const WM_GV_ONLINEUSER As Integer = WM_GV + 7
	'#define WM_GV_FORTUNEMENU		WM_GV + 8       ///< �û�ѡ����һ��Ƹ��˵��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ�Ƹ��˵���ǣ�ָʾ��ѡ������һ��˵�
	Public Const WM_GV_FORTUNEMENU As Integer = WM_GV + 8
	'#define WM_GV_ROOMWAITQUEUE	WM_GV + 9       ///< �û��յ���ǰ����ȴ�������Ϣ��wParam��INT����ʾ�û�ǰ��Ķ��г��ȣ�lParam��INT����ʾ��ǰ�����ܵĵȴ����г���
	Public Const WM_GV_ROOMATQUEUE As Integer = WM_GV + 9
	'#define WM_GV_ENTERREQUEST	WM_GV + 10      ///< �û�������뷿����Ϣ��wParam��INT����ʾ�û�ID�ţ�lParam��BOOL����ʾ���û���������루TRUE��������뿪��FALSE������ȴ�����
	Public Const WM_GV_ENTERREQUEST As Integer = WM_GV + 10
	'#define WM_GV_CAMERASTATE 		WM_GV + 11      ///< �û�����ͷ״̬�����仯��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ����ͷ�ĵ�ǰ״̬������Ϊ��GV_CAMERA_STATE_XXXX
	Public Const WM_GV_CAMERASTATE As Integer = WM_GV + 11
	'#define WM_GV_CHATMODECHG		WM_GV + 12      ///< �û�����ģʽ�����仯��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ�û��ĵ�ǰ����ģʽ
	Public Const WM_GV_CHATMODECHG As Integer = WM_GV + 12
	'#define WM_GV_ACTIVESTATE		WM_GV + 13      ///< �û��״̬�����仯��wParam��INT����ʾ�û�ID�ţ�lParam��INT����ʾ�û��ĵ�ǰ�״̬
	Public Const WM_GV_ACTIVESTATE As Integer = WM_GV + 13
	
	'#define WM_GV_PRIVATEREQUEST	WM_GV + 21      ///< �û�����˽������wParam��INT����ʾ�����ߵ��û�ID�ţ�lParam��INT����ʾ˽�������ţ���ʶ������
	Public Const WM_GV_PRIVATEREQUEST As Integer = WM_GV + 21
	'#define WM_GV_PRIVATEECHO		WM_GV + 22      ///< �û��ظ�˽������wParam��INT����ʾ�ظ��ߵ��û�ID�ţ�lParam��INT��Ϊ�������
	Public Const WM_GV_PRIVATEECHO As Integer = WM_GV + 22
	'#define WM_GV_PRIVATEEXIT		WM_GV + 23      ///< �û��˳�˽�ģ�wParam��INT����ʾ�˳��ߵ��û�ID�ţ�lParam��INT��Ϊ�������
	Public Const WM_GV_PRIVATEEXIT As Integer = WM_GV + 23
	
	'#define WM_GV_EXTENDBTNPRESS 	WM_GV + 31      ///< �û�������չ��ť��wParam��INT����ʾ��ť����Ӧ���û�ID�ţ�lParam��DWORD��ָʾ��ť�����½ǣ�������Ļλ��(x,y)���û��������øò�����ʾ�˵���
	Public Const WM_GV_EXITENDBTNPRESS As Integer = WM_GV + 31
End Module