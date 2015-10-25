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
					System.out.println("����ʵ��"+arg0);
					
				}
			});
			speechUtil = new SpeechUtil();
		}
		return speechUtil;
	}

	/**
	 * ����ʶ��
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
					// �Ự����
					System.out.println("��ֵ");

				}

				@Override
				public void onSpeakProgress(int percent, int beginPos, int endPos) {
					// �����Ự����
					System.out.println("���ȣ�"+percent);

				}

				@Override
				public void onSpeakPaused() {
					// �Ự��ͣ
					System.out.println("�ػ���ͣ");

				}

				@Override
				public void onSpeakBegin() {
					// �Ự��ʼ
					System.out.println("�ػ���ʼ");

				}

				@Override
				public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
					System.out.println("onEvent...");

				}

				@Override
				public void onCompleted(SpeechError arg0) {
					// �Ự����
					System.out.println("�ػ�����");

				}

				@Override
				public void onBufferProgress(int percent, int beginPos, int endPos,
						String info) {
					// ������Ȼص�
					System.out.println("������");
				}
			});
		}
	}

	

}
