package com.example.innotek.logger.protocol.bean.base;

/**
 * Created by wanglei on 2014/11/11.
 */
public class BaseRequest
{

	private String loginKey;
	private long collectorId;

	public String getLoginKey()
	{
		return loginKey;
	}

	public void setLoginKey(String loginKey)
	{
		this.loginKey = loginKey;
	}

	public long getCollectorId()
	{
		return collectorId;
	}

	public void setCollectorId(long collectorId)
	{
		this.collectorId = collectorId;
	}
}
