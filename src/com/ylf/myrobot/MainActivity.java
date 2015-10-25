package com.ylf.myrobot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.ylf.adapter.ChatAdapter;
import com.ylf.bean.ChatMessage;
import com.ylf.receiver.BatteryReceiver;
import com.ylf.server.DownLoadServer;
import com.ylf.util.HttpUtil;
import com.ylf.util.SpeechUtil;
import com.ylf.util.StringUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity implements OnClickListener {
	private String path;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ListView lv_chat;
	private ChatAdapter adapter;
	private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
	private Context mContext;
	private Button btn_send;
	private EditText et_input;
	private String inputMsg;
	private BatteryReceiver bReceiver;
	public Map<String, String> map = new HashMap<String, String>();
	private SpeechUtil speech;
	private SpeechSynthesizer synthesizer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initWidget();
	}

	private void initWidget() {
		path = StringUtil.SEND_PATH;
		mContext = MainActivity.this;
		speech=SpeechUtil.getInstance(mContext);
		lv_chat = (ListView) findViewById(R.id.lv_chat);
		btn_send = (Button) findViewById(R.id.btn_send);
		et_input = (EditText) findViewById(R.id.et_input);
		;
		btn_send.setOnClickListener(this);
		chatMessages.add(new ChatMessage("0", "您好,很高兴问您服务!", 0, sdf
				.format(new Date())));
		adapter = new ChatAdapter(mContext, chatMessages);
		lv_chat.setAdapter(adapter);
		initVoiceParam();
	}

	private void initVoiceParam() {
		synthesizer = new SpeechSynthesizer(mContext, "",new SpeechSynthesizerListener() {
			
			@Override
			public void onSynthesizeFinish(SpeechSynthesizer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartWorking(SpeechSynthesizer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSpeechStart(SpeechSynthesizer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSpeechResume(SpeechSynthesizer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSpeechProgressChanged(SpeechSynthesizer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSpeechPause(SpeechSynthesizer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSpeechFinish(SpeechSynthesizer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onNewDataArrive(SpeechSynthesizer arg0, byte[] arg1,
					boolean arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(SpeechSynthesizer arg0, SpeechError arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel(SpeechSynthesizer arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBufferProgressChanged(SpeechSynthesizer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		synthesizer.setApiKey("api_key","scret_key");
		synthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,SpeechSynthesizer.SPEAKER_FEMALE);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 监听电量变化的意图
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		bReceiver = new BatteryReceiver();
		registerReceiver(bReceiver, filter);
		//开启下载图片服务
		Intent  serverIntent=new Intent(this,DownLoadServer.class);
		serverIntent.putExtra("name", "jack");
		this.startService(serverIntent);
		/*ServiceConnection connection=new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				
			}
		};
		bindService(serverIntent,connection, BIND_AUTO_CREATE);*/
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_send:
			inputMsg = et_input.getText().toString().trim();
			if (inputMsg.length() > 0) {
				et_input.getText().clear();
				initMyData(inputMsg);
				new SensMessage().execute(inputMsg);
			} else {
				Toast.makeText(mContext, "输入不可为空", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		//解除广播
		unregisterReceiver(bReceiver);
		//停止服务
		Intent  serverIntent=new Intent(this,DownLoadServer.class);
		stopService(serverIntent);
		//绑定服务---解除服务
		//unbindService(conn);
	}

	private void initMyData(String inputMsg) {
		ChatMessage message = new ChatMessage("0", inputMsg, 1,
				sdf.format(new Date()));
		chatMessages.add(message);
		adapter.notifyDataSetChanged();
		lv_chat.smoothScrollToPosition(lv_chat.getBottom() - 1);

	}

	class SensMessage extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			try {
				map.put("key", StringUtil.ROBOT_KEY);
				map.put("info", arg0[0]);
				return HttpUtil.apacheGet(path, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					JSONObject object = new JSONObject(result);
					String text = object.getString("text");
					ChatMessage message = new ChatMessage("0", text, 0,
							sdf.format(new Date()));
					chatMessages.add(message);
					adapter.notifyDataSetChanged();
					synthesizer.speak(text);
					/*speech.RecognizerSpeesh(text);*/
					lv_chat.smoothScrollToPosition(lv_chat.getBottom() - 1);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
