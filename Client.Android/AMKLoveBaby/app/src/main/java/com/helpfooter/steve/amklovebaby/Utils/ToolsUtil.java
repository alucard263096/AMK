package com.helpfooter.steve.amklovebaby.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;

import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLayoutHelper;
import com.helpfooter.steve.amklovebaby.Extents.PercentLayout.PercentLinearLayout;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ToolsUtil {

    public static String sha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes());

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] httpPost(String url, String entity) {
        if (url == null || url.length() == 0) {
            Log.e("", "httpPost, url is null");
            return null;
        }

        HttpClient httpClient = getNewHttpClient();

        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new StringEntity(entity));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.e("", "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
                return null;
            }

            return EntityUtils.toByteArray(resp.getEntity());
        } catch (Exception e) {
            Log.e("", "httpPost exception, e = " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    private static class SSLSocketFactoryEx extends SSLSocketFactory {

        SSLContext sslContext = SSLContext.getInstance("TLS");

        public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,	String authType) throws java.security.cert.CertificateException {
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host,	port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }
    private static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    public static byte[] httpGet(final String url) {
        if (url == null || url.length() == 0) {
            Log.e("", "httpGet, url is null");
            return null;
        }

        HttpClient httpClient = getNewHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse resp = httpClient.execute(httpGet);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.e("", "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
                return null;
            }

            return EntityUtils.toByteArray(resp.getEntity());

        } catch (Exception e) {
            Log.e("", "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String Encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    public static PercentLinearLayout.LayoutParams getLayoutParam() {
        PercentLinearLayout.LayoutParams param = new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.MATCH_PARENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo = new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.fillLayoutParams(param, PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.MATCH_PARENT);
        param.mPercentLayoutInfo = layoutinfo;
        return param;
    }
    public static PercentLinearLayout.LayoutParams getLayoutParamHeightWrap() {
        PercentLinearLayout.LayoutParams param = new PercentLinearLayout.LayoutParams(PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.WRAP_CONTENT);
        PercentLayoutHelper.PercentLayoutInfo layoutinfo = new PercentLayoutHelper.PercentLayoutInfo();
        layoutinfo.fillLayoutParams(param, PercentLinearLayout.LayoutParams.MATCH_PARENT, PercentLinearLayout.LayoutParams.WRAP_CONTENT);
        param.mPercentLayoutInfo = layoutinfo;
        return param;
    }

    public static LinearLayout GenPLine(Context ctx) {

        LinearLayout pLine = new LinearLayout(ctx);
        LinearLayout.LayoutParams pLineLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        pLine.setLayoutParams(pLineLayout);
        pLine.setBackgroundColor(Color.parseColor("#cccccc"));

        return pLine;
    }

    public static String FormatString(String str) {
        String notice = "";
        try {
            notice = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return str;
    }

    public static boolean IsAfternoon() {
        Date curDate = new Date(System.currentTimeMillis());
        Date in12AM = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, 12, 0, 0);
        return (curDate.getTime() > in12AM.getTime());
    }

    public static boolean isMobileNO(String mobiles) {


        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    public static String earseMobileNo(String name){
        if(isMobileNO(name)){
            return name.substring(0,4)+"****"+name.substring(8);
        }
        return name;
    }


    static  int IO_BUFFER_SIZE=1024;
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public static int  StrParseToInt(String str){
        if(str.trim().length()==0){
            return 0;
        }else {
            try{
               return Integer.parseInt(str);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return 0;
    }

}