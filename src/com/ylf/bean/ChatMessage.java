package com.ylf.bean;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *   ����ʵ����
 *
 */
@SuppressWarnings("serial")
public class ChatMessage implements Serializable{
	/**
	 *  ������Ϣ��
	 */
	private String msgCode;
	/**
	 *   ������Ϣ
	 */
	private String message;
	/**
	 *  ��Ϣ���� ----0  ����   ---1 ����
	 */
	private int messageType;
	/**
	 * ����ʱ��
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
