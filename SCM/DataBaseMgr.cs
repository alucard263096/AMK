using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SCM
{
    public class DataBaseMgr
    {
        string connectionStr = "";
        string ret="";
        public string Return
        {
            get
            {
                return ret;
            }
        }
        public DataBaseMgr()
        {
            connectionStr = ConfigurationManager.ConnectionStrings["Default"].ToString();
        }
        public DataTable executeDataTable(string sql)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionStr))
                {
                    conn.Open();
                    using (SqlCommand cmd = new SqlCommand(sql ,conn))
                    {
                        SqlDataAdapter adt = new SqlDataAdapter(cmd);
                        DataTable dt = new DataTable();
                        adt.Fill(dt);
                        return dt;
                    }
                }
            }
            catch(Exception ex)
            {
                ret = ex.Message;
                return null;
            }
        }
        public IDataReader executeReader(string sql)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionStr))
                {
                    conn.Open();
                    using (SqlCommand cmd = new SqlCommand(sql,conn))
                    {
                        return cmd.ExecuteReader();
                    }
                }
            }
            catch (Exception ex)
            {
                ret = ex.Message;
                return null;
            }
        }

        public bool executeNoneQuery(string sql)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionStr))
                {
                    conn.Open();
                    using (SqlCommand cmd = new SqlCommand(sql,conn))
                    {
                        cmd.ExecuteNonQuery();
                    }
                    return true;
                }
            }
            catch (Exception ex)
            {
                ret = ex.Message;
            }
            return false;
        }
    }
}
