package com.example.innotek.logger.protocol.data;

import com.example.innotek.logger.enums.LogParam;
import com.example.innotek.logger.sqlite.Method;

import java.text.SimpleDateFormat;


public class LogModel {
	/**数据库唯一id*/
	private long _id;
	/**设备编码**/
	private String meidCode;
	/**调用类**/
	private String callClass;
	/**平台类型1：安卓2：wince**/
	private String plateForm;
	/**版本号**/
	private String versionNo;
	/**日志等级**/
	private int logLevel;
	/**异常类型**/
	private String exceptionType;
	/**日志描述**/
	private String logMessage;
	/**日志内容（json）**/
	private String logContent;
	/**创建时间**/
	private String createDate;
	/**MD5值**/
	private String md5;
	/**同步次数**/
	private long syncTimes;
	/**同步状态 0 否 1是**/
	private String syncStatus;
	/**
	 * 无参数构造函数
	 */
	public LogModel()
	{
		
	}
	/**
	 * 根据异常构造函数，其中设备编号，平台，版本号从全局变量获取，日期自动获取，MD5根据异常内容生成
	 * @param callClass
	 * @param Log_Level
	 * @param ex
	 */
	public LogModel(String callClass,int Log_Level,Exception ex)
	{
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");       
		String date= sDateFormat.format(new java.util.Date());   
		this.meidCode=LogParam.MeidCode;
		this.callClass=callClass;
		this.plateForm=LogParam.PlatForm;
		this.versionNo=LogParam.versionNo;
		this.logLevel=Log_Level;
		this.exceptionType=ex.getClass().toString();
		this.logMessage=ex.getMessage();
		this.logContent=Method.getExceptionAllinformation(ex);
		this.createDate=date;
		this.md5=Method.MD5(this.logContent);
		this.syncTimes=0;
		this.syncStatus=LogParam.Up_No;
	}
	/**
	 * 生成logmodel的实体
	 * @param Log_Level  日志等级
	 * @param callClass   调用类
	 * @param exceptionType  日志类型
	 * @param logMessage   日志描述
	 * @param logContent   日志内容
	 */
	public LogModel(int Log_Level,String callClass,String exceptionType,String logMessage,String logContent)
	{
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");       
		String date= sDateFormat.format(new java.util.Date());   
		this.meidCode=LogParam.MeidCode;
		this.callClass=callClass;
		this.plateForm=LogParam.PlatForm;
		this.versionNo=LogParam.versionNo;
		this.logLevel=Log_Level;
		this.exceptionType=exceptionType;
		this.logMessage=logMessage;
		this.logContent=logContent;
		this.createDate=date;
		this.md5=Method.MD5(this.logContent);
		this.syncTimes=0;
		this.syncStatus=LogParam.Up_No;
	}
	/**
	 * 全部参数的构造函数
	 * @param Meidcode    设备号
	 * @param callClass     调用类
	 * @param plateForm     系统平台
	 * @param versionNo     系统版本号
	 * @param logLevel      日志等级
	 * @param exceptionType 日志类型
	 * @param logMessage    日志描述
	 * @param logContent    日志内容
	 * @param createDate    创建时间
	 * @param MD5           MD5值
	 * @param syncStatus    同步状态
	 */
	public LogModel(String Meidcode,String callClass,String plateForm,String versionNo,int logLevel,String exceptionType,
			String logMessage,String logContent,String createDate,String MD5,Long syncTimes,String syncStatus)
	{
		this.meidCode=Meidcode;
		this.callClass=callClass;
		this.plateForm=plateForm;
		this.versionNo=versionNo;
		this.logLevel=logLevel;
		this.exceptionType=exceptionType;
		this.logMessage=logMessage;
		this.logContent=logContent;
		this.createDate=createDate;
		this.md5=MD5;
		this.syncTimes=syncTimes;
		this.syncStatus=syncStatus;
	}

	/**
	 * @return the meidcode
	 */
	public String getmeidCode() {
		return meidCode;
	}

	/**
	 * @param meidcode the meidcode to set
	 */
	public void setmeidCode(String meidcode) {
		meidCode = meidcode;
	}

	/**
	 * @return the call_class
	 */
	public String getcallClass() {
		return callClass;
	}

	/**
	 * @param call_class the call_class to set
	 */
	public void setcallClass(String call_class) {
		callClass = call_class;
	}

	/**
	 * @return the platform
	 */
	public String getplatform() {
		return plateForm;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setplatform(String platform) {
		plateForm = platform;
	}

	/**
	 * @return the version_No
	 */
	public String getversionNo() {
		return versionNo;
	}

	/**
	 * @param version_No the version_No to set
	 */
	public void setversionNo(String version_No) {
		versionNo = version_No;
	}

	/**
	 * @return the log_Level
	 */
	public int getlogLevel() {
		return logLevel;
	}

	/**
	 * @param log_Level the log_Level to set
	 */
	public void setlogLevel(int log_Level) {
		logLevel = log_Level;
	}

	/**
	 * @return the exception_Type
	 */
	public String getexceptionType() {
		return exceptionType;
	}

	/**
	 * @param exception_Type the exception_Type to set
	 */
	public void setexceptionType(String exception_Type) {
		exceptionType = exception_Type;
	}

	/**
	 * @return the log_Message
	 */
	public String getlogMessage() {
		return logMessage;
	}

	/**
	 * @param log_Message the log_Message to set
	 */
	public void setlogMessage(String log_Message) {
		logMessage = log_Message;
	}

	/**
	 * @return the log_Content
	 */
	public String getlogContent() {
		return logContent;
	}

	/**
	 * @param log_Content the log_Content to set
	 */
	public void setlogContent(String log_Content) {
		logContent = log_Content;
	}

	/**
	 * @return the creat_Date
	 */
	public String getcreateDate() {
		return createDate;
	}

	/**
	 * @param creat_Date the creat_Date to set
	 */
	public void setcreateDate(String creat_Date) {
		createDate = creat_Date;
	}

	/**
	 * @return the mD5
	 */
	public String getmd5() {
		return md5;
	}

	/**
	 * @param mD5 the mD5 to set
	 */
	public void setmd5(String mD5) {
		md5 = mD5;
	}

	/**
	 * @return the sync_Status
	 */
	public String getsyncStatus() {
		return syncStatus;
	}

	/**
	 * @param sync_Status the sync_Status to set
	 */
	public void setsyncStatus(String sync_Status) {
		syncStatus = sync_Status;
	}
	/**
	 * @return the _id
	 */
	public long get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id(long _id) {
		this._id = _id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @return the syncTimes
	 */
	public long getsyncTimes() {
		return syncTimes;
	}
	/**
	 * @param syncTimes the syncTimes to set
	 */
	public void setsyncTimes(long syncTimes) {
		this.syncTimes = syncTimes;
	}	
}
