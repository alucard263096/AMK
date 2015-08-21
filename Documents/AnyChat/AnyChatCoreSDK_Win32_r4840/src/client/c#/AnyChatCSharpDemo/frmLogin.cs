using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Net;

namespace AnyChatCSharpDemo
{
    public partial class frmLogin : Form
    {

        #region �������

        
        
        /// <summary>
        /// �û���¼�˺�
        /// </summary>
        public static string m_UserName = "";
        /// <summary>
        /// ��Ƶ������IP
        /// </summary>
        public static string m_VideoServerIP = "211.155.25.89";
       
        /// <summary>
        /// ������Ƶ�ɼ����
        /// </summary>
        public static int m_LocalVideoWidth = 320;
        /// <summary>
        /// ������Ƶ�ɼ��߶�
        /// </summary>
        public static int m_LocalVideoHeight = 240;
        /// <summary>
        /// ������TCP��IP
        /// </summary>
        public static int m_VideoTcpPort = 8906;//����8906��


        #endregion

        #region ���캯��

        public frmLogin()
        {
            InitializeComponent();
        }

        #endregion

        #region ��¼

        private void btn_login_Click(object sender, EventArgs e)
        {
            string m_User = txt_username.Text.Trim();
            string m_Pass = txt_password.Text.Trim();
            if (m_User.Length == 0)
            {
                MessageBox.Show("�˺Ų���Ϊ��", "��ʾ");
                return;
            }


            if (m_UserName == "")
            {
                m_UserName = m_User;
            }
            m_VideoServerIP = txt_hospitalip.Text.Trim();


            try
            {
                m_VideoTcpPort = Convert.ToInt32(tb_port.Text);
            }
            catch (Exception)
            {
                MessageBox.Show("�˿ں�������");
            }

            this.Hide();
            frmRoom m_FR = new frmRoom();
            m_FR.Show();
        }

        #endregion

        #region �˳�

        private void btn_canel_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(0);
        }

        #endregion

       
        #region �������

        private void frmLogin_Load(object sender, EventArgs e)
        {


        }

        #endregion

        

    }
}