unit GVMessageDefine;
(****************************************************************************
          GVMessageDefine.h
****************************************************************************)

interface
uses
  SysUtils,Messages,Classes,Types,Graphics;

const
  WM_GV=WM_USER + 200;

  WM_GV_CONNECT				=WM_GV + 1;		///< 客户端连接服务器，wParam（BOOL）表示是否连接成功
  WM_GV_LOGINSYSTEM			=WM_GV + 2;		///< 客户端登录系统，wParam（INT）表示自己的用户ID号，lParam（INT）表示登录结果：0 成功，否则为出错代码，参考出错代码定义
  WM_GV_ENTERROOM			=WM_GV + 3;		///< 客户端进入房间，wParam（INT）表示所进入房间的ID号，lParam（INT）表示是否进入房间：0成功进入，否则为出错代码
  WM_GV_MICSTATECHANGE		=WM_GV + 4;		///< 用户的音频设备状态变化消息，wParam（INT）表示用户ID号，lParam（BOOL）表示该用户是否已打开音频采集设备
  WM_GV_USERATROOM			=WM_GV + 5;		///< 用户进入（离开）房间，wParam（INT）表示用户ID号，lParam（BOOL）表示该用户是进入（TRUE）或离开（FALSE）房间
  WM_GV_LINKCLOSE			=WM_GV + 6;		///< 网络连接已关闭，该消息只有在客户端连接服务器成功之后，网络异常中断之时触发
  WM_GV_ONLINEUSER			=WM_GV + 7;		///< 收到当前房间的在线用户信息，进入房间后触发一次，wParam（INT）表示在线用户数（包含自己），lParam（INT）表示房间ID
  WM_GV_FORTUNEMENU			=WM_GV + 8;		///< 用户选择了一项财富菜单项，wParam（INT）表示用户ID号，lParam（INT）表示财富菜单标记，指示是选择了哪一项菜单
  WM_GV_ROOMWAITQUEUE		=WM_GV + 9;		///< 用户收到当前房间等待队列消息，wParam（INT）表示用户前面的队列长度，lParam（INT）表示当前房间总的等待队列长度
  WM_GV_ENTERREQUEST		=WM_GV + 10;	///< 用户申请进入房间消息，wParam（INT）表示用户ID号，lParam（BOOL）表示该用户是申请进入（TRUE）房间或离开（FALSE）房间等待队列

  WM_GV_CAMERASTATE			=WM_GV + 11;	///< 用户摄像头状态发生变化，wParam（INT）表示用户ID号，lParam（INT）表示摄像头的当前状态，定义为：GV_CAMERA_STATE_XXXX
  WM_GV_CHATMODECHG			=WM_GV + 12;	///< 用户聊天模式发生变化，wParam（INT）表示用户ID号，lParam（INT）表示用户的当前聊天模式
  WM_GV_ACTIVESTATE			=WM_GV + 13;	///< 用户活动状态发生变化，wParam（INT）表示用户ID号，lParam（INT）表示用户的当前活动状态
  WM_GV_P2PCONNECTSTATE		=WM_GV + 14;	///< 本地用户与其它用户的P2P网络连接状态发生变化，wParam（INT）表示其它用户ID号，lParam（INT）表示本地用户与其它用户的当前P2P网络连接状态
  
  WM_GV_PRIVATEREQUEST		=WM_GV + 21;	///< 用户发起私聊请求，wParam（INT）表示发起者的用户ID号，lParam（INT）表示私聊请求编号，标识该请求
  WM_GV_PRIVATEECHO			=WM_GV + 22;	///< 用户回复私聊请求，wParam（INT）表示回复者的用户ID号，lParam（INT）为出错代码
  WM_GV_PRIVATEEXIT			=WM_GV + 23;	///< 用户退出私聊，wParam（INT）表示退出者的用户ID号，lParam（INT）为出错代码

  WM_GV_EXTENDBTNPRESS		=WM_GV + 31;	///< 用户按下扩展按钮，wParam（INT）表示按钮所对应的用户ID号，lParam（DWORD）指示按钮（左下角）所在屏幕位置(x,y)，用户可以利用该参数显示菜单等
  WM_GV_SDKWARNING			=WM_GV + 41;	///< SDK警告信息，当SDK在运行过程中自检发现异常状态时，将向上层发送该消息，wParam（INT）表示警告代码，定义为：GV_ERR_WARNING_XXXX

implementation

end.
