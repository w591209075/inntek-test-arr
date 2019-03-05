package com.example.innotek.logger.protocol.bean.base;

/**
 * Created by wanglei on 2014/11/11. Modified by dws on 2015/12/28
 */
public class BaseResponse
{

	private int errCode = -2;
	private String errMsg = "未初始化";

	public int getErrCode()
	{
		return errCode;
	}

	public void setErrCode(int errCode)
	{
		this.errCode = errCode;
	}

	public String getErrMsg()
	{
		return errMsg;
	}

	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}
}
