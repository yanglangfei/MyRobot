package com.ylf.bean;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *   聊天实体类
 *
 */
@SuppressWarnings("serial")
public class ChatMessage implements Serializable{
	/**
	 *  返回消息码
	 */
	private String msgCode;
	/**
	 *   返回消息
	 */
	private String message;
	/**
	 *  消息类型 ----0  接受   ---1 发送
	 */
	private int messageType;
	/**
	 * 聊天时间
	 */
	private String time;
	
	public ChatMessage(String msgCode, String message, int messageType,
			String time) {
		this.msgCode = msgCode;
		this.message = message;
		this.messageType = messageType;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	

}
