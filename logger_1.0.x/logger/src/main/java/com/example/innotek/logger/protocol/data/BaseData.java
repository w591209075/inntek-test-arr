package com.example.innotek.logger.protocol.data;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/** 基础数据基类 */
public class BaseData
{

	public static JSONDeserializer<Object> deserializer = new JSONDeserializer<Object>();

	public static JSONSerializer serializer = new JSONSerializer();

	/** yyyy-MM-dd HH:mm:ss */
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	/** yyyy-MM-dd HH:mm */
	public static SimpleDateFormat formatNoSed = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
	/** yyyy-MM-dd HH:mm:ss.SSS */
	public static SimpleDateFormat formatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);

	/**MM.dd*/
	public static SimpleDateFormat formatAccount = new SimpleDateFormat("MM.dd", Locale.CHINA);
	/**yyyy-MM-dd*/
	public static SimpleDateFormat formatAccountTotal = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json)
	{
		if (json == null)
		{
			return null;
		}
		Map<String, Object> map = null;
		try
		{
			map = (Map<String, Object>) deserializer.deserialize(json, Map.class);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return map;
	}

	public static String getUUID()
	{
		return UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.CHINA);
	}
}
