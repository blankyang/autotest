package com.test.framework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class HttpUtil {

	public static String byGet(String path, Map<String, String> headers) {
		try {
			URL url = new URL(path.trim());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (headers != null) {
				// 设置请求头
				for (Map.Entry<String, String> en : headers.entrySet()) {
					conn.setRequestProperty(en.getKey(), en.getValue());
				}
			}
			conn.connect();
			if (200 == conn.getResponseCode()) {
				InputStream is = conn.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = is.read(buffer))) {
					baos.write(buffer, 0, len);
					baos.flush();
				}
				baos.close();
				is.close();
				conn.disconnect();
				return baos.toString("utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "请求炸了";
	}

	public static String byPost(String path, Map<String, String> headers,
			String params) {
		try {
			URL url = new URL(path.trim());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			if (headers != null) {
				// 设置请求头
				for (Map.Entry<String, String> en : headers.entrySet()) {
					conn.setRequestProperty(en.getKey(), en.getValue());
				}
			}
			conn.connect();
			PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
			printWriter.write(params);// post的参数 xx=xx&yy=yy
			printWriter.flush();
			printWriter.close();
			if (200 == conn.getResponseCode()) {
				BufferedInputStream bis = new BufferedInputStream(
						conn.getInputStream());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = bis.read(buffer))) {
					baos.write(buffer, 0, len);
					baos.flush();
				}
				baos.close();
				bis.close();
				conn.disconnect();
				return baos.toString("utf-8");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "请求炸了";
	}

	public static String uploadFile(String uploadUrl, List<File> files,
			Map<String, String> params) {
		String response = "";
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			URL url = new URL(uploadUrl);
			if (uploadUrl.startsWith("https")) {
				SslUtils.ignoreSsl();
			}
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			StringBuffer contentBody = new StringBuffer();
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			if (params != null && params.size() > 0) {
				for (String key : params.keySet()) {
					contentBody.append("\r\n").append("\r\n")
							.append(params.get(key)).append("\r\n");
				}
			}
			out.write(contentBody.toString().getBytes("utf-8"));
			if (files != null && files.size() > 0) {
				for (File file : files) {
					contentBody = new StringBuffer();
					out.write(contentBody.toString().getBytes("utf-8"));
					DataInputStream dis = new DataInputStream(
							new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = dis.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					dis.close();
				}
			}
			byte[] endData = contentBody.toString().getBytes();
			out.write(endData);
			out.flush();
			out.close();
			int statusCode = conn.getResponseCode();

			if (statusCode >= 400) {
				is = conn.getErrorStream();
			} else {
				is = conn.getInputStream();
			}// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			response = strBuf.toString();
			br.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return "请求炸了";
	}

	public static String byJson(String path, Map<String, String> headers,
			JSONObject json) {
		try {
			URL url = new URL(path.trim());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			if (headers != null) {
				// 设置请求头
				for (Map.Entry<String, String> en : headers.entrySet()) {
					conn.setRequestProperty(en.getKey(), en.getValue());
				}
			}
			conn.connect();
			PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
			if (200 == conn.getResponseCode()) {
				BufferedInputStream bis = new BufferedInputStream(
						conn.getInputStream());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = bis.read(buffer))) {
					baos.write(buffer, 0, len);
					baos.flush();
				}
				baos.close();
				bis.close();
				conn.disconnect();
				return baos.toString("utf-8");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "请求炸了";
	}

}
