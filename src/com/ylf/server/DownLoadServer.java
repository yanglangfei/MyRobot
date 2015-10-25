package com.ylf.server;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpStatus;
import com.ylf.myrobot.R;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class DownLoadServer extends Service {
	private static final String DOWNLOAD_PATH = "http://www.baidu.com/img/bdlogo.gif";
	private NotificationManager manager;
	private NotificationCompat.Builder builder;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what==1){
				builder.setProgress(100, 100, false);
				stopSelf();
				Toast.makeText(getApplicationContext(), "下载完成", 5).show();
			}
			builder.setProgress(100, msg.arg1, false);
			manager.notify(1001, builder.build());
		};
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(getApplicationContext());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("down load file");
		builder.setContentText("down load ...");
		// 创建builder 对象
		manager.notify(1001, builder.build());

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String name=intent.getStringExtra("name");
		Log.i("111","name="+name);
		new DownLoadThread().start();
		return super.onStartCommand(intent, flags, startId);
	}

	private class DownLoadThread extends Thread {

		@Override
		public void run() {
			try {
				URL url = new URL(DOWNLOAD_PATH);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() == HttpStatus.SC_OK) {
					InputStream fiS = null;
					// 获取文件总长度
					int totle_length = conn.getContentLength();
					// 当前读取下载的总长度
					int sum_length = 0;
					int len;
					byte data[] = new byte[1];
					fiS = conn.getInputStream();
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					while ((len = fiS.read(data)) != -1) {
						sum_length += len;
						baos.write(data, 0, len);
						// 计算每次下载的文件大小的刻度
						int value = (int) (sum_length / (float) totle_length * 100);
						Message message = Message.obtain();
						message.arg1 = value;
						mHandler.sendMessage(message);
					}
					mHandler.sendEmptyMessage(1);
				}
			} catch (Exception e) {
			}
		}
	}

}
