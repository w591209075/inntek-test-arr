package com.example.innotek.logger.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * Created by Administrator on 2014/11/10.
 */
public class RestletUtils
{
	public static String httpUrlConnection(String urlString, String request)
	{
		try
		{
			// 建立连接
			URL url = new URL(urlString);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

			// !!!!需要设置超时时间
			httpConn.setReadTimeout(5 * 1000);
			httpConn.setConnectTimeout(5 * 1000);

			// 设置连接属性
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod("POST");// 设置URL请求方法

			// 设置请求属性
			// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
			byte[] requestStringBytes = request.getBytes("UTF-8");
			httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
			httpConn.setRequestProperty("Content-Type", "application/json");// application/octet-stream
			httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpConn.setRequestProperty("Charset", "UTF-8");

			// 建立输出流，并写入数据
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();
			// 获得响应状态
			int responseCode = httpConn.getResponseCode();

			// 连接成功
			if (HttpURLConnection.HTTP_OK == responseCode)
			{
				// 当正确响应时处理数据
				StringBuffer sb = new StringBuffer();
				String readLine;
				BufferedReader responseReader;
				// 处理响应流，必须与服务器响应流输出的编码一致
				responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null)
				{
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				return sb.toString();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public static String object2Json(Object obj)
	{
		return new JSONSerializer().exclude("class").deepSerialize(obj);
	}

	public static <T> T json2Object(String source, Class<T> bean)
	{
		return new JSONDeserializer<T>().deserialize(source, bean);
	}

}
