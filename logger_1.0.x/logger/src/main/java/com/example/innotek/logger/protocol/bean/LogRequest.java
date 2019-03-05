/**
 * 
 */
package com.example.innotek.logger.protocol.bean;

import com.example.innotek.logger.protocol.bean.base.BaseRequest;

/**
 * @author wuhao
 *2016-5-19
 */
public class LogRequest extends BaseRequest {
	/**数据库唯一id*/
	private long _id;
	/**设备编码**/
	private String Meidcode;
	/**调用类**/
	private String Call_Class;
	/**平台类型1：安卓2：wince**/
	private String Platform;
	/**版本号**/
	private String Version_No;
	/**日志等级**/
	private String Log_Level;
	/**异常类型**/
	private String Exception_Type;
	/**日志描述**/
	private String Log_Message;
	/**日志内容（json）**/
	private String Log_Content;
	/**创建时间**/
	private String Create_Date;
	/**MD5值**/
	private String MD5;
	/**同步次数**/
	private long syncTimes;
	/**同步状态 0 否 1是**/
	private String Sync_Status;
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
	/**
	 * @return the meidcode
	 */
	public String getMeidcode() {
		return Meidcode;
	}
	/**
	 * @param meidcode the meidcode to set
	 */
	public void setMeidcode(String meidcode) {
		Meidcode = meidcode;
	}
	/**
	 * @return the call_Class
	 */
	public String getCall_Class() {
		return Call_Class;
	}
	/**
	 * @param call_Class the call_Class to set
	 */
	public void setCall_Class(String call_Class) {
		Call_Class = call_Class;
	}
	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return Platform;
	}
	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		Platform = platform;
	}
	/**
	 * @return the version_No
	 */
	public String getVersion_No() {
		return Version_No;
	}
	/**
	 * @param version_No the version_No to set
	 */
	public void setVersion_No(String version_No) {
		Version_No = version_No;
	}
	/**
	 * @return the log_Level
	 */
	public String getLog_Level() {
		return Log_Level;
	}
	/**
	 * @param log_Level the log_Level to set
	 */
	public void setLog_Level(String log_Level) {
		Log_Level = log_Level;
	}
	/**
	 * @return the exception_Type
	 */
	public String getException_Type() {
		return Exception_Type;
	}
	/**
	 * @param exception_Type the exception_Type to set
	 */
	public void setException_Type(String exception_Type) {
		Exception_Type = exception_Type;
	}
	/**
	 * @return the log_Message
	 */
	public String getLog_Message() {
		return Log_Message;
	}
	/**
	 * @param log_Message the log_Message to set
	 */
	public void setLog_Message(String log_Message) {
		Log_Message = log_Message;
	}
	/**
	 * @return the log_Content
	 */
	public String getLog_Content() {
		return Log_Content;
	}
	/**
	 * @param log_Content the log_Content to set
	 */
	public void setLog_Content(String log_Content) {
		Log_Content = log_Content;
	}
	/**
	 * @return the create_Date
	 */
	public String getCreate_Date() {
		return Create_Date;
	}
	/**
	 * @param create_Date the create_Date to set
	 */
	public void setCreate_Date(String create_Date) {
		Create_Date = create_Date;
	}
	/**
	 * @return the mD5
	 */
	public String getMD5() {
		return MD5;
	}
	/**
	 * @param mD5 the mD5 to set
	 */
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	/**
	 * @return the sync_Status
	 */
	public String getSync_Status() {
		return Sync_Status;
	}
	/**
	 * @param sync_Status the sync_Status to set
	 */
	public void setSync_Status(String sync_Status) {
		Sync_Status = sync_Status;
	}
	/**
	 * @return the syncTimes
	 */
	public long getSyncTimes() {
		return syncTimes;
	}
	/**
	 * @param syncTimes the syncTimes to set
	 */
	public void setSyncTimes(long syncTimes) {
		this.syncTimes = syncTimes;
	}

}
