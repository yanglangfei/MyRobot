package com.ylf.util;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import android.content.Context;
import android.os.Bundle;

public class SpeechUtil {

	private static SpeechUtil speechUtil;
	private static SpeechSynthesizer syn;

	private SpeechUtil() {

	}

	public static SpeechUtil getInstance(Context context) {
		if (speechUtil == null) {
			SpeechUtility.createUtility(context, SpeechConstant.APPID
					+ "=55c2fdba");
			syn = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
				
				@Override
				public void onInit(int arg0) {
					System.out.println("创建实例"+arg0);
					
				}
			});
			speechUtil = new SpeechUtil();
		}
		return speechUtil;
	}

	/**
	 * 语音识别
	 */
	public void RecognizerSpeesh(String message) {
		if (syn != null) {
			syn.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
			syn.setParameter(SpeechConstant.SPEED, "50");
			syn.setParameter(SpeechConstant.VOLUME, "80");
			syn.setParameter(SpeechConstant.ENGINE_TYPE,
					SpeechConstant.TYPE_CLOUD);
			System.out.println("   speek...");
			syn.startSpeaking(message, new SynthesizerListener() {

				@Override
				public void onSpeakResumed() {
					// 会话重置
					System.out.println("充值");

				}

				@Override
				public void onSpeakProgress(int percent, int beginPos, int endPos) {
					// 监听会话进度
					System.out.println("进度："+percent);

				}

				@Override
				public void onSpeakPaused() {
					// 会话暂停
					System.out.println("回话暂停");

				}

				@Override
				public void onSpeakBegin() {
					// 会话开始
					System.out.println("回话开始");

				}

				@Override
				public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
					System.out.println("onEvent...");

				}

				@Override
				public void onCompleted(SpeechError arg0) {
					// 会话结束
					System.out.println("回话结束");

				}

				@Override
				public void onBufferProgress(int percent, int beginPos, int endPos,
						String info) {
					// 缓冲进度回调
					System.out.println("缓冲会掉");
				}
			});
		}
	}

	

}
