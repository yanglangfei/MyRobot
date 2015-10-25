package com.ylf.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *  ���ʷ�����
 * 
 * @author YLF
 * 
 */
public class HttpUtil {

	/**
	 * ����java���GET��ʽ�����ύ
	 * 
	 * @param path
	 *            ���ݵķ���·��
	 * @param params
	 *            ·��������Ĳ���
	 * @return ������Ӧ��Ϣ����
	 * @throws Exception
	 */
	public static InputStream sendHttpGet(String path,
			Map<String, String> params) throws Exception {
		// ���׽����ַ���ƴ��
		StringBuffer sb = new StringBuffer(path);
		if (params != null && !params.isEmpty()) {
			sb.append("?");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				sb.append("&");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == HttpStatus.SC_OK) {
			InputStream is = conn.getInputStream();
			return is;
		}

		return null;
	}

	/**
	 * ����java���POST��ʽ�����ύ
	 * 
	 * @param path
	 *            ���ݵķ���·��
	 * @param params
	 *            ·��������Ĳ���
	 * @return ������Ӧ��Ϣ����
	 * @throws Exception
	 */
	public static InputStream sendHttpPost(String path,
			Map<String, String> params) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (params != null && !params.isEmpty()) {
			
			for (Map.Entry<String, String> data : params.entrySet()) {
				sb.append(data.getKey());
				sb.append("=");
				sb.append(data.getValue());
				sb.append("&");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		byte[] entry = sb.toString().getBytes("UTF-8");
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", "" + entry.length);
		conn.setDoOutput(true);// �����������������
		OutputStream os = conn.getOutputStream();
		os.write(entry);
		if (conn.getResponseCode() == 200) {
			InputStream is = conn.getInputStream();
			return is;
		}

		return null;
	}

	/**
	 * ����apache�ĵ��������Get��ʽ�����ύ
	 * 
	 * @param path
	 *            ���ݵķ���·��
	 * @param params
	 *            ·��������Ĳ���
	 * @return ������Ӧ��Ϣ����
	 * @throws Exception
	 */
	public static String apacheGet(String path, Map<String, String> params)
			throws Exception {
		
		StringBuffer sb = new StringBuffer(path);
		if (params != null && !params.isEmpty()) {
			sb.append("?");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				sb.append("&");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		HttpClient client = new DefaultHttpClient();// �൱��һ�������
		HttpGet get = new HttpGet(sb.toString()); // �ύ�ķ�ʽ
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			//InputStream is = response.getEntity().getContent();// ������һ��ʵ�壬��ʵ���еõ���
			byte bs[]=EntityUtils.toByteArray(response.getEntity());
			return new String(bs, "UTF-8");
		}

		return null;
	}

	/**
	 * ����apache�ĵ��������post��ʽ�����ύ
	 * 
	 * @param path
	 *            ���ݵķ���·��
	 * @param params
	 *            ·��������Ĳ���
	 * @return ������Ӧ��Ϣ����
	 * @throws Exception
	 */
	public static InputStream apachePost(String path, Map<String, String> params)
			throws Exception {

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(path);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == 200) {
			// ����ֱ�ӽ����ص�ʵ��ת���ֽ�������ַ���
			// byte[] data = EntityUtils.toByteArray(response.getEntity());//EntityUtils
			InputStream is = response.getEntity().getContent();
			return is;
		}
		return null;
	}

	/**
	 * ��������ת����һ���ֽ�����
	 * 
	 * @param is
	 *            ������
	 * @return ����һ���ֽ�����
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = is.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}

		return bos.toByteArray();
	}
}
