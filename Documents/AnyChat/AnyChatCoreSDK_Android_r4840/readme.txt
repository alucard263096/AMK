
   		    AnyChat for Android SDK （V2.3）


    欢迎您选择佰锐科技的产品，我们将以“锐意创新 引领未来”的佰锐精神为您提供
优质的产品和服务。

一、SDK包概述

    AnyChat是一套跨平台的音、视频即时通讯解决方案，支持Windows、Linux、Web浏
览器、Android、iOS等操作系统平台，提供客户端SDK、服务器端SDK，可与第三方平台
紧密集成。

    AnyChat for Android采用JNI接口技术，提供纯JAVA接口，支持Android 2.x、
Android 4.x以及Android 5.x等平台，支持ARM、x86两种CPU架构。

    AnyChat for Android可与其它平台的AnyChat互联互通，连接同一个服务器程序，有关
服务器的SDK可参考AnyChat Platform Core SDK开发包。

    AnyChat for Android SDK包里面提供了Demo程序源代码（基于Eclipse工程）供参考。


目录清单

|----bin				AnyChat SDK演示程序（安装包）
|
|----doc				客户端开发指南
|
|----src				Demo程序源代码（Eclipse工程）
|
|----sdk				客户端SDK引用文件、库文件



技术支持

                                http://bbs.anychat.cn
                                http://www.anychat.cn
                                TEL：+86-020-85276986 38103410
                                Email：service@bairuitech.com
                                广州佰锐网络科技有限公司
                        广州市天河区天河软件园建工路9号309、310室

                                       2015年07月

附：版本变更记录

2015-07-15	V2.2
优化移动设备上服务器合成流录制，功能更稳定；
增加业务对象API，支持智能排队等新功能
支持动态调节视频参数，包括码率、视频质量、预设参数等；
增加中心服务器拍照功能；
优化网络数据传输，提高移动网络抖动时的音视频质量；
修正在android 5.0系统上应用闪退的问题；

2015-01-15	V2.2
优化音视频内核，编解码效率更高；
增加服务器合成录制、合成流录制功能；
视频录制过程中支持动态改变视频分辩率；
优化网络抖动，减少网络连接断开，提高连接速度；
支持通过API接口开启AnyChat内核调试模式；
增加对Android x86平台的支持；
针对部分海思芯片手机视频初始化失败的问题进行适配；
Android增加本地视频录制，拍照功能；

2014-09-01	V2.1
优化并更新内核Codec库，整体性能有较大幅度提升
针对网络状况较差时网络连接的稳定性进行优化
AnyChat支持录像文件格式设置（MP4、WMV、FLV、MP3）
中心录像服务器返回录像文件路径可配置
中心服务器录像支持触发客户端回调事件
实现服务器集中收集客户端日志信息功能
Android平台播放语音时支持听筒、喇叭之间切换
修正Android平台特定场景下会导致应用闪退的问题
修正低性能设备上视频通话时延迟累计增大的问题
修正合成录像时本地视频有时会卡顿的问题

2014-06-01	V2.0
增加数据加密、解密API接口，可实现上层应用对语音、视频等数据的加解密；
AnyChat Server SDK支持64bit Java环境
增加视频方向手工修正API接口，包括本地视频采集方向和远程视频显示方向
修正文件传输时在某些网络环境下文件内容接收不完整的问题；
优化文件传输模块，提高文件传输效率，特别针对卫星网络环境下的文件传输优化
修正传输大容量文件时内存占用过高的问题，新增文件传输的断点续传功能；
修正Android平台Java驱动模式下，摄像头状态不准的Bug

2014-01-01	V1.9
调整Android平台缓冲区（文件）传输API接口参数，支持taskid输出；
提供完整的视频呼叫解决方案；
提供完整的大厅好友解决方案；
修正Android平台收发大量文字消息时会导致错误的Bug；
修正Android平台不能接收中文文件名的Bug
优化回声消除算法，提高通话过程中的用户体验；
支持最新的Android 4.4平台；
修正Android平台频繁分配内存导致java内存回收机制占用资源过多的Bug；
修正Android平台竖屏模式（Portrait）本地预览图像旋转90度的Bug；
将Java层的AnyChatTransTaskOutParam类由通用的AnyChatOutParam类代替；
修正Android平台视频显示有时只部分刷新的问题；

2013-07-08	V1.8
基于AnyChat Platform Core SDK V4.8版本编译；

2013-03-20	V1.7
基于AnyChat Platform Core SDK V4.7版本编译；

2012-11-22	V1.6
基于AnyChat Platform Core SDK V4.6版本编译；

2012-09-10	V1.5
基于AnyChat Platform Core SDK V4.5版本编译；

2012-05-11	V1.4
基于AnyChat Platform Core SDK V4.4版本编译；

2012-02-20	V1.3
基于AnyChat Platform Core SDK V4.3版本编译；
优化语音通信效果
支持Android 4.x平台

2011-11-15	V1.2
基于AnyChat Platform Core SDK V4.2版本编译；
修正内核多线程操作界面元素导致程序异常的Bug；
增加回音抑制模块，提高语音通信体验；

2011-10-08	V1.0
初始版本，基于AnyChat Platform Core SDK V4.1版本编译