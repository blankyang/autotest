package com.test.framework.utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

	public static final MediaType IMG = MediaType.parse("image/png");
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static String get(String url) {
		try {
			OkHttpClient client = new OkHttpClient();
			Request req = new Request.Builder().url(url).build();
			Response response = client.newCall(req).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "请求失败";
	}

	public static String post4Json(String url, String json) {
		try {
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, json);
			Request req = new Request.Builder().url(url).post(body).build();
			Response response = client.newCall(req).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "请求失败";
	}

	public static String post4Img(String url, String json) {
		try {
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, json);
			Request req = new Request.Builder().url(url).post(body).build();
			Response response = client.newCall(req).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "请求失败";
	}

	public static String post4Form(String url, Map<String,String> params) {
		try {
			OkHttpClient client = new OkHttpClient();
			okhttp3.FormBody.Builder builder = new FormBody.Builder();
			for(Map.Entry<String, String> en : params.entrySet()){
				builder.add(en.getKey(), en.getValue());
			}
			RequestBody body = builder.build();
			Request req = new Request.Builder().url(url).post(body).build();
			Response response = client.newCall(req).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "请求失败";
	}
	
	

}
