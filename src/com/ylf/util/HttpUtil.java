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
 *  访问服务器
 * 
 * @author YLF
 * 
 */
public class HttpUtil {

	/**
	 * 采用java类的GET方式进行提交
	 * 
	 * @param path
	 *            传递的访问路径
	 * @param params
	 *            路径后面跟的参数
	 * @return 返回响应信息的流
	 * @throws Exception
	 */
	public static InputStream sendHttpGet(String path,
			Map<String, String> params) throws Exception {
		// 容易进行字符串拼接
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
	 * 采用java类的POST方式进行提交
	 * 
	 * @param path
	 *            传递的访问路径
	 * @param params
	 *            路径后面跟的参数
	 * @return 返回响应信息的流
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
		conn.setDoOutput(true);// 先允许向外输出数据
		OutputStream os = conn.getOutputStream();
		os.write(entry);
		if (conn.getResponseCode() == 200) {
			InputStream is = conn.getInputStream();
			return is;
		}

		return null;
	}

	/**
	 * 采用apache的第三方类的Get方式进行提交
	 * 
	 * @param path
	 *            传递的访问路径
	 * @param params
	 *            路径后面跟的参数
	 * @return 返回响应信息的流
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
		HttpClient client = new DefaultHttpClient();// 相当于一个浏览器
		HttpGet get = new HttpGet(sb.toString()); // 提交的方式
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			//InputStream is = response.getEntity().getContent();// 返回了一个实体，从实体中得到流
			byte bs[]=EntityUtils.toByteArray(response.getEntity());
			return new String(bs, "UTF-8");
		}

		return null;
	}

	/**
	 * 采用apache的第三方类的post方式进行提交
	 * 
	 * @param path
	 *            传递的访问路径
	 * @param params
	 *            路径后面跟的参数
	 * @return 返回响应信息的流
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
			// 可以直接将返回的实体转成字节数组或字符串
			// byte[] data = EntityUtils.toByteArray(response.getEntity());//EntityUtils
			InputStream is = response.getEntity().getContent();
			return is;
		}
		return null;
	}

	/**
	 * 将输入流转化成一个字节数组
	 * 
	 * @param is
	 *            输入流
	 * @return 返回一个字节数组
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
