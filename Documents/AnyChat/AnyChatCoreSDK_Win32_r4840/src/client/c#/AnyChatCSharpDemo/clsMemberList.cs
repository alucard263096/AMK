using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace AnyChatCSharpDemo
{
    public class clsMemberList
    {
        /// <summary>
        /// �����û����Խṹ
        /// </summary>
        public struct MemberItem
        {
            /// <summary>
            /// �û��˺�
            /// </summary>
            public string m_UserName;
            /// <summary>
            /// �û��ɣģ��������Զ�����
            /// </summary>
            public int m_UserID;
            /// <summary>
            /// ��ʵ����
            /// </summary>
            public string m_RealName;
            /// <summary>
            /// �û��б��е�˳��
            /// </summary>
            public int m_Index;
            /// <summary>
            /// �û�Ȩ��,0������Ȩ�ޣ�1��Ƶ��2��Ƶ,3����
            /// </summary>
            public int[] m_Permission;
        }
    }
}
